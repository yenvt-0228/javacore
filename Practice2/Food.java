package Practice2;

import java.time.LocalDate;

public class Food extends Goods {
    private static final double VAT_RATE = 0.05;

    private final LocalDate manufactureDate;
    private final LocalDate expiryDate;
    private final String supplier;

    public Food(String productCode, String name, int quantity, double unitPrice,
                LocalDate manufactureDate, LocalDate expiryDate, String supplier) {
        super(productCode, name, quantity, unitPrice);
        if (manufactureDate == null || expiryDate == null) {
            throw new IllegalArgumentException("Manufacture date and expiry date must not be empty");
        }
        if (expiryDate.isBefore(manufactureDate)) {
            throw new IllegalArgumentException("Expiry date must be on or after the manufacture date");
        }
        if (supplier == null || supplier.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier must not be empty");
        }
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
        this.supplier = supplier;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getSupplier() {
        return supplier;
    }

    @Override
    public String getTypeName() {
        return "Food";
    }

    @Override
    public double getVatRate() {
        return VAT_RATE;
    }

    @Override
    public String evaluateConsumption() {
        if (quantity > 0 && LocalDate.now().isAfter(expiryDate)) {
            return "Hard-to-sell";
        }
        return "Normal";
    }

    @Override
    protected String getExtraInfo() {
        return String.format("mfg: %s, exp: %s, supplier: %s", manufactureDate, expiryDate, supplier);
    }
}
