package jpp.qrcode.encode;

import jpp.qrcode.DataPositions;
import jpp.qrcode.ReservedModulesMask;

public class DataInserter {
    public static void insert(boolean[][] target, ReservedModulesMask mask, byte[] data) {

        DataPositions dp = new DataPositions(mask);


        int bitOffset = 7;
        int offset = 0;
        while (offset < data.length) {
            boolean bit = ((data[offset] >> bitOffset) & 1) != 0;
            if (bit == true) {
                target[dp.i()][dp.j()] = true;
            }
            if (bit == false) {
                target[dp.i()][dp.j()] = false;
            }
            dp.next();

            if (bitOffset == 0) {
                bitOffset = 7;
                offset++;
            } else {
                bitOffset--;
            }
        }

    }
}
