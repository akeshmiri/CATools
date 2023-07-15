package org.catools.atlassian.scale.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.catools.atlassian.scale.utils.CustomDateDeserializer;
import org.catools.atlassian.scale.utils.CustomDateSerializer;
import org.catools.common.date.CDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CZScaleChangeHistory {
  private Integer id;
  private String source;
  private Integer sourceId;
  private String type;
  private String userKey;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private CDate historyDate;
  private CZScaleChangeHistoryItems changeHistoryItems;
}
