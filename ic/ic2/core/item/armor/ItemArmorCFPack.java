/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemArmorCFPack
/*    */   extends ItemArmorUtility
/*    */ {
/*    */   public ItemArmorCFPack(int index, int armorrendering) {
/* 10 */     super(index, armorrendering, 1);
/* 11 */     func_77656_e(260);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getCFPellet(EntityPlayer player, ItemStack pack) {
/* 16 */     if (pack.func_77960_j() < pack.func_77958_k() - 1) {
/*    */       
/* 18 */       pack.func_77964_b(pack.func_77960_j() + 1);
/* 19 */       return true;
/*    */     } 
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRepairable() {
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTextureName() {
/* 33 */     return "batpack";
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\ItemArmorCFPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */