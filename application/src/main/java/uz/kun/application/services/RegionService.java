package uz.kun.application.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.application.models.exception.APIException;
import uz.kun.domain.entities.Region;
import uz.kun.domain.models.request.RegionRequest;
import uz.kun.domain.models.response.RegionResponse;
import uz.kun.domain.usecases.RegionUseCase;
import uz.kun.infrastructure.jpa.repository.RegionJpaRepository;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class RegionService implements RegionUseCase {

    private final RegionJpaRepository regionJpaRepository;

    @Override
    public void create(RegionRequest regionRequest) {
        var optionalRegion = regionJpaRepository.findByKey(regionRequest.key());
        if (optionalRegion.isPresent()) throw new APIException("Region exist...", 400);

        var region = regionRequest.mapToEntity();
        regionJpaRepository.save(region);
    }

    @Override
    public RegionResponse read(Integer regionId) {
        return regionJpaRepository
                .findById(regionId)
                .map(Region::mapToResponse)
                .orElseThrow(() -> new APIException("Region not found...", 404));
    }

    @Override
    public RegionResponse update(Integer regionId, RegionRequest regionRequest) {
        var regionEntity = regionJpaRepository
                .findById(regionId)
                .orElseThrow(() -> new APIException("Region not found...", 404));

        regionEntity.setKey(regionRequest.key());
        regionEntity.setNameEn(regionRequest.nameEn());
        regionEntity.setNameUz(regionRequest.nameUz());
        regionEntity.setNameRu(regionRequest.nameRu());

        regionJpaRepository.save(regionEntity);
        return regionEntity.mapToResponse();
    }

    @Override
    public void delete(Integer regionId) {
        regionJpaRepository.deleteById(regionId);
    }

    @Override
    public List<RegionResponse> regionsList() {
        return regionJpaRepository
                .findAll()
                .stream()
                .map(Region::mapToResponse)
                .toList();
    }
}
