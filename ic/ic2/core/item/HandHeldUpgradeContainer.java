/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.item.tool.HandHeldInventory;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class HandHeldUpgradeContainer
/*    */   extends HandHeldInventory
/*    */ {
/*    */   public HandHeldUpgradeContainer(EntityPlayer entityPlayer, ItemStack itemStack) {
/* 13 */     super(entityPlayer, itemStack, 3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 19 */     return new ContainerUpgradeContainer(this, p0.field_71071_by);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getGuiClassName(EntityPlayer p0) {
/* 25 */     return "item.GuiUpgradeContainer";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_145825_b() {
/* 31 */     return "HandHeldUpradeContainer";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\HandHeldUpgradeContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */