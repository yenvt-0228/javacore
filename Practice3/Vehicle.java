package Practice3;

import java.time.Year;
import java.util.regex.Pattern;

public abstract class Vehicle {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[A-Za-z0-9]{5}$");

    private final String vehicleNumber;
    private final Manufacturer manufacturer;
    private final int manufactureYear;
    private final String color;
    private final Owner owner;

    protected Vehicle(String vehicleNumber, Manufacturer manufacturer, int manufactureYear,
                       String color, Owner owner) {
        if (!NUMBER_PATTERN.matcher(vehicleNumber).matches()) {
            throw new IllegalArgumentException("Vehicle number must be exactly 5 characters");
        }
        int currentYear = Year.now().getValue();
        if (manufactureYear <= 2000 || manufactureYear > currentYear) {
            throw new IllegalArgumentException(
                    "Manufacture year must be greater than 2000 and less than or equal to " + currentYear);
        }
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Color must not be empty");
        }
        this.vehicleNumber = vehicleNumber;
        this.manufacturer = manufacturer;
        this.manufactureYear = manufactureYear;
        this.color = color;
        this.owner = owner;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public String getColor() {
        return color;
    }

    public Owner getOwner() {
        return owner;
    }

    // Name of the concrete vehicle type, used for per-type statistics (Requirement 7)
    public abstract String getVehicleTypeName();

    // Extra fields specific to each subclass, appended to the common toString()
    protected abstract String getExtraInfo();

    @Override
    public String toString() {
        return String.format("[%s] %s (%s, %d, color: %s) - %s - Owner: %s",
                vehicleNumber, getVehicleTypeName(), manufacturer, manufactureYear, color,
                getExtraInfo(), owner);
    }
}
