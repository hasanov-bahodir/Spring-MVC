package uz.pdp.controller.mentor;


//Asadbek Xalimjonov 2/23/22 5:57 PM


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.dtos.CourseDto;
import uz.pdp.dtos.UserDto;
import uz.pdp.service.MentorService;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

@Controller
@RequestMapping("/mentor")
public class MentorController {

    @Autowired
    MentorService mentorService;

    @GetMapping("/{mentorId}")
    public String getMentorPage(@PathVariable Integer mentorId, Model model) {

        try {
            UserDto mentorData = mentorService.getMentorData(mentorId);
            mentorData.setImage_path(b64(mentorData.getImage_path()));
            for (CourseDto courseDto : mentorData.getCourseDto()) {
                courseDto.setImage_path(b64(courseDto.getImage_path()));
            }
            model.addAttribute("mentor", mentorData);
            return "teacher-detail";
        } catch (Exception e) {
        }
        return "redirect:/";
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
