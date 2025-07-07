/*    */ package ic2.neiIntegration.core;
/*    */ 
/*    */ import codechicken.nei.VisiblityData;
/*    */ import codechicken.nei.api.INEIGuiAdapter;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiHandlerReactorPlanner
/*    */   extends INEIGuiAdapter
/*    */ {
/*    */   public VisiblityData modifyVisiblity(GuiContainer gui, VisiblityData currentVisibility) {
/* 14 */     if (gui instanceof ic2.core.block.machine.gui.GuiReactorPlanner)
/*    */     {
/* 16 */       currentVisibility.showNEI = false;
/*    */     }
/* 18 */     return currentVisibility;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\neiIntegration\core\GuiHandlerReactorPlanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */