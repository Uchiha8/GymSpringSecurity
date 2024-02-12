package com.epam.finalDemo.repository;

import com.epam.finalDemo.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {


    @Query("select t from Trainer t where t.user.username = ?1")
    Optional<Trainer> findByUserUsername(String username);

    boolean existsByUserUsername(String username);


}
