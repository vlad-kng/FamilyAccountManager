package ru.dorin.familyaccountmanager.application.port.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@Getter
@Setter
public class RestResponse<OK, ERR> extends ResponseEntity<Object> {
    private final OK ok;
    private final ERR err;

    private RestResponse(OK ok, ERR err, HttpStatus status) {
        super(status);
        this.ok = ok;
        this.err = err;
    }

    public static <OK, ERR> RestResponse<OK, ERR> success(OK ok) {
        return new RestResponse<>(ok, null, HttpStatus.OK);
    }

    public static <OK, ERR> RestResponse<OK, ERR> error(ERR err) {
        return new RestResponse<>(null, err, HttpStatus.CONFLICT);
    }

    public boolean isOk() {
        return ok != null;
    }

    @Override
    public Object getBody() {
        return isOk() ? ok : err;
    }

    @Override
    public boolean hasBody() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RestResponse<?, ?> that = (RestResponse<?, ?>) o;
        return Objects.equals(this.getBody(), that.getBody())
                && Objects.equals(this.getHeaders(), that.getHeaders());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(ok);
        result = 31 * result + Objects.hashCode(err);
        return result;
    }
}
