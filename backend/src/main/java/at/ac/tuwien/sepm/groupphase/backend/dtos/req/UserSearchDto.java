package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;

public class UserSearchDto {
    private Long id;
    private String email;
    private UserRole role;

    public UserSearchDto() { }

    public UserSearchDto(Long id, String email, UserRole role) {
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
