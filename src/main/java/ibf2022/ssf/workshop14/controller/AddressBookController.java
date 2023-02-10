package ibf2022.ssf.workshop14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
}
