package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import uz.pdp.dao.CourseDao;
import uz.pdp.dtos.CourseDto;
import uz.pdp.dtos.ModuleDto;
import uz.pdp.model.Course;
import uz.pdp.model.Module;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

import static uz.pdp.config.Constants.path_url;

// Bahodir Hasanov 2/18/2022 2:43 PM
@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    public List<Course> getAdminCourses() {
        return courseDao.getAdminCourses();
    }

    public void saveCourse(Course theCourse, CommonsMultipartFile file) {
        String path = path_url;
        String filename = "" + UUID.randomUUID() + file.getOriginalFilename();
        byte[] barr = file.getBytes();
        String imgUrl = path + "/" + filename;
        try {
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(imgUrl));
            bout.write(barr);
            bout.flush();
            bout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        theCourse.setImage_path(imgUrl);

        courseDao.saveCourse(theCourse);
    }

    public void deleteCourse(Integer course) {
        courseDao.deleteCourse(course);
    }

    public void updateCourse(Course theCourse, CommonsMultipartFile file) {
        String path = path_url;
        if (file.getOriginalFilename().length() > 0) {
            String filename = "" + UUID.randomUUID() + file.getOriginalFilename();
            byte[] barr = file.getBytes();
            String imgUrl = path + "/" + filename;
            try {
                BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(imgUrl));
                bout.write(barr);
                bout.flush();
                bout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            theCourse.setImage_path(imgUrl);
        } else {
            String image_path = courseDao.findById(theCourse.getId()).getImage_path();
            theCourse.setImage_path(image_path);
        }

        courseDao.updateCourse(theCourse);
    }

    public List<Course> getAllCourse() {
        return courseDao.getAllCourse();
    }

    public Course findById(Integer id) {
        return courseDao.findById(id);
    }


    public List<Course> getPageCourse(Integer pageid, int total) {
        return courseDao.getPageCourse(pageid, total);
    }

    public List<Course> serarch(String search) {
        return courseDao.seaerch(search);
    }

    public List<Module> getAllModule() {
        return courseDao.getAllModule();
    }

    public Module getModule(Integer id) {
        return courseDao.getModule(id);
    }

    public CourseDto findbyIdAuthors(Integer id) {
        return courseDao.getCourseWithAuthors(id);
    }

    public List<ModuleDto> getCourseModule(Integer id) {
        return courseDao.getCourseModule(id);
    }

    public void enrollCourse(Integer courseId, Integer userId) {
        courseDao.enrollCourse(courseId, userId);
    }

    public List<Course> getUserCourse(Integer id) {
        return courseDao.getUserCourse(id);
    }

    public List<CourseDto> getUserCourses()
    {
        return courseDao.getUserPageCourse();
    }

    public Integer isEnrolled(Integer course,Integer id) {
        return courseDao.isEnrolled(course,id);
    }

    public List<Course> getCourseByIdAndSearch(Integer id, String search) {
        return courseDao.getCourseByIdAndSearch(id,search);
    }

    public List<Course> getCourseByMentorId(Integer id) {
        return courseDao.getCourseByMentorId(id);

    }

    public List<Course> getCourseByMentorIdPage(Integer id,Integer pageid, int total) {
        return courseDao.getCourseByMentorIdPage(id,pageid,total);
    }
}
