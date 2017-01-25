package au.gov.nla.mvc.controller;

import au.gov.nla.mvc.model.Person;
import au.gov.nla.mvc.service.PersonService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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

public class AppControllerTest {

	@Mock
	PersonService service;
	
	@Mock
	MessageSource message;
	
	@InjectMocks
	AppController appController;
	
	@Spy
	List<Person> people = new ArrayList<Person>();

	@Spy
	ModelMap model;
	
	@Mock
	BindingResult result;
	
	@BeforeClass
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		people = getPersonList();
	}
	
	@Test
	public void listPersons(){
		when(service.findAllPersons()).thenReturn(people);
		Assert.assertEquals(appController.listPersons(model), "allpersons");
		Assert.assertEquals(model.get("persons"), people);
		verify(service, atLeastOnce()).findAllPersons();
	}
	
	@Test
	public void newPerson(){
		Assert.assertEquals(appController.newPerson(model), "registration");
		Assert.assertNotNull(model.get("person"));
		Assert.assertFalse((Boolean)model.get("shouldEdit"));
		Assert.assertEquals(((Person)model.get("person")).getId(), 0);
	}


	@Test
	public void savePersonWithValidationError(){
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).savePerson(any(Person.class));
		Assert.assertEquals(appController.savePerson(people.get(0), result, model), "registration");
	}

	@Test
	public void savePersonWithValidationErrorNonUniqueSSN(){
		when(result.hasErrors()).thenReturn(false);
		when(service.findById(anyInt())).thenReturn(null);
		Assert.assertEquals(appController.savePerson(people.get(0), result, model), "registration");
	}

	
	@Test
	public void savePersonWithSuccess(){
		when(result.hasErrors()).thenReturn(false);
		doNothing().when(service).savePerson(any(Person.class));
		Assert.assertEquals(appController.savePerson(people.get(0), result, model), "success");
		Assert.assertEquals(model.get("success"), "Person Axel registered successfully");
	}

	@Test
	public void editPerson(){
		Person emp = people.get(0);
		when(service.findById(anyInt())).thenReturn(emp);
		Assert.assertEquals(appController.editPerson(anyInt(), model), "registration");
		Assert.assertNotNull(model.get("person"));
		Assert.assertTrue((Boolean)model.get("shouldEdit"));
		Assert.assertEquals(((Person)model.get("person")).getId(), 1);
	}

	@Test
	public void updatePersonWithValidationError(){
		when(result.hasErrors()).thenReturn(true);
		doNothing().when(service).updatePerson(any(Person.class));
		Assert.assertEquals(appController.updatePerson(people.get(0), result, model,""), "registration");
	}

	@Test
	public void updatePersonWithValidationErrorNonUniqueSSN(){
		when(result.hasErrors()).thenReturn(false);
		when(service.findById(anyInt())).thenReturn(null);
		Assert.assertEquals(appController.updatePerson(people.get(0), result, model,""), "registration");
	}

	@Test
	public void updatePersonWithSuccess(){
		when(result.hasErrors()).thenReturn(false);
		doNothing().when(service).updatePerson(any(Person.class));
		Assert.assertEquals(appController.updatePerson(people.get(0), result, model, ""), "success");
		Assert.assertEquals(model.get("success"), "Person Axel updated successfully");
	}
	
	
	@Test
	public void deletePerson(){
		doNothing().when(service).deletePersonBySsn(anyString());
		Assert.assertEquals(appController.deletePerson("123"), "redirect:/list");
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
