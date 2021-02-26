package fi.abo.pvp20.grupp5.pointofsale.cashier_client.services;

import fi.abo.pvp20.grupp5.pointofsale.cashier_client.models.CashBoxStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The service taking care of the cash box
 */
@Service
public class CashBoxService {
    /**
     * Get the status of CashBox
     * @return Returns the cash box's current status, either OPEN or CLOSED
     */
    public CashBoxStatus getStatus() {
        final String localUrl = "http://localhost:9001/cashbox/status";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(localUrl, String.class);

        return CashBoxStatus.valueOf(result);
    }

    /**
     * Open CashBox if CashBox is closed. If CashBox is open, nothing happens.
     */
    public void openCashBox() {
        final String localUrl = "http://localhost:9001/cashbox/open";
        RestTemplate restTemplate = new RestTemplate();

        String request = "OPEN";

        restTemplate.postForObject(localUrl, request, String.class);
    }

}
