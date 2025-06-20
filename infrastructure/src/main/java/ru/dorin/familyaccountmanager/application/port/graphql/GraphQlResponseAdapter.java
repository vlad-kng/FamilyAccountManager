package ru.dorin.familyaccountmanager.application.port.graphql;

import org.springframework.graphql.GraphQlResponse;
import org.springframework.graphql.ResponseError;
import org.springframework.graphql.ResponseField;
import ru.dorin.familyaccountmanager.application.port.rest.HttpResponse;

import java.util.List;
import java.util.Map;

public class GraphQlResponseAdapter<OK, ERR> implements GraphQlResponse {
    private final HttpResponse<OK, ERR> httpResponse;

    public GraphQlResponseAdapter(HttpResponse<OK, ERR> httpResponse) {
        this.httpResponse = httpResponse;
    }

    @Override
    public boolean isValid() {
        return httpResponse.isOk();
    }

    @Override
    public <T> T getData() {
        return (T) httpResponse.body();
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
        return Map.of("ok", httpResponse.ok(), "error", httpResponse.err());
    }
}
