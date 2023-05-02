package uz.pdp.dao;


//Asadbek Xalimjonov 2/18/22 6:02 PM


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.bytebuddy.description.method.MethodDescription;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.pdp.dtos.CommentDto;
import uz.pdp.dtos.CourseDto;
import uz.pdp.dtos.UserDto;
import uz.pdp.model.Role;
import uz.pdp.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthDao {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    JdbcTemplate template;

    public String registerUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        user.setRole(getRole());
        user.setIs_blocked(false);
        currentSession.saveOrUpdate(user);
        return "Success";
    }

    public Role getRole() {
        Session currentSession = sessionFactory.getCurrentSession();
        Role role = currentSession.get(Role.class, 3);
        return role;
    }


    public User findByEmail(String email) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from users where email = '" + email + "'");
        User user = (User) query.uniqueResult();
        return user;
    }

    public User findByEmailPassword(String email, String password) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from users where email = '" + email + "' and password='" + password + "'");
        User user = (User) query.uniqueResult();
        return user;
    }

    public void updateUser(User updateUser) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update users set userDetail.firstName =:firstName,userDetail.lastName=:lastName,userDetail.image_path=:image_path where id = :id ");
        query.setString("firstName", updateUser.getUserDetail().getFirstName());
        query.setString("lastName", updateUser.getUserDetail().getLastName());
        query.setString("image_path", updateUser.getUserDetail().getImage_path());
        query.setInteger("id", updateUser.getId());
        query.executeUpdate();


    }

    public void updateUserImg(User updateUser) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("update users set userDetail.firstName =:firstName,userDetail.lastName=:lastName where id = :id ");
        query.setString("firstName", updateUser.getUserDetail().getFirstName());
        query.setString("lastName", updateUser.getUserDetail().getLastName());
        query.setInteger("id", updateUser.getId());
        query.executeUpdate();
    }

    public void addAccount(User updateUser) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(updateUser);
    }


    public void blockUser(int id){
        String sqlQuery = "update users set is_blocked = true where id = "+id;
        template.update(sqlQuery);
    }

    public void unblockUser(int id){
        String sqlQuery = "update users set is_blocked = false where id = "+id;
        template.update(sqlQuery);
    }

    public List<User> getAllUser() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    public List<User> getAllMentor() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("from users where role.id=2");
        return query.getResultList();
    }

    public User findById(Integer s) {
        Integer id = Integer.valueOf(s);
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(User.class, id);
    }

    public void registerUserAdmin(User updateUser) {
        Session currentSession = sessionFactory.getCurrentSession();
        updateUser.setRole(getRoleUser(updateUser.getRole().getId()));
        currentSession.saveOrUpdate(updateUser);
    }

    public Role getRoleUser(Integer id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Role role = currentSession.get(Role.class, id);
        return role;
    }

    public List<UserDto> search(String search) {
        String queryStr =
                "select u.id as id, u.email as email,r.name as role,u.firstname as firstname, u.is_blocked as is_blocked, u.image_path as image_path, u.lastname as lastname  from users u join roles r on u.role_id = r.id  where firstname ilike '%"+search+"%' or lastname ilike '%"+search+"%' ";
        return template.query(queryStr, (rs, row) -> {
            UserDto e = new UserDto();
            e.setId(rs.getInt("id"));
            e.setEmail(rs.getString("email"));
            e.setRole(rs.getString("role"));
            e.setFirstname(rs.getString("firstname"));
            e.setLastname(rs.getString("lastname"));
            e.setImage_path(rs.getString("image_path"));
            e.setIs_blocked(rs.getBoolean("is_blocked"));
            return e;
        });
    }

    public List<UserDto> getPageUser(Integer pageid, int total) {
        String queryStr =
                "select u.id as id, u.email as email,r.name as role,u.firstname as firstname, u.is_blocked as is_blocked, u.image_path as image_path, u.lastname as lastname  from users u join roles r on u.role_id = r.id limit "+total+" offset "+(pageid-1)+";";
        return template.query(queryStr, (rs, row) -> {
            UserDto e = new UserDto();
            e.setId(rs.getInt("id"));
            e.setEmail(rs.getString("email"));
            e.setRole(rs.getString("role"));
            e.setFirstname(rs.getString("firstname"));
            e.setLastname(rs.getString("lastname"));
            e.setImage_path(rs.getString("image_path"));
            e.setIs_blocked(rs.getBoolean("is_blocked"));
            return e;
        });
    }

    public List<UserDto> getALlByMentorId(Integer id) {
        String queryStr = "select * from userwithcourseview";
        return template.query(queryStr, (rs, row) -> {
            UserDto e = new UserDto();
            e.setId(rs.getInt("id"));
            e.setEmail(rs.getString("email"));
            e.setRole(rs.getString("role"));
            e.setFirstname(rs.getString("firstname"));
            e.setLastname(rs.getString("lastname"));
            e.setImage_path(rs.getString("image_path"));
            Array courses = rs.getArray("courses");
            List<CourseDto> courseList = new Gson().fromJson(courses.toString(),
                    new TypeToken<ArrayList<CourseDto>>(){}.getType());
            e.setCourseDto(courseList);
            return e;
        });
    }
}
