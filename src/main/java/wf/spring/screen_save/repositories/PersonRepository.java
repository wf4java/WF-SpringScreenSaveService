package wf.spring.screen_save.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wf.spring.screen_save.models.entities.Person;


import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    public Optional<Person> findByUsername(String username);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    @Modifying
    @Query("update Person p set p.verified = :verified where p.id = :id")
    public int setVerified(@Param("id") long id, @Param("verified") boolean verified);

}
