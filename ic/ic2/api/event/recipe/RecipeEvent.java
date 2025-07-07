/*    */ package ic2.api.event.recipe;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.Cancelable;
/*    */ import cpw.mods.fml.common.eventhandler.Event;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Cancelable
/*    */ public class RecipeEvent
/*    */   extends Event
/*    */ {
/*    */   final ItemStack input;
/*    */   final ItemStack output;
/*    */   
/*    */   public RecipeEvent(ItemStack input, ItemStack output) {
/* 22 */     this.input = input;
/* 23 */     this.output = output;
/*    */   }
/*    */ 
/*    */   
/*    */   public final ItemStack getInput() {
/* 28 */     return this.input.func_77946_l();
/*    */   }
/*    */ 
/*    */   
/*    */   public final ItemStack getOutput() {
/* 33 */     return this.output.func_77946_l();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\event\recipe\RecipeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */