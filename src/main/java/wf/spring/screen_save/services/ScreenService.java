package wf.spring.screen_save.services;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.models.entities.Screen;
import wf.spring.screen_save.models.exceptions.AccessException;
import wf.spring.screen_save.models.exceptions.NotFoundException;
import wf.spring.screen_save.repositories.ScreenRepository;
import wf.spring.screen_save.utils.EncodeUtils;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScreenService {


    private final long SCREEN_EXPIRATION_DELETE_TIME = 10 * 60 * 1000;

    private final ScreenRepository screenRepository;
    private final ImageStorageService imageStorageService;





    @Async
    @Scheduled(fixedRate = SCREEN_EXPIRATION_DELETE_TIME)
    @Transactional
    public void deleteExpiredScreens() {
        Date date = new Date();
        imageStorageService.deleteImagesAsync(screenRepository.findAllByDeleteAtBefore(date).stream().map(Screen::getFileName).toList());
        screenRepository.deleteAllByDeleteAtBefore(date);
    }



    @Transactional
    public Screen save(Person person, MultipartFile image, int expiredHours) {
        String fileName = imageStorageService.storeImage(image);

        Screen screen = new Screen();

        screen.setPerson(person);
        screen.setCreatedAt(new Date());
        screen.setFileName(fileName);

        if(expiredHours > 0)
            screen.setDeleteAt(Date.from(ZonedDateTime.now().plusHours(expiredHours).toInstant()));

        return screenRepository.save(screen);
    }




    public List<Screen> findAllByPerson(Person person, long offset, int limit) {
        return screenRepository.findAllByPersonIdAndLinkOffset(person.getId(), offset, limit, new Date());
    }

    public Screen getById(long id) {
        Screen screen = screenRepository.findById(id).orElseThrow(() -> new NotFoundException("Screen with this id not found"));

        if(screen.getDeleteAt() != null && screen.getDeleteAt().before(new Date()))
            throw new NotFoundException("Screen with this id not found");

        return screen;
    }

    public Screen getById(long id, Person person) {
        Screen screen = getById(id);

        if(screen.getPersonId() != person.getId())
            throw new AccessException("You don't have permission to get this screen");

        return screen;
    }


    @Transactional(propagation = Propagation.NEVER)
    public String getShortId(Screen screen) {
        return EncodeUtils.Encoder.RADIX_62.encode(screen.getId());
    }



    public InputStream findScreenResourceByShortId(String shortId) {
        return findScreenResourceById(EncodeUtils.Encoder.RADIX_62.decode(shortId));
    }

    public InputStream findScreenResourceById(long id) {
        Screen screen = getById(id);

        return imageStorageService.getImage(screen.getFileName());
    }






    @Transactional
    public void delete(long id, Person person) {
        Screen screen = getById(id);

        if(screen.getPersonId() != person.getId())
            throw new AccessException("You don't have permission to delete this screen");

        imageStorageService.deleteImageAsync(screen.getFileName());
        screenRepository.delete(screen);
    }



}
