package fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.ProductSaleRecord;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.BonusCustomerService;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.SaleService;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.components.AppStatus;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.InputUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
@FxmlView("sales-figures-tab.fxml")
public class SalesFiguresTabController {
    private static final LocalDate MIN_DATE = LocalDate.of(2010, 1, 1);

    private final SaleService saleService;
    private final BonusCustomerService bonusCustomerService;

    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private TextField fromAgeField;
    @FXML
    private TextField toAgeField;
    @FXML
    private TextField barcodeField;
    @FXML
    private CheckBox filterByAge;
    @FXML
    private ChoiceBox<BonusCustomer.Sex> sexChoiceBox;
    @FXML
    private Button filterButton;
    @FXML
    private ListView<ProductSaleRecord> mostSoldProducts;
    @FXML
    private ListView<ProductSaleRecord> leastSoldProducts;

    public SalesFiguresTabController(SaleService saleService, BonusCustomerService bonusCustomerService) {
        this.saleService = saleService;
        this.bonusCustomerService = bonusCustomerService;
    }

    @FXML
    private void initialize() {
        fromDatePicker.setValue(LocalDate.now().minusMonths(1));
        toDatePicker.setValue(LocalDate.now());
        sexChoiceBox.getItems().setAll(BonusCustomer.Sex.values());
        sexChoiceBox.setValue(BonusCustomer.Sex.ANY);

        fromAgeField.setTextFormatter(new TextFormatter<>(InputUtils.INTEGER_INPUT_VALIDATOR));
        toAgeField.setTextFormatter(new TextFormatter<>(InputUtils.INTEGER_INPUT_VALIDATOR));
        fromAgeField.setText("1");
        toAgeField.setText("100");

        filterButton.setOnAction(this::handleFilter);
    }

    private void handleFilter(Event e) {
        mostSoldProducts.getItems().clear();
        leastSoldProducts.getItems().clear();

        var fromDate = ChronoZonedDateTime.from(fromDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()));
        var toDate = ChronoZonedDateTime.from(toDatePicker.getValue().plusDays(1).atStartOfDay(ZoneId.systemDefault()));
        var fromAge = Integer.parseInt(fromAgeField.getText());
        var toAge = Integer.parseInt(toAgeField.getText());
        var barcode = barcodeField.getText();
        var checkAge = filterByAge.isSelected();
        var sex = sexChoiceBox.getValue();

        // Get all sales matching filter
        var items = saleService.getSaleItems(fromDate, toDate, checkAge, fromAge, toAge, sex);
        items.whenComplete((map, throwable) -> {
            Platform.runLater(() -> {
                if (map == null || throwable != null) {
                    AppStatus.showError(filterButton.getScene(), "Failed to get filtered items", true);
                } else {
                    if (map.size() == 0) return;
                    if (barcode.length() > 0) {
                        // If user is filtering by barcode, only show relevant product
                        var record = map.get(barcode);
                        if (record != null)
                            mostSoldProducts.getItems().setAll(record);
                    } else {
                        // Get best and worst sold products
                        var records = map.values().stream()
                                .sorted(Comparator.comparingInt(ProductSaleRecord::getCount))
                                .collect(Collectors.toList());
                        var worst = records.subList(0, Math.min(10, records.size()));
                        var best = records.subList(Math.max(0, records.size() - 10), records.size());

                        var bestOrdered = new ArrayList<>(best);
                        Collections.reverse(bestOrdered);

                        mostSoldProducts.getItems().setAll(bestOrdered);
                        leastSoldProducts.getItems().setAll(worst);
                    }
                }
            });
        });
    }

    /**
     * This date <-> string converter uses the Finnish standard date format (dd.MM.yyyy)
     */
    private static final StringConverter<LocalDate> DATE_STRING_CONVERTER = new StringConverter<LocalDate>() {
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        @Override
        public String toString(LocalDate localDate) {
            return localDate != null ? formatter.format(localDate) : "";
        }

        @Override
        public LocalDate fromString(String s) {
            return s != null && !s.isEmpty() ? LocalDate.parse(s, formatter) : null;
        }
    };
}
