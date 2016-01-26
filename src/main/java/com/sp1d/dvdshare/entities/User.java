package com.sp1d.dvdshare.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
 * Пользователь системы. В связке имя-пароль используются поля email, password.
 * Поля plainPassword и plainPasswordCheck нигде не хранятся, очищаются при записи
 * пользователя в базу, используются для приема пароля из формы.
 *
 * @author sp1d
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = -4674771583377131450L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long id;

    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "user_password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<DiskRequest> requests;

    @Transient
    private String plainPassword;

    @Transient
    private String plainPasswordCheck;

    @Column(name = "user_enabled", nullable = false)
    private boolean enabled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPlainPasswordCheck() {
        return plainPasswordCheck;
    }

    public void setPlainPasswordCheck(String plainPasswordCheck) {
        this.plainPasswordCheck = plainPasswordCheck;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<DiskRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<DiskRequest> requests) {
        this.requests = requests;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.email);
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
        final User other = (User) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", username=" + username + ", password=" + "secret" + ", enabled=" + enabled + '}';
    }

}
