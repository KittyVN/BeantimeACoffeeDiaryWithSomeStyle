package at.ac.tuwien.sepm.groupphase.backend.service;

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
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Stream;

public interface UserService extends UserDetailsService {

    /**
     * Find a user in the context of Spring Security based on the username
     * <br>
     * For more information have a look at this tutorial:
     * https://www.baeldung.com/spring-security-authentication-with-a-database
     *
     * @param username the username
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exist
     */
    @Override
    UserCredentialsDto loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Find an application user based on the username.
     *
     * @param username the username
     * @return an application user
     * @throws NotFoundException if user by username does not exist
     */
    User findApplicationUserByUsername(String username) throws NotFoundException;

    /**
     * Find an application user based on the email address.
     *
     * @param email the email address
     * @return an application user
     * @throws NotFoundException if user by email does not exist
     */
    User findApplicationUserByEmail(String email) throws NotFoundException;

    /**
     * Log in a user.
     *
     * @param userLoginDto login credentials
     * @return the JWT, if successful
     * @throws BadCredentialsException if credentials are bad
     */
    String login(UserLoginDto userLoginDto) throws BadCredentialsException;

    /**
     * Register a new user.
     *
     * @param userRegisterDto registration data
     * @return the JWT, if successful
     */
    String register(UserRegisterDto userRegisterDto);

    /**
     * Updates an already existing user.
     * Checks if the request sender is not trying to change someone else's data
     *
     * @param userUpdateRequestDto the new user data
     * @throws AccessDeniedException in case the sender of the request is trying to change data of another user
     * @throws BadCredentialsException if password is false
     */
    void updateUser(UserUpdateRequestDto userUpdateRequestDto) throws AccessDeniedException, BadCredentialsException;

    /**
     * Reset the password for a given email.
     *
     * @param email emailaddress
     * @throws UsernameNotFoundException if user doesnt exist
     */
    void resetPassword(UserResetPasswordDto email) throws UsernameNotFoundException;

    /**
     * Deletes an account.
     *
     * @param id account Id
     */
    void deleteUser(Long id);

    /**
     * Search for users matching the criteria in {@code searchParameters}.
     * <p>
     * A user is considered matched,
     * if its id matches {@code searchParameters.id},
     * if its email contains {@code searchParameters.email},
     * if its role matches {@code searchParameters.role}.
     * All parameters are optional.
     * </p>
     *
     * @param searchParameters object containing the search parameters to match
     * @return a stream containing users matching the criteria in {@code searchParameters}
     */
    Stream<UserDetailDto> search(UserSearchDto searchParameters);

    /**
     * Get UserDetailDto by id.
     *
     * @param id of the user to be retrieved
     * @return a UserDetailDto of the matching user.
     * @throws NotFoundException if no user with the given ID exists.
     */
    UserDetailDto getById(Long id) throws NotFoundException;

    /**
     * Update user by id with the UserDetailDto provided.
     *
     * @param id of the user to be updated
     * @param userDto attributes to update (only {@code userDetail.role} and {@code userDetail.active}
     * @return the new UserDetailDto of the user
     * @throws NotFoundException if no user with the given ID exists.
     */
    UserDetailDto updateByAdmin(Long id, UserAdminEditDto userDto) throws NotFoundException;

    /**
     * Get UserProfileDto by user id.
     *
     * @param id of the user.
     * @return UserProfileDto of the specified user.
     * @throws NotFoundException if no user with the given ID exists.
     */
    UserProfileDto getProfileById(Long id) throws NotFoundException;
}
