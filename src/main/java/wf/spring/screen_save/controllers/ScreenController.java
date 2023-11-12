package wf.spring.screen_save.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wf.spring.screen_save.dto.ErrorMessageResponseDTO;
import wf.spring.screen_save.dto.screen.ScreenGetAllRequestDTO;
import wf.spring.screen_save.dto.screen.ScreenGetRequestDTO;
import wf.spring.screen_save.dto.screen.ScreenResponseDTO;
import wf.spring.screen_save.dto.screen.ScreenShortIdResponseDTO;
import wf.spring.screen_save.mappers.ScreenMapper;
import wf.spring.screen_save.models.ServiceException;
import wf.spring.screen_save.models.entities.Person;
import wf.spring.screen_save.models.entities.Screen;
import wf.spring.screen_save.models.exceptions.AccessException;
import wf.spring.screen_save.models.exceptions.BadRequestException;
import wf.spring.screen_save.models.exceptions.HttpException;
import wf.spring.screen_save.models.exceptions.NotFoundException;
import wf.spring.screen_save.models.exceptions.screen.ScreenExtensionException;
import wf.spring.screen_save.services.ScreenService;

import java.util.List;

@RestController
@RequestMapping("/api/screen")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;
    private final ScreenMapper screenMapper;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ScreenShortIdResponseDTO uploadFile(@RequestParam MultipartFile file, @RequestParam(value = "hours", defaultValue = "-1") int expiredHours, @AuthenticationPrincipal Person person) {
        Screen screen = screenService.save(person, file, expiredHours);

        return new ScreenShortIdResponseDTO(screenService.getShortId(screen));
    }

    @GetMapping("/resource/get_by_short_id")
    public InputStreamResource getScreenResourceByShortId(@RequestBody @Valid ScreenGetRequestDTO screenGetRequestDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        return new InputStreamResource(screenService.findScreenResourceByShortId(screenGetRequestDTO.getShortId()));
    }

    @GetMapping("/resource/{id}")
    public InputStreamResource getScreenResource(@PathVariable long id) {
        return new InputStreamResource(screenService.findScreenResourceById(id));
    }


    @GetMapping("/{id}")
    public ScreenResponseDTO getScreen(@PathVariable long id, @AuthenticationPrincipal Person person) {
        return screenMapper.toScreenResponseDTO(screenService.getById(id, person));
    }

    @GetMapping("/get_all")
    public List<ScreenResponseDTO> getAllScreens(@RequestBody @Valid ScreenGetAllRequestDTO screenGetAllRequestDTO, BindingResult bindingResult, @AuthenticationPrincipal Person person) {
        if(bindingResult.hasErrors())
            throw new BadRequestException(bindingResult);

        return screenMapper.toScreenResponseDTOList(screenService.findAllByPerson(person, screenGetAllRequestDTO.getOffset(), screenGetAllRequestDTO.getLimit()));
    }

    @DeleteMapping("/{id}")
    public void deleteScreen(@PathVariable int id, @AuthenticationPrincipal Person person) {
        screenService.delete(id, person);
    }




    @ExceptionHandler({NotFoundException.class, BadRequestException.class, AccessException.class, ScreenExtensionException.class, ServiceException.class})

    private ResponseEntity<ErrorMessageResponseDTO> exceptionHandler(HttpException e){
        return new ResponseEntity<>(new ErrorMessageResponseDTO(e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    private ResponseEntity<ErrorMessageResponseDTO> exceptionHandler(FileSizeLimitExceededException e){
        return new ResponseEntity<>(new ErrorMessageResponseDTO(e.getMessage()), HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
