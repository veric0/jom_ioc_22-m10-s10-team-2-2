package com.softserve.itacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    final private static String PACKAGE = "com.softserve.itacademy.service.";
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @DisplayName("Check that UserService is present")
    @Test
    public void isInterfacePresent() {
        String userServiceInterfaceName = "UserService";
        try {
            Class<?> clazz = Class.forName(PACKAGE + userServiceInterfaceName);
            assertNotNull(clazz);
            assertEquals(userServiceInterfaceName, clazz.getSimpleName());
            assertTrue(Modifier.isInterface(clazz.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("There is no interface " + userServiceInterfaceName);
        }
    }

    @DisplayName("Check that UserServiceImpl is present")
    @Test
    public void isImplPresent() {
        String userServiceImplClassName = "UserServiceImpl";
        try {
            Class<?> clazz = Class.forName(PACKAGE + "impl." + userServiceImplClassName);
            assertNotNull(clazz);
            assertEquals(userServiceImplClassName, clazz.getSimpleName());
            assertTrue(!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("There is no class " + userServiceImplClassName);
        }
    }

    @Test
    public void checkAddUser() {
        User user = null;       // TODO, update code
        User expected = null;   // TODO, update code
        User actual = userService.addUser(user);
        Assertions.assertEquals(expected, actual, "check message");
    }

    // TODO, other tests
}
