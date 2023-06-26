package org.example;

import org.assertj.core.api.Assertions;
import org.example.model.Scheduler;
import org.example.model.User;
import org.example.model.Workout;
import org.example.repo.UserRepository;
import org.example.security.Role;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    private void prepare() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void createTest()    {
        // given
        User u = new User();
        u.setRole(Role.USER);
        u.setLogin("user");
        u.setPassword("pwd");
        u.setId(1l);

        // when
        userService.create(u);
        // then
        ArgumentCaptor<User> groupArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        Mockito.verify(userRepository).save(groupArgumentCaptor.capture());

        User capturedUser = groupArgumentCaptor.getValue();
        Assertions.assertThat(capturedUser).isEqualTo(u);
    }

    @Test
    void updateTest() {
        // given
        User u = new User();
        u.setRole(Role.USER);
        u.setLogin("user");
        u.setPassword("pwd");
        u.setId(1l);

        // when
        userService.update(u, u.getId());

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(u);
    }

    @Test
    void findByIdTest()  {
        // given
        User u = new User();
        u.setRole(Role.USER);
        u.setLogin("user");
        u.setPassword("pwd");
        u.setId(1l);

        Mockito.when(userRepository.findById(u.getId())).thenReturn(Optional.of(u));

        // when
        Optional<User> found = userService.findById(u.getId());

        // then
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.get().getId()).isEqualTo(u.getId());
        Assertions.assertThat(found.get().getLogin()).isEqualTo(u.getLogin());
        Assertions.assertThat(found.get().getFirstName()).isEqualTo(u.getFirstName());
        Assertions.assertThat(found.get().getLastName()).isEqualTo(u.getLastName());
        Assertions.assertThat(found.get().getPassword()).isEqualTo(u.getPassword());
        Assertions.assertThat(found.get().getSchedulers()).isEqualTo(u.getSchedulers());
    }

    @Test
    void findByLoginTest(){
        // given
        User u = new User();
        u.setRole(Role.USER);
        u.setLogin("user");
        u.setPassword("pwd");
        u.setId(1l);

        Mockito.when(userRepository.findByLogin(u.getLogin())).thenReturn(Optional.of(u));

        // when
        Optional<User> found = userService.findByLogin(u.getLogin());

        // then
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.get().getId()).isEqualTo(u.getId());
        Assertions.assertThat(found.get().getLogin()).isEqualTo(u.getLogin());
        Assertions.assertThat(found.get().getFirstName()).isEqualTo(u.getFirstName());
        Assertions.assertThat(found.get().getLastName()).isEqualTo(u.getLastName());
        Assertions.assertThat(found.get().getPassword()).isEqualTo(u.getPassword());
        Assertions.assertThat(found.get().getSchedulers()).isEqualTo(u.getSchedulers());
    }

}
