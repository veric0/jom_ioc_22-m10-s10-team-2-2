package com.softserve.itacademy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    final private static String PACKAGE = "com.softserve.itacademy.service.";
    final private static String userServiceInterfaceName = "UserService";
    final private static String userServiceImplClassName = "UserServiceImpl";
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
        try {
            Class<?> clazz = Class.forName(PACKAGE + "impl." + userServiceImplClassName);
            assertNotNull(clazz);
            assertEquals(userServiceImplClassName, clazz.getSimpleName());
            assertTrue(!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()));
            Class<?>[] interfaces = clazz.getInterfaces();
            assertTrue(interfaces.length == 1, "Check there is only 1 implemented interface");
            assertEquals(interfaces[0].getSimpleName(), userServiceInterfaceName, "Check if implemented interface is " + userServiceInterfaceName);
        } catch (ClassNotFoundException e) {
            fail("There is no class " + userServiceImplClassName);
        }
    }

    @DisplayName("Check that constructor is present")
    @Test
    public void isConstructorPresent() {
        try {
            Class<?> clazz = Class.forName(PACKAGE + "impl." + userServiceImplClassName);
            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
            assertEquals(1, declaredConstructors.length, "There is no exactly 1 constructor");
            Constructor<?> constructor = declaredConstructors[0];
            assertTrue(Modifier.isPublic(constructor.getModifiers()), "Constructor is not public");
            assertEquals(0, constructor.getParameterCount(), "Unexpected parameters in constructor");
        } catch (ClassNotFoundException e) {
            fail("There is no class " + userServiceImplClassName);
        }
    }

    @DisplayName("Check that interface contains method")
    @ParameterizedTest
    @MethodSource("listMethods")
    void isMethodPresent(String methodName, Class<?>[] parameterTypes, Class<?> parameterReturn) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + userServiceInterfaceName);
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            assertTrue(Modifier.isPublic(method.getModifiers()), "Check if is public");
            assertTrue(Modifier.isAbstract(method.getModifiers()), "Check if is abstract");
            assertEquals(parameterReturn.getName(), method.getReturnType().getName(), "Invalid return type");
        } catch (ClassNotFoundException e) {
            fail("There is no interface " + userServiceInterfaceName);
        } catch (NoSuchMethodException e) {
            fail("Interface do not have method " + methodName + " with given parameters");
        }

    }

    private static Stream<Arguments> listMethods() {
        return Stream.of(Arguments.of("addUser", new Class<?>[]{User.class}, User.class),
                Arguments.of("updateUser", new Class<?>[]{User.class}, User.class),
                Arguments.of("deleteUser", new Class<?>[]{User.class}, void.class),
                Arguments.of("getAll", new Class<?>[]{}, List.class));
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
