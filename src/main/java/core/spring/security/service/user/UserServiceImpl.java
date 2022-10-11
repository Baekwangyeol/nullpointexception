package core.spring.security.service.user;


import core.spring.security.domain.user.Account;
import core.spring.security.dto.UserDto;
import core.spring.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Override
    public Long userJoin(UserDto.RequestDto dto) {

        dto.encryptPassword(encoder.encode(dto.getPassword()));

        Account account = dto.toEntity();
        userRepository.save(account);

        log.info("DB에 회원 저장 성공");
        return account.getId();
    }
}
