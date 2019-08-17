package nomadictents.structure.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nomadictents.block.BlockTentDoorHGM;
import nomadictents.block.BlockTentDoorSML;
import nomadictents.block.TileEntityTentDoor;
import nomadictents.init.Content;
import nomadictents.item.ItemTent;
import nomadictents.structure.StructureBase;

public class StructureData implements net.minecraftforge.common.util.INBTSerializable<CompoundNBT> {
	
	////// String keys for NBT //////
	public static final String KEY_TENT_CUR = "TentType";
	//public static final String KEY_WIDTH_PREV = "WidthPrev";
	public static final String KEY_WIDTH_CUR = "WidthCur";
	//public static final String KEY_DEPTH_PREV = "DepthPrev";
	public static final String KEY_DEPTH_CUR = "DepthCur";
	public static final String KEY_ID = "ID";	
	// this one is only really used for Shamiana tent
	public static final String KEY_COLOR = "TentColor";
	
	////// Important fields with their default values //////
	private StructureTent tent = StructureTent.getById((byte)0);
	private StructureWidth width = StructureWidth.getById((byte)0);
	private StructureDepth depth = StructureDepth.getById((byte)0);
	//private StructureWidth prevWidth = StructureWidth.getById((byte)0);
	//private StructureDepth prevDepth = StructureDepth.getById((byte)0);
	private DyeColor color = DyeColor.WHITE;
	private long locationID = ItemTent.ERROR_TAG;
	
	public StructureData() {
		// empty constructor (uses defaults)
	}
	
	/** Deserializes the given NBT to set important fields **/
	public StructureData(final CompoundNBT nbt) {
		this();
		if(nbt != null) {
			this.deserializeNBT(nbt);
		}
	}
	
	/** Parses StructureData NBT information from the given stack, if it exists **/
	public StructureData(final ItemStack tentStack) {
		this(tentStack != null && !tentStack.isEmpty() && tentStack.getItem() instanceof ItemTent 
				? tentStack.getOrCreateChildTag(ItemTent.TENT_DATA) 
				: null);
	}
	
	/** @return the same StructureData object with the given values applied to both "Current" and "Previous" Values **/
	public StructureData setAll(final StructureTent tentIn, final StructureWidth widthIn, final StructureDepth depthIn) {
		this.setCurrent(tentIn, widthIn, depthIn);
		//this.setPrev(widthIn, depthIn);
		return this;
	}
	
	/** @return the same StructureData with the given values used to set "Current" Values **/
	public StructureData setCurrent(final StructureTent tentCur, final StructureWidth widthCur, final StructureDepth depthCur) {
		this.tent = tentCur;
		this.width = widthCur;
		this.depth = depthCur;
		return this;
	}
	
	/** @return the same StructureData with the given valued used to set "Previous" Values **/
//	public StructureData setPrev(final StructureWidth widthPrev, final StructureDepth depthPrev) {
//		this.prevWidth = widthPrev;
//		this.prevDepth = depthPrev;
//		return this;
//	}
	
	/** @return a StructureData object that uses this object's "Previous" values for its "Current" ones **/
//	public StructureData prevData() {
//		return new StructureData()
//				.setCurrent(tent, prevWidth, prevDepth)
//				.setPrev(prevWidth, prevDepth)
//				.setID(locationID)
//				.setColor(color);
//	}
	
	/** @return an exact copy of this StructureData that is NOT the original **/
	public StructureData copy() {
		return new StructureData()
				.setCurrent(tent, width, depth)
				//.setPrev(prevWidth, prevDepth)
				.setID(locationID)
				.setColor(color);
	}
	
	/** @return true if this object has valid X and Z coordinates **/
	public boolean isValid() {
		return locationID != ItemTent.ERROR_TAG;
	}
 	
	//////////////////////////////////
	////// GETTERS AND SETTERS ///////
	//////////////////////////////////

	/** Set the StructureTent type used by this StructureData **/
	public StructureData setTent(final StructureTent tentIn) {
		this.tent = tentIn;
		return this;
	}

	/** Set or update the current Width used by this StructureData **/
	public StructureData setWidth(final StructureWidth widthIn) {
		this.width = widthIn;
		return this;
	}
	
	/** Set or update the current Depth used by this StructureData **/
	public StructureData setDepth(final StructureDepth depthIn) {
		this.depth = depthIn;
		return this;
	}

	/** Set or update the previous Width used by this StructureData **/
//	public StructureData setPrevWidth(final StructureWidth widthIn) {
//		this.prevWidth = widthIn;
//		return this;
//	}
	
