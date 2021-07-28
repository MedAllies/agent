package org.nhindirect.stagent.trust;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.security.cert.X509Certificate;

import org.nhindirect.stagent.NHINDException;
import org.nhindirect.stagent.utils.TestUtils;


public class TrustChainValidator_downloadCertFromAIATest
{
	protected String filePrefix;
	
	@BeforeEach
	public void setUp()
	{	
		// check for Windows... it doens't like file://<drive>... turns it into FTP
		File file = new File("./src/test/resources/certs/bob.der");
		if (file.getAbsolutePath().contains(":/"))
			filePrefix = "file:///";
		else
			filePrefix = "file:///";
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testDownloadCertFromAIA_validURL_assertDownloaded() throws Exception
	{
		final TrustChainValidator validator = new TrustChainValidator();
		
		final File fl = new File("src/test/resources/certs/bob.der");
		
		final X509Certificate downloadedCert = validator.downloadCertFromAIA(filePrefix + fl.getAbsolutePath());
		
		assertNotNull(downloadedCert);
		
		assertEquals(TestUtils.loadCertificate("bob.der"), downloadedCert);
	}
	
	@Test
	@SuppressWarnings("deprecation")
	public void testDownloadCertFromAIA_certNotAtURL_assertException() throws Exception
	{
		final TrustChainValidator validator = new TrustChainValidator();
		
		final File fl = new File("src/test/resources/certs/bob.derdd");
		
		boolean exceptionOccurred = false;
		
		try
		{
			validator.downloadCertFromAIA(filePrefix + fl.getAbsolutePath());
		}
		catch (NHINDException e)
		{
			exceptionOccurred = true;
		}
		
		assertTrue(exceptionOccurred);
	}
}
