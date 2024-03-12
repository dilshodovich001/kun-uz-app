package uz.kun.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({ItemAlreadyExistsException.class})
    public ResponseEntity<Object> handle(ItemAlreadyExistsException e){

        HttpStatus found = HttpStatus.FOUND;

        var apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(found)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiException, found);
    }

    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<Object> handle(ItemNotFoundException e){

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        var apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(notFound)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler({AttachException.class})
    public ResponseEntity<Object> handle(AttachException e){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        var apiException = ApiException.builder()
                .message(e.getMessage())
                .httpStatus(badRequest)
                .timestamp(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiException, badRequest);
    }
}
