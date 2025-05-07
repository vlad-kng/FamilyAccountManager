package ru.dorin.familyaccountmanager.application.usecase;


public interface UseCaseWithPresenter<I, P> {
    void execute(I input, P presenter);
}
