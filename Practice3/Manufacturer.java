package Practice3;

public enum Manufacturer {
    HONDA, YAMAHA, TOYOTA, SUZUKI;

    public static Manufacturer fromString(String input) {
        for (Manufacturer m : values()) {
            if (m.name().equalsIgnoreCase(input.trim())) {
                return m;
            }
        }
        throw new IllegalArgumentException(
                "Manufacturer must be one of: Honda, Yamaha, Toyota, Suzuki");
    }
}
