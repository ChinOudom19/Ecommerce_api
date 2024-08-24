package co.oudom.ecommerce.features.address;

import co.oudom.ecommerce.features.address.dto.AddressRequest;
import co.oudom.ecommerce.features.address.dto.AddressResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteByUuid(@PathVariable("uuid") String uuid) {
        addressService.deleteByUuid(uuid);
    }

    @PutMapping("/{uuid}")
    AddressResponse updateByUuid(@PathVariable("uuid") String uuid,@Valid @RequestBody AddressRequest addressRequest) {
        return addressService.updateByUuid(uuid, addressRequest);
    }

    @GetMapping("/{uuid}")
    List<AddressResponse> findListByUuid(@PathVariable String uuid) {
        return addressService.findListByUuid(uuid);
    }

    @PostMapping
    AddressResponse createNew(@Valid @RequestBody AddressRequest addressRequest) {
        return addressService.createNew(addressRequest);
    }

}
