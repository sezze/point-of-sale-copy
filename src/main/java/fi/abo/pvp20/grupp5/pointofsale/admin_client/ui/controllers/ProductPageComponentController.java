package fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.Product;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.ProductService;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.components.AppStatus;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.CurrencyUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("product-page-component.fxml")
public class ProductPageComponentController {
    private final ProductService productService;
    @FXML
    private VBox productPage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label barcodeLabel;
    @FXML
    private Label offerLabel;
    @FXML
    private Label vatLabel;
    @FXML
    private Label keywordsLabel;
    @FXML
    private TextField priceField;
    @FXML
    private Button updatePriceButton;
    private Product product;

    /**
     * The ProductPageComponentController's constructor
     */
    @Autowired
    public ProductPageComponentController(ProductService productService) {
        this.productService = productService;
    }

    @FXML
    private void initialize() {
        productPage.setVisible(false);
        updatePriceButton.setOnAction(this::handleUpdatePrice);
        priceField.setOnAction(this::handleUpdatePrice);
        barcodeLabel.setOnMouseClicked(this::handleBarcodeClick);

    }

    private void handleUpdatePrice(Event event) {
        try {
            int newPrice = CurrencyUtils.Parse(priceField.getText());
            product.changeNetPrice(newPrice);
            productService.updatePrice(product);
        } catch (IllegalArgumentException e) {
            AppStatus.showError(nameLabel.getScene(), "Failed to update price!", true);
        }
    }

    private void update() {
        productPage.setVisible(true);
        nameLabel.setText(product.getProductName());
        barcodeLabel.setText("Barcode (click to copy): " + product.getBarcode());
        vatLabel.setText("VAT: " + product.getVatPercentage() + "%");
        keywordsLabel.setText("Keywords: " + String.join(", ", product.getKeywords()));
        priceField.setText(CurrencyUtils.toSimpleString(product.getNetPrice()));
        offerLabel.setText(product.getDiscountPercentage(null) == 0
                ? "No special offer"
                : "Special offer: -" + product.getDiscountPercentage(null) + "% off");
    }

    private void handleBarcodeClick(Event e) {
        var content = new ClipboardContent();
        var barcodeParts = barcodeLabel.getText().split(": ");
        content.putString(barcodeParts[barcodeParts.length - 1]);
        Clipboard.getSystemClipboard().setContent(content);
    }

    /**
     * Show the selected product in the GUI
     * @param product The product to show visually in the product page
     */
    public void setProduct(Product product) {
        this.product = product;
        update();
    }
}