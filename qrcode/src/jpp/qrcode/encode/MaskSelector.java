package jpp.qrcode.encode;

import jpp.qrcode.*;

import java.util.ArrayList;
import java.util.TreeMap;

import static java.lang.Math.abs;

public class MaskSelector {

    public static void placeFormatInformation(boolean[][] res, int formatInformation) {

        String s = Integer.toBinaryString(formatInformation);

        int i = s.length() - 1;
        for (int zeile = 0; zeile < 9; zeile++) {
            if (i >= 0) {
                if (zeile == 6) {
                    continue;
                }
                if (s.charAt(i) == '1') {
                    res[zeile][8] = true;
                } else {
                    res[zeile][8] = false;
                }
                i--;
            } else {
                res[zeile][8] = false;
                i--;
            }
        }
        for (int spalte = 7; spalte >= 0; spalte--) {
            if (i >= 0) {
                if (spalte == 6) {
                    continue;
                }
                if (s.charAt(i) == '1') {
                    res[8][spalte] = true;
                } else {
                    res[8][spalte] = false;
                }
                i--;
            } else {
                res[8][spalte] = false;
                i--;
            }
        }

        i = s.length() - 1;
        for (int spalte = res.length - 1; spalte > res.length - 9; spalte--) {
            if (i >= 0) {
                if (s.charAt(i) == '1') {
                    res[8][spalte] = true;
                } else {
                    res[8][spalte] = false;
                }
                i--;
            } else {
                res[8][spalte] = false;
                i--;
            }
        }

        for (int zeile = res.length - 7; zeile < res.length; zeile++) {
            if (i >= 0) {
                if (s.charAt(i) == '1') {
                    res[zeile][8] = true;
                } else {
                    res[zeile][8] = false;
                }
                i--;
            } else {
                res[zeile][8] = false;
                i--;
            }
        }
    }


    public static int calculatePenaltySameColored(boolean[][] data) {

        int penalty = 0;
        //Zeilen
        for (int zeile = 0; zeile < data.length; zeile++) {
            for (int spalte = 0; spalte < data.length - 4; spalte++) {
                if (data[zeile][spalte] == true &&
                        data[zeile][spalte + 1] == true &&
                        data[zeile][spalte + 2] == true &&
                        data[zeile][spalte + 3] == true &&
                        data[zeile][spalte + 4] == true) {
                    penalty = penalty + 3;
                    if (spalte + 5 < data.length) {
                        spalte = spalte + 5;

                        while (data[zeile][spalte] == true) {
                            penalty++;
                            if (spalte == data.length - 1) {
                                break;
                            }
                            spalte++;
                        }
                    }
                }
                if (spalte < data.length - 4) {
                    if (data[zeile][spalte] == false &&
                            data[zeile][spalte + 1] == false &&
                            data[zeile][spalte + 2] == false &&
                            data[zeile][spalte + 3] == false &&
                            data[zeile][spalte + 4] == false) {
                        penalty = penalty + 3;
                        if (spalte + 5 < data.length) {
                            spalte = spalte + 5;

                            while (data[zeile][spalte] == false) {
                                penalty++;
                                if (spalte == data.length - 1) {
                                    break;
                                }
                                spalte++;
                            }
                            spalte--;
                        }
                    }
                }
            }
        }

        //Spalten
        for (int spalte = 0; spalte < data.length; spalte++) {
            for (int zeile = 0; zeile < data.length - 4; zeile++) {
                if (data[zeile][spalte] == true &&
                        data[zeile + 1][spalte] == true &&
                        data[zeile + 2][spalte] == true &&
                        data[zeile + 3][spalte] == true &&
                        data[zeile + 4][spalte] == true) {
                    penalty = penalty + 3;
                    if (zeile + 5 < data.length) {
                        zeile = zeile + 5;

                        while (data[zeile][spalte] == true) {
                            penalty++;
                            if (zeile == data.length - 1) {
                                break;
                            }
                            zeile++;
                        }
                    }
                }

                if (zeile < data.length - 4) {
                    if (data[zeile][spalte] == false &&
                            data[zeile + 1][spalte] == false &&
                            data[zeile + 2][spalte] == false &&
                            data[zeile + 3][spalte] == false &&
                            data[zeile + 4][spalte] == false) {
                        penalty = penalty + 3;
                        if (zeile + 5 < data.length) {
                            zeile = zeile + 5;

                            while (data[zeile][spalte] == false) {
                                penalty++;
                                if (zeile == data.length - 1) {
                                    break;
                                }
                                zeile++;
                            }
                            zeile--;
                        }
                    }
                }
            }
        }
        return penalty;
    }

