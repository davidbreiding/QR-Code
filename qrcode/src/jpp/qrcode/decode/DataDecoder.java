package jpp.qrcode.decode;

import jpp.qrcode.*;

import java.util.ArrayList;

public class DataDecoder {
    public static Encoding readEncoding(byte[] bytes) {


        StringBuilder sb = new StringBuilder();

        int bitOffset = 7;
        int offset = 0;
        while (offset == 0) {
            boolean bit = ((bytes[offset] >> bitOffset) & 1) != 0;
            if (bit) {
                sb.append("1");
            } else {
                sb.append("0");
            }
            if (bitOffset == 4) {
                offset++;
            } else {
                bitOffset--;
            }
        }

        int res = Byte.parseByte(sb.toString(), 2);

        return Encoding.fromBits(res);
    }

    public static int readCharacterCount(byte[] bytes, int count) {
        int res = 0;

        if (count == 8) {
            res = (bytes[0] << 4) & 0xF0 | (bytes[1] >> 4) & 0x0F;
        }
        if (count == 16) {
            res = (bytes[0] & 0xF) << 12 | (bytes[1] << 4) & 0xFF0 | (bytes[2] >> 4) & 0xF;
        }

        return res;
    }

    public static String decodeToString(byte[] bytes, Version version, ErrorCorrection errorCorrection) {
        if (bytes.length != version.correctionInformationFor(errorCorrection).totalDataByteCount()) {
            throw new IllegalArgumentException();
        }
        if ((readEncoding(bytes) != Encoding.BYTE)) {
            throw new QRDecodeException();
        }

        if (version.number() < 10) {
            if (readCharacterCount(bytes, 8) > bytes.length - 2) {
                throw new QRDecodeException("Fail");
            }
        }
        if (version.number() > 9) {
            if (readCharacterCount(bytes, 16) > bytes.length - 2) {
                throw new QRDecodeException("Fail");
            }
        }

        ArrayList<Character> bits = new ArrayList<>();
        StringBuilder sb = new StringBuilder();


        if (version.number() < 10) {

            for (int i = 0; i < readCharacterCount(bytes, 8); i++) {
                bits.add((char) ((bytes[i + 1] << 4) & 0xF0 | (bytes[i + 2] >> 4) & 0xF));
            }

            for (int i = 0; i < bits.size(); i++) {
                sb.append(bits.get(i));
            }
            return sb.toString();

        }

        if (version.number() > 9) {
            for (int i = 0; i < readCharacterCount(bytes, 16); i++) {
                bits.add((char) ((bytes[i + 2] << 4) & 0xF0 | (bytes[i + 3] >> 4) & 0xF));
            }

            for (int i = 0; i < bits.size(); i++) {
                sb.append(bits.get(i));
            }
            return sb.toString();
        }
        return null;
    }
}
