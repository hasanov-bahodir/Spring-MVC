package uz.pdp.service;
//Asliddin Kenjaev, created: 2/24/2022 12:27 AM

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.dao.CommentDao;
import uz.pdp.dtos.CommentDto;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public void sendComment(Integer user_id, Integer course_id,Integer lesson_id, String comment_body) {
        commentDao.sendComment(user_id, course_id,lesson_id, comment_body);
    }

    public List<CommentDto> getCommentByCourseId(Integer course_id,Integer lesson_id) {
        return commentDao.getCommentByCourseId(course_id,lesson_id);
    }
}
