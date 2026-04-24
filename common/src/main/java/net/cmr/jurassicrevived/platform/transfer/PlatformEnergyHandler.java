package net.cmr.jurassicrevived.platform.transfer;

public interface PlatformEnergyHandler {
	int extract(int amount, boolean simulate);
	int insert(int amount, boolean simulate);
}
