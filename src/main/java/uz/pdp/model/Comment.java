package uz.pdp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

//Asadbek Xalimjonov 2/18/22 11:33 AM

@Entity(name = "comments")
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "lesson_id",columnDefinition = "default null")
    private Lesson lesson;
    @ManyToOne
    @JoinColumn(name = "course_id",columnDefinition = "default null")
    private Course course;


}
