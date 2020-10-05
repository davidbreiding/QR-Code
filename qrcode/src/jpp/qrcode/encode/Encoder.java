package jpp.qrcode.encode;

import jpp.qrcode.*;
import jpp.qrcode.decode.DataDecoder;
import jpp.qrcode.decode.DataExtractor;

public class Encoder {
    public static QRCode createFromString(String msg, ErrorCorrection correction) {


        Version version = DataEncoder.encodeForCorrectionLevel(msg, correction).version();

        DataEncoderResult dataEncoderResult = new DataEncoderResult(DataEncoder.encodeForCorrectionLevel(msg, correction).bytes(), version);

        byte[] bytes = DataStructurer.structure(dataEncoderResult.bytes, dataEncoderResult.version().correctionInformationFor(correction));

        boolean[][] code = PatternPlacer.createBlankForVersion(dataEncoderResult.version());

        ReservedModulesMask mask = ReservedModulesMask.forVersion(dataEncoderResult.version());

        DataInserter.insert(code, mask, bytes);

        return new QRCode(code, version, MaskSelector.maskWithBestMask(code, correction, mask), correction);
    }
}