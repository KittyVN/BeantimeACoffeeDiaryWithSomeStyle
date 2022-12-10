package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;

public record UserDto(
    Long id,
    String email,
    UserRole role,
    Boolean isActive
) {
    @Override
    public String toString() {
        return String.format("UserDto{id=%d,email='%s',role='%s',isActive='%s'}", id, email, role, isActive);
    }
}
