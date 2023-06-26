package org.example.controller;

import org.example.model.Workout;
import org.example.service.WorkoutService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workouts")
public class WorkoutController {

    private static WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }


    @GetMapping
    @PreAuthorize("hasAuthority('admins:read')")
    public ResponseEntity<List<Workout>> list()
    {
        var workouts = workoutService.findAll();
        return workouts != null
                ? new ResponseEntity<>(workouts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admins:write')")
    private ResponseEntity<Boolean> addWorkout(@RequestBody Workout workout)
    {
        return workoutService.create(workout) == true
                ? new ResponseEntity<>(true, HttpStatus.CREATED)
                : new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('admins:read')")
    private ResponseEntity<Workout> getOneWorkout(@PathVariable Long id) {
        var workout =  workoutService.read(id);
        return workout != null && !workout.isEmpty()
                ? new ResponseEntity<>(workout.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admins:write')")
    public ResponseEntity<?> update(@PathVariable(name="id") long id, @RequestBody Workout workout) {
        var workoutFromDbOpt = workoutService.read(id);
        if(workoutFromDbOpt.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var workoutFromDb = workoutFromDbOpt.get();
        BeanUtils.copyProperties(workout, workoutFromDb, "id");
        return workoutService.update(workout, workoutFromDb.getId()) == true
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admins:write')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return workoutService.delete(id) == true
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("name/{name}")
    @PreAuthorize("hasAuthority('admins:read')")
    public ResponseEntity<List<Workout>> listWorkoutsByName(@PathVariable(name="name") String name)
    {
        var workouts = workoutService.findWorkoutsByName(name);
        return workouts != null
                ? new ResponseEntity<>(workouts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
