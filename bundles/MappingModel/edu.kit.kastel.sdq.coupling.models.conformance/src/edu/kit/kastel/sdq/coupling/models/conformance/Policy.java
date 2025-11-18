package edu.kit.kastel.sdq.coupling.models.conformance;

public class Policy {
    String id;                 // securityLevelAnnotations id
    String securityLevelRef;   // points to appliedSecurityLevel
    String parameterHref;      // Java element affected

    @Override
    public String toString() {
        return "Policy{" +
                "id='" + id + '\'' +
                ", securityLevelRef='" + securityLevelRef + '\'' +
                ", parameterHref='" + parameterHref + '\'' +
                '}';
    }
}
