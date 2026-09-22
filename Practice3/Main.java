package Practice3;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final VehicleManager manager = new VehicleManager();

    public static void main(String[] args) {
        seedSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addVehicle(); break;
                case "2": searchByVehicleNumber(); break;
                case "3": findByOwnerIdCard(); break;
                case "4": deleteByManufacturer(); break;
                case "5": manufacturerWithMostVehicles(); break;
                case "6": sortManufacturersByVehicleCount(); break;
                case "7": statisticsByType(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid choice, please try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n===== VEHICLE MANAGEMENT =====");
        System.out.println("1. Add a new vehicle");
        System.out.println("2. Search for a vehicle by vehicle number");
        System.out.println("3. Find vehicles by owner's ID card number");
        System.out.println("4. Delete all vehicles of a manufacturer");
        System.out.println("5. Manufacturer with the most vehicles");
        System.out.println("6. Sort manufacturers by vehicle count (desc)");
        System.out.println("7. Statistics of vehicles by type (Car/Motorbike/Truck)");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");
    }

    private static void addVehicle() {
        try {
            String vehicleTypeChoice = promptVehicleTypeChoice();
            String number = promptVehicleNumber();
            Manufacturer manufacturer = promptManufacturer();
            int year = promptYear();
            String color = promptNonEmpty("Color: ");
            Owner owner = promptOwner();

            Vehicle vehicle;
            switch (vehicleTypeChoice) {
                case "1":
                    int seats = promptPositiveInt("Number of seats: ");
                    String engineType = promptNonEmpty("Engine type: ");
                    vehicle = new Car(number, manufacturer, year, color, owner, seats, engineType);
                    break;
                case "2":
                    double capacity = promptPositiveDouble("Capacity (cc): ");
                    vehicle = new Motorbike(number, manufacturer, year, color, owner, capacity);
                    break;
                default:
                    double tonnage = promptPositiveDouble("Tonnage (tons): ");
                    vehicle = new Truck(number, manufacturer, year, color, owner, tonnage);
                    break;
            }

            manager.addVehicle(vehicle);
            System.out.println("Vehicle added successfully: " + vehicle);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Owner promptOwner() {
        String idCard = promptIdCard();
        Owner existing = findOwnerIfExists(idCard);
        if (existing != null) {
            System.out.println("Existing owner found and reused: " + existing);
            return existing;
        }
        String name = promptNonEmpty("Owner full name: ");
        String email = promptEmail();
        return manager.getOrRegisterOwner(idCard, name, email);
    }

    private static Owner findOwnerIfExists(String idCard) {
        List<Vehicle> owned = manager.findByOwnerIdCard(idCard);
        return owned.isEmpty() ? null : owned.get(0).getOwner();
    }

    private static void searchByVehicleNumber() {
        String number = promptNonEmpty("Enter vehicle number: ");
        Vehicle vehicle = manager.findByVehicleNumber(number);
        System.out.println(vehicle != null ? "Found: " + vehicle : "No vehicle found with number " + number);
    }

    private static void findByOwnerIdCard() {
        String idCard = promptNonEmpty("Enter owner's ID card number: ");
        List<Vehicle> result = manager.findByOwnerIdCard(idCard);
        if (result.isEmpty()) {
            System.out.println("No vehicles found for this ID card number.");
        } else {
            result.forEach(System.out::println);
        }
    }

    private static void deleteByManufacturer() {
        try {
            Manufacturer manufacturer = promptManufacturer();
            int removed = manager.deleteByManufacturer(manufacturer);
            System.out.println(removed + " vehicle(s) of " + manufacturer + " deleted.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void manufacturerWithMostVehicles() {
        Manufacturer top = manager.findManufacturerWithMostVehicles();
        System.out.println(top != null ? "Manufacturer with most vehicles: " + top : "No data available.");
    }

    private static void sortManufacturersByVehicleCount() {
        List<Map.Entry<Manufacturer, Long>> sorted = manager.sortManufacturersByVehicleCountDesc();
        for (Map.Entry<Manufacturer, Long> entry : sorted) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " vehicle(s)");
        }
    }

    private static void statisticsByType() {
        Map<String, Long> stats = manager.statisticsByType();
        for (Map.Entry<String, Long> entry : stats.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " vehicle(s)");
        }
    }

    private static String promptVehicleTypeChoice() {
        while (true) {
            System.out.println("Vehicle type: 1-Car, 2-Motorbike, 3-Truck");
            String input = promptNonEmpty("Choose (1/2/3): ");
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                return input;
            }
            System.out.println("Invalid choice.");
        }
    }

    private static String promptVehicleNumber() {
        while (true) {
            String number = promptNonEmpty("Vehicle number (exactly 5 characters): ");
            if (!number.matches("^[A-Za-z0-9]{5}$")) {
                System.out.println("Invalid format, please enter exactly 5 letters/digits.");
                continue;
            }
            if (manager.isVehicleNumberTaken(number)) {
                System.out.println("This vehicle number already exists, please enter another one.");
                continue;
            }
            return number;
        }
    }

    private static Manufacturer promptManufacturer() {
        while (true) {
            String input = promptNonEmpty("Manufacturer (Honda/Yamaha/Toyota/Suzuki): ");
            try {
                return Manufacturer.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int promptYear() {
        int currentYear = Year.now().getValue();
        while (true) {
            String input = promptNonEmpty("Manufacture year (2001-" + currentYear + "): ");
            try {
                int year = Integer.parseInt(input);
                if (year > 2000 && year <= currentYear) {
                    return year;
                }
                System.out.println("Year must be greater than 2000 and less than or equal to " + currentYear);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String promptIdCard() {
        while (true) {
            String idCard = promptNonEmpty("Owner's ID card number (exactly 12 digits): ");
            if (idCard.matches("^\\d{12}$")) {
                return idCard;
            }
            System.out.println("ID card number must be exactly 12 digits.");
        }
    }

    private static String promptEmail() {
        while (true) {
            String email = promptNonEmpty("Owner's email: ");
            if (email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            }
            System.out.println("Invalid email format.");
        }
    }

    private static int promptPositiveInt(String label) {
        while (true) {
            String input = promptNonEmpty(label);
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double promptPositiveDouble(String label) {
        while (true) {
            String input = promptNonEmpty(label);
            try {
                double value = Double.parseDouble(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String promptNonEmpty(String label) {
        while (true) {
            System.out.print(label);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field must not be empty.");
        }
    }

    private static void seedSampleData() {
        Owner o1 = manager.getOrRegisterOwner("123456789012", "Nguyen Van A", "vana@gmail.com");
        Owner o2 = manager.getOrRegisterOwner("223456789012", "Tran Thi B", "thib@gmail.com");
        Owner o3 = manager.getOrRegisterOwner("323456789012", "Le Van C", "vanc@gmail.com");

        manager.addVehicle(new Motorbike("HD001", Manufacturer.HONDA, 2018, "Black", o1, 125));
        manager.addVehicle(new Car("HD002", Manufacturer.HONDA, 2020, "White", o2, 5, "Gasoline"));
        manager.addVehicle(new Motorbike("YM001", Manufacturer.YAMAHA, 2015, "Blue", o1, 150));
        manager.addVehicle(new Car("TY001", Manufacturer.TOYOTA, 2022, "Silver", o3, 7, "Hybrid"));
        manager.addVehicle(new Truck("SZ001", Manufacturer.SUZUKI, 2010, "Red", o2, 3.5));
        manager.addVehicle(new Motorbike("HD003", Manufacturer.HONDA, 2019, "Black", o3, 110));
    }
}
