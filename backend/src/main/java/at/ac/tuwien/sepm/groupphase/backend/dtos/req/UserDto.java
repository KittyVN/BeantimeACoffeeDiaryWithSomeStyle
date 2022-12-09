package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;

public record UserDto(
    Long id,
    String email,
    UserRole role
) {
    @Override
    public String toString() {
        return String.format("UserDto{id=%d,email='%s',role='%s'}", id, email, role);
    }
}
