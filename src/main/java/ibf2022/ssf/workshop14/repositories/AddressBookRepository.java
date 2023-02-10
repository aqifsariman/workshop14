package ibf2022.ssf.workshop14.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.ssf.workshop14.model.Contact;

@Repository
public class AddressBookRepository {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void save(final Contact contact) {
        redisTemplate.opsForList().leftPush("contactList", contact.getId());
        redisTemplate.opsForHash().put("addressBookMap", contact.getId(), contact);
    }

    public Contact findById(String contactId) {
        return (Contact) redisTemplate.opsForHash().get("addressBookMap", contactId);
    }

    public List<Contact> findAll(int startIndex) {
        List<Object> contactList = redisTemplate.opsForList().range("contactList", startIndex, 10);
        List<Contact> contacts = redisTemplate.opsForHash()
                .multiGet("addressBookMap", contactList)
                .stream().filter(Contact.class::isInstance)
                .map(Contact.class::cast)
                .toList();
        return contacts;
    }

}
