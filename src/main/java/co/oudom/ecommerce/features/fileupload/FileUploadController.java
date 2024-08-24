package co.oudom.ecommerce.features.fileupload;

import co.oudom.ecommerce.features.fileupload.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fileName}")
    void deleteByFileName(@PathVariable String fileName) {
        fileUploadService.deleteByFileName(fileName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image-product-item")
    List<FileUploadResponse> uploadProductItem(@RequestPart List<MultipartFile> files) {
        return fileUploadService.uploadProductItem(files);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image-shop")
    FileUploadResponse uploadShop(@RequestPart MultipartFile file) {
        return fileUploadService.uploadShop(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image-profile")
    FileUploadResponse uploadProfile(@RequestPart MultipartFile file) {
        return fileUploadService.uploadProfile(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image-category")
    FileUploadResponse uploadCategory(@RequestPart MultipartFile file) {
        return fileUploadService.uploadCategory(file);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/image-product")
    FileUploadResponse uploadProduct(@RequestPart MultipartFile file) {
        return fileUploadService.uploadProduct(file);
    }

}
