package Practice2;

public abstract class Goods {
    protected final String productCode;
    protected final String name;
    protected int quantity;
    protected double unitPrice;

    protected Goods(String productCode, String name, int quantity, double unitPrice) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Product code must not be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price must be >= 0");
        }
        this.productCode = productCode;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public abstract String getTypeName();

    public abstract double getVatRate();

    public double getVatAmount() {
        return quantity * unitPrice * getVatRate();
    }

    // Requirement 2: per-type consumption evaluation
    public abstract String evaluateConsumption();

    protected abstract String getExtraInfo();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - qty: %d - price: %.0f - VAT: %.0f - %s - status: %s",
                productCode, name, getTypeName(), quantity, unitPrice, getVatAmount(),
                getExtraInfo(), evaluateConsumption());
    }
}
