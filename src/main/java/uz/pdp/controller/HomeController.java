package uz.pdp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.dtos.CourseDto;
import uz.pdp.service.CourseService;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class

HomeController {

    @Autowired
    CourseService courseService;


    @GetMapping
    public String homePage(Model model) {

        model.addAttribute("homePage", "active");
        return "index";
    }

    @GetMapping("/coursePage")
    public String getCoursePage(Model model) {
        List<CourseDto> courseDtoList = courseService.getUserCourses();
        List<CourseDto> courseDtoList1 = new ArrayList<>();
        for (CourseDto courseDto : courseDtoList) {
            if (courseDto.getImage_path() != null) {
                courseDto.setImage_path(b64(courseDto.getImage_path()));
            }
            courseDtoList1.add(courseDto);
        }

        model.addAttribute("courses", courseDtoList);
        model.addAttribute("coursePage", "active");
        return "course-list";
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
            return DatatypeConverter.printBase64Binary(imageInByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

}
