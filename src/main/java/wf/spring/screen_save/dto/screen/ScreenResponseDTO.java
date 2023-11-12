package wf.spring.screen_save.dto.screen;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScreenResponseDTO {

    private long id;

    private String shortId;

    private Date createdAt;

    private Date deleteAt;


}
