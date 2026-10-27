package com.epam.training.person.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Subscriber {

    @XmlElement(name = "phone-number")
    private String phone;

    @XmlElement(name = "client-name")
    private String clientName;

    // 1. Required No-Arg Constructor
    public Subscriber() {}

    // 2. Convenience Constructor
    public Subscriber(String phone, String clientName) {
        this.phone = phone;
        this.clientName = clientName;
    }

    // Getters
    public String getPhone() { return phone; }
    public String getClientName() { return clientName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(phone, that.phone) &&
                Objects.equals(clientName, that.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, clientName);
    }
}