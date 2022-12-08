package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDto;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = UserEndpoint.BASE_PATH)
public class UserEndpoint {
    static final String BASE_PATH = "/users";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService service;

    public UserEndpoint(UserService service) { this.service = service; }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public Stream<UserDto> searchUsers(@RequestBody UserDto searchParameters) {
        LOGGER.info("GET " + BASE_PATH);
        LOGGER.info("Request parameters: {}", searchParameters);
        return service.search(searchParameters);
    }
}
