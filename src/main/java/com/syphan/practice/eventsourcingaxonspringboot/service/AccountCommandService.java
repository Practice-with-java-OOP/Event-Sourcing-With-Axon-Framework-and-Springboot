package com.syphan.practice.eventsourcingaxonspringboot.service;

import com.syphan.practice.eventsourcingaxonspringboot.dto.AccountCreateDTO;
import com.syphan.practice.eventsourcingaxonspringboot.dto.MoneyCreditDTO;
import com.syphan.practice.eventsourcingaxonspringboot.dto.MoneyDebitDTO;

import java.util.concurrent.CompletableFuture;

public interface AccountCommandService {

    CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO);

    CompletableFuture<String> creditMoneyToAccount(String accountNumber, MoneyCreditDTO moneyCreditDTO);

    CompletableFuture<String> debitMoneyFromAccount(String accountNumber, MoneyDebitDTO moneyDebitDTO);
}
