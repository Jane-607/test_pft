package ru.stqa.ptf.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.ptf.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTest extends TestBase{

  @Test
  public void testContactPhone() {
    app.goTo().HomePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHome(),contact.getMobile(),contact.getWork())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactPhoneTest::cleanedPhone)
            .collect(Collectors.joining("\n"));
  }

  public static String cleanedPhone(String phone){
    return phone.replaceAll("[^0-9+]","");
  }

}
