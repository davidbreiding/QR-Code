package jpp.qrcode.encode;

import jpp.qrcode.ErrorCorrection;
import jpp.qrcode.Version;

public final class DataEncoder {
    public static DataEncoderResult encodeForCorrectionLevel(String str, ErrorCorrection level) {

        if (str.getBytes().length > Version.forDataBytesCount(str.length(), level).correctionInformationFor(level).totalDataByteCount()) {
            throw new IllegalArgumentException("Fail");
        }

        byte[] bytes = str.getBytes();

        Version version1 = Version.forDataBytesCount(str.length() + 2, level);
        Version version2 = Version.forDataBytesCount(str.length() + 3, level);


        if (version1.number() > 9) {
            byte[] result = new byte[version2.correctionInformationFor(level).totalDataByteCount()];
            int i;

            for (i = 0; i < bytes.length + 3; i++) {
                if (i == 0) {
                    result[i] = (byte) ((byte) (result[i] + (1 << (6))) | (byte) (str.length() >> 12 & 0xF));
                } else if (i == 1) {
                    result[i] = (byte) (str.length() >> 4 & 0x00FF);
                } else if (i == 2) {
                    result[i] = (byte) ((str.length() << 4 & 0xF0) | (bytes[0] >> 4 & 0x0F));
                } else if (i == bytes.length + 2) {
                    result[i] = (byte) ((bytes[bytes.length - 1]) << 4 & 0xF0);
                } else {
                    result[i] = (byte) (bytes[i - 3] << 4 & 0xF0 | bytes[i - 2] >> 4 & 0x0F);
                }
            }

            //Padding
            for (int k = i; k < result.length; k = k + 2) {
                result[k] = ((byte) 0b11101100);
            }

            for (int j = i + 1; j < result.length; j = j + 2) {
                result[j] = ((byte) 0b00010001);
            }

            return new DataEncoderResult(result, version2);

        } else {

            byte[] result = new byte[version1.correctionInformationFor(level).totalDataByteCount()];
            int i = 0;


            if (bytes.length == 0) {
                result[0] = (byte) ((byte) (result[0] + (1 << (6))) | (byte) (str.length() >> 4 & 0xF));
                result[1] = (byte) (str.length() << 4 & 0xF0);

                //Padding
                for (int k = 2; k < result.length; k = k + 2) {
                    result[k] = ((byte) 0b11101100);
                }

                for (int j = 3; j < result.length; j = j + 2) {
                    result[j] = ((byte) 0b00010001);
                }

                return new DataEncoderResult(result, version1);


            } else {
                for (i = 0; i < bytes.length + 2; i++) {
                    if (i == 0) {
                        result[i] = (byte) ((byte) (result[i] + (1 << (6))) | (byte) (str.length() >> 4 & 0xF));
                    } else if (i == 1) {
                        result[i] = (byte) (str.length() << 4 & 0xF0 | bytes[0] >> 4 & 0xF);
                    } else if (i == bytes.length + 1) {
                        result[i] = (byte) ((bytes[bytes.length - 1]) << 4 & 0xF0);
                    } else {
                        result[i] = (byte) (bytes[i - 2] << 4 & 0xF0 | bytes[i - 1] >> 4 & 0x0F);
                    }
                }

                //Padding
                for (int k = i; k < result.length; k = k + 2) {
                    result[k] = ((byte) 0b11101100);
                }

                for (int j = i + 1; j < result.length; j = j + 2) {
                    result[j] = ((byte) 0b00010001);
                }
                return new DataEncoderResult(result, version1);
            }
        }
    }
}

