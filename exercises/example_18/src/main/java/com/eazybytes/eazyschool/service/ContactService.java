package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.constants.SchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

//    private static Logger logger = LoggerFactory.getLogger((ContactService.class));

    @Autowired
    private ContactRepository contactRepository;
    public boolean saveMessageDetails(Contact contact){

//        boolean isSaved = true;
//        log.info(contact.toString());

        boolean isSaved = false;

        contact.setStatus(SchoolConstants.OPEN);
//        contact.setCreatedBy(SchoolConstants.ANONYMOUS);
//        contact.setCreatedAt(LocalDateTime.now());

//        int result = contactRepository.saveContactMessage(contact);
        Contact savedContact = contactRepository.save(contact);

//        if(result > 0){
//            isSaved = true;
//        }

        if(savedContact != null && savedContact.getContactId()>0){
            isSaved = true;
        }


        return isSaved;
    }

//    public List<Contact> findMessagesWithOpenStatus() {
//        List<Contact> contactList = contactRepository.findMessagesWithStatus(SchoolConstants.OPEN);
//        List<Contact> contactList = contactRepository.findByStatus(SchoolConstants.OPEN);
//        return contactList;

//    }

    public Page<Contact> findMessagesWithOpenStatus(int pageNum,String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatusWithQuery(
                SchoolConstants.OPEN,pageable);
//        Page<Contact> msgPage = contactRepository.findOpenMsgs(
//                SchoolConstants.OPEN,pageable);
//        Page<Contact> msgPage = contactRepository.findOpenMsgsNative(
//                SchoolConstants.OPEN,pageable);
        return msgPage;
    }

//    public boolean updateMessageStatusToClose(int contactId/*, String updatedBy*/) {
//
//        boolean isUpdated = false;
//
//        Optional<Contact> contact = contactRepository.findById(contactId);
//        contact.ifPresent(contact1 -> {
//            contact1.setStatus(SchoolConstants.CLOSE);
////            contact1.setUpdatedAt(LocalDateTime.now());
////            contact1.setUpdatedBy(updatedBy);
//        });
//
////        int result = contactRepository.updateMessageStatusToClose(contactId, SchoolConstants.CLOSE, updatedBy);
////        if(result > 0){
////            isUpdated = true;
////        }
//
//        Contact updatedContact = contactRepository.save(contact.get());
//        if(updatedContact != null && updatedContact.getUpdatedBy() != null){
//            isUpdated = true;
//        }
//
//        return isUpdated;
//    }

    public boolean updateMessageStatusToClose(int contactId){
        boolean isUpdated = false;
//        int returnedRows = contactRepository.updateStatusById(SchoolConstants.CLOSE, contactId);
//        int returnedRows = contactRepository.updateMsgStatus(SchoolConstants.CLOSE, contactId);

        int returnedRows = contactRepository.updateMsgStatusNative(SchoolConstants.CLOSE, contactId);


        if(returnedRows > 0)
            isUpdated = true;

        return isUpdated;
    }


}
