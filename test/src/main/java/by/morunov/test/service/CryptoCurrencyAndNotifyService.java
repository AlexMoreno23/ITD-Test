package by.morunov.test.service;

import by.morunov.test.client.api.CoinLoreAPI;
import by.morunov.test.domain.dto.CryptoCurrencyDto;
import by.morunov.test.domain.entity.CryptoCurrency;
import by.morunov.test.domain.entity.NotifyForUser;
import by.morunov.test.repository.CryptoCurrencyRepo;
import by.morunov.test.repository.NotifyForUserRepo;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * @author Alex Morunov
 */
@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CryptoCurrencyAndNotifyService {

    private final CoinLoreAPI coinLoreAPI;
    private final CryptoCurrencyRepo cryptoCurrencyRepo;
    private final NotifyForUserRepo notifyForUserRepo;

    public CryptoCurrency converter(@NonNull CryptoCurrencyDto cryptoCurrencyDto) {
        return new CryptoCurrency(Long.parseLong(cryptoCurrencyDto.getId()), cryptoCurrencyDto.getSymbol(), cryptoCurrencyDto.getName(),
                Double.parseDouble(cryptoCurrencyDto.getPrice_usd()));
    }

    @Scheduled(fixedRate = 60000)
    public void saveCurrencyInDb() {
        cryptoCurrencyRepo.saveAll(coinLoreAPI.getCryptos().stream()
                .map(this::converter)
                .collect(collectingAndThen(toList(), ImmutableList::copyOf)));
    }

    public CryptoCurrency getCurrencyByCode(String code) {
        return cryptoCurrencyRepo.findByCurrencyCode(code);
    }

    public NotifyForUser createAndSaveNotify(String username, String currencyCode) {
        log.info("User: " + username + " is tracking " + currencyCode);
        return notifyForUserRepo.save(new NotifyForUser(username,
                getCurrencyByCode(currencyCode).getCurrencyCode(),
                getCurrencyByCode(currencyCode).getCurrencyPrice()));
    }


    public List<CryptoCurrency> getCryptoCurrencies() {
        return cryptoCurrencyRepo.findAll();
    }


}
