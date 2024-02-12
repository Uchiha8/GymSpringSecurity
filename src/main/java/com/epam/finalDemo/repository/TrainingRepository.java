package com.epam.finalDemo.repository;

import com.epam.finalDemo.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("select count(t)>0 from Training t where t.trainingName = ?1")
    boolean existsByTrainingName(String name);

}
