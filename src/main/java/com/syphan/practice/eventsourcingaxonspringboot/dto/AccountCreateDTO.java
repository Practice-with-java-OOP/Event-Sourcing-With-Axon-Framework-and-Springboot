package com.syphan.practice.eventsourcingaxonspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDTO {
    public double startingBalance;

    public String currency;
}
