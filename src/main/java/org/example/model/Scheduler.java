package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Getter
@Setter
@ToString
@Table(name = "scheduler")
public class Scheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "time")
    private String time;
    @ManyToOne
    @JoinColumn(name="workout_id")
    Workout workout;

    @ManyToMany(mappedBy = "schedulers")
    @JsonIgnore
    Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof User)) return false;

        return id != null && id.equals(((Scheduler) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

