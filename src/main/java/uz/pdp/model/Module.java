package uz.pdp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data


@Entity(name = "modules")
//Asadbek Xalimjonov 2/18/22 10:55 AM

public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Module(String name, String description, Course course) {
        this.name = name;
        this.description = description;
        this.course = course;
    }
}
