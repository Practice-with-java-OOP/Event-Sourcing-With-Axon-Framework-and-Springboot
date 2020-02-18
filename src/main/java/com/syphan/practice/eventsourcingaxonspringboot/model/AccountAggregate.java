package com.syphan.practice.eventsourcingaxonspringboot.model;

import com.syphan.practice.eventsourcingaxonspringboot.command.CreateAccountCommand;
import com.syphan.practice.eventsourcingaxonspringboot.command.CreditMoneyCommand;
import com.syphan.practice.eventsourcingaxonspringboot.command.DebitMoneyCommand;
import com.syphan.practice.eventsourcingaxonspringboot.event.AccountActivatedEvent;
import com.syphan.practice.eventsourcingaxonspringboot.event.AccountCreatedEvent;
import com.syphan.practice.eventsourcingaxonspringboot.event.AccountHeldEvent;
import com.syphan.practice.eventsourcingaxonspringboot.event.MoneyCreditedEvent;
import com.syphan.practice.eventsourcingaxonspringboot.event.MoneyDebitedEvent;
import com.syphan.practice.eventsourcingaxonspringboot.event.Status;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate //tells Axon that this entity will be managed by Axon, this is similar to the @Entity
public class AccountAggregate {

    @AggregateIdentifier
    //annotation is used for identifying a particular instance of the Aggregate. In other words, this is similar to JPA's @Id annotation.
    private String id;

    private double accountBalance;

    private String currency;

    private String status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.id,
                createAccountCommand.accountBalance,
                createAccountCommand.currency));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent accountCreatedEvent) {
        this.id = accountCreatedEvent.id;
        this.accountBalance = accountCreatedEvent.accountBalance;
        this.currency = accountCreatedEvent.currency;
        this.status = String.valueOf(Status.CREATED);

        AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvent accountActivatedEvent) {
        this.status = String.valueOf(accountActivatedEvent.status);
    }

    @CommandHandler
    protected void on(CreditMoneyCommand creditMoneyCommand) {
        AggregateLifecycle.apply(new MoneyCreditedEvent(creditMoneyCommand.id, creditMoneyCommand.creditAmount, creditMoneyCommand.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent moneyCreditedEvent) {

        if (this.accountBalance < 0 & (this.accountBalance + moneyCreditedEvent.creditAmount) >= 0) {
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, Status.ACTIVATED));
        }

        this.accountBalance += moneyCreditedEvent.creditAmount;
    }

    @CommandHandler
    protected void on(DebitMoneyCommand debitMoneyCommand){
        AggregateLifecycle.apply(new MoneyDebitedEvent(debitMoneyCommand.id, debitMoneyCommand.debitAmount, debitMoneyCommand.currency));
    }


    @EventSourcingHandler
    protected void on(MoneyDebitedEvent moneyDebitedEvent) {

        if (this.accountBalance >= 0 & (this.accountBalance - moneyDebitedEvent.debitAmount) < 0) {
            AggregateLifecycle.apply(new AccountHeldEvent(this.id, Status.HOLD));
        }

        this.accountBalance -= moneyDebitedEvent.debitAmount;
    }

    @EventSourcingHandler
    protected void on(AccountHeldEvent accountHeldEvent) {
        this.status = String.valueOf(accountHeldEvent.status);
    }
}
