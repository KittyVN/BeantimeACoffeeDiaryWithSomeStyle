package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanExtractionsListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.CoffeeBeanRatingListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.ExtractionListDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserAdminEditDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserCredentialsDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserResetPasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserUpdateRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.repository.CoffeeBeanRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.ExtractionRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.RecipeRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.UserRepository;
import at.ac.tuwien.sepm.groupphase.backend.security.JwtTokenizer;
import at.ac.tuwien.sepm.groupphase.backend.service.CoffeeBeanService;
import at.ac.tuwien.sepm.groupphase.backend.service.ExtractionService;
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

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final CoffeeBeanRepository beanRepository;
    private final CoffeeBeanService beanService;
    private final ExtractionRepository extractionRepository;
    private final ExtractionService extractionService;
    private final RecipeRepository recipeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;
    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CoffeeBeanRepository beanRepository, ExtractionRepository extractionRepository,
                           RecipeRepository recipeRepository, ExtractionService extractionService,
                           CoffeeBeanService beanService,
                           UserMapper mapper, PasswordEncoder passwordEncoder, JwtTokenizer jwtTokenizer) {
        this.userRepository = userRepository;
        this.beanRepository = beanRepository;
        this.extractionRepository = extractionRepository;
        this.recipeRepository = recipeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
        this.mapper = mapper;
        this.extractionService = extractionService;
        this.beanService = beanService;
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
    public User findApplicationUserByEmail(String email) throws NotFoundException {
        LOGGER.debug("Find application user by email");
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        }
        throw new NotFoundException(String.format("Could not find the user with the email address %s", email));
    }

    @Override
    public String login(UserLoginDto userLoginDto) throws BadCredentialsException {
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
        User user = new User(userRegisterDto.getEmail(), userRegisterDto.getUsername(),
            passwordEncoder.encode(userRegisterDto.getPassword()), UserRole.USER);
        userRepository.save(user);

        UserCredentialsDto userDetails = loadUserByUsername(userRegisterDto.getEmail());
        List<String> roles = userDetails.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        return jwtTokenizer.getAuthToken(userDetails.getId().toString(), userDetails.getUsername(), roles);
    }

    @Override
    public void updateUser(UserUpdateRequestDto userUpdateRequestDto) throws BadCredentialsException {
        LOGGER.debug("Update user {}", userUpdateRequestDto);
        User user = userRepository.findFirstById(userUpdateRequestDto.getId());
        if (passwordEncoder.matches(userUpdateRequestDto.getPassword(), user.getPassword())) {
            user.setEmail(userUpdateRequestDto.getEmail());
            user.setUsername(userUpdateRequestDto.getUsername());
            if (userUpdateRequestDto.getNewPassword() != null) {
                user.setPassword(passwordEncoder.encode(userUpdateRequestDto.getNewPassword()));
            }
            userRepository.save(user);
        } else {
            throw new BadCredentialsException("Password is incorrect");
        }
    }

    @Override
    public void resetPassword(UserResetPasswordDto userToReset) throws UsernameNotFoundException {
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
    public void deleteUser(Long id) {
        extractionRepository.deleteByUserId(id);
        beanRepository.deleteCoffeeBeanByUserId(id);
        userRepository.deleteById(id);
    }

    public Stream<UserDetailDto> search(UserSearchDto searchParameters) {
        LOGGER.trace("Search users by parameters {}", searchParameters);
        return userRepository.search(searchParameters).stream().map(mapper::entityToDto);
    }

    @Override
    public UserDetailDto getById(Long id) throws NotFoundException {
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
        if (user != null) {
            user.setRole(userDto.getRole());
            user.setActive(userDto.isActive());
            userRepository.save(user);
            return mapper.entityToDto(user);
        } else {
            throw new NotFoundException(String.format("No user with ID %d found", id));
        }
    }

    @Override
    public UserProfileDto getProfileById(Long id) throws NotFoundException {
        User user = userRepository.findFirstById(id);
        if (user != null) {
            List<ExtractionListDto> topRatedList = extractionService.getTop5RatedByUserId(id);
            List<CoffeeBeanExtractionsListDto> topExtractionsList = beanService.getTop5ExtractedByUserId(id);
            List<CoffeeBeanRatingListDto> topRatingsList = beanService.getTop5RatedByUserId(id);

            return new UserProfileDto(
                user.getUsername(),
                extractionService.getExtractionMatrixByUserId(id),
                topRatedList.toArray(new ExtractionListDto[topRatedList.size()]),
                topExtractionsList.toArray(new CoffeeBeanExtractionsListDto[topExtractionsList.size()]),
                topRatingsList.toArray(new CoffeeBeanRatingListDto[topRatingsList.size()]));
        } else {
            throw new NotFoundException(String.format("No user with ID %d found", id));
        }

    }
}
