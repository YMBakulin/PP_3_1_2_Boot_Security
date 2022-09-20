package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = ?1")
    User findByUsername(String username);

    @Query(value = "SELECT u FROM User u JOIN FETCH u.roles")
    List<User> findAll();

    @Query(value = "SELECT u FROM User u JOIN FETCH u.roles WHERE u.id = ?1")
    User getUserById(long id);

}
