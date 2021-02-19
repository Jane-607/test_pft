package ru.stqa.ptf.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactDate;
import ru.stqa.ptf.addressbook.model.GroupDate;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    app.getGroupHelper().returnToGroupPage();
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()){
      app.getGroupHelper().createGroup(new GroupDate("test1", null, null));
    }
    app.getNavigationHelper().returnToHomePage();
    app.getContactHelper().createContact(new ContactDate(
            "Eva",
            "Victorovna",
            "Orlova",
            "OOO Test",
            "84832121212",
            "e.orlova@bk.ru",
            "test1"), true);
    app.getNavigationHelper().returnToHomePage();
  }
}
