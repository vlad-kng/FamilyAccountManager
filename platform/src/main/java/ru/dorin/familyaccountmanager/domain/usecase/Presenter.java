package ru.dorin.familyaccountmanager.domain.usecase;

public interface Presenter<S,F> {
    void presentSuccess(S success);
    void presentFailure(F failure);
}