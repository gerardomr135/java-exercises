package com.epam.training.login.service;

import com.epam.training.login.data.UserStore;
import com.epam.training.login.domain.Address;
import com.epam.training.login.domain.LoginResult;
import com.epam.training.login.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {
    @Mock
    UserStore userStore;
    @InjectMocks
    DefaultUserService userService;


   @Test
   @DisplayName("login with correct credentials should return LogingResult.SUCCESS")
   void loginShouldReturnLoggingResultSuccess() {
       var loginName = "adam";
       var password = "secret_adam";

       when(userStore.getUserByLoginName(loginName)).thenReturn(createAdamUser());

       var result = userService.login(loginName, password);
       assertEquals(LoginResult.SUCCESS, result);
       
       verify(userStore, times(1)).updateFailedLoginCounter(loginName, 0);
   }

   @Test
   @DisplayName("logging with credentials of an inexistent user should return LoginResult.UNSUCCESSFUL")
   void loggingWithCredentialsOfAnInexistentUserShouldReturnLoginResultUnsuccessful() {
    var loginName = "bob";
    var password = "secret_bob";

    when(userStore.getUserByLoginName(loginName)).thenReturn(null);

    var result = userService.login(loginName, password);
    assertEquals(LoginResult.UNSUCCESSFUL, result);
   }

   @Test
   @DisplayName("login should throw UserLockedException because of a locked user")
   void loginShouldThrowUserLockedExceptionBecauseOfLockedUser() {
       var loginName = "adam";
       var password = "secret_adam";

       when(userStore.getUserByLoginName(loginName)).thenReturn(createLockedAdamUser());

       assertThrows(UserLockedException.class, () -> userService.login(loginName, password));
   }

   @Test
   @DisplayName("logging should return LoginResult.UNSUCCESSFUL")
   void loggingShouldReturnLoginResultUnsuccessful() {
       var loginName = "adam";
       var password = "invalid_password";

       when(userStore.getUserByLoginName(loginName)).thenReturn(createAdamUser());
       when(userStore.getFailedLoginCounter(loginName)).thenReturn(0);

       var result = userService.login(loginName, password);
       assertEquals(LoginResult.UNSUCCESSFUL, result);
       
       verify(userStore, times(1)).updateFailedLoginCounter(loginName, 1);
   }

    @Test
    @DisplayName("logging should throw UserLockedException due to excessive attempts")
    void loggingShouldThrowUserLockedExceptionDueToExcessiveAttempts() {
        var loginName = "adam";
        var password = "invalid_password";

        when(userStore.getUserByLoginName(loginName)).thenReturn(createAdamUser());
        when(userStore.getFailedLoginCounter(loginName)).thenReturn(2);

        assertThrows(UserLockedException.class, () ->userService.login(loginName, password));
        
        verify(userStore, times(1)).updateFailedLoginCounter(loginName, 3);
   }

   @Test
   @DisplayName("getLoggedInUserAddress should return user address after successful login")
   void getLoggedInUserAddressShouldReturnUserAddressAfterSuccessfulLogin() {
       var loginName = "adam";
       var password = "secret_adam";

       var adamUser = createAdamUser();

       when(userStore.getUserByLoginName(loginName)).thenReturn(adamUser);

       userService.login(loginName, password);

       var want = adamUser.getAddress();
       var got =  userService.getLoggedInUserAddress();

       assertEquals(want, got);
   }

   private User createAdamUser() {
       Address adamAddress = new Address();
       adamAddress.setCity("Belleville");
       adamAddress.setCountry("United States of America");
       adamAddress.setZipCode("07109");
       adamAddress.setAddressLine("Lincoln Street 2659");
       adamAddress.setName("Adam Davis");

       User adam = new User();
       adam.setLoginName("adam");
       adam.setPassword("secret_adam");
       adam.setAddress(adamAddress);
       adam.setLocked(false);

       return adam;
   }

   private User createLockedAdamUser() {
       var adam = createAdamUser();
       adam.setLocked(true);
       return adam;
   }
}
