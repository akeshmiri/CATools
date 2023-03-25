package org.catools.pipeline.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@NamedQueries({
    @NamedQuery(name = "getEnvironmentByName", query = "FROM CPipelineEnvironment where name=:name")
})
@Entity
@Table(name = "environment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "environment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CPipelineEnvironment implements Serializable {
  @Serial
  private static final long serialVersionUID = 6267674018185613707L;

  @Id
  @Column(name = "code", length = 10, unique = true, nullable = false)
  private String code;

  @Column(name = "name", length = 100)
  private String name;
}
