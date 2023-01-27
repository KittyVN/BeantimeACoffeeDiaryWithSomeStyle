package at.ac.tuwien.sepm.groupphase.backend.mapper;

import at.ac.tuwien.sepm.groupphase.backend.dtos.req.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class UserMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public UserMapper() {
    }

    public UserDetailDto entityToDto(User user) {
        LOGGER.debug("entityToDto({})", user);
        return new UserDetailDto(user.getId(), user.getEmail(), user.getUsername(), user.getRole(), user.isActive());
    }
}
