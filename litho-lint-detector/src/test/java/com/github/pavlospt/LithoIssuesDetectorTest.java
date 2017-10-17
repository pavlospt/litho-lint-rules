package com.github.pavlospt;

import com.android.tools.lint.checks.infrastructure.TestFile;
import com.android.tools.lint.detector.api.Severity;
import com.github.pavlospt.misc.IssuesInfo;
import org.junit.Test;

import static com.android.tools.lint.checks.infrastructure.TestFiles.java;
import static com.android.tools.lint.checks.infrastructure.TestLintTask.lint;

public class LithoIssuesDetectorTest {

  private static final TestFile LITHO_COMPONENT = java(""
      + "package com.github.pavlospt.litholintrulessample;\n"
      + "\n"
      + "import android.graphics.drawable.Drawable;\n"
      + "import android.text.Layout;\n"
      + "import com.facebook.litho.ComponentContext;\n"
      + "import com.facebook.litho.ComponentLayout;\n"
      + "import com.facebook.litho.annotations.LayoutSpec;\n"
      + "import com.facebook.litho.annotations.OnCreateLayout;\n"
      + "import com.facebook.litho.annotations.Prop;\n"
      + "import com.facebook.litho.widget.Text;\n"
      + "import com.facebook.yoga.YogaAlign;\n"
      + "\n"
      + "import static com.facebook.yoga.YogaEdge.ALL;\n"
      + "\n"
      + "/*\n"
      + "* This class is an example class where all LithoLint rules apply.\n"
      + "* */\n"
      + "\n"
      + "@LayoutSpec\n"
      + "public class RandomCompSpec {\n"
      + "\n"
      + "  @OnCreateLayout\n"
      + "  public static ComponentLayout onCreateLayout(ComponentContext context,\n"
      + "      @Prop(optional = true) String test, @Prop String text, @Prop(optional = true) float ratio,\n"
      + "      @Prop(optional = true) Drawable drawable) {\n"
      + "    return Text.create(context)\n"
      + "        .text(text)\n"
      + "        .textSizeDip(18)\n"
      + "        .textColorRes(android.R.color.white)\n"
      + "        .textSizeSp(18)\n"
      + "        .textAlignment(Layout.Alignment.ALIGN_CENTER)\n"
      + "        .alignSelf(YogaAlign.STRETCH)\n"
      + "        .paddingDip(ALL, 8)\n"
      + "        .buildWithLayout();\n"
      + "  }\n"
      + "}\n");

  @Test
  public void testAllRulesApply() {
    lint().files(LITHO_COMPONENT)
        .issues(IssuesInfo.LAYOUT_SPEC_NAME_ISSUE, IssuesInfo.ANNOTATED_METHOD_VISIBILITY_ISSUE,
            IssuesInfo.POSSIBLE_RESOURCE_TYPE_ISSUE, IssuesInfo.COMPONENT_CONTEXT_NAME_ISSUE_ISSUE,
            IssuesInfo.OPTIONAL_PROP_BEFORE_REQUIRED_ISSUE)
        .allowCompilationErrors()
        .run()
        .expectCount(5, Severity.WARNING, Severity.INFORMATIONAL);
  }
}
