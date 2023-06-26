package org.example.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public
class SchedulerExDto {
    private List<SchedulerEx> schedulerexs = new ArrayList<SchedulerEx>();;

    public void add(SchedulerEx item) {
        schedulerexs.add(item);
    }
}
