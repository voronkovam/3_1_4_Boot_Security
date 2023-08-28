package ru.kata.spring.boot_security.demo.services;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


@Service
@Transactional
public interface UserService {

    User findUserByEmail(String username);

    List<User> getListUsers();

    boolean saveUser(User user);

    void removeUserById(long id);

    void updateUser(User user, long id);

    User getUserById(long id);

}


