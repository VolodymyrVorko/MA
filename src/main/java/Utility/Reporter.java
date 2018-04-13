//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.collections.Lists;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;

public class Reporter implements IReporter {
    private static final Logger LOG = Logger.getLogger(Reporter.class);
    protected PrintWriter writer;
    protected final List<Reporter.SuiteResult> suiteResults = Lists.newArrayList();
    Date date = new Date();
    private final StringBuilder buffer = new StringBuilder();
    private String fileName = "Test-report" + new SimpleDateFormat("yyyy-MM-dd__HH_mm").format(date) + ".html";
    private static final String JVM_ARG = "emailable.report2.name";

    public Reporter() {
    }

    public void setFileName(String var1) {
        this.fileName = var1;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void generateReport(List<XmlSuite> var1, List<ISuite> var2, String var3) {
        try {
            this.writer = this.createWriter(var3);
        } catch (IOException var6) {
            LOG.error("Unable to create output file", var6);
            return;
        }

        Iterator var4 = var2.iterator();

        while(var4.hasNext()) {
            ISuite var5 = (ISuite)var4.next();
            this.suiteResults.add(new Reporter.SuiteResult(var5));
        }

        this.writeDocumentStart();
        this.writeHead();
        this.writeBody();
        this.writeDocumentEnd();
        this.writer.close();
    }

    protected PrintWriter createWriter(String var1) throws IOException {
        (new File(var1)).mkdirs();
        String var2 = System.getProperty("emailable.report2.name");
        if (var2 != null && !var2.trim().isEmpty()) {
            this.fileName = var2;
        }

        return new PrintWriter(Files.newBufferedWriter((new File(var1, this.fileName)).toPath(), StandardCharsets.UTF_8));
    }

    protected void writeDocumentStart() {
        this.writer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        this.writer.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
    }

    protected void writeHead() {
        this.writer.println("<head>");
        this.writer.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>");
        this.writer.println("<title>TestNG Report</title>");
        this.writeStylesheet();
        this.writer.println("</head>");
    }

    protected void writeStylesheet() {
        this.writer.print("<style type=\"text/css\">");
        this.writer.print("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
        this.writer.print("th,td {border:1px solid #009;padding:.25em .5em}");
        this.writer.print("th {vertical-align:bottom}");
        this.writer.print("td {vertical-align:top}");
        this.writer.print("table a {font-weight:bold}");
        this.writer.print(".stripe td {background-color: #E6EBF9}");
        this.writer.print(".num {text-align:right}");
        this.writer.print(".passedodd td {background-color: #3F3}");
        this.writer.print(".passedeven td {background-color: #0A0}");
        this.writer.print(".skippedodd td {background-color: #DDD}");
        this.writer.print(".skippedeven td {background-color: #CCC}");
        this.writer.print(".failedodd td,.attn {background-color: #F33}");
        this.writer.print(".failedeven td,.stripe .attn {background-color: #D00}");
        this.writer.print(".stacktrace {white-space:pre;font-family:monospace}");
        this.writer.print(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
        this.writer.print(".invisible {display:none}");
        this.writer.println("</style>");
    }

    protected void writeBody() {
        this.writer.println("<body>");
        this.writeSuiteSummary();
        this.writeScenarioSummary();
        this.writeScenarioDetails();
        this.writer.println("</body>");
    }

    protected void writeDocumentEnd() {
        this.writer.println("</html>");
    }

    protected void writeSuiteSummary() {
        NumberFormat var1 = NumberFormat.getIntegerInstance();
        NumberFormat var2 = NumberFormat.getNumberInstance();
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        long var6 = 0L;
        this.writer.println("<table>");
        this.writer.print("<tr>");
        this.writer.print("<th>Test</th>");
        this.writer.print("<th># Passed</th>");
        this.writer.print("<th># Skipped</th>");
        this.writer.print("<th># Failed</th>");
        this.writer.print("<th>Time (ms)</th>");
        this.writer.print("<th>Included Groups</th>");
        this.writer.print("<th>Excluded Groups</th>");
        this.writer.println("</tr>");
        int var8 = 0;
        Iterator var9 = this.suiteResults.iterator();

        while(var9.hasNext()) {
            Reporter.SuiteResult var10 = (Reporter.SuiteResult)var9.next();
            this.writer.print("<tr><th colspan=\"7\">");
            this.writer.print(Utils.escapeHtml(var10.getSuiteName()));
            this.writer.println("</th></tr>");

            for(Iterator var11 = var10.getTestResults().iterator(); var11.hasNext(); ++var8) {
                Reporter.TestResult var12 = (Reporter.TestResult)var11.next();
                int var13 = var12.getPassedTestCount();
                int var14 = var12.getSkippedTestCount();
                int var15 = var12.getFailedTestCount();
                long var16 = var12.getDuration();
                this.writer.print("<tr");
                if (var8 % 2 == 1) {
                    this.writer.print(" class=\"stripe\"");
                }

                this.writer.print(">");
                this.buffer.setLength(0);
                this.writeTableData(this.buffer.append("<a href=\"#t").append(var8).append("\">").append(Utils.escapeHtml(var12.getTestName())).append("</a>").toString());
                this.writeTableData(var1.format((long)var13), "num");
                this.writeTableData(var1.format((long)var14), var14 > 0 ? "num attn" : "num");
                this.writeTableData(var1.format((long)var15), var15 > 0 ? "num attn" : "num");
                this.writeTableData(var2.format(var16), "num");
                this.writeTableData(var12.getIncludedGroups());
                this.writeTableData(var12.getExcludedGroups());
                this.writer.println("</tr>");
                var3 += var13;
                var4 += var14;
                var5 += var15;
                var6 += var16;
            }
        }

        if (var8 > 1) {
            this.writer.print("<tr>");
            this.writer.print("<th>Total</th>");
            this.writeTableHeader(var1.format((long)var3), "num");
            this.writeTableHeader(var1.format((long)var4), var4 > 0 ? "num attn" : "num");
            this.writeTableHeader(var1.format((long)var5), var5 > 0 ? "num attn" : "num");
            this.writeTableHeader(var2.format(var6), "num");
            this.writer.print("<th colspan=\"2\"></th>");
            this.writer.println("</tr>");
        }

        this.writer.println("</table>");
    }

    protected void writeScenarioSummary() {
        this.writer.print("<table id='summary'>");
        this.writer.print("<thead>");
        this.writer.print("<tr>");
        this.writer.print("<th>Class</th>");
        this.writer.print("<th>Method</th>");
        this.writer.print("<th>Start</th>");
        this.writer.print("<th>Time (ms)</th>");
        this.writer.print("</tr>");
        this.writer.print("</thead>");
        int var1 = 0;
        int var2 = 0;
        Iterator var3 = this.suiteResults.iterator();

        while(var3.hasNext()) {
            Reporter.SuiteResult var4 = (Reporter.SuiteResult)var3.next();
            this.writer.print("<tbody><tr><th colspan=\"4\">");
            this.writer.print(Utils.escapeHtml(var4.getSuiteName()));
            this.writer.print("</th></tr></tbody>");

            for(Iterator var5 = var4.getTestResults().iterator(); var5.hasNext(); ++var1) {
                Reporter.TestResult var6 = (Reporter.TestResult)var5.next();
                this.writer.printf("<tbody id=\"t%d\">", var1);
                String var7 = Utils.escapeHtml(var6.getTestName());
                int var8 = var2;
                var2 += this.writeScenarioSummary(var7 + " &#8212; failed (configuration methods)", var6.getFailedConfigurationResults(), "failed", var2);
                var2 += this.writeScenarioSummary(var7 + " &#8212; failed", var6.getFailedTestResults(), "failed", var2);
                var2 += this.writeScenarioSummary(var7 + " &#8212; skipped (configuration methods)", var6.getSkippedConfigurationResults(), "skipped", var2);
                var2 += this.writeScenarioSummary(var7 + " &#8212; skipped", var6.getSkippedTestResults(), "skipped", var2);
                var2 += this.writeScenarioSummary(var7 + " &#8212; passed", var6.getPassedTestResults(), "passed", var2);
                if (var2 == var8) {
                    this.writer.print("<tr><th colspan=\"4\" class=\"invisible\"/></tr>");
                }

                this.writer.println("</tbody>");
            }
        }

        this.writer.println("</table>");
    }

    private int writeScenarioSummary(String var1, List<Reporter.ClassResult> var2, String var3, int var4) {
        int var5 = 0;
        if (!var2.isEmpty()) {
            this.writer.print("<tr><th colspan=\"4\">");
            this.writer.print(var1);
            this.writer.print("</th></tr>");
            int var6 = var4;
            int var7 = 0;

            for(Iterator var8 = var2.iterator(); var8.hasNext(); ++var7) {
                Reporter.ClassResult var9 = (Reporter.ClassResult)var8.next();
                String var10 = var3 + (var7 % 2 == 0 ? "even" : "odd");
                this.buffer.setLength(0);
                int var11 = 0;
                int var12 = 0;

                for(Iterator var13 = var9.getMethodResults().iterator(); var13.hasNext(); ++var12) {
                    Reporter.MethodResult var14 = (Reporter.MethodResult)var13.next();
                    List var15 = var14.getResults();
                    int var16 = var15.size();

                    assert var16 > 0;

                    ITestResult var17 = (ITestResult)var15.iterator().next();
                    String var18 = Utils.escapeHtml(var17.getMethod().getMethodName());
                    long var19 = var17.getStartMillis();
                    long var21 = var17.getEndMillis() - var19;
                    if (var12 > 0) {
                        this.buffer.append("<tr class=\"").append(var10).append("\">");
                    }

                    this.buffer.append("<td><a href=\"#m").append(var6).append("\">").append(var18).append("</a></td>").append("<td rowspan=\"").append(var16).append("\">").append(var19).append("</td>").append("<td rowspan=\"").append(var16).append("\">").append(var21).append("</td></tr>");
                    ++var6;

                    for(int var23 = 1; var23 < var16; ++var23) {
                        this.buffer.append("<tr class=\"").append(var10).append("\">").append("<td><a href=\"#m").append(var6).append("\">").append(var18).append("</a></td></tr>");
                        ++var6;
                    }

                    var11 += var16;
                }

                this.writer.print("<tr class=\"");
                this.writer.print(var10);
                this.writer.print("\">");
                this.writer.print("<td rowspan=\"");
                this.writer.print(var11);
                this.writer.print("\">");
                this.writer.print(Utils.escapeHtml(var9.getClassName()));
                this.writer.print("</td>");
                this.writer.print(this.buffer);
            }

            var5 = var6 - var4;
        }

        return var5;
    }

    protected void writeScenarioDetails() {
        int var1 = 0;
        Iterator var2 = this.suiteResults.iterator();

        while(var2.hasNext()) {
            Reporter.SuiteResult var3 = (Reporter.SuiteResult)var2.next();

            Reporter.TestResult var5;
            for(Iterator var4 = var3.getTestResults().iterator(); var4.hasNext(); var1 += this.writeScenarioDetails(var5.getPassedTestResults(), var1)) {
                var5 = (Reporter.TestResult)var4.next();
                this.writer.print("<h2>");
                this.writer.print(Utils.escapeHtml(var5.getTestName()));
                this.writer.print("</h2>");
                var1 += this.writeScenarioDetails(var5.getFailedConfigurationResults(), var1);
                var1 += this.writeScenarioDetails(var5.getFailedTestResults(), var1);
                var1 += this.writeScenarioDetails(var5.getSkippedConfigurationResults(), var1);
                var1 += this.writeScenarioDetails(var5.getSkippedTestResults(), var1);
            }
        }

    }

    private int writeScenarioDetails(List<Reporter.ClassResult> var1, int var2) {
        int var3 = var2;
        Iterator var4 = var1.iterator();

        while(var4.hasNext()) {
            Reporter.ClassResult var5 = (Reporter.ClassResult)var4.next();
            String var6 = var5.getClassName();
            Iterator var7 = var5.getMethodResults().iterator();

            while(var7.hasNext()) {
                Reporter.MethodResult var8 = (Reporter.MethodResult)var7.next();
                List var9 = var8.getResults();

                assert !var9.isEmpty();

                String var10 = Utils.escapeHtml(var6 + "#" + ((ITestResult)var9.iterator().next()).getMethod().getMethodName());

                for(Iterator var11 = var9.iterator(); var11.hasNext(); ++var3) {
                    ITestResult var12 = (ITestResult)var11.next();
                    this.writeScenario(var3, var10, var12);
                }
            }
        }

        return var3 - var2;
    }

    private void writeScenario(int var1, String var2, ITestResult var3) {
        this.writer.print("<h3 id=\"m");
        this.writer.print(var1);
        this.writer.print("\">");
        this.writer.print(var2);
        this.writer.print("</h3>");
        this.writer.print("<table class=\"result\">");
        boolean var4 = false;
        Object[] var5 = var3.getParameters();
        int var6 = var5 == null ? 0 : var5.length;
        if (var6 > 0) {
            this.writer.print("<tr class=\"param\">");

            for(int var7 = 1; var7 <= var6; ++var7) {
                this.writer.print("<th>Parameter #");
                this.writer.print(var7);
                this.writer.print("</th>");
            }

            this.writer.print("</tr><tr class=\"param stripe\">");
            Object[] var11 = var5;
            int var8 = var5.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                Object var10 = var11[var9];
                this.writer.print("<td>");
                this.writer.print(Utils.escapeHtml(Utils.toString(var10)));
                this.writer.print("</td>");
            }

            this.writer.print("</tr>");
            var4 = true;
        }

        List var12 = org.testng.Reporter.getOutput(var3);
        if (!var12.isEmpty()) {
            this.writer.print("<tr><th");
            if (var6 > 1) {
                this.writer.printf(" colspan=\"%d\"", var6);
            }

            this.writer.print(">Messages</th></tr>");
            this.writer.print("<tr><td");
            if (var6 > 1) {
                this.writer.printf(" colspan=\"%d\"", var6);
            }

            this.writer.print(">");
            this.writeReporterMessages(var12);
            this.writer.print("</td></tr>");
            var4 = true;
        }

        Throwable var13 = var3.getThrowable();
        if (var13 != null) {
            this.writer.print("<tr><th");
            if (var6 > 1) {
                this.writer.printf(" colspan=\"%d\"", var6);
            }

            this.writer.print(">");
            this.writer.print(var3.getStatus() == 1 ? "Expected Exception" : "Exception");
            this.writer.print("</th></tr>");
            this.writer.print("<tr><td");
            if (var6 > 1) {
                this.writer.printf(" colspan=\"%d\"", var6);
            }

            this.writer.print(">");
            this.writeStackTrace(var13);
            this.writer.print("</td></tr>");
            var4 = true;
        }

        if (!var4) {
            this.writer.print("<tr><th");
            if (var6 > 1) {
                this.writer.printf(" colspan=\"%d\"", var6);
            }

            this.writer.print(" class=\"invisible\"/></tr>");
        }

        this.writer.print("</table>");
        this.writer.println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");
    }

    protected void writeReporterMessages(List<String> var1) {
        this.writer.print("<div class=\"messages\">");
        Iterator var2 = var1.iterator();

        assert var2.hasNext();

        if (org.testng.Reporter.getEscapeHtml()) {
            this.writer.print(Utils.escapeHtml((String)var2.next()));
        } else {
            this.writer.print((String)var2.next());
        }

        while(var2.hasNext()) {
            this.writer.print("<br/>");
            if (org.testng.Reporter.getEscapeHtml()) {
                this.writer.print(Utils.escapeHtml((String)var2.next()));
            } else {
                this.writer.print((String)var2.next());
            }
        }

        this.writer.print("</div>");
    }

    protected void writeStackTrace(Throwable var1) {
        this.writer.print("<div class=\"stacktrace\">");
        this.writer.print(Utils.shortStackTrace(var1, true));
        this.writer.print("</div>");
    }

    protected void writeTableHeader(String var1, String var2) {
        this.writeTag("th", var1, var2);
    }

    protected void writeTableData(String var1) {
        this.writeTableData(var1, (String)null);
    }

    protected void writeTableData(String var1, String var2) {
        this.writeTag("td", var1, var2);
    }

    protected void writeTag(String var1, String var2, String var3) {
        this.writer.print("<");
        this.writer.print(var1);
        if (var3 != null) {
            this.writer.print(" class=\"");
            this.writer.print(var3);
            this.writer.print("\"");
        }

        this.writer.print(">");
        this.writer.print(var2);
        this.writer.print("</");
        this.writer.print(var1);
        this.writer.print(">");
    }

    protected static class MethodResult {
        private final List<ITestResult> results;

        public MethodResult(List<ITestResult> var1) {
            this.results = var1;
        }

        public List<ITestResult> getResults() {
            return this.results;
        }
    }

    protected static class ClassResult {
        private final String className;
        private final List<Reporter.MethodResult> methodResults;

        public ClassResult(String var1, List<Reporter.MethodResult> var2) {
            this.className = var1;
            this.methodResults = var2;
        }

        public String getClassName() {
            return this.className;
        }

        public List<Reporter.MethodResult> getMethodResults() {
            return this.methodResults;
        }
    }

    protected static class TestResult {
        protected static final Comparator<ITestResult> RESULT_COMPARATOR = new Comparator<ITestResult>() {
            public int compare(ITestResult var1, ITestResult var2) {
                int var3 = var1.getTestClass().getName().compareTo(var2.getTestClass().getName());
                if (var3 == 0) {
                    var3 = var1.getMethod().getMethodName().compareTo(var2.getMethod().getMethodName());
                }

                return var3;
            }
        };
        private final String testName;
        private final List<Reporter.ClassResult> failedConfigurationResults;
        private final List<Reporter.ClassResult> failedTestResults;
        private final List<Reporter.ClassResult> skippedConfigurationResults;
        private final List<Reporter.ClassResult> skippedTestResults;
        private final List<Reporter.ClassResult> passedTestResults;
        private final int failedTestCount;
        private final int skippedTestCount;
        private final int passedTestCount;
        private final long duration;
        private final String includedGroups;
        private final String excludedGroups;

        public TestResult(ITestContext var1) {
            this.testName = var1.getName();
            Set var2 = var1.getFailedConfigurations().getAllResults();
            Set var3 = var1.getFailedTests().getAllResults();
            Set var4 = var1.getSkippedConfigurations().getAllResults();
            Set var5 = var1.getSkippedTests().getAllResults();
            Set var6 = var1.getPassedTests().getAllResults();
            this.failedConfigurationResults = this.groupResults(var2);
            this.failedTestResults = this.groupResults(var3);
            this.skippedConfigurationResults = this.groupResults(var4);
            this.skippedTestResults = this.groupResults(var5);
            this.passedTestResults = this.groupResults(var6);
            this.failedTestCount = var3.size();
            this.skippedTestCount = var5.size();
            this.passedTestCount = var6.size();
            this.duration = var1.getEndDate().getTime() - var1.getStartDate().getTime();
            this.includedGroups = this.formatGroups(var1.getIncludedGroups());
            this.excludedGroups = this.formatGroups(var1.getExcludedGroups());
        }

        protected List<Reporter.ClassResult> groupResults(Set<ITestResult> var1) {
            List var2 = Lists.newArrayList();
            if (!var1.isEmpty()) {
                List var3 = Lists.newArrayList();
                List var4 = Lists.newArrayList();
                List var5 = Lists.newArrayList(var1);
                Collections.sort(var5, RESULT_COMPARATOR);
                Iterator var6 = var5.iterator();

                assert var6.hasNext();

                ITestResult var7 = (ITestResult)var6.next();
                var4.add(var7);
                String var8 = var7.getTestClass().getName();

                for(String var9 = var7.getMethod().getMethodName(); var6.hasNext(); var4.add(var7)) {
                    var7 = (ITestResult)var6.next();
                    String var10 = var7.getTestClass().getName();
                    if (!var8.equals(var10)) {
                        assert !var4.isEmpty();

                        var3.add(new Reporter.MethodResult(var4));
                        var4 = Lists.newArrayList();

                        assert !var3.isEmpty();

                        var2.add(new Reporter.ClassResult(var8, var3));
                        var3 = Lists.newArrayList();
                        var8 = var10;
                        var9 = var7.getMethod().getMethodName();
                    } else {
                        String var11 = var7.getMethod().getMethodName();
                        if (!var9.equals(var11)) {
                            assert !var4.isEmpty();

                            var3.add(new Reporter.MethodResult(var4));
                            var4 = Lists.newArrayList();
                            var9 = var11;
                        }
                    }
                }

                assert !var4.isEmpty();

                var3.add(new Reporter.MethodResult(var4));

                assert !var3.isEmpty();

                var2.add(new Reporter.ClassResult(var8, var3));
            }

            return var2;
        }

        public String getTestName() {
            return this.testName;
        }

        public List<Reporter.ClassResult> getFailedConfigurationResults() {
            return this.failedConfigurationResults;
        }

        public List<Reporter.ClassResult> getFailedTestResults() {
            return this.failedTestResults;
        }

        public List<Reporter.ClassResult> getSkippedConfigurationResults() {
            return this.skippedConfigurationResults;
        }

        public List<Reporter.ClassResult> getSkippedTestResults() {
            return this.skippedTestResults;
        }

        public List<Reporter.ClassResult> getPassedTestResults() {
            return this.passedTestResults;
        }

        public int getFailedTestCount() {
            return this.failedTestCount;
        }

        public int getSkippedTestCount() {
            return this.skippedTestCount;
        }

        public int getPassedTestCount() {
            return this.passedTestCount;
        }

        public long getDuration() {
            return this.duration;
        }

        public String getIncludedGroups() {
            return this.includedGroups;
        }

        public String getExcludedGroups() {
            return this.excludedGroups;
        }

        protected String formatGroups(String[] var1) {
            if (var1.length == 0) {
                return "";
            } else {
                StringBuilder var2 = new StringBuilder();
                var2.append(var1[0]);

                for(int var3 = 1; var3 < var1.length; ++var3) {
                    var2.append(", ").append(var1[var3]);
                }

                return var2.toString();
            }
        }
    }

    protected static class SuiteResult {
        private final String suiteName;
        private final List<Reporter.TestResult> testResults = Lists.newArrayList();

        public SuiteResult(ISuite var1) {
            this.suiteName = var1.getName();
            Iterator var2 = var1.getResults().values().iterator();

            while(var2.hasNext()) {
                ISuiteResult var3 = (ISuiteResult)var2.next();
                this.testResults.add(new Reporter.TestResult(var3.getTestContext()));
            }

        }

        public String getSuiteName() {
            return this.suiteName;
        }

        public List<Reporter.TestResult> getTestResults() {
            return this.testResults;
        }
    }
}
