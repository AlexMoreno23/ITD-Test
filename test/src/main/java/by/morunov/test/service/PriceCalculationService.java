package by.morunov.test.service;

import by.morunov.test.domain.entity.CryptoCurrency;
import by.morunov.test.domain.entity.NotifyForUser;
import by.morunov.test.repository.CryptoCurrencyRepo;
import by.morunov.test.repository.NotifyForUserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Alex Morunov
 */
@Service
@AllArgsConstructor
@Slf4j
public class PriceCalculationService {

    private final CryptoCurrencyRepo cryptoCurrencyRepo;
    private final NotifyForUserRepo notifyForUserRepo;

    public double priceDifferenceFormula(double price1, double price2) {
        return 100 * (price2 - price1) / price1;
    }

    @Scheduled(fixedRate = 60000)
    public void calculationDifferencePrice() {
        List<NotifyForUser> allAlerts = notifyForUserRepo.findAll();
        List<CryptoCurrency> allCrypto = cryptoCurrencyRepo.findAll();

        if (allAlerts != null) {
            allAlerts.forEach(i -> {
                for (CryptoCurrency cryptoCurrency : allCrypto) {
                    if (cryptoCurrency.getCurrencyCode().equals(i.getCryptoCode())) {
                        double difference = priceDifferenceFormula(i.getCryptoPrice(),
                                cryptoCurrency.getCurrencyPrice());

                        if (difference > 1 || difference < -1) {

                            log.warn(i.getUserName() + " price changed to " +
                                    new DecimalFormat("#0.00").format(difference) + "%");

                        }
                    }
                }
            });


        } else {
            log.info("Nothing to notify");
        }
    }
}
