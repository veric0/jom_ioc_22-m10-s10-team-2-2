package com.softserve.itacademy;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Random data from <a href="https://www.mockaroo.com/">mockaroo</a>
 * @author Dmytro Borak
 */
@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    final private static String PACKAGE = "com.softserve.itacademy.service.";
    final private static String userServiceInterfaceName = "UserService";
    final private static String userServiceImplClassName = "UserServiceImpl";
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @BeforeEach
    public void clearService() {
        userService.getAll().clear();
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
            assertEquals(1, interfaces.length, "Check there is only 1 implemented interface");
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

    @DisplayName("Check that fields is present")
    @Test
    void isFieldPresent() {
        String fieldName = "users";
        try {
            Class<?> clazz = Class.forName(PACKAGE + "impl." + userServiceImplClassName);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()), "Is field private");
            assertEquals(List.class, field.getType(), "Invalid field type");
        } catch (ClassNotFoundException e) {
            fail("There is no class " + userServiceImplClassName);
        } catch (NoSuchFieldException e) {
            fail("There is no field " + fieldName);
        }
    }

    @DisplayName("Check add one user")
    @Test
    public void checkAddUser() {
        User user = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User expected = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User actual = userService.addUser(user);
        assertEquals(expected, actual, "check result");
        List<User> expectedList = new ArrayList<>(1);
        expectedList.add(expected);
        List<User> actualList = userService.getAll();
        assertEquals(expectedList, actualList, "check all users");
    }

    @DisplayName("Check add many users")
    @Test
    public void checkAddManyUsers() {
        User user1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("Lon", "Castille", "lcastille8@cyberchimps.com", "HBUKq7");
        User user3 = new User("Tabbi", "Enden", "tenden2@purevolume.com", "WjDrbGyn1R");
        User expected1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User expected2 = new User("Lon", "Castille", "lcastille8@cyberchimps.com", "HBUKq7");
        User expected3 = new User("Tabbi", "Enden", "tenden2@purevolume.com", "WjDrbGyn1R");
        User actual = userService.addUser(user1);
        assertEquals(expected1, actual, "check add first user");
        actual = userService.addUser(user2);
        assertEquals(expected2, actual, "check add second user");
        actual = userService.addUser(user3);
        assertEquals(expected3, actual, "check add third user");
        List<User> expectedList = new ArrayList<>(3);
        expectedList.add(expected1);
        expectedList.add(expected2);
        expectedList.add(expected3);
        List<User> actualList = userService.getAll();
        assertNotNull(actualList, "check list is not null");
        assertEquals(expectedList.size(), actualList.size(), "check list size");
        assertEquals(expectedList, actualList, "check all users");
    }

    @DisplayName("Check add duplicated user")
    @Test
    public void checkAddDuplicatedUser() {
        User user1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "txsTxD");
        User user3 = new User("Carlos", "Snooks", "fsnooks0@businesswire.com", "txsTxD");
        User expected = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        userService.addUser(user1);
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user2), "check add duplicated user");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user3), "check add updated user");
        List<User> expectedList = new ArrayList<>(1);
        expectedList.add(expected);
        List<User> actualList = userService.getAll();
        assertEquals(expectedList, actualList, "check all users");
    }

    @DisplayName("Check add wrong data")
    @Test
    public void checkAddWrongData() {
        User user1 = new User(null, "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user3 = new User("Fredek", null, "fsnooks0@businesswire.com", "NzgOWl8");
        User user4 = new User("Fredek", "", "fsnooks0@businesswire.com", "NzgOWl8");
        User user5 = new User("Fredek", "Snooks", "WRONG_EMAIL", "NzgOWl8");
        User user6 = new User("Fredek", "Snooks", null, "NzgOWl8");
        User user7 = new User("Lon", "Castille", "lcastille8@cyberchimps.com", "psswd");
        User user8 = new User("Lon", "Castille", "lcastille8@cyberchimps.com", null);
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user1), "check null first name");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user2), "check empty first name");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user3), "check null last name");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user4), "check empty last name");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user5), "check wrong email");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user6), "check null email");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user7), "check short password");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(user8), "check null password");
        assertThrows(IllegalArgumentException.class, () -> userService.addUser(null), "check add null user");
        List<User> expectedList = new ArrayList<>(0);
        List<User> actualList = userService.getAll();
        assertEquals(expectedList, actualList, "check all users");
    }

    @DisplayName("Check update user first name")
    @Test
    public void checkUpdateFirstName() {
        User user1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("Carlos", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user3 = new User("", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user4 = new User(null, "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User expected = new User("Carlos", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        userService.addUser(user1);
        User actual = userService.updateUser(user2);
        assertEquals(expected, actual, "check update user first name");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user3), "check update empty first name");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user4), "check update null first name");
        List<User> expectedList = new ArrayList<>(1);
        expectedList.add(expected);
        List<User> actualList = userService.getAll();
        assertEquals(expectedList, actualList, "check all users");
    }

    @DisplayName("Check update user last name")
    @Test
    public void checkUpdateLastName() {
        User user1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("Fredek", "Barker", "fsnooks0@businesswire.com", "NzgOWl8");
        User user3 = new User("Fredek", "", "fsnooks0@businesswire.com", "NzgOWl8");
        User user4 = new User("Fredek", null, "fsnooks0@businesswire.com", "NzgOWl8");
        User expected = new User("Fredek", "Barker", "fsnooks0@businesswire.com", "NzgOWl8");
        userService.addUser(user1);
        User actual = userService.updateUser(user2);
        assertEquals(expected, actual, "check update user last name");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user3), "check update empty last name");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user4), "check update null last name");
        List<User> expectedList = new ArrayList<>(1);
        expectedList.add(expected);
        List<User> actualList = userService.getAll();
        assertEquals(expectedList, actualList, "check all users");
    }

    @DisplayName("Check update user password")
    @Test
    public void checkUpdatePassword() {
        User user1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "WZzRoJ");
        User user3 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "JZpmD");
        User user5 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", null);
        User expected = new User("Fredek", "Barker", "fsnooks0@businesswire.com", "WZzRoJ");
        userService.addUser(user1);
        User actual = userService.updateUser(user2);
        assertEquals(expected, actual, "check update user password");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user3), "check update short password");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user5), "check update null password");
        List<User> expectedList = new ArrayList<>(1);
        expectedList.add(expected);
        List<User> actualList = userService.getAll();
        assertEquals(expectedList, actualList, "check all users");
    }

    @DisplayName("Check update in empty list")
    @Test
    public void checkUpdateInEmptyList() {
        User user = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user), "check update in empty list");
        List<User> expected = new ArrayList<>(0);
        List<User> actual = userService.getAll();
        assertEquals(expected, actual, "check all users");
    }

    @DisplayName("Check delete user")
    @Test
    public void checkDeleteUser() {
        User user1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("Lon", "Castille", "lcastille8@cyberchimps.com", "HBUKq7");
        User user3 = new User("Tabbi", "Enden", "tenden2@purevolume.com", "WjDrbGyn1R");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.deleteUser(user2);
        List<User> expectedList = new ArrayList<>(2);
        expectedList.add(new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8"));
        expectedList.add(new User("Tabbi", "Enden", "tenden2@purevolume.com", "WjDrbGyn1R"));
        List<User> actualList = userService.getAll();
        assertEquals(expectedList, actualList, "check user is deleted");
    }

    @DisplayName("Check delete with wrong data")
    @Test
    public void checkDeleteWrongData() {
        User user1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("Lon", "Castille", "lcastille8@cyberchimps.com", "HBUKq7");
        User user3 = new User("Tabbi", "Enden", "tenden2@purevolume.com", "WjDrbGyn1R");
        User wrongUser = new User("Betti", "Brands", "bbrands2@narod.ua", "5arnKzVl");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(wrongUser), "check delete another user");
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(null), "check delete null");
        List<User> expectedList = new ArrayList<>(3);
        expectedList.add(new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8"));
        expectedList.add(new User("Lon", "Castille", "lcastille8@cyberchimps.com", "HBUKq7"));
        expectedList.add(new User("Tabbi", "Enden", "tenden2@purevolume.com", "WjDrbGyn1R"));
        List<User> actualList = userService.getAll();
        assertNotNull(actualList, "check list is not null");
        assertEquals(expectedList.size(), actualList.size(), "check list size");
        assertEquals(expectedList, actualList, "check user is not deleted");
    }

    @DisplayName("Check get all users")
    @Test
    public void checkGetAll() {
        User user1 = new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8");
        User user2 = new User("Lon", "Castille", "lcastille8@cyberchimps.com", "HBUKq7");
        User user3 = new User("Tabbi", "Enden", "tenden2@purevolume.com", "WjDrbGyn1R");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        List<User> expectedList = new ArrayList<>(3);
        expectedList.add(new User("Fredek", "Snooks", "fsnooks0@businesswire.com", "NzgOWl8"));
        expectedList.add(new User("Lon", "Castille", "lcastille8@cyberchimps.com", "HBUKq7"));
        expectedList.add(new User("Tabbi", "Enden", "tenden2@purevolume.com", "WjDrbGyn1R"));
        List<User> actualList = userService.getAll();
        assertNotNull(actualList, "check list is not null");
        assertEquals(expectedList.size(), actualList.size(), "check list size");
        assertEquals(expectedList, actualList, "check get all users");
    }
}
