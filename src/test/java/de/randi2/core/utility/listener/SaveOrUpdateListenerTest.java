package de.randi2.core.utility.listener;

import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.hibernate.context.ManagedSessionContext;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.testUtility.utility.DomainObjectFactory;
import static junit.framework.Assert.*;

//import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/service-test.xml",
		"classpath:/META-INF/subconfig/test.xml" })
public class SaveOrUpdateListenerTest {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private DomainObjectFactory factory;


	@Test
	@Ignore
	public void onPersist() {
		AbstractDomainObject object = factory.getPerson();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
	}

	@Test
	@Ignore
	public void onPersistWithCascade() {
		Login object = factory.getLogin();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());
	}

	@Test
	@Ignore
	public void onMerge() {
		AbstractDomainObject object = factory.getPerson();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());

		GregorianCalendar create = object.getCreatedAt();
		GregorianCalendar update = (GregorianCalendar)object.getUpdatedAt().clone();
		
		try{
			Thread.sleep(1000);
			entityManager.merge(object);
			assertEquals(create, object.getCreatedAt());
			assertFalse(update.compareTo(object.getUpdatedAt()) == 0);
		}catch (Exception e) {
			fail(e.getMessage());
		}
	
	}

	@Test
	@Ignore
	public void onMergewithCascade() {
		Login object = factory.getLogin();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());

		GregorianCalendar createL = object.getCreatedAt();
		GregorianCalendar updateL = object.getUpdatedAt();
		GregorianCalendar createP = object.getPerson().getCreatedAt();
		GregorianCalendar updateP = object.getPerson().getUpdatedAt();

		try{
		Thread.sleep(1000);
		entityManager.merge(object);

		assertEquals(createL, object.getCreatedAt());
		assertFalse(updateL.equals(object.getUpdatedAt()));
		assertEquals(createP, object.getPerson().getCreatedAt());
		assertFalse(updateP.equals(object.getPerson().getUpdatedAt()));
		}catch (Exception e) {
			fail(e.getMessage());
		}
		
		object.setPerson(factory.getPerson());

		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		entityManager.merge(object);
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());
	}
	
	
	@Test
	@Ignore
	public void onSaveOrUpdate() {
		Login object = factory.getLogin();
		assertNull(object.getCreatedAt());
		assertNull(object.getUpdatedAt());
		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		entityManager.persist(object);
		assertNotNull(object.getCreatedAt());
		assertNotNull(object.getUpdatedAt());
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());

		GregorianCalendar createL = object.getCreatedAt();
		GregorianCalendar updateL = object.getUpdatedAt();
		GregorianCalendar createP = object.getPerson().getCreatedAt();
		try{
		Thread.sleep(1000);
		object = entityManager.merge(object);

		assertEquals(createL, object.getCreatedAt());
		assertFalse(updateL.equals(object.getUpdatedAt()));
		assertEquals(createP, object.getPerson().getCreatedAt());
		}catch (Exception e) {
			fail(e.getMessage());
		}
		
		object.setPerson(factory.getPerson());

		assertNull(object.getPerson().getCreatedAt());
		assertNull(object.getPerson().getUpdatedAt());
		object = entityManager.merge(object);
		entityManager.flush();
		assertNotNull(object.getPerson().getCreatedAt());
		assertNotNull(object.getPerson().getUpdatedAt());
	}

}
