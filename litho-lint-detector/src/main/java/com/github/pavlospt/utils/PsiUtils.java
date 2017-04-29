package com.github.pavlospt.utils;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiParameterList;

public class PsiUtils {

  public static boolean hasAnnotations(PsiModifierList psiModifierList) {
    if (psiModifierList == null) return false;

    PsiAnnotation[] psiAnnotations = psiModifierList.getAnnotations();

    return psiAnnotations.length > 0;
  }

  public static boolean hasParameters(PsiMethod psiMethod) {
    if (psiMethod == null) return false;

    PsiParameterList psiParameterList = psiMethod.getParameterList();

    return psiParameterList.getParametersCount() > 0;
  }
}
