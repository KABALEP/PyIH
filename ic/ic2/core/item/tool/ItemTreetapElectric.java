/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IElectricTool;
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnumEnchantmentType;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemTreetapElectric extends ItemElectricTool implements IElectricTool {
/*    */   public ItemTreetapElectric(int sprite) {
/* 17 */     super(sprite, Item.ToolMaterial.IRON, 50);
/* 18 */     this.maxCharge = 10000;
/* 19 */     this.transferLimit = 100;
/* 20 */     this.tier = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
/* 25 */     if (world.func_147439_a(i, j, k) != Block.func_149634_a(Ic2Items.rubberWood.func_77973_b()))
/*    */     {
/* 27 */       return false;
/*    */     }
/* 29 */     if (!ElectricItem.manager.use(itemstack, this.operationEnergyCost, (EntityLivingBase)entityplayer))
/*    */     {
/* 31 */       return false;
/*    */     }
/* 33 */     ItemTreetap.attemptExtract(entityplayer, world, i, j, k, l, null);
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumEnchantmentType getType(ItemStack item) {
/* 40 */     return EnumEnchantmentType.breakable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSpecialSupport(ItemStack item, Enchantment ench) {
/* 46 */     if (ench == Enchantment.field_77346_s)
/*    */     {
/* 48 */       return true;
/*    */     }
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isExcluded(ItemStack item, Enchantment ench) {
/* 56 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemTreetapElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */