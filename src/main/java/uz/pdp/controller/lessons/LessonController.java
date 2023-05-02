package uz.pdp.controller.lessons;


//Asadbek Xalimjonov 2/18/22 11:30 AM


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dtos.CommentDto;
import uz.pdp.dtos.LessonDto;
import uz.pdp.model.Course;
import uz.pdp.model.Lesson;
import uz.pdp.model.Module;
import uz.pdp.model.User;
import uz.pdp.service.CommentService;
import uz.pdp.service.CourseService;
import uz.pdp.service.LessonService;
import uz.pdp.service.ModuleService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    LessonService lessonService;
    @Autowired
    CourseService courseService;
    @Autowired
    CommentService commentService;
    @Autowired
    ModuleService moduleService;

    @GetMapping
    public String getAllLesson(Model model, HttpSession session, @RequestParam(required = false) String search, @RequestParam(required = false) Integer pageid) {
        List<LessonDto> lessons = new ArrayList<>();
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && (user.getRole().getId() == 1 || user.getRole().getId() == 2)) {
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
                lessons = lessonService.search(search);
                size = lessons.size();
            } else if (search != null && search.length() > 0 && user.getRole().getId() == 2) {
                lessons = lessonService.getLessonsByMentorIdAndSearch(search, user.getId());
                size = lessons.size();
            } else if (user.getRole().getId() == 2) {
                lessons = lessonService.getLessonBySearchAndMentorIdAndPage(pageid, total, user.getId());
                size = lessonService.getLessonsByMentorId(user.getId()).size();
            } else if (user.getRole().getId() == 1) {
                lessons = lessonService.getPageLessons(pageid, total);
                size = lessonService.getAllLesson().size();
            }

            model.addAttribute("activeLessonClose", "open");
            model.addAttribute("activeLesson", "active");
            model.addAttribute("activeLessonSide", "active");
            model.addAttribute("size", size);
            model.addAttribute("lessons", lessons);
            return "admin/lesson/index";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/create")
    public String createLesson(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            model.addAttribute("activeLessonClose", "open");
            model.addAttribute("activeLessonCreate", "active");
            model.addAttribute("activeLessonSide", "active");
            model.addAttribute("modules", courseService.getAllModule());
            return "admin/lesson/create";
        } else if (user != null && user.getRole().getId() == 2){
            model.addAttribute("activeLessonClose", "open");
            model.addAttribute("activeLessonCreate", "active");
            model.addAttribute("activeLessonSide", "active");
            model.addAttribute("modules",moduleService .getModuleByMentorId(user.getId()));
            return "admin/lesson/create";
        }
            return "redirect:/auth/login";
    }

    @PostMapping("/create")
    public String saveLesson(HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String video_path = request.getParameter("video_path");
            Integer module_id = Integer.valueOf(request.getParameter("module_id"));
            Module module = courseService.getModule(module_id);
            Lesson lesson = new Lesson(name, description, video_path, module);
            lessonService.saveLesson(lesson);
            return "redirect:/lessons";
        }
        return "redirect:/auth/login";
    }


    @GetMapping("/update/{id}")
    public String editLesson(Model model, @PathVariable Integer id, HttpSession session) {

        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            LessonDto lessonDto = lessonService.findById(id);
            model.addAttribute("lesson", lessonDto);
            model.addAttribute("activeLessonClose", "open");
            model.addAttribute("activeLesson", "active");
            model.addAttribute("activeLessonSide", "active");
            model.addAttribute("modules", courseService.getAllModule());
            return "admin/lesson/edit";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/update/{id}")
    public String updateLesson(HttpServletRequest request, @PathVariable String id, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String video_path = request.getParameter("video_path");
            Integer module_id = Integer.valueOf(request.getParameter("module_id"));
            Module module = courseService.getModule(module_id);
            Lesson lesson = new Lesson(name, description, video_path, module);
            lessonService.update(lesson, id);
            return "redirect:/lessons";
        }
        return "redirect:/auth/login";

    }

    @GetMapping("/delete/{id}")
    public String deleteLessin(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            try {

                lessonService.delete(id);
            } catch (Exception e) {
            }
            return "redirect:/lessons";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/course/{courseid}/watch/{lessonId}")
    public String watchLesson(HttpServletRequest request, @PathVariable Integer courseid, @PathVariable Integer lessonId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            LessonDto lessonDto = lessonService.getLessonById(lessonId);
            List<CommentDto> commentByCourseId = commentService.getCommentByCourseId(null, lessonId);
            for (CommentDto commentDto : commentByCourseId) {
                if (commentDto.getUserDto() != null && commentDto.getUserDto().getImage_path() != null)
                    commentDto.getUserDto().setImage_path(b64(commentDto.getUserDto().getImage_path()));
            }
            model.addAttribute("lesson", lessonDto);
            model.addAttribute("courseId", courseid);
            model.addAttribute("lesssonComment", commentByCourseId);
            return "user/lesson-watch";
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
