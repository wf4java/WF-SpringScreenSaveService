package wf.spring.screen_save.services;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.models.entities.VerificationCode;


@Service
@RequiredArgsConstructor
public class MailService {

    private final MailProperties mailProperties;
    private final JavaMailSender emailSender;

    public void sendVerificationCode(Person person, VerificationCode verificationCode){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mailProperties.getUsername());
        message.setTo(person.getEmail());
        message.setSubject("Verification code");
        message.setText("Hello man, this is your verification code: " + verificationCode.getCode());

        emailSender.send(message);
    }

}
