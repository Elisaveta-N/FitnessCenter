package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Workout;
import org.example.repo.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {

        this.workoutRepository = workoutRepository;
    }

    @Transactional(readOnly = true)
    public List<Workout> findAll()  {

        return workoutRepository.findAll();
    }

    @Transactional
    public boolean create(Workout w) {
        try {
            workoutRepository.save(w);
            log.info("Workout created, name: ", w.getName());
            return true;
        }
        catch (Exception e)  {
            log.error("Failed to save workout: " + e.getMessage());
            return false;
        }
    }
    @Transactional
    public boolean delete(long id) {
        log.info("Delete workout by id = {}", id);
        workoutRepository.deleteById(id);
        return true;
    }

    public Optional<Workout> findByName(String name) {
        try {
            log.info("Find workouts by name = {}", name);
            return workoutRepository.findByName(name);
        }
        catch (Exception e)  {
            log.error("Failed to find workouts by name: " + e.getMessage());
            return null;
        }
    }

    public Optional<Workout> read(long id) {
        try {
            log.info("Read workout by id = {}", id);
            return workoutRepository.findById(id);
        }
        catch (Exception e)  {
            log.error("Failed to read workout by id: " + e.getMessage());
            return null;
        }
    }
    @Transactional
    public boolean update(Workout workout, long id) {
        try {
            log.info("Update workout by id = {}", id);
            workout.setId(id);
            workoutRepository.save(workout);
            return true;
        }
        catch (Exception e)  {
            log.error("Failed to update workout by id: " + e.getMessage());
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Workout> findWorkoutsByName(String name) {
        try {
            log.info("Find workouts by name = {}", name);
            return workoutRepository.findAllByNameContaining(name);
        }
        catch (Exception e)  {
            log.error("Failed to find workouts by name: " + e.getMessage());
            return null;
        }
    }
}
