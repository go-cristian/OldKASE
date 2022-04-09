package dev.cgomez.oldkase.processor

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier.OVERRIDE
import com.squareup.kotlinpoet.TypeSpec
import dev.cgomez.oldkase.annotations.WidgetComponent
import dev.cgomez.oldkase.processor.ProcessorTypes.Companion.BUNDLE
import dev.cgomez.oldkase.processor.ProcessorTypes.Companion.FRAGMENT
import dev.cgomez.oldkase.processor.ProcessorTypes.Companion.LAYOUT_INFLATER
import dev.cgomez.oldkase.processor.ProcessorTypes.Companion.VIEW
import dev.cgomez.oldkase.processor.ProcessorTypes.Companion.VIEW_GROUP
import dev.cgomez.oldkase.processor.ProcessorTypes.Companion.optionsReturn
import java.io.File
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind.METHOD
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(WidgetsProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class WidgetsProcessor : AbstractProcessor() {
  companion object {
    const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
  }

  private val root by lazy { processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty() }

  private fun packageName(element: Element) =
    processingEnv.elementUtils.getPackageOf(element).toString()

  override fun process(
    annotations: MutableSet<out TypeElement>,
    roundEnv: RoundEnvironment
  ): Boolean {

    // An option is a combination of the name on the annotation and the generated Fragment
    // The algorithm loops the annotations and creates a fragment for each annotated component
    // At the end it creates a file with all the options se we can loop on the UI lib demo
    val options = mutableListOf<Pair<String, String>>()
    var packageName = ""

    roundEnv.getElementsAnnotatedWith(WidgetComponent::class.java).forEach { element ->
      if (element.kind != METHOD) {
        return false
      }
      packageName = packageName(element)
      val option = processAnnotation(element)
      options.add(option)
    }

    createOptionsFile(options, packageName)

    return false
  }

  private fun processAnnotation(element: Element): Pair<String, String> {
    val annotiation = element.getAnnotation(WidgetComponent::class.java)
    val componentName = annotiation.name.toCamelCase().capitalize(Locale.ROOT)
    val fragmentName = "${componentName}Fragment"
    createFragmentFile(element, fragmentName)
    return Pair(annotiation.name, fragmentName)
  }

  private fun createFragmentFile(
    element: Element,
    fragmentName: String
  ) {
    val function = FunSpec
      .builder("onCreateView")
      .addModifiers(OVERRIDE)
      .addParameter("inflater", LAYOUT_INFLATER)
      .addParameter("container", VIEW_GROUP)
      .addParameter("savedInstanceState", BUNDLE)
      .returns(VIEW)
      .addStatement("return container?.let{ ${element.simpleName}(container, requireActivity()) }")
      .build()

    val type = TypeSpec
      .classBuilder(fragmentName)
      .superclass(FRAGMENT)
      .addFunction(function)
      .build()

    val file = File(root)
    file.mkdir()

    FileSpec
      .builder(packageName(element), fragmentName)
      .addType(type)
      .build()
      .writeTo(file)
  }

  private fun createOptionsFile(
    options: MutableList<Pair<String, String>>,
    packageName: String
  ) {
    val optionsString = options.joinToString { "\nPair(\"${it.first}\", ${it.second}())" }

    val optionsFunction = FunSpec.builder("allOptions")
      .returns(optionsReturn)
      .addCode("return listOf(\n${optionsString})\n")
      .build()

    val file = File(root)
    file.mkdir()
    FileSpec
      .builder(packageName, "Options")
      .addFunction(optionsFunction)
      .build()
      .writeTo(file)
  }

  override fun getSupportedAnnotationTypes(): MutableSet<String> =
    mutableSetOf(WidgetComponent::class.java.canonicalName)

  private fun String.toCamelCase(): String {
    var s = this
    val tokens = s
      .replace("`|'| |[0-9]".toRegex(), "")
      .split("[\\W_]+|(?<=[a-z])(?=[A-Z][a-z])".toRegex())
      .toTypedArray()
    s = ""
    for (token in tokens) {
      val lowercaseToken = token.toLowerCase(Locale.ROOT)
      s += if (tokens[0] == token) lowercaseToken else lowercaseToken.toUpperCase(
        Locale.ROOT
      )[0].toString() + lowercaseToken.substring(
        1
      )
    }
    return s
  }
}