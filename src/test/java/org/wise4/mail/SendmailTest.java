package org.wise4.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SendmailTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SendmailTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SendmailTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testSendmail()
    {
		final Properties props = new Properties();
		try {
			props.load(new FileInputStream("src/main/resources/mail.properties"));
		} catch (FileNotFoundException e1) {
			System.err.println("Properties file not found");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.err.println("Error loading properties file");
			e1.printStackTrace();
		}
		
		Sendmail.send(props);		
	}
}
