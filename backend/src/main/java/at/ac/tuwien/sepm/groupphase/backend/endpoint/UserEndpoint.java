package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserUpdateRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.Base64;
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

    @Secured("ROLE_USER")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long tokenId = getUserId(token);
        if (id.equals(tokenId)) {
            service.deleteUser(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


    //TODO: proper response handling
    @Secured("ROLE_USER")
    @PutMapping("/{id}")
    public String updateUser(@RequestHeader("Authorization") String token, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        Long id = getUserId(token);
        if (id.equals(userUpdateRequestDto.getId())) {
            return service.updateUser(userUpdateRequestDto);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    private Long getUserId(String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        return Long.parseLong(payload.split(",")[2].split(":")[1].replace("\"", ""));
    }
}
