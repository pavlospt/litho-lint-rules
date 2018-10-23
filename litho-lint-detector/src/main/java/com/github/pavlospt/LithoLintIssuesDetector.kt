package com.github.pavlospt

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.LintFix
import com.github.pavlospt.misc.IssuesInfo
import com.github.pavlospt.misc.LithoLintConstants
import com.github.pavlospt.utils.PsiUtils
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiModifier
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UClass
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UMethod
import org.jetbrains.uast.UNamedExpression
import org.jetbrains.uast.visitor.AbstractUastVisitor


class LithoLintIssuesDetector : Detector(), Detector.UastScanner {

  override fun getApplicableUastTypes(): List<Class<out UElement>> {
    return listOf<Class<out UElement>>(UClass::class.java)
  }

  override fun createUastHandler(context: JavaContext): UElementHandler? {
    return object : UElementHandler() {
      override fun visitClass(node: UClass) {
        node.accept(LithoVisitor(context))
      }

      override fun visitMethod(node: UMethod) {
        node.accept(LithoVisitor(context))
      }
    }
  }

  internal class LithoVisitor(private val context: JavaContext?) : AbstractUastVisitor() {

    private val INVALID_POSITION = -1

    override fun visitClass(node: UClass): Boolean {
      detectAnnotatedClassNameIssue(context, node)
      return super.visitClass(node)
    }

    override fun visitMethod(node: UMethod): Boolean {
      detectMethodVisibilityIssue(node)
      detectComponentContextNameIssue(node)
      detectOptionalPropOrderIssue(node)
      detectPossibleResourceTypesIssue(node)
      return super.visitMethod(node)
    }

    // Detect annotated class name issue
    private fun detectAnnotatedClassNameIssue(context: JavaContext?, uClass: UClass) {
      if (!PsiUtils.hasAnnotations(uClass.modifierList)) return

      val annotations = uClass.modifierList?.annotations ?: return

      annotations.forEach {
        val worthCheckingClass = LithoLintConstants.LAYOUT_SPEC_ANNOTATION == it.qualifiedName

        val psiClassName = uClass.name ?: return@forEach

        val notSuggestedName = LithoLintConstants.SUGGESTED_LAYOUT_COMPONENT_SPEC_NAME_FORMAT !in psiClassName

        val shouldReportClass = worthCheckingClass && notSuggestedName

        if (shouldReportClass) {
          context?.report(IssuesInfo.LAYOUT_SPEC_NAME_ISSUE, it,
              context.getLocation(uClass.psi.nameIdentifier as PsiElement),
              IssuesInfo.LAYOUT_SPEC_CLASS_NAME_ISSUE_DESC)
        }
      }
    }

    // Detect possible resource types issue
    private fun detectPossibleResourceTypesIssue(uMethod: UMethod) {
      if (!worthCheckingMethod(uMethod)) return

      val parametersList = uMethod.uastParameters

      for (parameter in parametersList) {
        if (parameter.type.canonicalText
            !in LithoLintConstants.POSSIBLE_RESOURCE_PARAMETER_TYPES) continue

        val annotations = parameter.annotations

        annotations.filter {
          LithoLintConstants.PROP_PARAMETER_ANNOTATION == it.qualifiedName
        }.forEach {
          context?.report(IssuesInfo.POSSIBLE_RESOURCE_TYPE_ISSUE, parameter as UElement,
              context.getLocation(parameter as UElement),
              IssuesInfo.POSSIBLE_RESOURCE_TYPE_ISSUE_DESC)
        }
      }
    }

