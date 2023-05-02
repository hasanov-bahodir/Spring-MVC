package uz.pdp.controller.auth;


//Asadbek Xalimjonov 2/18/22 11:25 AM


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.model.User;
import uz.pdp.service.AuthenticationService;
import uz.pdp.validation.UserRegistrationValidator;
import uz.pdp.validation.ValidationResult;

import javax.servlet.http.HttpSession;

import static uz.pdp.validation.ValidationResult.SUCCESS;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "auth/login";
    }


    @PostMapping("/login")
    public String loginPost(HttpSession session, @RequestParam String email, @RequestParam String password, Model model) {

        User user = authenticationService.findByEmailPassword(email, password);
        if (user != null && user.getIs_blocked()) {
            model.addAttribute("error", "You are blocked by admin");
        } else if (user != null) {
            session.setAttribute("authUser", user);
            switch (user.getRole().getId()) {
                case 1:
                case 2:
                    return "redirect:/admin";
                case 3:
                    return "redirect:/user";
                default:
                    model.addAttribute("error", "Something Wrong");
                    return "auth/login";

            }
        } else {
            model.addAttribute("error", "Email or Password Incorrect");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        return "auth/register";
    }

    @GetMapping("/registered")
    public String registeredPage(Model model) {
        return "auth/registered";
    }

    @PostMapping("/register")
    public String createAccount(User user, @RequestParam String confirm_password, Model model) {

        if (user.getPassword().equals(confirm_password)) {
            User byEmail = authenticationService.findByEmail(user.getEmail());
            ValidationResult apply = UserRegistrationValidator.isEmailValid().and(UserRegistrationValidator.isPasswordValid()).apply(user);

            if (byEmail != null) {
                model.addAttribute("email_invalid", "Email Already Exist");
                return "auth/register";
            }
            if (apply.equals(SUCCESS)) {
                authenticationService.createAccount(user);
                return "auth/registered";
            } else {
                model.addAttribute("email_invalid", apply.name());
                return "auth/register";
            }
        }
        model.addAttribute("email_invalid", "Email Already Exist");
        return "auth/register";
    }


    @GetMapping("/logout")
    public String logoutPage(HttpSession session) {
        session.invalidate();
        return "auth/logout";
    }

}
