/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sp1d.dvdshare.service.UserSerializer;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author che
 */
@Entity
@Table(name = "requests")
@NamedQueries(
        @NamedQuery(name = "ALL", query = "SELECT dr FROM DiskRequest dr ORDER BY dr.id DESC")
)
public class DiskRequest implements Serializable {

    private static final long serialVersionUID = -599951805620463341L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_id", nullable = false)
    private long id;

    @Column(name = "req_status", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "req_disk")
    private Disk disk;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    @JoinColumn(name = "req_user")
    private User user;

//    public DiskRequest() {
//        this.status = Status.REQUESTED;
//    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public enum Status {

        REQUESTED, CANCELLED, ACCEPTED, REJECTED, TAKEN
    }
}
