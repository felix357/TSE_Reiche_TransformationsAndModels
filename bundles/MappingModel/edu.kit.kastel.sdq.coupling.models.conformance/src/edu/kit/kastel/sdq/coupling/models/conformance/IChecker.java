package edu.kit.kastel.sdq.coupling.models.conformance;

/**
 * Common interface for all integration condition checkers.
 * Defines the contract for executing a integration condition check.
 */
public interface IChecker {

	/**
     * Executes the specific integration condition logic for the implementing class.
     * The checker must be configured before calling this method.
     *
     * @return true if the integration condition holds, false otherwise.
     */
	boolean runCheck();
}
