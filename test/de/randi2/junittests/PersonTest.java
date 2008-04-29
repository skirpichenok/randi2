package de.randi2.junittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.randi2.datenbank.DatenbankFactory;
import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.PersonException;
import de.randi2.model.fachklassen.Person;
import de.randi2.model.fachklassen.beans.PersonBean;
import de.randi2.utility.Log4jInit;

/**
 * Die Test-Klasse fuer die Klasse Person.
 * 
 * @author Thomas Willert [twillert@stud.hs-heilbronn.de]
 * @version $Id: PersonTest.java 2426 2007-05-06 17:34:32Z twillert $
 */
public class PersonTest {

	private PersonBean testPB;

	/**
	 * Initialisiert den Logger. Bitte log4j.lcf.pat in log4j.lcf umbenennen und
	 * es funktioniert.
	 * 
	 */
	@BeforeClass
	public static void log() {
		Log4jInit.initDebug();
	}

	@Before
	public void setUp() {
		testPB = new PersonBean();
		testPB.setFilter(true);
		try {
			testPB.setNachname("Hitzelmeier");
			testPB.setVorname("Antonius");
			testPB.setTitel(PersonBean.Titel.DR);
			testPB.setGeschlecht('m');
			testPB.setEmail("antonhitzel@gmx.net");
			testPB.setTelefonnummer("04738-4839278");
			testPB.setHandynummer("017394837263");
			testPB.setFax("05748/38291");
		} catch (PersonException e) {
			fail("Beim Erzeugen eines PersonBeans trat ein Fehler auf: "
					+ e.getMessage());
		}
	}

	@After
	public void tearDown() {
		testPB = null;
	}

	/**
	 * Test Methode fuer suchen(PersonBean gesuchtesBean). Sie sucht nach einem
	 * PersonBean(mit Filter=true).
	 * 
	 * {@link de.randi2.model.fachklassen.Person#suchenPerson(de.randi2.model.fachklassen.beans.PersonBean)}.
	 */
	@Test
	public void testSuchenPerson() {
		try {
			// Speichern in der Datenbank
			DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(testPB);
			// Initialisierung eines Vektors
			Vector<PersonBean> tempVec = new Vector<PersonBean>();
			// Holen aus der Datenbank
			tempVec = Person.suchenPerson(testPB);
			/*
			 * Da wir wissen, dass sich zur Zeit in der Datenbank eine Person
			 * mit den gesuchten Eigenschaften befindet.
			 */
			for (int i = 0; i < tempVec.size(); i++) {
				assertEquals(tempVec.elementAt(i).getNachname(), "Hitzelmeier");
				assertEquals(tempVec.elementAt(i).getVorname(), "Antonius");
				assertEquals(tempVec.elementAt(i).getTitel(),
						PersonBean.Titel.DR);
				assertEquals(tempVec.elementAt(i).getGeschlecht(), 'm');
				assertEquals(tempVec.elementAt(i).getEmail(),
						"antonhitzel@gmx.net");
				assertEquals(tempVec.elementAt(i).getTelefonnummer(),
						"04738-4839278");
				assertEquals(tempVec.elementAt(i).getHandynummer(),
						"017394837263");
				assertEquals(tempVec.elementAt(i).getFax(), "05748/38291");
			}
			/*
			 * Suchen nach einer nicht existierenden Person - Ergebnis: keine
			 * Person wird gefunden.
			 */
			PersonBean atestPB = new PersonBean();
			atestPB.setFilter(true);
			try {
				atestPB.setNachname("Moooshammer");
				atestPB.setVorname("Alfred");
				atestPB.setGeschlecht('m');
				atestPB.setEmail("klingkling@web.de");
				atestPB.setTelefonnummer("07238330271");
			} catch (PersonException e) {
				fail("Bei der PersonBean Klasse trat ein Fehler auf: "
						+ e.getMessage());
			}
			tempVec = new Vector<PersonBean>();
			// wird nicht gefunden, da nicht geschrieben wurde
			tempVec = Person.suchenPerson(atestPB);
			assertTrue(tempVec.size() == 0);
		} catch (DatenbankExceptions e) {
			fail("In der Datenbank trat ein Fehler auf: " + e.getMessage());
		}
	}

	/**
	 * Test Methode fuer get(long id).
	 * 
	 * {@link de.randi2.model.fachklassen.Person#suchen(de.randi2.model.fachklassen.beans.PersonBean)}.
	 */
	@Test
	public void testGet() {
		try {
			testPB.setNachname("Meisner");
			testPB.setVorname("Albert");
			testPB.setGeschlecht('m');
			testPB.setEmail("blubblub@web.de");
			testPB.setTelefonnummer("07238323498");
		} catch (PersonException e) {
			fail("Bei der PersonBean Klasse trat ein Fehler auf: "
					+ e.getMessage());
		}
		try {
			// schreiben in die Datenbank, Rueckgabewert ist inklusive ID !!!
			testPB = DatenbankFactory.getAktuelleDBInstanz().schreibenObjekt(
					testPB);
			PersonBean vergleichPB = Person.get(testPB.getId());
			assertEquals(vergleichPB, testPB);
		} catch (DatenbankExceptions e) {
			fail("In der Datenbank trat ein Fehler auf: " + e.getMessage());
		}
	}
}