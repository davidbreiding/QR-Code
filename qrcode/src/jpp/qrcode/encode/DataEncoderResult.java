package jpp.qrcode.encode;

import jpp.qrcode.Version;

public final class DataEncoderResult {

	byte[] bytes;
	Version version;

	public DataEncoderResult(byte[] bytes, Version version) {
		this.bytes = bytes;
		this.version = version;
	}
	
	public byte[] bytes() {
		return bytes;
	}
	
	public Version version() {
		return version;
	}
}
