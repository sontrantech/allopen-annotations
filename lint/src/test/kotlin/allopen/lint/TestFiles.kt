package allopen.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.TestFile
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.gradle
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin

fun openForTesting(): TestFile = kotlin(
    "java/allopen/annotations/OpenForTesting.kt",
    """
    |package allopen.annotations
    |
    |@Open
    |@Target(AnnotationTarget.CLASS)
    |@Retention(AnnotationRetention.SOURCE)
    |annotation class OpenForTesting
    """.trimMargin()
)

fun pet(): TestFile = kotlin(
    "java/Pet.kt",
    """
    |import allopen.annotations.OpenForTesting
    |
    |@OpenForTesting
    |class Pet
    """.trimMargin()
)

fun tiger(): TestFile = kotlin(
    "java/Tiger.kt",
    """
    |class Tiger : Pet()
    """.trimMargin()
)

fun wolf(): TestFile = java(
    "java/Wolf.java",
    """
    |public class Wolf extends Pet {
    |}
    """.trimMargin()
)

fun allOpenBuildScript(): TestFile = gradle(
    "build.gradle",
    """
    |apply plugin: 'kotlin-allopen'
    |
    |allOpen {
    |    annotation("allopen.annotations.Open")
    |}
    """.trimMargin()
)

fun allOpenForTestingBuildScript(): TestFile = gradle(
    "build.gradle",
    """
    |apply plugin: 'kotlin-allopen'
    |
    |allOpen {
    |    annotation("allopen.annotations.OpenForTesting")
    |}
    """.trimMargin()
)