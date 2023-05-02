package uz.pdp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.dao.ModuleDao;
import uz.pdp.dtos.ModuleDto;
import uz.pdp.model.Module;

import java.util.Collection;
import java.util.List;

// Bahodir Hasanov 2/20/2022 5:11 PM
@Service
public class ModuleService {
    @Autowired
    ModuleDao moduleDao;

    public void saveModule(Module module) {
        moduleDao.saveModule(module);
    }

    public List<ModuleDto> getAllModule() {
        return moduleDao.getAllModules();
    }


    public ModuleDto getModuleById(Integer id) {
        return moduleDao.getModuleById(id);
    }

    public void update(Module module, String id) {
        moduleDao.updateModule(module, id);
    }

    public void delete(Integer id) {
        moduleDao.delete(id);
    }

    public List<ModuleDto> getPageModule(Integer pageid, int total) {
        return moduleDao.getPageModule(pageid, total);
    }

    public List<ModuleDto> search(String search) {
        return moduleDao.search(search);
    }

    public List<ModuleDto> searchModuleById(String search, Integer id) {
        return moduleDao.searchModuleById(search,id);
    }

    public List<ModuleDto> getPageModuleById(Integer id, Integer pageid, int total) {
        return moduleDao.getPageModuleById(id,pageid,total);
    }

    public List<ModuleDto> getModuleByMentorId(Integer id) {
        return moduleDao.getModuleByMentorId(id);
    }
}
