package ma.youcode.myrhbackendapi.utils;

import java.util.UUID;

public class Utils {
    public static UUID pareseStringToUUID(String uuidString) {
        try { return UUID.fromString(uuidString); }
        catch (IllegalArgumentException exception) { throw new IllegalArgumentException("UUID is Invalid"); }
    }

    public static String normalizeEmail(String email) {
        int plusIndex = email.indexOf("+");
        int atIndex = email.indexOf("@");
        if (plusIndex != -1 && atIndex != -1) {
            return email.substring(0, plusIndex) + email.substring(atIndex);
        }
        return email;
    }
}
