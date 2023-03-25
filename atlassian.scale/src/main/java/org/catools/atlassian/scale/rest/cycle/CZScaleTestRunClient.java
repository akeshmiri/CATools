package org.catools.atlassian.scale.rest.cycle;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.catools.atlassian.scale.configs.CZScaleConfigs;
import org.catools.atlassian.scale.model.*;
import org.catools.atlassian.scale.rest.CZScaleRestClient;
import org.catools.common.collections.CList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;
import java.util.function.Consumer;

@Log4j2
public class CZScaleTestRunClient extends CZScaleRestClient {

  public CZScaleTestRunClient() {
    super();
  }

  public CZScaleTestRuns getAllTestRuns(String projectKey, String folder, String fields) {
    return getAllTestRuns(projectKey, folder, fields, 1, 1, null);
  }

  public CZScaleTestRuns getAllTestRuns(String projectKey,
                                        String folder,
                                        String fields,
                                        int parallelInputCount,
                                        int parallelOutputCount,
                                        Consumer<CZScaleTestRun> onAction) {
    Set<CZScaleTestRun> results = readAllInParallel(
        "Get Test Runs",
        parallelInputCount,
        parallelOutputCount,
        (start, max) -> _getAllTestRuns(projectKey, folder, fields, start, max),
        onAction);
    return new CZScaleTestRuns(results);
  }

  private CZScaleTestRuns _getAllTestRuns(String projectKey, String folder, String fields, int startAt, int maxResults) {
    String homeUri = CZScaleConfigs.Scale.getHomeUri();
    log.debug("All Test Runs from {}, projectKey: {}, fields: {}, startAT: {}, maxResult: {}", homeUri, projectKey, fields, startAt, maxResults);
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(homeUri)
            .basePath("/testrun/search")
            .queryParam("startAt", startAt)
            .queryParam("maxResults", maxResults)
            .queryParam("query", String.format("projectKey = \"%s\" AND folder = \"%s\"", projectKey, folder));

    if (StringUtils.isNotEmpty(fields)) {
      specification.queryParam("fields", fields);
    }

    Response response = get(specification);

    if (response.statusCode() != 200) {
      log.warn("Response::\n{}", response.body().asString());
    }

    response.then().statusCode(200);
    return response.body().as(CZScaleTestRuns.class);
  }

  public CZScaleTestRun getTestRun(String testRunKey) {
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZScaleConfigs.Scale.getHomeUri())
            .basePath("/testrun/" + testRunKey);

    Response response = get(specification);

    if (response.statusCode() != 200)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(200);
    return response.body().as(CZScaleTestRun.class);
  }

  public CZScaleTestResults getTestResults(String testRunKey) {
    return getTestResults(testRunKey, StringUtils.EMPTY, 1, 1, null);
  }

  public CZScaleTestResults getTestResults(String testRunKey,
                                           String fields,
                                           int parallelInputCount,
                                           int parallelOutputCount,
                                           Consumer<CZScaleTestResult> onAction) {
    Set<CZScaleTestResult> get_test_results = readAllInParallel(
        "Get Test Results",
        parallelInputCount,
        parallelOutputCount,
        (start, max) -> _getTestResults(testRunKey, fields, start, max),
        onAction);
    return new CZScaleTestResults(get_test_results);
  }

  private CZScaleTestResults _getTestResults(String testRunKey, String fields, int startAt, int maxResults) {
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZScaleConfigs.Scale.getHomeUri())
            .queryParam("startAt", startAt)
            .queryParam("maxResults", maxResults)
            .basePath("/testrun/" + testRunKey + "/testresults");

    if (StringUtils.isNotEmpty(fields)) {
      specification.queryParam("fields", fields);
    }

    Response response = get(specification);

    if (response.statusCode() != 200)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(200);
    return response.body().as(CZScaleTestResults.class);
  }

  public CList<Long> createTestResults(String testRunKey, CZScaleTestResults testResults) {
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZScaleConfigs.Scale.getHomeUri())
            .basePath("/testrun/" + testRunKey + "/testresults")
            .body(testResults);
    Response response = post(specification);

    if (response.statusCode() != 201)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(201);
    return CList.of(new JSONArray(response.body().asString()))
        .mapToList(o -> ((JSONObject) o).getLong("id"));
  }

  public long updateTestResult(String testRunKey, String testCaseKey, CZScaleUpdateTestResultRequest testResult) {
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZScaleConfigs.Scale.getHomeUri())
            .basePath("/testrun/" + testRunKey + "/testcase/" + testCaseKey + "/testresult")
            .body(testResult);

    Response response = put(specification);

    if (response.statusCode() != 200)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(200);
    return new JSONObject(response.body().asString()).getLong("id");
  }

  public void deleteTestRun(String testRunKey) {
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZScaleConfigs.Scale.getHomeUri())
            .basePath("/testrun/" + testRunKey);

    Response response = delete(specification);

    if (response.statusCode() != 204)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(204);
  }

  public String createTestRun(CZScalePlanTestRun planTestRun) {
    RequestSpecification specification =
        RestAssured.given()
            .baseUri(CZScaleConfigs.Scale.getHomeUri())
            .basePath("/testrun")
            .body(planTestRun);

    Response response = post(specification);

    if (response.statusCode() != 201)
      log.trace("Response::\n{}", response.body().asString());

    response.then().statusCode(201);
    return response.body().jsonPath().get("key");
  }
}
