package Practice3;

import java.util.regex.Pattern;

public class Owner {
    public static final Pattern ID_CARD_PATTERN = Pattern.compile("^\\d{12}$");
    public static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    private final String idCard;
    private final String fullName;
    private final String email;

    public Owner(String idCard, String fullName, String email) {
        if (!ID_CARD_PATTERN.matcher(idCard).matches()) {
            throw new IllegalArgumentException("ID card number must be exactly 12 digits");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name must not be empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not in a valid format");
        }
        this.idCard = idCard;
        this.fullName = fullName;
        this.email = email;
    }

    public String getIdCard() {
        return idCard;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%s (CMND: %s, email: %s)", fullName, idCard, email);
    }
}
