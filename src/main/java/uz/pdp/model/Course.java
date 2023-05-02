package uz.pdp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Asadbek Xalimjonov 2/18/22 10:52 AM

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "isActive", columnDefinition = "default false")
    private Boolean isActive;

    private String name;
    private String description;
    private Double price;

    @Column(name = "isFree", columnDefinition = "default false")
    private Boolean isFree;

    private String image_path;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    @JoinTable(name = "course_author", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<User> authors = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "course_user",
            joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userCourse = new ArrayList<>();


    public Course(String name, String description, Boolean isActive) {
        this.isActive = isActive;
        this.name = name;
        this.description = description;
    }

    public Course(Boolean isActive, String name, Double price, String description, Boolean isFree) {
        this.isActive = isActive;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isFree = isFree;
    }
}
