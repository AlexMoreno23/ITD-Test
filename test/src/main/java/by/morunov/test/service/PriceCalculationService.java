package by.morunov.test.service;

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
        if (allAlerts != null) {
            allAlerts.forEach(i -> {
                double difference = priceDifferenceFormula(i.getCryptoPrice(),
                        cryptoCurrencyRepo.findByCurrencyCode(i.getCryptoCode()).getCurrencyPrice());

                if (difference > 1 || difference < -1) {
                    String formattedDouble = new DecimalFormat("#0.00").format(difference);
                    log.warn(i.getUserName() + " price changed to " + formattedDouble + "%");

                }
            });
        } else {
            log.info("Nothing to notify");
        }
    }
}
