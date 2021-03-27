package ru.stqa.ptf.mantis.model;

public class MailMessage extends Object {
  public String to;
  public String text;

  public MailMessage(String to, String text) {
    this.to = to;
    this.text = text;
  }
}
