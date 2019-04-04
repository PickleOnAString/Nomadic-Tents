package com.yurtmod.proxies;

import com.yurtmod.block.BlockBarrier;
import com.yurtmod.block.BlockBedouinRoof;
import com.yurtmod.block.BlockBedouinWall;
import com.yurtmod.block.BlockIndluWall;
import com.yurtmod.block.BlockTentDoorHGM;
import com.yurtmod.block.BlockTentDoorSML;
import com.yurtmod.block.BlockTentFrame;
import com.yurtmod.block.BlockTentFrame.BlockToBecome;
import com.yurtmod.block.BlockTepeeWall;
import com.yurtmod.block.BlockUnbreakable;
import com.yurtmod.block.BlockYurtRoof;
import com.yurtmod.block.BlockYurtWall;
import com.yurtmod.crafting.RecipeManager;
import com.yurtmod.dimension.BiomeTent;
import com.yurtmod.dimension.TentDimension;
import com.yurtmod.init.Content;
import com.yurtmod.init.NomadicTents;
import com.yurtmod.item.IBTepeeWall;
import com.yurtmod.item.ItemMallet;
import com.yurtmod.item.ItemSuperMallet;
import com.yurtmod.item.ItemTent;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;

public class CommonProxy {
	
	public void registerRenders(ModelRegistryEvent event) { }

	public void registerRecipes(final RegistryEvent.Register<IRecipe> event) {
		RecipeManager.mainRegistry(event);
	}

	public void registerBiome(final RegistryEvent.Register<Biome> event) {
		event.getRegistry()
				.register(new BiomeTent().setRegistryName(NomadicTents.MODID, TentDimension.BIOME_TENT_NAME));
	}

	public void registerBlocks(final RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(

				// helpful blocks
				new BlockBarrier().setRegistryName(NomadicTents.MODID, "tentmod_barrier")
						.setUnlocalizedName("tentmod_barrier").setCreativeTab(NomadicTents.TAB),
				new BlockUnbreakable(Material.GROUND).setRegistryName(NomadicTents.MODID, "super_dirt")
						.setUnlocalizedName("super_dirt").setCreativeTab(NomadicTents.TAB),

				// wall and roof blocks
				new BlockYurtWall().setRegistryName(NomadicTents.MODID, "yurt_wall_outer")
						.setUnlocalizedName("yurt_wall_outer").setCreativeTab(NomadicTents.TAB),
				new BlockYurtWall().setRegistryName(NomadicTents.MODID, "yurt_wall_inner")
						.setUnlocalizedName("yurt_wall_inner").setCreativeTab(NomadicTents.TAB),
				new BlockYurtRoof().setRegistryName(NomadicTents.MODID, "yurt_roof").setUnlocalizedName("yurt_roof")
						.setCreativeTab(NomadicTents.TAB),
				new BlockTepeeWall().setRegistryName(NomadicTents.MODID, "tepee_wall").setUnlocalizedName("tepee_wall")
						.setCreativeTab(NomadicTents.TAB),
				new BlockBedouinWall().setRegistryName(NomadicTents.MODID, "bed_wall").setUnlocalizedName("bed_wall")
						.setCreativeTab(NomadicTents.TAB),
				new BlockBedouinRoof().setRegistryName(NomadicTents.MODID, "bed_roof").setUnlocalizedName("bed_roof")
						.setCreativeTab(NomadicTents.TAB),
				new BlockIndluWall().setRegistryName(NomadicTents.MODID, "indlu_wall_outer").setUnlocalizedName("indlu_wall_outer")
						.setCreativeTab(NomadicTents.TAB),
				new BlockIndluWall().setRegistryName(NomadicTents.MODID, "indlu_wall_inner").setUnlocalizedName("indlu_wall_inner")
						.setCreativeTab(NomadicTents.TAB),

				// door blocks
				new BlockTentDoorSML().setRegistryName(NomadicTents.MODID, "yurt_door_0").setUnlocalizedName("yurt_door_0"),
				new BlockTentDoorHGM().setRegistryName(NomadicTents.MODID, "yurt_door_1").setUnlocalizedName("yurt_door_1"),
				new BlockTentDoorSML().setRegistryName(NomadicTents.MODID, "tepee_door_0").setUnlocalizedName("tepee_door_0"),
				new BlockTentDoorHGM().setRegistryName(NomadicTents.MODID, "tepee_door_1").setUnlocalizedName("tepee_door_1"),
				new BlockTentDoorSML().setRegistryName(NomadicTents.MODID, "bed_door_0").setUnlocalizedName("bed_door_0"),
				new BlockTentDoorHGM().setRegistryName(NomadicTents.MODID, "bed_door_1").setUnlocalizedName("bed_door_1"),
				new BlockTentDoorSML().setRegistryName(NomadicTents.MODID, "indlu_door_0").setUnlocalizedName("indlu_door_0"),
				new BlockTentDoorHGM().setRegistryName(NomadicTents.MODID, "indlu_door_1").setUnlocalizedName("indlu_door_1"),

				// frame blocks
				new BlockTentFrame(BlockToBecome.YURT_WALL_OUTER).setRegistryName(NomadicTents.MODID, "frame_yurt_wall")
						.setUnlocalizedName("frame_yurt_wall"),
				new BlockTentFrame(BlockToBecome.YURT_ROOF).setRegistryName(NomadicTents.MODID, "frame_yurt_roof")
						.setUnlocalizedName("frame_yurt_roof"),
				new BlockTentFrame(BlockToBecome.TEPEE_WALL).setRegistryName(NomadicTents.MODID, "frame_tepee_wall")
						.setUnlocalizedName("frame_tepee_wall"),
				new BlockTentFrame(BlockToBecome.BEDOUIN_WALL).setRegistryName(NomadicTents.MODID, "frame_bed_wall")
						.setUnlocalizedName("frame_bed_wall"),
				new BlockTentFrame(BlockToBecome.BEDOUIN_ROOF).setRegistryName(NomadicTents.MODID, "frame_bed_roof")
						.setUnlocalizedName("frame_bed_roof"),
				new BlockTentFrame(BlockToBecome.INDLU_WALL).setRegistryName(NomadicTents.MODID, "frame_indlu_wall")
						.setUnlocalizedName("frame_indlu_wall"));
	}

	public void registerItems(final RegistryEvent.Register<Item> event) {
		// Item
		event.getRegistry().registerAll(
				// items
				new ItemTent().setCreativeTab(NomadicTents.TAB).setRegistryName(NomadicTents.MODID, "tent"),
				new ItemMallet(ToolMaterial.IRON).setCreativeTab(NomadicTents.TAB)
						.setRegistryName(NomadicTents.MODID, "mallet").setUnlocalizedName("mallet"),
				new ItemSuperMallet(ToolMaterial.DIAMOND).setCreativeTab(NomadicTents.TAB)
						.setRegistryName(NomadicTents.MODID, "super_mallet").setUnlocalizedName("super_mallet"),
				basicItem("tent_canvas"), basicItem("yurt_wall_piece"), basicItem("tepee_wall_piece"),
				basicItem("bed_wall_piece"), basicItem("indlu_wall_piece"), basicItem("tent_upgrade_gold"),
				basicItem("tent_upgrade_obsidian"), basicItem("tent_upgrade_diamond"),

				// ItemBlock
				makeIB(Content.TENT_BARRIER), makeIB(Content.SUPER_DIRT), makeIB(Content.YURT_WALL_INNER),
				makeIB(Content.YURT_WALL_OUTER), makeIB(Content.YURT_ROOF),
				new IBTepeeWall(Content.TEPEE_WALL).setRegistryName(Content.TEPEE_WALL.getRegistryName()),
				makeIB(Content.BEDOUIN_WALL), makeIB(Content.BEDOUIN_ROOF),
				makeIB(Content.INDLU_WALL_OUTER), makeIB(Content.INDLU_WALL_INNER)
				/* , makeIB(Content.BEDOUIN_DOOR_LARGE),
				 * makeIB(Content.BEDOUIN_DOOR_MEDIUM), makeIB(Content.BEDOUIN_DOOR_SMALL),
				 * makeIB(Content.TEPEE_DOOR_LARGE), makeIB(Content.TEPEE_DOOR_MEDIUM),
				 * makeIB(Content.TEPEE_DOOR_SMALL), makeIB(Content.YURT_DOOR_LARGE),
				 * makeIB(Content.YURT_DOOR_MEDIUM), makeIB(Content.YURT_DOOR_SMALL)
				 */
		);
	}

	private static final Item basicItem(String name) {
		return new Item().setCreativeTab(NomadicTents.TAB).setRegistryName(NomadicTents.MODID, name)
				.setUnlocalizedName(name);
	}

	private static final ItemBlock makeIB(Block base) {
		ItemBlock ib = new ItemBlock(base);
		ib.setRegistryName(base.getRegistryName());
		return ib;
	}

}
