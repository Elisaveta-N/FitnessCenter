package org.example.repo;


import org.example.model.User;
import org.example.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByName(String name);
    List<Workout> findAllByNameContaining(String name);
}
