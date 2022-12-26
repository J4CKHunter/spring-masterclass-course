package com.eazybytes.eazyschool.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity{

    // bu tabloda primary key'imiz yok database'de
    // fakat jpa bizden istiyor çünkü kendi methodları içinde buna ihtiyacı var
    // biz de mantıken primary key olabilecek en mantıklı adayın gün olduğuna
    // karar verdik çünkü her özel gün unique bir değer
    @Id
    private String day;
    private String reason;

    @Enumerated(EnumType.STRING)
    private Type type;

//    private final String day;
//    private final String reason;
//    private final Type type;
//
    public enum Type{
        FEDERAL, FESTIVAL
    }

//    public Holiday(String day, String reason, Type type) {
//        this.day = day;
//        this.reason = reason;
//        this.type = type;
//    }
//
//    public String getDay() {
//        return day;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//    public Type getType() {
//        return type;
//    }
}
