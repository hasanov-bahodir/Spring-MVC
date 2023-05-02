package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.model.User;
import uz.pdp.service.AuthenticationService;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Properties;


//Asadbek Xalimjonov 2/21/22 2:24 PM

@Controller
@RequestMapping("/emails")
public class EmailController {


    @Autowired
    AuthenticationService authenticationService;


    @GetMapping("/{userId}")
    public String sendEmailPage(Model model, @PathVariable String userId) {
        User byId = authenticationService.findById(userId);
        model.addAttribute("user", byId);
        model.addAttribute("adminUserActive", "active");
        model.addAttribute("adminUserMenuActive", "active");
        model.addAttribute("activeCourseUserClose", "open");
        return "admin/user/email";
    }

    @PostMapping("/{userId}")
    public String sendEmail(@RequestParam String subject, @RequestParam String body, HttpSession session1, @PathVariable Integer userId) {

        User user = (User) session1.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 1) {

            String user_id = "" + userId;
            User byId = authenticationService.findById(user_id);
            final String username = "emailsendermailer@gmail.com";
            final String password = "AB5447316";


            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            session.getProperties().put("mail.smtp.starttls.enable", true);
            session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("no-reply@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(byId.getEmail()));
                message.setSubject(subject);
                message.setText(body + "\nEmail " + byId.getEmail() + " ; Password : " + byId.getPassword());

                Transport.send(message);
                System.out.println("Done");

            } catch (Exception e) {
            }
            return "redirect:/admin/users";
        }
        return "redirect:/auth/login";
    }
}
