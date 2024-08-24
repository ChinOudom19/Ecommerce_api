package co.oudom.ecommerce.features.fileupload;

import co.oudom.ecommerce.features.fileupload.dto.FileUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileUploadService {

    void deleteByFileName(String fileName);

    List<FileUploadResponse> uploadProductItem(List<MultipartFile> files);

    FileUploadResponse uploadShop(MultipartFile file);

    FileUploadResponse uploadProfile(MultipartFile file);

    FileUploadResponse uploadCategory(MultipartFile file);

    FileUploadResponse uploadProduct(MultipartFile file);

}
