/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.item.IHandHeldInventory;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemCropnalyzer
/*    */   extends ItemIC2
/*    */   implements IHandHeldInventory {
/*    */   public ItemCropnalyzer(int index) {
/* 21 */     super(index);
/* 22 */     func_77625_d(1);
/* 23 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/* 24 */     setSpriteID("i1");
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77659_a(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
/* 29 */     if (IC2.platform.isSimulating() && !entityPlayer.func_70093_af())
/*    */     {
/* 31 */       IC2.platform.launchGui(entityPlayer, getInventory(entityPlayer, itemStack));
/*    */     }
/* 33 */     return itemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 46 */     return EnumRarity.uncommon;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IHasGui getInventory(EntityPlayer entityPlayer, ItemStack itemStack) {
/* 52 */     return new HandHeldCropnalyzer(entityPlayer, itemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
/* 57 */     if (item != null && player.field_71070_bA instanceof ContainerCropnalyzer) {
/*    */       
/* 59 */       HandHeldCropnalyzer cropnalyzer = ((ContainerCropnalyzer)player.field_71070_bA).cropnalyzer;
/* 60 */       NBTTagCompound nbtTagCompound = StackUtil.getOrCreateNbtData(item);
/* 61 */       if (cropnalyzer.matchesUid(nbtTagCompound.func_74762_e("uid")))
/*    */       {
/* 63 */         player.func_71053_j();
/*    */       }
/*    */     } 
/* 66 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemCropnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */