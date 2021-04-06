package ru.stqa.ptf.mantis.test;

import ru.stqa.ptf.mantis.model.Project;

import java.util.Objects;

public class Issue {

  int id;
  private String summery;
  private String description;
  private String subject;
  private int state;
  private Project project;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSummary() {
    return summery;
  }

  public String getDescription() {
    return description;
  }

  public Project getProject() {
    return project;
  }

  public String getSubject() {
    return subject;
  }

  public int getState() {
    return state;
  }


  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public Issue withSummary(String summery) {
    this.summery = summery;
    return this;
  }

  public Issue withProject(Project project) {
    this.project = project;
    return this;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  public Issue withSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public Issue withState(int state) {
    this.state = state;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Issue issue = (Issue) o;
    return id == issue.id && Objects.equals(summery, issue.summery) && Objects.equals(description, issue.description) && Objects.equals(subject, issue.subject) && Objects.equals(state, issue.state) && Objects.equals(project, issue.project);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, summery, description, subject, state, project);
  }
}
