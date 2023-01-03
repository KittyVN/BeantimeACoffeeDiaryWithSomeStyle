package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import at.ac.tuwien.sepm.groupphase.backend.enums.UserRole;

import javax.validation.constraints.NotNull;

public class UserAdminEditDto {
    @NotNull
    private UserRole role;
    @NotNull
    private Boolean active;

    public UserAdminEditDto() { }

    public UserAdminEditDto(UserRole role, Boolean active) {
        this.role = role;
        this.active = active;
    }

    public UserRole getRole() {
        return this.role;
    }

    public Boolean isActive() {
        return this.active;
    }

    @Override
    public String toString() {
        return String.format("UserAdminEditDto{role='%s',active=%s}", role, active);
    }
}
