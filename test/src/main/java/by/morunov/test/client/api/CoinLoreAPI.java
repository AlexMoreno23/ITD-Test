package by.morunov.test.client.api;

import by.morunov.test.domain.dto.CryptoCurrencyDto;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Alex Morunov
 */
@Component
public class CoinLoreAPI {

    HttpClient httpClient = HttpClientBuilder.create().build();
    ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
    private RestTemplate restTemplate = new RestTemplate(requestFactory);
    List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    public List<CryptoCurrencyDto> getCryptos() {

        try {
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
            messageConverters.add(converter);
            restTemplate.setMessageConverters(messageConverters);
            final String GET_COINS = "https://api.coinlore.net/api/ticker/?id=90,80,48543";
            CryptoCurrencyDto[] cryptoCurrencyDtos =
                    restTemplate.getForObject(new URI(GET_COINS), CryptoCurrencyDto[].class);
            assert cryptoCurrencyDtos != null;
            return Arrays.asList(cryptoCurrencyDtos);

        } catch (URISyntaxException e) {
            throw new RuntimeException();
        }

    }
}
