package util;

import org.jetbrains.annotations.NotNull;
import users.students.Major;

/**
 */
public abstract class StringUtil {
    @NotNull
    public static String formatMajor(Major major){
        StringBuilder sb = new StringBuilder(major.toString());
        for (int i = 1; i < sb.length(); i++){
            if (Character.isUpperCase(sb.charAt(i))){
                sb.insert(i++, " ");
            }
        }
        return sb.toString();
    }
}
