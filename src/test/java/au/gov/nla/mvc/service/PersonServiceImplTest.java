package au.gov.nla.mvc.service;

import au.gov.nla.mvc.dao.PersonDao;
import au.gov.nla.mvc.model.Person;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonServiceImplTest {

	@Mock
	PersonDao dao;
	
	@InjectMocks
	PersonServiceImpl personService;
	
	@Spy
	List<Person> people = new ArrayList<Person>();
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		people = getPersonList();
	}

	@Test
	public void findById(){
		Person emp = people.get(0);
		when(dao.findById(anyInt())).thenReturn(emp);
		Assert.assertEquals(personService.findById(emp.getId()),emp);
	}

	@Test
	public void savePerson(){
		doNothing().when(dao).savePerson(any(Person.class));
		personService.savePerson(any(Person.class));
		verify(dao, atLeastOnce()).savePerson(any(Person.class));
	}
	
	@Test
	public void updatePerson(){
		Person emp = people.get(0);
		when(dao.findById(anyInt())).thenReturn(emp);
		personService.updatePerson(emp);
		verify(dao, atLeastOnce()).findById(anyInt());
	}

	@Test
	public void deletePersonBySsn(){
		doNothing().when(dao).deletePersonBySsn(anyString());
		personService.deletePersonBySsn(anyString());
		verify(dao, atLeastOnce()).deletePersonBySsn(anyString());
	}
	
	@Test
	public void findAllPersons(){
		when(dao.findAllPersons()).thenReturn(people);
		Assert.assertEquals(personService.findAllPersons(), people);
	}
	
	public List<Person> getPersonList(){
		Person e1 = new Person();
		e1.setId(1);
		e1.setName("Axel");
		e1.setEmail("jeremy@emailcom");
		e1.setPhone("0262000000");
		
		Person e2 = new Person();
		e2.setId(2);
		e2.setName("Jeremy");
		e2.setEmail("jeremy@emailcom");
		e2.setPhone("0262000000");

		people.add(e1);
		people.add(e2);
		return people;
	}
	
}
