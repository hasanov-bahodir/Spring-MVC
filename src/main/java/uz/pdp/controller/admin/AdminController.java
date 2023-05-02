package uz.pdp.controller.admin;


//Asadbek Xalimjonov 2/18/22 2:13 PM

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import uz.pdp.dtos.UserDto;
import uz.pdp.model.Role;
import uz.pdp.model.User;
import uz.pdp.model.UserDetail;
import uz.pdp.service.AuthenticationService;
import uz.pdp.service.CourseService;
import uz.pdp.service.LessonService;
import uz.pdp.service.ModuleService;
import uz.pdp.validation.UserRegistrationValidator;
import uz.pdp.validation.ValidationResult;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static uz.pdp.config.Constants.profile_path;
import static uz.pdp.validation.ValidationResult.SUCCESS;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    CourseService courseService;
    @Autowired
    LessonService lessonService;
    @Autowired
    ModuleService moduleService;

    private static final String UPLOAD_DIRECTORY = profile_path;

    @GetMapping
    public String adminPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 1) {
            model.addAttribute("usersCount", authenticationService.getAllUser().size());
            model.addAttribute("coursesCount", courseService.getAllCourse().size());
            model.addAttribute("modelsCount", courseService.getAllModule().size());
            model.addAttribute("lesonsCount", lessonService.getAllLesson().size());
            model.addAttribute("adminActive", "active");
            model.addAttribute("user", user);
            return "admin/index";
        } else if (user != null && user.getRole().getId() == 3) {
            return "redirect:/user";
        } else if (user != null && user.getRole().getId() == 2) {
            model.addAttribute("usersCount", authenticationService.getAllByMentorId(user.getId()).size());
            model.addAttribute("coursesCount", courseService.getCourseByMentorId(user.getId()).size());
            model.addAttribute("modelsCount", moduleService.getModuleByMentorId(user.getId()).size());
            model.addAttribute("lesonsCount", lessonService.getLessonsByMentorId(user.getId()).size());
            model.addAttribute("adminActive", "active");
            model.addAttribute("user", user);
            return "admin/index";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/users")
    public String adminUserPage(Model model, HttpSession session, @RequestParam(required = false) String search, @RequestParam(required = false) Integer pageid) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && (user.getRole().getId() == 1 || user.getRole().getId() == 2)) {
            List<UserDto> allUser = null;
            if (pageid != null) {
            } else {
                pageid = 1;
            }
            int total = 3;
            if (pageid == 1) {
            } else {
                pageid = pageid - 1;
                pageid = pageid * total + 1;
            }
            int size = 0;
            if (search != null && search.length() > 0 && user.getRole().getId() == 1) {
                allUser = authenticationService.search(search);
                size = allUser.size();
            } else if (search != null && search.length() > 0 && user.getRole().getId() == 2) {
                allUser = authenticationService.getAllByMentorId(user.getId());
                size = allUser.size();
            } else if (user.getRole().getId() == 1) {
                allUser = authenticationService.getPageUser(pageid, total);
                size = authenticationService.getAllUser().size();
            } else if (user.getRole().getId() == 2) {
                allUser = authenticationService.getAllByMentorId(user.getId());
                size = allUser.size();
            }

            model.addAttribute("adminUserActive", "active");
            model.addAttribute("adminUserMenuActive", "active");
            model.addAttribute("activeCourseUserClose", "open");
            model.addAttribute("id",user.getRole().getId());
            List<UserDto> userList = new ArrayList<>();
            for (UserDto user1 : allUser) {
                if (user1 != null && user1.getImage_path() != null) {
                    user1.setImage_path(b64(user1.getImage_path()));
                }
                userList.add(user1);
            }
            model.addAttribute("size", size);
            model.addAttribute("allUser", userList);
            return "admin/user/index";
        } else if (user != null && user.getRole().getId() == 3) {
            return "redirect:/user";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/users/block/{id}")
    public String blockUser(Model model, HttpSession session, @PathVariable String id) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 1) {
            authenticationService.blockUser(Integer.parseInt(id));
            model.addAttribute("usersCount", authenticationService.getAllUser().size());
            model.addAttribute("coursesCount", courseService.getAllCourse().size());
            model.addAttribute("modelsCount", courseService.getAllModule().size());
            model.addAttribute("lesonsCount", lessonService.getAllLesson().size());
            model.addAttribute("adminActive", "active");
            return "admin/index";
        } else if (user != null && user.getRole().getId() == 3) {
            return "redirect:/user";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/users/unblock/{id}")
    public String unblockUser(Model model, HttpSession session, @PathVariable String id) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 1) {
            authenticationService.unblockUser(Integer.parseInt(id));
            model.addAttribute("usersCount", authenticationService.getAllUser().size());
            model.addAttribute("coursesCount", courseService.getAllCourse().size());
            model.addAttribute("modelsCount", courseService.getAllModule().size());
            model.addAttribute("lesonsCount", lessonService.getAllLesson().size());
            model.addAttribute("adminActive", "active");
            return "admin/index";
        } else if (user != null && user.getRole().getId() == 1) {
            return "redirect:/user";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/users/create")
    public String adminCreateUserPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 1) {
            model.addAttribute("adminCreateActive", "active");
            model.addAttribute("adminUserMenuActive", "active");
            model.addAttribute("activeCourseUserClose", "open");
            return "admin/user/create";
        } else if (user != null && user.getRole().getId() == 3) {
            return "redirect:/user";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/users/create")
    public String saveUser(HttpServletRequest request, Model model, @RequestParam CommonsMultipartFile file, HttpSession session) {

        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            int role_id = Integer.parseInt(request.getParameter("role_id"));
            User updateUser = new User();
            updateUser.setEmail(email);
            updateUser.setPassword(password);
            User byEmail = authenticationService.findByEmail(email);
            ValidationResult apply = UserRegistrationValidator.isEmailValid().and(UserRegistrationValidator.isPasswordValid()).apply(updateUser);

            if (byEmail != null) {
                model.addAttribute("email_invalid", "Email Already Exist");
                return "admin/user/create";
            }
            if (apply.equals(SUCCESS)) {
                updateUser.setRole(new Role(role_id));
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
                        authenticationService.addAccount(updateUser);
                    } else {
                        updateUser.setUserDetail(new UserDetail(firstName, lastName));
                        authenticationService.addAccount(updateUser);
                    }
                } catch (Exception e) {
                }
                authenticationService.registerUserAdmin(updateUser);
                return "redirect:/admin/users";
            }
        }
        return "redirect:/auth/login";
    }


    public String b64(String image) {
        try {
            String imgName = "" + image;
            BufferedImage bImage = ImageIO.read(new File(imgName));//give the path of an image
            ByteArrayOutputStream base = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", base);
            base.flush();
            byte[] imageInByteArray = base.toByteArray();
            base.close();
            String b64 = DatatypeConverter.printBase64Binary(imageInByteArray);
            return b64;
        } catch (Exception e) {
        }
        return image;
    }
}
