package uz.pdp.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

//Asadbek Xalimjonov 2/20/22 1:21 PM

public class LessonDto {

    private Integer id;
    private String name;
    private String description;
    private String module;
    private String course;
    private String vide_path;
    private Integer moduleid;
}
