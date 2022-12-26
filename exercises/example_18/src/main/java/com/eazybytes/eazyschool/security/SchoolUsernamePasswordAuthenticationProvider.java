package com.eazybytes.eazyschool.security;

import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.Roles;
import com.eazybytes.eazyschool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Person person = personRepository.readByEmail(email);

//        if(person != null && person.getPersonId()>0 && password.equals(person.getPwd())){
        if(person != null && person.getPersonId()>0 && passwordEncoder.matches(password, person.getPwd())){


            // burada password'ü null da return edebiliriz çünkü
            // zaten spring security login olduktan sonra user üzerindeki bilgilerde
            // password'ü tutmaz güvenlik nedeniyle
//            return new UsernamePasswordAuthenticationToken(person.getName(), password, getGrantedAuthorities(person.getRoles()));

            // burada geriye kullanıcı ismini döndürüyoruz
//            return new UsernamePasswordAuthenticationToken(person.getName(), null, getGrantedAuthorities(person.getRoles()));

            // burada ise email'i döndürüyoruz çünkü aynı isme sahip kullanıcılar olabilir
            // yapacağımız işlemlerde sıkıntı yaşatabilir.
            return new UsernamePasswordAuthenticationToken(person.getEmail(), null, getGrantedAuthorities(person.getRoles()));
        }else {
            throw new BadCredentialsException("invalid credentials");
        }

    }

    private List<GrantedAuthority> getGrantedAuthorities(Roles roles){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+roles.getRoleName()));
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
