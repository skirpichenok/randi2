package de.randi2.datenbank;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.randi2.model.fachklassen.beans.BenutzerkontoBean;
import de.randi2.model.fachklassen.*;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.PasswortUtil;

import java.util.*;
/**
 * @author Benjamin Theel <BTheel@stud.hs-heilbronn.de>
 * @version $Id$
 */
public class DatenbankDummyTest {

    private DatenbankSchnittstelle aDB;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        aDB = new DatenbankDummy();
    }

    @After
    public void tearDown() throws Exception {
        aDB = null;
    }

    /**
     * Test method for
     * {@link de.randi2.datenbank.DatenbankDummy#schreibenObjekt(java.lang.Object)}.
     */
    @Test
    public void testSchreibenObjekt() {

        // IllegalArgument test
        try {
            aDB.schreibenObjekt(null);
            fail("Sollte Exception werfen");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        BenutzerkontoBean bean;
        Object result;
        BenutzerkontoBean ergebnisBean;
        // Benutzerbean schreiben
        try {
            bean = new BenutzerkontoBean();
            assertEquals(NullKonstanten.DUMMY_ID, bean.getId());
            result = aDB.schreibenObjekt(bean);
            assertTrue(result instanceof BenutzerkontoBean);
            ergebnisBean = (BenutzerkontoBean) result;
            assertEquals(bean, ergebnisBean);

            assertNotSame(NullKonstanten.DUMMY_ID, ergebnisBean.getId());
        } catch (Exception e) {
            fail("Sollte keine Exception werfen");
        }

    }

    /**
     * Test method for
     * {@link de.randi2.datenbank.DatenbankDummy#suchenObjekt(java.lang.Object)}.
     */
    @Test
    public void testSuchenObjekt() {

        try {
        	
        	Vector<BenutzerkontoBean>  ergebnisseBeans = null;
            String suchname = "stat";
            String passbrot = "stat";
            BenutzerkontoBean suchbean = new BenutzerkontoBean();
            suchbean.setBenutzername(suchname);

            ergebnisseBeans = aDB.suchenObjekt(suchbean);
            assertEquals(1, ergebnisseBeans.size());

            Object tmp = ergebnisseBeans.firstElement();
            
            assertTrue(tmp instanceof BenutzerkontoBean);

            BenutzerkontoBean ergebnisBean = (BenutzerkontoBean) tmp;
            assertEquals(suchname, ergebnisBean.getBenutzername());
            assertEquals(Rolle.getStatistiker(), ergebnisBean.getRolle());
            assertEquals(PasswortUtil.getInstance().hashPasswort(passbrot), ergebnisBean.getPasswort());
        } catch (Exception e) {
            fail("Ao!");
        }

    }
    @Test
    public void testSchreibenSuchen(){
        BenutzerkontoBean schreibBean,leseBean;
        BenutzerkontoBean ergebnisBean;
        String testname = "Testname";
        try {
            // schreiben
            schreibBean = new BenutzerkontoBean();
            schreibBean.setBenutzername(testname);
            ergebnisBean = (BenutzerkontoBean) aDB.schreibenObjekt(schreibBean);
            // lesen
            BenutzerkontoBean suchbean = new BenutzerkontoBean();
            suchbean.setBenutzername(testname);
            assertEquals(schreibBean.getBenutzername(), suchbean.getBenutzername());
            
            leseBean = (BenutzerkontoBean) (aDB.suchenObjekt(suchbean)).firstElement();
            assertEquals(schreibBean.getBenutzername(),leseBean.getBenutzername());
        }catch(Exception e){
            fail("Sollte keine Exception werfen");
        }
        
    }
    

}
