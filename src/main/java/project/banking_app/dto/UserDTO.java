package project.banking_app.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

    private Long id;
    private String name;
    private String password;
    private String email;


}
