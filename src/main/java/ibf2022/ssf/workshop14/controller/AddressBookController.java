package ibf2022.ssf.workshop14.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ibf2022.ssf.workshop14.model.Contact;
import ibf2022.ssf.workshop14.services.AddressBookService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact-form";
    }

    @PostMapping("/contact")
    public String saveContact(@Valid Contact contact, BindingResult bResult, Model model,
            HttpServletResponse response) {

        if (bResult.hasErrors()) {
            return "contact-form";
        }
        addressBookService.saveContact(contact);
        model.addAttribute("contact", contact);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return "contact";
    }

    /*
     * REQUEST PARAM IS FOR WHEN THERE'S
     * THINGS TO BE EDITED FOR EXAMPLE
     * SETTING COUNTRY AND CITY TO GET
     * WEATHER UPDATES THERE
     */

    @GetMapping("/contact")
    public String getAllContacts(Model model, @RequestParam(name = "startIndex") Integer startIndex) {
        List<Contact> result = addressBookService.findAllContact(startIndex);
        model.addAttribute("contacts", result);
        return "list";
    }

    /*
     * PATH VARIABLE IS FOR DIRECT ACCESS
     * FOR EXAMPLE A LIST OF PRODUCTS AND
     * YOU WANT TO CLICK ON THAT PRODUCT
     * TO GET TO THE PAGE DIRECTLY
     */
    @GetMapping(path = "/contact/{contactId}")
    public String getContactDetails(Model model, @PathVariable(value = "contactId") String contactId) {
        Contact contact = addressBookService.findContactById(contactId);
        model.addAttribute("contact", contact);
        return "contact";
    }
}
