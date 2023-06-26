package org.example;

import org.assertj.core.api.Assertions;
import org.example.model.Workout;
import org.example.repo.WorkoutRepository;
import org.example.service.WorkoutService;
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
public class WorkoutServiceTest {
    @Mock
    private WorkoutRepository workoutRepository;

    private WorkoutService workoutService;

    @BeforeEach
    private void prepare() {

        workoutService = new WorkoutService(workoutRepository);
    }

    @Test
    void createTest()    {
        // given
        Workout w = new Workout();
        w.setName("Теннис");
        w.setId(1l);

        // when
        workoutService.create(w);
        // then
        ArgumentCaptor<Workout> groupArgumentCaptor =
                ArgumentCaptor.forClass(Workout.class);

        Mockito.verify(workoutRepository).save(groupArgumentCaptor.capture());

        Workout capturedWorkout = groupArgumentCaptor.getValue();
        Assertions.assertThat(capturedWorkout).isEqualTo(w);
    }

    @Test
    void deleteTest() {
        // given
        Workout w = new Workout();
        w.setName("Теннис");
        w.setId(1l);

        // when
        workoutService.delete(w.getId());

        // then
        Mockito.verify(workoutRepository, Mockito.times(1)).deleteById(w.getId());
    }

    @Test
    void findAllTest()    {
        // given
        Workout w = new Workout();
        w.setName("Теннис");
        w.setId(1l);

        Mockito.when(workoutRepository.findAll()).thenReturn(List.of(w));

        // when
        List<Workout> expected = workoutService.findAll();

        // then
        Mockito.verify(workoutRepository).findAll();
        Assertions.assertThat(expected).isNotNull();
        Assertions.assertThat(expected.size()).isEqualTo(1);
        Assertions.assertThat(expected.get(0).getId()).isEqualTo(w.getId());
        Assertions.assertThat(expected.get(0).getName()).isEqualTo(w.getName());
    }

    @Test
    void readTest()    {
        // given
        Workout w = new Workout();
        w.setName("Теннис");
        w.setId(1l);

        Mockito.when(workoutRepository.findById(w.getId())).thenReturn(Optional.of(w));

        // when
        var expected = workoutService.read(w.getId());

        // then
        Mockito.verify(workoutRepository, Mockito.times(1)).findById(w.getId());
        Assertions.assertThat(expected.get()).isEqualTo(w);
    }

    @Test
    void updateTest() {
        // given
        Workout w = new Workout();
        w.setName("Теннис");
        w.setId(1l);

        // when
        workoutService.update(w, w.getId());

        // then
        Mockito.verify(workoutRepository, Mockito.times(1)).save(w);
    }

    @Test
    void findByNameTest(){
        // given
        Workout w = new Workout();
        w.setName("Теннис");
        w.setId(1l);

        Mockito.when(workoutRepository.findByName(w.getName())).thenReturn(Optional.of(w));

        // when
        Optional<Workout> found = workoutService.findByName(w.getName());

        // then
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.get().getName()).isEqualTo(w.getName());
    }

    @Test
    void findWorkoutsByName(){
        // given
        Workout w = new Workout();
        w.setName("Теннис");
        w.setId(1l);

        Mockito.when(workoutRepository.findAllByNameContaining(Mockito.any())).thenReturn(List.of(w));

        // when
        List<Workout> found = workoutService.findWorkoutsByName("Тенн");

        // then
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.size()).isEqualTo(1);
        Assertions.assertThat(found.get(0).getName()).isEqualTo(w.getName());
    }
}
