package by.morunov.test.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Alex Morunov
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "crypto_currency")
public class CryptoCurrency {

    @Id
    private Long id;

    private String currencyCode;

    private String currencyName;

    private double currencyPrice;

    private String timeUpdatePrice;

    public CryptoCurrency(Long id, String currencyCode, String currencyName, double currencyPrice) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencyPrice = currencyPrice;
    }

    @PrePersist
    public void setDate() {
        timeUpdatePrice = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @PreUpdate
    public void setDateOnUpdate() {
        timeUpdatePrice = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
