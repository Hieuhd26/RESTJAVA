package K21.HAIDUONG.controller;

import K21.HAIDUONG.dto.request.UserCreationRequest;
import K21.HAIDUONG.dto.request.UserUpdateRequest;
import K21.HAIDUONG.dto.response.ApiResponse;
import K21.HAIDUONG.dto.response.UserCreationResponse;
import K21.HAIDUONG.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;


    @PostMapping()
    public UserCreationResponse user(@RequestBody UserCreationRequest request) {
        return userService.createUser(request);
    }

    @GetMapping()

    //@PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserCreationResponse>> getAllUser() {
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserCreationResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserCreationResponse> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
}