	/** Set or update the previous Depth used by this StructureData **/
//	public StructureData setPrevDepth(final StructureDepth depthIn) {
//		this.prevDepth = depthIn;
//		return this;
//	}
	
	/** Set or update the location ID of this StructureData **/
	public StructureData setID(final long id) {
		this.locationID = id;
		return this;
	}
	
	/** Set the color of this tent. **/
	public StructureData setColor(final DyeColor colorIn) {
		this.color = colorIn;
		return this;
	}
	
	/** @return the Tent type represented by this StructureData **/
	public StructureTent getTent() {
		return this.tent;
	}

	/** @return the current Width of this StructureData **/
	public StructureWidth getWidth() {
		return this.width;
	}
	
	/** @return the current Depth of this StructureData **/
	public StructureDepth getDepth() {
		return this.depth;
	}
	
	/** @return the previous Width of this StructureData (may be same as current) **/
//	public StructureWidth getPrevWidth() {
//		return this.prevWidth;
//	}
	
	/** @return the previous Depth of this StructureData (may be same as current) **/
//	public StructureDepth getPrevDepth() {
//		return this.prevDepth;
//	}
	
	/**
	 * @return the Location ID of this tent
	 **/
	public long getID() {
		return locationID;
	}
	
	/** @return the color data stored by this door. Defaults to WHITE **/
	public DyeColor getColor() {
		return this.color;
	}
	
	//////////////////////////////////
	//////// STRUCTURE BLOCKS ////////
	//////////////////////////////////
	
	/** @return the Tent Door instance used for this structure **/
	public BlockState getDoorBlock() {
		final boolean xl = this.getWidth().isXL();
		final Block block = getDoorBlockRaw(xl);
		final EnumProperty<StructureWidth> sizeEnum = xl ? BlockTentDoorHGM.SIZE_HGM :  BlockTentDoorSML.SIZE_SML;
		return block.getDefaultState().with(sizeEnum, this.getWidth());
	}
	
	/** @return the correct BlockTentDoor for this structure's Size and Tent type **/
	private Block getDoorBlockRaw(boolean isXL) {
		switch (this.getTent()) {
		case YURT:		return isXL ? Content.YURT_DOOR_HGM : Content.YURT_DOOR_SML;
		case TEPEE:		return isXL ? Content.TEPEE_DOOR_HGM : Content.TEPEE_DOOR_SML;
		case BEDOUIN:	return isXL ? Content.BEDOUIN_DOOR_HGM : Content.BEDOUIN_DOOR_SML;
		case INDLU:		return isXL ? Content.INDLU_DOOR_HGM : Content.INDLU_DOOR_SML;
		case SHAMIANA:	return isXL ? Content.SHAMIANA_DOOR_HGM : Content.SHAMIANA_DOOR_SML;
		}
		return Content.YURT_DOOR_SML;
	}
	
	/** @return the specific Roof block for this tent type **/
	public BlockState getRoofBlock(final boolean isInsideTent) {
		return this.tent.getRoofBlock(isInsideTent);
	}

	/** @return the specific Frame for this structure type. May be different for walls and roofs **/
	public BlockState getFrameBlock(final boolean isRoof) {
		return this.tent.getFrameBlock(isRoof);
	}
	
	/** @return the main building block for this tent type. May be different inside tent. **/
	public BlockState getWallBlock(final boolean isInsideTent) {
		return this.tent.getWallBlock(isInsideTent);
	}

	///////////////////////////////
	/////// OTHER HELPFUL /////////
	///////////////////////////////
	
	/** Sets "Previous" values to be equal to "Current" ones. Usually means tent structure was fully updated. **/
//	public void resetPrevData() {
//		this.prevWidth = this.width;
//		this.prevDepth = this.depth;
//	}
	
	
//	public boolean needsUpdate(final StructureData oldData) {
//		return needsUpdateDepth(oldData) && needsUpdateWidth(oldData);
//	}
//	
//	public boolean needsUpdateDepth(final StructureData oldData) {
//		return depth != oldData.getDepth();
//	}
//	
//	public boolean needsUpdateWidth(final StructureData oldData) {
//		return width != oldData.getWidth();
//	}
//	
//	public boolean needsUpdateColor(final StructureData oldData) {
//		return color != oldData.getColor();
//	}
	
