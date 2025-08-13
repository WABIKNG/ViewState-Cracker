package cn.wanghw.utils;

import java.time.LocalDateTime;
import java.util.*;

// Convert from https://github.com/yuvadm/viewstate by DeepSeek R1

public class ViewStateParser {
    public static boolean isWithoutMACViewState(String viewState) {
        try {
            byte[] data = Base64.decode(viewState);
            if (data[0] == -1 && data[1] == 1) {
                ParseResult result = parse(Arrays.copyOfRange(data, 2, data.length));
                return result.remaining.length == 0;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    static class ViewStateException extends Exception {
        public ViewStateException(String message) {
            super(message);
        }
    }

    private static final String[] COLORS = new String[]{
            "None",
            "ActiveBorder",
            "ActiveCaption",
            "ActiveCaptionText",
            "AppWorkspace",
            "Control",
            "ControlDark",
            "ControlDarkDark",
            "ControlLight",
            "ControlLightLight",
            "ControlText",
            "Desktop",
            "GrayText",
            "Highlight",
            "HighlightText",
            "HotTrack",
            "InactiveBorder",
            "InactiveCaption",
            "InactiveCaptionText",
            "Info",
            "InfoText",
            "Menu",
            "MenuText",
            "ScrollBar",
            "Window",
            "WindowFrame",
            "WindowText",
            "Transparent",
            "AliceBlue",
            "AntiqueWhite",
            "Aqua",
            "Aquamarine",
            "Azure",
            "Beige",
            "Bisque",
            "Black",
            "BlanchedAlmond",
            "Blue",
            "BlueViolet",
            "Brown",
            "BurlyWood",
            "CadetBlue",
            "Chartreuse",
            "Chocolate",
            "Coral",
            "CornflowerBlue",
            "Cornsilk",
            "Crimson",
            "Cyan",
            "DarkBlue",
            "DarkCyan",
            "DarkGoldenrod",
            "DarkGray",
            "DarkGreen",
            "DarkKhaki",
            "DarkMagenta",
            "DarkOliveGreen",
            "DarkOrange",
            "DarkOrchid",
            "DarkRed",
            "DarkSalmon",
            "DarkSeaGreen",
            "DarkSlateBlue",
            "DarkSlateGray",
            "DarkTurquoise",
            "DarkViolet",
            "DeepPink",
            "DeepSkyBlue",
            "DimGray",
            "DodgerBlue",
            "Firebrick",
            "FloralWhite",
            "ForestGreen",
            "Fuchsia",
            "Gainsboro",
            "GhostWhite",
            "Gold",
            "Goldenrod",
            "Gray",
            "Green",
            "GreenYellow",
            "Honeydew",
            "HotPink",
            "IndianRed",
            "Indigo",
            "Ivory",
            "Khaki",
            "Lavender",
            "LavenderBlush",
            "LawnGreen",
            "LemonChiffon",
            "LightBlue",
            "LightCoral",
            "LightCyan",
            "LightGoldenrodYellow",
            "LightGray",
            "LightGreen",
            "LightPink",
            "LightSalmon",
            "LightSeaGreen",
            "LightSkyBlue",
            "LightSlateGray",
            "LightSteelBlue",
            "LightYellow",
            "Lime",
            "LimeGreen",
            "Linen",
            "Magenta",
            "Maroon",
            "MediumAquamarine",
            "MediumBlue",
            "MediumOrchid",
            "MediumPurple",
            "MediumSeaGreen",
            "MediumSlateBlue",
            "MediumSpringGreen",
            "MediumTurquoise",
            "MediumVioletRed",
            "MidnightBlue",
            "MintCream",
            "MistyRose",
            "Moccasin",
            "NavajoWhite",
            "Navy",
            "OldLace",
            "Olive",
            "OliveDrab",
            "Orange",
            "OrangeRed",
            "Orchid",
            "PaleGoldenrod",
            "PaleGreen",
            "PaleTurquoise",
            "PaleVioletRed",
            "PapayaWhip",
            "PeachPuff",
            "Peru",
            "Pink",
            "Plum",
            "PowderBlue",
            "Purple",
            "Red",
            "RosyBrown",
            "RoyalBlue",
            "SaddleBrown",
            "Salmon",
            "SandyBrown",
            "SeaGreen",
            "SeaShell",
            "Sienna",
            "Silver",
            "SkyBlue",
            "SlateBlue",
            "SlateGray",
            "Snow",
            "SpringGreen",
            "SteelBlue",
            "Tan",
            "Teal",
            "Thistle",
            "Tomato",
            "Turquoise",
            "Violet",
            "Wheat",
            "White",
            "WhiteSmoke",
            "Yellow",
            "YellowGreen",
            "ButtonFace",
            "ButtonHighlight",
            "ButtonShadow",
            "GradientActiveCaption",
            "GradientInactiveCaption",
            "MenuBar",
            "MenuHighlight"
    };

    private static final Map<Integer, Class<? extends Parser>> registry = new HashMap<>();

    static {
        // 注册所有解析器
        registerParser(NoopParser.class);
        registerParser(NoneConst.class);
        registerParser(EmptyConst.class);
        registerParser(ZeroConst.class);
        registerParser(TrueConst.class);
        registerParser(FalseConst.class);
        registerParser(IntegerParser.class);
        registerParser(StringParser.class);
        registerParser(EnumParser.class);
        registerParser(EmptyColor.class);
        registerParser(ColorParser.class);
        registerParser(PairParser.class);
        registerParser(TripletParser.class);
        registerParser(DatetimeParser.class);
        registerParser(UnitParser.class);
        registerParser(RGBAParser.class);
        registerParser(StringArrayParser.class);
        registerParser(ArrayParser.class);
        registerParser(StringRefParser.class);
        registerParser(FormattedStringParser.class);
        registerParser(SparseArrayParser.class);
        registerParser(DictParser.class);
        registerParser(TypedArrayParser.class);
        registerParser(BinaryFormattedParser.class);
    }

    private static void registerParser(Class<? extends Parser> parserClass) {
        try {
            if (parserClass == IntegerParser.class) {
                registry.put(0x02, parserClass);
                registry.put(0x2B, parserClass);
            } else if (parserClass == StringParser.class) {
                registry.put(0x05, parserClass);
                registry.put(0x1E, parserClass);
                registry.put(0x2A, parserClass);
                registry.put(0x29, parserClass);
            } else {
                int marker = parserClass.getField("marker").getInt(null);
                registry.put(marker, parserClass);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to register parser: " + parserClass.getSimpleName(), e);
        }
    }

    public static ParseResult parse(byte[] data) throws ViewStateException {
        if (data == null || data.length == 0) {
            throw new ViewStateException("Empty data");
        }
        int marker = data[0] & 0xFF;
        Class<? extends Parser> parserClass = registry.get(marker);
        if (parserClass == null) {
            throw new ViewStateException("Unknown marker: " + marker);
        }
        byte[] remaining = Arrays.copyOfRange(data, 1, data.length);
        return Parser.parse(parserClass, remaining);
    }

    static class ParseResult {
        Object value;
        byte[] remaining;

        ParseResult(Object value, byte[] remaining) {
            this.value = value;
            this.remaining = remaining;
        }
    }

    static abstract class Parser {
        public static ParseResult parse(Class<? extends Parser> parserClass, byte[] data) throws ViewStateException {
            if (ConstParser.class.isAssignableFrom(parserClass)) {
                return ConstParser.parse(parserClass, data);
            }
            try {
                return (ParseResult) parserClass.getMethod("parse", byte[].class).invoke(null, (Object) data);
            } catch (Exception e) {
                throw new ViewStateException("Parser error: " + e.getMessage());
            }
        }
    }

    static abstract class ConstParser extends Parser {
        public static ParseResult parse(Class<? extends Parser> parserClass, byte[] data) {
            try {
                Object constValue = parserClass.getField("constValue").get(null);
                return new ParseResult(constValue, data);
            } catch (Exception e) {
                throw new RuntimeException("Constant parser error", e);
            }
        }
    }

    static class NoopParser extends Parser {
        public static final int marker = 0x01;

        public static ParseResult parse(byte[] data) {
            return new ParseResult(null, data);
        }
    }

    static class NoneConst extends ConstParser {
        public static final int marker = 0x64;
        public static final Object constValue = null;
    }

    static class EmptyConst extends ConstParser {
        public static final int marker = 0x65;
        public static final Object constValue = "";
    }

    static class ZeroConst extends ConstParser {
        public static final int marker = 0x66;
        public static final Object constValue = 0;
    }

    static class TrueConst extends ConstParser {
        public static final int marker = 0x67;
        public static final Object constValue = true;
    }

    static class FalseConst extends ConstParser {
        public static final int marker = 0x68;
        public static final Object constValue = false;
    }

    static class IntegerParser extends Parser {
        public static ParseResult parse(byte[] data) {
            int n = 0;
            int bits = 0;
            int i = 0;
            while (bits < 32 && i < data.length) {
                int tmp = data[i] & 0xFF;
                i++;
                n |= (tmp & 0x7F) << bits;
                if ((tmp & 0x80) == 0) {
                    break;
                }
                bits += 7;
            }
            return new ParseResult(n, Arrays.copyOfRange(data, i, data.length));
        }
    }

    static class StringParser extends Parser {
        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult lenResult = IntegerParser.parse(data);
            int len = (int) lenResult.value;
            byte[] remaining = lenResult.remaining;

            if (len > remaining.length) {
                throw new ViewStateException("Invalid string length");
            }
            byte[] strBytes = Arrays.copyOfRange(remaining, 0, len);
            return new ParseResult(new java.lang.String(strBytes), Arrays.copyOfRange(remaining, len, remaining.length));
        }
    }

    static class EnumParser extends Parser {
        public static final int marker = 0x0B;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult enumResult = ViewStateParser.parse(data);
            Object enumVal = enumResult.value;
            ParseResult intResult = IntegerParser.parse(enumResult.remaining);
            int val = (int) intResult.value;
            String result = "Enum: " + enumVal + ", val: " + val;
            return new ParseResult(result, intResult.remaining);
        }
    }

    static class EmptyColor extends ConstParser {
        public static final int marker = 0x0C;
        public static final Object constValue = "Color: Empty";
    }

    static class ColorParser extends Parser {
        public static final int marker = 0x0A;

        public static ParseResult parse(byte[] data) {
            int index = data[0] & 0xFF;
            String color = (index < COLORS.length) ? COLORS[index] : "Unknown";
            return new ParseResult("Color: " + color, Arrays.copyOfRange(data, 1, data.length));
        }
    }

    static class PairParser extends Parser {
        public static final int marker = 0x0F;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult firstResult = ViewStateParser.parse(data);
            ParseResult secondResult = ViewStateParser.parse(firstResult.remaining);
            Object[] pair = new Object[]{firstResult.value, secondResult.value};
            return new ParseResult(pair, secondResult.remaining);
        }
    }

    static class TripletParser extends Parser {
        public static final int marker = 0x10;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult firstResult = ViewStateParser.parse(data);
            ParseResult secondResult = ViewStateParser.parse(firstResult.remaining);
            ParseResult thirdResult = ViewStateParser.parse(secondResult.remaining);
            Object[] triplet = new Object[]{firstResult.value, secondResult.value, thirdResult.value};
            return new ParseResult(triplet, thirdResult.remaining);
        }
    }

