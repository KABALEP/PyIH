/*    */ package ic2.api.event.recipe;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElectrolyzerRecipeEvent
/*    */   extends RecipeEvent
/*    */ {
/*    */   public final int energy;
/*    */   public final boolean charge;
/*    */   public final boolean discharge;
/*    */   
/*    */   public ElectrolyzerRecipeEvent(ItemStack par1, ItemStack par2, int par3, boolean par4, boolean par5) {
/* 31 */     super(par1, par2);
/* 32 */     this.energy = par3;
/* 33 */     this.charge = par4;
/* 34 */     this.discharge = par5;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\event\recipe\ElectrolyzerRecipeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */