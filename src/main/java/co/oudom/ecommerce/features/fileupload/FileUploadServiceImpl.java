package co.oudom.ecommerce.features.fileupload;

import co.oudom.ecommerce.features.fileupload.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file-upload.server-path}")
    private String serverPath;

    @Value("${file-upload.base-uri}")
    private String baseUri;


    @Override
    public void deleteByFileName(String fileName) {

        Path path = Paths.get(serverPath + fileName);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
                log.info("Deleted file: {}", fileName);
            } catch (IOException e) {
                log.error("Failed to delete file: {}. Error: {}", fileName, e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "File deletion failed");
            }
        } else {
            log.warn("File not found: {}", fileName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "File not found");
        }

    }

    @Override
    public List<FileUploadResponse> uploadProductItem(List<MultipartFile> files) {

        return files.stream()
                .map(this::uploadProduct)
                .collect(Collectors.toList());

    }

    @Override
    public FileUploadResponse uploadShop(MultipartFile file) {
        return handleFileUpload(file);
    }

    @Override
    public FileUploadResponse uploadProfile(MultipartFile file) {
        return handleFileUpload(file);
    }

    @Override
    public FileUploadResponse uploadCategory(MultipartFile file) {
        return  handleFileUpload(file);
    }

    @Override
    public FileUploadResponse uploadProduct(MultipartFile file) {
        return handleFileUpload(file);
    }

    private FileUploadResponse handleFileUpload(MultipartFile file) {

        String extension = Objects.requireNonNull(file.getContentType()).split("/")[1];
        String newFileName = String.format("%s.%s",
                UUID.randomUUID(),
                extension);

        log.info("Extension: {}", extension);
        log.info("New File Name: {}", newFileName);

        Path path = Paths.get(serverPath + newFileName);
        try {
            Files.copy(file.getInputStream(), path);
            log.info("Uploaded file: {}", newFileName);
        } catch (IOException e) {
            log.error("Failed to upload file: {}. Error: {}", newFileName, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload failed");
        }
        return FileUploadResponse.builder()
                .name(newFileName)
                .url(baseUri + newFileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .build();

    }

}
