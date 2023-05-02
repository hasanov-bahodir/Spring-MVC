package uz.pdp.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

//Asadbek Xalimjonov 2/20/22 8:55 PM

public class UserDto {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String image_path;
    private String role;
    private Boolean is_blocked;
    private String course_id;
    private Integer student_count;
    private List<CourseDto> courseDto = new ArrayList<>();
}
