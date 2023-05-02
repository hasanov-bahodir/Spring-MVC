package uz.pdp.service;


//Asadbek Xalimjonov 2/17/22 2:27 PM

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.dao.AuthDao;
import uz.pdp.dtos.UserDto;
import uz.pdp.model.User;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class AuthenticationService {


    @Autowired
    private AuthDao authDao;

    public String createAccount(User user) {
        return authDao.registerUser(user);
    }

    public User findUser(User user) {
        return null;
    }

    public User findByEmail(String email) {
        return authDao.findByEmail(email);
    }

    public User findById(String id) {
        return authDao.findById(Integer.valueOf(id));
    }

    public User findByEmailPassword(String email, String password) {
        return authDao.findByEmailPassword(email, password);
    }

    public void updateUser(User updateUser) {
        authDao.updateUser(updateUser);
    }

    public void updateUserImg(User updateUser) {
        authDao.updateUserImg(updateUser);
    }

    public void addAccount(User updateUser) {
        authDao.addAccount(updateUser);
    }

    public List<User> getAllUser() {
        return authDao.getAllUser();
    }

    public void blockUser(int id){
        authDao.blockUser(id);
    }

    public void unblockUser(int id){
        authDao.unblockUser(id);
    }

    public List<User> getAllMentor() {
        return authDao.getAllMentor();
    }

    public User findByIdUser(String s) {
        return authDao.findById(Integer.valueOf(s));
    }

    public void registerUserAdmin(User updateUser) {
        authDao.registerUserAdmin(updateUser);
    }

    public List<UserDto> search(String search) {
        return authDao.search(search);
    }

    public List<UserDto> getPageUser(Integer pageid, int total) {
        return authDao.getPageUser(pageid, total);
    }

    public List<UserDto> getAllByMentorId(Integer id) {
        return authDao.getALlByMentorId(id);
    }
}
