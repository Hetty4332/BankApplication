package com.konnovaLA.mappers;

import com.konnovaLA.entities.CreditDto;
import com.konnovaLA.model.Bank;
import com.konnovaLA.model.Credit;
import com.konnovaLA.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditMapper {
    private final BankRepository bankRepository;

    public CreditDto toDto(Credit credit) {
        return CreditDto.builder()
                .id(credit.getId())
                .interestRate(credit.getInterestRate())
                .creditLimit(credit.getCreditLimit())
                .bankName(bankRepository.findBankByCreditsContains(credit)
                        .map(Bank::getName)
                        .orElse("банк не найден!"))
                .build();
    }

    public Credit fromDto(CreditDto dto) {
        return Credit.builder()
                .id(dto.getId())
                .creditLimit(dto.getCreditLimit())
                .interestRate(dto.getInterestRate())
                .build();
    }
}
