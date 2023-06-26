package org.example.controller;

import org.example.model.SchedulerEx;
import org.example.model.SchedulerExDto;
import org.example.model.User;
import org.example.security.Role;
import org.example.service.SchedulerService;
import org.example.service.UserService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Controller
public class UserSchedulerController {
    private List<String> labels;
    private List<String> keys;
    private final SchedulerService schedulerService;
    private final UserService userService;

    public UserSchedulerController(SchedulerService schedulerService, UserService userService) {
        labels = List.of("Понедельник", "Вторник", "Среда", "Четверг", "Пятница");
        keys = List.of("pn", "vt", "sr", "cht", "pt");
        this.schedulerService = schedulerService;
        this.userService = userService;
    }

    private myPair isUserAdminRole()
    {
        myPair res  = new myPair();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.findByLogin(auth.getName()).get();

        if(user.getRole() == Role.ADMIN)
            res.bAdmin = true;
        else
            res.bAdmin = false;

        res.id = user.getId();
        return res;
    }

    @PostMapping("user_scheduler")
    @PreAuthorize("hasAuthority('users:read')")
    public String editUserScheduler(SchedulerExDto sch, Model model)
    {
        model.addAttribute("labels", labels);
        model.addAttribute("keys", keys);

        var res = isUserAdminRole();
        if(res.bAdmin){
            model.addAttribute("admin", true);
        }

        var schedulers = schedulerService.readAll();
        var ouser = userService.findById(res.id);
        if(ouser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        User user = ouser.get();
        var user_schedulers = user.getSchedulers();

        for (int i=0; i<keys.size(); ++i) {
            SchedulerExDto dataDto = new SchedulerExDto();
            int finalI = i;
            schedulers.stream().filter(scheduler -> scheduler.getDay()
                            .equals(labels.get(finalI)))
                    .sorted(Comparator.comparing(o -> o.getTime()))
                    .forEach(item -> {
                        var osch = sch.getSchedulerexs().stream()
                                .filter(cur -> cur.getId().equals(item.getId()))
                                .findFirst();
                        if(osch.isEmpty()) { //пользователь не был подписан на данное занятие
                            var sh = new SchedulerEx(item);
                            if (user_schedulers.contains(item)) {
                                sh.setChecked(true);
                            }
                            dataDto.add(sh);
                        }
                        else { //пользователь был подписан на данное занятие
                            dataDto.add(osch.get());
                            var s = schedulerService.findById(osch.get().getId()).get();
                            if(osch.get().getChecked() == true) {
                                user_schedulers.add(s);
                            }
                            else {
                                user_schedulers.remove(s);
                            }
                            userService.update(user);
                        }
                    });
            model.addAttribute(keys.get(i), dataDto);
        }

        return "user_scheduler";
    }

    @GetMapping("user_scheduler")
    @PreAuthorize("hasAuthority('users:read')")
    public String showUserScheduler(Model model)
    {
        model.addAttribute("labels", labels);
        model.addAttribute("keys", keys);
        var res = isUserAdminRole();
        if(res.bAdmin){
            model.addAttribute("admin", true);
        }

        var schedulers = schedulerService.readAll();
        var user = userService.findById(res.id);
        var user_schedulers = user.get().getSchedulers();

        for (int i=0; i<keys.size(); ++i) {
            SchedulerExDto dataDto = new SchedulerExDto();
            int finalI = i;
            schedulers.stream().filter(scheduler -> scheduler.getDay()
                            .equals(labels.get(finalI)))
                    .sorted(Comparator.comparing(o -> o.getTime()))
                    .forEach(item -> {
                        var sh = new SchedulerEx(item);
                        if(user_schedulers.contains(item)){
                            sh.setChecked(true);
                        }
                        dataDto.add(sh);
                    });
            model.addAttribute(keys.get(i), dataDto);
        }

        return "user_scheduler";
    }
}

class myPair{
    public long id;
    public boolean bAdmin;
}
