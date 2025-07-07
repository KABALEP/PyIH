/*    */ package ic2.toolIntigration.core;
/*    */ 
/*    */ import buildcraft.api.tools.IToolWrench;
/*    */ import ic2.api.item.IWrenchHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BCWrenchModul
/*    */   implements IWrenchHandler
/*    */ {
/*    */   public boolean supportsItem(ItemStack possibleWrench) {
/* 14 */     return possibleWrench.func_77973_b() instanceof IToolWrench;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canWrench(ItemStack wrench, int x, int y, int z, EntityPlayer player) {
/* 20 */     return ((IToolWrench)wrench.func_77973_b()).canWrench(player, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void useWrench(ItemStack wrench, int x, int y, int z, EntityPlayer player) {
/* 26 */     ((IToolWrench)wrench.func_77973_b()).wrenchUsed(player, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\toolIntigration\core\BCWrenchModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */