package com.example.authsystem.repository;

import com.example.authsystem.model.entity.User;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final Map<Long, User> userMap = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(sequence.getAndIncrement());
        }
        userMap.put(user.getId(), user);

        return user;
    }

    public User findById(Long id) {
        return userMap.get(id);
    }

    public User findByUsername(String username) {
        return userMap.values().stream()
            .filter(user -> user.getUsername().equals(username))
            .findFirst()
            .orElse(null);
    }

    public boolean existsByUsername(String username) {
        return userMap.values().stream()
            .anyMatch(user -> user.getUsername().equals(username));
    }
}
