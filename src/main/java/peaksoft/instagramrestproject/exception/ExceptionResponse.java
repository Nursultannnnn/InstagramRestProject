package peaksoft.instagramrestproject.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String messageClassName;
    private String message;
}