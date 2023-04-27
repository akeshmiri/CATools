package org.catools.common.tests.verify.retry;

import org.catools.common.extensions.verify.CObjectVerification;

import java.util.function.Consumer;

public class ObjectVerificationVerifyTest extends ObjectVerificationBaseTest {
  @Override
  public void verify(Consumer<CObjectVerification> action) {
    action.accept(verify.Object);
  }
}
