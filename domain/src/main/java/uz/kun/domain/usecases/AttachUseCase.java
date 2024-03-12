package uz.kun.domain.usecases;

import uz.kun.domain.models.request.AttachRequest;
import uz.kun.domain.models.response.AttachResponse;

public interface AttachUseCase {

    AttachResponse uploadFile(AttachRequest attachRequest);
    AttachResponse getFile(String fileId);
    byte[] open(String fileName);
    void delete(String fileName);

}
