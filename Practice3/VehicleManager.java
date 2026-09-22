package Practice3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VehicleManager {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final Map<String, Owner> ownersByIdCard = new HashMap<>();

    // Reuses the owner record if the ID card is already registered,
    // otherwise validates and registers a new owner.
    public Owner getOrRegisterOwner(String idCard, String fullName, String email) {
        Owner existing = ownersByIdCard.get(idCard);
        if (existing != null) {
            return existing;
        }
        Owner owner = new Owner(idCard, fullName, email);
        ownersByIdCard.put(idCard, owner);
        return owner;
    }

    public boolean isVehicleNumberTaken(String vehicleNumber) {
        return vehicles.stream().anyMatch(v -> v.getVehicleNumber().equalsIgnoreCase(vehicleNumber));
    }

    // 1. Add a new vehicle
    public void addVehicle(Vehicle vehicle) {
        if (isVehicleNumberTaken(vehicle.getVehicleNumber())) {
            throw new IllegalArgumentException("Vehicle number already exists: " + vehicle.getVehicleNumber());
        }
        vehicles.add(vehicle);
    }

    // 2. Search for a vehicle by vehicle number
    public Vehicle findByVehicleNumber(String vehicleNumber) {
        return vehicles.stream()
                .filter(v -> v.getVehicleNumber().equalsIgnoreCase(vehicleNumber))
                .findFirst()
                .orElse(null);
    }

    // 3. Find all vehicles owned by the owner with the given ID card number
    public List<Vehicle> findByOwnerIdCard(String idCard) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getOwner().getIdCard().equals(idCard)) {
                result.add(v);
            }
        }
        return result;
    }

    // 4. Delete all vehicles of a given manufacturer
    public int deleteByManufacturer(Manufacturer manufacturer) {
        int before = vehicles.size();
        vehicles.removeIf(v -> v.getManufacturer() == manufacturer);
        return before - vehicles.size();
    }

    // 5. Manufacturer with the most vehicles under management
    public Manufacturer findManufacturerWithMostVehicles() {
        Map<Manufacturer, Long> counts = countByManufacturer();
        return counts.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // 6. Manufacturers sorted by number of vehicles, descending
    public List<Map.Entry<Manufacturer, Long>> sortManufacturersByVehicleCountDesc() {
        Map<Manufacturer, Long> counts = countByManufacturer();
        List<Map.Entry<Manufacturer, Long>> entries = new ArrayList<>(counts.entrySet());
        entries.sort((a, b) -> Long.compare(b.getValue(), a.getValue()));
        return entries;
    }

    // 7. Number of vehicles being managed, grouped by vehicle type (Car/Motorbike/Truck)
    public Map<String, Long> statisticsByType() {
        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("Car", 0L);
        stats.put("Motorbike", 0L);
        stats.put("Truck", 0L);
        for (Vehicle v : vehicles) {
            stats.merge(v.getVehicleTypeName(), 1L, Long::sum);
        }
        return stats;
    }

    private Map<Manufacturer, Long> countByManufacturer() {
        Map<Manufacturer, Long> counts = new EnumMap<>(Manufacturer.class);
        for (Manufacturer m : Manufacturer.values()) {
            counts.put(m, 0L);
        }
        for (Vehicle v : vehicles) {
            counts.merge(v.getManufacturer(), 1L, Long::sum);
        }
        return counts;
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }
}
