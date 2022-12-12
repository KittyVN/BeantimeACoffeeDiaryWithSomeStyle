package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.*;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.debug("Load all user by email");
        try {
            User user = findApplicationUserByEmail(email);

            List<GrantedAuthority> grantedAuthorities;
            if (user.getRole().equals(UserRole.ADMIN)) {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
            } else {
                grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            }

            return new org.springframework.security.core.userdetails
                .User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, grantedAuthorities);
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
        UserDetails userDetails = loadUserByUsername(userLoginDto.getEmail());
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
            return jwtTokenizer.getAuthToken(userDetails.getUsername(), roles);
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

        UserDetails userDetails = loadUserByUsername(userRegisterDto.getEmail());
        List<String> roles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        return jwtTokenizer.getAuthToken(userDetails.getUsername(), roles);
    }

    @Override
    public Stream<UserDetailDto> search(UserSearchDto searchParameters) {
        LOGGER.trace("Search users by parameters {}", searchParameters);
        return userRepository.search(searchParameters).stream().map(mapper::entityToDto);
    }

    @Override
    public UserDetailDto getById(Long id) {
        LOGGER.trace("Get user by id {}", id);
        User user = userRepository.findFirstById(id);
        if (user == null) {
            throw new NotFoundException(String.format("No user with ID %d found", id));
        }
        return mapper.entityToDto(userRepository.findFirstById(id));
    }

    @Override
    public UserDetailDto updateByAdmin(Long id, UserAdminEditDto userDto) throws NotFoundException {
        User user = userRepository.findFirstById(id);
        user.setRole(userDto.getRole());
        user.setActive(userDto.isActive());
        userRepository.save(user);

        return mapper.entityToDto(user);
    }
}
