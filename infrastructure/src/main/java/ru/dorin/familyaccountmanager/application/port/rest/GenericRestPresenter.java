package ru.dorin.familyaccountmanager.application.port.rest;

import lombok.Getter;
import lombok.Setter;
import ru.dorin.familyaccountmanager.application.port.ErrorResponse;

@Getter
@Setter
public class GenericRestPresenter<T> {
    private HttpResponse<T, ErrorResponse> response;
}
