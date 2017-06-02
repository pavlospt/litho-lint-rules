package com.github.pavlospt.misc

import java.util.ArrayList
import java.util.Arrays
import java.util.Collections

object LithoLintConstants {

  // Class Annotations
  val LAYOUT_SPEC_ANNOTATION = "com.facebook.litho.annotations.LayoutSpec"
  val MOUNT_SPEC_ANNOTATION = "com.facebook.litho.annotations.MountSpec"

  // Parameter Annotations
  val PROP_PARAMETER_ANNOTATION = "com.facebook.litho.annotations.Prop"

  // Method Annotations

  // LayoutSpec - Method Annotations
  val ON_CREATE_LAYOUT_ANNOTATION = "com.facebook.litho.annotations.OnCreateLayout"

  // MountSpec - Method Annotations
  val ON_CREATE_MOUNT_CONTENT_ANNOTATION = "com.facebook.litho.annotations.OnCreateMountContent"
  val ON_MOUNT_ANNOTATION = "com.facebook.litho.annotations.OnMount"
  val ON_MEASURE_ANNOTATION = "com.facebook.litho.annotations.OnMeasure"
  val ON_UNMOUNT_ANNOTATION = "com.facebook.litho.annotations.OnUnmount"
  val ON_BIND_ANNOTATION = "com.facebook.litho.annotations.OnBind"
  val ON_UNBIND_ANNOTATION = "com.facebook.litho.annotations.OnUnbind"

  val MOUNT_SPEC_METHOD_ANNOTATIONS = Collections.unmodifiableList(
      Arrays.asList(ON_CREATE_MOUNT_CONTENT_ANNOTATION, ON_MOUNT_ANNOTATION, ON_MEASURE_ANNOTATION,
          ON_UNMOUNT_ANNOTATION, ON_BIND_ANNOTATION, ON_UNBIND_ANNOTATION))

  val LAYOUT_SPEC_METHOD_ANNOTATIONS = Collections.unmodifiableList(
      Arrays.asList(ON_CREATE_LAYOUT_ANNOTATION))

  val ALL_METHOD_ANNOTATIONS = Collections.unmodifiableList(object : ArrayList<String>() {
    init {
      addAll(LAYOUT_SPEC_METHOD_ANNOTATIONS)
      addAll(MOUNT_SPEC_METHOD_ANNOTATIONS)
    }
  })

  val SUGGESTED_LAYOUT_COMPONENT_SPEC_NAME_FORMAT = "ComponentSpec"
  val COMPONENT_CONTEXT_CLASS_NAME = "com.facebook.litho.ComponentContext"
  val COMPONENT_CONTEXT_DESIRABLE_PARAMETER_NAME = "c"
  val OPTIONAL_PROP_ATTRIBUTE_NAME = "optional"

  val POSSIBLE_RESOURCE_PARAMETER_TYPES = Collections.unmodifiableList(
      object : ArrayList<String>() {
        init {
          add("float")
          add("android.graphics.drawable.Drawable")
        }
      })
}
