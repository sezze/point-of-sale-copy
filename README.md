# Point of sale

![CI Build](https://github.com/it-teaching-abo-akademi/project-pvp20-grupp-5-1/workflows/CI%20Build/badge.svg)

Documentation gets automatically built and published here:
[it-teaching-abo-akademi.github.io/project-pvp20-grupp-5-1](https://it-teaching-abo-akademi.github.io/project-pvp20-grupp-5-1/)

## How to run
The easiest way, if you use Intellij, is to run the configuration "Start everything". If not you can run the
ServerApplication, AdminClientApplication and CashierClientApplication separately.

```Bash
java -jar CashBox.jar
java -jar CardReader.jar
java -jar ProductCatalog.jar
java -jar CustomerRegister.jar
```

# User manual
This is the user manual.

## Cashier display
The cashier display is the display that is shown to the cashier. The cashier can manage different functions through the 
cashier display and see the status of the sale. Furthermore the cashier can add products to the sale by scanning the 
barcode or add them manually through the product catalog.

### Status
The status part, located on the left side, of the cashier display shows the status of the sale. Every kind of status 
will be explained one by one starting from the top row on the status bar.

Bonus card status: Shows if a bonus card has been swiped.  
Number of items: Shows how many items have been scanned.  
Remaining: Shows how much money remains to be paid.  
Total: Shows the totalt amount of the sale.  
Paid in cash: Shows how much money has been paid in cash.  
Return: Shows how much should be returned in cash payment.

### "Clear" button
The cashier can "clear" the sale with the "clear" button meaning that the cashier display resets.

### "Shelve" button
The cashier can "shelf" a sale so that the customer can go after money or something else that the customer has forgotten
while the cashier serves the following customer in line.

### "Add cash" button
If the customer pays with cash the cashier can add the amount of cash paid with the "add cash" button. The amount of 
cash paid is shown in the status bar.

### "Open Cash Box" button
The "open cash box" button opens the cash box.

NB! The cash box must be closed manually.

### "Read Card" button
The "Read Card" button transfers the remaining amount of payment to the card reader.

### "Abort" button
The cashier can abort the payment by clicking the "Abort" button.

### "Sale" window
Shows which items the sale consists of. The cashier can scan new items with the barcode scanner in the "sale" window.

### "Product Catalog" window
The cashier can search for product names and add them manually to the sale.

## Customer display
The customer display shows in real time what is happening in the sale. The display shows which item the cashier is 
currently scanning. The amount that should be paid is shown on the display, how much has been paid with cash and how 
much remains of the payment.

## Administrator display
The administrator display is shown to the administrator. It is possible to manage products and special offers as well as
finding bonus customers and looking through sales figures.

### "Products" window
The administrator can search for product names and change the price and VAT value.

### "Special offers" window
The administrator can create a new special offer by writing the name/ID of the product and deciding the amount of 
discount. It is also possible to put the start and end date for the special offer and if the special offer is available
for everybody or only bonus customers.

### "Sales figures" window
Allows the administrator to see the most and least sold products. It is possible to apply filters like for example time
period, age and sex for the sales figures.

### "Customers" window
Allows the administrator to find bonus customers.