package com.yurtmod.structure.util;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

public enum StructureDepth implements IStringSerializable {
	
	NORMAL(1),
	DOUBLE(2),
	TRIPLE(3),
	QUADRUPLE(4),
	QUINTUPLE(5),
	SEXTUPLE(6);
	
	private static final int NUM_ENTRIES = values().length;
	
	private final int layers;
	
	StructureDepth(final int i) {
		this.layers = i;
	}
	
	/** @return the number of tent floor filler layers represented by this StructureDepth **/
	public int getLayers() {
		return this.layers;
	}

	/** @return A unique identifier. For now just the ordinal value **/
	public byte getId() {
		return (byte)this.ordinal();
	}

	/** @return The StructureDepth that uses this ID **/
	public static StructureDepth getById(final byte id) {
		return values()[MathHelper.clamp(id, 0, NUM_ENTRIES - 1)];
	}

	/** @return the maximum number of upgrades the given tent can hold **/
	public static int maxUpgrades(final StructureData data) {
		return data.getWidth().getMaxDepth() - 1;
	}
	
	/** @return the number of depth upgrades the given tent has received **/
	public static int countUpgrades(final StructureData data) {
		return data.getDepth().ordinal();
	}

	public static StructureDepth getByName(final String name) {
		for(final StructureDepth d : values()) {
			if(name.equals(d.getName())) {
				return d;
			}
		}
		return NORMAL;
	}
	
	@Override
	public String getName() {
		return this.toString().toLowerCase();
	}
}