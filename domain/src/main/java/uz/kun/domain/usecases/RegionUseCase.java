package uz.kun.domain.usecases;

import uz.kun.domain.models.request.RegionRequest;
import uz.kun.domain.models.response.RegionResponse;

import java.util.List;

public interface RegionUseCase extends CrudUseCase<Integer, RegionRequest, RegionResponse> {
    List<RegionResponse> regionsList();
}
