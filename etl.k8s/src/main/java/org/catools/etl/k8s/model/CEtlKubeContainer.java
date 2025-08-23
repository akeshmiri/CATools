package org.catools.etl.k8s.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.catools.etl.k8s.configs.CEtlKubeConfigs.K8S_SCHEMA;


@Entity
@Table(name = "container", schema = K8S_SCHEMA)
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CEtlKubeContainer implements Serializable {

  @Serial
  private static final long serialVersionUID = 6052874018185613707L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "type", length = 50, nullable = false)
  private String type;

  @Column(name = "name", length = 300, nullable = false)
  private String name;

  @Column(name = "image", length = 300, nullable = false)
  private String image;

  @Column(name = "image_id", length = 300, nullable = false)
  private String imageId;

  @Column(name = "ready")
  private Boolean ready;

  @Column(name = "started")
  private Boolean started;

  @Column(name = "restart_count")
  private Integer restartCount;

  @Column(name = "started_at")
  private Date startedAt;

  @Column(name = "sync_time")
  private Date syncTime;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      schema = K8S_SCHEMA,
      name = "container_metadata_mid",
      joinColumns = {@JoinColumn(name = "container_id")},
      inverseJoinColumns = {@JoinColumn(name = "metadata_id")})
  private Set<CEtlKubeContainerMetaData> metadata = new HashSet<>();

  public CEtlKubeContainer(
      String type,
      String name,
      String image,
      String imageId,
      Boolean ready,
      Boolean started,
      Integer restartCount,
      Date startedAt,
      Date syncTime) {
    this.type = type;
    this.name = StringUtils.substring(name, 0, 300);
    this.image = StringUtils.substring(image, 0, 300);
    this.imageId = StringUtils.substring(imageId, 0, 300);
    this.ready = ready;
    this.started = started;
    this.startedAt = startedAt;
    this.syncTime = syncTime;
    this.restartCount = restartCount;
  }

  public void addMetaData(CEtlKubeContainerMetaData metadata) {
    this.metadata.add(metadata);
  }
}