    // Detect wrong props order issue
    private fun detectOptionalPropOrderIssue(uMethod: UMethod) {
      if (!worthCheckingMethod(uMethod)) return

      val parametersList = uMethod.uastParameters

      var indexOfFirstRequiredProp = INVALID_POSITION
      var indexOfFirstOptionalProp = INVALID_POSITION

      for (i in parametersList.indices) {
        val parameter = parametersList[i]

        if (!PsiUtils.hasAnnotations(parameter.modifierList)) continue

        val annotations = parameter.annotations

        val propAnnotation: UAnnotation = annotations.find {
          LithoLintConstants.PROP_PARAMETER_ANNOTATION == it.qualifiedName
        } ?: continue

        val annotationParameters: List<UNamedExpression> = propAnnotation.attributeValues

        if (annotationParameters.isEmpty()) {
          indexOfFirstRequiredProp = assignPositionIfInvalid(indexOfFirstRequiredProp, i)
        } else {
          for (psiNameValuePair in annotationParameters) {
            if (LithoLintConstants.OPTIONAL_PROP_ATTRIBUTE_NAME == psiNameValuePair.name) {
              indexOfFirstOptionalProp = assignPositionIfInvalid(indexOfFirstOptionalProp, i)
            } else {
              indexOfFirstRequiredProp = assignPositionIfInvalid(indexOfFirstRequiredProp, i)
            }
          }
        }

        if (indexOfFirstOptionalProp != INVALID_POSITION
            && indexOfFirstRequiredProp != INVALID_POSITION
            && indexOfFirstOptionalProp < indexOfFirstRequiredProp) {
          val psiParameter = parametersList[indexOfFirstOptionalProp]

          context?.report(IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE, psiParameter as UElement,
              context.getLocation(psiParameter.psi.nameIdentifier as PsiElement),
              IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_DESC)
          break
        }
      }
    }

    // Detect component context name issue
    private fun detectComponentContextNameIssue(uMethod: UMethod) {
      if (!worthCheckingMethod(uMethod)) return

      val parameters = uMethod.uastParameters

      for (parameter in parameters) {
        if (parameter.type
                .canonicalText == LithoLintConstants.COMPONENT_CONTEXT_CLASS_NAME) {
          val shouldReportParameter = LithoLintConstants.COMPONENT_CONTEXT_DESIRABLE_PARAMETER_NAME != parameter.name

          if (shouldReportParameter) {
            val lintFix = LintFix.create().replace().text(parameter.name).with("c").build()
            context?.report(IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_ISSUE, parameter as UElement,
                context.getLocation(parameter.psi.nameIdentifier as PsiElement),
                IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_DESC, lintFix)
          }
        }
      }
    }

    // Detect method visibility issue
    private fun detectMethodVisibilityIssue(uMethod: UMethod) {
      if (!PsiUtils.hasAnnotations(uMethod.modifierList)) return

      val annotations = uMethod.annotations

      for (annotation in annotations) {
        val worthCheckingMethod = LithoLintConstants.ALL_METHOD_ANNOTATIONS
            .contains(annotation.qualifiedName)

        val notSuggestedVisibility = !uMethod.modifierList.hasModifierProperty(
            PsiModifier.PACKAGE_LOCAL)
        val missesStaticModifier = !uMethod.modifierList.hasExplicitModifier(PsiModifier.STATIC)

        val shouldReportMethodVisibility = worthCheckingMethod && notSuggestedVisibility
        val shouldReportMissingStaticModifier = worthCheckingMethod && missesStaticModifier

        if (shouldReportMethodVisibility) {
          context?.report(IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE, uMethod as UElement,
              context.getLocation(uMethod.psi.nameIdentifier as PsiElement),
              IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE_DESC)
        }

        if (shouldReportMissingStaticModifier) {
          context?.report(IssuesInfo.MISSING_STATIC_MODIFIER_ISSUE, uMethod as UElement,
              context.getLocation(uMethod.psi.nameIdentifier as PsiElement),
              IssuesInfo.MISSING_STATIC_MODIFIER_ISSUE_DESC)
        }
      }
    }

    // Utility method
    private fun worthCheckingMethod(uMethod: UMethod): Boolean {
      if (!PsiUtils.hasAnnotations(uMethod.modifierList)) return false

      val annotations = uMethod.annotations

      var worthCheckingMethod = false

      for (annotation in annotations) {
        worthCheckingMethod = LithoLintConstants.ALL_METHOD_ANNOTATIONS
            .contains(annotation.qualifiedName)

        if (worthCheckingMethod) break
      }

      return worthCheckingMethod && PsiUtils.hasParameters(uMethod)
    }

    // Utility method
    private fun assignPositionIfInvalid(assignable: Int, position: Int): Int {
      return if (assignable == INVALID_POSITION) position else assignable
    }
  }
}
