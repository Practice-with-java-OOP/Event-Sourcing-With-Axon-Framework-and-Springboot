package com.syphan.practice.eventsourcingaxonspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoneyCreditDTO {
    public double creditAmount;

    public String currency;
}
