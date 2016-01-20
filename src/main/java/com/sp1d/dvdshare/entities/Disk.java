/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sp1d.dvdshare.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author sp1d
 */
@Entity
@Table(name = "disks")
public class Disk {

    @Transient
    private static final Logger LOG = LogManager.getLogger(Disk.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disk_id", nullable = false)
    private long id;

    @Column(name = "disk_title", nullable = false)
    private String title;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    @JoinColumn(name = "disk_owner", nullable = false)
    private User owner;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    @JoinColumn(name = "disk_holder", nullable = false)
    private User holder;

    public Disk() {
        LOG.debug("Constructed {}", this.toString());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }
public enum Field {
    ID, TITLE, OWNER, HOLDER
}

}
