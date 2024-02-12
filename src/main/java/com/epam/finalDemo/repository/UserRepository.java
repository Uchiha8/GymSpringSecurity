package com.epam.finalDemo.repository;

import com.epam.finalDemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("update User u set u.password = ?3 where (u.username = ?1 and u.password = ?2)")
    void updateUserPasswordByUsername(String username, String oldPassword, String newPassword);
}
