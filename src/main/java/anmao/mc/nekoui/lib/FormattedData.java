package anmao.mc.nekoui.lib;

import java.text.DecimalFormat;

public class FormattedData {
    public static String numberToString(Object value){
        return formatValue(value,2);
    }
    public static String numberToString(Object value, int decimalPlaces) {
        double doubleValue = 0.0;
        if (value instanceof Number) {
            doubleValue = ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                doubleValue = Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid numeric string");
            }
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
        String pattern = "0.";
        for (int i = 0; i < decimalPlaces; i++) {
            pattern += "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(doubleValue);
    }
    public static String formatValue(Object value, int decimalPlaces) {
        if (value instanceof Number) {
            double doubleValue = ((Number) value).doubleValue();
            return formatDecimal(doubleValue, decimalPlaces);
        } else if (value instanceof String) {
            try {
                double doubleValue = Double.parseDouble((String) value);
                return formatDecimal(doubleValue, decimalPlaces);
            } catch (NumberFormatException e) {
                return (String) value;
            }
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    private static String formatDecimal(double value, int decimalPlaces) {
        String pattern = "0.";
        for (int i = 0; i < decimalPlaces; i++) {
            pattern += "0";
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }
}
