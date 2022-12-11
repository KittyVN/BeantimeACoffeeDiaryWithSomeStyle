package at.ac.tuwien.sepm.groupphase.backend.endpoint;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Claims;

import javax.annotation.security.PermitAll;
import java.util.Base64;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationEndpoint {

    private final UserService userService;

    public AuthenticationEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @PostMapping("login")
    public String login(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    @PermitAll
    @PostMapping("register")
    public String register(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }

    //TODO: remove me when authentication is fully tested and integrated
    @Secured("ROLE_ADMIN")
    @GetMapping("test")
    public String testAuth() {
        return "it works";
    }

    @PermitAll
    @GetMapping("find/{token}")
    public UserRegisterDto findApplicationUserByToken(@PathVariable String token) {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        System.out.println(payload);
        String email = payload.split(",")[2].split(":")[1].replace("\"","");
        System.out.println(email);
        User user = userService.findApplicationUserByEmail(email);
        return new UserRegisterDto(user.getEmail(), user.getPassword());
    }
}
