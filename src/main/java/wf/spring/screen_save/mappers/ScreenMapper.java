package wf.spring.screen_save.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wf.spring.screen_save.dto.screen.ScreenResponseDTO;
import wf.spring.screen_save.models.entities.Screen;
import wf.spring.screen_save.services.ScreenService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScreenMapper {

    private final ScreenService screenService;


    public ScreenResponseDTO toScreenResponseDTO(Screen screen) {
        ScreenResponseDTO screenResponseDTO = new ScreenResponseDTO();

        screenResponseDTO.setId(screen.getId());
        screenResponseDTO.setShortId(screenService.getShortId(screen));
        screenResponseDTO.setCreatedAt(screen.getCreatedAt());
        screenResponseDTO.setDeleteAt(screen.getDeleteAt());

        return screenResponseDTO;
    }

    public List<ScreenResponseDTO> toScreenResponseDTOList(List<Screen> screens) {
        return screens.stream().map(this::toScreenResponseDTO).toList();
    }


}
