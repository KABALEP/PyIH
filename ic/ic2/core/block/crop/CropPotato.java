/*    */ package ic2.core.block.crop;
/*    */ 
/*    */ import ic2.api.crops.ICropTile;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class CropPotato
/*    */   extends CropSeedFood
/*    */ {
/*    */   public CropPotato() {
/* 12 */     super("Potato", 51, "Yellow", new ItemStack(Items.field_151174_bG));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 18 */     return ((crop.getWorld()).field_73012_v.nextInt(50) == 0) ? new ItemStack(Items.field_151170_bI) : super.getGain(crop);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropPotato.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */