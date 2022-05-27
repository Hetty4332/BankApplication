package com.konnovaLA.service;

import com.konnovaLA.entities.request.CreditOfferRequest;
import com.konnovaLA.exeption.ApiException;
import com.konnovaLA.mappers.CreditOfferMapper;
import com.konnovaLA.model.*;
import com.konnovaLA.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CreditOfferService {

    private final ClientService clientService;

    private final CreditRepository creditRepository;

    private final CreditOfferRepository creditOfferRepository;

    private final PaymentRepository paymentRepository;

    private final CreditOfferMapper creditOfferMapper;

    public double deptPart(int creditTime, double sumCredit) {

        return sumCredit / creditTime;

    }

    public double percent(double remainder, double interestRate) {

        return remainder * (interestRate / 100.0 / 12.0);
    }

    public double amountOfMonthlyPayment(int creditTime, double remainder, int sumCredit, double interestRate) {
        return deptPart(creditTime, sumCredit) + percent(remainder, interestRate);
    }

    public List<Payment> getPayments(CreditOfferRequest creditOffer) throws ApiException {
        Credit credit = getCreditById(creditOffer.getCreditId());
        List<Payment> chartOfPayments = new ArrayList<>();
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

    public void saveCreditOffer(CreditOfferRequest creditOffer) throws ApiException {
        Credit credit = getCreditById(creditOffer.getCreditId());
        Client client = clientService.getClientById(creditOffer.getClientId()).orElseThrow(() -> new ApiException("Ошибка при поиске объекта"));
        List<Payment> chartOfPayments = new ArrayList<>(getPayments(creditOffer));
        CreditOffer saveCreditOffer = getSaveCreditOffer(creditOffer, credit, client, chartOfPayments);
        creditOfferRepository.save(saveCreditOffer);
    }

    private CreditOffer getSaveCreditOffer(CreditOfferRequest creditOffer, Credit credit, Client client, List<Payment> chartOfPayments) {
        CreditOffer saveCreditOffer = new CreditOffer();
        saveCreditOffer.setChartOfPayments(chartOfPayments);
        saveCreditOffer.setCredit(credit);
        saveCreditOffer.setClient(client);
        saveCreditOffer.setSumCredit(creditOffer.getSumCredit());
        return saveCreditOffer;
    }

    private Credit getCreditById(Long id) throws ApiException {
        return creditRepository.findById(id)
                .orElseThrow(() -> new ApiException("Ошибка при поиске объекта"));
    }

    public List<Payment> getPaymentsByCreditOfferId(Long id) {
        List<Payment> payments = new ArrayList<>();
        creditOfferRepository.findById(id).ifPresent(creditOffer ->
                payments.addAll(creditOffer.getChartOfPayments()));
        return payments;
    }

    public void deleteByUser(Long id) {
        creditOfferRepository.findByClient_Id(id)
                .ifPresent(creditOfferRepository::delete);
    }

    public void deleteByCreditId(Long id) {
        creditOfferRepository.findByCredit_Id(id)
                .ifPresent(creditOfferRepository::delete);
    }

    public String validate(CreditOfferRequest creditOffer) {
        Credit credit = creditRepository.getById(creditOffer.getCreditId());
        if (creditOffer.getSumCredit() > credit.getCreditLimit()) {
            return "требуемая сумма выше предлагаемого кредитного лимита";
        }
        return "";
    }

    public List<CreditOfferRequest> getCreditOffers() {
        List<CreditOfferRequest> creditOfferWebs = new ArrayList<>();
        List<CreditOffer> creditOffers = creditOfferRepository.findAll();
        for (CreditOffer value : creditOffers) {
            creditOfferWebs.add(creditOfferMapper.creditOfferToDto((value)));
        }
        return creditOfferWebs;
    }
}
