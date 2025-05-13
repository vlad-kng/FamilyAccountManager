package ru.dorin.familyaccountmanager.domain.aggregate;

public enum TransactionType {
    ACCOUNT_INITIAL_DEPOSIT,
    DEPOSITED,
    WITHDRAWAL,
    TRANSFER,
    RECEIVED_TRANSFER
}
