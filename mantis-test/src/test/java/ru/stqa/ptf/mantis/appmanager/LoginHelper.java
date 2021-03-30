package ru.stqa.ptf.mantis.appmanager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {

  public LoginHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    click(By.cssSelector("input[value='Вход']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Вход']"));
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
  }

  public void startUser(String username, String password) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    click(By.cssSelector("input[value='Вход']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[value='Вход']"));
  }


  public void finishLogin(String confirmationLink, String user, String password) {
    wd.get(confirmationLink);
    type(By.name("realname"),user);
    type(By.name("password"),password);
    type(By.name("password_confirm"),password);
    click(By.cssSelector("span.bigger-110"));
  }
}
