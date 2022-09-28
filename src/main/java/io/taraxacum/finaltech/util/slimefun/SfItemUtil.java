package io.taraxacum.finaltech.util.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

import javax.annotation.Nonnull;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public class SfItemUtil {

    @Nonnull
    public static String getIdFormatName(@Nonnull Class<? extends SlimefunItem> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FINALTECH_");
        boolean append = false;
        for(char c : clazz.getSimpleName().toCharArray()) {
            if(c >= 'A' && c <= 'Z') {
                if(append) {
                    stringBuilder.append("_");
                }
                stringBuilder.append(c);
                append = true;
            } else if (c >= 'a' && c <= 'z'){
                stringBuilder.append((char)(c - 32));
            } else {
                if(append) {
                    stringBuilder.append("_");
                    append = false;
                }
                stringBuilder.append(c);
            }
        }
        if (stringBuilder.indexOf("_") == 0) {
            stringBuilder.delete(0, 1);
        }
        return stringBuilder.toString();
    }
}
