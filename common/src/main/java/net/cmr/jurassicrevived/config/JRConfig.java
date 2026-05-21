package net.cmr.jurassicrevived.config;

public final class JRConfig {
	/**
	 * When false, machines do not require/consume power, machine GUIs hide their power bars,
	 * and energy pipes do not connect to machines. Generator and power cell behavior is unchanged.
	 */
	public boolean requirePower = false;

	/**
	 * Controls whether dinosaurs should naturally spawn.
	 * Entity spawning is not implemented yet, but future spawn code should read this.
	 */
	public boolean naturallySpawning = false;

	/**
	 * Energy pipe transfer rate in FE per second.
	 */
	public int fePerSecond = 1000;

	/**
	 * Item pipe transfer rate in items per second.
	 */
	public int itemsPerSecond = 100;

	/**
	 * Fluid pipe transfer rate in milliBuckets per second.
	 */
	public int milliBucketsPerSecond = 1000;

	public JRConfig() {
	}
}
