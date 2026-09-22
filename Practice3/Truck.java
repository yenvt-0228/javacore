package Practice3;

public class Truck extends Vehicle {
    private final double tonnage;

    public Truck(String vehicleNumber, Manufacturer manufacturer, int manufactureYear, String color,
                 Owner owner, double tonnage) {
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        if (tonnage <= 0) {
            throw new IllegalArgumentException("Tonnage must be greater than 0");
        }
        this.tonnage = tonnage;
    }

    public double getTonnage() {
        return tonnage;
    }

    @Override
    public String getVehicleTypeName() {
        return "Truck";
    }

    @Override
    protected String getExtraInfo() {
        return String.format("tonnage: %.1f tons", tonnage);
    }
}
