package jpp.qrcode;

import java.util.Arrays;

public class QRCode {

    boolean[][] matrix;
    Version version;
    MaskPattern pattern;
    ErrorCorrection correction;


    public QRCode(boolean[][] matrix, Version version, MaskPattern pattern, ErrorCorrection correction) {
        this.matrix = matrix;
        this.version = version;
        this.pattern = pattern;
        this.correction = correction;

    }


    public Version version() {
        return version;
    }

    public boolean[][] data() {
        return matrix;
    }

    public MaskPattern maskPattern() {
        return pattern;
    }

    public ErrorCorrection errorCorrection() {
        return correction;
    }

    public String matrixToString() {
        StringBuilder str = new StringBuilder();
        for (int zeile = 0; zeile < matrix.length; zeile++) {
            for (int spalte = 0; spalte < matrix[zeile].length; spalte++) {
                if (matrix[zeile][spalte] == true) {
                    str.append((char) 0x2588);
                    str.append((char) 0x2588);
                } else {
                    str.append((char) 0x2591);
                    str.append((char) 0x2591);
                }
            }
            str.append("\n");
        }
        if (str.length() > 1) {
            str.setLength(str.length() - 1);
        }
        return str.toString();
    }


    public static QRCode createValidatedFromBooleans(boolean[][] data) throws InvalidQRCodeException {


        //null
        if (data == null) {
            throw new InvalidQRCodeException("data ist null");
        }
        //leer
        if (data.length == 0) {
            throw new InvalidQRCodeException("data ist leer");
        }
        //quadratisch
        for (int zeile = 0; zeile < data.length; zeile++) {
            if (data.length != data[zeile].length) {
                throw new InvalidQRCodeException("data ist nicht quadratisch");
            }
        }
        //gültige size für Version
        if (!(20 < data.length && data.length < 178)) {
            throw new InvalidQRCodeException("keine gültige size");
        } else {
            if ((data.length - 17) % 4 != 0) {
                throw new InvalidQRCodeException("keine gültige size");
            }
        }
        //Separatoren
        //horizontal
        for (int spalte = 0; spalte <= 7; spalte++) {
            if (data[7][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Separator sein");
            }
        }
        for (int spalte = data.length - 8; spalte < data.length; spalte++) {
            if (data[7][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Separator sein");
            }
        }

        for (int spalte = 0; spalte <= 7; spalte++) {
            if (data[data.length - 8][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Separator sein");
            }
        }

        //vertikal
        for (int zeile = 0; zeile <= 7; zeile++) {
            if (data[zeile][7] == true) {
                throw new InvalidQRCodeException("Hier muss Separator sein");
            }
        }
        for (int zeile = data.length - 8; zeile < data.length; zeile++) {
            if (data[zeile][7] == true) {
                throw new InvalidQRCodeException("Hier muss Separator sein");
            }
        }

        for (int zeile = 0; zeile <= 7; zeile++) {
            if (data[zeile][data.length - 8] == true) {
                throw new InvalidQRCodeException("Hier muss Separator sein");
            }
        }


        //OrientationPattern
        //horizontal
        for (int spalte = 0; spalte < 7; spalte++) {
            if (data[0][spalte] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationpattern sein");
            }
            if (data[6][spalte] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[data.length - 7][spalte] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[data.length - 1][spalte] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
        }
        for (int spalte = data.length - 7; spalte < data.length; spalte++) {
            if (data[0][spalte] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[6][spalte] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
        }

        for (int spalte = 1; spalte <= 5; spalte++) {
            if (data[1][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[5][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern stehen");
            }
            if (data[data.length - 6][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[data.length - 2][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
        }

        for (int spalte = data.length - 6; spalte < data.length - 1; spalte++) {
            if (data[1][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[5][spalte] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
        }

        //vertikal
        for (int zeile = 1; zeile < 6; zeile++) {
            if (data[zeile][0] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][1] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][5] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][6] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][data.length - 7] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][data.length - 6] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][data.length - 2] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][data.length - 1] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
        }
        for (int zeile = data.length - 6; zeile < data.length - 1; zeile++) {
            if (data[zeile][0] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][1] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][5] == true) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][6] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
        }

        //Mitte
        for (int zeile = 2; zeile < 5; zeile++) {
            if (data[zeile][2] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][3] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][4] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][data.length - 5] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][data.length - 4] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][data.length - 3] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
        }

        for (int zeile = data.length - 5; zeile < data.length - 2; zeile++) {
            if (data[zeile][2] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][3] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
            if (data[zeile][4] == false) {
                throw new InvalidQRCodeException("Hier muss Orientationspattern sein");
            }
        }

        //DarkModule
        if (data[data.length - 8][8] == false) {
            throw new InvalidQRCodeException("Dark Modul stimmt nicht!");
        }

