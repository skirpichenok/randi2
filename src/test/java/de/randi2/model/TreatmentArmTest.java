package de.randi2.model;


import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.exceptions.ValidationException;
import de.randi2.test.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;

public class TreatmentArmTest extends AbstractDomainTest<TreatmentArm> {

	private TreatmentArm validTreatmentArm;
	
	public TreatmentArmTest(){
		super(TreatmentArm.class);
	}
	
	@Before
	public void setUp(){
		validTreatmentArm = new TreatmentArm();
		validTreatmentArm.setDescription("description");
		validTreatmentArm.setName("arm");
		validTreatmentArm.setPlannedSubjects(10);
		validTreatmentArm.setTrial(factory.getTrial());
	}
	
	@Test
	public void testSubjects(){
		assertTrue(validTreatmentArm.getSubjects().isEmpty());
		TrialSubject subject1 = new TrialSubject();
		subject1.setIdentification("id1");
		TrialSubject subject2 = new TrialSubject();
		subject2.setIdentification("id2");
		validTreatmentArm.addSubject(subject1);
		validTreatmentArm.addSubject(subject2);
		assertEquals(2, validTreatmentArm.getSubjects().size());
		assertTrue(validTreatmentArm.getSubjects().contains(subject1));
		assertTrue(validTreatmentArm.getSubjects().contains(subject2));
	}
	
	@Test
	public void testCheckValuePlannedSize(){
		try{
			validTreatmentArm.checkValue("plannedSubjects",0);
			fail();
		}catch (ValidationException e) {
			assertNotNull(e);
		}
		try{
			validTreatmentArm.checkValue("plannedSubjects",Long.MAX_VALUE);
			fail();
		}catch (ValidationException e) {
			assertNotNull(e);
		}
		try{
			validTreatmentArm.checkValue("plannedSubjects",30);
		}catch (ValidationException e) {
			fail();
		}
	}
	
	@Test
	public void testCheckValueTrial(){
		try{
			validTreatmentArm.checkValue("trial",validTreatmentArm.getTrial());
		}catch (ValidationException e) {
			fail();
		}
		try{
			validTreatmentArm.checkValue("trial",null);
			fail();
		}catch (ValidationException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void testCheckValueName(){
		try{
			validTreatmentArm.checkValue("name","ABCDEF");
		}catch (ValidationException e) {
			fail();
		}
		try{
			validTreatmentArm.checkValue("name",stringUtil.getWithLength(1000));
			fail();
		}catch (ValidationException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void databaseIntegrationTest(){
		hibernateTemplate.persist(validTreatmentArm.getTrial().getLeadingSite());
		hibernateTemplate.persist(validTreatmentArm.getTrial().getSponsorInvestigator());
		hibernateTemplate.persist(validTreatmentArm.getTrial());
		hibernateTemplate.persist(validTreatmentArm);
		assertTrue(validTreatmentArm.getId() >0);
		TreatmentArm dbArm = (TreatmentArm)hibernateTemplate.get(TreatmentArm.class, validTreatmentArm.getId());
		assertEquals(validTreatmentArm.getDescription(), dbArm.getDescription());
		assertEquals(validTreatmentArm.getName(), dbArm.getName());
		assertEquals(validTreatmentArm.getPlannedSubjects(),dbArm.getPlannedSubjects());
		assertEquals(validTreatmentArm.getTrial().getId(), dbArm.getTrial().getId());
		assertEquals(validTreatmentArm.getTrial().getName(), dbArm.getTrial().getName());
	}
	
	@Test
	public void testEqualsHashCode(){
		TreatmentArm arm1 = new TreatmentArm();
		TreatmentArm arm2 = new TreatmentArm();
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setId(12);
		arm2.setId(12);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setName("name");
		arm2.setName("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setDescription("name");
		arm2.setDescription("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setPlannedSubjects(10);
		arm2.setPlannedSubjects(10);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setId(12);
		arm2.setId(13);
		assertFalse(arm1.equals(arm2));
		arm2.setId(12);
		assertTrue(arm1.equals(arm2));
		
		arm1.setName("name");
		arm2.setName("name1");
		assertFalse(arm1.equals(arm2));
		arm2.setName("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setDescription("name");
		arm2.setDescription("name1");
		assertFalse(arm1.equals(arm2));
		arm2.setDescription("name");
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setPlannedSubjects(10);
		arm2.setPlannedSubjects(11);
		assertFalse(arm1.equals(arm2));
		arm2.setPlannedSubjects(10);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
		
		arm1.setVersion(256);
		assertTrue(arm1.equals(arm2));
		assertEquals(arm1.hashCode(), arm2.hashCode());
	}
}
