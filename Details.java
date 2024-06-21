/* Name: Miguel Sanchez
 * Course: CNT 4714 - Spring 2024 
 * Assignment Title: Project 1 - An Event-driven Enterprise Simulation
 * Date: Tuesday, January 30, 2024
 */

package eventdriven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Details extends JFrame {
    private JTextField inputField, quantityField;
    private JTextArea cartArea;
    private JButton findItemButton, addItemButton, checkoutButton, viewCartButton, emptyCartButton, exitButton;
    private JLabel titleLabel, itemDetailsLabel, subtotalLabel;
    private List<Item> itemList;
    private List<Item> cartItems;
    private double subtotal;

    public Details() {
        super("Nile.Com");

        // Initialize lists
        itemList = new ArrayList<>();
        cartItems = new ArrayList<>();
        subtotal = 0.0;

        // Load items from CSV
        loadItemsFromCSV();

     // Create components
        inputField = new JTextField(20);
        quantityField = new JTextField(20);
        cartArea = new JTextArea(10, 20);
        findItemButton = new JButton("Find Item");
        addItemButton = new JButton("Add Item to Cart");
        checkoutButton = new JButton("Checkout");
        viewCartButton = new JButton("View Cart");
        emptyCartButton = new JButton("Empty Cart - Start A New Order");
        exitButton = new JButton("Exit");
        titleLabel = new JLabel("Your current shopping cart with 0 items");
        itemDetailsLabel = new JLabel();
        subtotalLabel = new JLabel("Current Subtotal for 0 item(s): $0.00");

        // Set layout
        setLayout(new BorderLayout());

        // Add components to the frame
        JPanel inputPanel = new JPanel();
     // Modify the layout of the input panel
        inputPanel.setLayout(new GridLayout(5, 2));

        // Add "Enter item ID" components
        inputPanel.add(new JLabel("<html><b>Enter item ID:</b></html>"));
        inputPanel.add(inputField);

        // Add "Enter quantity" components
        inputPanel.add(new JLabel("<html><b>Enter quantity:</b></html>"));
        inputPanel.add(quantityField);

        // Add remaining buttons
        inputPanel.add(findItemButton);
        inputPanel.add(addItemButton);
        inputPanel.add(viewCartButton);
        inputPanel.add(checkoutButton);
        inputPanel.add(emptyCartButton);
        inputPanel.add(exitButton);
        inputPanel.setBackground(Color.GREEN);

        // Add item details label and subtotal label to north panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(itemDetailsLabel, BorderLayout.CENTER); // Place item details label here
        northPanel.add(subtotalLabel, BorderLayout.SOUTH);

        // Add components to the frame
        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(cartArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
     // Modify the layout of the input panel
        inputPanel.setLayout(new GridLayout(5, 2, 5, 5)); // Added spacing between components

        // Set background colors for buttons
        findItemButton.setBackground(Color.LIGHT_GRAY);
        addItemButton.setBackground(Color.LIGHT_GRAY);
        viewCartButton.setBackground(Color.LIGHT_GRAY);
        checkoutButton.setBackground(Color.LIGHT_GRAY);
        emptyCartButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setBackground(Color.LIGHT_GRAY);

        // Set font for labels
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        itemDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Add padding to the cart area
        cartArea.setMargin(new Insets(5, 5, 5, 5));

        // Set font and alignment for text fields
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        quantityField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        quantityField.setHorizontalAlignment(JTextField.CENTER);

        // Add borders to the input fields
        inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        quantityField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Set font and alignment for buttons
        findItemButton.setFont(new Font("Arial", Font.BOLD, 14));
        addItemButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewCartButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        emptyCartButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Set alignment for buttons
        findItemButton.setHorizontalAlignment(SwingConstants.CENTER);
        addItemButton.setHorizontalAlignment(SwingConstants.CENTER);
        viewCartButton.setHorizontalAlignment(SwingConstants.CENTER);
        checkoutButton.setHorizontalAlignment(SwingConstants.CENTER);
        emptyCartButton.setHorizontalAlignment(SwingConstants.CENTER);
        exitButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        addItemButton.setEnabled(false);
        viewCartButton.setEnabled(false);
        checkoutButton.setEnabled(false);

        findItemButton.addActionListener(new FindItemListener());
        addItemButton.addActionListener(new AddItemListener());
        viewCartButton.addActionListener(new ViewCartListener());
        checkoutButton.addActionListener(new CheckoutListener());
        emptyCartButton.addActionListener(new EmptyCartListener());
        exitButton.addActionListener(new ExitListener());
    }
    
 // Action listener classes for buttons
    private class FindItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Action performed when the findItemButton is clicked
            String searchItemId = inputField.getText();
            findItem();
        }
    }

    private class AddItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Action performed when the addItemButton is clicked
            String itemId = inputField.getText();
            addItemToCart();

        }
    }

    private class ViewCartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Action performed when the viewCartButton is clicked
            viewCart();
        }
    }

    private class CheckoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Action performed when the checkoutButton is clicked
            checkout();
        }
    }
    
    
    private class EmptyCartListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		emptyCart();
    	}
    }
    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Action performed when the exitButton is clicked
            System.exit(0); // Exit the application
        }
    }

    
    private void loadItemsFromCSV() {
        String csvFile = "/Users/miguelsanchez/Downloads/inventoryy.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String itemId = data[0].trim();
                String itemName = data[1].trim();
                boolean inStock = Boolean.parseBoolean(data[2].trim());
                int quantity = Integer.parseInt(data[3].trim());
                double price = Double.parseDouble(data[4].trim());
                itemList.add(new Item(itemId, itemName, inStock, quantity, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void emptyCart() {
        cartItems.clear();
        subtotal = 0.0;
        subtotalLabel.setText("<html><b>Current Subtotal for 0 item(s): $0.00</html></b>");
        titleLabel.setText("Your current shopping cart with 0 item(s):");
        cartArea.setText("Your cart is empty.");
        itemDetailsLabel.setText(" ");
        
        findItemButton.setEnabled(true);
        addItemButton.setEnabled(false);
        checkoutButton.setEnabled(false);
        viewCartButton.setEnabled(false);
    }
    
    private double calculateDiscount(int quantity) {
        double discount = 0.0;
        if (quantity >= 5 && quantity <= 9) {
            discount = 0.10;
        } else if (quantity >= 10 && quantity <= 14) {
            discount = 0.15;
        } else if (quantity >= 15) {
            discount = 0.20;
        }
        return discount;
    }
    private void findItem() {
        String searchItemId = inputField.getText();
        boolean found = false;
        for (Item item : itemList) {
            if (item.getItemId().equals(searchItemId)) {
                found = true;
                if (!item.isInStock()) {
                    JOptionPane.showMessageDialog(this, "Sorry, the item is out of stock. Please try another item.", "Out of Stock", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Found the item, display its details
                	itemDetailsLabel.setText("<html><b>Item Details:</b><br>" +
                            "Item ID: " + item.getItemId() + "<br>" +
                            "Name: " + item.getItemName() + "<br>" +
                            "Price: $" + item.getItemPrice() + "<br>" +
                            "Stock: " + item.getQuantity() + "</html>");
                    addItemButton.setEnabled(true);
                	
                }
                break;
            }
        }
        // Item not found
        if (!found) {
            JOptionPane.showMessageDialog(this, "Item ID " + searchItemId + " not found in file.", "Item Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void viewCart() {
        StringBuilder cartContent = new StringBuilder();
        double cartTotal = 0.0;
        for (Item item : cartItems) {
            double itemTotal = item.getItemPrice() * item.getQuantity();
            cartContent.append(item.getItemName()).append(" - ").append(item.getQuantity()).append(" units - $").append(itemTotal).append("\n");
            cartTotal += itemTotal;
        }
        cartContent.append("Cart Total: $").append(cartTotal).append("\n");
        if (cartItems.isEmpty()) {
            cartContent.append("Your cart is empty.");
        }
        cartArea.setText(cartContent.toString());
        JOptionPane.showMessageDialog(this, cartContent.toString(), "View Cart", JOptionPane.INFORMATION_MESSAGE);
    }


    private void addItemToCart() {
        String itemId = inputField.getText();
        String quantityText = quantityField.getText();
        int desiredQuantity = Integer.parseInt(quantityText);
        boolean found = false;

        for (Item item : itemList) {
            if (item.getItemId().equals(itemId)) {
                if (item.isInStock()) {
                    if (desiredQuantity > item.getQuantity()) {
                        JOptionPane.showMessageDialog(this, "Sorry, the requested quantity exceeds available stock.", "Insufficient Stock", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double discount = calculateDiscount(desiredQuantity);
                    double itemTotal = item.getItemPrice() * desiredQuantity * (1 - discount);

                    // Check if the item is already in the cart
                    for (Item cartItem : cartItems) {
                        if (cartItem.getItemId().equals(itemId)) {
                            // Update the quantity and total for the item in the cart
                            cartItem.setQuantity(cartItem.getQuantity() + desiredQuantity);
                            subtotal += itemTotal; // Update subtotal
                            subtotalLabel.setText("Current Subtotal for " + cartItems.size() + " item(s): $" + subtotal);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        // If the item is not in the cart, add it to the cart
                        cartItems.add(new Item(item.getItemId(), item.getItemName(), item.isInStock(), desiredQuantity, item.getItemPrice()));
                        subtotal += itemTotal; // Update subtotal
                        subtotalLabel.setText("Current Subtotal for " + cartItems.size() + " item(s): $" + subtotal);
                    }

                    if (cartItems.size() == 1) {
                        titleLabel.setText("Your current shopping cart with 1 item");
                    } else {
                        titleLabel.setText("Your current shopping cart with " + cartItems.size() + " items");
                    }

                    if (cartItems.size() >= 5) {
                        findItemButton.setEnabled(false);
                        addItemButton.setEnabled(false);
                    }
                    viewCartButton.setEnabled(true);
                    checkoutButton.setEnabled(true);
                    found = true;
                    break;
                } else {
                    // Item is not in stock
                    JOptionPane.showMessageDialog(this, "Sorry, item " + item.getItemName() + " is out of stock.", "Out of Stock", JOptionPane.ERROR_MESSAGE);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            // Item not found
            JOptionPane.showMessageDialog(this, "Item not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 


    private void checkout() {
        // Generate invoice
        generateInvoice();

        // Save transaction to CSV
        saveTransactionToCSV();
        
        viewCartButton.setEnabled(false);
        addItemButton.setEnabled(false);
        checkoutButton.setEnabled(false);
    }

    private void generateInvoice() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss z");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);

        double taxRate = 0.06; // Assuming a 6% tax rate
        double taxAmount = 0.0; // Initialize tax amount
        double subtotalAfterDiscount = 0.0; // Initialize subtotal after discount

        StringBuilder invoiceText = new StringBuilder();
        invoiceText.append("Invoice Date: ").append(formattedDate).append("\n");
        invoiceText.append("Description of Items:\n");

        // Calculate discount and subtotal for each item
        for (Item item : cartItems) {
            double discount = 0.0;
            int quantity = item.getQuantity();
            double itemTotal = item.getItemPrice() * quantity;

            // Apply discount based on the quantity of the item
            if (quantity >= 5 && quantity <= 9) {
                discount = 0.10;
            } else if (quantity >= 10 && quantity <= 14) {
                discount = 0.15;
            } else if (quantity >= 15) {
                discount = 0.20;
            }

            double discountedItemTotal = itemTotal * (1 - discount);
            subtotalAfterDiscount += discountedItemTotal;

            // Append item details to the invoice
            invoiceText.append("- ").append(item.getItemName()).append(": ").append(quantity).append(" units - $").append(item.getItemPrice()).append(" each").append("\n");
            invoiceText.append("   Discount: ").append(discount * 100).append("%\n");
            invoiceText.append("   Total after Discount: $").append(discountedItemTotal).append("\n");
        }

        // Calculate tax amount
        taxAmount = subtotalAfterDiscount * taxRate;

        double orderTotal = subtotalAfterDiscount + taxAmount;
        invoiceText.append("Subtotal: $").append(subtotalAfterDiscount).append("\n");
        invoiceText.append("Tax Rate: ").append(taxRate * 100).append("%\n");
        invoiceText.append("Tax Amount: $").append(taxAmount).append("\n");
        invoiceText.append("Order Total: $").append(orderTotal).append("\n");

        JOptionPane.showMessageDialog(this, invoiceText.toString(), "Invoice", JOptionPane.INFORMATION_MESSAGE);
    }



    private void saveTransactionToCSV() {
        String csvFile = "transactionFile.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy/ hh:mm:ss a z");
            Date date = new Date();
            String formattedDate = dateFormat.format(date);

            writer.write("Transaction ID,Item ID,Item Name,Price,Stock ordered,Discount applied,Total after discount,Date\n");
            for (Item item : cartItems) {
                double discount = 0.0;
                int quantity = item.getQuantity();
                double itemTotal = item.getItemPrice() * quantity;

                // Apply discount based on the quantity of the item
                if (quantity >= 5 && quantity <= 9) {
                    discount = 0.10;
                } else if (quantity >= 10 && quantity <= 14) {
                    discount = 0.15;
                } else if (quantity >= 15) {
                    discount = 0.20;
                }

                double discountedItemTotal = itemTotal * (1 - discount);

                // Write transaction details to the CSV file
                writer.write(generateTransactionID() + "," + item.getItemId() + "," + item.getItemName() + "," +
                             item.getItemPrice() + "," + quantity + "," + discount * 100 + "%," +
                             discountedItemTotal + "," + formattedDate + "\n");
            }
            writer.write("Subtotal:," + subtotal + "\n");
            writer.write("----------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generate a unique transaction ID based on the current timestamp
    private String generateTransactionID() {
        return Long.toString(System.currentTimeMillis());
    }


    private static class Item {
        private String itemId;
        private String itemName;
        private boolean inStock;
        private int quantity;
        private double itemPrice;

        public Item(String itemId, String itemName, boolean inStock, int quantity, double itemPrice) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.inStock = inStock;
            this.quantity = quantity;
            this.itemPrice = itemPrice;
        }

        public String getItemId() {
            return itemId;
        }

        public String getItemName() {
            return itemName;
        }
        
        public boolean isInStock() {
        	return inStock;
        }

        public double getItemPrice() {
            return itemPrice;
        }
        public int getQuantity() {
        	return quantity;
        }
        
        public void setQuantity(int quantity) {
        	this.quantity = quantity;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Details::new);
    }
}
