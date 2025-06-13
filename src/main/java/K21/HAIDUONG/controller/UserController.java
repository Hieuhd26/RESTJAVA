package K21.HAIDUONG.controller;

import K21.HAIDUONG.dto.request.UserCreationRequest;
import K21.HAIDUONG.dto.request.UserUpdateRequest;
import K21.HAIDUONG.dto.response.ApiResponse;
import K21.HAIDUONG.dto.response.UserCreationResponse;
import K21.HAIDUONG.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
