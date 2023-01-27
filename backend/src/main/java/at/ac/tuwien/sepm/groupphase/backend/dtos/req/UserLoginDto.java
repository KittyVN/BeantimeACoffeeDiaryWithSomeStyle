package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UserLoginDto {

    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserLoginDto userLoginDto)) {
            return false;
        }
        return Objects.equals(userLoginDto, userLoginDto.username)
            && Objects.equals(password, userLoginDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "UserLoginDto{"
            + "username='" + username + '\''
            + ", password='" + password + '\''
            + '}';
    }
}
