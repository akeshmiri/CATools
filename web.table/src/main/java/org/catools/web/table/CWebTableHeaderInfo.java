package org.catools.web.table;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.utils.CStringUtil;
import org.catools.web.collections.CWebElements;
import org.catools.web.drivers.CDriver;

@Getter
public class CWebTableHeaderInfo<DR extends CDriver> {

  private CList<Header> headers = new CList<>();

  public CWebTableHeaderInfo(DR driver, String headersLocator) {
    new CWebElements<>("Headers", driver, headersLocator).forEach(h -> {
      String headerText = CStringUtil.normalizeSpace(h.getText(1));
      if (StringUtils.isBlank(headersLocator)) {
        h.moveTo();
        headerText = CStringUtil.normalizeSpace(h.getText(1));
      }
      headers.add(new Header(headers.size() + 1, headerText, h.Visible.isTrue()));
    });
  }

  public Integer getHeaderIndex(String header) {
    Header first = headers.getFirstOrNull(h -> StringUtils.equalsIgnoreCase(header, h.header()));
    return first == null ? -1 : first.index();
  }

  public CMap<Integer, String> getHeadersMap() {
    CMap<Integer, String> output = new CHashMap<>();
    for (Header h : headers) {
      output.put(h.index(), h.header());
    }
    return output;
  }

  public CMap<Integer, String> getVisibleHeadersMap() {
    CMap<Integer, String> output = new CHashMap<>();
    for (Header h : headers) {
      if (h.visible())
        output.put(h.index(), h.header());
    }
    return output;
  }

  public record Header(int index, String header, boolean visible) {
  }
}
