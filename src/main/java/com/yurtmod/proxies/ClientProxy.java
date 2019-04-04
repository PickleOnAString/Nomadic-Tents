package com.yurtmod.proxies;

import com.yurtmod.block.BlockTepeeWall;
import com.yurtmod.init.Content;
import com.yurtmod.init.NomadicTents;
import com.yurtmod.structure.StructureType;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenders(ModelRegistryEvent event) {
		// register variant renders
		int len = StructureType.values().length;
		ResourceLocation[] RLTentItem = new ResourceLocation[len];
		ResourceLocation[] RLTepeeWall = new ResourceLocation[BlockTepeeWall.NUM_TEXTURES];
		for (int i = 0; i < len; i++) {
			String modelName = NomadicTents.MODID + ":" + StructureType.getName(i);
			RLTentItem[i] = new ResourceLocation(modelName);
			register(Content.ITEM_TENT, modelName, i);
		}
		for (int j = 0; j < BlockTepeeWall.NUM_TEXTURES; j++) {
			String modelName = Content.IB_TEPEE_WALL.getRegistryName() + "_" + j;
			RLTepeeWall[j] = new ResourceLocation(modelName);
			register(Content.IB_TEPEE_WALL, modelName, j);
		}
		ModelBakery.registerItemVariants(Content.ITEM_TENT, RLTentItem);
		ModelBakery.registerItemVariants(Content.IB_TEPEE_WALL, RLTepeeWall);

		// register items
		register(Content.ITEM_TENT_CANVAS);
		register(Content.ITEM_YURT_WALL);
		register(Content.ITEM_TEPEE_WALL);
		register(Content.ITEM_BEDOUIN_WALL);
		register(Content.ITEM_INDLU_WALL);
		register(Content.ITEM_MALLET);
		register(Content.ITEM_SUPER_MALLET);
		register(Content.ITEM_UPGRADE_GOLD);
		register(Content.ITEM_UPGRADE_OBSDIDIAN);
		register(Content.ITEM_UPGRADE_DIAMOND);
		// register blocks
		register(Content.TENT_BARRIER);
		register(Content.SUPER_DIRT);
		//// yurt blocks
		register(Content.YURT_WALL_OUTER);
		register(Content.YURT_WALL_INNER);
		register(Content.YURT_ROOF);
		//// bedouin blocks
		register(Content.BEDOUIN_WALL);
		register(Content.BEDOUIN_ROOF);
		//// indlu blocks
		register(Content.INDLU_WALL_OUTER);
		register(Content.INDLU_WALL_INNER);
		//// door blocks
		register(Content.YURT_DOOR_SM);
		register(Content.YURT_DOOR_MED);
		register(Content.TEPEE_DOOR_SML);
		register(Content.TEPEE_DOOR_HGM);
		register(Content.BEDOUIN_DOOR_SML);
		register(Content.BEDOUIN_DOOR_HGM);
		register(Content.INDLU_DOOR_SML);
		register(Content.INDLU_DOOR_HGM);
		//// frame blocks
		int[] progress = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
		register(Content.FRAME_YURT_WALL, progress);
		register(Content.FRAME_YURT_ROOF, progress);
		register(Content.FRAME_TEPEE_WALL, progress);
		register(Content.FRAME_BEDOUIN_WALL, progress);
		register(Content.FRAME_BEDOUIN_ROOF, progress);
		register(Content.FRAME_INDLU_WALL, progress);
	}

	private static void register(Item i, String name, int... meta) {
		if (meta.length < 1)
			meta = new int[] { 0 };
		for (int m : meta) {
			ModelLoader.setCustomModelResourceLocation(i, m, new ModelResourceLocation(name, "inventory"));
		}
	}

	private static void register(Item i, int... meta) {
		register(i, i.getRegistryName().toString(), meta);
	}

	private static void register(Block b, int... meta) {
		Item i = Item.getItemFromBlock(b);
		if (i != null) {
			register(i, meta);
		} else {
			System.out.println("[NomadicTents.ClientProxy] Tried to register render for a null ItemBlock. Skipping.");
		}
	}
}
