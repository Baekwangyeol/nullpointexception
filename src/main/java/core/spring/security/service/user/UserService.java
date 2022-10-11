package core.spring.security.service.user;

import core.spring.security.dto.UserDto;

public interface UserService {
     Long userJoin(UserDto.RequestDto dto);
}
