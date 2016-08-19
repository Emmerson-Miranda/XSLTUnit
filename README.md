# XSLTUnit

This project allow validate the outputs of XSLT transformations against XSD files and create a JUnit report file. The below image show the output in Eclipse by just click on *xslt-junit-results.xml* file.

![Output example](./images/junit-output.png)



## Generating XSLTUnit projects

XSLTUnit comes with a Maven archetype to create projects easily.

```bash
mvn archetype:generate -B -DarchetypeGroupId=org.emmerson.xsltunit.maven.archetype -DarchetypeArtifactId=xsltunit-maven-archetype -DarchetypeVersion=1.0.0 -DgroupId=org.xsltunit.example -DartifactId=xsltunit-prj1
```

By default the archetype generate a project with a set of examples and configuration working out-of-the-box.


### Structure of a XSLTUnit project

![Structure example](./images/project_structure.png)

By default all the files (XSLT, XSD and XMLs) will be under *src/main/resources* folder and can be organized in folders.



## Configuration

The file *xsltunit-definition.xml* store the association between our XSLT, XSD and XML files and the root element is *xsltunit* and it can contains severals suites.

![Structure example](./images/xsd_root.png)

Each *suite* have a "key"(showed in JUnit report) a "description" and can store multiple tests.

![Structure example](./images/xsd_suite.png)

Each *test* have a "key"(showed in JUnit report) a "description" and other properties like:

* xslt - path to a concrete XSLT file (file under test)
* xsd - path to a concrete XSD or set of XSDs (support Ant file selector expression) to be used to validate the output of XSLT file.
* xml - XML sources to be used by the XSLT file (support Ant file selector expression)


![Structure example](./images/xsd_test.png)


# How to work with XSLTUnit


1. Create the project with XSLTUnit maven archetype
2. Organize your XSD and XML files in folders under *src/main/resources* 
3. Create your XSLT files
4. Configure the file *xsltunit-definition.xml* to make the associations among the files.
5. From command line console, go to your root folder project and run "mvn compile"
6. If the results of run your XSLT are OK you will get a "BUILD SUCCESS", if not you will get an error.
7. Analize the results inspecting the *xslt-junit-results.xml* file.



# Where use XSLTUnit

For example in SOA projects who use lots of XSLTs and needs validate the outputs against WSDLs or XSDs without need the servers started.


# Download and install

```bash
git clone https://github.com/Emmerson-Miranda/XSLTUnit.git
cd XSLTUnit/XSLTUnit
mvn install
```

