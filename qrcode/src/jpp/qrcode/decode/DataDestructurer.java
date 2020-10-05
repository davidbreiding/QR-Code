package jpp.qrcode.decode;

import jpp.qrcode.DataBlock;
import jpp.qrcode.ErrorCorrectionInformation;
import jpp.qrcode.reedsolomon.ReedSolomon;
import jpp.qrcode.reedsolomon.ReedSolomonException;

public class DataDestructurer {
    public static byte[] join(DataBlock[] blocks, ErrorCorrectionInformation errorCorrectionInformation) {

        byte[] bytes = new byte[errorCorrectionInformation.totalDataByteCount()];
        for (int i = 0; i < blocks.length; i++) {
            try {
                ReedSolomon.correct(blocks[i].dataBytes(), blocks[i].correctionBytes());
            } catch (ReedSolomonException e) {
                throw new QRDecodeException("Stimmt was nicht");
            }
        }
        int j = 0;
        for (int block = 0; block < blocks.length; block++) {
            for (int i = 0; i < blocks[block].dataBytes().length; i++) {
                bytes[j] = blocks[block].dataBytes()[i];
                j++;
            }
        }
        return bytes;
    }


    public static DataBlock[] deinterleave(byte[] data, ErrorCorrectionInformation errorCorrectionInformation) {

        DataBlock[] deinterleave = new DataBlock[errorCorrectionInformation.totalBlockCount()];


        //Gruppe1
        if (errorCorrectionInformation.correctionGroups().length == 1) {

            byte[][] dataBytes = new byte[errorCorrectionInformation.correctionGroups()[0].blockCount()][errorCorrectionInformation.correctionGroups()[0].dataByteCount()];
            byte[][] correctBytes = new byte[errorCorrectionInformation.correctionGroups()[0].blockCount()][errorCorrectionInformation.correctionBytesPerBlock()];
            int index = 0;
            int counter = 0;

            //Databytes
            for (int i = 0; i < errorCorrectionInformation.correctionGroups()[0].dataByteCount(); i++) {
                for (int j = 0; j < errorCorrectionInformation.correctionGroups()[0].blockCount(); j++) {
                    dataBytes[j][counter] = data[index];
                    index++;
                }
                counter++;
            }

            counter = 0;

            //Korrekturbytes
            for (int i = 0; i < errorCorrectionInformation.correctionBytesPerBlock(); i++) {
                for (int j = 0; j < errorCorrectionInformation.correctionGroups()[0].blockCount(); j++) {
                    correctBytes[j][counter] = data[index];
                    index++;
                }
                counter++;
            }


            for (int i = 0; i < errorCorrectionInformation.totalBlockCount(); i++) {

                deinterleave[i] = new DataBlock(dataBytes[i], correctBytes[i]);
            }
        } else {       //2 Gruppen
            byte[][] dataBytes1 = new byte[errorCorrectionInformation.correctionGroups()[0].blockCount()][errorCorrectionInformation.correctionGroups()[0].dataByteCount()];
            byte[][] dataBytes2 = new byte[errorCorrectionInformation.correctionGroups()[1].blockCount()][errorCorrectionInformation.correctionGroups()[1].dataByteCount()];

            byte[][] correctBytes = new byte[errorCorrectionInformation.totalBlockCount()][errorCorrectionInformation.correctionBytesPerBlock()];

            int index = 0;


//Databytes
            for (int i = 0; i < (errorCorrectionInformation.totalDataByteCount() - errorCorrectionInformation.correctionGroups()[1].blockCount()) / errorCorrectionInformation.totalBlockCount(); i++) {
                for (int j = 0; j < errorCorrectionInformation.totalBlockCount(); j++) {
                    if (j < errorCorrectionInformation.correctionGroups()[0].blockCount()) {
                        dataBytes1[j][i] = data[index];

                    } else {
                        dataBytes2[j - errorCorrectionInformation.correctionGroups()[0].blockCount()][i] = data[index];

                    }

                    index++;
                }
            }

            //Zusätzliches Byte Gruppe 2
            int counter2 = 0;
            for (int i = index; i < errorCorrectionInformation.totalDataByteCount(); i++) {
                dataBytes2[counter2][dataBytes2[counter2].length - 1] = data[index];
                counter2++;
                index++;
            }


            //Korrekturbytes
            int counter = 0;
            index = errorCorrectionInformation.totalDataByteCount();
            for (int i = 0; i < errorCorrectionInformation.correctionBytesPerBlock(); i++) {
                for (int j = 0; j < errorCorrectionInformation.correctionGroups()[0].blockCount() + errorCorrectionInformation.correctionGroups()[1].blockCount(); j++) {
                    correctBytes[j][counter] = data[index];
                    index++;
                }
                counter++;
            }

            //Zusammenführung
            for (int i = 0; i < errorCorrectionInformation.totalBlockCount(); i++) {

                if (i < errorCorrectionInformation.correctionGroups()[0].blockCount()) {
                    deinterleave[i] = new DataBlock(dataBytes1[i], correctBytes[i]);
                } else {
                    deinterleave[i] = new DataBlock(dataBytes2[i - errorCorrectionInformation.correctionGroups()[0].blockCount()], correctBytes[i]);

                }
            }
        }

        return deinterleave;

    }


    public static byte[] destructure(byte[] data, ErrorCorrectionInformation ecBlocks) throws ReedSolomonException {
        if (data.length != ecBlocks.totalByteCount()) {
            throw new IllegalArgumentException();
        } else {
            return join(deinterleave(data, ecBlocks), ecBlocks);
        }
    }
}
