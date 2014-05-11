Karma-Information-Extraction
============================

## Overview
A RESTful service that wraps multiple Named Entity Recognition (NER) tools providing  
`capabilities` for extracting Person Names, Locations, Dates etc.

The service currently supports two NER tools-  

  
**
1) Stanford CoreNLP NER  
**
  
This API accepts a POST request at `http://karmanlp.isi.edu:8080/ExtractionService/StanfordCoreNLP`   and accepts a JSON document in the body of the request as described later in the document.  
  
**
2) Apache OpenNLP NER  
**
  
This API accepts a POST request at `http://karmanlp.isi.edu:8080/ExtractionService/OpenNLP` and also accepts a JSON document similar to CoreNLP API.  

---
  
  Both APIs support a `GET` request on `/getCapabilities` sub-path that returns the `capabilities` of the tool in a  `JSON` document.  
  
**For Example-**  
  
A `GET` request to `http://karmanlp.isi.edu:8080/ExtractionService/StanfordCoreNLP/getCapabilities` will return the following JSON document-  
```     
[
    {
    	capability: "People"
    },
    {
    	capability: "Places"
    },
    {
    	capability: "Dates"
    }
 ]  
```

The `POST` request takes a JSON document as input in the request body defined in the following format-  
  
  ```
 [
 	{
 		"rowID" : "R4",
    	"text" : "Michael Joseph Jackson was born on August 29, 1958, in  
        Gary,Indiana, to an African-American working-class family. His father, Joseph  
        Jackson, had been a guitarist but had put aside his musical aspirations to  
        provide for his family as a crane operator."
	},
    {
 		"rowID" : "R5",
    	"text" : "Michael Jeffrey Jordan (born February 17, 1963), also known by his  
        initials, MJ is an American former  
        professional basketball player,entrepreneur, and majority owner and chairman  
        of the Charlotte Bobcats."
	},
    {
 		"rowID" : "R6",
    	"text" : "Bertrand Arthur William Russell, was a British philosopher,  
        logician, mathematician, historian, and social critic."
	}
 ]
 ```
   

## Installation and Setup
System Requirements: **Java 1.7, Apache Tomcat 7.0** and above.  
  
  
Import the project `karma-information-extraction` into Eclipse and setup a Tomcat 7.0 server which would be the container in which this application will run.  
  
**Download Instructions for Classifiers and Models**  
  
** 1. Apache OpenNLP Models -**  Go to `http://opennlp.sourceforge.net/models-1.5/` and download the following models:  
> en-sent.bin  
> en-ner-date.bin  
> en-ner-location.bin  
  
  Place these models in the `/WebContent/resources` directory under `karma-information-extraction` .  
  
** 1. Stanford CoreNLP Classifiers -**  Download Stanford's NER package from  `http://nlp.stanford.edu/software/stanford-ner-2014-01-04.zip` and extract the following classifiers from `/classifiers` directory:  
> english.muc.7class.distsim.crf.ser.gz  
> english.muc.7class.distsim.prop  

  
  Place these classifiers in the `/WebContent/classifiers` directory under `karma-information-extraction` .  
  ---
    
    
Deploy the application to Tomcat and start the server from Eclipse. The application and server will be running on port `8080` by default. 


## Demo  
  
Below is a screenshot of Karma with an input file (.xlsx) with Biography of 3 famous personalities-  
  ![Karma](http://i60.tinypic.com/aokh0j.png)  
  
Clicking on _'Extract Entities'_ will open a pop-up where you need to enter the URL for Extraction service. Here, we use the Stanford NER service running on our `http://karmanlp.isi.edu:8080` server. The URLs for Stanford is-  
> http://karmanlp.isi.edu:8080/ExtractionService/StanfordCoreNLP  
  
Screenshot below-  
![Pop-up for Extraction service URL](http://i57.tinypic.com/sgpdw6.png)  
  
Next, Karma will send a `GET` request behind the scenes to `/getCapabilities` sub-path as described previously and displays a selection box where you can select the entities to extract-  
![Capabilities of Extraction service](http://i59.tinypic.com/ns6l0.png)  
  
After you make the selections, Karma adds another column with selected extractions in the worksheet as shown below (here, the selections made were People, Places and Dates)-  
![Extracted entities](http://i57.tinypic.com/2le5aq9.png)  


