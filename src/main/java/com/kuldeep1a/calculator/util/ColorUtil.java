package com.kuldeep1a.calculator.util;

import java.awt.*;
import java.util.Optional;

public class ColorUtil {
    public ColorUtil(){
        throw new AssertionError("Constructor nahi chalega!");
    }

    public static Color hexTwoColor(String colorHex){
        return Optional.ofNullable(colorHex)
                .map(hex -> new Color(
                        Integer.valueOf(colorHex.substring(0, 2), 16),
                        Integer.valueOf(colorHex.substring(2, 4), 16),
                        Integer.valueOf(colorHex.substring(4, 6), 16)))
                .orElse(null);
    }
}
