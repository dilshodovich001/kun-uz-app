package uz.kun.service.region;

import uz.kun.model.request.RegionRequest;
import uz.kun.model.response.RegionResponse;

import java.util.List;

public interface RegionService {
    void createRegion(RegionRequest regionRequest);
    List<RegionResponse> getAllRegionsList();
    RegionResponse getRegionById(Integer regionId);
    void updateRegion(Integer regionId, RegionRequest regionRequest);
    void deleteRegionById(Integer regionId);
}
