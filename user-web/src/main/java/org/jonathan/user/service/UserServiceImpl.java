package org.jonathan.user.service;

import org.jonathan.user.doman.User;
import org.jonathan.user.repository.DatabaseUserRepository;
import org.jonathan.user.repository.UserRepository;
import org.jonathan.user.sql.DBConnectionManager;

public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImpl() {
       // this.userRepository = new DatabaseUserRepository();
    }

    @Override
    public boolean register(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return this.userRepository.getByNameAndPassword(name, password);
    }
}
