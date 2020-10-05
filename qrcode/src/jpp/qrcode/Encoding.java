package jpp.qrcode;

public enum Encoding {
    NUMERIC, ALPHANUMERIC, BYTE, KANJI, ECI, INVALID;

    public static Encoding fromBits(int i) {
        if (i == 0b1) {
            return NUMERIC;
        }
        if (i == 0b10) {
            return ALPHANUMERIC;
        }
        if (i == 0b100) {
            return BYTE;
        }
        if (i == 0b1000) {
            return KANJI;
        }
        if (i == 0b111) {
            return ECI;
        } else {
            return INVALID;
        }
    }

    public int bits() {
        if (this == NUMERIC) {
            return 0b1;
        }
        if (this == ALPHANUMERIC) {
            return 0b10;
        }
        if (this == BYTE) {
            return 0b100;
        }
        if (this == KANJI) {
            return 0b1000;
        }
        if (this == ECI) {
            return 0b111;
        }
        return -1;
    }
}
