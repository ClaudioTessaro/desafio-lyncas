package com.desafio.lyncas.contas.config.exception_handler;

import com.desafio.lyncas.contas.domain.exception.BusinessException;
import com.desafio.lyncas.contas.domain.exception.GeneralException;
import com.desafio.lyncas.contas.domain.exception.NotFoundException;
import com.desafio.lyncas.contas.domain.exception.ResponseError;
import com.desafio.lyncas.contas.domain.exception.RestTemplateException;
import com.desafio.lyncas.contas.domain.exception.UnauthorizedException;
import com.desafio.lyncas.contas.domain.exception.ValidationResponseError;
import com.desafio.lyncas.contas.domain.utils.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageUtil messageUtil;

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ResponseError> validation(IllegalArgumentException e) {
        String message = messageUtil.getMessage(e.getMessage());
        ResponseError err = new ResponseError(message, OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value());
        log.error("Invalid arg: {}", err);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ResponseError> janaException(BusinessException e) {
        String message = messageUtil.getMessage(e.getMessage());
        ResponseError err = new ResponseError(message, OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value());
        log.error("Business error: {}", err);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<ResponseError> generalException(GeneralException e) {
        String message = messageUtil.getMessage(e.getMessage());
        ResponseError err = new ResponseError(message, OffsetDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error("General error: {}", err);
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ResponseError> unauthorizedException(UnauthorizedException e) {
        String message = messageUtil.getMessage(e.getMessage());
        ResponseError err = new ResponseError(message, OffsetDateTime.now(), HttpStatus.UNAUTHORIZED.value());
        log.error("Unauthorized error: {}", err);
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseError> notFoundException(NotFoundException e) {
        String message = messageUtil.getMessage(e.getMessage());
        ResponseError err = new ResponseError(message, OffsetDateTime.now(), HttpStatus.NOT_FOUND.value());
        log.error("Not found error: {}", err);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RestTemplateException.class)
    public ResponseEntity<ResponseError> handleNegocioException(RestTemplateException e)  {
        ResponseError err = new ResponseError(e.getMessage(), OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value());
        log.error("Rest template error: {}", err);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponseError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<String> msgErroValidation = new ArrayList<>();
        for (ObjectError x : e.getBindingResult().getAllErrors()) {
            msgErroValidation.add(x.getDefaultMessage());
        }
        ValidationResponseError err = new ValidationResponseError(msgErroValidation, OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value());
        log.error("Validation error: {}", err);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}