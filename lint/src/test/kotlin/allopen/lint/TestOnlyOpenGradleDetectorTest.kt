package allopen.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class OpenForTestingGradleDetectorTest : LintDetectorTest() {

    override fun getDetector() = OpenForTestingGradleDetector()
    override fun getIssues() = listOf(
        OpenForTestingGradleDetector.ISSUE
    )

    @Test
    fun testErrors() {
        val result = lintFiles(allOpenForTestingBuildScript())

        assertThat(
            result,
            equalTo(
                """
                |build.gradle:4: Error: Type is not meta annotation [NonMetaAnnotation]
                |    annotation("allopen.annotations.OpenForTesting")
                |    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
                |
                """.trimMargin()
            )
        )
    }

    @Test
    fun testNoWarnings() {
        val result = lintFiles(allOpenBuildScript())

        assertThat(result, equalTo("No warnings."))
    }
}