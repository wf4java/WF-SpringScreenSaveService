package wf.spring.screen_save.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wf.spring.screen_save.properties.JwtProperties;
import wf.spring.screen_save.security.PersonDetails;


import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtAuthService {

    private final PersonDetailsService personDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;




    public String validateAndGenerateToken(String username, String password) throws AuthenticationException {
        PersonDetails personDetails = personDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, personDetails.getPassword()))
            throw new BadCredentialsException("Password not valid");

        if (!personDetails.isEnabled()) throw new DisabledException("This account not verified");

        return generateTokenFromId(personDetails.getPerson().getId());
    }


    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String jwtToken) throws AuthenticationException {
        if (jwtToken == null)
            throw new AuthenticationCredentialsNotFoundException("Token was empty");

        long id;

        try {id = validateTokenAndRetrieveSubjectToId(jwtToken);}
        catch (JWTVerificationException exception) {throw new BadCredentialsException("Token not valid");}

        PersonDetails personDetails = personDetailsService.loadUserById(id);

        if (!personDetails.isEnabled()) throw new DisabledException("This account not verified");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                personDetails.getPerson(),
                personDetails.getPerson().getPassword(),
                personDetails.getAuthorities()
        );
        authenticationToken.setDetails(personDetails);

        return authenticationToken;
    }


    public PersonDetails validateForActive(String username, String password) throws AuthenticationException {
        PersonDetails personDetails = personDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(password, personDetails.getPassword()))
            throw new BadCredentialsException("Password not valid");

        if(personDetails.isEnabled()) throw new AuthenticationServiceException("This account already verified");

        return personDetails;
    }


    private String generateTokenFromId(long id) {

        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(jwtProperties.getExpirationDays()).toInstant());

        return JWT.create()
                .withSubject("user_details")
                .withClaim("id", id)
                .withIssuer(jwtProperties.getIssuer())
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }

    private long validateTokenAndRetrieveSubjectToId(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                .withSubject("user_details")
                .withIssuer(jwtProperties.getIssuer())
                .build();


        return Optional.ofNullable(jwtVerifier.verify(token).getClaim("id").asLong())
                .orElseThrow(() -> new AuthenticationServiceException("JWT token invalid"));
    }

}