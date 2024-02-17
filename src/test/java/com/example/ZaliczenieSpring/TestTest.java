//package com.example.ZaliczenieSpring;
//
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.testng.annotations.Test;
//
//@SpringBootTest
//class UserServiceTest {
//    @MockBean
//    private RequestCounterRepository requestCounterRepository;
//
//    BookService bookService = new BookService();
//
//    @Test
//    public void testGetUserInfoUser() {
//        bookService.requestCounterRepository = requestCounterRepository;
//
//        String login = "octocat";
//
//        User result = userService.getUserInfo(login);
//
//        double calculations = 6.0 / 10064 * (2 + 8);
//        assertEquals(String.format("%.5f", calculations), String.format("%.5f", result.getCalculations()));
//    }
//
//    @Test
//    public void testUserNotFound() {
//        userService.requestCounterRepository = requestCounterRepository;
//
//        String login = "invalidUserxxx";
//
//        Throwable exception = assertThrows(RuntimeException.class, () -> userService.getUserInfo(login));
//        assertEquals("User not found on GitHub", exception.getMessage());
//    }
//
//
//}
