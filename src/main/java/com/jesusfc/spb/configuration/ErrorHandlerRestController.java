package com.jesusfc.spb.configuration;

import com.jesusfc.spb.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * /**
 *
 * @author jesus.fdez.caraballo@gmail.com
 * Created on Nov 2024
 */
@RestControllerAdvice
@AllArgsConstructor
public class ErrorHandlerRestController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(BadRequestException ex) {
        ApiError details = new ApiError(LocalDateTime.now(ZoneOffset.UTC), ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    protected ResponseEntity<Object> handleForbidden(ForbiddenException ex) {
        ApiError details = new ApiError(LocalDateTime.now(ZoneOffset.UTC), ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex) {
        ApiError details = new ApiError(LocalDateTime.now(ZoneOffset.UTC), ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorized(UnauthorizedException ex) {
        ApiError details = new ApiError(LocalDateTime.now(ZoneOffset.UTC), ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }

}


