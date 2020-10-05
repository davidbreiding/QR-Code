package jpp.qrcode.io;

import java.io.IOException;
import java.io.OutputStream;

public class TextWriter {
    public static void write(OutputStream stream, boolean[][] data) throws IOException {
        StringBuilder build = new StringBuilder();
        for (int zeile = 0; zeile < data.length; zeile++) {
            for (int spalte = 0; spalte < data.length; spalte++) {
                if (data[zeile][spalte] == true) {
                    build.append("1");
                    build.append(" ");
                }
                if (data[zeile][spalte] == false) {
                    build.append("0");
                    build.append(" ");
                }
            }
            if (build.length() > 0) {
                build.setLength(build.length() - 1);
                build.append("\n");
            }
        }
        if (build.length() > 0) {
            build.setLength(build.length() - 1);
        }

        stream.write(build.toString().getBytes());

    }
}
