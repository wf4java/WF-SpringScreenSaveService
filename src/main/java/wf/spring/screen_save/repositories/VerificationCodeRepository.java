package wf.spring.screen_save.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.models.entities.VerificationCode;

import java.util.Date;
import java.util.Optional;


@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    public Optional<VerificationCode> findByPerson(Person person);

    public Optional<VerificationCode> findByPersonId(long personId);

    public void deleteAllByCreatedAtBefore(Date createdAt);

}
