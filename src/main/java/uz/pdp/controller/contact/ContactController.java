package uz.pdp.controller.contact;

//Asadbek Xalimjonov 2/23/22 12:14 AM


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @GetMapping
    public String getContactPage(Model model)
    {
        model.addAttribute("contactPage","active");
        return "contact";
    }
}
