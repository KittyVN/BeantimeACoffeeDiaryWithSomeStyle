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
    private String username;
    @NotNull
    private UserRole role;
    @NotNull
    private Boolean active;

    public UserDetailDto() {
    }

    public UserDetailDto(Long id, String email, String username, UserRole role, Boolean active) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.active = active;
        this.username = username;
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

    public Boolean isActive() {
        return this.active;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return String.format("UserDetailDto{id=%d,username='%s',email='%s',role='%s',active=%s}",
            id, username, email, role, active);
    }
}
