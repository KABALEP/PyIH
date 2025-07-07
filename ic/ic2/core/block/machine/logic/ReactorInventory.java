/*    */ package ic2.core.block.machine.logic;
/*    */ 
/*    */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ReactorInventory
/*    */   implements IInventory
/*    */ {
/*    */   TileEntityReactorPlanner planner;
/*    */   
/*    */   public ReactorInventory(TileEntityReactorPlanner tile) {
/* 14 */     this.planner = tile;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_70302_i_() {
/* 20 */     return getLogic().func_70302_i_();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_70301_a(int p_70301_1_) {
/* 26 */     return getLogic().func_70301_a(p_70301_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_70298_a(int p_70298_1_, int p_70298_2_) {
/* 32 */     return getLogic().func_70298_a(p_70298_1_, p_70298_2_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack func_70304_b(int p_70304_1_) {
/* 38 */     return getLogic().func_70304_b(p_70304_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70299_a(int p_70299_1_, ItemStack p_70299_2_) {
/* 44 */     getLogic().func_70299_a(p_70299_1_, p_70299_2_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String func_145825_b() {
/* 50 */     return getLogic().func_145825_b();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 56 */     return getLogic().func_145818_k_();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_70297_j_() {
/* 62 */     return getLogic().func_70297_j_();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70296_d() {
/* 68 */     getLogic().func_70296_d();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_70300_a(EntityPlayer p_70300_1_) {
/* 74 */     return getLogic().func_70300_a(p_70300_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70295_k_() {
/* 80 */     getLogic().func_70295_k_();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_70305_f() {
/* 86 */     getLogic().func_70305_f();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
/* 92 */     return getLogic().func_94041_b(p_94041_1_, p_94041_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public IInventory getLogic() {
/* 97 */     return this.planner.getReactorLogic();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\ReactorInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */