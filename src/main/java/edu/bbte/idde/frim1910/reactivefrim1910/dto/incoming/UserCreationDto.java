package edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming;

import edu.bbte.idde.frim1910.reactivefrim1910.model.Role;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class UserCreationDto implements Serializable {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotNull
    private List<Role> roles = Collections.singletonList(Role.USER);
}
