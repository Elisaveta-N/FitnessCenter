package org.example;

import org.assertj.core.api.Assertions;
import org.example.model.Scheduler;
import org.example.model.Workout;
import org.example.repo.SchedulerRepository;
import org.example.service.SchedulerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SchedulerServiceTest {
    @Mock
    private SchedulerRepository schedulerRepository;
    private SchedulerService schedulerService;

    @BeforeEach
    private void prepare() {

        schedulerService = new SchedulerService(schedulerRepository);
    }

    @Test
    void createTest()    {
        // given
        Scheduler s = new Scheduler();
        Workout w = new Workout();
        s.setWorkout(w);
        s.setTime("08:00");
        s.setDay("Понедельник");

        // when
        schedulerService.create(s);
        // then
        ArgumentCaptor<Scheduler> groupArgumentCaptor =
                ArgumentCaptor.forClass(Scheduler.class);

        Mockito.verify(schedulerRepository).save(groupArgumentCaptor.capture());

        Scheduler capturedScheduler = groupArgumentCaptor.getValue();
        Assertions.assertThat(capturedScheduler).isEqualTo(s);
    }


    @Test
    void readAllTest()    {
        // given
        Scheduler s = new Scheduler();
        Workout w = new Workout();
        s.setWorkout(w);
        s.setTime("08:00");
        s.setDay("Понедельник");

        Mockito.when(schedulerRepository.findAll()).thenReturn(List.of(s));

        // when
        List<Scheduler> expected = schedulerService.readAll();

        // then
        Mockito.verify(schedulerRepository).findAll();
        Assertions.assertThat(expected).isNotNull();
        Assertions.assertThat(expected.size()).isEqualTo(1);
        Assertions.assertThat(expected.get(0).getTime()).isEqualTo(s.getTime());
        Assertions.assertThat(expected.get(0).getDay()).isEqualTo(s.getDay());
        Assertions.assertThat(expected.get(0).getWorkout()).isEqualTo(s.getWorkout());
    }

    @Test
    void readTest()    {
        // given
        Scheduler s = new Scheduler();
        Workout w = new Workout();
        s.setWorkout(w);
        s.setTime("08:00");
        s.setDay("Понедельник");
        s.setId(1l);

        Mockito.when(schedulerRepository.findById(s.getId())).thenReturn(Optional.of(s));

        // when
        var expected = schedulerService.read(s.getId());

        // then
        Mockito.verify(schedulerRepository, Mockito.times(1)).findById(s.getId());
        Assertions.assertThat(expected.get()).isEqualTo(s);
    }

    @Test
    void findByIdTest()  {
        // given
        Scheduler s = new Scheduler();
        Workout w = new Workout();
        s.setWorkout(w);
        s.setTime("08:00");
        s.setDay("Понедельник");
        s.setId(1l);

        Mockito.when(schedulerRepository.findById(s.getId())).thenReturn(Optional.of(s));

        // when
        Optional<Scheduler> existed = schedulerService.findById(s.getId());

        // then
        Assertions.assertThat(existed).isNotNull();
        Assertions.assertThat(existed.get().getId()).isEqualTo(s.getId());
        Assertions.assertThat(existed.get().getWorkout()).isEqualTo(s.getWorkout());
        Assertions.assertThat(existed.get().getTime()).isEqualTo(s.getTime());
        Assertions.assertThat(existed.get().getDay()).isEqualTo(s.getDay());
    }

    @Test
    void updateTest() {
        // given
        Scheduler s = new Scheduler();
        Workout w = new Workout();
        s.setWorkout(w);
        s.setTime("08:00");
        s.setDay("Понедельник");

        // when
        schedulerService.update(s);

        // then
        Mockito.verify(schedulerRepository, Mockito.times(1)).save(s);
    }

    @Test
    void deleteTest() {
        // given
        Scheduler s = new Scheduler();
        Workout w = new Workout();
        s.setWorkout(w);
        s.setTime("08:00");
        s.setDay("Понедельник");
        s.setId(1l);

        // when
        schedulerService.delete(s.getId());

        // then
        Mockito.verify(schedulerRepository, Mockito.times(1)).deleteById(s.getId());
    }


}
