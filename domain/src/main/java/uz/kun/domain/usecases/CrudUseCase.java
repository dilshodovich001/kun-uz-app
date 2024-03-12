package uz.kun.domain.usecases;

public interface CrudUseCase<ID, REQUEST, RESPONSE> {
    void create(REQUEST request);
    RESPONSE read(ID id);
    RESPONSE update(ID id, REQUEST request);
    void delete(ID id);
}
