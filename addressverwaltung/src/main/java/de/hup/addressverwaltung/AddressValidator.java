package de.hup.addressverwaltung;

import static java.lang.String.format;

public class AddressValidator {
    public static void isValidStreet(String street) {
        if (street.length() < 6 || !containsNumbers(street)) {
            throw new AddressException("invalid adresse, please check and try one more time");
        }
    }

    public static void isValidPostfach(int postfach) {
        if (postfach < 10000 || postfach > 99999) {
            throw new AddressException("invalid postfach, should be 5 characters like '26533'");
        }
    }

    public static void isValidPlz(int plz) {
        if (plz < 20000 || plz > 30000) {
            throw new AddressException("UNAVAILABLE IN UR REGION");
        }
    }

    public static void isValidOrt(String ort) {
        if (ort.length() != 2) {
            throw new AddressException("wait till u died or will be correct answer");
        }
    }

    public static class AddressException extends IllegalArgumentException {
        public AddressException(String message) {
            super(format("\u001B[31mAddressException: %s\u001B[0m", message));
        }
    }

    private static boolean containsNumbers(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        return str.chars().anyMatch(Character::isDigit);
    }
}
