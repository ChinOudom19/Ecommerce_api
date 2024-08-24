package co.oudom.ecommerce.mapper;

import co.oudom.ecommerce.domain.Address;
import co.oudom.ecommerce.features.address.dto.AddressRequest;
import co.oudom.ecommerce.features.address.dto.AddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address fromAddressRequest(AddressRequest addressRequest);

    AddressResponse toAddressResponse(Address address);

}
