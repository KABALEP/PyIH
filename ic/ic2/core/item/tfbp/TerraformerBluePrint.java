/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.api.item.ITerraformerBP;
/*    */ import ic2.api.item.ITerraformingBP;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class TerraformerBluePrint
/*    */   implements ITerraformerBP
/*    */ {
/*    */   ITerraformingBP bp;
/*    */   
/*    */   public TerraformerBluePrint(ITerraformingBP bluePrint) {
/* 14 */     this.bp = bluePrint;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getConsume(ItemStack item) {
/* 20 */     return this.bp.getConsume();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRange(ItemStack item) {
/* 26 */     return this.bp.getRange();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean terraform(ItemStack item, World world, int x, int z, int yCoord) {
/* 32 */     return this.bp.terraform(world, x, z, yCoord);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tfbp\TerraformerBluePrint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */