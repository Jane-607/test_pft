package ru.stqa.ptf.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.ptf.addressbook.model.ContactData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  private final Properties properties;

  public ContactDataGenerator() throws IOException {
    properties = new Properties();
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")) {
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    try (Writer writer = new FileWriter(file)) {
      for (ContactData contact : contacts) {
        writer.write(String.format("%s;%s;%s\n", contact.getFirstName(), contact.getMiddleName(), contact.getLastName()));
      }
    }
  }

  private List<ContactData> generateContacts(int count) {

    int a = 0;
    int b = 100000000;
    int random_number = a + (int) (Math.random() * b);

    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withFirstName(String.format(properties.getProperty("web.FirstName") + " %s", i))
              .withMiddleName(String.format(properties.getProperty("web.MiddleName") + " %s", i))
              .withLastName(String.format(properties.getProperty("web.LastName") + " %s", i))
              .withCompany(String.format(properties.getProperty("web.Company") + "%s", i))
              .withAddress(String.format(properties.getProperty("web.Address") + "%s", i))
              .withHome(properties.getProperty("web.HomePhone") + random_number)
              .withMobile(properties.getProperty("web.MobilePhone") + random_number)
              .withWork(properties.getProperty("web.WorkPhone") + random_number)
              .withEmail(String.format(properties.getProperty("web.Email") + "%s@bk.ru", i))
              .withEmail2(String.format(properties.getProperty("web.Email2") + "%s@ya.ru", i))
              .withEmail3(String.format(properties.getProperty("web.Email3") + "%s@gmail.com", i))
              .withGroup(properties.getProperty("web.Group")));
    }
    return contacts;
  }


}
