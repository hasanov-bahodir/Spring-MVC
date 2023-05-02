package uz.pdp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity(name = "lessons")

//Asadbek Xalimjonov 2/18/22 10:56 AM

public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String video_path;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    public Lesson(String name, String description, String video_path, Module module) {
        this.name = name;
        this.description = description;
        this.video_path = video_path;
        this.module = module;
    }
}
