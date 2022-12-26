package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.constants.SchoolConstants;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.Roles;
import com.eazybytes.eazyschool.repository.PersonRepository;
import com.eazybytes.eazyschool.repository.RolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    // burada BCryptPasswordEncoder inject etmedik çünkü ileride belki implementasyonu
    // değiştirebiliriz.
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person){

        boolean isSaved = false;

        // default olarak herkesi student yaratıyoruz
        Roles role = rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE);

        // person entity'si üzerinde role bilgisi not nullable olduğu için
        // role veriyoruz db'ye kontrol etmeden önce
        // adress ise nullable olduğu için onun hakkında bir şey yapmadık
        person.setRoles(role);

        // şifre hash'e çevrilerek kaydedildi aynı kişiye
        person.setPwd(passwordEncoder.encode(person.getPwd()));

        Person savedPerson = personRepository.save(person);

        if(savedPerson != null && person.getPersonId() > 0){
            isSaved = true;
        }

        return isSaved;
    }
}
