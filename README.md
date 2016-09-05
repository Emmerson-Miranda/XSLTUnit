# XSLTUnit (XSLT Unit testing)

This project enable validation of the outputs of XSLT transformations against XSD files and create a JUnit report file, in other words this project allow you validate the results of your XSLTs are full-compliant with their target schemas, or even detect possible problems in production environments when the schemas definition changes.

This project support JSON files a source files converting all of them to XML files.

The below image show the output in Eclipse by just click on *xslt-junit-results.xml* file.

![Output example](./images/junit-output.png)

### Structure proposed to a XSLTUnit project

By default all the files (XSLT, XSD and XMLs) will be under *src/main/resources* folder and can be organized in folders as you need.

### Analizing results

![Structure example](./images/mvn_compile.png)

To analize just run "mvn compile" in your project, after that you can analize the reports and even the outputs of the transformations (have a look in the *target/xslt-outputs*).


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


# Download and Install

```bash
git clone https://github.com/Emmerson-Miranda/XSLTUnit.git
cd XSLTUnit/xsltunit
mvn install
```

## Requirements

* Java 1.8
* Maven 3


# More info
Please review wiki pages
