package uz.kun.domain.exception;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String message) {
        super(message);
    }
}
