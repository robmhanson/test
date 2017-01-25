package au.gov.nla.mvc.dao;

import au.gov.nla.mvc.model.Person;

import java.util.List;

public interface PersonDao {

	Person findById(int id);

	void savePerson(Person person);
	
	void deletePersonBySsn(String ssn);
	
	List<Person> findAllPersons();

	Person findPersonBySsn(String ssn);

}
