package com.x;

import java.util.List;

import com.google.inject.Inject;

// N-Tier Architecture Implementation -> Data Access Layer
public class UserService {
    private final UserDAO userDAO;

    @Inject
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.listAll();
    }

    public void addUser(User user) {
        userDAO.insertUser(user);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(String id) {
        userDAO.deleteUser(id);
    }
}
