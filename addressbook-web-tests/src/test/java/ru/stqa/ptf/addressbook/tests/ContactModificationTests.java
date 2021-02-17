package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactDate;

public class ContactModificationTests extends TestBase {

  @Test
  public void ContactModificationTests() {
    app.getContactHelper().returnToHomePage();
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactDate(
            "Eva",
            "Victorovna",
            "Orlova",
            "OOO Test",
            "84832121212",
            "e.orlova@bk.ru",
            null),false);
    app.getContactHelper().submitContacModification();
    app.getContactHelper().returnToHomePage();
    }
}
