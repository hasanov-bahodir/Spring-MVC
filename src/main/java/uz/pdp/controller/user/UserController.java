package uz.pdp.controller.user;


//Asadbek Xalimjonov 2/18/22 11:39 PM


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import uz.pdp.model.User;
import uz.pdp.model.UserDetail;
import uz.pdp.service.AuthenticationService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import static uz.pdp.config.Constants.profile_path;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthenticationService authenticationService;

    private static final String UPLOAD_DIRECTORY = profile_path;

    @GetMapping
    public String userPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            User byEmail = authenticationService.findByEmail(user.getEmail());
            try {
                String imgName = "" + byEmail.getUserDetail().getImage_path();
                BufferedImage bImage = ImageIO.read(new File(imgName));//give the path of an image
                ByteArrayOutputStream base = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", base);
                base.flush();
                byte[] imageInByteArray = base.toByteArray();
                base.close();
                String b64 = DatatypeConverter.printBase64Binary(imageInByteArray);
                byEmail.getUserDetail().setImage_path(b64);
            } catch (Exception e) {
            }
            model.addAttribute("user", byEmail);
            return "user/index";
        } else if (user != null) {
            return "redirect:/admin";
        }
        return "redirect:/auth/login";
    }


    @GetMapping("/profiles")
    public String profilePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        model.addAttribute("profileActive", "active");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            User byEmail = authenticationService.findByEmail(user.getEmail());
            session.setAttribute("authUser", user);
            model.addAttribute("user", byEmail);
            return "user/index";
        } else if (user != null && (user.getRole().getId() == 1 || user.getRole().getId()==2)) {
            User byEmail = authenticationService.findByEmail(user.getEmail());
            try {
                String imgName = "" + byEmail.getUserDetail().getImage_path();
                BufferedImage bImage = ImageIO.read(new File(imgName));//give the path of an image
                ByteArrayOutputStream base = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", base);
                base.flush();
                byte[] imageInByteArray = base.toByteArray();
                base.close();
                String b64 = DatatypeConverter.printBase64Binary(imageInByteArray);
                byEmail.getUserDetail().setImage_path(b64);
            } catch (Exception e) {
            }
            session.setAttribute("authUser", user);
            model.addAttribute("user", byEmail);
            return "admin/profile";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/profile/edit/{email}")
    public String editProfilePage(@PathVariable String email, Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        model.addAttribute("profileActive", "active");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            User byEmail = authenticationService.findByEmail(email);
            try {
                String imgName = "" + byEmail.getUserDetail().getImage_path();
                BufferedImage bImage = ImageIO.read(new File(imgName));//give the path of an image
                ByteArrayOutputStream base = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", base);
                base.flush();
                byte[] imageInByteArray = base.toByteArray();
                base.close();
                String b64 = DatatypeConverter.printBase64Binary(imageInByteArray);
                byEmail.getUserDetail().setImage_path(b64);
            } catch (Exception e) {
            }
            session.setAttribute("authUser", user);
            model.addAttribute("user", byEmail);
            return "user/edit-user";
        } else if (user != null && user.getRole().getId() == 1) {
            User byEmail = authenticationService.findByEmail(email);
            try {
                String imgName = "" + byEmail.getUserDetail().getImage_path();
                BufferedImage bImage = ImageIO.read(new File(imgName));//give the path of an image
                ByteArrayOutputStream base = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", base);
                base.flush();
                byte[] imageInByteArray = base.toByteArray();
                base.close();
                String b64 = DatatypeConverter.printBase64Binary(imageInByteArray);
                byEmail.getUserDetail().setImage_path(b64);
            } catch (Exception e) {
            }
            session.setAttribute("authUser", user);
            model.addAttribute("user", byEmail);
            return "admin/profile-form";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/profile")
    public String updateProfile(@RequestParam Integer id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam CommonsMultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            User updateUser = new User();
            updateUser.setId(id);
            try {

                String path = UPLOAD_DIRECTORY;
                if (file.getOriginalFilename().length() > 0) {
                    String filename = UUID.randomUUID() + file.getOriginalFilename();

                    String image_path = path + "/" + filename;

                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + File.separator + filename)));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();
                    updateUser.setUserDetail(new UserDetail(firstName, lastName, image_path));
                    authenticationService.updateUser(updateUser);
                } else {
                    updateUser.setUserDetail(new UserDetail(firstName, lastName));
                    authenticationService.updateUserImg(updateUser);
                }
            } catch (Exception e) {

            }
            return "redirect:/user";
        } else if (user != null && user.getRole().getId() == 1) {
            User updateUser = new User();
            updateUser.setId(id);
            try {

                String path = UPLOAD_DIRECTORY;
                if (file.getOriginalFilename().length() > 0) {
                    String filename = UUID.randomUUID() + file.getOriginalFilename();

                    String image_path = path + "/" + filename;

                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + File.separator + filename)));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();
                    updateUser.setUserDetail(new UserDetail(firstName, lastName, image_path));
                    authenticationService.updateUser(updateUser);
                } else {
                    updateUser.setUserDetail(new UserDetail(firstName, lastName));
                    authenticationService.updateUserImg(updateUser);
                }
            } catch (Exception e) {

            }

            return "redirect:/user/profiles";
        }
        return "redirect:/auth/login";
    }


    @GetMapping("/roadMap")
    public String roadMap() {
        return "roadmap";
    }


}
