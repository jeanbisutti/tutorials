package com.baeldung.vavr.repositories;

import com.baeldung.Application;
import com.baeldung.QuickPerfConfig;
import com.baeldung.repositories.VavrUserRepository;
import com.baeldung.vavr.User;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quickperf.spring.junit4.QuickPerfSpringRunner;
import org.quickperf.sql.annotation.DisplaySql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(QuickPerfSpringRunner.class)
@SpringBootTest(classes = {Application.class, QuickPerfConfig.class})
public class VavrRepositoryIntegrationTest {

    @Autowired
    private VavrUserRepository userRepository;

    @Before
    public void setup() {
        User user1 = new User();
        user1.setName("John");
        User user2 = new User();
        user2.setName("John");

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    @DisplaySql
    public void whenAddUsers_thenGetUsers() {
        Option<User> user = userRepository.findById(1L);
        assertFalse(user.isEmpty());
        assertTrue(user.get().getName().equals("John"));

        Seq<User> users = userRepository.findByName("John");
        assertEquals(2, users.size());
    }
}