    static class DatetimeParser extends Parser {
        public static final int marker = 0x06;

        public static ParseResult parse(byte[] data) {
            // 简化处理，实际应解析字节
            return new ParseResult(LocalDateTime.of(2000, 1, 1, 0, 0),
                    Arrays.copyOfRange(data, 8, data.length));
        }
    }

    static class UnitParser extends Parser {
        public static final int marker = 0x1B;

        public static ParseResult parse(byte[] data) {
            return new ParseResult("Unit: ", Arrays.copyOfRange(data, 12, data.length));
        }
    }

    static class RGBAParser extends Parser {
        public static final int marker = 0x09;

        public static ParseResult parse(byte[] data) {
            String rgba = String.format("RGBA(%d,%d,%d,%d)",
                    data[0] & 0xFF, data[1] & 0xFF, data[2] & 0xFF, data[3] & 0xFF);
            return new ParseResult(rgba, Arrays.copyOfRange(data, 4, data.length));
        }
    }

    static class StringArrayParser extends Parser {
        public static final int marker = 0x15;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult lenResult = IntegerParser.parse(data);
            int n = (int) lenResult.value;
            byte[] remaining = lenResult.remaining;
            List<String> list = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if (remaining.length == 0) break;
                if (remaining[0] == 0) {
                    list.add("");
                    remaining = Arrays.copyOfRange(remaining, 1, remaining.length);
                } else {
                    ParseResult strResult = StringParser.parse(remaining);
                    list.add((java.lang.String) strResult.value);
                    remaining = strResult.remaining;
                }
            }
            return new ParseResult(list, remaining);
        }
    }

    static class ArrayParser extends Parser {
        public static final int marker = 0x16;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult lenResult = IntegerParser.parse(data);
            int n = (int) lenResult.value;
            byte[] remaining = lenResult.remaining;
            List<Object> list = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                ParseResult itemResult = ViewStateParser.parse(remaining);
                list.add(itemResult.value);
                remaining = itemResult.remaining;
            }
            return new ParseResult(list, remaining);
        }
    }

    static class StringRefParser extends Parser {
        public static final int marker = 0x1F;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult intResult = IntegerParser.parse(data);
            int ref = (int) intResult.value;
            return new ParseResult("Stringref #" + ref, intResult.remaining);
        }
    }

    static class FormattedStringParser extends Parser {
        public static final int marker = 0x28;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult typeResult = ViewStateParser.parse(data);
            ParseResult strResult = StringParser.parse(typeResult.remaining);
            String str = (java.lang.String) strResult.value;
            String result = "Formatted string: " + str + " type ref " + typeResult.value;
            return new ParseResult(result, strResult.remaining);
        }
    }

    static class SparseArrayParser extends Parser {
        public static final int marker = 0x3C;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult typeResult = ViewStateParser.parse(data);
            ParseResult lenResult = IntegerParser.parse(typeResult.remaining);
            int length = (int) lenResult.value;
            ParseResult nResult = IntegerParser.parse(lenResult.remaining);
            int n = (int) nResult.value;
            byte[] remaining = nResult.remaining;
            List<Object> list = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                list.add(null);
            }

            for (int i = 0; i < n; i++) {
                ParseResult idxResult = IntegerParser.parse(remaining);
                int idx = (int) idxResult.value;
                ParseResult valResult = ViewStateParser.parse(idxResult.remaining);
                list.set(idx, valResult.value);
                remaining = valResult.remaining;
            }
            return new ParseResult(list, remaining);
        }
    }

    static class DictParser extends Parser {
        public static final int marker = 0x18;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            int n = data[0] & 0xFF;
            byte[] remaining = Arrays.copyOfRange(data, 1, data.length);
            Map<Object, Object> map = new HashMap<>();

            for (int i = 0; i < n; i++) {
                ParseResult keyResult = ViewStateParser.parse(remaining);
                ParseResult valResult = ViewStateParser.parse(keyResult.remaining);
                map.put(keyResult.value, valResult.value);
                remaining = valResult.remaining;
            }
            return new ParseResult(map, remaining);
        }
    }

    static class TypedArrayParser extends Parser {
        public static final int marker = 0x14;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult typeResult = ViewStateParser.parse(data);
            ParseResult lenResult = IntegerParser.parse(typeResult.remaining);
            int n = (int) lenResult.value;
            byte[] remaining = lenResult.remaining;
            List<Object> list = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                ParseResult itemResult = ViewStateParser.parse(remaining);
                list.add(itemResult.value);
                remaining = itemResult.remaining;
            }
            return new ParseResult(list, remaining);
        }
    }

    static class BinaryFormattedParser extends Parser {
        public static final int marker = 0x32;

        public static ParseResult parse(byte[] data) throws ViewStateException {
            ParseResult lenResult = IntegerParser.parse(data);
            int n = (int) lenResult.value;
            byte[] remaining = lenResult.remaining;
            if (n > remaining.length) {
                throw new ViewStateException("Invalid binary length");
            }
            byte[] binData = Arrays.copyOfRange(remaining, 0, n);
            return new ParseResult("Binary: " + Arrays.toString(binData),
                    Arrays.copyOfRange(remaining, n, remaining.length));
        }
    }
}

