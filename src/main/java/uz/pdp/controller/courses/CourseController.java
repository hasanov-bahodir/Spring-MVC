package uz.pdp.controller.courses;
//Asadbek Xalimjonov 2/18/22 11:27 AM

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import uz.pdp.dtos.*;
import uz.pdp.model.Course;
import uz.pdp.model.User;
import uz.pdp.service.AuthenticationService;
import uz.pdp.service.CommentService;
import uz.pdp.service.CourseService;
import uz.pdp.service.LessonService;

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
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    LessonService lessonService;
    @Autowired
    CommentService commentService;

    @GetMapping()
    private String getAllCourses(Model model, @RequestParam(required = false) String search, @RequestParam(required = false) Integer pageid, HttpSession session) {

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
            List<Course> allCourse = new ArrayList<>();
            if (search != null && search.length() > 0) {
                allCourse = courseService.serarch(search);
                size = allCourse.size();
            } else if (search != null && search.length() > 0 && user.getRole().getId() == 2) {
                allCourse = courseService.getCourseByIdAndSearch(user.getId(), search);
            } else if (user.getRole().getId() == 1) {
                allCourse = courseService.getPageCourse(pageid, total);
                size = courseService.getAdminCourses().size();
            } else if (user.getRole().getId() == 2) {
                allCourse = courseService.getCourseByMentorIdPage(user.getId(), pageid, total);
                size = courseService.getCourseByMentorId(user.getId()).size();

            }

//            int size = courseService.getAdminCourses().size();
            List<Course> courseDtoList1 = new ArrayList<>();
            for (Course courseDto : allCourse) {
                if (courseDto.getImage_path() != null) {
                    courseDto.setImage_path(b64(courseDto.getImage_path()));
                }
                courseDtoList1.add(courseDto);
            }

            model.addAttribute("courses", courseDtoList1);
            model.addAttribute("size", size);
            model.addAttribute("activeCourse", "active");
            model.addAttribute("activeCourseMenu", "active");
            model.addAttribute("activeCourseClose", "open");
            return "admin/course/index";
        }
        return "redirect:/auth/login";

    }

    @GetMapping("/create")
    private String createPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            model.addAttribute("activeCourseCreate", "active");
            model.addAttribute("activeCourseMenu", "active");
            model.addAttribute("activeCourseClose", "open");
            List<User> allMentor = authenticationService.getAllMentor();
            model.addAttribute("authors", allMentor);
            return "admin/course/create";
        }
        return "redirect:/auth/login";

    }


    @PostMapping("/create")
    private String saveCourse(HttpServletRequest req, @RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && (user.getRole().getId() == 1 || user.getRole().getId() == 2)) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String active = req.getParameter("active");
            String free = req.getParameter("free");
            Double price = Double.parseDouble(req.getParameter("price"));

            String[] authorsId = req.getParameterValues("authors");
            Boolean isActive = false;
            if (active != null && active.equals("on")) isActive = true;
            Boolean isFree = false;
            if (free != null && free.equals("on")) isFree = true;

            List<User> userList = new ArrayList<>();
            for (int i = 0; i < authorsId.length; i++) {
                User user2 = authenticationService.findByIdUser(authorsId[i]);
                userList.add(user2);
            }

            Course course = new Course(isActive, name, price, description, isFree);
            course.setAuthors(userList);
            courseService.saveCourse(course, file);
            return "redirect:/courses";
        }
        return "redirect:/auth/login";
    }


    @GetMapping("/update/{id}")
    public String editCourse(@PathVariable Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            Course course = courseService.findById(id);
            course.setImage_path(b64(course.getImage_path()));
            List<User> allMentor = authenticationService.getAllMentor();
            model.addAttribute("authors", allMentor);
            model.addAttribute("course", course);
            model.addAttribute("activeCourse", "active");
            model.addAttribute("activeCourseMenu", "active");
            model.addAttribute("activeCourseClose", "open");
            return "admin/course/edit";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/update/{id}")
    private String updateCourse(HttpServletRequest req, @PathVariable Integer id, @RequestParam("file") CommonsMultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            String active = req.getParameter("active");
            String free = req.getParameter("free");
            Double price = Double.parseDouble(req.getParameter("price"));

            String[] authorsId = req.getParameterValues("authors");
            Boolean isActive = false;
            if (active != null && active.equals("on")) isActive = true;
            Boolean isFree = false;
            if (free != null && free.equals("on")) isFree = true;

            List<User> userList = new ArrayList<>();
            for (int i = 0; i < authorsId.length; i++) {
                User user5 = authenticationService.findByIdUser(authorsId[i]);
                userList.add(user5);
            }

            Course course = new Course(isActive, name, price, description, isFree);
            course.setAuthors(userList);
            course.setId(id);
            courseService.updateCourse(course, file);
            return "redirect:/courses";
        }
        return "redirect:/auth/login";
    }


    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            try {
                courseService.deleteCourse(id);
            } catch (Exception ignored) {
            }
            return "redirect:/courses";
        }
        return "redirect:/auth/login";
    }


    @GetMapping("/detail/{id}")
    public String showCourseUserPage(@PathVariable Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        Integer isEnrolled = 0;
        if (user != null && user.getRole().getId() == 3) {
            isEnrolled = courseService.isEnrolled(id, user.getId());
        }
        CourseDto course = courseService.findbyIdAuthors(id);
        course.setImage_path(b64(course.getImage_path()));
        List<ModuleDto> courseModule = courseService.getCourseModule(id);

        for (UserDto author : course.getAuthors()) {
            if (author != null && author.getImage_path() != null) author.setImage_path(b64(author.getImage_path()));
        }

        List<CommentDto> commentByCourseId = commentService.getCommentByCourseId(id, null);
        for (CommentDto commentDto : commentByCourseId) {
            if (commentDto.getUserDto() != null && commentDto.getUserDto().getImage_path() != null)
                commentDto.getUserDto().setImage_path(b64(commentDto.getUserDto().getImage_path()));
        }
        model.addAttribute("course", course);
        model.addAttribute("isEnrolled", isEnrolled);
        model.addAttribute("modules", courseModule);
        model.addAttribute("courseComments", commentByCourseId);

        return "course-detail";
    }

    @PostMapping("/enroll/{id}")
    public String enrollCourse(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            courseService.enrollCourse(id, user.getId());
            return "redirect:/courses/mycourses";
        }

        return "redirect:/auth/login";
    }

    @GetMapping("/mycourses")
    public String myCourses(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3 && !user.getIs_blocked()) {
            List<Course> courseDtoList = courseService.getUserCourse(user.getId());
            List<Course> courseDtoList1 = new ArrayList<>();
            for (Course courseDto : courseDtoList) {
                courseDto.setImage_path(b64(courseDto.getImage_path()));
            }
            model.addAttribute("myCourse", "active");
            model.addAttribute("courses", courseDtoList);
            return "user/mycourse";
        }
        return "redirect:/auth/login";
    }


    @GetMapping("/continuestudy/{courseId}")
    public String continueStudy(@PathVariable Integer courseId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            CourseDto course = courseService.findbyIdAuthors(courseId);
            course.setImage_path(b64(course.getImage_path()));
            List<ModuleDto> courseModule = courseService.getCourseModule(courseId);
            model.addAttribute("course", course);
            model.addAttribute("modules", courseModule);
            return "user/module-study";
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

    @GetMapping("/view/{id}")
    public String courseUsers(@RequestParam Integer CourseId, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null) {

        }
        return "redirect:/auth/login";
    }

}
