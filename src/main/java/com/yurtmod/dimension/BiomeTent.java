package com.yurtmod.dimension;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeVoid;
import net.minecraft.world.biome.BiomeVoidDecorator;

public class BiomeTent extends Biome
{
	public BiomeTent() {
		super(new Biome.BiomeProperties("Tent"));
		this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.theBiomeDecorator = new BiomeVoidDecorator();
	}
}