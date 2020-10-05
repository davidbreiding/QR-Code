package jpp.qrcode.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TextReader {
    public static boolean[][] read(InputStream in) throws IOException {
        if (in == null) {
            throw new IOException("InputSTream ist null");
        }

        ArrayList<Boolean> bools = new ArrayList<>();
        boolean isQRCode = true;

        while (true) {
            byte zeichen = (byte) in.read();

            //Abbruchbedingung
            if ((int) zeichen == -1) {
                break;
            }
            if (zeichen == '#') {
                isQRCode = false;
            } else if (zeichen == '\n') {
                isQRCode = true;
            }
            if (isQRCode == false) {
                continue;
            }

            if (zeichen != '0' && zeichen != '1' && zeichen != ' ' && zeichen != '\n' && zeichen != '\r' && isQRCode == true) {
                throw new IOException("Illegale Zeichen verwendet");
            } else {
                if (zeichen == (byte) '1') {
                    bools.add(true);
                } else if (zeichen == (byte) '0') {
                    bools.add(false);
                }
            }
        }


        boolean[][] res = new boolean[(int) Math.sqrt(bools.size())][(int) Math.sqrt(bools.size())];
        int index = 0;
        for (int zeile = 0; zeile < res.length; zeile++) {
            for (int spalte = 0; spalte < res.length; spalte++) {
                res[zeile][spalte] = bools.get(index);
                index++;
            }
        }
        return res;
    }
}