        //TimingPattern
        for (int zahl = 8; zahl <= data.length - 7; zahl++) {
            if ((zahl & 1) == 0) {
                if (data[zahl][6] == false) {
                    throw new InvalidQRCodeException("TimingPattern stimmt nicht");
                }
                if (data[6][zahl] == false) {
                    throw new InvalidQRCodeException("Timing Pattern stimmt nicht");
                }
            } else if (!((zahl & 1) == 0)) {
                if (data[6][zahl] == true) {
                    throw new InvalidQRCodeException("Timing Pattern stimmt nicht");
                }
                if (data[zahl][6] == true) {
                    throw new InvalidQRCodeException("Timing Pattern stimmt nicht");
                }
            }
        }


        int version = (data.length - 17) / 4;


        //Version erster Block
        int index1 = 17;
        int[] vers1 = new int[18];
        for (int zeile = 0; zeile < 6; zeile++) {
            for (int spalte = data.length - 11; spalte < data.length - 8; spalte++) {
                if (data[zeile][spalte]) {
                    vers1[index1] = 1;
                } else {
                    vers1[index1] = 0;
                }
                index1--;
            }
        }

        String s1 = Arrays.toString(vers1).replaceAll("\\[|\\]|,|\\s", "");


        //das gleiche noch für den 2. Block!!!!!

        int index2 = 17;
        int[] vers2 = new int[18];
        for (int spalte = 0; spalte < 6; spalte++) {
            for (int zeile = data.length - 11; zeile < data.length - 8; zeile++) {
                if (data[zeile][spalte]) {
                    vers2[index2] = 1;
                } else {
                    vers2[index2] = 0;
                }
                index2--;
            }
        }

        String s2 = Arrays.toString(vers2).replaceAll("\\[|\\]|,|\\s", "");


        if (version > 6) {
            if ((VersionInformation.fromBits(Integer.parseInt(s1, 2)) == null || VersionInformation.fromBits(Integer.parseInt(s1, 2)).number() != version)
                    && (VersionInformation.fromBits(Integer.parseInt(s2, 2)) == null || VersionInformation.fromBits(Integer.parseInt(s2, 2)).number() != version)) {
                throw new InvalidQRCodeException("Ein Versionsblock muss stimmen");
            }
        }


        //FormatInformation oberer
        int index3 = 14;
        int[] form1 = new int[15];
        for (int zeile = 0; zeile < 9; zeile++) {
            if (zeile == 6) {
                continue;
            }
            if (data[zeile][8] == true) {
                form1[index3] = 1;
            } else {
                form1[index3] = 0;
            }
            index3--;
        }

        for (int spalte = 7; spalte >= 0; spalte--) {
            if (spalte == 6) {
                continue;
            }
            if (data[8][spalte] == true) {
                form1[index3] = 1;
            } else {
                form1[index3] = 0;
            }
            index3--;
        }

        String s3 = Arrays.toString(form1).replaceAll("\\[|\\]|,|\\s", "");


        //Das gleiche nochmal mit 2. Block
        int index4 = 14;
        int[] form2 = new int[15];
        for (int spalte = data.length - 1; spalte > data.length - 9; spalte--) {
            if (data[8][spalte] == true) {
                form2[index4] = 1;
            } else {
                form2[index4] = 0;
            }
            index4--;
        }

        for (int zeile = data.length - 7; zeile < data.length; zeile++) {
            if (data[zeile][8] == true) {
                form2[index4] = 1;
            } else {
                form2[index4] = 0;
            }
            index4--;
        }

        String s4 = Arrays.toString(form2).replaceAll("\\[|\\]|,|\\s", "");


        if (FormatInformation.fromBits(Integer.parseInt(s3, 2)) == null && FormatInformation.fromBits(Integer.parseInt(s4, 2)) == null) {
            throw new InvalidQRCodeException("FormatInformation ist null");
        }


        //Alignment patterns


        Version verssssion = null;
        if (version <= 6) {
            verssssion = Version.fromNumber(version);
        }
        if (version > 6) {
            if (VersionInformation.fromBits(Integer.parseInt(s1, 2)) != null && VersionInformation.fromBits(Integer.parseInt(s1, 2)).number() == version) {
                verssssion = VersionInformation.fromBits(Integer.parseInt(s1, 2));
            } else if (VersionInformation.fromBits(Integer.parseInt(s2, 2)) != null && VersionInformation.fromBits(Integer.parseInt(s2, 2)).number() == version) {
                verssssion = VersionInformation.fromBits(Integer.parseInt(s2, 2));
            }
        }

