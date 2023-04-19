package com.ssu.moassubackend.domain.user;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest() throws Exception {
        User user = new User("test", "userA");

        User savedUser = userRepository.save(user);

        assertThat(user.getNickName()).isEqualTo(savedUser.getNickName());
        assertThat(user.getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    public void selectUserTest() throws Exception {
        User user = new User("test", "userB");

        userRepository.save(user);
        User findUser = userRepository.findById(user.getId()).get();

        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(findUser.getNickName()).isEqualTo(user.getNickName());
    }

    @Test
    public void selectAllUserTest() throws Exception {
        User user1 = new User("test1", "user1");
        User user2 = new User("test2", "user2");
        User user3 = new User("test3", "user3");
        userRepository.saveAll(Lists.newArrayList(user1, user2, user3));

        List<User> userList = userRepository.findAll();

        assertThat(userList.size()).isEqualTo(5);
        assertThat(userList.containsAll(Lists.newArrayList(user1, user2, user3)));
    }

    @Test
    public void updateUserTest() throws Exception {

        User user = userRepository.findById(4l).get();
        String updateEmail = "updateEmail";

        user.changeEmail(updateEmail);
        User foundUser = userRepository.findById(user.getId()).get();

        assertThat(foundUser.getEmail()).isEqualTo(updateEmail);
    }


    public void deleteUser() throws Exception {
        User user = userRepository.findById(4l).get();

        userRepository.delete(user);

        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @BeforeEach
    void beforeEach() {
        User userA = new User("emailA", "userA");
        User userB = new User("emailB", "userB");

        userRepository.save(userA);
        userRepository.save(userB);
    }

}