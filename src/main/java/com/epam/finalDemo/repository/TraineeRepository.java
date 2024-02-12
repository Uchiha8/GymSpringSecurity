package com.epam.finalDemo.repository;

import com.epam.finalDemo.domain.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {


    @Query("select t from Trainee t where t.user.username = ?1")
    Optional<Trainee> findByUserUsername(String username);

    boolean existsByUserUsername(String username);

}
