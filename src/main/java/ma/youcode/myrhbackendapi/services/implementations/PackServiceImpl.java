package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.PackRequest;
import ma.youcode.myrhbackendapi.dto.responses.PackResponse;
import ma.youcode.myrhbackendapi.entities.Pack;
import ma.youcode.myrhbackendapi.exceptions.ResourceAlreadyExistException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.PackRepository;
import ma.youcode.myrhbackendapi.services.PackService;
import ma.youcode.myrhbackendapi.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {

    private final PackRepository packRepository;
    private final ModelMapper mapper;

    @Override
    public List<PackResponse> getAll() {
        List<Pack> packs = packRepository.findAll();
        if (packs.isEmpty()) throw new ResourceNotFoundException("No Packs Found");
        return packs.stream().map(pack -> mapper.map(pack, PackResponse.class)).toList();
    }

    @Override
    public Page<PackResponse> getAll(Pageable pageable) {
        Page<Pack> packs = packRepository.findAll(pageable);
        if (packs.isEmpty()) throw new ResourceNotFoundException("No Packs Found");
        return packs.map(pack -> mapper.map(pack, PackResponse.class));
    }

    @Override
    public Optional<PackResponse> find(String id) {
        Pack pack = packRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Pack Found with ID: " + id));
        return Optional.of(mapper.map(pack, PackResponse.class));
    }

    @Override
    public Optional<PackResponse> create(PackRequest packRequest) {
        Pack existPack = packRepository.findByNameAndPrice(packRequest.getName(), packRequest.getPrice())
                .orElseThrow(() -> new ResourceAlreadyExistException("Pack Already exist with this Info"));
        Pack pack = mapper.map(packRequest, Pack.class);
        Pack savedPack = packRepository.save(pack);
        return Optional.of(mapper.map(savedPack, PackResponse.class));
    }

    @Override
    public Optional<PackResponse> update(PackRequest packRequest, String id) {
        return Optional.empty();
    }

    @Override
    public boolean destroy(String id) {
        return false;
    }
}
