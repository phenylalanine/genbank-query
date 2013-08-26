/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/25/13
 */

package edu.pdx.cs.data

import org.apache.commons.mail.*

class AnalysisFileMailer {
	private static final String host = "smtp.gmail.com"
	private static final int port = 465
	private static final String senderName = "GenBank-Query Service"
	private static final String senderAddress = "genbank.query.service@gmail.com"
	private static final String senderPassword = "phenylalanine"
	private static final String subject = "GenBank-Query analysis results for you!"
	
	static void sendEmail(String destAddress, String resultsFilePath) {
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(resultsFilePath);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
	  
		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(host)
		email.setSmtpPort(port)
		email.setAuthenticator(new DefaultAuthenticator(senderAddress, senderPassword))
		email.setSSLOnConnect(true)
		email.setFrom(senderAddress)
		email.setSubject(subject)
		email.addTo(destAddress)
		
		email.attach(attachment)
		
		email.send()
	}
}
