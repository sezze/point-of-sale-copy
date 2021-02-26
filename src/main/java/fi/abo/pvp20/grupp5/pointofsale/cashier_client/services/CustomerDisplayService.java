package fi.abo.pvp20.grupp5.pointofsale.cashier_client.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * The service taking care of the customer facing display
 */
@Service
public class CustomerDisplayService {
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * The CustomerDisplayService constructor
     * @param applicationEventPublisher
     */
    @Autowired
    public CustomerDisplayService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Display text on the customer facing display
     * @param text The text to display
     */
    public void displayText(String text) {
        DisplayTextChangedEvent displayTextChangedEvent = new DisplayTextChangedEvent(this, text);
        applicationEventPublisher.publishEvent(displayTextChangedEvent);
    }

    /**
     * Event that checks for changes in the customer facing display
     */
    public static class DisplayTextChangedEvent extends ApplicationEvent {
        private final String text;

        public DisplayTextChangedEvent(Object source, String text) {
            super(source);
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
