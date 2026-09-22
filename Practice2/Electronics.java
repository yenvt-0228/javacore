package Practice2;

public class Electronics extends Goods {
    private static final double VAT_RATE = 0.10;
    private static final int WELL_SOLD_THRESHOLD = 3;

    private final int warrantyMonths;
    private final double capacityKW;

    public Electronics(String productCode, String name, int quantity, double unitPrice,
                        int warrantyMonths, double capacityKW) {
        super(productCode, name, quantity, unitPrice);
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty period must be >= 0");
        }
        if (capacityKW < 0) {
            throw new IllegalArgumentException("Capacity must be >= 0");
        }
        this.warrantyMonths = warrantyMonths;
        this.capacityKW = capacityKW;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public double getCapacityKW() {
        return capacityKW;
    }

    @Override
    public String getTypeName() {
        return "Electronics";
    }

    @Override
    public double getVatRate() {
        return VAT_RATE;
    }

    @Override
    public String evaluateConsumption() {
        if (quantity < WELL_SOLD_THRESHOLD) {
            return "Well-sold";
        }
        return "Normal";
    }

    @Override
    protected String getExtraInfo() {
        return String.format("warranty: %dm, capacity: %.1fkW", warrantyMonths, capacityKW);
    }
}
