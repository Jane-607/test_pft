package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

  public RegistrationHelper (ApplicationManager app) {
    super(app);
  }

  public void start (String username, String email) {

    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    String text = wd.findElement(By.cssSelector("input[value='Зарегистрироваться']")).getAttribute("value");
    wd.findElement(By.cssSelector("input[value='Зарегистрироваться']")).click();
  }
  public void finish(String confirmationLink, String user, String password) {
    wd.get(confirmationLink);
    type(By.name("realname"),user);
    type(By.name("password"),password);
    type(By.name("password_confirm"),password);
    click(By.cssSelector("span.bigger-110"));
  }
}
