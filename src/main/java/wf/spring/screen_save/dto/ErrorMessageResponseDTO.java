package wf.spring.screen_save.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorMessageResponseDTO {


    private String message;

    private long timestamp;

    public ErrorMessageResponseDTO(String message) {
        this.message = message;
        timestamp = System.currentTimeMillis();
    }

    public ErrorMessageResponseDTO(Exception exception) {
        this.message = exception.getMessage();
        timestamp = System.currentTimeMillis();
    }

}
