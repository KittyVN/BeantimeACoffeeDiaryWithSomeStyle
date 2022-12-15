package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserLoginDto;
import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.security.PermitAll;

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
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.register(userRegisterDto);
    }


    //TODO: remove me when authentication is fully tested and integrated
    @Secured("ROLE_ADMIN")
    @GetMapping("test")
    public String testAuth() {
        return "it works";
    }


}
