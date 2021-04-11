package org.jonathan.user.service;

import org.jonathan.context.ClassicComponentContext;
import org.jonathan.context.ComponentContext;
import org.jonathan.user.doman.User;
import org.jonathan.user.repository.UserRepository;
import org.jonathan.user.utils.MD5Utils;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class UserServiceImpl implements UserService{

    @Resource(name = "bean/UserRepository")
    private UserRepository userRepository;
    @Resource(name = "bean/Validator")
    private Validator validator;

    @Override
    public boolean register(User user) {
        // 校验结果
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.size() > 0) {
            throw new RuntimeException(violations.iterator().next().getMessage());
        }

        String password = MD5Utils.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return userRepository.deleteById(user.getId());
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user);
    }

    @Override
    public User queryUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return userRepository.getByNameAndPassword(name, MD5Utils.encode(password));
    }
}
