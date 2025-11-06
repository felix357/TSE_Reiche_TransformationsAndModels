/**
 */
package uncertainty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Scenario</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see uncertainty.UncertaintyPackage#getUncertaintyScenario()
 * @model
 * @generated
 */
public enum UncertaintyScenario implements Enumerator {
	/**
	 * The '<em><b>NON CONFORMANCE TO INPUT INTERFACE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NON_CONFORMANCE_TO_INPUT_INTERFACE_VALUE
	 * @generated
	 * @ordered
	 */
	NON_CONFORMANCE_TO_INPUT_INTERFACE(0, "NON_CONFORMANCE_TO_INPUT_INTERFACE", "NON_CONFORMANCE_TO_INPUT_INTERFACE"),

	/**
	 * The '<em><b>IMPRECISE INPUT DATA</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IMPRECISE_INPUT_DATA_VALUE
	 * @generated
	 * @ordered
	 */
	IMPRECISE_INPUT_DATA(0, "IMPRECISE_INPUT_DATA", "IMPRECISE_INPUT_DATA"),

	/**
	 * The '<em><b>INCORRECT INPUT DATA</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INCORRECT_INPUT_DATA_VALUE
	 * @generated
	 * @ordered
	 */
	INCORRECT_INPUT_DATA(0, "INCORRECT_INPUT_DATA", "INCORRECT_INPUT_DATA"),

	/**
	 * The '<em><b>CORRECT INPUT DATA</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORRECT_INPUT_DATA_VALUE
	 * @generated
	 * @ordered
	 */
	CORRECT_INPUT_DATA(0, "CORRECT_INPUT_DATA", "CORRECT_INPUT_DATA"),

	/**
	 * The '<em><b>SCENARIO DEFINITION INCORRECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCENARIO_DEFINITION_INCORRECT_VALUE
	 * @generated
	 * @ordered
	 */
	SCENARIO_DEFINITION_INCORRECT(0, "SCENARIO_DEFINITION_INCORRECT", "SCENARIO_DEFINITION_INCORRECT"),

	/**
	 * The '<em><b>SCENARIO DEFINITION CORRECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCENARIO_DEFINITION_CORRECT_VALUE
	 * @generated
	 * @ordered
	 */
	SCENARIO_DEFINITION_CORRECT(0, "SCENARIO_DEFINITION_CORRECT", "SCENARIO_DEFINITION_CORRECT"),

	/**
	 * The '<em><b>METHODOLOGY ABSTRACTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_ABSTRACTION_VALUE
	 * @generated
	 * @ordered
	 */
	METHODOLOGY_ABSTRACTION(0, "METHODOLOGY_ABSTRACTION", "METHODOLOGY_ABSTRACTION"),

	/**
	 * The '<em><b>METHODOLOGY APPROXIMATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_APPROXIMATION_VALUE
	 * @generated
	 * @ordered
	 */
	METHODOLOGY_APPROXIMATION(0, "METHODOLOGY_APPROXIMATION", "METHODOLOGY_APPROXIMATION"),

	/**
	 * The '<em><b>METHODOLOGY OVER SIMPLIFICATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_OVER_SIMPLIFICATION_VALUE
	 * @generated
	 * @ordered
	 */
	METHODOLOGY_OVER_SIMPLIFICATION(0, "METHODOLOGY_OVER_SIMPLIFICATION", "METHODOLOGY_OVER_SIMPLIFICATION"),

	/**
	 * The '<em><b>METHODOLOGY CORRECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_CORRECT_VALUE
	 * @generated
	 * @ordered
	 */
	METHODOLOGY_CORRECT(0, "METHODOLOGY_CORRECT", "METHODOLOGY_CORRECT"),

	/**
	 * The '<em><b>MODEL UNDER SPECIFICATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_UNDER_SPECIFICATION_VALUE
	 * @generated
	 * @ordered
	 */
	MODEL_UNDER_SPECIFICATION(0, "MODEL_UNDER_SPECIFICATION", "MODEL_UNDER_SPECIFICATION"),

	/**
	 * The '<em><b>MODEL ABSTRACTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_ABSTRACTION_VALUE
	 * @generated
	 * @ordered
	 */
	MODEL_ABSTRACTION(0, "MODEL_ABSTRACTION", "MODEL_ABSTRACTION"),

	/**
	 * The '<em><b>MODEL DISCREPANCY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_DISCREPANCY_VALUE
	 * @generated
	 * @ordered
	 */
	MODEL_DISCREPANCY(0, "MODEL_DISCREPANCY", "MODEL_DISCREPANCY"),

	/**
	 * The '<em><b>MODEL CORRECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_CORRECT_VALUE
	 * @generated
	 * @ordered
	 */
	MODEL_CORRECT(0, "MODEL_CORRECT", "MODEL_CORRECT"),

	/**
	 * The '<em><b>OUTPUT ERROR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_ERROR_VALUE
	 * @generated
	 * @ordered
	 */
	OUTPUT_ERROR(0, "OUTPUT_ERROR", "OUTPUT_ERROR"),

	/**
	 * The '<em><b>OUTPUT IMPRECISION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_IMPRECISION_VALUE
	 * @generated
	 * @ordered
	 */
	OUTPUT_IMPRECISION(0, "OUTPUT_IMPRECISION", "OUTPUT_IMPRECISION"),

	/**
	 * The '<em><b>OUTPUT CORRECT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_CORRECT_VALUE
	 * @generated
	 * @ordered
	 */
	OUTPUT_CORRECT(0, "OUTPUT_CORRECT", "OUTPUT_CORRECT"),

