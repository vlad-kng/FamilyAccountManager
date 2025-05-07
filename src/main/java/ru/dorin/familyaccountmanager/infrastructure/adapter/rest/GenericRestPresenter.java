package ru.dorin.familyaccountmanager.infrastructure.adapter.rest;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.infrastructure.adapter.graphql.ErrorResponse;

@Getter
@Setter
public class GenericRestPresenter<T> {
    private RestResponse<T, ErrorResponse> response;
}
