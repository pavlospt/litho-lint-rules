package com.github.pavlospt.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LithoLintConstants {

  // Class Annotations
  public static final String LAYOUT_SPEC_ANNOTATION = "com.facebook.litho.annotations.LayoutSpec";
  public static final String MOUNT_SPEC_ANNOTATION = "com.facebook.litho.annotations.MountSpec";

  // Parameter Annotations
  public static final String PROP_PARAMETER_ANNOTATION = "com.facebook.litho.annotations.Prop";

  // Method Annotations

  // LayoutSpec - Method Annotations
  public static final String ON_CREATE_LAYOUT_ANNOTATION =
      "com.facebook.litho.annotations.OnCreateLayout";

  // MountSpec - Method Annotations
  public static final String ON_CREATE_MOUNT_CONTENT_ANNOTATION =
      "com.facebook.litho.annotations.OnCreateMountContent";
  public static final String ON_MOUNT_ANNOTATION = "com.facebook.litho.annotations.OnMount";
  public static final String ON_MEASURE_ANNOTATION = "com.facebook.litho.annotations.OnMeasure";
  public static final String ON_UNMOUNT_ANNOTATION = "com.facebook.litho.annotations.OnUnmount";
  public static final String ON_BIND_ANNOTATION = "com.facebook.litho.annotations.OnBind";
  public static final String ON_UNBIND_ANNOTATION = "com.facebook.litho.annotations.OnUnbind";

  public static final List<String> MOUNT_SPEC_METHOD_ANNOTATIONS = Collections.unmodifiableList(
      Arrays.asList(ON_CREATE_MOUNT_CONTENT_ANNOTATION, ON_MOUNT_ANNOTATION, ON_MEASURE_ANNOTATION,
          ON_UNMOUNT_ANNOTATION, ON_BIND_ANNOTATION, ON_UNBIND_ANNOTATION));

  public static final List<String> LAYOUT_SPEC_METHOD_ANNOTATIONS =
      Collections.unmodifiableList(Arrays.asList(ON_CREATE_LAYOUT_ANNOTATION));

  public static final List<String> ALL_METHOD_ANNOTATIONS =
      Collections.unmodifiableList(new ArrayList<String>() {
        {
          addAll(LAYOUT_SPEC_METHOD_ANNOTATIONS);
          addAll(MOUNT_SPEC_METHOD_ANNOTATIONS);
        }
      });

  public static final String SUGGESTED_LAYOUT_COMPONENT_SPEC_NAME_FORMAT = "ComponentSpec";
  public static final String COMPONENT_CONTEXT_CLASS_NAME = "com.facebook.litho.ComponentContext";
  public static final String COMPONENT_CONTEXT_DESIRABLE_PARAMETER_NAME = "c";
  public static final String OPTIONAL_PROP_ATTRIBUTE_NAME = "optional";

  public static final List<String> POSSIBLE_RESOURCE_PARAMETER_TYPES =
      Collections.unmodifiableList(new ArrayList<String>() {{
        add("float");
        add("android.graphics.drawable.Drawable");
      }});
}
