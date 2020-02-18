package com.syphan.practice.eventsourcingaxonspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDebitDTO {
    public double debitAmount;

    public String currency;
}
