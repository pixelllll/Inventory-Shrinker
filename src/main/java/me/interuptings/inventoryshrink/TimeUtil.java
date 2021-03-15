package me.interuptings.inventoryshrink;

import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created on 3/15/2021
 *
 * @author Dylan
 */
public final class TimeUtil {

    private static final NumberFormat FORMATTER = NumberFormat.getInstance();

    static {
        FORMATTER.setParseIntegerOnly(true);
        FORMATTER.setMinimumIntegerDigits(2);
    }

    private TimeUtil() {}

    public static String secondsToString(long toConvert) {
        long seconds = toConvert;
        final TimeUnit[] units = TimeUnit.values();

        final StringBuilder builder = new StringBuilder();

        boolean negative = false;
        if (seconds < 0) {
            negative = true;
            seconds *= -1;
        }

        for (int i = TimeUnit.DAYS.ordinal(); i >= TimeUnit.SECONDS.ordinal(); i--) {
            final TimeUnit unit = units[i];

            final long count = unit.convert(seconds, TimeUnit.SECONDS);

            if (count > 0) {
                builder.append(FORMATTER.format(count))
                        .append(unit.name().toLowerCase().charAt(0))
                        .append(" ");
                seconds -= unit.toSeconds(count);
            }
        }

        if (builder.length() == 0) {
            return "00s";
        }

        builder.setLength(builder.length() - 1);
        String built = builder.toString();

        if (negative) {
            built = "-" + built;
        }

        return built;
    }
}
