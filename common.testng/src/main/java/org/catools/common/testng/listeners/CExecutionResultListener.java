package org.catools.common.testng.listeners;

import org.catools.common.collections.CList;
import org.catools.common.io.CFile;
import org.catools.common.testng.model.CTestResult;
import org.catools.common.testng.model.CTestResults;
import org.catools.common.testng.utils.CRetryAnalyzer;
import org.catools.common.utils.CJsonUtil;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import static org.catools.common.testng.utils.CTestClassUtil.noRetryLeft;

public class CExecutionResultListener implements CITestNGListener {
  private static final CTestResults executionResults = new CTestResults();
  public static CTestResults getExecutionResults() {
    return executionResults;
  }

  public static boolean isPassed(ITestNGMethod method) {
    return executionResults.isPassed(method);
  }

  @Override
  public int priority() {
    return 0;
  }

  @Override
  public void onStart(ITestContext context) {
    CList<ITestNGMethod> allMethods = new CList<>(context.getSuite().getAllMethods());
    allMethods.removeIf(m -> {
      CTestResult testResult = executionResults.getTestResultOrNull(m);
      return testResult != null && testResult.getStatus().isPassed();
    });

    for (ITestNGMethod method : allMethods) {
      if (method == null) continue;

      CTestResult testResult = executionResults.getTestResultOrNull(method);

      if (testResult == null || testResult.getOrigin() == null) continue;

      IRetryAnalyzer retryAnalyzer = method.getRetryAnalyzer(testResult.getOrigin());
      if (retryAnalyzer instanceof CRetryAnalyzer analyzer) {
        analyzer.resetCount();
      }
    }
  }

  @Override
  public synchronized void onTestSuccess(ITestResult result) {
    addResult(result);
  }

  @Override
  public synchronized void onTestFailure(ITestResult result) {
    if (noRetryLeft(result, true)) {
      addResult(result);
    }
  }

  @Override
  public synchronized void onTestSkipped(ITestResult result) {
    if (noRetryLeft(result, true)) {
      addResult(result);
    }
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    if (noRetryLeft(result, true)) {
      addResult(result);
    }
  }

  @Override
  public void onExecutionFinish() {
    CJsonUtil.write(CFile.fromOutput("./CTestResultCollection.json"), executionResults);
  }

  private boolean addResult(ITestResult result) {
    return executionResults.add(new CTestResult(result));
  }
}
