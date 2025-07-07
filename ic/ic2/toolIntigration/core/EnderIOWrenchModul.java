/*    */ package ic2.toolIntigration.core;
/*    */ 
/*    */ import crazypants.enderio.api.tool.ITool;
/*    */ import ic2.api.item.IWrenchHandler;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnderIOWrenchModul
/*    */   implements IWrenchHandler
/*    */ {
/*    */   public boolean canWrench(ItemStack item, int x, int y, int z, EntityPlayer player) {
/* 14 */     return ((ITool)item.func_77973_b()).canUse(item, player, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean supportsItem(ItemStack item) {
/* 20 */     return item.func_77973_b() instanceof ITool;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void useWrench(ItemStack item, int x, int y, int z, EntityPlayer player) {
/* 26 */     ((ITool)item.func_77973_b()).used(item, player, x, y, z);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\toolIntigration\core\EnderIOWrenchModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */