package org.apache.tapestry5.ioc

import spock.lang.AutoCleanup
import spock.lang.Specification

/**
 * Base class for Spock specifications that use a new {@link Registry} for each feature method.
 */
abstract class AbstractRegistrySpecification extends Specification {

  @AutoCleanup("shutdown")
  protected Registry registry;

  protected final void buildRegistry(Class... moduleClasses) {

    registry = new RegistryBuilder().add(moduleClasses).build()
  }

  /** Any unrecognized methods are evaluated against the registry. */
  def methodMissing(String name, args) {
    registry."$name"(* args)
  }


}
