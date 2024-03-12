package uz.kun.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiException {
    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
}
