package uz.kun.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.config.DetailService;
import uz.kun.config.Language;
import uz.kun.exception.ItemAlreadyExistsException;
import uz.kun.exception.ItemNotFoundException;
import uz.kun.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    /** CREATE **/
    public RegionResponse create(RegionRequest request) {
        var regionExists = regionRepository.findByKey(request.getKey()).isPresent();
        if (regionExists)
            throw new ItemAlreadyExistsException("Region already exists, key: " + request.getKey());

        RegionEntity region = toEntity(request);
        regionRepository.save(region);

        return toResponse(region);
    }

    /** READ **/
    public List<RegionResponse> getList() {
        return regionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<RegionResponse> getList(Language lang) {
        return regionRepository.findAll()
                .stream()
                .map(e -> {
                    var response = toResponse(e);
                    switch (lang) {
                        case uz -> response.setName(e.getNameUz());
                        case ru -> response.setName(e.getNameRu());
                        case en -> response.setName(e.getNameEn());
                    }
                    return response;
                })
                .toList();
    }

    public RegionResponse getRegion(Integer id) {
        var region = get(id);
        return toResponse(region);
    }

    public RegionResponse getRegion(Integer id, Language lang) {
        var region = get(id);
        var response = toResponse(region);

        switch (lang){
            case uz -> response.setName(region.getNameUz());
            case en -> response.setName(region.getNameEn());
            case ru -> response.setName(region.getNameRu());
        }

        return response;
    }

    /** UPDATE **/
    public RegionResponse update(Integer id, RegionRequest request) {
        var region = get(id);

        region.setKey(request.getKey());
        region.setNameRu(request.getNameRu());
        region.setNameUz(request.getNameUz());
        region.setNameEn(request.getNameEn());

        regionRepository.save(region);
        return toResponse(region);
    }

    /** DELETE **/
    public boolean delete(Integer id) {
        var region = get(id);
        regionRepository.delete(region);

        return true;
    }

    public boolean delete(String id) {
        var region = get(id);
        regionRepository.delete(region);

        return true;
    }

    private RegionResponse toResponse(RegionEntity region) {
        return RegionResponse.builder()
                .id(region.getId())
                .key(region.getKey())
                .nameUz(region.getNameUz())
                .nameEn(region.getNameEn())
                .nameRu(region.getNameRu())
                .creatorId(region.getCreatorId())
                .createdDate(region.getCreatedDate())
                .build();
    }

    private RegionEntity toEntity(RegionRequest request) {
        UserEntity creator = DetailService.getUser();

        return RegionEntity.builder()
                .key(request.getKey())
                .nameUz(request.getNameUz())
                .nameRu(request.getNameRu())
                .nameEn(request.getNameEn())
                .creatorId(creator.getId())
                .user(creator)
                .createdDate(LocalDateTime.now())
                .build();
    }
    private RegionEntity get(Integer id){
        return regionRepository.findById(id)
                .orElseThrow(()-> new ItemNotFoundException("Region not found, id: "+id));
    }

    private RegionEntity get(String key){
        return regionRepository.findByKey(key)
                .orElseThrow(()-> new ItemNotFoundException("Region not found, key: "+key));
    }
}
