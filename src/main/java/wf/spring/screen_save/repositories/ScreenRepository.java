package wf.spring.screen_save.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.models.entities.Screen;

import java.util.Date;
import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

    public List<Screen> findAllByDeleteAtBefore(Date deleteAt);

    public void deleteAllByDeleteAtBefore(Date deleteAt);

    public List<Screen> findAllByPerson(Person person, Pageable pageable);

    @Query(value = "SELECT * FROM screen s where s.person_id = :person_id and s.id > :offset_id and (s.delete_at IS NULL or s.delete_at > :date) order by s.id limit :limit", nativeQuery = true)
    public List<Screen> findAllByPersonIdAndLinkOffset(@Param(value = "person_id") long ownerId , @Param(value = "offset_id")
                                                        long offsetLinkId, @Param(value = "limit") int limit, @Param("date") Date date);

}
