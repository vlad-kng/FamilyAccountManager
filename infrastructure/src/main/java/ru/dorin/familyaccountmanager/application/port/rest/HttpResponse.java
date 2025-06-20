package ru.dorin.familyaccountmanager.application.port.rest;

import org.springframework.graphql.GraphQlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.dorin.familyaccountmanager.application.port.graphql.GraphQlResponseAdapter;

public record HttpResponse<OK, ERR>(
        OK ok,
        ERR err,
        HttpStatus status
) {

    public static <OK, ERR> HttpResponse<OK, ERR> success(OK ok) {
        return new HttpResponse<>(ok, null, HttpStatus.OK);
    }

    public static <OK, ERR> HttpResponse<OK, ERR> error(ERR err) {
        return new HttpResponse<>(null, err, HttpStatus.CONFLICT);
    }

    public boolean isOk() {
        return ok != null;
    }

    public Object body() {
        return isOk() ? ok : err;
    }

    public ResponseEntity<Object> toResponseEntity(HttpStatus status) {
        return ResponseEntity.status(status).body(body());
    }

    public ResponseEntity<Object> toOkResponse() {
        return toResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<Object> toErrorResponse() {
        return toResponseEntity(HttpStatus.CONFLICT);
    }

    public GraphQlResponse toGraphQlResponse() {
        return new GraphQlResponseAdapter<>(this);
    }
}