    public static int calculatePenalty2x2(boolean[][] arr) {
        int penalty = 0;
        for (int zeile = 0; zeile < arr.length - 1; zeile++) {
            for (int spalte = 0; spalte < arr.length - 1; spalte++) {
                if (arr[zeile][spalte] == true &&
                        arr[zeile][spalte + 1] == true &&
                        arr[zeile + 1][spalte] == true &&
                        arr[zeile + 1][spalte + 1] == true) {
                    penalty = penalty + 3;
                }
                if (arr[zeile][spalte] == false &&
                        arr[zeile][spalte + 1] == false &&
                        arr[zeile + 1][spalte] == false &&
                        arr[zeile + 1][spalte + 1] == false) {
                    penalty = penalty + 3;
                }
            }
        }
        return penalty;
    }

    public static int calculatePenaltyBlackWhite(boolean[][] arr) {
        int module = 0;
        int dunkel = 0;
        for (int zeile = 0; zeile < arr.length; zeile++) {
            for (int spalte = 0; spalte < arr.length; spalte++) {
                if (arr[zeile][spalte] == true) {
                    dunkel++;
                }
                module++;
            }
        }
        return (10 * (abs(2 * dunkel - module) * 10 / module));
    }

    public static int calculatePenaltyPattern(boolean[][] array) {
        int penalty = 0;

        //Zeilen
        for (int zeile = 0; zeile < array.length; zeile++) {
            for (int spalte = 0; spalte < array.length - 6; spalte++) {

                //Muster in der Mitte
                if (spalte > 3 && spalte < array.length - 10) {
                    if (array[zeile][spalte] == true &&
                            array[zeile][spalte + 1] == false &&
                            array[zeile][spalte + 2] == true &&
                            array[zeile][spalte + 3] == true &&
                            array[zeile][spalte + 4] == true &&
                            array[zeile][spalte + 5] == false &&
                            array[zeile][spalte + 6] == true) {
                        if ((array[zeile][spalte - 4] == false &&
                                array[zeile][spalte - 3] == false &&
                                array[zeile][spalte - 2] == false &&
                                array[zeile][spalte - 1] == false) ||
                                (array[zeile][spalte + 7] == false &&
                                        array[zeile][spalte + 8] == false &&
                                        array[zeile][spalte + 9] == false &&
                                        array[zeile][spalte + 10] == false)
                        ) {
                            penalty = penalty + 40;

                        }
                    }
                }
                //Wenn Muster ganz am Anfang liegt
                else if (spalte < 4) {
                    if (array[zeile][spalte] == true &&
                            array[zeile][spalte + 1] == false &&
                            array[zeile][spalte + 2] == true &&
                            array[zeile][spalte + 3] == true &&
                            array[zeile][spalte + 4] == true &&
                            array[zeile][spalte + 5] == false &&
                            array[zeile][spalte + 6] == true) {
                        if (array[zeile][spalte + 7] == false &&
                                array[zeile][spalte + 8] == false &&
                                array[zeile][spalte + 9] == false &&
                                array[zeile][spalte + 10] == false) {
                            penalty = penalty + 40;
                        }
                    }
                }
                //Muster am Ende
                else if (spalte >= array.length - 10) {
                    if (array[zeile][spalte] == true &&
                            array[zeile][spalte + 1] == false &&
                            array[zeile][spalte + 2] == true &&
                            array[zeile][spalte + 3] == true &&
                            array[zeile][spalte + 4] == true &&
                            array[zeile][spalte + 5] == false &&
                            array[zeile][spalte + 6] == true) {
                        if ((array[zeile][spalte - 4] == false &&
                                array[zeile][spalte - 3] == false &&
                                array[zeile][spalte - 2] == false &&
                                array[zeile][spalte - 1] == false)) {
                            penalty = penalty + 40;
                        }
                    }
                }
            }
        }

        //Spalten
        for (int spalte = 0; spalte < array.length; spalte++) {
            for (int zeile = 0; zeile < array.length - 6; zeile++) {

                //Muster in Mitte
                if (zeile > 3 && zeile < array.length - 10) {
                    if (array[zeile][spalte] == true &&
                            array[zeile + 1][spalte] == false &&
                            array[zeile + 2][spalte] == true &&
                            array[zeile + 3][spalte] == true &&
                            array[zeile + 4][spalte] == true &&
                            array[zeile + 5][spalte] == false &&
                            array[zeile + 6][spalte] == true) {
                        if ((array[zeile - 4][spalte] == false &&
                                array[zeile - 3][spalte] == false &&
                                array[zeile - 2][spalte] == false &&
                                array[zeile - 1][spalte] == false) ||
                                (array[zeile + 7][spalte] == false &&
                                        array[zeile + 8][spalte] == false &&
                                        array[zeile + 9][spalte] == false &&
                                        array[zeile + 10][spalte] == false)
                        ) {
                            penalty = penalty + 40;
                        }
                    }
                }
                //Wenn am Anfang
                else if (zeile < 4) {
                    if (array[zeile][spalte] == true &&
                            array[zeile + 1][spalte] == false &&
                            array[zeile + 2][spalte] == true &&
                            array[zeile + 3][spalte] == true &&
                            array[zeile + 4][spalte] == true &&
                            array[zeile + 5][spalte] == false &&
                            array[zeile + 6][spalte] == true) {
                        if (array[zeile + 7][spalte] == false &&
                                array[zeile + 8][spalte] == false &&
                                array[zeile + 9][spalte] == false &&
                                array[zeile + 10][spalte] == false) {
                            penalty = penalty + 40;
                        }
                    }
                }

                //Muster am Ende
                else if (zeile >= array.length - 10) {
                    if (array[zeile][spalte] == true &&
                            array[zeile + 1][spalte] == false &&
                            array[zeile + 2][spalte] == true &&
                            array[zeile + 3][spalte] == true &&
                            array[zeile + 4][spalte] == true &&
                            array[zeile + 5][spalte] == false &&
                            array[zeile + 6][spalte] == true) {
                        if ((array[zeile - 4][spalte] == false &&
                                array[zeile - 3][spalte] == false &&
                                array[zeile - 2][spalte] == false &&
                                array[zeile - 1][spalte] == false)) {
                            penalty = penalty + 40;
                        }

                    }
                }
            }
        }

        return penalty;
    }

