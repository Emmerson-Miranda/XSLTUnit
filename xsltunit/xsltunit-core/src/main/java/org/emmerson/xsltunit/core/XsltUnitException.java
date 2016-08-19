package org.emmerson.xsltunit.core;

/**
 * 
 * @author Emmerson
 *
 */
public class XsltUnitException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7368119368190387779L;

	public XsltUnitException() {
	}

	public XsltUnitException(String message) {
		super(message);
	}

	public XsltUnitException(Throwable cause) {
		super(cause);
	}

	public XsltUnitException(String message, Throwable cause) {
		super(message, cause);
	}

	public XsltUnitException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
