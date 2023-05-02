package uz.pdp.dtos;
//Asliddin Kenjaev, created: 2/24/2022 12:18 AM

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    private Integer id;
    private String body;
    private UserDto userDto;
}
