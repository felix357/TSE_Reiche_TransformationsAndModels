/**
 */
package uncertainty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Location</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see uncertainty.UncertaintyPackage#getLocation()
 * @model
 * @generated
 */
public enum Location implements Enumerator {
	/**
	 * The '<em><b>EXTERNAL INPUT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXTERNAL_INPUT_VALUE
	 * @generated
	 * @ordered
	 */
	EXTERNAL_INPUT(0, "EXTERNAL_INPUT", "EXTERNAL_INPUT"),

	/**
	 * The '<em><b>INTERNAL ANALYSIS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTERNAL_ANALYSIS_VALUE
	 * @generated
	 * @ordered
	 */
	INTERNAL_ANALYSIS(0, "INTERNAL_ANALYSIS", "INTERNAL_ANALYSIS"),

	/**
	 * The '<em><b>COUPLING OUTPUT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COUPLING_OUTPUT_VALUE
	 * @generated
	 * @ordered
	 */
	COUPLING_OUTPUT(0, "COUPLING_OUTPUT", "COUPLING_OUTPUT");

	/**
	 * The '<em><b>EXTERNAL INPUT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXTERNAL_INPUT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EXTERNAL_INPUT_VALUE = 0;

	/**
	 * The '<em><b>INTERNAL ANALYSIS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTERNAL_ANALYSIS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INTERNAL_ANALYSIS_VALUE = 0;

	/**
	 * The '<em><b>COUPLING OUTPUT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COUPLING_OUTPUT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COUPLING_OUTPUT_VALUE = 0;

	/**
	 * An array of all the '<em><b>Location</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final Location[] VALUES_ARRAY =
		new Location[] {
			EXTERNAL_INPUT,
			INTERNAL_ANALYSIS,
			COUPLING_OUTPUT,
		};

	/**
	 * A public read-only list of all the '<em><b>Location</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<Location> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Location</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Location get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Location result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Location</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Location getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Location result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Location</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static Location get(int value) {
		switch (value) {
			case EXTERNAL_INPUT_VALUE: return EXTERNAL_INPUT;
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
	private Location(int value, String name, String literal) {
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
	
} //Location
