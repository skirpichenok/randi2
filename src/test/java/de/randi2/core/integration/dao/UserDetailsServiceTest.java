package de.randi2.core.integration.dao;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.randi2.dao.LoginDao;
import de.randi2.dao.UserDetailsServiceImpl;
import de.randi2.model.Login;
import de.randi2.testUtility.utility.DomainObjectFactory;
import de.randi2.testUtility.utility.TestStringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/spring-test.xml" })
@Transactional
public class UserDetailsServiceTest extends AbstractDaoTest{

	
	@Autowired
	private LoginDao loginDao;

	protected EntityManager entityManager;

	@PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	        this. entityManager = entityManager;
	}
	
	@Autowired
	private DomainObjectFactory factory;
	@Autowired
	private TestStringUtil testStringUtil;
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Before
	public void setUp(){
		super.setUp();
		userDetailsServiceImpl = new  UserDetailsServiceImpl();
		userDetailsServiceImpl.setEntityManager(entityManager);
		
	}
	
	@Test
	public void getUsernameTest() {
		
		Login l = initUsernameTest();
		
		entityManager.flush();
		entityManager.clear();

		Login l2 = (Login) userDetailsServiceImpl.loadUserByUsername(l.getUsername());
		assertEquals(l.getId(), l2.getId());
		assertEquals(l.getUsername(), l2.getUsername());
		try{
			l2 = (Login) userDetailsServiceImpl.loadUserByUsername(testStringUtil.getWithLength(20));
			fail();
		}catch (UsernameNotFoundException e){
		}
	
		
	}
	
	private Login initUsernameTest(){
		Login l = factory.getLogin();
		l.setUsername(testStringUtil.getWithLength(Login.MIN_USERNAME_LENGTH) + "@xyz.com");
		loginDao.create(l);
		return l;
	}
}
