package com.github.pavlospt.misc;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.github.pavlospt.LithoLintIssuesDetector;

public class IssuesInfo {

  // LayoutSpec
  public static final String LAYOUT_SPEC_CLASS_NAME_ISSUE_ID = "LayoutSpecClassName";
  public static final String LAYOUT_SPEC_CLASS_NAME_ISSUE_DESC =
      "Component class name is not following Litho best practices";
  public static final String LAYOUT_SPEC_CLASS_NAME_ISSUE_EXPLANATION =
      "Component class name should follow the NAMEComponentSpec name pattern";
  public static final Category LAYOUT_SPEC_CLASS_NAME_ISSUE_CATEGORY = Category.CORRECTNESS;
  public static final int LAYOUT_SPEC_CLASS_NAME_ISSUE_PRIORITY = 5;
  public static final Severity LAYOUT_SPEC_CLASS_NAME_ISSUE_SEVERITY = Severity.WARNING;
  public static final Implementation LAYOUT_SPEC_CLASS_NAME_ISSUE_IMPLEMENTATION =
      new Implementation(LithoLintIssuesDetector.class, Scope.JAVA_FILE_SCOPE);

  public static final Issue LAYOUT_SPEC_NAME_ISSUE =
      Issue.create(LAYOUT_SPEC_CLASS_NAME_ISSUE_ID, LAYOUT_SPEC_CLASS_NAME_ISSUE_DESC,
          LAYOUT_SPEC_CLASS_NAME_ISSUE_EXPLANATION, LAYOUT_SPEC_CLASS_NAME_ISSUE_CATEGORY,
          LAYOUT_SPEC_CLASS_NAME_ISSUE_PRIORITY, LAYOUT_SPEC_CLASS_NAME_ISSUE_SEVERITY,
          LAYOUT_SPEC_CLASS_NAME_ISSUE_IMPLEMENTATION);

  // AnnotatedMethodVisibility
  public static final String ANNOTATED_METHOD_VISIBILITY_ISSUE_ID = "AnnotatedMethodVisibility";
  public static final String ANNOTATED_METHOD_VISIBILITY_ISSUE_DESC =
      "Method visibility is not following Litho best practices";
  public static final String ANNOTATED_METHOD_VISIBILITY_ISSUE_EXPLANATION =
      "Method visibility should be package-private and static";
  public static final Category ANNOTATED_METHOD_VISIBILITY_ISSUE_CATEGORY = Category.CORRECTNESS;
  public static final int ANNOTATED_METHOD_VISIBILITY_ISSUE_PRIORITY = 5;
  public static final Severity ANNOTATED_METHOD_VISIBILITY_ISSUE_SEVERITY = Severity.WARNING;
  public static final Implementation ANNOTATED_METHOD_VISIBILITY_ISSUE_IMPLEMENTATION =
      new Implementation(LithoLintIssuesDetector.class, Scope.JAVA_FILE_SCOPE);

  public static final Issue ANNOTATED_METHOD_VISIBILITY_ISSUE =
      Issue.create(ANNOTATED_METHOD_VISIBILITY_ISSUE_ID, ANNOTATED_METHOD_VISIBILITY_ISSUE_DESC,
          ANNOTATED_METHOD_VISIBILITY_ISSUE_EXPLANATION, ANNOTATED_METHOD_VISIBILITY_ISSUE_CATEGORY,
          ANNOTATED_METHOD_VISIBILITY_ISSUE_PRIORITY, ANNOTATED_METHOD_VISIBILITY_ISSUE_SEVERITY,
          ANNOTATED_METHOD_VISIBILITY_ISSUE_IMPLEMENTATION);

  // Component Context Name
  public static final String COMPONENT_CONTEXT_NAME_ISSUE_ID = "ComponentContextName";
  public static final String COMPONENT_CONTEXT_NAME_ISSUE_DESC =
      "ComponentContext parameter name is not following Litho best practices";
  public static final String COMPONENT_CONTEXT_NAME_ISSUE_EXPLANATION =
      "ComponentContext parameter name should be 'c' in order to make your code more readable";
  public static final Category COMPONENT_CONTEXT_NAME_ISSUE_CATEGORY = Category.CORRECTNESS;
  public static final int COMPONENT_CONTEXT_NAME_ISSUE_PRIORITY = 5;
  public static final Severity COMPONENT_CONTEXT_NAME_ISSUE_SEVERITY = Severity.WARNING;
  public static final Implementation COMPONENT_CONTEXT_NAME_ISSUE_IMPLEMENTATION =
      new Implementation(LithoLintIssuesDetector.class, Scope.JAVA_FILE_SCOPE);

  public static final Issue COMPONENT_CONTEXT_NAME_ISSUE_ISSUE =
      Issue.create(COMPONENT_CONTEXT_NAME_ISSUE_ID, COMPONENT_CONTEXT_NAME_ISSUE_DESC,
          COMPONENT_CONTEXT_NAME_ISSUE_EXPLANATION, COMPONENT_CONTEXT_NAME_ISSUE_CATEGORY,
          COMPONENT_CONTEXT_NAME_ISSUE_PRIORITY, COMPONENT_CONTEXT_NAME_ISSUE_SEVERITY,
          COMPONENT_CONTEXT_NAME_ISSUE_IMPLEMENTATION);

  // Optional Prop Order
  public static final String OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_ID = "OptionalPropBeforeRequired";
  public static final String OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_DESC =
      "Props order is not following Litho best practices";
  public static final String OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_EXPLANATION =
      "Required props should be defined before optional ones";
  public static final Category OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_CATEGORY = Category.CORRECTNESS;
  public static final int OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_PRIORITY = 5;
  public static final Severity OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_SEVERITY = Severity.WARNING;
  public static final Implementation OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_IMPLEMENTATION =
      new Implementation(LithoLintIssuesDetector.class, Scope.JAVA_FILE_SCOPE);

  public static final Issue OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE =
      Issue.create(OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_ID, OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_DESC,
          OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_EXPLANATION,
          OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_CATEGORY,
          OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_PRIORITY,
          OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_SEVERITY,
          OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_IMPLEMENTATION);

  // Possible Resource Type
  public static final String POSSIBLE_RESOURCE_TYPE_ISSUE_ID = "PossibleResourceType";
  public static final String POSSIBLE_RESOURCE_TYPE_ISSUE_DESC =
      "Consider using a ResType for parameters representing Android resources";
  public static final String POSSIBLE_RESOURCE_TYPE_ISSUE_EXPLANATION =
      "Consider using a ResType for parameters representing Android resources";
  public static final Category POSSIBLE_RESOURCE_TYPE_ISSUE_CATEGORY = Category.CORRECTNESS;
  public static final int POSSIBLE_RESOURCE_TYPE_ISSUE_PRIORITY = 5;
  public static final Severity POSSIBLE_RESOURCE_TYPE_ISSUE_SEVERITY = Severity.INFORMATIONAL;
  public static final Implementation POSSIBLE_RESOURCE_TYPE_ISSUE_IMPLEMENTATION =
      new Implementation(LithoLintIssuesDetector.class, Scope.JAVA_FILE_SCOPE);

  public static final Issue POSSIBLE_RESOURCE_TYPE_ISSUE =
      Issue.create(POSSIBLE_RESOURCE_TYPE_ISSUE_ID, POSSIBLE_RESOURCE_TYPE_ISSUE_DESC,
          POSSIBLE_RESOURCE_TYPE_ISSUE_EXPLANATION, POSSIBLE_RESOURCE_TYPE_ISSUE_CATEGORY,
          POSSIBLE_RESOURCE_TYPE_ISSUE_PRIORITY, POSSIBLE_RESOURCE_TYPE_ISSUE_SEVERITY,
          POSSIBLE_RESOURCE_TYPE_ISSUE_IMPLEMENTATION);

  public static final Issue DEBUG_ISSUE =
      Issue.create("Debug Issue", "Debug Issue", "Debug Issue", Category.CORRECTNESS, 5,
          Severity.WARNING, new Implementation(LithoLintIssuesDetector.class, Scope.JAVA_FILE_SCOPE));
}
