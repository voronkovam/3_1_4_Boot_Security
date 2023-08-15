package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User user left join fetch user.roles where user.name =:name")
    User findUserByName(String name);

    @Query("select user from User user left join fetch user.roles where user.email =:email")
    User findUserByEmail(String email);


}
