package co.oudom.ecommerce.features.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequest (

        @NotBlank(message = "User is request")
        String userUuid,

        @NotNull(message = "Phone Number is request")
        String phoneNumber,

        @NotBlank(message = "Street is request")
        String street,

        @NotBlank(message = "Village is request")
        String village,

        @NotBlank(message = "Commune or Sangkat is request")
        String communeOrSangKat,

        @NotBlank(message = "District or Khan is request")
        String districtOrKhan,

        @NotBlank(message = "Province or City is request")
        String provinceOrCity

){
}
