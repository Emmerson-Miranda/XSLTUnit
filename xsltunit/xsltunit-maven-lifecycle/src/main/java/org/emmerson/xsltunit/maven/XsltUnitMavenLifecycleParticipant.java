package org.emmerson.xsltunit.maven;

import java.util.logging.Logger;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

/**
 * XSLTUnit Maven Lifecycle
 * 
 * @author Emmerson
 *
 */
@Component( role = AbstractMavenLifecycleParticipant.class, hint = "xsltunit")
public class XsltUnitMavenLifecycleParticipant extends AbstractMavenLifecycleParticipant {

	@Requirement
	private Logger logger;

    @Override
    public void afterSessionStart( MavenSession session ) throws MavenExecutionException {
      // start the beer machine
    }


    @Override
    public void afterProjectsRead( MavenSession session ) throws MavenExecutionException {
    	XsltVerifierMaven ver = new XsltVerifierMaven();
		try {
			logger.info("START XSLT UNIT");
			ver.run();
		} catch (Throwable t) {
			throw new MavenExecutionException("XSLTUnit found an error", t);
		} finally {
			logger.info("END XSLT UNIT");
		}
    }
    
}
