package by.morunov.test.controller;

import by.morunov.test.domain.entity.CryptoCurrency;
import by.morunov.test.domain.entity.NotifyForUser;
import by.morunov.test.service.CryptoCurrencyAndNotifyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Alex Morunov
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CryptoCurrencyAndNotifyService cryptoCurrencyAndNotifyService;

    @GetMapping
    public ResponseEntity<List<CryptoCurrency>> getAllCrypto() {
        List<CryptoCurrency> allCrypto = cryptoCurrencyAndNotifyService.getCryptoCurrencies();
        return new ResponseEntity<>(allCrypto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NotifyForUser> addUser(@RequestParam(value = "username") String username,
                                                 @RequestParam(value = "code") String code ) {
        NotifyForUser notifyForUser = cryptoCurrencyAndNotifyService.createAndSaveNotify(username, code);
        return new ResponseEntity<>(notifyForUser, HttpStatus.CREATED);
    }
}
