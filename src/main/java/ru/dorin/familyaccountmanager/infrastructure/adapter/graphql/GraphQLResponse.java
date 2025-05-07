package ru.dorin.familyaccountmanager.infrastructure.adapter.graphql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.GraphQlResponse;
import org.springframework.graphql.ResponseError;
import org.springframework.graphql.ResponseField;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GraphQLResponse<OK, ERR> implements GraphQlResponse {
    private final OK ok;
    private final ERR error;

    public GraphQLResponse(OK ok, ERR error) {
        this.ok = ok;
        this.error = error;
    }

    public static <OK, ERR> GraphQLResponse<OK, ERR> success(OK ok) {
        return new GraphQLResponse<>(ok, null);
    }

    public static <OK, ERR> GraphQLResponse<OK, ERR> error(ERR err) {
        return new GraphQLResponse<>(null, err);
    }

    @Override
    public boolean isValid() {
        return isOk();
    }

    @Override
    public <T> T getData() {
        return isOk() ? (T) ok : (T) error;
    }

    @Override
    public List<ResponseError> getErrors() {
        return List.of();
    }

    @Override
    public ResponseField field(String path) {
        return null;
    }

    @Override
    public Map<Object, Object> getExtensions() {
        return Map.of();
    }

    @Override
    public Map<String, Object> toMap() {
        return Map.of("ok", ok, "error", error);
    }

    public boolean isOk() {
        return ok != null;
    }

}
