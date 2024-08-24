package co.oudom.ecommerce.features.fileupload.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(

        String name,
        String url,
        String contentType,
        Long size

) {

}
