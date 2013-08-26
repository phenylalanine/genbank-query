/**
 * Created with Eclipse!
 * User: Jim Miller (JGM)
 * Date: 8/25/13
 */

package edu.pdx.cs.data

import org.junit.Test;

class AnalysisFileMailerTests {

	@Test
	// (JGM) Verify by examining any sent and received email in appropriate accounts.
	void testMailingTextFile() {
		AnalysisFileMailer.sendEmail("jgm2@pdx.edu", "biosql-schema")
	}

}
