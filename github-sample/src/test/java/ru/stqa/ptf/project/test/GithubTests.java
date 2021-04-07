package ru.stqa.ptf.project.test;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test

  public void testCommits() throws IOException {

    Github github = new RtGithub("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");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("Jane-607", "test_pft")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
