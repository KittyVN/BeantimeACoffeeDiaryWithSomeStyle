package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserAdminEditDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserProfileDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserResetPasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserUpdateRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = UserEndpoint.BASE_PATH)
public class UserEndpoint {
    static final String BASE_PATH = "/api/v1/users";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService service;

    public UserEndpoint(UserService service) {
        this.service = service;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public Stream<UserDetailDto> searchUsers(UserSearchDto searchParameters) {
        LOGGER.info("GET " + BASE_PATH);
        LOGGER.info("Request parameters: {}", searchParameters);
        return service.search(searchParameters);
    }

    @PermitAll
    @GetMapping("/checkemail")
    public UserResetPasswordDto checkEmail(@Valid @RequestParam String email) {
        LOGGER.info("GET " + BASE_PATH + "/checkemail");
        LOGGER.info("Request parameters: {}", email);
        return new UserResetPasswordDto(service.findApplicationUserByEmail(email.trim()).getEmail());
    }

    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/resetpassword")
    public void resetPassword(@Valid @RequestBody UserResetPasswordDto emailToReset) {
        LOGGER.info("PUT " + BASE_PATH + "/resetpassword");
        LOGGER.info("Request parameters: {}", emailToReset.getEmail());
        service.resetPassword(emailToReset);
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) "
        + "and authentication.principal.equals(#id.toString())")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        LOGGER.info("DELETE " + BASE_PATH + " with id: {}", id);
        service.deleteUser(id);
    }


    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) "
        + "and authentication.principal.equals(#id.toString()) "
        + "and authentication.principal.equals(#userUpdateRequestDto.getId().toString())")
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        try {
            service.updateUser(userUpdateRequestDto);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("admin/{id}")
    public UserDetailDto getById(@PathVariable Long id) {
        LOGGER.info(String.format("GET %s/%d", BASE_PATH, id));
        LOGGER.info("Request id: {}", id);
        try {
            return service.getById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @PreAuthorize("(hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')) "
        + "and authentication.principal.equals(#id.toString()) ")
    @GetMapping("{id}")
    public UserDetailDto getSelf(@PathVariable Long id) {
        LOGGER.info(String.format("GET %s/%d", BASE_PATH, id));
        LOGGER.info("Request id: {}", id);
        try {
            return service.getById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("{id}")
    public UserDetailDto updateByAdmin(@PathVariable Long id, @RequestBody UserAdminEditDto userDto) {
        LOGGER.info(String.format("PUT %s/%d", BASE_PATH, id));
        LOGGER.info("Request id: {}, Request body {}", id, userDto);
        try {
            return service.updateByAdmin(id, userDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @PreAuthorize("authentication.principal.equals(#id.toString())")
    @GetMapping("profile/{id}")
    public UserProfileDto getProfileById(@PathVariable Long id) {
        LOGGER.info(String.format("GET %s/%d", BASE_PATH + "/profile", id));
        LOGGER.info("Request id: {}", id);
        try {
            return service.getProfileById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }
}
