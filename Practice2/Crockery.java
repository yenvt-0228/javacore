package Practice2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Crockery extends Goods {
    private static final double VAT_RATE = 0.10;
    private static final int SLOW_SELLING_QUANTITY = 50;
    private static final int SLOW_SELLING_DAYS = 10;

    private final String manufacturerInfo;
    private final LocalDate arrivalDate;

    public Crockery(String productCode, String name, int quantity, double unitPrice,
                     String manufacturerInfo, LocalDate arrivalDate) {
        super(productCode, name, quantity, unitPrice);
        if (manufacturerInfo == null || manufacturerInfo.trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer info must not be empty");
        }
        if (arrivalDate == null) {
            throw new IllegalArgumentException("Arrival date must not be empty");
        }
        this.manufacturerInfo = manufacturerInfo;
        this.arrivalDate = arrivalDate;
    }

    public String getManufacturerInfo() {
        return manufacturerInfo;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String getTypeName() {
        return "Crockery";
    }

    @Override
    public double getVatRate() {
        return VAT_RATE;
    }

    @Override
    public String evaluateConsumption() {
        long daysInStorage = ChronoUnit.DAYS.between(arrivalDate, LocalDate.now());
        if (quantity > SLOW_SELLING_QUANTITY && daysInStorage > SLOW_SELLING_DAYS) {
            return "Slow-selling";
        }
        return "Normal";
    }

    @Override
    protected String getExtraInfo() {
        return String.format("manufacturer: %s, arrival: %s", manufacturerInfo, arrivalDate);
    }
}
