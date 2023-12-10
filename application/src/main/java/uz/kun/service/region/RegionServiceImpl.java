package uz.kun.service.region;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.entity.Region;
import uz.kun.model.request.RegionRequest;
import uz.kun.model.response.RegionResponse;
import uz.kun.repository.RegionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    /**
     * @param regionRequest
     */
    @Override
    public void createRegion(RegionRequest regionRequest) {
        var regionExists = regionRepository
                .findByKey(regionRequest.getKey())
                .isPresent();

        if (regionExists) {
            throw new RuntimeException("Region already exists");
        }

        var regionEntity = Region.builder()
                .key(regionRequest.getKey())
                .nameEn(regionRequest.getNameEn())
                .nameRu(regionRequest.getNameRu())
                .nameUz(regionRequest.getNameUz())
                .creatorId(new Random().nextInt(1,1000)) //todo: authenticated user id...
                .createdDate(LocalDateTime.now())
                .build();

        regionRepository.save(regionEntity);
    }

    /**
     * @return
     */
    @Override
    public List<RegionResponse> getAllRegionsList() {
        return regionRepository
                .findAll()
                .stream()
                .map(mapToResponse())
                .toList();
    }

    private Function<Region, RegionResponse> mapToResponse() {
        return region -> RegionResponse.builder()
                .id(region.getId())
                .key(region.getKey())
                .nameUz(region.getNameUz())
                .nameRu(region.getNameRu())
                .nameEn(region.getNameEn())
                .createdDate(region.getCreatedDate())
                .build();
    }

    /**
     * @param regionId
     * @return
     */
    @Override
    public RegionResponse getRegionById(Integer regionId) {
        return regionRepository
                .findById(regionId)
                .map(mapToResponse())
                .orElseThrow();
    }

    /**
     * @param regionId
     * @param regionRequest
     */
    @Override
    public void updateRegion(Integer regionId, RegionRequest regionRequest) {
        var regionEntity = regionRepository
                .findById(regionId)
                .orElseThrow();

        regionEntity.setNameEn(regionRequest.getNameEn());
        regionEntity.setNameRu(regionRequest.getNameRu());
        regionEntity.setNameUz(regionRequest.getNameUz());

        regionRepository.save(regionEntity);
    }

    /**
     * @param regionId
     */
    @Override
    public void deleteRegionById(Integer regionId) {
        var regionEntity = regionRepository
                .findById(regionId)
                .orElseThrow();

        regionRepository.delete(regionEntity);
    }
}
