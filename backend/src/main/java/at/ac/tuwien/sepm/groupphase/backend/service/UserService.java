package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserAdminEditDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserCredentialsDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserResetPasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserUpdateRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Stream;

public interface UserService extends UserDetailsService {

    /**
     * Find a user in the context of Spring Security based on the email address
     * <br>
     * For more information have a look at this tutorial:
     * https://www.baeldung.com/spring-security-authentication-with-a-database
     *
     * @param email the email address
     * @return a Spring Security user
     * @throws UsernameNotFoundException is thrown if the specified user does not exists
     */
    @Override
    UserCredentialsDto loadUserByUsername(String email) throws UsernameNotFoundException;

    /**
     * Find an application user based on the email address.
     *
     * @param email the email address
     * @return a application user
     */
    User findApplicationUserByEmail(String email);

    /**
     * Log in a user.
     *
     * @param userLoginDto login credentials
     * @return the JWT, if successful
     * @throws org.springframework.security.authentication.BadCredentialsException if credentials are bad
     */
    String login(UserLoginDto userLoginDto);

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
     */
    void updateUser(UserUpdateRequestDto userUpdateRequestDto) throws AccessDeniedException;

    /**
     * Reset the password for a given email.
     *
     * @param email emailaddress
     */
    void resetPassword(UserResetPasswordDto email);

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
}
