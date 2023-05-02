package uz.pdp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Bahodir Hasanov 2/20/2022 7:12 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModuleDto {
    private Integer id;
    private String name;
    private String description;
    private Integer courseId;
    private String courseName;
    private List<LessonDto> lessonDtos = new ArrayList<>();
}