    public static int calculatePenaltyFor(boolean[][] data) {
        int penalty = calculatePenaltySameColored(data) + calculatePenalty2x2(data) + calculatePenaltyBlackWhite(data) + calculatePenaltyPattern(data);
        return penalty;
    }

    public static MaskPattern maskWithBestMask(boolean[][] data, ErrorCorrection correction, ReservedModulesMask modulesMask) {
        if (modulesMask.size() != data.length) {
            throw new IllegalArgumentException("Größen passen nicht!");
        }

        TreeMap<Integer, MaskPattern> map = new TreeMap<>();
        MaskPattern[] maskPatt = MaskPattern.values();

        for (int i = maskPatt.length - 1; i > -1; i--) {
            MaskApplier.applyTo(data, maskPatt[i].maskFunction(), modulesMask);
            placeFormatInformation(data, FormatInformation.get(correction, maskPatt[i]).formatInfo());

            int penalty = calculatePenaltyFor(data);
            map.put(penalty, maskPatt[i]);

            MaskApplier.applyTo(data, maskPatt[i].maskFunction(), modulesMask);

        }

        MaskPattern bestPattern = map.get(map.firstKey());

        MaskApplier.applyTo(data, bestPattern.maskFunction(), modulesMask);

        placeFormatInformation(data, FormatInformation.get(correction, bestPattern).formatInfo());

        return bestPattern;
    }
}
