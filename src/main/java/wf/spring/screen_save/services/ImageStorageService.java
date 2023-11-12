package wf.spring.screen_save.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wf.spring.screen_save.models.exceptions.ServiceException;
import wf.spring.screen_save.models.exceptions.screen.ScreenExtensionException;
import wf.spring.screen_save.properties.ImageStorageProperties;
import wf.spring.screen_save.utils.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageStorageService {


    private static final Logger log = LoggerFactory.getLogger(ImageStorageService.class);
    private final ImageStorageProperties imageStorageProperties;
    private Set<String> imageExtensions;


    @PostConstruct
    public void init() {
        try {Files.createDirectories(Paths.get(imageStorageProperties.getPath()));}
        catch (IOException e) {throw new RuntimeException(e);}

        imageExtensions = new HashSet<>(imageStorageProperties.getExtensions());
    }


    

    public FileInputStream getImage(String name){
        try {return new FileInputStream(Paths.get(imageStorageProperties.getPath(), name).toString());}
        catch (IOException e) {
            log.error("Cannot read file : {}", e.getMessage());
            throw new ServiceException("Cannot read file : " + e.getMessage());
        }
    }

    @Async
    public void deleteImageAsync(String name) {
        deleteImage(name);
    }

    private void deleteImage(String name) {
        Path path = Paths.get(imageStorageProperties.getPath(), name);

        if (Files.exists(path))
            try {Files.delete(Paths.get(imageStorageProperties.getPath(), name));}
            catch (NoSuchFileException e) {log.warn(e.getMessage());}
            catch (IOException e) {log.error(e.getMessage());}
    }

    @Async
    public void deleteImagesAsync(List<String> names) {
        for(String name : names) {
            deleteImageAsync(name);
        }
    }





    public String storeImage(MultipartFile file) {
        Path path = Paths.get(imageStorageProperties.getPath());
        String extension = FileUtils.getExtension(file.getOriginalFilename());

        if (!imageExtensions.contains(extension))
            throw new ScreenExtensionException("Image extension is not allowed");


        String name = UUID.randomUUID() + "." + extension;

        try {
            if (file.isEmpty()) {
                log.warn("Cannot save empty file with name : {}", name);
                throw new RuntimeException("File is empty");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, path.resolve(name), StandardCopyOption.REPLACE_EXISTING);
                return name;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Avatar save error");
        }
    }


}
