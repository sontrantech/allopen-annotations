package allopen.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class OpenForTestingDetectorTest : LintDetectorTest() {

    override fun getDetector() = OpenForTestingDetector()
    override fun getIssues() = listOf(
        OpenForTestingDetector.TYPE_USAGE,
        OpenForTestingDetector.INHERITANCE_USAGE
    )

    @Test
    fun testNoWarnings() {
        val result = lintFiles(
            openForTesting(),
            pet()
        )

        assertThat(result, equalTo("No warnings."))
    }

    @Test
    fun testErrorsInKotlin() {
        val result = lintFiles(
            openForTesting(),
            pet(),
            tiger()
        )

        assertThat(
            result,
            equalTo(
                """
                |java/Tiger.kt:1: Error: This type is open for tests only [InheritingOpenForTestingType]
                |class Tiger : Pet()
                |              ~~~
                |java/Pet.kt:4: Error: This type is open for tests only, still being inherited from [OpenForTestingType]
                |class Pet
                |      ~~~
                |2 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }

    @Test
    fun testErrorsInJava() {
        val result = lintFiles(
            openForTesting(),
            pet(),
            wolf()
        )

        assertThat(
            result,
            equalTo(
                """
                |java/Wolf.java:1: Error: This type is open for tests only [InheritingOpenForTestingType]
                |public class Wolf extends Pet {
                |                          ~~~
                |java/Pet.kt:4: Error: This type is open for tests only, still being inherited from [OpenForTestingType]
                |class Pet
                |      ~~~
                |2 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }

    @Test
    fun testMultipleErrors() {
        val result = lintFiles(
            openForTesting(),
            pet(),
            tiger(),
            wolf()
        )

        assertThat(
            result,
            equalTo(
                """
                |java/Tiger.kt:1: Error: This type is open for tests only [InheritingOpenForTestingType]
                |class Tiger : Pet()
                |              ~~~
                |java/Wolf.java:1: Error: This type is open for tests only [InheritingOpenForTestingType]
                |public class Wolf extends Pet {
                |                          ~~~
                |java/Pet.kt:4: Error: This type is open for tests only, still being inherited from [OpenForTestingType]
                |class Pet
                |      ~~~
                |3 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }
}