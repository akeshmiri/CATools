package org.catools.web.table;

import org.catools.web.drivers.CDriver;
import org.catools.web.tests.CWebTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CWebTableTest extends CWebTest<CDriver> {

  private CustomerTable customerTable;

  @Override
  @BeforeClass
  public void beforeClass() {
    getDriver().open("https://www.w3schools.com/html/html_tables.asp");
    customerTable = new CustomerTable(getDriver());
  }

  @Test
  public void testGetRecord() {
    customerTable.getFirst()
        .getRecord()
        .verifyContains(verify, "Company", "Alfreds Futterkiste");
  }

  @Test
  public void testGetAll() {
    customerTable.getAll().verifySizeEquals(verify, 6);
    customerTable.getAll().get(0)
        .getRecord()
        .verifyContains(verify, "Company", "Alfreds Futterkiste");
  }

  @Test
  public void testGetFirst() {
    customerTable.getFirst("Company", "Island Trading")
        .getRecord()
        .verifyContains(verify, "Company", "Island Trading");
  }
}