package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerEx {

    private Long id;
    private String day;
    private String time;
    private String workoutname;
    private Boolean checked;

    public SchedulerEx(Scheduler scheduler)
    {
        this.id = scheduler.getId();
        this.day = scheduler.getDay();
        this.time = scheduler.getTime();
        this.workoutname = scheduler.workout.getName();
        this.checked = false;
    }
}

