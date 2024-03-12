package uz.kun.domain.exception;

public class ItemAlreadyExistsException extends RuntimeException{
    public ItemAlreadyExistsException(String message) {
        super(message);
    }
}
