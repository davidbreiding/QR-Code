package jpp.qrcode;


import java.util.Arrays;

public class ReservedModulesMask {

    final boolean[][] mask;

    public ReservedModulesMask(boolean[][] mask) {
        this.mask = mask;
    }

    public boolean isReserved(int i, int j) {
        if (mask[i][j] == true) {
            return true;
        }
        return false;
    }

    public int size() {
        return mask.length;
    }

    public static ReservedModulesMask forVersion(Version version) {
        boolean[][] mask1 = new boolean[version.size()][version.size()]; //hiermit eigentlich Kopie

//Orientationspatterns & Format
        for (int zeile = 0; zeile < 9; zeile++) {
            for (int spalte = 0; spalte < 9; spalte++) {
                mask1[zeile][spalte] = true;
            }
        }
        for (int zeile = mask1.length - 8; zeile < mask1.length; zeile++) {
            for (int spalte = 0; spalte < 9; spalte++) {
                mask1[zeile][spalte] = true;
            }
        }
        for (int zeile = 0; zeile < 9; zeile++) {
            for (int spalte = mask1.length - 8; spalte < mask1.length; spalte++) {
                mask1[zeile][spalte] = true;
            }
        }


        //AlignmentPattern
        //Höchste und niedrigste Koordinate auslesen
        //Höchste + höchste geht, niedrigste + niedrigste, höchste niedrigste und niedrigste höchste geht nicht

        if (version.alignmentPositions().length != 0) {
            //hier muss alles rein

            int[] array = Arrays.copyOf(version.alignmentPositions(), version.alignmentPositions().length);
            Arrays.sort(array);

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    if ((i == 0 && j == 0) || (i == 0 && j == array.length - 1) || (i == array.length - 1 && j == 0)) {
                        continue;

                    } else {
                        for (int y = array[i] - 2; y <= array[i] + 2; y++) {
                            for (int z = array[j] - 2; z <= array[j] + 2; z++) {
                                mask1[y][z] = true;
                            }
                        }
                    }
                }
            }
        }

        //timingPattern:
        for (int spalte = 0; spalte < mask1.length; spalte++) {
            mask1[6][spalte] = true;
        }
        for (int zeile = 0; zeile < mask1.length; zeile++) {
            mask1[zeile][6] = true;
        }

        //VersionPattern ab 7
        if (version.number() >= 7) {

            for (int zeile = 0; zeile < 6; zeile++) {
                for (int spalte = mask1.length - 11; spalte < mask1.length - 8; spalte++) {
                    mask1[zeile][spalte] = true;
                }
            }
            for (int zeile = mask1.length - 11; zeile < mask1.length - 8; zeile++) {
                for (int spalte = 0; spalte < 6; spalte++) {
                    mask1[zeile][spalte] = true;
                }
            }
        }

        return new ReservedModulesMask(mask1);


    }
}

