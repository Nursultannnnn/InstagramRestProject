package peaksoft.instagramrestproject.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(
        HttpStatus httpStatus,
        String message,
        String exceptionName
) {
}