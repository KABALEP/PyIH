/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemElectricToolDDrill
/*    */   extends ItemElectricToolDrill {
/*    */   public ItemElectricToolDDrill(int sprite) {
/* 10 */     super(sprite, Item.ToolMaterial.EMERALD, 80);
/* 11 */     this.maxCharge = 10000;
/* 12 */     this.transferLimit = 100;
/* 13 */     this.tier = 1;
/* 14 */     this.field_77864_a = 16.0F;
/* 15 */     setHarvestLevel("shovel", 3);
/* 16 */     setHarvestLevel("pickaxe", 3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 22 */     super.init();
/* 23 */     this.mineableBlocks.add(Blocks.field_150343_Z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBasicDrill(ItemStack drill) {
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExtraSpeed(ItemStack drill) {
/* 35 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExtraEnergyCost(ItemStack drill) {
/* 41 */     return 14;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemElectricToolDDrill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */