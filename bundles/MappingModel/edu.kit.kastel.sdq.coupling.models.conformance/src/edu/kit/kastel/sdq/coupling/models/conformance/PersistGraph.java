package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.AnalysisGraph;
import analysiscouplinggraph.AnalysiscouplinggraphFactory;
import analysiscouplinggraph.Connection;
import analysiscouplinggraph.ProvidedInterface;
import analysiscouplinggraph.RequiredInterface;
import mapping.MappingDefinition;
import mapping.MappingPackage;
import uncertainty.UncertaintyFactory;

public class PersistGraph {

    public static void main(String[] args) throws IOException {
        AnalysiscouplinggraphFactory graphFactory = AnalysiscouplinggraphFactory.eINSTANCE;
        UncertaintyFactory uncertaintyFactory = UncertaintyFactory.eINSTANCE;

        // ---------------------------
        // ResourceSet erstellen
        // ---------------------------
        ResourceSet resSet = new ResourceSetImpl();
        resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());

        // ---------------------------
        // MappingPackage registrieren
        // ---------------------------
        MappingPackage.eINSTANCE.eClass();
        resSet.getPackageRegistry().put(MappingPackage.eNS_URI, MappingPackage.eINSTANCE);

        // ---------------------------
        // Alle relevanten EPackages laden
        // ---------------------------
        registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
                "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.identifier/model/identifier.ecore"));
 
        registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
                "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.java/model/java.ecore"));

        registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
                "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/DataDictionaryCharacterized.ecore"));

        registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
                "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/pcm.ecore"));

        // Input-Referenzmetamodell
        EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
                "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");
        registerEPackageRecursively(resSet, inputRefMeta);

        // Output-Referenzmetamodell
        EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
                "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");
        registerEPackageRecursively(resSet, outputRefMeta);

        // CodeQL-spezifische Ecore
        registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
                "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.codeql/model/codeql.ecore"));
        
     // parameterannotation-spezifische Ecore
        registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
                "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.extension.dataflowanalysis.parameterannotation/model/parameterannotation.ecore"));

        // ---------------------------
        // Mapping laden
        // ---------------------------
        String codeqlMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping.xmi";
        MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

        String codeqlOutputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlOutputMapping.xmi";
        MappingDefinition codeqlOutputMapping = loadMapping(resSet, codeqlOutputMappingPath);

        String edfaInputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/EDFAInputMappingTest.xmi";
        MappingDefinition edfaInputMapping = loadMapping(resSet, edfaInputMappingPath);
        
        // ---------------------------
        // Proxies auflösen
        // ---------------------------
        EcoreUtil.resolveAll(resSet);

        // Debug: prüfen ob TargetClasses korrekt aufgelöst wurden
        codeqlInputMapping.getClassMappings().forEach(cm -> {
            EClass target = cm.getTargetClass();
            if (target != null && !target.eIsProxy()) {
                System.out.println("[DEBUG] Target class resolved: " + target.getName());
            } else {
                System.err.println("[DEBUG] Target class unresolved: " + EcoreUtil.getURI(target));
            }
        });

        // ---------------------------
        // AnalysisGraph bauen
        // ---------------------------
        AnalysisComponent codeql = graphFactory.createAnalysisComponent();
        codeql.setName("CodeQL");

        AnalysisComponent edfa = graphFactory.createAnalysisComponent();
        edfa.setName("EDFA");

        ProvidedInterface codeqlProvided = graphFactory.createProvidedInterface();
        codeqlProvided.setOwner(codeql);
        codeqlProvided.setMappingModel(codeqlOutputMapping);

        RequiredInterface codeqlRequired = graphFactory.createRequiredInterface();
        codeqlRequired.setOwner(codeql);
        codeqlRequired.setMappingModel(codeqlInputMapping);

        RequiredInterface edfaRequired = graphFactory.createRequiredInterface();
        edfaRequired.setOwner(edfa);

        codeql.getOutputs().add(codeqlProvided);
        codeql.getInputs().add(codeqlRequired);

        edfa.getInputs().add(edfaRequired);
        edfa.getOutputs().add(graphFactory.createProvidedInterface());

        Connection conn = graphFactory.createConnection();
        conn.setFrom(codeqlProvided);
        conn.setTo(edfaRequired);

        AnalysisGraph graph = graphFactory.createAnalysisGraph();
        graph.getComponents().add(codeql);
        graph.getComponents().add(edfa);
        graph.getConnections().add(conn);

        // ---------------------------
        // Conformance-Check direkt mit den EPackages aus dem ResourceSet
        // ---------------------------
        
        boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(
                edfaInputMapping, inputRefMeta);
        
        boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(
        		codeqlInputMapping, inputRefMeta);
        
        boolean codeqlOutputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(
        		codeqlOutputMapping, outputRefMeta);

        if (codeqlInputConforms && codeqlOutputConforms && edfaInputConforms) {
            System.out.println("Alle Mappingklassen sind konform!");
        } else {
            System.out.println("Mindestens eine Mappingklasse ist nicht konform!");
        }
    }

    private static void registerEPackageRecursively(ResourceSet resSet, EPackage pkg) {
        if (pkg == null) return;
        resSet.getPackageRegistry().put(pkg.getNsURI(), pkg);
        System.out.println("EPackage registriert: " + pkg.getName() + " (nsURI=" + pkg.getNsURI() + ")");
        for (EPackage subPkg : pkg.getESubpackages()) {
            registerEPackageRecursively(resSet, subPkg);
        }
    }

    private static EPackage loadAndRegisterEPackage(ResourceSet resSet, String path) {
        try {
            Resource res = resSet.getResource(URI.createFileURI(path), true);
            res.load(Collections.EMPTY_MAP);
            EPackage pkg = (EPackage) res.getContents().get(0);
            registerEPackageRecursively(resSet, pkg);
            System.out.println("EPackage geladen: " + pkg.getName() + " (nsURI=" + pkg.getNsURI() + ")");
            return pkg;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Fehler beim Laden des EPackage: " + path);
            return null;
        }
    }

    private static MappingDefinition loadMapping(ResourceSet resSet, String mappingFilePath) {
        try {
            Resource mappingResource = resSet.getResource(URI.createFileURI(mappingFilePath), true);
            mappingResource.load(Collections.EMPTY_MAP);
            MappingDefinition mapping = (MappingDefinition) mappingResource.getContents().get(0);
            System.out.println("Mapping XMI erfolgreich geladen: " + mappingFilePath);
            return mapping;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Fehler beim Laden der Mapping XMI-Datei: " + mappingFilePath);
            return null;
        }
    }
}
