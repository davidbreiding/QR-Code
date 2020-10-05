package jpp.qrcode.encode;

import jpp.qrcode.DataBlock;
import jpp.qrcode.ErrorCorrectionInformation;
import jpp.qrcode.reedsolomon.ReedSolomon;

public class DataStructurer {
    public static DataBlock[] split(byte[] data, ErrorCorrectionInformation errorCorrectionInformation) {

        //eine Gruppe
        if (errorCorrectionInformation.correctionGroups().length == 1) {

            int blocks = errorCorrectionInformation.totalBlockCount();
            int dataBytesPerBlock = errorCorrectionInformation.totalDataByteCount() / blocks;
            DataBlock[] dp = new DataBlock[blocks];
            byte[][] dataBytes = new byte[blocks][dataBytesPerBlock];

            int counterBlocks = 0;
            int counterDataBytes = 0;

            for (int i = 0; i < data.length; i++) {
                dataBytes[counterBlocks][counterDataBytes] = data[i];
                counterDataBytes++;

                if (counterDataBytes == dataBytesPerBlock) {
                    counterBlocks++;
                    counterDataBytes = 0;
                }
            }
            //Zusammenführung zu DataBlock[]
            for (int i = 0; i < dataBytes.length; i++) {
                dp[i] = new DataBlock(dataBytes[i], ReedSolomon.calculateCorrectionBytes(dataBytes[i], errorCorrectionInformation.correctionBytesPerBlock()));

            }
            return dp;
        }
        //2 Gruppen
        else {
            int blocks = errorCorrectionInformation.totalBlockCount();
            int dataBytesPerBlockG1 = errorCorrectionInformation.lowerDataByteCount();
            int dataBytesPerBlockG2 = dataBytesPerBlockG1 + 1;
            DataBlock[] dp = new DataBlock[blocks];
            byte[][] dataBytesG1 = new byte[errorCorrectionInformation.correctionGroups()[0].blockCount()][dataBytesPerBlockG1];
            byte[][] dataBytesG2 = new byte[errorCorrectionInformation.correctionGroups()[1].blockCount()][dataBytesPerBlockG2];

            //Gruppe 1
            int counterBlocks = 0;
            int counterDataBytes = 0;
            int i = 0;

            for (i = 0; i < dataBytesPerBlockG1 * errorCorrectionInformation.correctionGroups()[0].blockCount(); i++) {
                dataBytesG1[counterBlocks][counterDataBytes] = data[i];
                counterDataBytes++;

                if (counterDataBytes == dataBytesPerBlockG1) {
                    counterBlocks++;
                    counterDataBytes = 0;
                }
            }
            //Gruppe 2
            counterBlocks = 0;
            counterDataBytes = 0;
            for (int j = i; j < data.length; j++) {
                dataBytesG2[counterBlocks][counterDataBytes] = data[j];
                counterDataBytes++;

                if (counterDataBytes == dataBytesPerBlockG2) {
                    counterBlocks++;
                    counterDataBytes = 0;
                }
            }
            //Zusammenführung
            int l = 0;
            for (l = 0; l < dataBytesG1.length; l++) {
                dp[l] = new DataBlock(dataBytesG1[l], ReedSolomon.calculateCorrectionBytes(dataBytesG1[l], errorCorrectionInformation.correctionBytesPerBlock()));
            }

            for (int k = 0; k < dataBytesG2.length; k++) {
                dp[l] = new DataBlock(dataBytesG2[k], ReedSolomon.calculateCorrectionBytes(dataBytesG2[k], errorCorrectionInformation.correctionBytesPerBlock()));
                l++;
            }
            return dp;
        }
    }

    public static byte[] interleave(DataBlock[] blocks, ErrorCorrectionInformation ecBlocks) {
        byte[] bytes = new byte[ecBlocks.totalByteCount()];

        if (ecBlocks.correctionGroups().length == 1) {
            //Databytes
            int counter = 0;
            for (int i = 0; i < ecBlocks.lowerDataByteCount(); i++) {
                for (int j = 0; j < ecBlocks.totalBlockCount(); j++) {
                    bytes[counter] = blocks[j].dataBytes()[i];
                    counter++;
                }
            }
            //Korrektur
            for (int i = 0; i < ecBlocks.correctionBytesPerBlock(); i++) {
                for (int j = 0; j < ecBlocks.totalBlockCount(); j++) {
                    bytes[counter] = blocks[j].correctionBytes()[i];
                    counter++;
                }
            }
            return bytes;
        } else {

            int anzahlBlocksG1 = ecBlocks.correctionGroups()[0].blockCount();
            int anzahlBlocksG2 = ecBlocks.correctionGroups()[1].blockCount();

            //Databytes bis auf zusätzliche von zweiter Gruppe
            int counter = 0;
            int i = 0;
            for (i = 0; i < ecBlocks.lowerDataByteCount(); i++) {
                for (int j = 0; j < ecBlocks.totalBlockCount(); j++) {
                    bytes[counter] = blocks[j].dataBytes()[i];
                    counter++;
                }
            }

            //Zusätzliche
            for (int k = ecBlocks.correctionGroups()[0].blockCount(); k < ecBlocks.totalBlockCount(); k++) {
                bytes[counter] = blocks[k].dataBytes()[i];
                counter++;
            }

            //Korrektur
            for (int j = 0; j < ecBlocks.correctionBytesPerBlock(); j++) {
                for (int k = 0; k < ecBlocks.totalBlockCount(); k++) {
                    bytes[counter] = blocks[k].correctionBytes()[j];
                    counter++;
                }
            }
        }
        return bytes;
    }

    public static byte[] structure(byte[] data, ErrorCorrectionInformation ecBlocks) {
        if (ecBlocks.totalDataByteCount() != data.length) {
            throw new IllegalArgumentException("Längen passen nicht!");
        } else {
            return interleave(split(data, ecBlocks), ecBlocks);
        }
    }
}
