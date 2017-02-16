package com.intesasanpaolo.conco.ispairlines.service;

import com.intesasanpaolo.conco.ispairlines.dao.JdbcUserDao;
import com.intesasanpaolo.conco.ispairlines.dao.UserDAO;
import com.intesasanpaolo.conco.ispairlines.model.User;

import java.util.List;

/**
 *
 */
public class UserService {

    private UserDAO dao = new JdbcUserDao();

    public void addEntry(User user) {
        dao.save(user);
    }

    public List<User> getAllEntries() {
        return dao.list();
    }
}