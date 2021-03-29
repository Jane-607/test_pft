package ru.stqa.ptf.mantis.test;

import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.ptf.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class RegistrationTests extends TestBase {

  //@BeforeMethod
  public  void startMailServer(){
    app.mail().start();
  }

  @Test

  public  void  testRegistration () throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
    String email =  user + "@localhost";
    app.james().createUser(user, password);
    app.registration().start(user,email);
    //List<MailMessage> mailMessages= app.mail().waitForMail(2, 10000);
    List<MailMessage> mailMessages= app.james().waitForMail(user,password,30000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, user, password);
    assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    String mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get().text;
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage);
  }


 // @AfterMethod(alwaysRun = true)
  public  void stopMailServer(){
    app.mail().stop();
  }
}
