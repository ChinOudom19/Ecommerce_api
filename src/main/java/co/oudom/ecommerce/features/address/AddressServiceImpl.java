package co.oudom.ecommerce.features.address;

import co.oudom.ecommerce.domain.Address;
import co.oudom.ecommerce.domain.User;
import co.oudom.ecommerce.features.address.dto.AddressRequest;
import co.oudom.ecommerce.features.address.dto.AddressResponse;
import co.oudom.ecommerce.features.user.UserRepository;
import co.oudom.ecommerce.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public void deleteByUuid(String uuid) {

        Address address  = addressRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address not found"));

        addressRepository.delete(address);

    }

    @Override
    public AddressResponse updateByUuid(String uuid, AddressRequest addressRequest) {

        Address address = addressRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address not found"));
        address.setPhoneNumber(addressRequest.phoneNumber());
        address.setStreet(addressRequest.street());
        address.setVillage(addressRequest.village());
        address.setCommuneOrSangKat(addressRequest.communeOrSangKat());
        address.setDistrictOrKhan(addressRequest.districtOrKhan());
        address.setProvinceOrCity(addressRequest.provinceOrCity());

        addressRepository.save(address);

        return addressMapper.toAddressResponse(address);

    }

    @Override
    public List<AddressResponse> findListByUuid(String uuid) {
        // Fetch the user from the repository
        User user = userRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Assuming User class has a method getAddresses() that returns a List<Address>
        List<Address> addresses = user.getAddresses();

        // Convert Address objects to AddressResponse objects using the AddressMapper
        return addresses.stream()
                .map(addressMapper::toAddressResponse)
                .collect(Collectors.toList());

    }

    // Create Address
    @Override
    public AddressResponse createNew(AddressRequest addressRequest) {

        User user = userRepository
                .findByUuid(addressRequest.userUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Transfer DTO to Domain
        Address address = addressMapper.fromAddressRequest(addressRequest);
        address.setUuid(UUID.randomUUID().toString());
        address.setUser(user);

        // Save address into database and get latest data back
        addressRepository.save(address);

        return addressMapper.toAddressResponse(address);

    }

}
