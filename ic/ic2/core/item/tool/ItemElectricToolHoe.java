/*    */ package ic2.core.item.tool;
/*    */ import cpw.mods.fml.common.eventhandler.Event;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IElectricTool;
/*    */ import ic2.core.IC2;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ import net.minecraft.enchantment.EnumEnchantmentType;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.event.entity.player.UseHoeEvent;
/*    */ 
/*    */ public class ItemElectricToolHoe extends ItemElectricTool implements IElectricTool {
/*    */   public ItemElectricToolHoe(int sprite) {
/* 20 */     super(sprite, Item.ToolMaterial.IRON, 50);
/* 21 */     this.maxCharge = 10000;
/* 22 */     this.transferLimit = 100;
/* 23 */     this.tier = 1;
/* 24 */     this.field_77864_a = 16.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 29 */     this.mineableBlocks.add(Blocks.field_150346_d);
/* 30 */     this.mineableBlocks.add(Blocks.field_150349_c);
/* 31 */     this.mineableBlocks.add(Blocks.field_150391_bh);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer entityliving) {
/* 36 */     ElectricItem.manager.use(itemstack, this.operationEnergyCost, (EntityLivingBase)entityliving);
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
/* 43 */     if (!entityplayer.func_82246_f(i, j, k))
/*    */     {
/* 45 */       return false;
/*    */     }
/* 47 */     if (!ElectricItem.manager.use(itemstack, this.operationEnergyCost, (EntityLivingBase)entityplayer))
/*    */     {
/* 49 */       return false;
/*    */     }
/* 51 */     if (MinecraftForge.EVENT_BUS.post((Event)new UseHoeEvent(entityplayer, itemstack, world, i, j, k)))
/*    */     {
/* 53 */       return true;
/*    */     }
/* 55 */     Block i2 = world.func_147439_a(i, j, k);
/* 56 */     Block j2 = world.func_147439_a(i, j + 1, k);
/* 57 */     if ((l == 0 || j2 != Blocks.field_150350_a || i2 != Blocks.field_150349_c) && i2 != Blocks.field_150346_d)
/*    */     {
/* 59 */       return false;
/*    */     }
/* 61 */     Block block = Blocks.field_150458_ak;
/* 62 */     world.func_72908_a((i + 0.5F), (j + 0.5F), (k + 0.5F), block.field_149762_H.field_150501_a, (block.field_149762_H.func_150497_c() + 1.0F) / 2.0F, block.field_149762_H.func_150494_d() * 0.8F);
/* 63 */     if (!IC2.platform.isSimulating())
/*    */     {
/* 65 */       return true;
/*    */     }
/* 67 */     world.func_147449_b(i, j, k, block);
/* 68 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumEnchantmentType getType(ItemStack item) {
/* 74 */     return EnumEnchantmentType.breakable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSpecialSupport(ItemStack item, Enchantment ench) {
/* 80 */     if (ench == Enchantment.field_77346_s)
/*    */     {
/* 82 */       return true;
/*    */     }
/* 84 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isExcluded(ItemStack item, Enchantment ench) {
/* 90 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemElectricToolHoe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */