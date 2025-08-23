package org.catools.etl.k8s.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

import static org.catools.etl.k8s.configs.CEtlKubeConfigs.K8S_SCHEMA;


@NamedQuery(name = "getEtlKubePodStatus", query = "FROM CEtlKubePodStatus where status=:status and phase=:phase and message=:message and reason=:reason")
@Entity
@Table(name = "pod_status", schema = K8S_SCHEMA)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "pod_status")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlKubePodStatus implements Serializable {

  @Serial
  private static final long serialVersionUID = 6867874018185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "status", length = 100)
  private String status;

  @Column(name = "phase", length = 100)
  private String phase;

  @Column(name = "message", length = 500)
  private String message;

  @Column(name = "reason", length = 500)
  private String reason;

  public CEtlKubePodStatus(String status, String phase, String message, String reason) {
    this.status = StringUtils.substring(status, 0, 100);
    this.phase = StringUtils.substring(phase, 0, 100);
    this.message = StringUtils.substring(message, 0, 500);
    this.reason = StringUtils.substring(reason, 0, 500);
  }
}
