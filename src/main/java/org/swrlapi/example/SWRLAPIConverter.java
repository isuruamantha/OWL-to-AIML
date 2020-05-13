package org.swrlapi.example;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.swrlapi.example.model.AIMLPattern;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Isuru Amantha Siriwardena
 * <p>
 * Extract information from OWL/RDF file and then convert to AIML
 */

public class SWRLAPIConverter {

    public static String WHAT_ARE_THE_TREATMENTS = "What are the treatments?";

    public static void main(String[] args) {

        try {
            // Create an OWL ontology using the OWLAPI
            OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
            //Use your own RDF/OWL file
            OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/coronavirus.rdf"));

            // Create SQWRL query engine using the SWRLAPI
            SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);

            // Create and execute a SQWRL query using the SWRLAPI
            SQWRLResult treatmentResult = queryEngine.runSQWRLQuery("Q1", "Treatments(?p) ^ available_for(?p, ?c) ^ name(?p, ?g)-> sqwrl:select(?g)");
            // SQWRLResult symptomResult = queryEngine.runSQWRLQuery("Q2", "coronavirus:Symptoms(?p) ^ coronavirus:shows_by(?p, ?c) ^ coronavirus:behaviour(?p, ?g) -> sqwrl:select(?g");
            // SQWRLResult precautionResult = queryEngine.runSQWRLQuery("Q3", "coronavirus:Precautions(?p) ^ coronavirus:for(?p, ?c) ^ coronavirus:behaviour(?p, ?g) -> sqwrl:select(?g)");
            // SQWRLResult detailsResult = queryEngine.runSQWRLQuery("Q3", "coronavirus:Details(?p) ^ coronavirus:of(?p, ?c) ^ coronavirus:details(?p, ?g) -> sqwrl:select(?g)");
            // SQWRLResult methodResult = queryEngine.runSQWRLQuery("Q3", "coronavirus:Method(?p) ^ coronavirus:of_spreading(?p, ?c) ^ coronavirus:details(?p, ?g) -> sqwrl:select(?g)");

            ArrayList<String> treatments = new ArrayList<>();
            // Process the SQWRL result
            while (treatmentResult.next()) {
                treatments.add(treatmentResult.getValue("g").asLiteralResult().getOWLLiteral().getLiteral());
            }

            AIMLPattern aimlPattern = new AIMLPattern();
            aimlPattern.setPattern(WHAT_ARE_THE_TREATMENTS);
            aimlPattern.setAttributes(treatments);

            // Create the AIML
            AIMLParser.generateAIML(aimlPattern);

        } catch (OWLOntologyCreationException e) {
            System.err.println("Error creating OWL ontology: " + e.getMessage());
            System.exit(-1);
        } catch (SWRLParseException e) {
            System.err.println("Error parsing SWRL rule or SQWRL query: " + e.getMessage());
            System.exit(-1);
        } catch (SQWRLException e) {
            System.err.println("Error running SWRL rule or SQWRL query: " + e.getMessage());
            System.exit(-1);
        } catch (RuntimeException e) {
            System.err.println("Error starting application: " + e.getMessage());
            System.exit(-1);
        }
    }

}
