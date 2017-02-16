package com.intesasanpaolo.conco.ispairlines.dao;

import java.util.List;
import com.intesasanpaolo.conco.ispairlines.model.User;

public interface UserDAO {

    void save(User user);

    List<User> list();
}