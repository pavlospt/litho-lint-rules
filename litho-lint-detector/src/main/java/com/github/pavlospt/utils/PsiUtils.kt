package com.github.pavlospt.utils

import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifierList
import com.intellij.psi.PsiParameterList

object PsiUtils {

  fun hasAnnotations(
      psiModifierList: PsiModifierList?): Boolean = psiModifierList?.annotations?.isNotEmpty() ?: false

  fun hasParameters(
      psiMethod: PsiMethod?): Boolean {
    val psiParameterList: PsiParameterList? = psiMethod?.parameterList
    val parametersCount = psiParameterList?.parametersCount ?: 0
    return parametersCount > 0
  }
}
