package com.dirkjg.mouseion.services;

import com.dirkjg.mouseion.exceptions.ReadFileException;
import com.dirkjg.mouseion.models.PaintingImage;
import com.dirkjg.mouseion.repositories.FileUploadRepository;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {
    private final Path fileStoragePath;
    private final FileUploadRepository repo;

    public FileService(@Value("${my.upload_location}") String fileStorageLocation,
                       FileUploadRepository repo) throws IOException {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.repo = repo;

        Files.createDirectories(fileStoragePath);

    }

    public String storeFile(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        repo.save(new PaintingImage(fileName));
        return fileName;
    }

    public Resource downloadFile(String fileName) {

        Path path = fileStoragePath.resolve(fileName);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new ReadFileException("Een probleem in het lezen van het bestand", e);
        }

        if(resource.exists()&& resource.isReadable()) {
        return resource;
        } else {
            throw new ReadFileException("Het bestand bestaat niet of is niet leesbaar");
        }
    }

}
