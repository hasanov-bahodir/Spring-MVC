package uz.pdp.controller.comment;


//Asadbek Xalimjonov 2/21/22 9:34 AM


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.model.User;
import uz.pdp.service.CommentService;
import uz.pdp.service.CourseService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    CourseService courseService;


    @PostMapping("/send/{courseId}")
    public String sendComment(HttpServletRequest request, HttpSession session, @PathVariable Integer courseId, @RequestParam String message) {
        User user = (User) session.getAttribute("authUser");

        if (user != null && !user.getIs_blocked()) {
            commentService.sendComment(user.getId(), courseId, null, message);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/send/lesson/{lessonId}")
    public String sendCommentLesson(HttpServletRequest request, HttpSession session, @PathVariable Integer lessonId, @RequestParam String message) {
        User user = (User) session.getAttribute("authUser");

        if (user != null && !user.getIs_blocked()) {
            commentService.sendComment(user.getId(), null, lessonId, message);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
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
