package org.emmerson.xsltunit.maven;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.tools.ant.DirectoryScanner;
import org.emmerson.xsltunit.core.XsltUnitException;
import org.emmerson.xsltunit.core.XsltVerifier;
import org.emmerson.xsltunit.core.jaxb.config.XSLTUnitObjectFactory;
import org.emmerson.xsltunit.core.jaxb.config.Suite;
import org.emmerson.xsltunit.core.jaxb.config.Test;
import org.emmerson.xsltunit.core.jaxb.config.Xsltunit;
import org.emmerson.xsltunit.core.jaxb.junit.JUnitObjectFactory;
import org.emmerson.xsltunit.core.jaxb.junit.Testsuites;
import org.emmerson.xsltunit.core.jaxb.junit.ts.Testsuite;

/**
 * Read the file xsltunit-definition.xml and run all verifications defined. This
 * file must be stored in folder src/main/resources/
 * 
 * @author Emmerson
 *
 */
public class XsltVerifierMaven {

	private static final String ROOT = "src/main/resources/";

	/**
	 * Run the validation of project files
	 * @throws XsltUnitException
	 */
	public void run() throws XsltUnitException {
		JUnitObjectFactory jof = new JUnitObjectFactory();
		Testsuites tss = jof.createTestsuites();
		boolean error = false;
		try {
			File file = new File(ROOT + "xsltunit-definition.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(XSLTUnitObjectFactory.class);
			Unmarshaller u = jaxbContext.createUnmarshaller();
			Xsltunit xu = (Xsltunit) u.unmarshal(file);	
			
			for (Suite s : xu.getSuite()) {
				Testsuites.Testsuite ts = jof.createTestsuitesTestsuite();
				ts.setName(s.getKey());
				ts.setTime(new BigDecimal(0));
				
				for (Test t : s.getTest()) {
					if( runTestSuite(jof, s, ts, t) ){
						error = true;
					};
				}
				tss.getTestsuite().add(ts);
			}
		} catch (JAXBException e) {
			throw new XsltUnitException(e);
		}finally{
			try {
				JAXBContext jc = JAXBContext.newInstance(JUnitObjectFactory.class);
				Marshaller m = jc.createMarshaller();
				File o = new File("target/xslt-junit-results.xml");
				m.marshal(tss, o );
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		if(error){
			throw new XsltUnitException("To get more details please review target/xslt-junit-results.xml");
		}
	}

	private boolean runTestSuite(JUnitObjectFactory jof,
			Suite s, Testsuites.Testsuite ts, Test t) {
		
		boolean error = false;
		Testsuite.Testcase tc = jof.createTestsuiteTestcase();
		tc.setClassname("XSLTUnit");
		tc.setName(t.getKey());
		
		XsltVerifier poc = new XsltVerifier(s.getKey(), t.getKey());
		File xslt = new File(ROOT + t.getXslt());
		File []xsd = getFiles(ROOT, t.getXsd());
		String[] files = getListFiles(ROOT, t.getXmlSources());
		for (String filename : files) {
			if( runTestCase(jof, s, ts, t, tc, poc, xslt, xsd, filename) ){
				error = true;
			}
		}
		ts.getTestcase().add(tc);
		return error;
	}

	private boolean runTestCase(JUnitObjectFactory jof,
			Suite s, Testsuites.Testsuite ts, Test t,
			Testsuite.Testcase tc, XsltVerifier poc, File xslt, File[] xsd,
			String filename) {
		boolean error = false;
		File xml = new File(ROOT + filename);
		long ini = System.currentTimeMillis();
		try {
			poc.verifyXSLT(xslt, xml, xsd);
		} catch (XsltUnitException th) {
			Testsuite.Testcase.Error f = jof.createTestsuiteTestcaseError();
			f.setMessage(th.getMessage());
			f.setValue(getStackTrace(th));
			tc.setError(f);
			
			ts.setErrors(ts.getErrors() + 1);
			error = true;
			
		}finally{
			long time = System.currentTimeMillis() - ini;
			tc.setTime(new BigDecimal(time));
			ts.setTime(ts.getTime().add(new BigDecimal(time)));
		}
		return error;
	}

	private String[] getListFiles(String baseDir, String filtro) {
		DirectoryScanner scanner = new DirectoryScanner();
		scanner.setIncludes(new String[] { filtro });
		scanner.setBasedir(new File(baseDir));
		scanner.setCaseSensitive(false);
		scanner.scan();
		return scanner.getIncludedFiles();
	}
	
	private File[] getFiles(String baseDir, String filter) {
		String[] files = getListFiles(baseDir, filter);
		List<File> tmp = new ArrayList<File>();
		for(String f : files){
			tmp.add(new File(baseDir + f));
		}
		return tmp.toArray(new File[]{});
	}
	
	public static String getStackTrace(Throwable aThrowable) {
	    Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	  }

}
