package com.sp1d.dvdshare.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sp1d.dvdshare.service.UserSerializer;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author che
 */
@Entity
@Table(name = "requests")
//uniqueConstraints = @UniqueConstraint(columnNames = {"req_user","req_disk"})
@NamedQueries({
    @NamedQuery(name = "ALL", query = "SELECT dr FROM DiskRequest dr ORDER BY dr.id DESC"),
    @NamedQuery(name = "OUT", query = "SELECT dr FROM DiskRequest dr WHERE dr.user = :user ORDER BY dr.id DESC"),
    @NamedQuery(name = "IN", query = "SELECT dr FROM DiskRequest dr JOIN dr.disk d JOIN d.owner o WHERE o = :user"),
    @NamedQuery(name = "COUNT-IN", query = "SELECT COUNT(dr) FROM DiskRequest dr JOIN dr.disk d JOIN d.owner o WHERE o = :user"),
    @NamedQuery(name = "COUNT-NEW-IN", query = "SELECT COUNT(dr) FROM DiskRequest dr JOIN dr.disk d JOIN d.owner o WHERE o = :user AND dr.status = 'REQUESTED'"),
    @NamedQuery(name = "COUNT-BYDISK", query = "SELECT COUNT(dr) FROM DiskRequest dr WHERE dr.disk = :disk"),
    @NamedQuery(name = "BYDISK", query = "SELECT dr FROM DiskRequest dr WHERE dr.disk.id = :diskid")

})
public class DiskRequest implements Serializable {

    private static final long serialVersionUID = -599951805620463341L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_id", nullable = false)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "req_status", nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "req_disk", unique = true)
    private Disk disk;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    @JoinColumn(name = "req_user")
    private User user;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.disk);
        hash = 71 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DiskRequest other = (DiskRequest) obj;
        if (!Objects.equals(this.disk, other.disk)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

}
