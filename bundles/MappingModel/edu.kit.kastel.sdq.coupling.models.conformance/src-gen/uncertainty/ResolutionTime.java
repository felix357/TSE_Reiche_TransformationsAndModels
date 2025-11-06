/**
 */
package uncertainty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Resolution Time</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see uncertainty.UncertaintyPackage#getResolutionTime()
 * @model
 * @generated
 */
public enum ResolutionTime implements Enumerator {
	/**
	 * The '<em><b>REQUIREMENTS TIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REQUIREMENTS_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	REQUIREMENTS_TIME(0, "REQUIREMENTS_TIME", "REQUIREMENTS_TIME"),

	/**
	 * The '<em><b>DESIGN TIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DESIGN_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	DESIGN_TIME(0, "DESIGN_TIME", "DESIGN_TIME"),

	/**
	 * The '<em><b>REALIZATION TIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REALIZATION_TIME_VALUE
	 * @generated
	 * @ordered
	 */
	REALIZATION_TIME(0, "REALIZATION_TIME", "REALIZATION_TIME"),

	/**
	 * The '<em><b>RUNTIME</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RUNTIME_VALUE
	 * @generated
	 * @ordered
	 */
	RUNTIME(0, "RUNTIME", "RUNTIME"),

	/**
	 * The '<em><b>UNRESOLVABLE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNRESOLVABLE_VALUE
	 * @generated
	 * @ordered
	 */
	UNRESOLVABLE(0, "UNRESOLVABLE", "UNRESOLVABLE");

	/**
	 * The '<em><b>REQUIREMENTS TIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REQUIREMENTS_TIME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REQUIREMENTS_TIME_VALUE = 0;

	/**
	 * The '<em><b>DESIGN TIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DESIGN_TIME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DESIGN_TIME_VALUE = 0;

	/**
	 * The '<em><b>REALIZATION TIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REALIZATION_TIME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int REALIZATION_TIME_VALUE = 0;

	/**
	 * The '<em><b>RUNTIME</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RUNTIME
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RUNTIME_VALUE = 0;

	/**
	 * The '<em><b>UNRESOLVABLE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNRESOLVABLE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNRESOLVABLE_VALUE = 0;

	/**
	 * An array of all the '<em><b>Resolution Time</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ResolutionTime[] VALUES_ARRAY =
		new ResolutionTime[] {
			REQUIREMENTS_TIME,
			DESIGN_TIME,
			REALIZATION_TIME,
			RUNTIME,
			UNRESOLVABLE,
		};

	/**
	 * A public read-only list of all the '<em><b>Resolution Time</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ResolutionTime> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Resolution Time</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ResolutionTime get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ResolutionTime result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Resolution Time</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ResolutionTime getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ResolutionTime result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Resolution Time</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ResolutionTime get(int value) {
		switch (value) {
			case REQUIREMENTS_TIME_VALUE: return REQUIREMENTS_TIME;
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
	private ResolutionTime(int value, String name, String literal) {
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
	
} //ResolutionTime
