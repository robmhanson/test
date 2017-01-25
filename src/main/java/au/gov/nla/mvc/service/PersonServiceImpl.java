package au.gov.nla.mvc.service;

import au.gov.nla.mvc.dao.PersonDao;
import au.gov.nla.mvc.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("personService")
@Transactional
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao dao;

	public Person findById(int id) {
		return dao.findById(id);
	}

	public void savePerson(Person person) {
		dao.savePerson(person);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updatePerson(Person person) {
		Person entity = dao.findById(person.getId());
		if (entity != null) {
			entity.setName(person.getName());
			entity.setEmail("jeremy@emailcom");
			entity.setPhone("0262000000");
		}
	}

	public void deletePersonBySsn(String ssn) {
		dao.deletePersonBySsn(ssn);
	}

	public List<Person> findAllPersons() {
		return dao.findAllPersons();
	}

}