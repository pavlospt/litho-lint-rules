package com.github.pavlospt

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.JavaContext
import com.github.pavlospt.misc.IssuesInfo
import com.github.pavlospt.misc.LithoLintConstants
import com.github.pavlospt.utils.PsiUtils
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.JavaRecursiveElementVisitor
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiAnnotationParameterList
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifier


class LithoLintIssuesDetector : Detector(), Detector.UastScanner {

  override fun getApplicablePsiTypes(): List<Class<out PsiElement>> {
    return listOf<Class<out PsiElement>>(PsiClass::class.java)
  }

  override fun createPsiVisitor(context: JavaContext?): JavaElementVisitor {
    return object : JavaElementVisitor() {
      override fun visitClass(node: PsiClass) {
        node.accept(LithoVisitor(context))
      }

      override fun visitMethod(method: PsiMethod) {
        method.accept(LithoVisitor(context))
      }
    }
  }

  internal class LithoVisitor(private val context: JavaContext?) : JavaRecursiveElementVisitor() {

    private val INVALID_POSITION = -1

    override fun visitClass(psiClass: PsiClass) {
      detectAnnotatedClassNameIssue(context, psiClass)
      super.visitClass(psiClass)
    }

    override fun visitMethod(psiMethod: PsiMethod) {
      detectMethodVisibilityIssue(psiMethod)
      detectComponentContextNameIssue(psiMethod)
      detectOptionalPropOrderIssue(psiMethod)
      detectPossibleResourceTypesIssue(psiMethod)
      super.visitMethod(psiMethod)
    }

    // Detect annotated class name issue
    private fun detectAnnotatedClassNameIssue(context: JavaContext?, psiClass: PsiClass) {
      if (!PsiUtils.hasAnnotations(psiClass.modifierList)) return

      val annotations = psiClass.modifierList?.annotations

      annotations?.let {
        it.forEach annotationsLoop@ {
          val worthCheckingClass = LithoLintConstants.LAYOUT_SPEC_ANNOTATION == it.qualifiedName

          val psiClassName = psiClass.name ?: return@annotationsLoop

          val notSuggestedName = psiClassName in LithoLintConstants.SUGGESTED_LAYOUT_COMPONENT_SPEC_NAME_FORMAT

          val shouldReportClass = worthCheckingClass && notSuggestedName

          if (shouldReportClass) {
            context?.report(IssuesInfo.LAYOUT_SPEC_NAME_ISSUE, it,
                context.getLocation(psiClass.nameIdentifier),
                IssuesInfo.LAYOUT_SPEC_CLASS_NAME_ISSUE_DESC)
          }
        }
      }
    }

    // Detect possible resource types issue
    private fun detectPossibleResourceTypesIssue(psiMethod: PsiMethod) {
      if (!worthCheckingMethod(psiMethod)) return

      val parametersList = psiMethod.parameterList
      val parameters = parametersList.parameters

      for (parameter in parameters) {
        if (parameter.type.canonicalText
            !in LithoLintConstants.POSSIBLE_RESOURCE_PARAMETER_TYPES) continue

        val annotations = parameter.modifierList?.annotations

        annotations
            ?.let {
              it.filter {
                LithoLintConstants.PROP_PARAMETER_ANNOTATION == it.qualifiedName
              }.forEach {
                context?.report(IssuesInfo.POSSIBLE_RESOURCE_TYPE_ISSUE, parameter,
                    context.getLocation(parameter), IssuesInfo.POSSIBLE_RESOURCE_TYPE_ISSUE_DESC)
              }
            }
      }
    }

