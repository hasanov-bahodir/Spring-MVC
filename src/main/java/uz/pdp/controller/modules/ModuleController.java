package uz.pdp.controller.modules;

//Asadbek Xalimjonov 2/18/22 11:31 AM

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.dtos.ModuleDto;
import uz.pdp.model.Course;
import uz.pdp.model.Module;
import uz.pdp.model.User;
import uz.pdp.service.CourseService;
import uz.pdp.service.ModuleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/modules")
public class ModuleController {


    @Autowired
    ModuleService moduleService;
    @Autowired
    CourseService courseService;

    @GetMapping
    public String modulePage(Model model, HttpSession session, @RequestParam(required = false) String search, @RequestParam(required = false) Integer pageid) {
        User user = (User) session.getAttribute("authUser");
        List<ModuleDto> modules = null;
        if (user != null && user.getRole().getId() == 3) {
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
                modules = moduleService.search(search);
                size = modules.size();
            } else if (search != null && search.length() > 0 && user.getRole().getId() == 2) {
                modules = moduleService.searchModuleById(search, user.getId());
                size = modules.size();
            } else if (user.getRole().getId() == 1) {
                modules = moduleService.getPageModule(pageid, total);
                size = moduleService.getAllModule().size();
            } else if (user.getRole().getId() == 2) {
                modules = moduleService.getPageModuleById(user.getId(), pageid, total);
                size = moduleService.getModuleByMentorId(user.getId()).size();
            }

            model.addAttribute("activeModule", "active");
            model.addAttribute("activeModuleMenu", "active");
            model.addAttribute("activeModuleClose", "open");
            model.addAttribute("size", size);
            model.addAttribute("modules", modules);
            return "admin/module/index";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/create")
    public String createModulePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            model.addAttribute("activeModuleCreate", "active");
            model.addAttribute("activeModuleMenu", "active");
            model.addAttribute("activeModuleClose", "open");
            List<Course> allCourses = courseService.getAllCourse();
            model.addAttribute("courses", allCourses);
            return "admin/module/create";
        } else if(user != null && user.getRole().getId()==2){
            model.addAttribute("activeModuleCreate", "active");
            model.addAttribute("activeModuleMenu", "active");
            model.addAttribute("activeModuleClose", "open");
            List<Course> allCourses = courseService.getCourseByMentorId(user.getId());
            model.addAttribute("courses", allCourses);
            return "admin/module/create";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/create")
    private String saveModule(HttpServletRequest req, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && (user.getRole().getId() == 1 || user.getRole().getId()==2)) {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            Integer courseId = Integer.valueOf(req.getParameter("courses"));
            Course course = courseService.findById(courseId);
            Module module = new Module(name, description, course);
            moduleService.saveModule(module);
            return "redirect:/modules";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/update/{id}")
    public String editModule(Model model, @PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            ModuleDto moduleDto = moduleService.getModuleById(id);
            model.addAttribute("module", moduleDto);
            model.addAttribute("activeModule", "active");
            model.addAttribute("activeModuleMenu", "active");
            model.addAttribute("activeModuleClose", "open");
            List<Course> allCourses = courseService.getAllCourse();
            model.addAttribute("courses", allCourses);
            return "admin/module/edit";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("/update/{id}")
    public String updateModule(HttpServletRequest request, @PathVariable String id, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Integer course_id = Integer.valueOf(request.getParameter("course_id"));
            Course course = courseService.findById(course_id);
            Module module = new Module(name, description, course);
            moduleService.update(module, id);
            return "redirect:/modules";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("/delete/{id}")
    public String deleteLesson(@PathVariable Integer id, HttpSession session) {
        User user = (User) session.getAttribute("authUser");
        if (user != null && user.getRole().getId() == 3) {
            session.setAttribute("authUser", user);
            return "user/index";
        } else if (user != null && user.getRole().getId() == 1) {
            try {
                moduleService.delete(id);
            } catch (Exception e) {
            }
            return "redirect:/modules";
        }
        return "redirect:/auth/login";
    }

}
