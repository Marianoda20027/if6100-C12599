package ucr.ac.C12599.JPA.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.util.UUID;
@Entity // entidad de JPA
@Table(name = "users")
public class userEntity {

    @Id
    private UUID id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;

    public userEntity(){


    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
