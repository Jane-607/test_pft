package ru.stqa.ptf.mantis.test;

import org.testng.annotations.Test;
import ru.stqa.ptf.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {


  @Test
  public void testGetProjects() throws IOException, ServiceException {
    skipIfNotFixed(3);
    Set<Project> projects = app.soap().getProject();
    System.out.println(projects.size());
    for (Project project: projects) {
      System.out.println(project.getName());
    }
  }

  @Test
  public  void testCreateIssue() throws IOException, ServiceException {
    Set<Project> projects = app.soap().getProject();
    Issue issue = new Issue().withSummary("Test issue")
            .withDescription("Test issue description")
            .withProject(projects.iterator().next());

    Issue created = app.soap().addIssue(issue);
    assertEquals (issue.getSummary(), created.getSummary());
  }
}
