/*     */ package ic2.core.item.upgrades;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.Direction;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BaseMetaUpgrade
/*     */   implements IUpgradeMetaItem
/*     */ {
/*     */   public int getExtraProcessTime(ItemStack upgrade, IMachine machine) {
/*  28 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getProcessTimeMultiplier(ItemStack upgrade, IMachine machine) {
/*  34 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyDemand(ItemStack upgrade, IMachine machine) {
/*  40 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyDemandMultiplier(ItemStack upgrade, IMachine machine) {
/*  46 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraEnergyStorage(ItemStack upgrade, IMachine machine) {
/*  52 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyStorageMultiplier(ItemStack upgrade, IMachine machine) {
/*  58 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtraTier(ItemStack upgrade, IMachine machine) {
/*  64 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTick(ItemStack upgrade, IMachine machine) {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsTick() {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onProcessEnd(ItemStack upgrade, IMachine machine, List<ItemStack> results) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useRedstoneinverter(ItemStack upgrade, IMachine machine) {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInstalling(ItemStack upgrade, IMachine machine) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSoundVolumeMultiplier(ItemStack upgrade, IMachine machine) {
/* 100 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesOwner() {
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesDirection() {
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMachine.UpgradeType getType() {
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getTexture() {
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getDirection(ItemStack par1) {
/* 135 */     return Direction.fromForgeDirection(getDir(par1));
/*     */   }
/*     */ 
/*     */   
/*     */   public ForgeDirection getDir(ItemStack par1) {
/* 140 */     if (par1 == null)
/*     */     {
/* 142 */       return ForgeDirection.UNKNOWN;
/*     */     }
/* 144 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 145 */     if (!nbt.func_74764_b("UpgradeData"))
/*     */     {
/* 147 */       return ForgeDirection.UNKNOWN;
/*     */     }
/* 149 */     NBTTagCompound data = nbt.func_74775_l("UpgradeData");
/* 150 */     return ForgeDirection.getOrientation(data.func_74762_e("Direction"));
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getOwner(ItemStack par1) {
/* 155 */     if (par1 == null)
/*     */     {
/* 157 */       return null;
/*     */     }
/* 159 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(par1);
/* 160 */     if (!nbt.func_74764_b("UpgradeData"))
/*     */     {
/* 162 */       return null;
/*     */     }
/* 164 */     NBTTagCompound data = nbt.func_74775_l("UpgradeData");
/* 165 */     if (data.func_74764_b("PlayerMost") && data.func_74764_b("PlayerLeast"))
/*     */     {
/* 167 */       return new UUID(data.func_74763_f("PlayerMost"), data.func_74763_f("PlayerLeast"));
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getWorldTime(IMachine par1) {
/* 174 */     return ((TileEntity)par1).func_145831_w().func_82737_E();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 180 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addInfo(ItemStack par1, EntityPlayer par2, List<String> par3) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean usesExtraRightClick(ItemStack item) {
/* 193 */     return false;
/*     */   }
/*     */   
/*     */   public void onRightClick(ItemStack item, World world, EntityPlayer player) {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ite\\upgrades\BaseMetaUpgrade.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */