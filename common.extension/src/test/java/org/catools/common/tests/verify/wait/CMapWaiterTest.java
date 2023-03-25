package org.catools.common.tests.verify.wait;

import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.extensions.types.interfaces.CDynamicMapExtension;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.Map;

public class CMapWaiterTest extends CBaseUnitTest {
  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContains("1", 1), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContains(toEntity(2)), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContains("3", 3), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsAll() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContainsAll(toMap(1, 3)), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContainsAll(toMap()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N() {
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitContainsAll(toMap(1, 23)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N2() {
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitContainsAll(toMap(1, 2, 3, 4)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N3() {
    verify.Bool.isTrue(toWaiter(toMap()).waitContainsAll(toMap(1)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsAll_N4() {
    verify.Bool.isTrue(toWaiter(toMap()).waitContainsAll(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsNone() {
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitContainsNone(toMap(4, 5, 6)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N3() {
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitContainsNone(toMap(3, 6)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsNone_N4() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContainsNone(toMap()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContains("5", 5), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N2() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContains("5", 5), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_N3() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitContains("5", 5), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitEmptyOrContains("1", 1), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter(toMap()).waitEmptyOrContains("5", 5), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrContains_N() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitEmptyOrContains("6", 6), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrContains_Null() {
    verify.Bool.isTrue(toWaiter(null).waitEmptyOrContains("6", 6), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains() {
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitEmptyOrNotContains(toEntity(5)), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter(toMap()).waitEmptyOrNotContains("1", 1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEmptyOrNotContains_N() {
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitEmptyOrNotContains("3", 3), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEmptyOrNotContains_Null() {
    verify.Bool.isTrue(toWaiter(null).waitEmptyOrNotContains("0", 0), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitEquals(toMap(1, 2, 3)), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter(toMap()).waitEquals(toMap()), "%s#%s", getParams());
  }

  // Negative
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitEquals(toMap(1, 3)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N2() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitEquals(toMap(3)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N3() {
    verify.Bool.isTrue(toWaiter(toMap(1)).waitEquals(toMap(1, 2, 3)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_N4() {
    verify.Bool.isTrue(toWaiter(toMap(1)).waitEquals(toMap()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify.Bool.isTrue(toWaiter(toMap()).waitIsEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N() {
    verify.Bool.isTrue(toWaiter(toMap(1)).waitIsEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N2() {
    verify.Bool.isTrue(toWaiter(toMap(2)).waitIsEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_N3() {
    verify.Bool.isTrue(toWaiter(toMap(3)).waitIsEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify.Bool.isTrue(toWaiter(toMap(1)).waitIsNotEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N() {
    verify.Bool.isTrue(toWaiter(toMap()).waitIsNotEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N2() {
    verify.Bool.isTrue(toWaiter(toMap()).waitIsNotEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_N3() {
    verify.Bool.isTrue(toWaiter(toMap()).waitIsNotEmpty(1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitNotContains("4", 4), "%s#%s", getParams());
    verify.Bool.isTrue(toWaiter(toMap(1)).waitNotContains(toEntity(2)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsAll() {
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitNotContainsAll(toMap(1, 4)), "%s#%s", getParams());
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitNotContainsAll(toMap(1, 2, 3, 4)), "%s#%s", getParams());
    verify.Bool.isFalse(toWaiter(toMap(1)).waitNotContainsAll(toMap()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N() {
    verify.Bool.isTrue(
        toWaiter(toMap(1, 2, 3)).waitNotContainsAll(toMap(1, 2, 3)), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N2() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitNotContainsAll(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsAll_N3() {
    verify.Bool.isTrue(toWaiter(toMap()).waitNotContainsAll(toMap()), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitNotContains("1", 1), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N2() {
    verify.Bool.isTrue(toWaiter(toMap(1, 2, 3)).waitNotContains(null), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_N3() {
    verify.Bool.isTrue(toWaiter(toMap(1)).waitNotContains("1", 1), "%s#%s", getParams());
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

  private CDynamicMapExtension<String, Integer> toWaiter(Map<String, Integer> val) {
    return () -> val;
  }
}
