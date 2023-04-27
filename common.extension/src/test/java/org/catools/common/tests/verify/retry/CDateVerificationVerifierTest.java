package org.catools.common.tests.verify.retry;

import org.catools.common.extensions.verify.CDateVerification;
import org.catools.common.extensions.verify.CVerifier;

import java.util.function.Consumer;

public class CDateVerificationVerifierTest extends CDateVerificationBaseTest {
  @Override
  public void verify(Consumer<CDateVerification> action) {
    CVerifier verifier = new CVerifier();
    action.accept(verifier.Date);
    verifier.verify();
  }
}
