package wf.spring.screen_save.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "verification_code", indexes = { @Index(columnList = "person_id") })

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "code", nullable = false, length = 6)
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false, unique = true)
    private Person person;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


}
