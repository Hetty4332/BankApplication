package com.haulmont.testtask.service;

import com.haulmont.testtask.dto.CreditOfferRequest;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Credit;
import com.haulmont.testtask.model.CreditOffer;
import com.haulmont.testtask.model.Payment;
import com.haulmont.testtask.repository.ClientRepository;
import com.haulmont.testtask.repository.CreditOfferRepository;
import com.haulmont.testtask.repository.CreditRepository;
import com.haulmont.testtask.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CreditOfferService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private CreditOfferRepository creditOfferRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    // долговая часть ежемесячного платежа
    public double deptPart(int creditTime, double sumCredit) {

        return sumCredit / creditTime;

    }

    //проценты в ежемесячном платеже
    public double percent(double remainder, double interestRate) {

        return remainder * (interestRate / 100.0 / 12.0);
    }

    //сумма ежемесячного платежа
    public double amountOfMonthlyPayment(int creditTime, double remainder, int sumCredit, double interestRate) {
        return deptPart(creditTime, sumCredit) + percent(remainder, interestRate);
    }

    public List<Payment> getPayments(CreditOfferRequest creditOffer) {
        Credit credit = getCreditById(creditOffer.getCreditId());
        List<Payment> chartOfPayments = new ArrayList();
        int time = creditOffer.getCountMonthCredit();
        double remainder = creditOffer.getSumCredit();
        for (int i = 0; i < time; i++) {
            if (i > 0) {
                remainder = creditOffer.getSumCredit() - chartOfPayments.get(i - 1).getPaymentSum();
            }
            Payment payment = new Payment();
            payment.setPaymentDate(LocalDate.now().plusMonths(i));
            payment.setPaymentSum(amountOfMonthlyPayment(time, remainder, creditOffer.getSumCredit(), credit.getInterestRate()));
            payment.setCreditBodyRepayment(deptPart(time, creditOffer.getSumCredit()));
            payment.setAmountOfInterestRepayment(percent(remainder, credit.getInterestRate()));
            chartOfPayments.add(payment);
            paymentRepository.save(payment);
        }
        return chartOfPayments;
    }

    public CreditOffer saveCreditOffer(CreditOfferRequest creditOffer) {
        Credit credit = getCreditById(creditOffer.getCreditId());
        Optional<Client> clientOptional = clientRepository.findById(creditOffer.getClientId());
        Client client = clientOptional.get();
        List<Payment> chartOfPayments = new ArrayList();
        chartOfPayments.addAll(getPayments(creditOffer));
        CreditOffer saveCreditOffer = new CreditOffer();
        saveCreditOffer.setChartOfPayments(chartOfPayments);
        saveCreditOffer.setCredit(credit);
        saveCreditOffer.setClient(client);
        saveCreditOffer.setSumCredit(creditOffer.getSumCredit());
        return creditOfferRepository.save(saveCreditOffer);
    }

    private Credit getCreditById(Long id) {
        Optional<Credit> creditOptional = creditRepository.findById(id);
        return creditOptional.get();
    }

    public List<Payment> getPaymentsByCreditOfferId(Long id) {
        return creditOfferRepository.findById(id).get().getChartOfPayments();
    }

    public void deleteByUser(Long id) {
        creditOfferRepository.findByClient_Id(id)
                .ifPresent(creditOfferRepository::delete);
    }

    public void deleteByCreditId(Long id) {
        creditOfferRepository.findByCredit_Id(id)
                .ifPresent(creditOfferRepository::delete);
    }
}
