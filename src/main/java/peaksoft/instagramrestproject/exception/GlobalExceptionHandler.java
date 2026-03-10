package peaksoft.instagramrestproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NoSuchElementException ex){
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .messageClassName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badCredentialsException(BadCredentialsException ex){
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .messageClassName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse RuntimeException(RuntimeException ex){
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .messageClassName(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse validationException(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .messageClassName(ex.getClass().getSimpleName())
                .message(message)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse globalException(Exception ex){
        return ExceptionResponse.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .messageClassName(ex.getClass().getSimpleName())
                .message("Сервердик ката")
                .build();
    }






}

