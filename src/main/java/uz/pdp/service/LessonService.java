package uz.pdp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.dao.LessonDao;
import uz.pdp.dtos.LessonDto;
import uz.pdp.model.Lesson;

import java.util.List;

//Asadbek Xalimjonov 2/20/22 1:21 PM
@Service
@Transactional
public class LessonService {


    @Autowired
    LessonDao lessonDao;

    public List<LessonDto> getAllLesson() {
        return lessonDao.getAllLesson();
    }


    public void saveLesson(Lesson lesson) {
        lessonDao.saveLesson(lesson);
    }

    public void update(Lesson lesson, String id) {

        lessonDao.updateLesson(lesson, id);
    }

    public LessonDto findById(Integer id) {
        return lessonDao.getLessonById(id);
    }

    public void delete(Integer id) {
        lessonDao.delete(id);
    }

    public List<LessonDto> search(String search) {
        return lessonDao.search(search);
    }

    public List<LessonDto> getPageLessons(Integer pageid, int total) {
        return lessonDao.getPageLessons(pageid, total);
    }

    public LessonDto getLessonById(Integer lessonId) {
        return lessonDao.getLessonById(lessonId);
    }

    public List<LessonDto> getLessonsByMentorIdAndSearch(String search, Integer id) {
        return lessonDao.getLessonsByMentorIdAndSearch(search,id);
    }

    public List<LessonDto> getLessonsByMentorId(Integer id) {
        return lessonDao.getLessonsByMentorId(id);
    }

    public List<LessonDto> getLessonBySearchAndMentorIdAndPage(Integer pageid, int total, Integer id) {
        return lessonDao.getLessonBySearchAndMentorIdAndPage(pageid,total,id);
    }
}
