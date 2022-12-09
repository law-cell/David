package com.murex.retail.model.order;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;

@Entity(name = "Order")
@Table(name = "ORDER_TABLE")
public class Order {
    @Id
    private String id;
    @Enumerated
    private OrderStatus status;
    private long assemblyTime, startTime;

    @ElementCollection(targetClass = String.class, fetch = EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_id")
    @Cascade(CascadeType.ALL)
    private List<String> idList;

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.status = OrderStatus.RECEIVED;
        this.assemblyTime = 0;
        this.startTime = System.currentTimeMillis();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<String> getIdList() {
        return this.idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public long getAssemblyTime() {
        return this.assemblyTime;
    }

    public void setAssemblyTime(long assemblyTime) {
        this.assemblyTime = assemblyTime;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Order {\n" +
                "\tcomponentIds: " + this.idList + ",\n" +
                "\torderId: " + this.id + "\n" +
                "\torderStatus: " + this.status + "\n" +
                "\tassemblyTime: " + this.assemblyTime + "\n" +
                '}';
    }
}