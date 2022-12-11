package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UserUpdateDto {

    @NotNull(message = "Id must not be null")
    private Long id;
    @NotNull(message = "Email must not be null")
    @Email
    private String email;

    @NotNull(message = "Password must not be null")
    private String password;

    public UserUpdateDto() {
    }

    public UserUpdateDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() { return  id; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserUpdateDto userUpdateDto)) {
            return false;
        }
        return Objects.equals(email, userUpdateDto.email)
            && Objects.equals(password, userUpdateDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "UserLoginDto{"
            + "email='" + email + '\''
            + ", password='" + password + '\''
            + '}';
    }
}
