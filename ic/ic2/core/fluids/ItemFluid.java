/*    */ package ic2.core.fluids;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ public class ItemFluid
/*    */   extends ItemIC2 {
/*    */   String fluidID;
/*    */   
/*    */   public ItemFluid(String index) {
/* 14 */     super(0);
/* 15 */     this.fluidID = index;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public IIcon func_77617_a(int par1) {
/* 21 */     return ((Fluid)IC2Fluid.fluids.get(this.fluidID)).getStillIcon();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public int func_94901_k() {
/* 28 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\fluids\ItemFluid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */