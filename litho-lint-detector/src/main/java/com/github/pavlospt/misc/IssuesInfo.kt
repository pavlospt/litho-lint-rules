package com.github.pavlospt.misc

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.github.pavlospt.LithoLintIssuesDetector

object IssuesInfo {

  // LayoutSpec
  val LAYOUT_SPEC_CLASS_NAME_ISSUE_ID = "LayoutSpecClassName"
  @JvmField
  val LAYOUT_SPEC_CLASS_NAME_ISSUE_DESC = "Component class name is not following Litho best practices"
  val LAYOUT_SPEC_CLASS_NAME_ISSUE_EXPLANATION = "Component class name should follow the NAMEComponentSpec name pattern"
  val LAYOUT_SPEC_CLASS_NAME_ISSUE_CATEGORY = Category.CORRECTNESS
  val LAYOUT_SPEC_CLASS_NAME_ISSUE_PRIORITY = 5
  val LAYOUT_SPEC_CLASS_NAME_ISSUE_SEVERITY = Severity.WARNING
  val LAYOUT_SPEC_CLASS_NAME_ISSUE_IMPLEMENTATION = Implementation(
      LithoLintIssuesDetector::class.java, Scope.JAVA_FILE_SCOPE)

  @JvmField
  val LAYOUT_SPEC_NAME_ISSUE = Issue.create(LAYOUT_SPEC_CLASS_NAME_ISSUE_ID,
      LAYOUT_SPEC_CLASS_NAME_ISSUE_DESC,
      LAYOUT_SPEC_CLASS_NAME_ISSUE_EXPLANATION, LAYOUT_SPEC_CLASS_NAME_ISSUE_CATEGORY,
      LAYOUT_SPEC_CLASS_NAME_ISSUE_PRIORITY, LAYOUT_SPEC_CLASS_NAME_ISSUE_SEVERITY,
      LAYOUT_SPEC_CLASS_NAME_ISSUE_IMPLEMENTATION)

  // AnnotatedMethodVisibility
  val ANNOTATED_METHOD_VISIBILITY_ISSUE_ID = "AnnotatedMethodVisibility"
  @JvmField
  val ANNOTATED_METHOD_VISIBILITY_ISSUE_DESC = "Method visibility is not following Litho best practices"
  val ANNOTATED_METHOD_VISIBILITY_ISSUE_EXPLANATION = "Method visibility should be package-private"
  val ANNOTATED_METHOD_VISIBILITY_ISSUE_CATEGORY = Category.CORRECTNESS
  val ANNOTATED_METHOD_VISIBILITY_ISSUE_PRIORITY = 5
  val ANNOTATED_METHOD_VISIBILITY_ISSUE_SEVERITY = Severity.WARNING
  val ANNOTATED_METHOD_VISIBILITY_ISSUE_IMPLEMENTATION = Implementation(
      LithoLintIssuesDetector::class.java, Scope.JAVA_FILE_SCOPE)

  @JvmField
  val ANNOTATED_METHOD_VISIBILITY_ISSUE = Issue.create(ANNOTATED_METHOD_VISIBILITY_ISSUE_ID,
      ANNOTATED_METHOD_VISIBILITY_ISSUE_DESC,
      ANNOTATED_METHOD_VISIBILITY_ISSUE_EXPLANATION, ANNOTATED_METHOD_VISIBILITY_ISSUE_CATEGORY,
      ANNOTATED_METHOD_VISIBILITY_ISSUE_PRIORITY, ANNOTATED_METHOD_VISIBILITY_ISSUE_SEVERITY,
      ANNOTATED_METHOD_VISIBILITY_ISSUE_IMPLEMENTATION)

  // AnnotatedMethodMissingStaticModifier
  val MISSING_STATIC_MODIFIER_ISSUE_ID = "MissingStaticModifier"
  @JvmField
  val MISSING_STATIC_MODIFIER_ISSUE_DESC = "Method is missing 'static' modifier"
  val MISSING_STATIC_MODIFIER_ISSUE_EXPLANATION = "Method should be static"
  val MISSING_STATIC_MODIFIER_ISSUE_CATEGORY = Category.CORRECTNESS
  val MISSING_STATIC_MODIFIER_ISSUE_PRIORITY = 5
  val MISSING_STATIC_MODIFIER_ISSUE_SEVERITY = Severity.WARNING
  val MISSING_STATIC_MODIFIER_ISSUE_IMPLEMENTATION = Implementation(
      LithoLintIssuesDetector::class.java, Scope.JAVA_FILE_SCOPE)

  @JvmField
  val MISSING_STATIC_MODIFIER_ISSUE = Issue.create(MISSING_STATIC_MODIFIER_ISSUE_ID,
      MISSING_STATIC_MODIFIER_ISSUE_DESC,
      MISSING_STATIC_MODIFIER_ISSUE_EXPLANATION, MISSING_STATIC_MODIFIER_ISSUE_CATEGORY,
      MISSING_STATIC_MODIFIER_ISSUE_PRIORITY, MISSING_STATIC_MODIFIER_ISSUE_SEVERITY,
      MISSING_STATIC_MODIFIER_ISSUE_IMPLEMENTATION)

  // Component Context Name
  val COMPONENT_CONTEXT_NAME_ISSUE_ID = "ComponentContextName"
  @JvmField
  val COMPONENT_CONTEXT_NAME_ISSUE_DESC = "ComponentContext parameter name is not following Litho best practices"
  val COMPONENT_CONTEXT_NAME_ISSUE_EXPLANATION = "ComponentContext parameter name should be 'c' in order to make your code more readable"
  val COMPONENT_CONTEXT_NAME_ISSUE_CATEGORY = Category.CORRECTNESS
  val COMPONENT_CONTEXT_NAME_ISSUE_PRIORITY = 5
  val COMPONENT_CONTEXT_NAME_ISSUE_SEVERITY = Severity.WARNING
  val COMPONENT_CONTEXT_NAME_ISSUE_IMPLEMENTATION = Implementation(
      LithoLintIssuesDetector::class.java, Scope.JAVA_FILE_SCOPE)

  @JvmField
  val COMPONENT_CONTEXT_NAME_ISSUE_ISSUE = Issue.create(COMPONENT_CONTEXT_NAME_ISSUE_ID,
      COMPONENT_CONTEXT_NAME_ISSUE_DESC,
      COMPONENT_CONTEXT_NAME_ISSUE_EXPLANATION, COMPONENT_CONTEXT_NAME_ISSUE_CATEGORY,
      COMPONENT_CONTEXT_NAME_ISSUE_PRIORITY, COMPONENT_CONTEXT_NAME_ISSUE_SEVERITY,
      COMPONENT_CONTEXT_NAME_ISSUE_IMPLEMENTATION)

  // Optional Prop Order
  val OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_ID = "OptionalPropBeforeRequired"
  @JvmField
  val OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_DESC = "Props order is not following Litho best practices"
  val OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_EXPLANATION = "Required props should be defined before optional ones"
  val OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_CATEGORY = Category.CORRECTNESS
  val OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_PRIORITY = 5
  val OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_SEVERITY = Severity.WARNING
  val OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_IMPLEMENTATION = Implementation(
      LithoLintIssuesDetector::class.java, Scope.JAVA_FILE_SCOPE)

  @JvmField
  val OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE = Issue.create(OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_ID,
      OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_DESC,
      OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_EXPLANATION,
      OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_CATEGORY,
      OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_PRIORITY,
      OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_SEVERITY,
      OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_IMPLEMENTATION)

  // Possible Resource Type
  val POSSIBLE_RESOURCE_TYPE_ISSUE_ID = "PossibleResourceType"
  @JvmField
  val POSSIBLE_RESOURCE_TYPE_ISSUE_DESC = "Consider using a ResType for parameters representing Android resources"
  val POSSIBLE_RESOURCE_TYPE_ISSUE_EXPLANATION = "Consider using a ResType for parameters representing Android resources"
  val POSSIBLE_RESOURCE_TYPE_ISSUE_CATEGORY = Category.CORRECTNESS
  val POSSIBLE_RESOURCE_TYPE_ISSUE_PRIORITY = 5
  val POSSIBLE_RESOURCE_TYPE_ISSUE_SEVERITY = Severity.INFORMATIONAL
  val POSSIBLE_RESOURCE_TYPE_ISSUE_IMPLEMENTATION = Implementation(
      LithoLintIssuesDetector::class.java, Scope.JAVA_FILE_SCOPE)

  @JvmField
  val POSSIBLE_RESOURCE_TYPE_ISSUE = Issue.create(POSSIBLE_RESOURCE_TYPE_ISSUE_ID,
      POSSIBLE_RESOURCE_TYPE_ISSUE_DESC,
      POSSIBLE_RESOURCE_TYPE_ISSUE_EXPLANATION, POSSIBLE_RESOURCE_TYPE_ISSUE_CATEGORY,
      POSSIBLE_RESOURCE_TYPE_ISSUE_PRIORITY, POSSIBLE_RESOURCE_TYPE_ISSUE_SEVERITY,
      POSSIBLE_RESOURCE_TYPE_ISSUE_IMPLEMENTATION)

  @JvmField
  val DEBUG_ISSUE = Issue.create("Debug Issue", "Debug Issue", "Debug Issue", Category.CORRECTNESS,
      5,
      Severity.WARNING, Implementation(LithoLintIssuesDetector::class.java, Scope.JAVA_FILE_SCOPE))
}
