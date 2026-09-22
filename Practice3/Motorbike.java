package Practice3;

public class Motorbike extends Vehicle {
    private final double capacity; // engine displacement, in cc

    public Motorbike(String vehicleNumber, Manufacturer manufacturer, int manufactureYear, String color,
                      Owner owner, double capacity) {
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    @Override
    public String getVehicleTypeName() {
        return "Motorbike";
    }

    @Override
    protected String getExtraInfo() {
        return String.format("capacity: %.0fcc", capacity);
    }
}
