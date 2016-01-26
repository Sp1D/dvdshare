package com.sp1d.dvdshare.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sp1d.dvdshare.service.UserSerializer;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author sp1d
 */
@NamedQuery(name = "DISK-ID", query = "SELECT ti FROM TakenItem ti WHERE ti.disk.id = :id")
@Entity
@Table(name = "takenitems", uniqueConstraints = @UniqueConstraint(columnNames = {"ti_user", "ti_disk"}))
public class TakenItem implements Serializable {

    private static final long serialVersionUID = -7111578635890472013L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ti_id", nullable = false)
    long id;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    @JoinColumn(name = "ti_user")
    User user;

    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    @JoinColumn(name = "ti_owner")
    User owner;

    @OneToOne
    @JoinColumn(name = "ti_disk")
    Disk disk;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ti_date")
    Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 83 * hash + Objects.hashCode(this.user);
        hash = 83 * hash + Objects.hashCode(this.owner);
        hash = 83 * hash + Objects.hashCode(this.disk);
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
        final TakenItem other = (TakenItem) obj;
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        if (!Objects.equals(this.disk, other.disk)) {
            return false;
        }
        return true;
    }

}
