package ru.catn.core.model;

import ru.catn.webserver.servlet.ExcludeJson;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "street", nullable = false)
    private String street;

    @ExcludeJson
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    transient private User user;

    public Address() {
    }

    public Address(String street) {
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return street;
    }
}
