package edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming;

import edu.bbte.idde.frim1910.reactivefrim1910.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDto {
    private String username;
    private List<Role> roles;
}
