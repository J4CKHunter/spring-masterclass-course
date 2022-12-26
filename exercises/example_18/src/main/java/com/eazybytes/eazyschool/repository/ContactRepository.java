package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Contact;
//import com.eazybytes.eazyschool.rowmappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

//@Repository
//public class ContactRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public ContactRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public int saveContactMessage(Contact contact){
//        String query = "INSERT INTO CONTACT_MSG (NAME,MOBILE_NUM,EMAIL,SUBJECT,MESSAGE,STATUS," +
//                "CREATED_AT,CREATED_BY) VALUES (?,?,?,?,?,?,?,?)";
//
//        return jdbcTemplate.update(query, contact.getName(), contact.getMobileNum(),
//                contact.getEmail(), contact.getSubject(), contact.getMessage(),
//                contact.getStatus(), contact.getCreatedAt(), contact.getCreatedBy());
//    }
//
//    public List<Contact> findMessagesWithStatus(String status) {
//
//        // bu sql query'si çalıştırıldığında geriye contact messages listesi
//        // dönecek, bu gelen message'ları bir pojo class'ın üstüne map etmek istiyoruz
//        // ve bu yüzden RowMapper implement ediyoruz ContactRowMapper class'ında
//        // ve burada contact pojo objesinin üzerinde set ediyoruz.
//        // yani bu sayede query'den gelen resultSet'i java objesi üzerine çekiyoruz
//        // query() methodu da geriye liste olarak döndürüyor bunları
//
//        String query = "SELECT * FROM CONTACT_MSG WHERE STATUS = ?";
//        return jdbcTemplate.query(query, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, status);
//            }
//        }, new ContactRowMapper());
//    }
//
//
//    public int updateMessageStatusToClose(int contactId, String statusClose, String updatedBy) {
//        String query = "UPDATE CONTACT_MSG SET STATUS = ?, UPDATED_BY = ?,UPDATED_AT =? WHERE CONTACT_ID = ?";
//        return jdbcTemplate.update(query, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, statusClose);
//                ps.setString(2, updatedBy);
//                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//                ps.setInt(4, contactId);
//            }
//        });
//    }
//}

@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer> {
    List<Contact> findByStatus(String status);

//    Page<Contact> findByStatus(String status, Pageable pageable);


//@Query("SELECT c FROM Contact c WHERE c.status = ?1")
//    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    @Query(value = "SELECT * FROM contact_msg WHERE contact_msg.status = :status",nativeQuery = true)
//    Page<Contact> findByStatus(String status, Pageable pageable);
    Page<Contact> findByStatusWithQuery(@Param("status")String state, Pageable pageable);


    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);


    Page<Contact> findOpenMsgs(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);

    @Query(nativeQuery = true)
    Page<Contact> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int id);



}
