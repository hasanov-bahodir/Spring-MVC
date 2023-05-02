package uz.pdp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.dao.MentorDao;
import uz.pdp.dtos.UserDto;

//Asadbek Xalimjonov 2/23/22 5:58 PM
@Service
@Transactional
public class MentorService {

    @Autowired
    MentorDao mentorDao;

    public UserDto getMentorData(Integer mentorId)
    {
        return mentorDao.getMentor(mentorId);
    }

}
