package au.gov.nla.mvc.dao;

import au.gov.nla.mvc.model.Person;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PersonDaoImplTest extends EntityDaoImplTest{

	@Autowired
	PersonDao personDao;

	@Override
	protected IDataSet getDataSet() throws Exception{
		IDataSet dataSet = new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Person.xml"));
		return dataSet;
	}
	
	/* In case you need multiple datasets (mapping different tables) and you do prefer to keep them in separate XML's
	@Override
	protected IDataSet getDataSet() throws Exception {
	  IDataSet[] datasets = new IDataSet[] {
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Person.xml")),
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Benefits.xml")),
			  new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Departements.xml"))
	  };
	  return new CompositeDataSet(datasets);
	}
	*/

	@Test
	public void findById(){
		Assert.assertNotNull(personDao.findById(1));
		Assert.assertNull(personDao.findById(3));
	}

	
	@Test
	public void savePerson(){
		personDao.savePerson(getSamplePerson());
		Assert.assertEquals(personDao.findAllPersons().size(), 3);
	}
	
	@Test
	public void deletePersonBySsn(){
		personDao.deletePersonBySsn("11111");
		Assert.assertEquals(personDao.findAllPersons().size(), 1);
	}
	
	@Test
	public void deletePersonByInvalidSsn(){
		personDao.deletePersonBySsn("23423");
		Assert.assertEquals(personDao.findAllPersons().size(), 2);
	}

	@Test
	public void findAllPersons(){
		Assert.assertEquals(personDao.findAllPersons().size(), 2);
	}
	
	@Test
	public void findPersonBySsn(){
		Assert.assertNotNull(personDao.findPersonBySsn("11111"));
		Assert.assertNull(personDao.findPersonBySsn("14545"));
	}

	public Person getSamplePerson(){
		Person person = new Person();
		person.setName("Karen");
		person.setEmail("jeremy@emailcom");
		person.setPhone("0262000000");
		return person;
	}

}
