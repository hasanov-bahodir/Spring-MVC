package uz.pdp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.dtos.LessonDto;
import uz.pdp.model.Lesson;

import java.util.List;

@Repository
//Asadbek Xalimjonov 2/20/22 1:20 PM

public class LessonDao {


    @Autowired
    private SessionFactory sessionFactory;


    @Autowired
    JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public List<LessonDto> getAllLesson() {
        String queryStr = "select lessons.id as id,lessons.name as lessonname,m.name as modulename,c.name as coursename\n" + "from lessons\n" + "join modules m on m.id = lessons.module_id\n" + "join courses c on c.id = m.course_id";
        return template.query(queryStr, (rs, row) -> {
            LessonDto e = new LessonDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("lessonname"));
            e.setModule(rs.getString("modulename"));
            e.setCourse(rs.getString("coursename"));
            return e;
        });

    }

    public void saveLesson(Lesson lesson) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(lesson);
    }

    public void updateLesson(Lesson lesson, String id) {
        String sql = "update lessons set name='" + lesson.getName() + "', " + "description='" + lesson.getDescription() + "', video_path='" + lesson.getVideo_path() + "', module_id='" + lesson.getModule().getId() + "' where id=" + id + " ;";
        template.update(sql);
    }

    public LessonDto getLessonById(Integer id) {
        String queryStr = "select lessons.id as id,lessons.video_path as video,lessons.name as lessonname,lessons.description as description,m.name as modulename,m.id as moduleid,c.name as coursename\n" + "from lessons\n" + "join modules m on m.id = lessons.module_id\n" + "join courses c on c.id = m.course_id where lessons.id=" + id + " ;";

        return template.queryForObject(queryStr, (rs, row) -> {
            LessonDto e = new LessonDto();
            e.setId(rs.getInt("id"));
            e.setModuleid(rs.getInt("moduleid"));
            e.setVide_path(rs.getString("video"));
            e.setName(rs.getString("lessonname"));
            e.setDescription(rs.getString("description"));
            e.setModule(rs.getString("modulename"));
            e.setCourse(rs.getString("coursename"));
            return e;
        });
    }

    public void delete(Integer id) {
        String sql = "delete from lessons where lessons.id=" + id + " ;";
        template.update(sql);
    }

    public List<LessonDto> search(String search) {
        String queryStr = "select lessons.id as id,lessons.name as lessonname,m.name as modulename,c.name as coursename from lessons join modules m on m.id = lessons.module_id join courses c on c.id = m.course_id where lessons.name ilike '%" + search + "%'";
        return template.query(queryStr, (rs, row) -> {
            LessonDto e = new LessonDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("lessonname"));
            e.setModule(rs.getString("modulename"));
            e.setCourse(rs.getString("coursename"));
            return e;
        });
    }

    public List<LessonDto> getPageLessons(Integer pageid, int total) {
        String queryStr = "select l.id as id,l.name as lessonname,m.name as modulename,c.name as coursename from lessons l join modules m on m.id = l.module_id join courses c on c.id = m.course_id limit '" + total + "' offset '" + (pageid - 1) + "' ;";
        return template.query(queryStr, (rs, row) -> {
            LessonDto e = new LessonDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("lessonname"));
            e.setModule(rs.getString("modulename"));
            e.setCourse(rs.getString("coursename"));
            return e;
        });
    }

    public List<LessonDto> getLessonsByMentorIdAndSearch(String search, Integer id) {
        String queryStr = "select lessons.id as id,lessons.name as lessonname,m.name as modulename,c.name as coursename from lessons join modules m on m.id = lessons.module_id join courses c on c.id = m.course_id join course_author ca on c.id = ca.course_id where lessons.name ilike '%" + search + "%' and ca.author_id = '"+id+"' ;";
        return template.query(queryStr, (rs, row) -> {
            LessonDto e = new LessonDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("lessonname"));
            e.setModule(rs.getString("modulename"));
            e.setCourse(rs.getString("coursename"));
            return e;
        });
    }

    public List<LessonDto> getLessonsByMentorId(Integer id) {
        String queryStr = "select lessons.id as id,lessons.name as lessonname,m.name as modulename,c.name as coursename from lessons join modules m on m.id = lessons.module_id join courses c on c.id = m.course_id join course_author ca on c.id = ca.course_id where ca.author_id = '"+id+"' ;";
        return template.query(queryStr, (rs, row) -> {
            LessonDto e = new LessonDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("lessonname"));
            e.setModule(rs.getString("modulename"));
            e.setCourse(rs.getString("coursename"));
            return e;
        });
    }

    public List<LessonDto> getLessonBySearchAndMentorIdAndPage(Integer pageid, int total, Integer id) {
        String queryStr = "select l.id as id,l.name as lessonname,m.name as modulename,c.name as coursename from lessons l join modules m on m.id = l.module_id join courses c on c.id = m.course_id join course_author ca on c.id = ca.course_id where ca.author_id = '"+id+"' limit '"+total+"' offset '"+(pageid-1)+"' ;";
        return template.query(queryStr, (rs, row) -> {
            LessonDto e = new LessonDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("lessonname"));
            e.setModule(rs.getString("modulename"));
            e.setCourse(rs.getString("coursename"));
            return e;
        });
    }
}
