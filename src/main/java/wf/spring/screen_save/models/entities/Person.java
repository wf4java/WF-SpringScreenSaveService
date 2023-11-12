package wf.spring.screen_save.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.util.Date;

@Entity
@Table(name = "person")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Cloneable, Principal {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "verified", nullable = false)
    private boolean verified;


    @Override
    public Person clone() {
        try {return (Person) super.clone();} catch (CloneNotSupportedException e) {throw new RuntimeException(e);}
    }

    @Override
    public String getName() {
        return username;
    }

}
