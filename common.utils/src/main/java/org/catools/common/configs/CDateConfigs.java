package org.catools.common.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CStringUtil;

import java.util.TimeZone;

public class CDateConfigs {

  public static TimeZone getDefaultTimeZone() {
    String val = CHocon.get(Configs.DATE_TIME_ZONE).asString("America/New_York");
    return CStringUtil.isBlank(val) ? TimeZone.getDefault() : TimeZone.getTimeZone(val);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    DATE_TIME_ZONE("catools.time_zone");

    private final String path;
  }
}
