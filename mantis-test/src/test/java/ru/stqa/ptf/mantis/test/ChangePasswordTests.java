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

    app.login().start("administrator", "root");
    app.user().deleteAll();

    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
    String email = user + "@localhost";

    app.registration().start(user, email);
    app.login().start("administrator", "root");

    //Users allUsers = app.user().all();
    Users allUsers = app.db().users();
    List <String> userName = new ArrayList<>();
    for (UserData name: allUsers) {
      if (name.getName().contains(user)) {
        app.user().choice(user);

        List<MailMessage> mailMessages = app.mail().waitForMail(2, 30000);
        String confirmationLink = app.user().findConfirmationLink(mailMessages, email);

        password = "password2";
        app.registration().finish(confirmationLink, user, password);
        app.login().startUser(user, password);
        assertTrue(app.newSession().login(user, password));
      }
    }


//    Users allUsers = app.db().users();
//    List<Integer> userId = new ArrayList<>();
//    for (UserData id: allUsers) {
//      userId.add(id.getId());
//      //  if (userName.contains(user)) {
//      //    app.user().choice();
//    }
  }




  @AfterMethod(alwaysRun = true)
  public  void stopMailServer(){
    app.mail().stop();
  }
}
