package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UserLoginDto {

    @NotNull(message = "Email must not be null")
    @Email
    private String email;

    @NotNull(message = "Password must not be null")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserLoginDto userLoginDto)) {
            return false;
        }
        return Objects.equals(email, userLoginDto.email)
            && Objects.equals(password, userLoginDto.password);
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


    public static final class UserLoginDtoBuilder {
        private String email;
        private String password;

        public UserLoginDtoBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserLoginDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserRegisterDto build() {
            UserRegisterDto userRegisterDto = new UserRegisterDto();
            userRegisterDto.setEmail(email);
            userRegisterDto.setPassword(password);
            return userRegisterDto;
        }
    }
}
