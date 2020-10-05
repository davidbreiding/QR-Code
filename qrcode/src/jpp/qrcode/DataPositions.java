package jpp.qrcode;

public class DataPositions {
    int i;
    int j;
    ReservedModulesMask mask;
    boolean hoch = true;
    int ii;
    int jj;


    public DataPositions(ReservedModulesMask mask) {
        this.mask = mask;
        this.i = mask.size() - 1;
        this.j = mask.size() - 1;
        this.ii = i;
        this.jj = j;
    }

    public int i() {
        return i;
    }

    public int j() {
        return j;
    }


    public boolean next() {
        if (jj == 6) {
            jj--;
            return next();
        }

        if (ii == mask.size() - 8 && jj == 0) {
            return false;
        }

        if (ii < 0) {
            jj = jj - 2;
            ii = 0;
            hoch = false;
            return next();
        }

        if (ii > mask.size() - 1) {
            jj = jj - 2;
            ii = mask.size() - 1;
            hoch = true;
            return next();
        }

        if (jj > 6) {

            //rechts
            if (jj % 2 == 0) {

                if (hoch) {

                    if (!mask.isReserved(ii, jj)) {
                        if (ii == mask.size() - 1 && jj == mask.size() - 1) {
                            jj--;
                            return next();
                        }
                        i = ii;
                        j = jj;
                        jj--;
                        return true;
                    } else {
                        jj--;
                        return next();
                    }
                }

                if (!hoch) {
                    if (!mask.isReserved(ii, jj)) {
                        i = ii;
                        j = jj;
                        jj--;
                        return true;
                    } else {
                        jj--;
                        return next();
                    }
                }
            }


            //links
            if (jj % 2 != 0) {

                if (hoch) {
                    if (!mask.isReserved(ii, jj)) {
                        i = ii;
                        j = jj;
                        ii--;
                        jj++;
                        return true;
                    } else {
                        ii--;
                        jj++;
                        return next();
                    }
                }

                if (!hoch) {
                    if (!mask.isReserved(ii, jj)) {
                        i = ii;
                        j = jj;
                        ii++;
                        jj++;
                        return true;
                    } else {
                        ii++;
                        jj++;
                        return next();
                    }
                }
            }
        }


        if (jj < 6) {

            //rechts
            if (jj % 2 != 0) {

                if (hoch) {
                    if (!mask.isReserved(ii, jj)) {
                        i = ii;
                        j = jj;
                        jj--;
                        return true;
                    } else {
                        jj--;
                        return next();
                    }
                }

                if (!hoch) {
                    if (!mask.isReserved(ii, jj)) {
                        i = ii;
                        j = jj;
                        jj--;
                        return true;
                    } else {
                        jj--;
                        return next();
                    }
                }
            }


            //links
            if (jj % 2 == 0) {

                if (hoch) {
                    if (!mask.isReserved(ii, jj)) {

                        i = ii;
                        j = jj;
                        ii--;
                        jj++;
                        return true;
                    } else {
                        ii--;
                        jj++;
                        return next();
                    }
                }

                if (!hoch) {
                    if (!mask.isReserved(ii, jj)) {
                        i = ii;
                        j = jj;
                        ii++;
                        jj++;
                        return true;
                    } else {
                        ii++;
                        jj++;
                        return next();
                    }
                }
            }
        }

        return false;
    }
}

