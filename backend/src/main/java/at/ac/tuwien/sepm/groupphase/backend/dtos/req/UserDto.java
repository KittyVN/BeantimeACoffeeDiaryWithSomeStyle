package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;

import javax.validation.constraints.Email;

public class UserDto {
    private Long id;

    @Email
    private String email;

    private UserRole role;

    public UserDto() {

    }

    public UserDto(Long id, String email, UserRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
