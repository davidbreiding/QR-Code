package jpp.qrcode.decode;


import jpp.qrcode.DataPositions;
import jpp.qrcode.ReservedModulesMask;

public class DataExtractor {
    public static byte[] extract(boolean[][] data, ReservedModulesMask mask, int byteCount) {
        if (data.length != mask.size()) {
            throw new IllegalArgumentException("datalength & maskSize müssen gleich groß sein");
        }

        byte[] bytes = new byte[byteCount];
        DataPositions dp = new DataPositions(mask);


        //mit for schleife über matrix, falls eintrag 1 ist wird eine 1 um die position, an der sich zähler j befindet in bytearr geshifftet


        for (int i = 0; i < byteCount; i++) {
            int j = 0;
            while(j<=7){
                if (data[dp.i()][dp.j()] == true) {
                    bytes[i] = (byte) (bytes[i] + (1 << (7 - j)));
                }
                j++;
                dp.next();
            }
        }
        return bytes;

    }
}
