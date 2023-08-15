package ru.kata.spring.boot_security.demo.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    @PersistenceContext
    private EntityManager entityManager;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    @Lazy
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetails = Optional.ofNullable(userRepository.findUserByEmail(username));
        if (!userDetails.isPresent()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return userDetails.get();
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findUserByName(username);
    }
    @Override
    public List<User> getListUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        if (!user.getPassword().equals(userRepository.findUserByEmail(user.getPassword()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        entityManager.persist(user);
    }

    @Override
    public void removeUserById(long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public void updateUser(User user) {
        if (!user.getPassword().equals(userRepository.findUserByEmail(user.getPassword()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        entityManager.merge(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }
}


