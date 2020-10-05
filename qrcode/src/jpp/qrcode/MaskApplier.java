package jpp.qrcode;

public class MaskApplier {
	public static void applyTo(boolean[][] data, MaskFunction mask, ReservedModulesMask reservedModulesMask) {
		if (data.length != reservedModulesMask.size()) {
			throw new IllegalArgumentException("Matrixgröße & ReservedModulesMaskgröße müssen gleich sein");
		}

		for (int zeile = 0; zeile < data.length; zeile++) {
			for (int spalte = 0; spalte < data.length; spalte++) {
				if (mask.mask(zeile, spalte) == true && reservedModulesMask.isReserved(zeile, spalte) == false) {
					if (data[zeile][spalte] == true) {
						data[zeile][spalte] = false;
					} else {
						data[zeile][spalte] = true;
					}
				}
			}
		}
	}
}
