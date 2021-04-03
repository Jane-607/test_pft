package ru.stqa.ptf.mantis.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.ptf.mantis.model.MailMessage;
import ru.stqa.ptf.mantis.model.UserData;
import ru.stqa.ptf.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;


public class ChangePasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() throws MessagingException, IOException {
    app.mail().start();
  }

  @Test

  public void testChangePassword() throws IOException, MessagingException {

    Users allUsers = app.db().users();
    List<String> nonAdminUsers = new ArrayList<>();
    for (UserData user : allUsers) {
      if (!user.getName().equals("administrator")) {
        nonAdminUsers.add(user.getName());
      }
    }

    String password = "password2";
    String user = "";
    if (nonAdminUsers.size() == 0) {
      long now = System.currentTimeMillis();
      user = String.format("user%s", now);
      String email = user + "@localhost";

      app.registration().start(user, email);
      List<MailMessage> mailMessages = app.mail().waitForMail(2, 30000);
      String confirmationLink = app.user().findConfirmationLinkFirst(mailMessages, email);
      app.registration().finish(confirmationLink, user, password);
    } else {
      user = nonAdminUsers.get(0);
    }
    String email = user + "@localhost";

    app.login().start("administrator", "root");
    app.user().choice(user);
    app.user().changeUser();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);
    if (mailMessages.size() == 1) {
      String confirmationLink = app.user().findConfirmationLinkFirst(mailMessages, email);
      app.registration().finish(confirmationLink, user, password);
    } else {
      String confirmationLink = app.user().findConfirmationLinkLast(mailMessages, email);
      app.registration().finish(confirmationLink, user, password);
    }
    app.login().startUser(user, password);
    assertTrue(app.newSession().login(user, password));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
