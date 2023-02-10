package ibf2022.ssf.workshop14.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.ssf.workshop14.model.Contact;
import ibf2022.ssf.workshop14.repositories.AddressBookRepository;

@Service
public class AddressBookService {
    @Autowired
    private AddressBookRepository addressBookRepo;

    public void saveContact(final Contact contact) {
        addressBookRepo.save(contact);
    }

    public Contact findContactById(String contactId) {
        return addressBookRepo.findById(contactId);
    }

    public List<Contact> findAllContact(int startIndex) {
        return addressBookRepo.findAll(startIndex);
    }
}
