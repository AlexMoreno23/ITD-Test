package by.morunov.test.repository;

import by.morunov.test.domain.entity.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Morunov
 */
@Repository
public interface CryptoCurrencyRepo extends JpaRepository<CryptoCurrency, Long> {

    CryptoCurrency findByCurrencyCode(String currencyCode);


}
