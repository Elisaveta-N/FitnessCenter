package org.example.controller;

import org.example.model.Scheduler;
import org.example.model.User;
import org.example.model.Workout;
import org.example.service.SchedulerService;
import org.example.service.UserService;
import org.example.service.WorkoutService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Controller
public class AdminSchedulerController {
    private final SchedulerService schedulerService;
    private final WorkoutService workoutService;
    private final UserService userService;

    private List<String> labels;

    public AdminSchedulerController(SchedulerService schedulerService, WorkoutService workoutService, UserService userService) {
        this.schedulerService = schedulerService;
        this.workoutService = workoutService;
        this.userService = userService;
        labels = List.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница");
    }

    private List<Scheduler> sortSchedulers(List<Scheduler> schedulers)
    {
        Collections.sort(schedulers, (o1, o2) -> {
            var d1 = labels.indexOf(o1.getDay());
            var d2 = labels.indexOf(o2.getDay());

            if(d1 != d2) return d1 - d2;

            LocalTime t1 = LocalTime.parse( o1.getTime() ) ;
            LocalTime t2 = LocalTime.parse( o2.getTime() ) ;

            return t1.compareTo(t2);
        });

        return schedulers;
    }

    @GetMapping("admin_scheduler")
    @PreAuthorize("hasAuthority('admins:read')")
    public String showAdminEditSchedulers(Model model) {
        var schedulers = schedulerService.readAll();
        model.addAttribute("schedulers", sortSchedulers(schedulers));

        var workouts = workoutService.findAll();
        model.addAttribute("workouts", workouts);

        Workout W = new Workout();
        model.addAttribute("W", W);
        Scheduler S = new Scheduler();
        model.addAttribute("S", S);
        return "admin_schedulers";
    }

    @PostMapping("admin_scheduler")
    @PreAuthorize("hasAuthority('admins:write')")
    public String editAdminEditSchedulers(Workout W, Scheduler S, Model model) {
        if(!W.getName().isEmpty() && !S.getDay().isEmpty() && !S.getTime().isEmpty())
        {
            Workout workout = workoutService.findByName(W.getName()).get();

            var schedulers = schedulerService.readAll();
            Scheduler finalS = S;
            var osh = schedulers.stream().filter(scheduler -> (scheduler.getDay()
                    .equals(finalS.getDay()) && scheduler.getTime().equals(finalS.getTime()))).findFirst();
            if(osh.isEmpty())
            {
                Scheduler s = new Scheduler();
                s.setTime(S.getTime());
                s.setDay(S.getDay());
                s.setWorkout(workout);
                schedulerService.create(s);
            }
            else
            {
                Scheduler s = osh.get();
                s.setWorkout(workout);
                schedulerService.update(s);
            }
        }

        var schedulers = schedulerService.readAll();
        model.addAttribute("schedulers", sortSchedulers(schedulers));

        var workouts = workoutService.findAll();
        model.addAttribute("workouts", workouts);

        W = new Workout();
        model.addAttribute("W", W);
        S = new Scheduler();
        model.addAttribute("S", S);

        return "admin_schedulers";
    }

    @GetMapping("admin_scheduler/{id}")
    public String showAdminEditSchedulersPost(@PathVariable(name="id") long id) {
        var osh = schedulerService.findById(id);
        if(!osh.isEmpty())
        {
            var sh = osh.get();
            var users = sh.getUsers();
            for (User user: users) {
                user.removeScheduler(sh);
                userService.update(user);
            }

            sh.setWorkout(null);
            schedulerService.delete(sh.getId());
        }
        return "redirect:/admin_scheduler";
    }
}
