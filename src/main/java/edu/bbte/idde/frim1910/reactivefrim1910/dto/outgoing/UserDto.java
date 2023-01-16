package edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing;

import edu.bbte.idde.frim1910.reactivefrim1910.model.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDto extends BaseEntityDto {
    private String username;
    private List<Role> roles;
}
