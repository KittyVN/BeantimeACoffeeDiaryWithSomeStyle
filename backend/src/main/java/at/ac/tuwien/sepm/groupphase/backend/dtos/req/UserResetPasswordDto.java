package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UserResetPasswordDto {

    @NotNull(message = "Email must not be null")
    @Email
    private String email;


    public UserResetPasswordDto() {
    }

    public UserResetPasswordDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserResetPasswordDto that = (UserResetPasswordDto) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "UserResetPasswordDto{"
            + "email='" + email + '\''
            + '}';
    }
}
