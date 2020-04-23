package ru.catn.core.model;

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

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

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
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", userId=" + (user == null ? "" : user.getId()) +
                '}';
    }
}
