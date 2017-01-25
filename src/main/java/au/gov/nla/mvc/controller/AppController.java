package au.gov.nla.mvc.controller;

import au.gov.nla.mvc.model.Person;
import au.gov.nla.mvc.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	PersonService service;
	
	@Autowired
	MessageSource messageSource;

	/*
	 * This method will list all existing persons.
	 */
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String listPersons(ModelMap model) {

		List<Person> people = service.findAllPersons();
		model.addAttribute("persons", people);
		return "allpersons";
	}

	/*
	 * This method will provide the medium to add a new person.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newPerson(ModelMap model) {
		Person person = new Person();
		model.addAttribute("person", person);
		model.addAttribute("shouldEdit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving person in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String savePerson(@Valid Person person, BindingResult result,
							   ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation 
		 * and applying it on field [ssn] of Model class [Person].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
//		if(!service.isPersonSsnUnique(person.getId(), person.getSsn())){
//			FieldError ssnError =new FieldError("person","ssn",messageSource.getMessage("non.unique.ssn", new String[]{person.getSsn()}, Locale.getDefault()));
//		    result.addError(ssnError);
//			return "registration";
//		}
		
		service.savePerson(person);

		model.addAttribute("success", "Person " + person.getName() + " registered successfully");
		return "success";
	}


	/*
	 * This method will provide the medium to update an existing person.
	 */
	@RequestMapping(value = { "/edit-{id}-person" }, method = RequestMethod.GET)
	public String editPerson(@PathVariable int id, ModelMap model) {
		Person person = service.findById(id);
		model.addAttribute("person", person);
		model.addAttribute("shouldEdit", true);
		return "registration";
	}
	
	/*
	 * This method will be called on form submission, handling POST request for
	 * updating person in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{ssn}-person" }, method = RequestMethod.POST)
	public String updatePerson(@Valid Person person, BindingResult result,
								 ModelMap model, @PathVariable String ssn) {

		if (result.hasErrors()) {
			return "registration";
		}

		service.updatePerson(person);

		model.addAttribute("success", "Person " + person.getName()	+ " updated successfully");
		return "success";
	}

	
	/*
	 * This method will delete an person by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{ssn}-person" }, method = RequestMethod.GET)
	public String deletePerson(@PathVariable String ssn) {
		service.deletePersonBySsn(ssn);
		return "redirect:/list";
	}

}
