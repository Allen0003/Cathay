package org.example.coindesk.entity;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @Column(length = 10)
    private String code;

    @Column(nullable = false)
    private String name;

    public Currency() {
    }

    public Currency(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getter & Setter
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
