package com.desafio.lyncas.contas.api.annotations;

import com.desafio.lyncas.contas.domain.exception.ResponseError;
import com.desafio.lyncas.contas.domain.utils.ResponseSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = ResponseSuccess.class))}),
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ResponseError.class))}),
        @ApiResponse(responseCode = "404", description = "Not Found Exception",
                content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ResponseError.class))
                }),
        @ApiResponse(responseCode = "5xx", description = "General Exception",
                content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = ResponseError.class))
                })})

@Operation
public @interface SpringDocApiResponseUtil {

    @AliasFor(annotation = Operation.class, attribute = "summary")
    String summary() default "";
}
