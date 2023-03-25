package org.catools.tms.etl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "execution")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CEtlExecution implements Serializable {

  @Serial
  private static final long serialVersionUID = 6051874018285613707L;

  @Id
  @Column(name = "id", length = 20, unique = true, nullable = false)
  private String id;

  @Column(name = "created", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;

  @Column(name = "executed")
  @Temporal(TemporalType.TIMESTAMP)
  private Date executed;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "item_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_EXECUTION_ITEM"))
  private CEtlItem item;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "cycle_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_EXECUTION_CYCLE"))
  private CEtlCycle cycle;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "status_id",
      referencedColumnName = "id",
      nullable = false,
      foreignKey = @ForeignKey(name = "FK_EXECUTION_STATUS"))
  private CEtlExecutionStatus status;

  @ManyToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JoinColumn(
      name = "executor_id",
      referencedColumnName = "name",
      foreignKey = @ForeignKey(name = "FK_EXECUTION_USER"))
  private CEtlUser executor;

  public CEtlExecution(
      String id,
      CEtlItem item,
      CEtlCycle cycle,
      Date created,
      Date executed,
      CEtlUser executor,
      CEtlExecutionStatus status) {
    this.id = id;
    this.item = item;
    this.cycle = cycle;
    this.created = created;
    this.executed = executed;
    this.executor = executor;
    this.status = status;
  }
}
