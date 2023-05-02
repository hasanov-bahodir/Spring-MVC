package uz.pdp.model;

//Asadbek Xalimjonov 2/17/22 2:33 PM


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String email;
    private String password;

    @Column(name = "is_blocked", columnDefinition = " default false")
    private Boolean is_blocked;


    @Embedded
    private UserDetail userDetail;

    @OneToOne
    private Role role;
}
