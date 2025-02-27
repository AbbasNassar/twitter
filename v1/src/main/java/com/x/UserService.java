package com.x;

import java.util.List;

import com.google.inject.Inject;

public class UserService {
    private final UserDAO userDAO;

    @Inject
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<String> getUsersUsername() {
        return userDAO.getUsersUsername();
    }

    public String getUserUsername(int id) {
        return userDAO.getUserUsername(id);
    }
    public User getUser(String email){
        return userDAO.getUser(email);
    }
    public List <User> getSearchedUsers(String name){
        return userDAO.getSearchedUsers(name);
    }
    
    public String getUserName(int id){
        return userDAO.getUserName(id);
    }

    public List <String> getAllEmails(){
        return userDAO.listAllEmails();
    }

    public void addUser(User user) {
        userDAO.insertUser(user);
    }

    public int getUserId(String email) {
        return userDAO.getUserId(email);
    }

    public String checkPassword(String email) {
        return userDAO.checkUserPassword(email);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(String id) {
        userDAO.deleteUser(id);
    }
}
