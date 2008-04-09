package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.model.fachklassen.beans.PersonBean.Titel;
import de.randi2.utility.Log4jInit;
import de.randi2.utility.NullKonstanten;

/**
 * Die JUnit Klasse PersonBeanTest testet alle SetterMethoden der Klasse
 * PersonBean. Auf ein Testen der getter-Methoden kann verzichtet werden, da
 * dortige Fehler auf Kompilierebene liegen muessten.
 * 
 * @author Tino Noack [tnoack@stud.hs-heilbronn.de]
 * @version $Id: PersonBeanTest.java 2145 2007-04-24 15:32:04Z btheel $
 */
public class PersonBeanTest {

	private PersonBean testPB; // Objekt der Klasse PersonBean

	/**
	 * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
	 * es funktioniert.
	 * 
	 */
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}

	/**
	 * Erzeugt eine neue Instanz der Klasse PersonBean.
	 */
	@Before
	public void setUp() throws Exception {

		testPB = new PersonBean();
	}

	/**
	 * Weist dem PersonBean-Objekt den Wert "null" zu.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {

		testPB = null;
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Emailparameter
	 * null ist (da Pflichtfeld). Filter deaktiviert.
	 */
	@Test
	public void testSetEmailNullF() {
		try {
			testPB.setFilter(false);
			testPB.setEmail(null);
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.EMAIL_FEHLT, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Emailparameter
	 * null ist (da Pflichtfeld). Filter aktiviert.
	 */
	@Test
	public void testSetEmailNullT() {
		try {
			testPB.setFilter(true);
			testPB.setEmail(null);

		} catch (Exception e) {
			fail("Darf eigentlich keine Exception schmeissen.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Emailparameter
	 * ein leerer String ist. Filter deaktiviert.
	 */
	@Test
	public void testSetEmailLeerF() {

		try {
			testPB.setFilter(false);
			testPB.setEmail("");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.EMAIL_FEHLT, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Emailparameter
	 * ein leerer String ist. Filter aktiviert.
	 */
	@Test
	public void testSetEmailLeerT() {

		try {
			testPB.setFilter(true);
			testPB.setEmail("");

		} catch (Exception e) {
			fail("Darf eigentlich keine Exception schmeissen.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Emailparameter
	 * laenger als in der Definition vereinbart ist. Filter deaktiviert.
	 */
	@Test
	public void testSetEmailUeberlangF() {
		try {
			testPB.setFilter(false);
			testPB
					.setEmail("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa."
							+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
							+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaa"
							+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
							+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
							+ "aaaaaaaaaaaaaaaaaaaa");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.EMAIL_UNGUELTIG, pe.getMessage());
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Emailparameter
	 * laenger als in der Definition vereinbart ist. Filter aktiviert.
	 */
	@Test
	public void testSetEmailUeberlangT() {
		try {
			testPB.setFilter(true);
			testPB
					.setEmail("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa."
							+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
							+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaa"
							+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
							+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
							+ "aaaaaaaaaaaaaaaaaaaa");

		} catch (Exception e) {
			fail("Darf eigentlich keine Exception schmeissen.");
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Emailparameter
	 * nicht per Definition zugelassene Zeichen enthaelt.
	 */
	@Test
	public void testSetEmailSonderzeichen() {
		try {
			testPB.setEmail("���.�@&$.---");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.EMAIL_UNGUELTIG, pe.getMessage());
		}

	}

	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der Emailparameter
	 * korrekt per Definition ist.
	 */
	@Test
	public void testSetEmailKorrekt() {

		try {
			testPB.setEmail("blub@bla-blub.net");
			testPB.setEmail("a.b@d.de");

		} catch (Exception e) {
			fail("Exception geworfen bei korrekter Emailadresse");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Faxparameter ein
	 * leerer String ist.
	 */
	@Test
	public void testSetFaxLeer() {
		try {
			testPB.setFax("");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.FAX_UNGUELTIG, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Faxparameter mehr
	 * oder weniger Zeichen enthaelt als per Definition vereinbart.
	 */
	@Test
	public void testSetFaxLaenge() {

		try {
			testPB.setFax("1/1");
			testPB.setFax("1/1234");
			testPB.setFax("1/1234567890123456");
			testPB.setFax("123/1");
			testPB.setFax("123/1234567890123456");
			testPB.setFax("12345678901/1");
			testPB.setFax("12345678901/1234");
			testPB.setFax("12345678901/1234567890123456");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.FAX_UNGUELTIG, pe.getMessage());
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Faxparameter
	 * nicht per Definition zugelassene Zeichen enthaelt.
	 */
	@Test
	public void testSetFaxSonderzeichen() {
		try {
			testPB.setFax("we/ergfe");
			testPB.setFax("�$�%.)(9");
			testPB.setFax("0908/56477-");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.FAX_UNGUELTIG, pe.getMessage());
		}

	}

	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der Faxparameter korrekt
	 * per Definition ist.
	 */
	@Test
	public void testSetFaxKorrekt() {
		try {
			testPB.setFax(null);
			testPB.setFax("012/123456");

		} catch (Exception e) {
			fail("Exception geworfen bei korrekter Faxnummer.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Geschlechtsparameter nicht per Definition zugelassen ist.
	 */
	@Test
	public void testSetGeschlechtFehltF() {

		try {
			testPB.setFilter(false);
			testPB.setGeschlecht(NullKonstanten.NULL_CHAR);
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.GESCHLECHT_FEHLT, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Geschlechtsparameter nicht per Definition zugelassen ist.
	 */
	@Test
	public void testSetGeschlechtFehltT() {

		try {
			testPB.setFilter(true);
			testPB.setGeschlecht(NullKonstanten.NULL_CHAR);

		} catch (Exception e) {
			fail("Darf eigentlich keine Exception schmeissen.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Geschlechtsparameter nicht per Definition zugelassen ist.
	 */
	@Test
	public void testSetGeschlechtFalsch() {

		try {
			testPB.setGeschlecht(' ');
			testPB.setGeschlecht('0');
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.GESCHLECHT_UNGUELTIG, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der Geschlechtsparameter
	 * korrekt per Definition ist.
	 */
	@Test
	public void testSetGeschlechtKorrekt() {

		try {
			testPB.setGeschlecht('w');
			testPB.setGeschlecht('m');

		} catch (Exception e) {
			fail("Exception geworfen bei korrekter Geschlechtseingabe.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Handynummerparameter ein leerer String ist.
	 */
	@Test
	public void testSetHandynummerLeer() {
		try {
			testPB.setHandynummer("");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.HANDY_UNGUELTIG, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Handynummerparameter laenger oder kuerzer als per Definition vereinbart
	 * ist.
	 */
	@Test
	public void testSetHandynummerLaenge() {
		try {
			testPB.setHandynummer("1/1");
			testPB.setHandynummer("1/1234");
			testPB.setHandynummer("1/1234567890123456");
			testPB.setHandynummer("123/1");
			testPB.setHandynummer("123/1234567890123456");
			testPB.setHandynummer("12345678901/1");
			testPB.setHandynummer("12345678901/1234");
			testPB.setHandynummer("12345678901/1234567890123456");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.HANDY_UNGUELTIG, pe.getMessage());
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Handynummerparameter nicht zugelassene Sonderzeichen enthealt.
	 */
	@Test
	public void testSetHandynummerSonderzeichen() {
		try {
			testPB.setHandynummer("we/ergfe");
			testPB.setHandynummer("�$�%.)(9");
			testPB.setHandynummer("0908/56477-");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.HANDY_UNGUELTIG, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der Handynummerparameter
	 * korrekt per Definition ist.
	 */
	@Test
	public void testSetHandynummerKorrekt() {
		try {
			testPB.setHandynummer(null);
			testPB.setHandynummer("0127/123456");
			testPB.setHandynummer("00498/35682345678");

		} catch (Exception e) {
			fail("Exception geworfen bei korrekter Handynummer.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Nachnameparameter
	 * null ist (da Pflichtfeld). Filter deaktiviert.
	 */
	@Test
	public void testSetNachnameNullF() {
		try {
			testPB.setFilter(false);
			testPB.setNachname(null);
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.NACHNAME_FEHLT, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Nachnameparameter
	 * null ist (da Pflichtfeld). Filter aktiviert.
	 */
	@Test
	public void testSetNachnameNullT() {
		try {
			testPB.setFilter(true);
			testPB.setNachname(null);
		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Nachnameparameter
	 * ein leerer String ist. Filter deaktiviert.
	 */
	@Test
	public void testSetNachnameLeerF() {
		try {
			testPB.setFilter(false);
			testPB.setNachname("");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.NACHNAME_FEHLT, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Nachnameparameter
	 * ein leerer String ist. Filter aktiviert.
	 */
	@Test
	public void testSetNachnameLeerT() {
		try {
			testPB.setFilter(true);
			testPB.setNachname("");

		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Nachnameparameter
	 * laenger oder kuerzer als per Definition vereinbart ist. Filter
	 * deaktiviert.
	 */
	@Test
	public void testSetNachnameLaengeF() {
		try {
			testPB.setFilter(false);
			testPB.setNachname("X");
			testPB.setNachname("abcdefghijklmopqrstuvwxyzabcdefghijk"
					+ "lmopqrstuvwxyzabcdefghijklmopqrstuvwxyz");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.NACHNAME_UNGUELTIG, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Nachnameparameter
	 * laenger oder kuerzer als per Definition vereinbart ist. Filter aktiviert.
	 */
	@Test
	public void testSetNachnameLaengeT() {
		try {
			testPB.setFilter(true);
			testPB.setNachname("X");
			testPB.setNachname("abcdefghijklmopqrstuvwxyzabcdefghijk"
					+ "lmopqrstuvwxyzabcdefghijklmopqrstuvwxyz");

		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}

	}

	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der Nachnameparameter
	 * korrekt per Definition ist.
	 */
	@Test
	public void testSetNachnameKorrekt() {

		try {
			testPB.setNachname("Jameson");
			testPB.setNachname("Engelen-Kefer");
			testPB.setNachname("Anding Rost");
		} catch (Exception e) {
			fail("Exception aufgetreten trotz richtiger Parameter.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Telefonnummerparameter null ist (da Pflichtfeld). Filter deaktiviert.
	 */
	@Test
	public void testSetTelefonnummerNullF() {
		try {
			testPB.setFilter(false);
			testPB.setTelefonnummer(null);
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.TELEFONNUMMER_FEHLT, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Telefonnummerparameter null ist (da Pflichtfeld). Filter aktiviert.
	 */
	@Test
	public void testSetTelefonnummerNullT() {
		try {
			testPB.setFilter(true);
			testPB.setTelefonnummer(null);
		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Telefonnummerparameter null ist (da Pflichtfeld). Filter deaktiviert.
	 */
	@Test
	public void testSetTelefonnummerLeerF() {
		try {
			testPB.setFilter(false);
			testPB.setTelefonnummer("");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.TELEFONNUMMER_FEHLT, pe.getMessage());
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Telefonnummerparameter null ist (da Pflichtfeld). Filter aktiviert.
	 */
	@Test
	public void testSetTelefonnummerLeerT() {
		try {
			testPB.setFilter(true);
			testPB.setTelefonnummer("");
		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Telefonnummerparameter laenger oder kuerzer als per Definition vereinbart
	 * ist. Filter deaktiviert.
	 */
	@Test
	public void testSetTelefonnummerLaengeF() {
		try {
			testPB.setFilter(false);
			testPB.setTelefonnummer("1/1");
			testPB.setTelefonnummer("1/1234");
			testPB.setTelefonnummer("1/1234567890123456");
			testPB.setTelefonnummer("123/1");
			testPB.setTelefonnummer("123/1234567890123456");
			testPB.setTelefonnummer("12345678901/1");
			testPB.setTelefonnummer("12345678901/1234");
			testPB.setTelefonnummer("12345678901/1234567890123456");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.TELEFONNUMMER_UNGUELTIG, pe
					.getMessage());
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Telefonnummerparameter laenger oder kuerzer als per Definition vereinbart
	 * ist. Filter aktiviert.
	 */
	@Test
	public void testSetTelefonnummerLaengeT() {
		try {
			testPB.setFilter(true);
			testPB.setTelefonnummer("1/1");
			testPB.setTelefonnummer("1/1234");
			testPB.setTelefonnummer("1/1234567890123456");
			testPB.setTelefonnummer("123/1");
			testPB.setTelefonnummer("123/1234567890123456");
			testPB.setTelefonnummer("12345678901/1");
			testPB.setTelefonnummer("12345678901/1234");
			testPB.setTelefonnummer("12345678901/1234567890123456");

		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der
	 * Telefonnummerparameter nicht zugelassene Sonderzeichen enthealt.
	 */
	@Test
	public void testSetTelefonnummerSonderzeichen() {
		try {
			testPB.setTelefonnummer("we/ergfe");
			testPB.setTelefonnummer("�$�%.)(9");
			testPB.setTelefonnummer("0908/56477-");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.TELEFONNUMMER_UNGUELTIG, pe
					.getMessage());
		}

	}

	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der
	 * Telefonnummerparameter korrekt per Definition ist.
	 */
	@Test
	public void testSetTelefonnummerKorrekt() {
		try {
			testPB.setTelefonnummer("012/123456");
			testPB.setTelefonnummer("0023/35682345678");
			testPB.setTelefonnummer("+4922237033");

		} catch (Exception e) {
			fail("Exception geworfen bei korrekter Telefonnummer.");
		}
	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Vornameparameter
	 * null ist (da Pflichtfeld). Filter deaktiviert.
	 */
	@Test
	public void testSetVornameNullF() {
		try {
			testPB.setFilter(false);
			testPB.setVorname(null);
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.VORNAME_FEHLT, pe.getMessage());
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Vornameparameter
	 * null ist (da Pflichtfeld). Filter aktiviert.
	 */
	@Test
	public void testSetVornameNullT() {
		try {
			testPB.setFilter(true);
			testPB.setVorname(null);

		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Vornameparameter
	 * ein leerer String ist. Filter deaktiviert.
	 */
	@Test
	public void testSetVornameLeerF() {
		try {
			testPB.setFilter(false);
			testPB.setVorname("");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.VORNAME_FEHLT, pe.getMessage());
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Vornameparameter
	 * ein leerer String ist. Filter aktiviert.
	 */
	@Test
	public void testSetVornameLeerT() {
		try {
			testPB.setFilter(true);
			testPB.setVorname("");

		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Vornameparameter
	 * laenger oder kuerzer als per Definition vereinbart ist. Filter
	 * deaktiviert.
	 */
	@Test
	public void testSetVornameLaengeF() {
		try {
			testPB.setFilter(false);
			testPB.setVorname("X");
			testPB.setVorname("abcdefghijklmopqrstuvwxyzabcdefghijklmopqrst"
					+ "uvwxyzabcdefghijklmopqrstuvwxyz");
			fail();
		} catch (PersonException pe) {
			assertEquals(PersonException.VORNAME_UNGUELTIG, pe.getMessage());
		}

	}

	/**
	 * Testet, ob eine PersonException geworfen wird, wenn der Vornameparameter
	 * laenger oder kuerzer als per Definition vereinbart ist. Filter aktiviert.
	 */
	@Test
	public void testSetVornameLaengeT() {
		try {
			testPB.setFilter(true);
			testPB.setVorname("X");
			testPB.setVorname("abcdefghijklmopqrstuvwxyzabcdefghijklmopqrst"
					+ "uvwxyzabcdefghijklmopqrstuvwxyz");

		} catch (Exception e) {
			fail("Sollte keine Exception schmeissen.");
		}

	}

	/**
	 * Testet, ob eine Exception geworfen wird, obwohl der Vornameparameter
	 * korrekt per Definition ist.
	 */
	@Test
	public void testSetVornameKorrekt() {

		try {
			testPB.setVorname("Markus");
			testPB.setVorname("Jan Josef");
			testPB.setVorname("Elisabeth-Sophie");
		} catch (Exception e) {
			fail("Exception aufgetreten trotz richtiger Parameter.");
		}
	}

	/**
	 * Testet den TitelPerser der TitelEnum
	 */
	@Test
	public void testTitelParser() {
		PersonBean.Titel testTitel;
		try {
			for (PersonBean.Titel aTitel : PersonBean.Titel.values()) {
				testTitel = PersonBean.Titel.parseTitel(aTitel.toString());
				assertEquals(testTitel, aTitel);
				assertEquals(testTitel.toString(), aTitel.toString());
			}
		} catch (Exception e) {
			fail("Parsertest fehlgeschlagen");
		}

		try {
			PersonBean.Titel.parseTitel("Holla, die Waldfee!");
			fail("Sollte Exception auslösen");
		} catch (Exception e) {
		}

	}

	/**
	 * Testet die equals Methode an Hand zwei gleicher PersonBeans.
	 */
	@Test
	public void testEquals() {
		try {
			PersonBean pb1 = new PersonBean(2, 4, "wurst", "hans", Titel.PROF,
					'm', "hans@wurst.de", "022445409", "016097753596",
					"0223383434");
			PersonBean pb2 = new PersonBean(2, 4, "wurst", "hans", Titel.PROF,
					'm', "hans@wurst.de", "022445409", "016097753596",
					"0223383434");

			assertTrue(pb1.equals(pb2));

		} catch (PersonException pe) {
			fail("Personbean konnte nicht angelegt werden!");
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());
		}

	}

	/**
	 * Test method for
	 * {@link de.randi2.model.fachklassen.beans.PersonBean#toString()}.
	 */
	@Test
	public void testToString() {
		try {
			testPB.setEmail("holadiewaldfee@wald.com");
			testPB.setFax("02847483928");
			testPB.setHandynummer("01758392046");
			testPB.setTelefonnummer("07245938473");
			testPB.setStellvertreterId(45);
			testPB.setId(57);
			testPB.setNachname("Waldfee");
			testPB.setVorname("Holadie");
		} catch (PersonException e) {
			e.printStackTrace();
		} catch (DatenbankExceptions ex) {
			fail(ex.getMessage());
		}
		String sollWert = "id:\t" + testPB.getId() + "\tvorname:\t"
				+ testPB.getVorname() + "\tnachname:\t" + testPB.getNachname()
				+ "\temail:\t" + testPB.getEmail() + "\tfax:\t"
				+ testPB.getFax() + "\thandy:\t" + testPB.getHandynummer()
				+ "\tstellvertreterId:\t" + testPB.getStellvertreterId()
				+ "\ttel:\t" + testPB.getTelefonnummer();
		String istWert = testPB.toString();
		assertTrue(sollWert.equals(istWert));
	}

	/**
	 * Testet die equals Methode an Hand zwei unterschiedlicher PersonBeans.
	 */
	@Test
	public void testNotEquals() {
		try {
			PersonBean pb1 = new PersonBean(2, 4, "wurst", "hans", Titel.PROF,
					'm', "hans@wurst.de", "022445409", "016097753596",
					"0223383434");
			PersonBean pb2 = new PersonBean(2, 4, "wursat", "hans", Titel.PROF,
					'm', "hans@wurst.de", "022445409", "016097753596",
					"0223234214");

			assertTrue(!pb1.equals(pb2));

		} catch (PersonException pe) {
			fail("Personbean konnte nicht angelegt werden!");
		} catch (DatenbankExceptions e) {
			fail(e.getMessage());
		}

	}

}