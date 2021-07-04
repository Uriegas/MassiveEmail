package com.uriegas;

import com.uriegas.Model.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
/**
 * Test the sending of various mails (3 in this case)
 */
@RunWith(Parameterized.class)
public class MailsTest{
	Mail mail;
    /**
     * Constructor with expected value injection
     * @param e expected table
     */
    public MailsTest( Mail mail ){
		this.mail = mail;
    }
	/**
	 * Setup session: sender email
	 */
	@Before
	public void setUp() throws Exception{//After this I need to change my password 
		UseJavaMail.Login( new Account("1930526@upv.edu.mx", "dgy5k95IhfszaffD4qXSpczR") );
	}
    /**
     * Parameters to add to the test: array of sending emails
     * @return Collection of tables
     */
    @Parameterized.Parameters
    public static Collection<Mail> getTestData(){
		HashMap<String, String> vars = new HashMap<String, String>();
		vars.put("liz", "Lizbeth Pozos Yañez");

        return Arrays.asList(
			new Mail("1930526@upv.edu.mx", "Este es un test", "I love <liz>", vars , null),
			new Mail("jesusuriegas18@gmail.com", "Este es un test", "I love <liz>", vars , null),
			new Mail("jesusuriegas19@hotmail.com", "Este es un test", "I love <liz>", vars , null)
		);
	}
	/**
	 * Test if a test could be sent: manual check if the sended email was correctly sent
	 * In following versions it could be added code to receive a mail from an user to compare the expected sended with the received
	 */
    @Test
    public void testMail() throws Exception {
		assertTrue( mail.send() );
	}
}
