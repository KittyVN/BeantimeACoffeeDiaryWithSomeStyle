package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class UserDetailDto {
    private Long id;
    @Email
    @NotNull
    private String email;
    @NotNull
    private UserRole role;

    public UserDetailDto() {
    }

    public UserDetailDto(Long id, String email, UserRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public UserRole getRole() {
        return this.role;
    }

    @Override
    public String toString() {
        return String.format("UserDto{id=%d,email='%s',role='%s'}", id, email, role);
    }
}
