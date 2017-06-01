package com.github.pavlospt;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.JavaContext;
import com.github.pavlospt.misc.IssuesInfo;
import com.github.pavlospt.misc.LithoLintConstants;
import com.github.pavlospt.utils.PsiUtils;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationParameterList;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LithoLintIssuesDetector extends Detector implements Detector.JavaPsiScanner {

  private static final int INVALID_POSITION = -1;

  public LithoLintIssuesDetector() {
  }

  @Override
  public List<Class<? extends PsiElement>> getApplicablePsiTypes() {
    return Collections.<Class<? extends PsiElement>>singletonList(PsiClass.class);
  }

  @Override
  public JavaElementVisitor createPsiVisitor(final JavaContext context) {
    return new JavaElementVisitor() {
      @Override
      public void visitClass(PsiClass node) {
        node.accept(new LithoVisitor(context));
      }

      @Override
      public void visitMethod(PsiMethod method) {
        method.accept(new LithoVisitor(context));
      }
    };
  }

  private static class LithoVisitor extends JavaRecursiveElementVisitor {

    private final JavaContext context;

    // Detect annotated class name issue
    private static void detectAnnotatedClassNameIssue(JavaContext context, PsiClass psiClass) {
      if (!PsiUtils.INSTANCE.hasAnnotations(psiClass.getModifierList())) return;

      PsiAnnotation[] annotations = psiClass.getModifierList().getAnnotations();

      for (PsiAnnotation annotation : annotations) {
        boolean worthCheckingClass = LithoLintConstants.INSTANCE.getLAYOUT_SPEC_ANNOTATION()
            .equals(annotation.getQualifiedName());

        String psiClassName = psiClass.getName();

        if (psiClassName == null) continue;

        boolean notSuggestedName = !psiClass.getName()
            .contains(LithoLintConstants.INSTANCE.getSUGGESTED_LAYOUT_COMPONENT_SPEC_NAME_FORMAT());

        boolean shouldReportClass = worthCheckingClass && notSuggestedName;

        if (shouldReportClass) {
          context.report(IssuesInfo.LAYOUT_SPEC_NAME_ISSUE, annotation,
              context.getLocation(psiClass.getNameIdentifier()),
              IssuesInfo.LAYOUT_SPEC_CLASS_NAME_ISSUE_DESC);
        }
      }
    }

    LithoVisitor(JavaContext context) {
      this.context = context;
    }

    @Override
    public void visitClass(PsiClass psiClass) {
      detectAnnotatedClassNameIssue(context, psiClass);
      super.visitClass(psiClass);
    }

    @Override
    public void visitMethod(PsiMethod psiMethod) {
      detectMethodVisibilityIssue(psiMethod);
      detectComponentContextNameIssue(psiMethod);
      detectOptionalPropOrderIssue(psiMethod);
      detectPossibleResourceTypesIssue(psiMethod);
      super.visitMethod(psiMethod);
    }

    // Detect possible resource types issue
    private void detectPossibleResourceTypesIssue(PsiMethod psiMethod) {
      if (!worthCheckingMethod(psiMethod)) return;

      PsiParameterList parametersList = psiMethod.getParameterList();
      PsiParameter[] parameters = parametersList.getParameters();

      for (PsiParameter parameter : parameters) {

        PsiAnnotation[] annotations = parameter.getModifierList().getAnnotations();

        for (PsiAnnotation annotation : annotations) {

          if (LithoLintConstants.INSTANCE.getPROP_PARAMETER_ANNOTATION()
              .equals(annotation.getQualifiedName())) {

            if (LithoLintConstants.INSTANCE.getPOSSIBLE_RESOURCE_PARAMETER_TYPES()
                .contains(parameter.getType().getCanonicalText())) {
              context.report(IssuesInfo.POSSIBLE_RESOURCE_TYPE_ISSUE, parameter,
                  context.getLocation(parameter), IssuesInfo.POSSIBLE_RESOURCE_TYPE_ISSUE_DESC);
            }
          }
        }
      }
    }

    // Detect wrong props order issue
    private void detectOptionalPropOrderIssue(PsiMethod psiMethod) {
      if (!worthCheckingMethod(psiMethod)) return;

      PsiParameterList parametersList = psiMethod.getParameterList();
      PsiParameter[] parameters = parametersList.getParameters();

      int indexOfFirstRequiredProp = INVALID_POSITION;
      int indexOfFirstOptionalProp = INVALID_POSITION;

      for (int i = 0; i < parameters.length; i++) {
        PsiParameter parameter = parameters[i];

        if (!PsiUtils.INSTANCE.hasAnnotations(parameter.getModifierList())) continue;

        PsiAnnotation[] annotations = parameter.getModifierList().getAnnotations();

        PsiAnnotation propAnnotation = null;

        for (PsiAnnotation annotation : annotations) {
          if (LithoLintConstants.INSTANCE.getPROP_PARAMETER_ANNOTATION()
              .equals(annotation.getQualifiedName())) {
            propAnnotation = annotation;
            break;
          }
        }

        if (propAnnotation == null) continue;

        PsiAnnotationParameterList psiAnnotationParameterList = propAnnotation.getParameterList();

        if (psiAnnotationParameterList != null) {
          PsiNameValuePair[] psiNameValuePairs = psiAnnotationParameterList.getAttributes();

          if (psiNameValuePairs.length == 0) {
            indexOfFirstRequiredProp = assignPositionIfInvalid(indexOfFirstRequiredProp, i);
          } else {
            for (PsiNameValuePair psiNameValuePair : psiNameValuePairs) {
              if (LithoLintConstants.INSTANCE.getOPTIONAL_PROP_ATTRIBUTE_NAME()
                  .equals(psiNameValuePair.getName())) {
                indexOfFirstOptionalProp = assignPositionIfInvalid(indexOfFirstOptionalProp, i);
              } else {
                indexOfFirstRequiredProp = assignPositionIfInvalid(indexOfFirstRequiredProp, i);
              }
            }
          }
        } else {
          indexOfFirstRequiredProp = assignPositionIfInvalid(indexOfFirstRequiredProp, i);
        }

        if ((indexOfFirstOptionalProp != INVALID_POSITION
            && indexOfFirstRequiredProp != INVALID_POSITION)
            && indexOfFirstOptionalProp < indexOfFirstRequiredProp) {
          PsiParameter psiParameter = parameters[indexOfFirstOptionalProp];

          context.report(IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE, psiParameter,
              context.getLocation(psiParameter),
              IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE_DESC);
          break;
        }
      }
    }

    // Detect component context name issue
    private void detectComponentContextNameIssue(PsiMethod psiMethod) {
      if (!worthCheckingMethod(psiMethod)) return;

      PsiParameterList parametersList = psiMethod.getParameterList();
      PsiParameter[] parameters = parametersList.getParameters();

      for (PsiParameter parameter : parameters) {
        if (parameter.getType()
            .getCanonicalText()
            .equals(LithoLintConstants.INSTANCE.getCOMPONENT_CONTEXT_CLASS_NAME())) {
          boolean shouldReportParameter =
              !LithoLintConstants.INSTANCE.getCOMPONENT_CONTEXT_DESIRABLE_PARAMETER_NAME()
                  .equals(parameter.getName());

          if (shouldReportParameter) {
            context.report(IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_ISSUE, parameter,
                context.getLocation(parameter.getNameIdentifier()),
                IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_DESC);
          }
        }
      }
    }

    // Detect method visibility issue
    private void detectMethodVisibilityIssue(PsiMethod psiMethod) {
      if (!PsiUtils.INSTANCE.hasAnnotations(psiMethod.getModifierList())) return;

      PsiAnnotation[] annotations = psiMethod.getModifierList().getAnnotations();

      for (PsiAnnotation annotation : annotations) {
        boolean worthCheckingMethod = LithoLintConstants.INSTANCE.getALL_METHOD_ANNOTATIONS()
            .contains(annotation.getQualifiedName());

        boolean notSuggestedVisibility =
            !psiMethod.getModifierList().hasModifierProperty(PsiModifier.PACKAGE_LOCAL)
                || !psiMethod.getModifierList().hasExplicitModifier(PsiModifier.STATIC);

        boolean shouldReportMethod = worthCheckingMethod && notSuggestedVisibility;

        if (shouldReportMethod) {
          context.report(IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE, psiMethod,
              context.getLocation(psiMethod.getNameIdentifier()),
              IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE_DESC);
        }
      }
    }

    // Utility method
    private boolean worthCheckingMethod(PsiMethod psiMethod) {
      if (!PsiUtils.INSTANCE.hasAnnotations(psiMethod.getModifierList())) return false;

      PsiAnnotation[] annotations = psiMethod.getModifierList().getAnnotations();

      boolean worthCheckingMethod = false;

      for (PsiAnnotation annotation : annotations) {
        worthCheckingMethod = LithoLintConstants.INSTANCE.getALL_METHOD_ANNOTATIONS()
            .contains(annotation.getQualifiedName());

        if (worthCheckingMethod) break;
      }

      return worthCheckingMethod && PsiUtils.INSTANCE.hasParameters(psiMethod);
    }

    // Utility method
    private int assignPositionIfInvalid(int assignable, int position) {
      return assignable == INVALID_POSITION ? position : assignable;
    }
  }
}
