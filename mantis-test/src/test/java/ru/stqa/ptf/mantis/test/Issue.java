package ru.stqa.ptf.mantis.test;

import ru.stqa.ptf.mantis.model.Project;

public class Issue {

  int id;
  private String summery;
  private String description;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSummary() { return summery; }
  public String getDescription() { return description; }
  public Project getProject() { return project; }


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

  private Project project;

}
