package com.fireal99;

import com.fireal99.util.DataTransferObject;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order implements DataTransferObject {
    private long id;
    private LocalDate creationDate;
    private BigDecimal totalDue;
    private String status;
    private Customer customer;
    private SalesPerson salesPerson;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", totalDue=" + totalDue +
                ", status='" + status + '\'' +
                ", customer=" + customer +
                ", salesPerson=" + salesPerson +
                '}';
    }
}
