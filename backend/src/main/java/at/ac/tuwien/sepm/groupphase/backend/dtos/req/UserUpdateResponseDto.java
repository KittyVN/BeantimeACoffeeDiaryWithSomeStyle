package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserUpdateResponseDto {

    @NotNull(message = "Id must not be null")
    private Long id;
    @NotNull(message = "Email must not be null")
    @Email
    private String email;

    @NotNull(message = "Password must not be null")
    private String password;

    public UserUpdateResponseDto() {
    }

    public UserUpdateResponseDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserLoginDto{"
            + "email='" + email + '\''
            + ", password='" + password + '\''
            + '}';
    }
}
