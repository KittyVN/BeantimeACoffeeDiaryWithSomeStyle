package at.ac.tuwien.sepm.groupphase.backend.dtos.req;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserCredentialsDto extends org.springframework.security.core.userdetails.User {

    private Long id;


    public UserCredentialsDto(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
