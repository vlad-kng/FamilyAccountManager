package ru.dorin.familyaccountmanager.platform.domain.usecase;


public interface UseCaseWithPresenter<I, P> {
    void execute(I input, P presenter);
}
