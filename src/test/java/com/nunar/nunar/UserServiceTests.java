package com.nunar.nunar;

import com.nunar.nunar.model.User;
import com.nunar.nunar.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUsers() {
        //given
        User user = new User();
        user.setId("hsasy0113");
        user.setPw("password");
        user.setProfileUrl("https://image.com");
        user.setName("사승은");
        userRepository.save(user);

        //when
        User retrieveUser = userRepository.findById(user.getUserIdx()).get();

        //then
        Assert.assertEquals(retrieveUser.getName(), "사승은");
    }

}
