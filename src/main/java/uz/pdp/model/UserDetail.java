package uz.pdp.model;

//Asadbek Xalimjonov 2/18/22 11:37 AM

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class UserDetail {

    private String firstName;
    private String lastName;
    private String image_path;

    public UserDetail(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
