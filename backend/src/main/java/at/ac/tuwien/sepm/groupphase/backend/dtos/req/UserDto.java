package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;

public class UserDto {
    private Long id;

    private String email;

    private UserRole role;

    public UserDto() {

    }

    public UserDto(Long id, String email, UserRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public String toString() {
        return String.format("UserDto{id=%d,email='%s',role='%s'}", this.id, this.email, this.role);
    }
}
