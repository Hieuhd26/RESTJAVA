package K21.HAIDUONG.service.impl;

import K21.HAIDUONG.dto.request.UserCreationRequest;
import K21.HAIDUONG.dto.request.UserUpdateRequest;
import K21.HAIDUONG.dto.response.ApiResponse;
import K21.HAIDUONG.dto.response.UserCreationResponse;
import K21.HAIDUONG.exception.AppException;
import K21.HAIDUONG.exception.ErrorCode;
import K21.HAIDUONG.mapper.UserMapper;
import K21.HAIDUONG.model.User;
import K21.HAIDUONG.repository.UserRepository;
import K21.HAIDUONG.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserCreationResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail().trim())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(request.getUsername().trim())
                .email(request.getEmail().trim())
                .password(passwordEncoder.encode(request.getPassword().trim()))
                .dob(request.getDob())
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.toUserCreationResponse(savedUser);
    }

    @Override
    public ApiResponse<List<UserCreationResponse>> getAll() {
        List<User> users = userRepository.findAll();

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Current user: {}", authentication.getName());
        authentication.getAuthorities().forEach(auth -> log.info(auth.getAuthority()));

        List<UserCreationResponse> userCreationResponses = users.stream().map(user -> userMapper.toUserCreationResponse(user)).toList();
        return ApiResponse.<List<UserCreationResponse>>builder()
                .result(userCreationResponses).build();
    }

    @Override
    @Transactional
    public ApiResponse<UserCreationResponse> updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword().trim()));
        userRepository.save(user);
        UserCreationResponse userCreationResponse = userMapper.toUserCreationResponse(user);
        return ApiResponse.<UserCreationResponse>builder()
                .result(userCreationResponse)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
        return ApiResponse.<String>builder()
                .message("User deleted successfully")
                .build();
    }

    @Override
    public ApiResponse<UserCreationResponse> getUserById(Long id) {
        // KIEM TRA CHI TRA VE THONG TIN NGUOI DANG DANG NHAP
        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!user.getUsername().equals(currentName)){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        UserCreationResponse userCreationResponse = userMapper.toUserCreationResponse(user);
        return ApiResponse.<UserCreationResponse>builder()
                .result(userCreationResponse)
                .build();


    }
}
