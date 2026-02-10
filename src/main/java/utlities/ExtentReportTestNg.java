package utlities;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportTestNg implements IReporter {

    private ExtentReports extent;
    private ExtentHtmlReporter htmlReporter;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites,
                               List<ISuite> suites,
                               String outputDirectory) {

        // Report location
        htmlReporter = new ExtentHtmlReporter(
                System.getProperty("user.dir") + "/Reports/ExtentReport.html");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Loop through suites
        for (ISuite suite : suites) {

            Map<String, ISuiteResult> suiteResults = suite.getResults();

            for (ISuiteResult suiteResult : suiteResults.values()) {

                ITestContext context = suiteResult.getTestContext();

                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }

        extent.flush();
    }

    private void buildTestNodes(IResultMap tests, Status status) {

        ExtentTest test;

        if (tests.size() > 0) {

            for (ITestResult result : tests.getAllResults()) {

                test = extent.createTest(
                        result.getMethod().getMethodName(),
                        result.getMethod().getDescription()
                );

                // Assign groups
                for (String group : result.getMethod().getGroups()) {
                    test.assignCategory(group);
                }

                // Set start & end time
                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));

                // Log based on test status
                if (status == Status.FAIL) {
                    test.log(Status.FAIL, "Test Failed");
                    test.fail(result.getThrowable()); // 🔥 full stack trace
                }
                else if (status == Status.SKIP) {
                    test.log(Status.SKIP, "Test Skipped");
                    if (result.getThrowable() != null) {
                        test.skip(result.getThrowable());
                    }
                }
                else {
                    test.log(Status.PASS, "Test Passed");
                }
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
