package org.catools.atlassian.etl.scale.translators;

import lombok.extern.slf4j.Slf4j;
import org.catools.atlassian.etl.scale.helpers.CEtlZScaleSyncHelper;
import org.catools.atlassian.scale.model.CZScaleTestExecution;
import org.catools.atlassian.scale.model.CZScaleTestRun;
import org.catools.atlassian.scale.rest.cycle.CZScaleExecutionStatus;
import org.catools.common.utils.CStringUtil;
import org.catools.tms.etl.cache.CEtlCacheManager;
import org.catools.tms.etl.model.*;

import java.security.InvalidParameterException;
import java.util.Objects;

@Slf4j
public class CEtlZScaleTestRunTranslator {
  public static CEtlCycle translateTestRun(CEtlVersion version, CZScaleTestRun testRun) {
    Objects.requireNonNull(version);
    Objects.requireNonNull(testRun);

    try {
      String folder = "";

      if (CStringUtil.isNotBlank(testRun.getFolder())) {
        folder = testRun.getFolder().trim();
        if (!folder.endsWith("/")) {
          folder += "/";
        }
      }

      return new CEtlCycle(
          testRun.getKey(),
          folder + testRun.getName(),
          version,
          testRun.getPlannedEndDate(),
          testRun.getPlannedStartDate());
    } catch (Throwable t) {
      log.error("Failed to translate test run {} to cycle.", testRun, t);
      throw t;
    }
  }

  public static CEtlExecution translateExecution(CZScaleTestRun testRun, CEtlCycle cycle, CZScaleTestExecution execution) {
    Objects.requireNonNull(execution);
    CEtlItem item;
    try {
      try {
        item = CEtlCacheManager.readItem(execution.getTestCaseKey());
      } catch (InvalidParameterException e) {
        item = CEtlZScaleSyncHelper.addItem(execution.getTestCaseKey());
      }

      CEtlExecutionStatus status = getStatus(execution.getStatus());
      CEtlUser executor = getExecutor(execution);

      return new CEtlExecution(
          String.valueOf(execution.getId()),
          item,
          cycle,
          // We do not createdData for execution, so we replace it with CreatedOn from test run
          testRun.getCreatedOn(),
          execution.getExecutedOn(),
          executor,
          status);
    } catch (Throwable t) {
      log.error("Failed to translate execution {}.", execution, t);
      throw t;
    }
  }

  private static CEtlUser getExecutor(CZScaleTestExecution execution) {
    return CStringUtil.isBlank(execution.getExecutedBy()) ? CEtlUser.UNSET : new CEtlUser(execution.getExecutedBy());
  }

  private static CEtlExecutionStatus getStatus(CZScaleExecutionStatus status) {
    return status == null || CStringUtil.isBlank(status.getScaleName()) ?
        CEtlExecutionStatus.UNSET :
        new CEtlExecutionStatus(status.getScaleName());
  }
}
