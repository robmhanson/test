package au.gov.nla.mvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import au.gov.nla.mvc.model.Person;

@Repository("personDao")
public class PersonDaoImpl extends AbstractDao<Integer, Person> implements PersonDao {

	public Person findById(int id) {
		return getByKey(id);
	}

	public void savePerson(Person person) {
		persist(person);
	}

	public void deletePersonBySsn(String ssn) {
		Query query = getSession().createSQLQuery("delete from Person where ssn = :ssn");
		query.setString("ssn", ssn);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Person> findAllPersons() {
		Criteria criteria = createEntityCriteria();
		return (List<Person>) criteria.list();
	}

	public Person findPersonBySsn(String ssn) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssn", ssn));
		return (Person) criteria.uniqueResult();
	}
}
