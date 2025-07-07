/*    */ package ic2.toolIntigration.core;
/*    */ 
/*    */ import appeng.api.implementations.items.IAEWrench;
/*    */ import ic2.api.item.IWrenchHandler;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class AEWrenchModul
/*    */   implements IWrenchHandler
/*    */ {
/*    */   public boolean canWrench(ItemStack item, int x, int y, int z, EntityPlayer player) {
/* 14 */     return ((IAEWrench)item.func_77973_b()).canWrench(item, player, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean supportsItem(ItemStack item) {
/* 20 */     return item.func_77973_b() instanceof IAEWrench;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void useWrench(ItemStack item, int x, int y, int z, EntityPlayer player) {
/* 26 */     item.func_77972_a(1, (EntityLivingBase)player);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\toolIntigration\core\AEWrenchModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */