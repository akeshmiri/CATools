package org.catools.common.tests.verify.retry;

import org.catools.common.extensions.verify.CObjectVerification;
import org.catools.common.extensions.verify.CVerifier;

import java.util.function.Consumer;

public class ObjectVerificationVerifierTest extends ObjectVerificationBaseTest {
  @Override
  public void verify(Consumer<CObjectVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Object);
    verifier.verify();
  }
}
