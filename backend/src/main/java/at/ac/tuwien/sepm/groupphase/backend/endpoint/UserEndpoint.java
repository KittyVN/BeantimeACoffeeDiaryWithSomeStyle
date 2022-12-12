package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserUpdateRequestDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserUpdateResponseDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.PermitAll;
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

    //TODO: proper response handling, proper authentication
    @PermitAll
    @GetMapping("/{token}")
    public UserUpdateResponseDto findApplicationUserByToken(@PathVariable String token) {

        //Getting email out of jwt token

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        String email = payload.split(",")[2].split(":")[1].replace("\"", "");

        //Getting user with corresponding email and returning it

        User user = service.findApplicationUserByEmail(email);
        return new UserUpdateResponseDto(user.getId(), user.getEmail(), "");
    }

    //TODO: proper response handling, proper authentication
    @PermitAll
    @PutMapping("/{token}")
    public String updateUser(@PathVariable String token, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {

        //Getting email out of jwt token

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        String email = payload.split(",")[2].split(":")[1].replace("\"", "");

        //Getting user with corresponding email

        User user = service.findApplicationUserByEmail(email);

        //Checking if the user that is sending the request is authentic

        if (user.getId().equals(userUpdateRequestDto.getId())) {
            return service.updateUser(userUpdateRequestDto);
        }else{
            return "Cant change a user thats not YOU!";
        }
    }
}
