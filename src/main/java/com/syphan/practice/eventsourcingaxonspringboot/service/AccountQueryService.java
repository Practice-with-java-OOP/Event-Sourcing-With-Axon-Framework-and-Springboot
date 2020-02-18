package com.syphan.practice.eventsourcingaxonspringboot.service;

import java.util.List;

public interface AccountQueryService {
    List<Object> listEventsForAccount(String accountNumber);
}
