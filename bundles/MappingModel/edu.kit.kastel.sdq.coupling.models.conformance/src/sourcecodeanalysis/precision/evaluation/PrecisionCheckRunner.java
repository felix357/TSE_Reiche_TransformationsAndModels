package sourcecodeanalysis.precision.evaluation;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class PrecisionCheckRunner {

	public static void main(String[] args) throws Exception {
		// 1. Paths to your input files

		Path scarFile = Paths.get(
				"C:\\Users\\felix\\Git\\TSE_Reiche_TransformationsAndModels_Fork\\bundles\\MappingModel\\edu.kit.kastel.sdq.coupling.models.conformance\\TravelPlanner\\scar.codeqlscar");

//		Path gtFile = Paths.get(
//				"C:\\Users\\felix\\Git\\TSE_Reiche_TransformationsAndModels_Fork\\bundles\\MappingModel\\edu.kit.kastel.sdq.coupling.models.conformance\\JPMail\\jpmail.groundTruth.xml");

		Path gtFile = Paths.get(
				"C:\\Users\\felix\\Git\\TSE_Reiche_TransformationsAndModels_Fork\\bundles\\MappingModel\\edu.kit.kastel.sdq.coupling.models.conformance\\TravelPlanner\\travelplanner.groundTruth.xml");

		
		// 2. Parse the observed flows from the CodeQL SCAR file
		CodeQLScarParser scarParser = new CodeQLScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		// 3. Parse the ground truth flows
		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		// 4. Run the precision checker
		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		ComparisonResult result = checker.evaluate(observedFlows, groundTruthFlows);

		// 5. Print results
		System.out.println("Precision / Recall evaluation:");
		System.out.println(result);
	}
}
