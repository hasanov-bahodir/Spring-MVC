package uz.pdp.repo;

import uz.pdp.model.User;

public interface AuthRepo {

    String createAccount(User user);

    User findUser(User user);

    User findByEmail(String email);

    User findById(User user, Integer id);

}
