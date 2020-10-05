package jpp.qrcode.encode;

import jpp.qrcode.Version;
import jpp.qrcode.VersionInformation;

import java.util.Arrays;

public class PatternPlacer {
    public static void placeOrientation(boolean[][] res, Version version) {
        //OrientationPattern
        //horizontal
        for (int spalte = 0; spalte < 7; spalte++) {
            res[0][spalte] = true;
            res[6][spalte] = true;
            res[res.length - 7][spalte] = true;
            res[res.length - 1][spalte] = true;

        }

        for (int spalte = res.length - 7; spalte < res.length; spalte++) {
            res[0][spalte] = true;
            res[6][spalte] = true;
        }


        for (int spalte = 1; spalte <= 5; spalte++) {
            res[1][spalte] = false;
            res[5][spalte] = false;
            res[res.length - 6][spalte] = false;
            res[res.length - 2][spalte] = false;
        }

        for (int spalte = res.length - 6; spalte < res.length - 1; spalte++) {
            res[1][spalte] = false;
            res[5][spalte] = false;
        }

        //vertikal
        for (int zeile = 1; zeile < 6; zeile++) {
            res[zeile][0] = true;
            res[zeile][1] = false;
            res[zeile][5] = false;
            res[zeile][6] = true;
            res[zeile][res.length - 7] = true;
            res[zeile][res.length - 6] = false;
            res[zeile][res.length - 2] = false;
            res[zeile][res.length - 1] = true;
        }

        for (int zeile = res.length - 6; zeile < res.length - 1; zeile++) {
            res[zeile][0] = true;
            res[zeile][1] = false;
            res[zeile][5] = false;
            res[zeile][6] = true;
        }

        //Mitte
        for (int zeile = 2; zeile < 5; zeile++) {
            res[zeile][2] = true;
            res[zeile][3] = true;
            res[zeile][4] = true;
            res[zeile][res.length - 5] = true;
            res[zeile][res.length - 4] = true;
            res[zeile][res.length - 3] = true;
        }

        for (int zeile = res.length - 5; zeile < res.length - 2; zeile++) {
            res[zeile][2] = true;
            res[zeile][3] = true;
            res[zeile][4] = true;

        }
    }

    public static void placeTiming(boolean[][] res, Version version) {
        for (int zahl = 8; zahl < res.length - 8; zahl++) {
            if ((zahl & 1) == 0) {
                res[zahl][6] = true;
                res[6][zahl] = true;

            } else {
                res[6][zahl] = false;
                res[zahl][6] = false;
            }
        }
    }

    public static void placeAlignment(boolean[][] res, Version version) {

        if (version.alignmentPositions().length != 0) {
            int[] array = Arrays.copyOf(version.alignmentPositions(), version.alignmentPositions().length);
            Arrays.sort(array);

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    if ((i == 0 && j == 0) || (i == 0 && j == array.length - 1) || (i == array.length - 1 && j == 0)) {
                        continue;
                    } else {

                        res[array[i] - 2][array[j] - 2] = true;
                        res[array[i] - 2][array[j] - 1] = true;
                        res[array[i] - 2][array[j] - 0] = true;
                        res[array[i] - 2][array[j] + 1] = true;
                        res[array[i] - 2][array[j] + 2] = true;
                        res[array[i] - 1][array[j] - 2] = true;
                        res[array[i] - 1][array[j] - 1] = false;
                        res[array[i] - 1][array[j] - 0] = false;
                        res[array[i] - 1][array[j] + 1] = false;
                        res[array[i] - 1][array[j] + 2] = true;
                        res[array[i] - 0][array[j] - 2] = true;
                        res[array[i] - 0][array[j] - 1] = false;
                        res[array[i] - 0][array[j] - 0] = true;
                        res[array[i] - 0][array[j] + 1] = false;
                        res[array[i] - 0][array[j] + 2] = true;
                        res[array[i] + 1][array[j] - 2] = true;
                        res[array[i] + 1][array[j] - 1] = false;
                        res[array[i] + 1][array[j] - 0] = false;
                        res[array[i] + 1][array[j] + 1] = false;
                        res[array[i] + 1][array[j] + 2] = true;
                        res[array[i] + 2][array[j] - 2] = true;
                        res[array[i] + 2][array[j] - 1] = true;
                        res[array[i] + 2][array[j] - 0] = true;
                        res[array[i] + 2][array[j] + 1] = true;
                        res[array[i] + 2][array[j] + 2] = true;
                    }
                }
            }
        }
    }

    public static void placeVersionInformation(boolean[][] data, int versionInformation) {
        //Version erster Block
        String s = Integer.toBinaryString(versionInformation);
        int i = s.length() - 1;
        for (int zeile = 0; zeile < 6; zeile++) {
            for (int spalte = data.length - 11; spalte < data.length - 8; spalte++) {
                if (i >= 0) {
                    if (s.charAt(i) == '1') {
                        data[zeile][spalte] = true;
                    } else {
                        data[zeile][spalte] = false;
                    }
                    i--;
                } else {
                    data[zeile][spalte] = false;
                    i--;
                }
            }
        }


        //das gleiche noch für den 2. Block!!!!!
        int it = s.length() - 1;
        for (int spalte = 0; spalte < 6; spalte++) {
            for (int zeile = data.length - 11; zeile < data.length - 8; zeile++) {
                if (it >= 0) {
                    if (s.charAt(it) == '1') {
                        data[zeile][spalte] = true;
                    } else {
                        data[zeile][spalte] = false;
                    }
                    it--;
                } else {
                    data[zeile][spalte] = false;
                    it--;
                }
            }
        }


    }

    public static boolean[][] createBlankForVersion(Version version) {
        int l = version.size();
        boolean[][] matrix = new boolean[l][l];
        if (version.number() > 6) {
            placeOrientation(matrix, version);
            placeTiming(matrix, version);
            placeAlignment(matrix, version);
            placeVersionInformation(matrix, VersionInformation.forVersion(version));
            matrix[matrix.length - 8][8] = true;
        }
        if (version.number() <= 6) {
            placeOrientation(matrix, version);
            placeTiming(matrix, version);
            placeAlignment(matrix, version);
            matrix[matrix.length - 8][8] = true;
        }
        return matrix;
    }
}
