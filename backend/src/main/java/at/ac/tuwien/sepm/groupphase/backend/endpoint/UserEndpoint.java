package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserAdminEditDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @Secured("ROLE_ADMIN")
    @GetMapping("{id}")
    public UserDetailDto getById(@PathVariable Long id) {
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
}
