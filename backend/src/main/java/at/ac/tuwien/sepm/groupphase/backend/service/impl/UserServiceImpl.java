package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserCredentialsDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserUpdateRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserResetPasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.invoke.MethodHandles;

import org.springframework.security.access.AccessDeniedException;

import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final UserMapper mapper;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper mapper, PasswordEncoder passwordEncoder, JwtTokenizer jwtTokenizer) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
        this.mapper = mapper;
    }

    @Override
    public UserCredentialsDto loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all user by email");
        try {
            User user = findApplicationUserByEmail(email);

            List<GrantedAuthority> grantedAuthorities;
            if (user.getRole().equals(UserRole.ADMIN)) {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
            } else {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            }
            return new UserCredentialsDto(user.getId(), user.getEmail(), user.getPassword(), grantedAuthorities);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    @Override
    public User findApplicationUserByEmail(String email) {
        LOGGER.debug("Find application user by email");
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        }
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }


    @Override
    public String login(UserLoginDto userLoginDto) {
        LOGGER.debug("Login user {}", userLoginDto);
        UserCredentialsDto userDetails = loadUserByUsername(userLoginDto.getEmail());
        if (userDetails != null
            && userDetails.isAccountNonExpired()
            && userDetails.isAccountNonLocked()
            && userDetails.isCredentialsNonExpired()
            && passwordEncoder.matches(userLoginDto.getPassword(), userDetails.getPassword())
        ) {
            List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
            return jwtTokenizer.getAuthToken(userDetails.getId().toString(), userDetails.getUsername(), roles);
        }
        throw new BadCredentialsException("Username or password is incorrect or account is locked");
    }

    @Override
    public String register(UserRegisterDto userRegisterDto) {
        LOGGER.debug("Register user {}", userRegisterDto);
        User user = User
            .UserBuilder
            .aUser()
            .withEmail(userRegisterDto.getEmail())
            .withPassword(passwordEncoder.encode(userRegisterDto.getPassword()))
            .withRole(UserRole.USER)
            .build();
        userRepository.save(user);

        UserCredentialsDto userDetails = loadUserByUsername(userRegisterDto.getEmail());
        List<String> roles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        return jwtTokenizer.getAuthToken(userDetails.getId().toString(), userDetails.getUsername(), roles);
    }

    @Override
    public void updateUser(String token, UserUpdateRequestDto userUpdateRequestDto) throws AccessDeniedException {
        LOGGER.debug("Update user {}", userUpdateRequestDto);
        if (!getUserId(token).equals(userUpdateRequestDto.getId())) {
            throw new AccessDeniedException("Not authorized to change another users data!");
        }
        User user = User
            .UserBuilder
            .aUser()
            .withId(userUpdateRequestDto.getId())
            .withEmail(userUpdateRequestDto.getEmail())
            .withPassword(passwordEncoder.encode(userUpdateRequestDto.getPassword()))
            .withRole(UserRole.USER)
            .build();
        userRepository.save(user);
    }

    private Long getUserId(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        return Long.parseLong(payload.split(",")[2].split(":")[1].replace("\"", ""));
    }

    @Override
    public void resetPassword(UserResetPasswordDto userToReset) {
        String randomPassword = RandomStringUtils.randomAlphanumeric(10);
        User user = userRepository.findByEmail(userToReset.getEmail());
        if (user != null) {
            userRepository.updatePasswordByEmail(userToReset.getEmail(), passwordEncoder.encode(randomPassword));

            String subject = "Your Password has been reset";
            String content = "Hi, this is your new password: " + randomPassword;
            content += "\nNote: for security reason, "
                + "you must change your password after logging in.";
            try {
                EmailUtility.sendEmail("smtp.gmail.com", "587", "noreplybeantime@gmail.com", "Beantime", "xnpkqllidlxxeoqh",
                    userToReset.getEmail(), subject, content);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            throw new UsernameNotFoundException("User doesnt exist");
        }
    }

    @Override
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    public Stream<UserDetailDto> search(UserSearchDto searchParameters) {
        LOGGER.trace("Search users by parameters {}", searchParameters);
        return userRepository.search(searchParameters).stream().map(mapper::entityToDto);
    }
}
