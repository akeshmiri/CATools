package org.catools.etl.tms.dao;

import org.catools.etl.tms.model.CEtlItemType;
import org.hibernate.annotations.QueryHints;

public class CEtlItemTypeDao extends CEtlBaseDao {
  public static CEtlItemType getItemTypeByName(String name) {
    return getTransactionResult(
        session -> {
          return session
              .createNamedQuery("getItemTypeByName", CEtlItemType.class)
              .setParameter("name", name)
              .setHint(QueryHints.CACHEABLE, true)
              .getResultStream()
              .findFirst()
              .orElse(null);
        });
  }
}
