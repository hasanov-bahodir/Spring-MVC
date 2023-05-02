package uz.pdp.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.dtos.CommentDto;
import uz.pdp.dtos.UserDto;

import java.util.List;

//Asliddin Kenjaev, created: 2/24/2022 12:04 AM

@Repository
public class CommentDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    JdbcTemplate template;

    public void sendComment(Integer user_id, Integer course_id, Integer lesson_id, String comment_body) {
        if (course_id != null) {
            String sqlQuery = "insert into comments(name, course_id, user_id) values('" + comment_body + "', " + course_id + ", " + user_id + ");";
            template.update(sqlQuery);
        } else if (lesson_id != null) {
            String sqlQuery = "insert into comments(name, lesson_id, user_id) values('" + comment_body + "', " + lesson_id + ", " + user_id + ");";
            template.update(sqlQuery);
        }
    }


    public List<CommentDto> getCommentByCourseId(Integer courseId, Integer lessonId) {
        String queryStr = "";
        if (lessonId != null) {
            queryStr = "select comments.id,\n" +
                    "       comments.name,\n" +
                    "       u.image_path,\n" +
                    "       u.firstname,\n" +
                    "       u.lastname\n" +
                    "from comments\n" +
                    "join users u on u.id = comments.user_id\n" +
                    "where lesson_id=" + lessonId + "  order by comments.id desc;";
        } else if (courseId != null) {
            queryStr = "select comments.id,\n" +
                    "       comments.name,\n" +
                    "       u.image_path,\n" +
                    "       u.firstname,\n" +
                    "       u.lastname\n" +
                    "from comments\n" +
                    "join users u on u.id = comments.user_id\n" +
                    "where course_id=" + courseId + " order by comments.id desc;";
        }

        return template.query(queryStr, (rs, rowNum) -> {
            CommentDto com = new CommentDto();
            com.setId(rs.getInt("id"));
            com.setBody(rs.getString("name"));
            UserDto userDto = new UserDto();
            userDto.setFirstname(rs.getString("firstname"));
            userDto.setLastname(rs.getString("lastname"));
            userDto.setImage_path(rs.getString("image_path"));
            com.setUserDto(userDto);
            return com;
        });
    }
}
