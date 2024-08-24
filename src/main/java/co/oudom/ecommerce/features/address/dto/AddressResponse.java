package co.oudom.ecommerce.features.address.dto;

import lombok.Builder;

@Builder
public record AddressResponse(

        String uuid,

        String phoneNumber,

        String street,

        String village,

        String communeOrSangKat,

        String districtOrKhan,

        String provinceOrCity

) {
}
