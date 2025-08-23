package org.catools.atlassian.zapi.parser;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.catools.atlassian.zapi.exception.CZApiClientException;
import org.catools.atlassian.zapi.model.CZApiCycle;
import org.catools.atlassian.zapi.model.CZApiProjects;
import org.catools.atlassian.zapi.model.CZApiVersion;

public class CZApiCycleParser extends CZApiBaseParser {

  public static CZApiCycle parse(CZApiProjects projects, Response response) {
    JsonPath json;

    try {
      json = response.body().jsonPath();
      CZApiCycle cycle = new CZApiCycle();
      cycle.setId(json.getLong("id"));
      cycle.setProject(projects.getById(json.getLong("projectId")));
      cycle.setVersion(new CZApiVersion(json.getLong("versionId"), json.getString("versionName")));
      cycle.setDescription(json.getString("description"));
      cycle.setStartDate(getDate(json, "startDate"));
      cycle.setEndDate(getDate(json, "endDate"));
      cycle.setEnvironment(json.getString("environment"));
      cycle.setBuild(json.getString("get"));
      cycle.setName(json.getString("name"));
      cycle.setModifiedBy(StringUtils.removeEnd(json.getString("modifiedBy"), "(Inactive)"));
      return cycle;
    } catch (Exception e) {
      throw new CZApiClientException(
          "Could not parse input to JSON Array. input: " + response.body().asString(), e);
    }
  }
}
