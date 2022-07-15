package by.morunov.test.domain.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author Alex Morunov
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CryptoCurrencyDto implements Serializable {

    private String id;
    private String symbol;
    private String name;
    private String price_usd;


}
