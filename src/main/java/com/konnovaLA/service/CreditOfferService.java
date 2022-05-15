package com.konnovaLA.service;

import com.konnovaLA.dto.CreditOfferWeb;
import com.konnovaLA.exeption.NoEntityException;
import com.konnovaLA.repository.ClientRepository;
import com.konnovaLA.repository.CreditOfferRepository;
import com.konnovaLA.repository.CreditRepository;
import com.konnovaLA.repository.PaymentRepository;
import com.konnovaLA.model.Client;
import com.konnovaLA.model.Credit;
import com.konnovaLA.model.CreditOffer;
import com.konnovaLA.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CreditOfferService {

    private ClientRepository clientRepository;

    private CreditRepository creditRepository;

    private CreditOfferRepository creditOfferRepository;

    private PaymentRepository paymentRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    public void setCreditRepository(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Autowired
    public void setCreditOfferRepository(CreditOfferRepository creditOfferRepository) {
        this.creditOfferRepository = creditOfferRepository;
    }

    @Autowired
    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public double deptPart(int creditTime, double sumCredit) {

        return sumCredit / creditTime;

    }

    public double percent(double remainder, double interestRate) {

        return remainder * (interestRate / 100.0 / 12.0);
    }

    public double amountOfMonthlyPayment(int creditTime, double remainder, int sumCredit, double interestRate) {
        return deptPart(creditTime, sumCredit) + percent(remainder, interestRate);
    }

    public List<Payment> getPayments(CreditOfferWeb creditOffer) throws NoEntityException {
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

    public CreditOffer saveCreditOffer(CreditOfferWeb creditOffer) throws NoEntityException {
        Credit credit = getCreditById(creditOffer.getCreditId());
        Optional<Client> clientOptional = clientRepository.findById(creditOffer.getClientId());
        Client client = clientOptional.orElseThrow(() -> new NoEntityException("client"));
        List<Payment> chartOfPayments = new ArrayList<>();
        chartOfPayments.addAll(getPayments(creditOffer));
        CreditOffer saveCreditOffer = new CreditOffer();
        saveCreditOffer.setChartOfPayments(chartOfPayments);
        saveCreditOffer.setCredit(credit);
        saveCreditOffer.setClient(client);
        saveCreditOffer.setSumCredit(creditOffer.getSumCredit());
        return creditOfferRepository.save(saveCreditOffer);
    }

    private Credit getCreditById(Long id) throws NoEntityException {
        return creditRepository.findById(id)
                .orElseThrow(() -> new NoEntityException("credit"));
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

    public void validate(CreditOfferWeb creditOffer, BindingResult bindingResult) {
        Credit credit = creditRepository.getById(creditOffer.getCreditId());
        if (creditOffer.getSumCredit() > credit.getCreditLimit()) {
            bindingResult.addError(new FieldError("creditOffer", "sumCredit", "требуемая сумма выше предлагаемого кредитного лимита"));
        }
    }

   /* public List<CreditOffer> getCreditOffers() {
        List<CreditOfferWeb> creditOfferWebs = new ArrayList<>();
        List<CreditOffer> creditOffers = creditOfferRepository.findAll();
        for (CreditOffer value : creditOffers) {
            CreditOfferWeb creditOfferWeb = new CreditOfferWeb();
            CreditOffer creditOffer = value;
            creditOfferWeb.setId(creditOffer.getId());
            creditOfferWeb.setSumCredit(creditOffer.getSumCredit());
            //TODO Нужно доделать
            String bankName = bankRepository.findBankByCreditsContains(credit)
                    .map(Bank::getName)
                    .orElse("банк не найден!");
            creditWeb.setBankName(bankName);
            creditWebs.add(creditWeb);
        }
        return creditWebs;
    }*/
}