        if (verssssion.alignmentPositions().length != 0) {
            int[] array = Arrays.copyOf(verssssion.alignmentPositions(), verssssion.alignmentPositions().length);
            Arrays.sort(array);

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    if ((i == 0 && j == 0) || (i == 0 && j == array.length - 1) || (i == array.length - 1 && j == 0)) {

                        if (
                                data[array[i] - 2][array[j] - 2] == true &&
                                        data[array[i] - 2][array[j] - 1] == true &&
                                        data[array[i] - 2][array[j] - 0] == true &&
                                        data[array[i] - 2][array[j] + 1] == true &&
                                        data[array[i] - 2][array[j] + 2] == true &&
                                        data[array[i] - 1][array[j] - 2] == true &&
                                        data[array[i] - 1][array[j] - 1] == false &&
                                        data[array[i] - 1][array[j] - 0] == false &&
                                        data[array[i] - 1][array[j] + 1] == false &&
                                        data[array[i] - 1][array[j] + 2] == true &&
                                        data[array[i] - 0][array[j] - 2] == true &&
                                        data[array[i] - 0][array[j] - 1] == false &&
                                        data[array[i] - 0][array[j] - 0] == true &&
                                        data[array[i] - 0][array[j] + 1] == false &&
                                        data[array[i] - 0][array[j] + 2] == true &&
                                        data[array[i] + 1][array[j] - 2] == true &&
                                        data[array[i] + 1][array[j] - 1] == false &&
                                        data[array[i] + 1][array[j] - 0] == false &&
                                        data[array[i] + 1][array[j] + 1] == false &&
                                        data[array[i] + 1][array[j] + 2] == true &&
                                        data[array[i] + 2][array[j] - 2] == true &&
                                        data[array[i] + 2][array[j] - 1] == true &&
                                        data[array[i] + 2][array[j] - 0] == true &&
                                        data[array[i] + 2][array[j] + 1] == true &&
                                        data[array[i] + 2][array[j] + 2] == true) {
                            throw new InvalidQRCodeException("AlignmentPattern dürfen nicht auf Orientationspattern liegen");
                        }
                    } else {
                        if (!(
                                data[array[i] - 2][array[j] - 2] == true &&
                                        data[array[i] - 2][array[j] - 1] == true &&
                                        data[array[i] - 2][array[j] - 0] == true &&
                                        data[array[i] - 2][array[j] + 1] == true &&
                                        data[array[i] - 2][array[j] + 2] == true &&
                                        data[array[i] - 1][array[j] - 2] == true &&
                                        data[array[i] - 1][array[j] - 1] == false &&
                                        data[array[i] - 1][array[j] - 0] == false &&
                                        data[array[i] - 1][array[j] + 1] == false &&
                                        data[array[i] - 1][array[j] + 2] == true &&
                                        data[array[i] - 0][array[j] - 2] == true &&
                                        data[array[i] - 0][array[j] - 1] == false &&
                                        data[array[i] - 0][array[j] - 0] == true &&
                                        data[array[i] - 0][array[j] + 1] == false &&
                                        data[array[i] - 0][array[j] + 2] == true &&
                                        data[array[i] + 1][array[j] - 2] == true &&
                                        data[array[i] + 1][array[j] - 1] == false &&
                                        data[array[i] + 1][array[j] - 0] == false &&
                                        data[array[i] + 1][array[j] + 1] == false &&
                                        data[array[i] + 1][array[j] + 2] == true &&
                                        data[array[i] + 2][array[j] - 2] == true &&
                                        data[array[i] + 2][array[j] - 1] == true &&
                                        data[array[i] + 2][array[j] - 0] == true &&
                                        data[array[i] + 2][array[j] + 1] == true &&
                                        data[array[i] + 2][array[j] + 2] == true)) {
                            throw new InvalidQRCodeException("AlignmentPattern fehlt");
                        }
                    }
                }
            }
        }


        if (version > 6) {
            if (VersionInformation.fromBits(Integer.parseInt(s1, 2)) != null) {
                if (FormatInformation.fromBits(Integer.parseInt(s3, 2)) != null) {
                    QRCode code = new QRCode(data, VersionInformation.fromBits(Integer.parseInt(s1, 2)), FormatInformation.fromBits(Integer.parseInt(s3, 2)).maskPattern(), FormatInformation.fromBits(Integer.parseInt(s3, 2)).errorCorrection());
                    return code;
                } else {
                    QRCode code = new QRCode(data, VersionInformation.fromBits(Integer.parseInt(s1, 2)), FormatInformation.fromBits(Integer.parseInt(s4, 2)).maskPattern(), FormatInformation.fromBits(Integer.parseInt(s4, 2)).errorCorrection());
                    return code;
                }
            }
            if (FormatInformation.fromBits(Integer.parseInt(s3, 2)) != null) {
                QRCode code = new QRCode(data, VersionInformation.fromBits(Integer.parseInt(s2, 2)), FormatInformation.fromBits(Integer.parseInt(s3, 2)).maskPattern(), FormatInformation.fromBits(Integer.parseInt(s3, 2)).errorCorrection());
                return code;
            }
            QRCode code = new QRCode(data, VersionInformation.fromBits(Integer.parseInt(s2, 2)), FormatInformation.fromBits(Integer.parseInt(s4, 2)).maskPattern(), FormatInformation.fromBits(Integer.parseInt(s4, 2)).errorCorrection());
            return code;
        }

        if (FormatInformation.fromBits(Integer.parseInt(s3, 2)) != null) {
            QRCode code = new QRCode(data, Version.fromNumber(version), FormatInformation.fromBits(Integer.parseInt(s3, 2)).maskPattern(), FormatInformation.fromBits(Integer.parseInt(s3, 2)).errorCorrection());
            return code;
        }
        QRCode code = new QRCode(data, Version.fromNumber(version), FormatInformation.fromBits(Integer.parseInt(s4, 2)).maskPattern(), FormatInformation.fromBits(Integer.parseInt(s4, 2)).errorCorrection());
        return code;
    }
}
