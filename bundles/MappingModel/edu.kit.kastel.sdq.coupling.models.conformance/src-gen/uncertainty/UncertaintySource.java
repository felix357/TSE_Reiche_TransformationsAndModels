/**
 */
package uncertainty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Source</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see uncertainty.UncertaintyPackage#getUncertaintySource()
 * @model
 * @generated
 */
public enum UncertaintySource implements Enumerator {
	/**
	 * The '<em><b>INPUT DATA INDUCED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INPUT_DATA_INDUCED_VALUE
	 * @generated
	 * @ordered
	 */
	INPUT_DATA_INDUCED(0, "INPUT_DATA_INDUCED", "INPUT_DATA_INDUCED"),

	/**
	 * The '<em><b>SCENARIO ASSUMPTION INDUCED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCENARIO_ASSUMPTION_INDUCED_VALUE
	 * @generated
	 * @ordered
	 */
	SCENARIO_ASSUMPTION_INDUCED(0, "SCENARIO_ASSUMPTION_INDUCED", "SCENARIO_ASSUMPTION_INDUCED"),

	/**
	 * The '<em><b>METHODOLOGY INDUCED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_INDUCED_VALUE
	 * @generated
	 * @ordered
	 */
	METHODOLOGY_INDUCED(0, "METHODOLOGY_INDUCED", "METHODOLOGY_INDUCED"),

	/**
	 * The '<em><b>MODELING INDUCED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODELING_INDUCED_VALUE
	 * @generated
	 * @ordered
	 */
	MODELING_INDUCED(0, "MODELING_INDUCED", "MODELING_INDUCED"),

	/**
	 * The '<em><b>OUTPUT DATA INDUCED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_DATA_INDUCED_VALUE
	 * @generated
	 * @ordered
	 */
	OUTPUT_DATA_INDUCED(0, "OUTPUT_DATA_INDUCED", "OUTPUT_DATA_INDUCED"),

	/**
	 * The '<em><b>ORCHESTRATION DECISION INDUCED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORCHESTRATION_DECISION_INDUCED_VALUE
	 * @generated
	 * @ordered
	 */
	ORCHESTRATION_DECISION_INDUCED(0, "ORCHESTRATION_DECISION_INDUCED", "ORCHESTRATION_DECISION_INDUCED");

	/**
	 * The '<em><b>INPUT DATA INDUCED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INPUT_DATA_INDUCED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INPUT_DATA_INDUCED_VALUE = 0;

	/**
	 * The '<em><b>SCENARIO ASSUMPTION INDUCED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCENARIO_ASSUMPTION_INDUCED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCENARIO_ASSUMPTION_INDUCED_VALUE = 0;

	/**
	 * The '<em><b>METHODOLOGY INDUCED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHODOLOGY_INDUCED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int METHODOLOGY_INDUCED_VALUE = 0;

	/**
	 * The '<em><b>MODELING INDUCED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODELING_INDUCED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MODELING_INDUCED_VALUE = 0;

	/**
	 * The '<em><b>OUTPUT DATA INDUCED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_DATA_INDUCED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OUTPUT_DATA_INDUCED_VALUE = 0;

	/**
	 * The '<em><b>ORCHESTRATION DECISION INDUCED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ORCHESTRATION_DECISION_INDUCED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ORCHESTRATION_DECISION_INDUCED_VALUE = 0;

	/**
	 * An array of all the '<em><b>Source</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final UncertaintySource[] VALUES_ARRAY =
		new UncertaintySource[] {
			INPUT_DATA_INDUCED,
			SCENARIO_ASSUMPTION_INDUCED,
			METHODOLOGY_INDUCED,
			MODELING_INDUCED,
			OUTPUT_DATA_INDUCED,
			ORCHESTRATION_DECISION_INDUCED,
		};

	/**
	 * A public read-only list of all the '<em><b>Source</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<UncertaintySource> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Source</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UncertaintySource get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UncertaintySource result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Source</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UncertaintySource getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UncertaintySource result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Source</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UncertaintySource get(int value) {
		switch (value) {
			case INPUT_DATA_INDUCED_VALUE: return INPUT_DATA_INDUCED;
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
	private UncertaintySource(int value, String name, String literal) {
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
	
} //UncertaintySource
