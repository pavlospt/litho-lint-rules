package com.github.pavlospt.litholintrulessample;

import android.graphics.drawable.Drawable;
import android.text.Layout;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.annotations.LayoutSpec;
import com.facebook.litho.annotations.OnCreateLayout;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.widget.Text;
import com.facebook.yoga.YogaAlign;

import static com.facebook.yoga.YogaEdge.ALL;

/*
* This class is an example class where all LithoLint rules apply.
* */

@LayoutSpec
public class RandomCompSpec {

  @OnCreateLayout
  public static ComponentLayout onCreateLayout(ComponentContext context,
      @Prop(optional = true) String test, @Prop String text, @Prop(optional = true) float ratio,
      @Prop(optional = true) Drawable drawable) {
    return Text.create(context)
        .text(text)
        .textSizeDip(18)
        .textColorRes(android.R.color.white)
        .textSizeSp(18)
        .textAlignment(Layout.Alignment.ALIGN_CENTER)
        .alignSelf(YogaAlign.STRETCH)
        .paddingDip(ALL, 8)
        .buildWithLayout();
  }
}
