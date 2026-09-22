package Practice2;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GoodsManager manager = new GoodsManager();
    private static final String[] TYPES = {"Food", "Electronics", "Crockery"};

    public static void main(String[] args) {
        seedSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addGoods(); break;
                case "2": showQuantityByType(); break;
                case "3": showVatByType(); break;
                case "4": showConsumptionEvaluation(); break;
                case "5": listAllGoods(); break;
                case "0": running = false; break;
                default: System.out.println("Invalid choice, please try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n===== SUPERMARKET INVENTORY MANAGEMENT =====");
        System.out.println("1. Add goods");
        System.out.println("2. Inventory quantity by type");
        System.out.println("3. VAT amount by type");
        System.out.println("4. Consumption evaluation for all goods");
        System.out.println("5. List all goods");
        System.out.println("0. Exit");
        System.out.print("Your choice: ");
    }

    private static void addGoods() {
        try {
            String typeChoice = promptGoodsTypeChoice();
            String code = promptUniqueProductCode();
            String name = promptNonEmpty("Name: ");
            int quantity = promptNonNegativeInt("Quantity (>= 0): ");
            double unitPrice = promptNonNegativeDouble("Unit price (>= 0): ");

            Goods goods;
            switch (typeChoice) {
                case "1": {
                    LocalDate manufactureDate = promptDate("Manufacture date (yyyy-MM-dd): ");
                    LocalDate expiryDate = promptDate("Expiry date (yyyy-MM-dd): ");
                    String supplier = promptNonEmpty("Supplier: ");
                    goods = new Food(code, name, quantity, unitPrice, manufactureDate, expiryDate, supplier);
                    break;
                }
                case "2": {
                    int warrantyMonths = promptNonNegativeInt("Warranty period (months, >= 0): ");
                    double capacityKW = promptNonNegativeDouble("Capacity (kW, >= 0): ");
                    goods = new Electronics(code, name, quantity, unitPrice, warrantyMonths, capacityKW);
                    break;
                }
                default: {
                    String manufacturerInfo = promptNonEmpty("Manufacturer info: ");
                    LocalDate arrivalDate = promptDate("Arrival date (yyyy-MM-dd): ");
                    goods = new Crockery(code, name, quantity, unitPrice, manufacturerInfo, arrivalDate);
                    break;
                }
            }

            boolean added = manager.addGoods(goods);
            System.out.println(added ? "Goods added successfully: " + goods
                    : "Failed to add: product code already exists.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showQuantityByType() {
        for (String type : TYPES) {
            System.out.println(type + ": " + manager.getTotalQuantityByType(type) + " unit(s) in stock");
        }
    }

    private static void showVatByType() {
        for (String type : TYPES) {
            System.out.printf("%s: VAT amount = %.0f%n", type, manager.getTotalVatByType(type));
        }
    }

    private static void showConsumptionEvaluation() {
        Goods[] all = manager.getAllGoods();
        if (all.length == 0) {
            System.out.println("No goods in inventory.");
            return;
        }
        for (Goods g : all) {
            System.out.printf("[%s] %s (%s): %s%n", g.getProductCode(), g.getName(), g.getTypeName(),
                    g.evaluateConsumption());
        }
    }

    private static void listAllGoods() {
        Goods[] all = manager.getAllGoods();
        if (all.length == 0) {
            System.out.println("No goods in inventory.");
            return;
        }
        for (Goods g : all) {
            System.out.println(g);
        }
    }

    private static String promptGoodsTypeChoice() {
        while (true) {
            System.out.println("Goods type: 1-Food, 2-Electronics, 3-Crockery");
            String input = promptNonEmpty("Choose (1/2/3): ");
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                return input;
            }
            System.out.println("Invalid choice.");
        }
    }

    private static String promptUniqueProductCode() {
        while (true) {
            String code = promptNonEmpty("Product code: ");
            if (manager.isProductCodeTaken(code)) {
                System.out.println("This product code already exists, please enter another one.");
                continue;
            }
            return code;
        }
    }

    private static int promptNonNegativeInt(String label) {
        while (true) {
            String input = promptNonEmpty(label);
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Value must be >= 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double promptNonNegativeDouble(String label) {
        while (true) {
            String input = promptNonEmpty(label);
            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Value must be >= 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate promptDate(String label) {
        while (true) {
            String input = promptNonEmpty(label);
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, expected yyyy-MM-dd.");
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
        manager.addGoods(new Food("F0001", "Milk", 20, 25000,
                LocalDate.of(2026, 6, 1), LocalDate.of(2026, 9, 1), "Vinamilk"));
        manager.addGoods(new Food("F0002", "Instant noodles", 100, 5000,
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 1), "Acecook"));
        manager.addGoods(new Electronics("E0001", "Refrigerator", 2, 8000000, 24, 0.3));
        manager.addGoods(new Electronics("E0002", "Electric fan", 15, 500000, 12, 0.06));
        manager.addGoods(new Crockery("C0001", "Ceramic bowl", 60, 15000, "Minh Long", LocalDate.of(2026, 9, 1)));
        manager.addGoods(new Crockery("C0002", "Glass cup", 30, 10000, "Duralex", LocalDate.of(2026, 9, 15)));
    }
}