    // Detect wrong props order issue
    private fun detectOptionalPropOrderIssue(psiMethod: PsiMethod) {
      if (!worthCheckingMethod(psiMethod)) return

      val parametersList = psiMethod.parameterList
      val parameters = parametersList.parameters

      var indexOfFirstRequiredProp = INVALID_POSITION
      var indexOfFirstOptionalProp = INVALID_POSITION

      for (i in parameters.indices) {
        val parameter = parameters[i]

        if (!PsiUtils.hasAnnotations(parameter.modifierList)) continue

        val annotations = parameter.modifierList?.annotations

        var propAnnotation: PsiAnnotation? = null

        annotations?.let {
          propAnnotation = it.find {
            LithoLintConstants.PROP_PARAMETER_ANNOTATION == it.qualifiedName
          }
        }

        if (propAnnotation == null) continue

        val psiAnnotationParameterList: PsiAnnotationParameterList? = propAnnotation?.parameterList

        if (psiAnnotationParameterList != null) {
          val psiNameValuePairs = psiAnnotationParameterList.attributes

          if (psiNameValuePairs.isEmpty()) {
            indexOfFirstRequiredProp = assignPositionIfInvalid(indexOfFirstRequiredProp, i)
          } else {
            for (psiNameValuePair in psiNameValuePairs) {
              if (LithoLintConstants.OPTIONAL_PROP_ATTRIBUTE_NAME == psiNameValuePair.name) {
                indexOfFirstOptionalProp = assignPositionIfInvalid(indexOfFirstOptionalProp, i)
              } else {
                indexOfFirstRequiredProp = assignPositionIfInvalid(indexOfFirstRequiredProp, i)
              }
            }
          }
        } else {
          indexOfFirstRequiredProp = assignPositionIfInvalid(indexOfFirstRequiredProp, i)
        }

        if (indexOfFirstOptionalProp != INVALID_POSITION
            && indexOfFirstRequiredProp != INVALID_POSITION
            && indexOfFirstOptionalProp < indexOfFirstRequiredProp) {
          val psiParameter = parameters[indexOfFirstOptionalProp]

          context?.report(IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE, psiParameter,
              context.getLocation(psiParameter),
              IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_DESC)
          break
        }
      }
    }

    // Detect component context name issue
    private fun detectComponentContextNameIssue(psiMethod: PsiMethod) {
      if (!worthCheckingMethod(psiMethod)) return

      val parametersList = psiMethod.parameterList
      val parameters = parametersList.parameters

      for (parameter in parameters) {
        if (parameter.type
            .canonicalText == LithoLintConstants.COMPONENT_CONTEXT_CLASS_NAME) {
          val shouldReportParameter = LithoLintConstants.COMPONENT_CONTEXT_DESIRABLE_PARAMETER_NAME != parameter.name

          if (shouldReportParameter) {
            context?.report(IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_ISSUE, parameter,
                context.getLocation(parameter.nameIdentifier),
                IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_DESC)
          }
        }
      }
    }

    // Detect method visibility issue
    private fun detectMethodVisibilityIssue(psiMethod: PsiMethod) {
      if (!PsiUtils.hasAnnotations(psiMethod.modifierList)) return

      val annotations = psiMethod.modifierList.annotations

      for (annotation in annotations) {
        val worthCheckingMethod = LithoLintConstants.ALL_METHOD_ANNOTATIONS
            .contains(annotation.qualifiedName)

        val notSuggestedVisibility = !psiMethod.modifierList.hasModifierProperty(
            PsiModifier.PACKAGE_LOCAL) || !psiMethod.modifierList.hasExplicitModifier(
            PsiModifier.STATIC)

        val shouldReportMethod = worthCheckingMethod && notSuggestedVisibility

        if (shouldReportMethod) {
          context?.report(IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE, psiMethod,
              context.getLocation(psiMethod.nameIdentifier),
              IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE_DESC)
        }
      }
    }

    // Utility method
    private fun worthCheckingMethod(psiMethod: PsiMethod): Boolean {
      if (!PsiUtils.hasAnnotations(psiMethod.modifierList)) return false

      val annotations = psiMethod.modifierList.annotations

      var worthCheckingMethod = false

      for (annotation in annotations) {
        worthCheckingMethod = LithoLintConstants.ALL_METHOD_ANNOTATIONS
            .contains(annotation.qualifiedName)

        if (worthCheckingMethod) break
      }

      return worthCheckingMethod && PsiUtils.hasParameters(psiMethod)
    }

    // Utility method
    private fun assignPositionIfInvalid(assignable: Int, position: Int): Int {
      return if (assignable == INVALID_POSITION) position else assignable
    }
  }
}