	/**
	 * The '<em><b>ORCHESTRATION NOT FINAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORCHESTRATION_NOT_FINAL_VALUE
	 * @generated
	 * @ordered
	 */
	ORCHESTRATION_NOT_FINAL(0, "ORCHESTRATION_NOT_FINAL", "ORCHESTRATION_NOT_FINAL"),

	/**
	 * The '<em><b>ORCHESTRATION FINAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORCHESTRATION_FINAL_VALUE
	 * @generated
	 * @ordered
	 */
	ORCHESTRATION_FINAL(0, "ORCHESTRATION_FINAL", "ORCHESTRATION_FINAL");

	/**
	 * The '<em><b>NON CONFORMANCE TO INPUT INTERFACE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NON_CONFORMANCE_TO_INPUT_INTERFACE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NON_CONFORMANCE_TO_INPUT_INTERFACE_VALUE = 0;

	/**
	 * The '<em><b>IMPRECISE INPUT DATA</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IMPRECISE_INPUT_DATA
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int IMPRECISE_INPUT_DATA_VALUE = 0;

	/**
	 * The '<em><b>INCORRECT INPUT DATA</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INCORRECT_INPUT_DATA
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INCORRECT_INPUT_DATA_VALUE = 0;

	/**
	 * The '<em><b>CORRECT INPUT DATA</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORRECT_INPUT_DATA
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CORRECT_INPUT_DATA_VALUE = 0;

	/**
	 * The '<em><b>SCENARIO DEFINITION INCORRECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCENARIO_DEFINITION_INCORRECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCENARIO_DEFINITION_INCORRECT_VALUE = 0;

	/**
	 * The '<em><b>SCENARIO DEFINITION CORRECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCENARIO_DEFINITION_CORRECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCENARIO_DEFINITION_CORRECT_VALUE = 0;

	/**
	 * The '<em><b>METHODOLOGY ABSTRACTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_ABSTRACTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int METHODOLOGY_ABSTRACTION_VALUE = 0;

	/**
	 * The '<em><b>METHODOLOGY APPROXIMATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_APPROXIMATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int METHODOLOGY_APPROXIMATION_VALUE = 0;

	/**
	 * The '<em><b>METHODOLOGY OVER SIMPLIFICATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_OVER_SIMPLIFICATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int METHODOLOGY_OVER_SIMPLIFICATION_VALUE = 0;

	/**
	 * The '<em><b>METHODOLOGY CORRECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_CORRECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int METHODOLOGY_CORRECT_VALUE = 0;

	/**
	 * The '<em><b>MODEL UNDER SPECIFICATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_UNDER_SPECIFICATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MODEL_UNDER_SPECIFICATION_VALUE = 0;

	/**
	 * The '<em><b>MODEL ABSTRACTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_ABSTRACTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MODEL_ABSTRACTION_VALUE = 0;

	/**
	 * The '<em><b>MODEL DISCREPANCY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_DISCREPANCY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MODEL_DISCREPANCY_VALUE = 0;

	/**
	 * The '<em><b>MODEL CORRECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODEL_CORRECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MODEL_CORRECT_VALUE = 0;

	/**
	 * The '<em><b>OUTPUT ERROR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_ERROR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OUTPUT_ERROR_VALUE = 0;

	/**
	 * The '<em><b>OUTPUT IMPRECISION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_IMPRECISION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OUTPUT_IMPRECISION_VALUE = 0;

	/**
	 * The '<em><b>OUTPUT CORRECT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_CORRECT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OUTPUT_CORRECT_VALUE = 0;

	/**
	 * The '<em><b>ORCHESTRATION NOT FINAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORCHESTRATION_NOT_FINAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ORCHESTRATION_NOT_FINAL_VALUE = 0;

	/**
	 * The '<em><b>ORCHESTRATION FINAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORCHESTRATION_FINAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ORCHESTRATION_FINAL_VALUE = 0;

	/**
	 * An array of all the '<em><b>Scenario</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final UncertaintyScenario[] VALUES_ARRAY =
		new UncertaintyScenario[] {
			NON_CONFORMANCE_TO_INPUT_INTERFACE,
			IMPRECISE_INPUT_DATA,
			INCORRECT_INPUT_DATA,
			CORRECT_INPUT_DATA,
			SCENARIO_DEFINITION_INCORRECT,
			SCENARIO_DEFINITION_CORRECT,
			METHODOLOGY_ABSTRACTION,
			METHODOLOGY_APPROXIMATION,
			METHODOLOGY_OVER_SIMPLIFICATION,
			METHODOLOGY_CORRECT,
			MODEL_UNDER_SPECIFICATION,
			MODEL_ABSTRACTION,
			MODEL_DISCREPANCY,
			MODEL_CORRECT,
			OUTPUT_ERROR,
			OUTPUT_IMPRECISION,
			OUTPUT_CORRECT,
			ORCHESTRATION_NOT_FINAL,
			ORCHESTRATION_FINAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Scenario</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<UncertaintyScenario> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Scenario</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UncertaintyScenario get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UncertaintyScenario result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Scenario</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UncertaintyScenario getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UncertaintyScenario result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Scenario</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UncertaintyScenario get(int value) {
		switch (value) {
			case NON_CONFORMANCE_TO_INPUT_INTERFACE_VALUE: return NON_CONFORMANCE_TO_INPUT_INTERFACE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private UncertaintyScenario(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //UncertaintyScenario
