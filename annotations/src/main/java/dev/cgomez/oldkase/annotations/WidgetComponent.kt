package dev.cgomez.oldkase.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(
  AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
annotation class WidgetComponent(val name: String)
