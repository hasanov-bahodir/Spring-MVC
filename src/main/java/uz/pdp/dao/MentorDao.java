package uz.pdp.dao;


//Asadbek Xalimjonov 2/23/22 5:59 PM


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.dtos.CourseDto;
import uz.pdp.dtos.UserDto;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;

@Repository
public class MentorDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private JdbcTemplate template;


    public UserDto getMentor(Integer mentorId) {

        String qry = "select users.*,json_agg(data.*) as course_mentor\n" +
                "from users\n" +
                "join course_author ca on users.id = ca.author_id\n" +
                "join (select c.id                         as id,\n" +
                "             c.price                      as price,\n" +
                "             c.name                       as name,\n" +
                "             c.description                as description,\n" +
                "             c.image_path                 as image_path,\n" +
                "             coalesce(m.module_count, 0)  as module_count,\n" +
                "             coalesce(lm.lesson_count, 0) as lesson_count,\n" +
                "             coalesce(count(cu.*), 0)     as student_count\n" +
                "\n" +
                "\n" +
                "      from courses c\n" +
                "\n" +
                "               left join course_user cu on c.id = cu.course_id\n" +
                "               left join (\n" +
                "          select modules.course_id, coalesce(count(modules.*), 0) as module_count\n" +
                "          from modules\n" +
                "          group by modules.course_id) m on c.id = m.course_id\n" +
                "               left join (\n" +
                "          select modules.course_id, count(l.*) as lesson_count\n" +
                "          from modules\n" +
                "                   join lessons l on modules.id = l.module_id\n" +
                "          group by modules.course_id\n" +
                "      ) lm on c.id = lm.course_id\n" +
                "      where c.isactive = true\n" +
                "      group by c.image_path, c.description, c.name, c.id, m.module_count, lm.lesson_count) data on ca.course_id=data.id\n" +
                "where users.id=" + mentorId + "\n" +
                "group by users.id, email, password, role_id, firstname, users.image_path, lastname";

        return template.queryForObject(qry, (rs, row) -> {
            UserDto e = new UserDto();
            e.setId(rs.getInt("id"));
            e.setFirstname(rs.getString("firstname"));
            e.setLastname(rs.getString("lastname"));
            e.setEmail(rs.getString("email"));
            e.setImage_path(rs.getString("image_path"));

            Array authors = rs.getArray("course_mentor");
            String s = authors.toString();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<CourseDto>>() {
            }.getType();
            e.setCourseDto(gson.fromJson(s, type));
            return e;
        });
    }
}
