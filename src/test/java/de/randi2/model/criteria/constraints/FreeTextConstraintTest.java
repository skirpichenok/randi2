package de.randi2.model.criteria.constraints;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.randi2.test.utility.AbstractDomainTest;
import de.randi2.unsorted.ContraintViolatedException;
import edu.emory.mathcs.backport.java.util.Arrays;
import static junit.framework.Assert.*;


public class FreeTextConstraintTest extends	AbstractDomainTest<FreeTextConstraint> {

	private FreeTextConstraint constraint;
	private String element;
	
	public FreeTextConstraintTest(){
		super(FreeTextConstraint.class);
	}
	
	@Before
	public void setUp() throws Exception {
		element="value";
		constraint = new FreeTextConstraint(Arrays.asList(new String[]{element}));
	}
	
	@Test
	public void testConstructor(){
		List<String> elements = new ArrayList<String>();
		try {
			constraint = new FreeTextConstraint(elements);
			fail("the list of constraints should be not empty");
		} catch (ContraintViolatedException e) {}
		
		elements.add("Value1");
		try {
			constraint = new FreeTextConstraint(elements);
			assertTrue(constraint.getExpectedValue().equals("Value1"));
		} catch (ContraintViolatedException e) {
			fail("the list of constraints is ok");
		}
		elements.add("Value2");
		try {
			constraint = new FreeTextConstraint(elements);
			fail("the list of constraints has more than two objects");
		} catch (ContraintViolatedException e) {
		}
		try {
			constraint = new FreeTextConstraint(null);
			fail("the list of constraints is null");
		} catch (ContraintViolatedException e) {
		}
		
	}
	
	
	
	@Test
	public void testIsValueCorrect(){
		try {
			constraint.isValueCorrect(element);
		} catch (ContraintViolatedException e) {
			fail("Value is correct");
		}
		try {
			constraint.isValueCorrect("ValueXYZ");
			fail("Value is not correct");
		} catch (ContraintViolatedException e) {		}
	}
	
	@Test
	public void testExpectedValues(){
		assertTrue(constraint.getExpectedValue().equals(element));
		String test = "value123";
		constraint.setExpectedValue(test);
		assertTrue(constraint.getExpectedValue().equals(test));
	}
	
	
	@Test
	public void testCheckValue(){
		assertTrue(constraint.checkValue(element));
		assertFalse(constraint.checkValue("ValueXYZ"));
		
	}
	
	@Test
	public void databaseIntegrationTest(){
		hibernateTemplate.persist(constraint);
		assertTrue(constraint.getId()>0);
		
		FreeTextConstraint dbConstraint = (FreeTextConstraint) hibernateTemplate.get(FreeTextConstraint.class, constraint.getId());
		assertEquals(constraint.getId(), dbConstraint.getId());
		assertEquals(constraint.getExpectedValue(), dbConstraint.getExpectedValue());
	}

}