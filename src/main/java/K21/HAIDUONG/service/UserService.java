package K21.HAIDUONG.service;

import K21.HAIDUONG.dto.request.UserCreationRequest;
import K21.HAIDUONG.dto.request.UserUpdateRequest;
import K21.HAIDUONG.dto.response.ApiResponse;
import K21.HAIDUONG.dto.response.UserCreationResponse;

import java.util.List;

public interface UserService {
    public UserCreationResponse createUser(UserCreationRequest request);

    ApiResponse<List<UserCreationResponse>> getAll();

    ApiResponse<UserCreationResponse> updateUser(Long id, UserUpdateRequest request);

    ApiResponse<String> deleteUser(Long id);

    ApiResponse<UserCreationResponse> getUserById(Long id);
}
