package fi.abo.pvp20.grupp5.pointofsale.cashier_client.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.models.CardReaderResponse;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.models.PaymentStatus;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;

/**
 * The service taking care of the card reader
 */
@Service
public class CardReaderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${point-of-sale.card-reader.host}")
    private String host;

    /**
     * Send a payment to the card reader and await payment
     * @param amount The amount the card reader should request
     */
    public void waitForPayment(int amount) {
        String url = host + "/cardreader/waitForPayment";
        var request = new Request("amount", amount / 100.0);
        restTemplate.postForObject(url, request.httpEntity(), String.class);
    }

    /**
     * Get the card reader's current status
     * @return The status of the card reader
     */
    public PaymentStatus getStatus() {
        String url = host + "/cardreader/status";
        String status = restTemplate.getForObject(url, String.class);
        return PaymentStatus.valueOf(status);
    }

    /**
     * Get the result of the card reader transaction
     * @return The card reader's response to a transaction
     */
    public CardReaderResponse getResult() throws IOException {
        URL url = new URL(host + "/cardreader/result");
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(url, CardReaderResponse.class);
    }

    /**
     * Reset the card reader
     */
    public void resetCardReader() {
        String url = host + "/cardreader/reset";
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(url, "IDLE", String.class);
    }

    /**
     * Abort the card readers current transaction
     */
    public void abort() {
        String url = host + "/cardreader/abort";
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForObject(url, "IDLE", String.class);
    }
}
