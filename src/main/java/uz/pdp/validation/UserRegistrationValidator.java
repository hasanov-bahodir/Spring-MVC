package uz.pdp.validation;


//Asadbek Xalimjonov 2/4/22 10:12 PM

import uz.pdp.model.User;

import java.util.function.Function;
import java.util.regex.Pattern;

import static uz.pdp.validation.ValidationResult.*;

public interface UserRegistrationValidator extends Function<User, ValidationResult> {

    static UserRegistrationValidator isEmailValid() {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@" + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";

        return user -> Pattern.compile(regexPattern).matcher(user.getEmail()).matches() ? SUCCESS : EMAIL_NOT_VALID;
    }


    static UserRegistrationValidator isPasswordValid() {
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        return user -> p.matcher(user.getPassword()).matches() ? SUCCESS : PASSWORD_NOT_VALID;

    }

    default UserRegistrationValidator and(UserRegistrationValidator other) {
        return user -> {
            ValidationResult result = this.apply(user);
            return result.equals(SUCCESS) ? other.apply(user) : result;
        };
    }


}
