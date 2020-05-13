package org.swrlapi.example;

import org.swrlapi.example.model.AIMLPattern;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Isuru Amantha Siriwardena
 * <p>
 * Create AIML from the mined data from the ontology
 */

public class AIMLParser {

    public static void generateAIML(AIMLPattern aimlPattern) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element category = doc.createElement("category");
            doc.appendChild(category);

            // pattern elements
            Element pattern = doc.createElement("pattern");
            pattern.appendChild(doc.createTextNode(aimlPattern.getPattern()));
            category.appendChild(pattern);

            Element template = doc.createElement("template");
            category.appendChild(template);

            Element ul = doc.createElement("ul");
            template.appendChild(ul);

            for (int i = 0; i < aimlPattern.getAttributes().size(); i++) {
                Element li = doc.createElement("li");
                li.appendChild(doc.createTextNode(aimlPattern.getAttributes().get(i)));
                ul.appendChild(li);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Converted-file.aiml"));

            transformer.transform(source, result);
            System.out.println("File saved!");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    // Can run this this parser as a standalone to create AIML file
    public static void main(String[] args) {
        AIMLPattern aimlPattern = new AIMLPattern();
        aimlPattern.setPattern("what are the symptoms");
        ArrayList<String> symptoms = new ArrayList<>();
        symptoms.add("Fever");
        symptoms.add("Dry cough");
        symptoms.add("Tiredness");
        aimlPattern.setAttributes(symptoms);
        generateAIML(aimlPattern);
    }
}
