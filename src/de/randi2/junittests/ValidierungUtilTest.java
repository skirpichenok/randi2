package de.randi2.junittests;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.utility.ValidierungsUtil;

public class ValidierungUtilTest {

	@Before
	public void setUp() throws Exception {
			PropertyConfigurator.configureAndWatch("WebContent/WEB-INF/log4j.lcf");
			Logger.getLogger(this.getClass()).debug(
					"Log4J System konfigueriert.");
	}
	
	@Test
	public void TestValidiereRufnummer() {
		assertEquals("+4971316423147", ValidierungsUtil.validiereRufnummer("\t07131 6 42 31 47"));
		assertEquals("+49713144430", ValidierungsUtil.validiereRufnummer(" 07131 4 44 30"));
		assertEquals("+4971168582271", ValidierungsUtil.validiereRufnummer("(07 11) 685 - 8 22 71"));
		assertEquals("+3226749810", ValidierungsUtil.validiereRufnummer(" +32.2.674.9810 "));
		assertEquals("+49214521342", ValidierungsUtil.validiereRufnummer(" 02145/21342 "));
		assertEquals(null, ValidierungsUtil.validiereRufnummer("(07 11) 685 - 8 22 d71")); // falsche Zeichen
		assertEquals(null, ValidierungsUtil.validiereRufnummer("07131")); // Zu kurz
		assertEquals(null, ValidierungsUtil.validiereRufnummer("32.2.674")); // Falsche Anfang
		assertEquals(null, ValidierungsUtil.validiereRufnummer("")); // leer
		
	}

	@Test
	public void TestValidiereEMail() {

		assertTrue(ValidierungsUtil.validiereEMailPattern("johannes.thoenes@urz.uni-heidelberg.de"));
		assertTrue(ValidierungsUtil.validiereEMailPattern("h@alo.com"));
		assertTrue(ValidierungsUtil.validiereEMailPattern("info@2wikipedia.org"));
		assertTrue(ValidierungsUtil.validiereEMailPattern("mue5ller@gmx.net"));
		assertTrue(ValidierungsUtil.validiereEMailPattern("hans-martin-schleyer@raf.ru"));
		assertTrue(ValidierungsUtil.validiereEMailPattern("ulf_kirsten@bayer-leverkusen.de"));
		assertTrue(ValidierungsUtil.validiereEMailPattern("wa3lter_ulbricht@sed.com"));
		assertTrue(!ValidierungsUtil.validiereEMailPattern("mark@asdf_,de"));
		assertTrue(!ValidierungsUtil.validiereEMailPattern("adsf,@asd.-de"));
		assertTrue(!ValidierungsUtil.validiereEMailPattern("asdf,e@_.de"));
		assertTrue(!ValidierungsUtil.validiereEMailPattern("12nien"));
		
	}

	@Test
	public void TestValidierePasswortZeichen() {
		assertTrue(!ValidierungsUtil.validierePasswortZeichen("johannes"));
		assertTrue(!ValidierungsUtil.validierePasswortZeichen("johannes1"));
		assertTrue(!ValidierungsUtil.validierePasswortZeichen("johann,es"));
		assertTrue(!ValidierungsUtil.validierePasswortZeichen("johann.es"));
		assertTrue(ValidierungsUtil.validierePasswortZeichen("johan2-nes"));
		assertTrue(!ValidierungsUtil.validierePasswortZeichen("485474d"));
		assertTrue(!ValidierungsUtil.validierePasswortZeichen(",..$§%/()"));
		assertTrue(ValidierungsUtil.validierePasswortZeichen("45=nnes"));
		assertTrue(ValidierungsUtil.validierePasswortZeichen("joh--7es"));
		assertTrue(ValidierungsUtil.validierePasswortZeichen("l;1"));
		
	}
}
