package K21.HAIDUONG.mapper;

import K21.HAIDUONG.dto.request.UserCreationRequest;
import K21.HAIDUONG.dto.request.UserUpdateRequest;
import K21.HAIDUONG.dto.response.UserCreationResponse;
import K21.HAIDUONG.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserCreationResponse toUserCreationResponse(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserCreationRequest partialUpdate(UserCreationResponse userCreationResponse, @MappingTarget UserCreationRequest userCreationRequest);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}