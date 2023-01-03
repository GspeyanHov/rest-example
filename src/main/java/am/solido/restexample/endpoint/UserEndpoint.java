package am.solido.restexample.endpoint;

import am.solido.restexample.dto.AuthUserDto;
import am.solido.restexample.dto.CreateUserDto;
import am.solido.restexample.dto.UserAuthResponseDto;
import am.solido.restexample.mapper.UserMapper;
import am.solido.restexample.model.Role;
import am.solido.restexample.model.User;
import am.solido.restexample.repository.UserRepository;
import am.solido.restexample.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserEndpoint {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody CreateUserDto createUserDto) {
        Optional<User> userByEmail = userRepository.findByEmail(createUserDto.getEmail());
        if (userByEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserDto);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userMapper.map(userRepository.save(user)));
    }

    @PostMapping("/user/auth")
    public ResponseEntity<?> userAuth(@RequestBody AuthUserDto authUserDto) {
        Optional<User> userByEmail = userRepository.findByEmail(authUserDto.getEmail());
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            if (passwordEncoder.matches(authUserDto.getPassword(), user.getPassword())) {
                log.info("User with username {} get Auth token", user.getEmail());
                return ResponseEntity.ok(UserAuthResponseDto.builder()
                        .user(userMapper.map(user))
                        .token(jwtTokenUtil.generateToken(user.getEmail()))
                        .build()
                );
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
