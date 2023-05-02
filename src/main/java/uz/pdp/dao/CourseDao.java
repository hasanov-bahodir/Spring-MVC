package uz.pdp.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.dtos.CourseDto;
import uz.pdp.dtos.LessonDto;
import uz.pdp.dtos.ModuleDto;
import uz.pdp.dtos.UserDto;
import uz.pdp.model.Course;
import uz.pdp.model.Lesson;
import uz.pdp.model.Module;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

// Bahodir Hasanov 2/18/2022 2:42 PM
@Repository
public class CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public List<Course> getAdminCourses() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        Root<Course> root = cq.from(Course.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    public void saveCourse(Course theCourse) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(theCourse);
    }


    public void deleteCourse(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.get(Course.class, id);
        session.delete(course);
    }

    public void updateCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.update(course);
    }

    public List<Course> getAllCourse() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        Root<Course> root = cq.from(Course.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    public Course findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Course course = session.get(Course.class, id);
        return course;
    }


    public List<Course> getPageCourse(Integer start, int total) {
        String queryStr = "select id,name,isactive,image_path from courses limit " + total + " offset " + (start - 1) + " ;";
        return template.query(queryStr, (rs, row) -> {
            Course e = new Course();
            e.setId(rs.getInt(1));
            e.setName(rs.getString(2));
            e.setIsActive(rs.getBoolean(3));
            e.setImage_path(rs.getString(4));
            // TODO: Asadbek Xalimjonov 2/9/22 courseDto.setAutorDtoList
            return e;
        });

    }

    public List<Course> seaerch(String search) {
        String queryStr = "select id,name,isactive,image_path from courses where courses.name ilike '" + search + "%' ;";
        return template.query(queryStr, (rs, row) -> {
            Course e = new Course();
            e.setId(rs.getInt(1));
            e.setName(rs.getString(2));
            e.setIsActive(rs.getBoolean(3));
            e.setImage_path(rs.getString(4));
            // TODO: Asadbek Xalimjonov 2/9/22 courseDto.setAutorDtoList
            return e;
        });
    }

    public List<Module> getAllModule() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Module> cq = cb.createQuery(Module.class);
        Root<Module> root = cq.from(Module.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    public Module getModule(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Module.class, id);
    }


    public CourseDto getCourseWithAuthors(Integer courseId) {
        String query = "select c.id                         as id,\n" +
                "       c.price                      as price,\n" +
                "       c.name                       as course_name,\n" +
                "       c.description                as description,\n" +
                "       c.image_path                 as image_path,\n" +
                "       coalesce(m.module_count, 0)  as module_count,\n" +
                "       coalesce(lm.lesson_count, 0) as lesson_count,\n" +
                "       coalesce(ct.student, 0)              as student_count,\n" +
                "       json_agg(distinct auth.*)    as authors\n" +
                "from courses c\n" +
                "\n" +
                "         left join (\n" +
                "    select users.id, users.image_path, users.lastname, users.firstname, ca.course_id\n" +
                "    from users\n" +
                "             left join course_author ca on users.id = ca.author_id) auth on c.id = auth.course_id\n" +
                "\n" +
                "         left join (select course_user.course_id, count(course_id) as student\n" +
                "                    from course_user\n" +
                "                    group by course_id) ct on c.id = ct.course_id\n" +
                "\n" +
                "         left join (\n" +
                "    select modules.course_id, coalesce(count(modules.*), 0) as module_count\n" +
                "    from modules\n" +
                "    group by modules.course_id) m on c.id = m.course_id\n" +
                "\n" +
                "         left join (\n" +
                "    select modules.course_id, count(l.*) as lesson_count\n" +
                "    from modules\n" +
                "             join lessons l on modules.id = l.module_id\n" +
                "    group by modules.course_id) lm on c.id = lm.course_id\n" +
                "\n" +
                "where c.id = "+courseId+"\n" +
                "group by c.image_path, c.description, c.name, c.id, m.module_count, lm.lesson_count,ct.student;";
        return template.queryForObject(query, (rs, row) -> {
            CourseDto e = new CourseDto();
            e.setId(rs.getInt("id"));
            e.setPrice(rs.getDouble("price"));
            e.setName(rs.getString("course_name"));
            e.setDescription(rs.getString("description"));
            e.setImage_path(rs.getString("image_path"));
            e.setStudent_count(rs.getInt("student_count"));
            e.setLesson_count(rs.getInt("lesson_count"));
            e.setModule_count(rs.getInt("module_count"));
            Array authors = rs.getArray("authors");
            String s = authors.toString();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<UserDto>>() {
            }.getType();
            e.setAuthors(gson.fromJson(s, type));
            return e;
        });
    }


    public List<ModuleDto> getCourseModule(Integer courseId) {
        String query = "select id,description,name\n" + "from modules\n" + "where course_id = " + courseId + " ;";

        return template.query(query, (rs, row) -> {
            ModuleDto e = new ModuleDto();
            e.setId(rs.getInt("id"));
            e.setDescription(rs.getString("description"));
            e.setName(rs.getString("name"));
            e.setLessonDtos(getLessonByModule(rs.getInt("id")));
            return e;
        });

    }

    public List<LessonDto> getLessonByModule(Integer id) {
        String query = "select * from lessons where module_id=" + id + " ;";
        return template.query(query, (rs, row) -> {
            LessonDto l = new LessonDto();
            l.setId(rs.getInt("id"));
            l.setDescription(rs.getString("description"));
            l.setName(rs.getString("name"));
            l.setVide_path(rs.getString("video_path"));
            return l;
        });
    }

    public void enrollCourse(Integer courseId, Integer userId) {

        if (checkCourse(courseId, userId) < 1) {
            String sql = "insert into course_user(course_id,user_id) values('" + courseId + "','" + userId + "')";
            template.update(sql);
        }

    }

    public Integer checkCourse(Integer courseId, Integer userId) {
        String query = "select count(*)\n" + "from course_user\n" + "where course_id=" + courseId + " and user_id=" + userId + " ;";

        return template.queryForObject(query, (rs, row) -> {
            Integer e = rs.getInt(1);
            return e;
        });
    }

    public List<Course> getUserCourse(Integer id) {
        String query = "select courses.*\n" + "from courses\n" + "join course_user ca on courses.id = ca.course_id\n" + "where ca.user_id=" + id + ";";

        return template.query(query, (rs, row) -> {
            Course e = new Course();
            e.setId(rs.getInt("id"));
            e.setDescription(rs.getString("description"));
            e.setName(rs.getString("name"));
            e.setImage_path(rs.getString("image_path"));
            return e;
        });
    }


    public List<CourseDto> getUserPageCourse() {
        String queryStr = "select c.id                         as id,\n" +
                "       c.price                      as price,\n" +
                "       c.name                       as course_name,\n" +
                "       c.description                as description,\n" +
                "       c.image_path                 as image_path,\n" +
                "       coalesce(m.module_count, 0)  as module_count,\n" +
                "       coalesce(lm.lesson_count, 0) as lesson_count,\n" +
                "       coalesce(count(cu.*), 0)     as student_count\n" + "\n" +
                "from courses c\n" +
                "         left join course_user cu on c.id = cu.course_id\n" + "         left join (\n" + "    select modules.course_id, coalesce(count(modules.*), 0) as module_count\n" + "    from modules\n" + "    group by modules.course_id) m on c.id = m.course_id\n" + "         left join (\n" + "    select modules.course_id, count(l.*) as lesson_count\n" + "    from modules\n" + "             join lessons l on modules.id = l.module_id\n" + "    group by modules.course_id\n" + ") lm on c.id = lm.course_id\n" + "where c.isactive = true\n" + "group by c.image_path, c.description, c.name, c.id, m.module_count, lm.lesson_count";

        return template.query(queryStr, (rs, row) -> {
            CourseDto e = new CourseDto();
            e.setId(rs.getInt("id"));
            e.setPrice(rs.getDouble("price"));
            e.setName(rs.getString("course_name"));
            e.setDescription(rs.getString("description"));
            e.setImage_path(rs.getString("image_path"));
            e.setModule_count(rs.getInt("module_count"));
            e.setLesson_count(rs.getInt("lesson_count"));
            e.setStudent_count(rs.getInt("student_count"));
            return e;
        });

    }

    public Integer isEnrolled(Integer courseId, Integer userId) {
        String query = "select count(*)\n" + "from course_user\n" + "where course_id=" + courseId + " and user_id=" + userId + " ;";

        return template.queryForObject(query, (rs, row) -> {
            Integer e = rs.getInt(1);
            return e;
        });
    }

    public List<Course> getCourseByIdAndSearch(Integer id, String search) {
        String queryStr = "select c.id,c.name,c.isactive,c.image_path from courses c join course_author ca on c.id = ca.course_id" +
                " join users u on ca.author_id = u.id where c.name ilike '" + search + "%' and u.id ="+id+";";
        return template.query(queryStr, (rs, row) -> {
            Course e = new Course();
            e.setId(rs.getInt(1));
            e.setName(rs.getString(2));
            e.setIsActive(rs.getBoolean(3));
            e.setImage_path(rs.getString(4));
            return e;
        });
    }

    public List<Course> getCourseByMentorId(Integer id) {
        String queryStr = "select c.id,c.name,c.isactive,c.image_path from courses c join course_author ca on c.id = ca.course_id" +
                " join users u on ca.author_id = u.id where u.id ="+id+";";
        return template.query(queryStr, (rs, row) -> {
            Course e = new Course();
            e.setId(rs.getInt(1));
            e.setName(rs.getString(2));
            e.setIsActive(rs.getBoolean(3));
            e.setImage_path(rs.getString(4));
            return e;
        });

    }

    public List<Course> getCourseByMentorIdPage(Integer id,Integer pageid, int total) {
        String queryStr = "select c.id,c.name,c.isactive,c.image_path from courses c" +
                " join course_author ca on c.id = ca.course_id join users u on u.id = ca.author_id " +
                "where u.id = '"+id+"' limit " + total + " offset " + (pageid - 1) + " ;";
        return template.query(queryStr, (rs, row) -> {
            Course e = new Course();
            e.setId(rs.getInt(1));
            e.setName(rs.getString(2));
            e.setIsActive(rs.getBoolean(3));
            e.setImage_path(rs.getString(4));
            // TODO: Asadbek Xalimjonov 2/9/22 courseDto.setAutorDtoList
            return e;
        });
    }
}
