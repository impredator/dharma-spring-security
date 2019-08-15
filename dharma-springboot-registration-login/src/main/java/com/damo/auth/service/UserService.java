package com.damo.auth.service;

import com.damo.auth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
