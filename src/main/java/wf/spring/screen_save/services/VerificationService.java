package wf.spring.screen_save.services;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.models.entities.VerificationCode;
import wf.spring.screen_save.models.exceptions.authentication.IncorrectVerificationCodeException;
import wf.spring.screen_save.models.exceptions.authentication.NotFoundVerificationException;
import wf.spring.screen_save.models.exceptions.authentication.VerificationCodeExpiredException;
import wf.spring.screen_save.properties.VerificationProperties;
import wf.spring.screen_save.repositories.PersonRepository;
import wf.spring.screen_save.repositories.VerificationCodeRepository;
import wf.spring.screen_save.security.PersonDetails;
import wf.utils.java.algoritms.random.RandomStringGenerator;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VerificationService {

    private final long VERIFICATION_EXPIRATION_DELETE_TIME = 24 * (60 * 60 * 1000);

    private final VerificationCodeRepository verificationCodeRepository;
    private final VerificationProperties verificationProperties;
    private final PersonRepository personRepository;
    private final MailService mailService;



    @PostConstruct
    @Transactional
    public void init(){
        verificationCodeRepository.deleteAll();
    }


    @Scheduled(fixedRate = VERIFICATION_EXPIRATION_DELETE_TIME, initialDelay = VERIFICATION_EXPIRATION_DELETE_TIME)
    @Transactional
    public void deleteExpiredVerificationCodes(){
        Date date = Date.from(ZonedDateTime.now().minusMinutes(verificationProperties.getCodeExpiredMinutes()).toInstant());
        verificationCodeRepository.deleteAllByCreatedAtBefore(date);
    }


    @Transactional
    public void sendCode(PersonDetails personDetails) {
        VerificationCode verificationCode = generateVerification(personDetails.getPerson());
        mailService.sendVerificationCode(personDetails.getPerson(), verificationCode);
    }

    @Transactional
    public void verificationPerson(PersonDetails personDetails, String code) {
        VerificationCode verificationCode = verificationCodeRepository.findByPersonId(personDetails.getPerson().getId())
                .orElseThrow(() -> new NotFoundVerificationException("Verification not found"));

        if(verificationCode.getCreatedAt().before(Date.from(ZonedDateTime.now().minusMinutes(verificationProperties.getCodeExpiredMinutes()).toInstant())))
            throw new VerificationCodeExpiredException("This verification code expired, send new");

        if(!verificationCode.getCode().equals(code))
            throw new IncorrectVerificationCodeException("This verification code incorrect");


        personRepository.setVerified(personDetails.getPerson().getId(), true);
        verificationCodeRepository.delete(verificationCode);
    }


    @Transactional
    public VerificationCode generateVerification(Person person) {
        return verificationCodeRepository.findByPerson(person)
                .map(existingVerification -> {
                    existingVerification.setCreatedAt(new Date());
                    existingVerification.setCode(RandomStringGenerator.generateFromDigits(6));
                    return verificationCodeRepository.save(existingVerification);
                })
                .orElseGet(() -> {
                    VerificationCode newVerification = new VerificationCode();
                    newVerification.setPerson(person);
                    newVerification.setCreatedAt(new Date());
                    newVerification.setCode(RandomStringGenerator.generateFromDigits(6));
                    return verificationCodeRepository.save(newVerification);
                });
    }

    public VerificationCode getReferenceById(long id){
        return verificationCodeRepository.getReferenceById(id);
    }

}
