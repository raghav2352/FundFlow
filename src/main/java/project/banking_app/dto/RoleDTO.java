package project.banking_app.dto;

import lombok.*;
import project.banking_app.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleDTO {
    private Long id;
    private String name;
}
