package Practice3;

public class Car extends Vehicle {
    private final int numberOfSeats;
    private final String engineType;

    public Car(String vehicleNumber, Manufacturer manufacturer, int manufactureYear, String color,
               Owner owner, int numberOfSeats, String engineType) {
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats must be greater than 0");
        }
        if (engineType == null || engineType.trim().isEmpty()) {
            throw new IllegalArgumentException("Engine type must not be empty");
        }
        this.numberOfSeats = numberOfSeats;
        this.engineType = engineType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getEngineType() {
        return engineType;
    }

    @Override
    public String getVehicleTypeName() {
        return "Car";
    }

    @Override
    protected String getExtraInfo() {
        return String.format("seats: %d, engine: %s", numberOfSeats, engineType);
    }
}
