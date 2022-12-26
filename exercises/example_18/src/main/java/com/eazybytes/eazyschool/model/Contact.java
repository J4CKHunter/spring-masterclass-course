package com.eazybytes.eazyschool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "contact_msg")
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
})
@NamedQueries({
        @NamedQuery(name = "Contact.findOpenMsgs",
            query = "SELECT c FROM Contact c where c.status = :status"),
        @NamedQuery(name = "Contact.updateMsgStatus",
            query = "UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Contact.findOpenMsgsNative",
                query = "SELECT * FROM contact_msg c WHERE c.status = :status"
                ,resultClass = Contact.class),
        @NamedNativeQuery(name = "Contact.findOpenMsgsNative.count",
                query = "select count(*) as cnt from contact_msg c where c.status = :status",
                resultSetMapping = "SqlResultSetMapping.count"),
        @NamedNativeQuery(name = "Contact.updateMsgStatusNative",
                query = "UPDATE contact_msg c SET c.status = ?1 WHERE c.contact_id = ?2")
})
public class Contact extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator( name = "native", strategy = "native")
    @Column(name = "contact_id")
    private int contactId;
    // primary-key column'u temsil ediyor



    @NotBlank(message = "Name couldn't be blank")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Mobile number couldn't be blank")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
//    @JsonProperty("Mobile Number")
    private String mobileNum;

    @NotBlank(message = "email couldn't be blank")
    @Email(message = "Provide a valid email adress")
    private String email;

    @NotBlank(message = "Subject couldn't be blank")
    @Size(min = 5, message = "Subject must be at least 5 characters long")
    private String subject;

//    @NotBlank(message = "Message couldn't be blank")
//    @Size(min = 10, message = "Message must be at least 10 characters long")
//    @ColumnDefault("'message'")
    @Column(columnDefinition = "varchar(500) default 'message'")
    private String message;

    private String status;


//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getMobileNum() {
//        return mobileNum;
//    }
//
//    public void setMobileNum(String mobileNum) {
//        this.mobileNum = mobileNum;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    @Override
//    public String toString() {
//        return "Contact{" +
//                "name='" + name + '\'' +
//                ", mobileNum='" + mobileNum + '\'' +
//                ", email='" + email + '\'' +
//                ", subject='" + subject + '\'' +
//                ", message='" + message + '\'' +
//                '}';
//    }
}
