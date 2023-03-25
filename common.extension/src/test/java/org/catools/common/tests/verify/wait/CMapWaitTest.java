package org.catools.common.tests.verify.wait;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.wait.CMapWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Map;

public class CMapWaitTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify.Bool.isTrue(
        toWaiter().waitContains(toMap(1, 2, 3), "1", 1, 0, 100), "%s#%s", getParams());
    verify.Bool.isTrue(
        toWaiter().waitContains(toMap(1, 2, 3), toEntity(2), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    verify.Bool.isTrue(
        toWaiter().waitContainsAll(toMap(1, 2, 3), toMap(1, 3), 0, 100), "%s#%s", getParams());
    verify.Bool.isTrue(
        toWaiter().waitContainsAll(toMap(1, 2, 3), toMap(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    verify.Bool.isTrue(
        toWaiter().waitContainsAll(toMap(1, 2, 3), toMap(1, 23), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    verify.Bool.isTrue(
        toWaiter().waitContainsAll(toMap(1, 2, 3), toMap(1, 2, 3, 4), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    verify.Bool.isTrue(toWaiter().waitContainsAll(toMap(), toMap(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    verify.Bool.isTrue(toWaiter().waitContainsAll(toMap(), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    verify.Bool.isTrue(
        toWaiter().waitContainsNone(toMap(1, 2, 3), toMap(4, 5, 6), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N() {
    verify.Bool.isTrue(
        toWaiter().waitContainsNone(toMap(1, 2, 3), toMap(1, 5, 6), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N2() {
    verify.Bool.isTrue(
        toWaiter().waitContainsNone(toMap(1, 2, 3), toMap(2, 5, 6), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    verify.Bool.isTrue(
        toWaiter().waitContainsNone(toMap(1, 2, 3), toMap(3, 6), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    verify.Bool.isTrue(
        toWaiter().waitContainsNone(toMap(1, 2, 3), toMap(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    verify.Bool.isTrue(
        toWaiter().waitContains(toMap(1, 2, 3), "5", 2, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    verify.Bool.isTrue(
        toWaiter().waitContains(toMap(1, 2, 3), toEntity(5), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    verify.Bool.isTrue(
        toWaiter().waitContains(toMap(1, 2, 3), "1", 5, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    verify.Bool.isTrue(
        toWaiter().waitEmptyOrContains(toMap(1, 2, 3), "1", 1, 0, 100), "%s#%s", getParams());
    verify.Bool.isTrue(
        toWaiter().waitEmptyOrContains(toMap(), toEntity(5), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    verify.Bool.isTrue(
        toWaiter().waitEmptyOrContains(toMap(1, 2, 3), toEntity(6), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    verify.Bool.isTrue(toWaiter().waitEmptyOrContains(null, "6", 1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    verify.Bool.isTrue(
        toWaiter().waitEmptyOrNotContains(toMap(1, 2, 3), toEntity(5), 0, 100),
        "%s#%s",
        getParams());
    verify.Bool.isTrue(
        toWaiter().waitEmptyOrNotContains(toMap(), "1", 1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    verify.Bool.isTrue(
        toWaiter().waitEmptyOrNotContains(toMap(1, 2, 3), "3", 3, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    verify.Bool.isTrue(
        toWaiter().waitEmptyOrNotContains(null, "0", 0, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify.Bool.isTrue(
        toWaiter().waitEquals(toMap(1, 2, 3), toMap(1, 2, 3), 0, 100), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter().waitEquals(toMap(), toMap(), 0, 100), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify.Bool.isTrue(
        toWaiter().waitEquals(toMap(1, 2, 3), toMap(1, 3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify.Bool.isTrue(
        toWaiter().waitEquals(toMap(1, 2, 3), toMap(3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify.Bool.isTrue(
        toWaiter().waitEquals(toMap(1, 2, 3), toMap(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    verify.Bool.isTrue(toWaiter().waitEquals(toMap(), toMap(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify.Bool.isTrue(toWaiter().waitIsEmpty(toMap(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    verify.Bool.isTrue(toWaiter().waitIsEmpty(toMap(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    verify.Bool.isTrue(toWaiter().waitIsEmpty(toMap(2), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    verify.Bool.isTrue(toWaiter().waitIsEmpty(toMap(3), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify.Bool.isTrue(toWaiter().waitIsNotEmpty(toMap(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    verify.Bool.isTrue(toWaiter().waitIsNotEmpty(toMap(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    verify.Bool.isTrue(toWaiter().waitIsNotEmpty(toMap(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    verify.Bool.isTrue(toWaiter().waitIsNotEmpty(toMap(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify.Bool.isTrue(
        toWaiter().waitNotContains(toMap(1, 2, 3), toEntity(4), 0, 100), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter().waitNotContains(toMap(1), "2", 2, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    verify.Bool.isTrue(
        toWaiter().waitNotContainsAll(toMap(1, 2, 3), toMap(1, 4), 0, 100), "%s#%s", getParams());
    verify.Bool.isTrue(
        toWaiter().waitNotContainsAll(toMap(1, 2, 3), toMap(1, 2, 3, 4), 0, 100),
        "%s#%s",
        getParams());
    verify.Bool.isTrue(
        toWaiter().waitNotContainsAll(toMap(), toMap(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    verify.Bool.isTrue(
        toWaiter().waitNotContainsAll(toMap(1, 2, 3), toMap(1, 2, 3), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    verify.Bool.isTrue(
        toWaiter().waitNotContainsAll(toMap(1, 2, 3), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    verify.Bool.isTrue(
        toWaiter().waitNotContainsAll(toMap(), toMap(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    verify.Bool.isTrue(
        toWaiter().waitNotContains(toMap(1, 2, 3), toEntity(1), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    verify.Bool.isTrue(
        toWaiter().waitNotContains(toMap(1, 2, 3), null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    verify.Bool.isTrue(
        toWaiter().waitNotContains(toMap(1), toEntity(1), 0, 100), "%s#%s", getParams());
  }

  public Map.Entry<String, Integer> toEntity(Integer item) {
    return Map.entry(item + "", item);
  }

  public CMap<String, Integer> toMap(Integer... list) {
    if (list == null) {
      return null;
    }
    CMap<String, Integer> map = new CHashMap<>();
    for (Integer k : list) {
      map.put(k.toString(), k);
    }
    return map;
  }

  private CMapWait toWaiter() {
    return new CMapWait();
  }
}
