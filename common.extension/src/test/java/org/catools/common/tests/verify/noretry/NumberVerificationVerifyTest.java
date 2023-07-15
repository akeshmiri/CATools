package org.catools.common.tests.verify.noretry;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.verify.hard.CNumberVerification;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class NumberVerificationVerifyTest extends NumberVerificationBaseTest {
  @Override
  public void verifyBigDecimal(Consumer<CNumberVerification<BigDecimal>> action) {
    action.accept(CVerify.BigDecimal);
  }

  @Override
  public void verifyDouble(Consumer<CNumberVerification<Double>> action) {
    action.accept(CVerify.Double);
  }

  @Override
  public void verifyInt(Consumer<CNumberVerification<Integer>> action) {
    action.accept(CVerify.Int);
  }

  @Override
  public void verifyLong(Consumer<CNumberVerification<Long>> action) {
    action.accept(CVerify.Long);
  }
}
