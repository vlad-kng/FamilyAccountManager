package ru.dorin.familyaccountmanager.domain.usecase;


public interface UseCaseWithPresenter<I, P> {
    void execute(I input, P presenter);
}
