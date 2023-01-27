package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.persistence.NamedStoredProcedureQueries;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UserRegisterDto {

    @NotNull(message = "Email must not be null")
    @Email
    private String email;

    @NotNull(message = "Password must not be null")
    private String password;

    @NotNull(message = "Username must not be null")
    private String username;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() { return username; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRegisterDto userRegisterDto)) {
            return false;
        }
        return Objects.equals(email, userRegisterDto.email)
            && Objects.equals(password, userRegisterDto.password);
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
