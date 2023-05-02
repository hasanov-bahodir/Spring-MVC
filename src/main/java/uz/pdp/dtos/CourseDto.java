package uz.pdp.dtos;

// Bahodir Hasanov 2/18/2022 2:49 PM

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CourseDto {
    private Integer id;
    private Boolean isActive;
    private String name;
    private String description;
    private Boolean isFree;
    private Double price;
    private String image_path;
    private Integer lesson_count;
    private Integer module_count;
    private Integer student_count;
    private List<UserDto> authors = new ArrayList<>();
    private List<CommentDto> commentDtos = new ArrayList<>();

}
