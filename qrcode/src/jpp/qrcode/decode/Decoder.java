package jpp.qrcode.decode;

import jpp.qrcode.MaskApplier;
import jpp.qrcode.QRCode;
import jpp.qrcode.ReservedModulesMask;
import jpp.qrcode.Version;
import jpp.qrcode.reedsolomon.ReedSolomonException;

public class Decoder {
    public static String decodeToString(QRCode qrCode) throws ReedSolomonException {

        try {
            boolean[][] code = qrCode.data();
            MaskApplier.applyTo(code, qrCode.maskPattern().maskFunction(), ReservedModulesMask.forVersion(qrCode.version()));
            byte[] bytes = DataExtractor.extract(code, ReservedModulesMask.forVersion(qrCode.version()), qrCode.version().correctionInformationFor(qrCode.errorCorrection()).totalByteCount());
            return DataDecoder.decodeToString(DataDestructurer.destructure(bytes, Version.fromNumber(qrCode.version().number()).correctionInformationFor(qrCode.errorCorrection())), qrCode.version(), qrCode.errorCorrection());
        } catch (ReedSolomonException e) {
            throw new ReedSolomonException();
        }

    }
}
