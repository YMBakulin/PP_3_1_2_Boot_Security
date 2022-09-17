package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserDao {

    void saveUser(User user);

    void updateUser(User user);

    User getUserById(long id);

    void removeUserById(long id);

    List<User> getAllUsers();

    User loadUserByUsername(String name);

}
