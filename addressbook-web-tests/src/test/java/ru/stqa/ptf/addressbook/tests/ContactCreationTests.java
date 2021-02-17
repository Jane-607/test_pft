package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactDate;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    app.getContactHelper().initContactCreation();
    app.getContactHelper().fillContactForm(new ContactDate(
            "Eva",
            "Victorovna",
            "Orlova",
            "OOO Test",
            "84832121212",
            "e.orlova@bk.ru",
            "test1"), true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().returnToHomePage();
  }
}
