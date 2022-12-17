package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserResetPasswordDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserUpdateRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
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
        return new UserResetPasswordDto(service.findApplicationUserByEmail(email.trim()).getEmail());
    }

    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/resetpassword")
    public void resetPassword(@Valid @RequestBody UserResetPasswordDto emailToReset) {
        service.resetPassword(emailToReset);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        service.deleteUser(id);
    }


    //TODO: proper response handling
    @Secured("ROLE_USER")
    @PutMapping("/{id}")
    public void updateUser(@RequestHeader("Authorization") String token, @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        try {
            service.updateUser(token, userUpdateRequestDto);
        } catch (AccessDeniedException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage(), ex);
        }

    }
}
