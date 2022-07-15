package by.morunov.test.domain.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author Alex Morunov
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "notify")
public class NotifyForUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    private String cryptoCode;

    private double cryptoPrice;

    public NotifyForUser(String userName, String cryptoCode, double cryptoPrice) {
        this.userName = userName;
        this.cryptoCode = cryptoCode;
        this.cryptoPrice = cryptoPrice;
    }
}