	/** 
	 * @param oldData the data stored in the Tent Dimension door
	 * @param newData the data passed by the tent ItemStack and TentTeleporter
	 * @return whether the StructureBase should re-generate based on changed values.
	 * @see StructureBase#generateInTentDimension(int, World, BlockPos, double, double, double, float)
	 **/
	public static boolean shouldUpdate(final StructureData oldData, final StructureData newData) {
		return oldData.getWidth() != newData.getWidth() || oldData.getDepth() != newData.getDepth()
				|| oldData.getColor() != newData.getColor();
	}
	
	/** Uses internal fields and Player location to update the given TileEntityTentDoor, including Owner if enabled */
	public static void applyToTileEntity(final PlayerEntity player, final ItemStack stack, final TileEntityTentDoor te) {
		if (stack.getTag() == null || !stack.getTag().contains(ItemTent.TENT_DATA)) {
			System.out.println("[StructureType] ItemStack did not have any NBT information to pass to the TileEntity!");
			te.getWorld().removeTileEntity(te.getPos());
			return;
		}
		te.setTentData(new StructureData(stack));
		te.setOverworldXYZ(player.posX, player.posY, player.posZ);
		te.setPrevFacing(player.rotationYaw);
		te.setOwner(PlayerEntity.getOfflineUUID(player.getName().getUnformattedComponentText()));
	}

	/** @return an NBT-tagged Tent ItemStack that represents this StructureData **/
	public ItemStack getDropStack() {
		return writeTo(new ItemStack(Content.ITEM_TENT, 1));
	}
	
	/** @return the same ItemStack with this object's info in its NBT data **/
	public ItemStack writeTo(final ItemStack stack) {
		stack.getOrCreateTag().put(ItemTent.TENT_DATA, this.serializeNBT());
		return stack;
	}
	
	/** Note: the returned StructureBase only contains a COPY of this StructureData **/
//	public StructureBase makePrevStructure() {
//		return this.tent.makeStructure(this.prevData().copy());
//	}
	
	/** Note: the returned StructureBase only contains a COPY of this StructureData **/
	public StructureBase getStructure() {
		return this.tent.makeStructure(this.copy());
	}
	
	/** Unused **/
	public static StructureData getRandom(final Random rand) {
		final StructureTent t = StructureTent.values()[rand.nextInt(StructureTent.values().length)];
		final StructureWidth w = StructureWidth.values()[rand.nextInt(StructureWidth.values().length)];
		final StructureDepth d = StructureDepth.values()[rand.nextInt(StructureDepth.values().length)];
		return new StructureData().setAll(t, w, d);
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		// only write if non-null
		if(this.tent != null) {
			// 'Current' values
			nbt.putByte(KEY_TENT_CUR, this.tent.getId());
			nbt.putByte(KEY_WIDTH_CUR, this.width.getId());
			nbt.putByte(KEY_DEPTH_CUR, this.depth.getId());
			// 'Previous' values
			//nbt.setByte(KEY_WIDTH_PREV, this.prevWidth.getId());
			//nbt.setByte(KEY_DEPTH_PREV, this.prevDepth.getId());
			// Location ID
			nbt.putLong(KEY_ID, locationID);
			// Color (optional)
			nbt.putInt(KEY_COLOR, this.color.getId());
		}
		
		return nbt;
	}

	@Override
	public void deserializeNBT(final CompoundNBT nbt) {
		this.tent = StructureTent.getById(nbt.getByte(KEY_TENT_CUR));
		this.width = StructureWidth.getById(nbt.getByte(KEY_WIDTH_CUR));
		this.depth = StructureDepth.getById(nbt.getByte(KEY_DEPTH_CUR));
		//this.prevWidth = StructureWidth.getById(nbt.getByte(KEY_WIDTH_PREV));
		//this.prevDepth = StructureDepth.getById(nbt.getByte(KEY_DEPTH_PREV));
		this.locationID = nbt.getLong(KEY_ID);
		this.color = nbt.contains(KEY_COLOR) ? DyeColor.byId(nbt.getInt(KEY_COLOR)) : DyeColor.WHITE;
	}
	
	@Override
	public String toString() {
		return "\nStructureData: [TENT = " + tent.getName() + "; WIDTH = " 
				+ width.getName() + "; DEPTH = " + depth.getName() + 
				";\nPREV_WIDTH = " //+ prevWidth.getName() + "; PREV_DEPTH = " 
				//+ prevDepth.getName() 
				+ "; ID = " + locationID + "; COLOR = " 
				+ (color != null ? color.getName() : "null") + "]";
	}
}