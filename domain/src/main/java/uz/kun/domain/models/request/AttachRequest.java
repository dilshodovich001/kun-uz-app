package uz.kun.domain.models.request;

import uz.kun.domain.entities.Attach;

public record AttachRequest(
        String filename,
        byte[] bytes,
        long size) {
    public Attach mapToEntity(){
        return Attach.builder()
                .filename(filename)
                .size(size)
                .build();
    }
}
