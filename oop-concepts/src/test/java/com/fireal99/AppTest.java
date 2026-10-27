package com.fireal99;

import com.fireal99.mocks.UserRepo;
import com.fireal99.mocks.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
@ExtendWith(MockitoExtension.class)
public class AppTest {
       @BeforeEach
       public void beforeEach() {
              System.out.println("before each");
       }
       @AfterEach
       public void afterEach() {
              System.out.println("after each");
       }

       @BeforeAll
       public static void beforeAll() {
              System.out.println("before all");
       }

       @AfterAll
       public static void afterAll() {
              System.out.println("after all");
       }

       @Mock
       List<String> mockedList;
       @Test
       public void whenNotUseMockAnnotation_thenCorrect() {
              mockedList.add("one");
              verify(mockedList, times(1)).add("one");

              assertEquals(0, mockedList.size());

              when(mockedList.size()).thenReturn(100);
              assertEquals(100, mockedList.size());
       }

       @Spy
       List<String> spiedList = new ArrayList<>();
       @Test
       public void whenNotUseSpyAnnotation_thenCorrect() {
              spiedList.add("one");
              spiedList.add("two");

              verify(spiedList, times(1)).add("one");
              verify(spiedList, times(1)).add("two");

              assertEquals(2, spiedList.size());

              doReturn(100).when(spiedList).size();
              assertEquals(100, spiedList.size());
       }

       @Mock
       private UserRepo userRepo;

       @InjectMocks
       private UserService userService;

       @DisplayName("Test MC")
       @Test
       public void testMC() {
              userService.getUsers();

              verify(userRepo, times(1)).getUsers();

              when(userRepo.getUsers()).thenReturn(Arrays.asList("User 1", "User 2"));

              assertEquals(Arrays.asList("User 1", "User 2"), userRepo.getUsers());
       }

       @Test
       @Disabled("Not implemented yet")
       public void testMC2() {
              List<Integer> numbers = Arrays.asList(1, 2, 3);
              assertTrue(numbers.stream()
                      .mapToInt(Integer::intValue)
                      .sum() > 5, () -> "Sum should be greater than 5");
       }

       @Test
       @Disabled
       void groupAssertions() {
              int[] numbers = {0, 1, 2, 3, 4};
              assertAll("numbers",
                      () -> assertEquals(1, numbers[0]),
                      () -> assertEquals(3, numbers[3]),
                      () -> assertEquals(1, numbers[4])
              );
       }

       @Test
       void trueAssumption() {
              assumeTrue(5 > 1);
              assertEquals(5 + 2, 7);
       }

       @Test
       void falseAssumption() {
              assumeFalse(5 < 1);
              assertEquals(5 + 2, 7);
       }

       @Test
       void assumptionThat() {
              String someString = "Just a string";
              assumingThat(
                      someString.equals("Just a string"),
                      () -> assertEquals(2 + 2, 4)
              );
       }

       @Test
       void shouldThrowException() {
              Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
                     throw new UnsupportedOperationException("Not supported");
              });
              assertEquals("Not supported", exception.getMessage());
       }

       @Test
       void assertThrowsException() {
              String str = null;
              assertThrows(IllegalArgumentException.class, () -> {
                     Integer.valueOf(str);
              });
       }

       @TestFactory
       public Collection<DynamicTest> dynamicTestFactory() {
              return Arrays.asList(
                      DynamicTest.dynamicTest("Add test",
                              () -> assertEquals(2, Math.addExact(1, 1))),
                      DynamicTest.dynamicTest("Multiply Test",
                              () -> assertEquals(4, Math.multiplyExact(2, 2)))
              );
       }

       @TestFactory
       Stream<DynamicTest> dynamicTestsFromIntStream() {
              return IntStream.iterate(0, n -> n + 2).limit(10)
                      .mapToObj(n -> DynamicTest.dynamicTest("test" + n,
                              () -> assertTrue(n % 2 == 0)));
       }

       @ParameterizedTest
       @CsvSource({
               "1, 1, 2",
               "5, 5, 10",
               "10, 20, 30"
       })
       void testAddition(int a, int b, int expected) {
              assertEquals(expected, a + b);
       }

       @ParameterizedTest
       @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
       void testWithCsvFile(Integer firstNumber, Integer secondNumber, Integer expected) {
              assertEquals(expected, firstNumber + secondNumber);
       }
}
