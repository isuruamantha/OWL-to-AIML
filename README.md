# OWL-to-AIML

I've created this application for my university project. This basically convert OWl/RDF file to AIML. We've told to create a simple ontology regarding the Cornavirus and using that ontology, create a simple chatbot. Following screenshot shows my ontology. 

![Ontology](https://raw.githubusercontent.com/isuruamantha/OWL-to-AIML/master/images/ontology-protege.png)

For this I've used [Protege](https://protege.stanford.edu/ "Protege"). Additionally to mine the ontology, I've used [Twinkle](http://www.ldodds.com/projects/twinkle/ "Twinkle") as the software to execute SPARQL queries. 

![SPARQL query 1](https://raw.githubusercontent.com/isuruamantha/OWL-to-AIML/master/images/sample-sparql-queries-in-twinkle.png "SPARQL query 1")

![SPARQL query 2](https://raw.githubusercontent.com/isuruamantha/OWL-to-AIML/master/images/sample-sparql-queries-in-twinkle-2.png.png)

After generating the RDF/OWL file from Protege, for converting that to AIML I've created a small java program using [swrlapi](https://github.com/protegeproject/swrlapi/wiki/SQWRLCore#Basic_Queries "swrlapi").  This is how my SQWRL looks like. 

`queryEngine.runSQWRLQuery("Q1", "Treatments(?p) ^ available_for(?p, ?c) ^ name(?p, ?g)-> sqwrl:select(?g)");`

After that using a custom parser I've generated a simple AIML file. (It may require some modifications after generating) . Add below AIML tag to the file

`<aiml version="2.0"><aiml>`

For the chatbot I've used online platform called [Pandorabots](http://pandorabots.com "pandorabots"). Below diagram shows the simple architecture of my little system. 

![Architecture diagram ](https://raw.githubusercontent.com/isuruamantha/OWL-to-AIML/master/images/architecture-diagram.png "Architecture diagram ")

The final outcome of the chatbot would be as follows after importing the AIML to pandora. 

![Chatbot example](https://raw.githubusercontent.com/isuruamantha/OWL-to-AIML/master/images/chat-bot.png "Chatbot example")
