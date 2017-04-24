package com.github.pavlospt;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import com.github.pavlospt.misc.IssuesInfo;
import java.util.Arrays;
import java.util.List;

public class LithoIssueRegistry extends IssueRegistry {

  @Override
  public List<Issue> getIssues() {
    return Arrays.asList(IssuesInfo.LAYOUT_SPEC_NAME_ISSUE,
        IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE, IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_ISSUE,
        IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE);
  }
}
