package uz.pdp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.dtos.ModuleDto;
import uz.pdp.model.Module;

import java.util.List;

// Bahodir Hasanov 2/20/2022 4:30 PM

@Transactional
@Repository
public class ModuleDao {
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    JdbcTemplate template;

    public void saveModule(Module module) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(module);
    }

    public List<ModuleDto> getAllModules() {
        String queryStr =
                "select m.id as id,m.name as moduleName,m.description as description,c.id as courseId,c.name as courseName from modules m join courses c on m.course_id = c.id";
        return template.query(queryStr, (rs, row) -> {
            ModuleDto e = new ModuleDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("moduleName"));
            e.setDescription(rs.getString("description"));
            e.setCourseId(rs.getInt("courseId"));
            e.setCourseName(rs.getString("courseName"));
            return e;
        });
    }

    public ModuleDto getModuleById(Integer id) {
        String queryStr = "select m.id as id,m.name as module_name,m.description as description,c.name as course_name,c.id as course_id from modules m join courses c on m.course_id = c.id where m.id=" + id + " ;";
        return template.queryForObject(queryStr, (rs, row) -> {
            ModuleDto m = new ModuleDto();
            m.setId(rs.getInt("id"));
            m.setName(rs.getString("module_name"));
            m.setDescription(rs.getString("description"));
            m.setCourseName(rs.getString("course_name"));
            m.setCourseId(rs.getInt("course_id"));
            return m;
        });
    }

    public void updateModule(Module module, String id) {
        String sql = "update modules set name='" + module.getName() + "', " + "description='" + module.getDescription() + "', course_id ='" + module.getCourse().getId() + "' where id=" + id + " ;";
        template.update(sql);
    }

    public void delete(Integer id) {
        String sql = "delete from modules where modules.id=" + id + " ;";
        template.update(sql);

    }

    public List<ModuleDto> getPageModule(Integer pageid, int total) {
        String queryStr =
                "select m.id as id,m.name as moduleName,m.description as description,c.id as courseId,c.name as courseName from modules m join courses c on m.course_id = c.id limit '" + total + "' offset '" + (pageid - 1) + "'";
        return template.query(queryStr, (rs, row) -> {
            ModuleDto e = new ModuleDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("moduleName"));
            e.setDescription(rs.getString("description"));
            e.setCourseId(rs.getInt("courseId"));
            e.setCourseName(rs.getString("courseName"));
            return e;
        });
    }

    public List<ModuleDto> search(String search) {
        String queryStr =
                "select m.id as id,m.name as moduleName,m.description as description,c.id as courseId,c.name as courseName from modules m join courses c on m.course_id = c.id where m.name ilike '%" + search + "%'";
        return template.query(queryStr, (rs, row) -> {
            ModuleDto e = new ModuleDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("moduleName"));
            e.setDescription(rs.getString("description"));
            e.setCourseId(rs.getInt("courseId"));
            e.setCourseName(rs.getString("courseName"));
            return e;
        });
    }

    public List<ModuleDto> searchModuleById(String search, Integer id) {
        String queryStr =
                "select m.id as id,m.name as moduleName,m.description as description,c.id as courseId,c.name as courseName from modules m join courses c on m.course_id = c.id join course_author ca on c.id = ca.course_id where m.name ilike '%"+search+"%' and ca.author_id = '"+id+"';";
        return template.query(queryStr, (rs, row) -> {
            ModuleDto e = new ModuleDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("moduleName"));
            e.setDescription(rs.getString("description"));
            e.setCourseId(rs.getInt("courseId"));
            e.setCourseName(rs.getString("courseName"));
            return e;
        });
    }

    public List<ModuleDto> getPageModuleById(Integer id, Integer pageid, int total) {
        String queryStr =
                "select m.id as id,m.name as moduleName,m.description as description,c.id as courseId,c.name as courseName from modules m join courses c on m.course_id = c.id join course_author ca on c.id = ca.course_id where ca.author_id='"+id+"' limit '"+total+"' offset '"+(pageid-1)+"'";
        return template.query(queryStr, (rs, row) -> {
            ModuleDto e = new ModuleDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("moduleName"));
            e.setDescription(rs.getString("description"));
            e.setCourseId(rs.getInt("courseId"));
            e.setCourseName(rs.getString("courseName"));
            return e;
        });
    }

    public List<ModuleDto> getModuleByMentorId(Integer id) {
        String queryStr =
                "select m.id as id,m.name as moduleName,m.description as description,c.id as courseId,c.name as courseName from modules m join courses c on m.course_id = c.id join course_author ca on c.id = ca.course_id where ca.author_id='"+id+"';";
        return template.query(queryStr, (rs, row) -> {
            ModuleDto e = new ModuleDto();
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("moduleName"));
            e.setDescription(rs.getString("description"));
            e.setCourseId(rs.getInt("courseId"));
            e.setCourseName(rs.getString("courseName"));
            return e;
        });
    }
}
