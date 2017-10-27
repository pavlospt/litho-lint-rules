package com.github.pavlospt

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue
import com.github.pavlospt.misc.IssuesInfo
import java.util.Arrays

class LithoIssueRegistry : IssueRegistry() {

  override fun getIssues(): List<Issue> {
    return listOf(
        IssuesInfo.LAYOUT_SPEC_NAME_ISSUE,
        IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE,
        IssuesInfo.MISSING_STATIC_MODIFIER_ISSUE,
        IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_ISSUE,
        IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE,
        IssuesInfo.POSSIBLE_RESOURCE_TYPE_ISSUE
    )
  }
}
