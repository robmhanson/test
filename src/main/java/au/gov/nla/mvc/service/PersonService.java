package au.gov.nla.mvc.service;

import au.gov.nla.mvc.model.Person;

import java.util.List;

public interface PersonService {

	Person findById(int id);
	
	void savePerson(Person person);
	
	void updatePerson(Person person);
	
	void deletePersonBySsn(String ssn);

	List<Person> findAllPersons();
}
