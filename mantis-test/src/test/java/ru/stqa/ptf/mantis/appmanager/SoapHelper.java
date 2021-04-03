package ru.stqa.ptf.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.ptf.mantis.model.Project;
import ru.stqa.ptf.mantis.test.Issue;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;


public class SoapHelper {

  private ApplicationManager app;

  public SoapHelper(ApplicationManager app) {
    this.app = app;
  }



  public Set<Project> getProject() throws IOException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] project = mc.mc_projects_get_user_accessible("administrator", "root");
    return Arrays.asList(project).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
            .collect(Collectors.toSet());
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, IOException {
    MantisConnectPortType mc = new MantisConnectLocator().getMantisConnectPort(new URL(app.getProperty("soap.baseUrl")));
    return mc;
  }

  public Issue addIssue(Issue issue) throws IOException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories("administrator", "root", BigInteger.valueOf(issue.getProject().getId()));

    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData .setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add(app.getProperty("soap.login"), app.getProperty("soap.password"), issueData);
    IssueData createdIssueData = mc.mc_issue_get(app.getProperty("soap.login"), app.getProperty("soap.password"), issueId);
    return new Issue()
            .withId(createdIssueData.getId().intValue())
            .withSummary(createdIssueData.getSummary())
            .withDescription(createdIssueData.getDescription())
            .withProject(new Project()
            .withId(createdIssueData.getProject().getId().intValue())
            .withName(createdIssueData.getProject().getName()));
  }

  public IssueData getIssue(int issueId) throws IOException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();

    IssueData issue = mc.mc_issue_get(app.getProperty("soap.login"), app.getProperty("soap.password"), BigInteger.valueOf(issueId));
    return issue;
  }
}

