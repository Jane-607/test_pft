package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.ptf.mantis.model.MailMessage;
import ru.stqa.ptf.mantis.model.UserData;
import ru.stqa.ptf.mantis.model.Users;

import java.util.ArrayList;
import java.util.List;

public class UsersHelper extends HelperBase {

  public UsersHelper(ApplicationManager app) {
    super(app);
  }

  private Users userCache = null;
  public Users all() {
    userCache = new Users();
    List<WebElement> elements = wd.findElements(By.cssSelector("table>tbody>tr"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String user = cells.get(0).getText();
      String email = cells.get(2).getText();
      String access = cells.get(3).getText();
      userCache.add(new UserData().withUser(user).withEmail(email));
    }
    return new Users(userCache);
  }

  public void delete() {
    wd.findElement(By.cssSelector("tbody > tr:nth-child(2) > td:nth-child(1) > a")).click();
    wd.findElement(By.cssSelector("input[value = 'Удалить учётную запись']")).click();
    wd.findElement(By.cssSelector("input[value = 'Удалить учётную запись']")).click();
    wd.findElement(By.linkText("Продолжить")).click();
  }

  public void choice(String userName) {
    wd.findElement(By.linkText(userName)).click();
  }



  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    String mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get().text;
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage);
  }

  public void deleteAll() {
    Users allUsers = app.user().all();

    if (allUsers.size() > 1) {
      List<String> userNames = new ArrayList<>();
      for (UserData user : allUsers) {
        userNames.add(user.getName());
      }

      String foundedUser = "";
      for (String userName : userNames) {
        if (!userName.contains("administrator")) {
          app.user().delete();
        }
        if (userName.equals("")) {
          foundedUser = userName;
          break;
        }
      }
    }
  }

}



