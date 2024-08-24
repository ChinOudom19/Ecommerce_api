package co.oudom.ecommerce.features.address;

import co.oudom.ecommerce.features.address.dto.AddressRequest;
import co.oudom.ecommerce.features.address.dto.AddressResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    void deleteByUuid(String uuid);

    AddressResponse updateByUuid(String uuid, AddressRequest addressRequest);

    List<AddressResponse> findListByUuid(String uuid);

    AddressResponse createNew(AddressRequest addressRequest);

}
