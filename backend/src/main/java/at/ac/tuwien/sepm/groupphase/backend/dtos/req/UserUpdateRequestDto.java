package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserUpdateRequestDto {

    @NotNull(message = "Id must not be null")
    private Long id;
    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    @Email
    private String email;

    @NotNull(message = "Password must not be null")
    @NotBlank(message = "Password must not be blank")
    private String password;

    private String newPassword;

    public UserUpdateRequestDto() {
    }

    public UserUpdateRequestDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserUpdateRequestDto(Long id, String email, String password, String newPassword) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
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

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String toString() {
        return "UserLoginDto{"
            + "email='" + email + '\''
            + ", password='" + password + '\''
            + ", newPassword='" + newPassword + "\'"
            + '}';
    }
}
