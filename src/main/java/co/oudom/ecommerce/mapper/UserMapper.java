package co.oudom.ecommerce.mapper;

import co.oudom.ecommerce.domain.User;
import co.oudom.ecommerce.features.auth.dto.RegisterRequest;
import co.oudom.ecommerce.features.user.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    User fromRegisterRequest(RegisterRequest registerRequest);

}
