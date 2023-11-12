package wf.spring.screen_save.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "screen", indexes = { @Index(columnList = "delete_at,person_id,id", unique = true) })

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Screen {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "file_name", nullable = false, unique = true, updatable = false, length = 64)
    private String fileName;


    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    @Column(name = "delete_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;


    //---------------------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "person_id", updatable = false, insertable = false)
    private long personId;
    //---------------------------------------------------------


    public void setPerson(Person person) {
        this.person = person;
        this.personId = person.getId();
    }
}
