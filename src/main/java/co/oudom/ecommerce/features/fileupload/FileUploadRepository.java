package co.oudom.ecommerce.features.fileupload;

import co.oudom.ecommerce.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<Image, Integer> {


}
