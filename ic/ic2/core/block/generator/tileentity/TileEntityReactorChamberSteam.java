/*    */ package ic2.core.block.generator.tileentity;
/*    */ 
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidTankInfo;
/*    */ import net.minecraftforge.fluids.IFluidHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityReactorChamberSteam
/*    */   extends TileEntityReactorChamber
/*    */   implements IFluidHandler
/*    */ {
/*    */   public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
/* 17 */     TileEntityNuclearReactor reactor = getReactor();
/* 18 */     if (reactor == null || !(reactor instanceof IFluidHandler))
/*    */     {
/* 20 */       return 0;
/*    */     }
/* 22 */     return ((IFluidHandler)reactor).fill(from, resource, doFill);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
/* 28 */     TileEntityNuclearReactor reactor = getReactor();
/* 29 */     if (reactor == null || !(reactor instanceof IFluidHandler))
/*    */     {
/* 31 */       return null;
/*    */     }
/* 33 */     return ((IFluidHandler)reactor).drain(from, resource, doDrain);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
/* 39 */     TileEntityNuclearReactor reactor = getReactor();
/* 40 */     if (reactor == null || !(reactor instanceof IFluidHandler))
/*    */     {
/* 42 */       return null;
/*    */     }
/* 44 */     return ((IFluidHandler)reactor).drain(from, maxDrain, doDrain);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canFill(ForgeDirection from, Fluid fluid) {
/* 50 */     TileEntityNuclearReactor reactor = getReactor();
/* 51 */     if (reactor == null || !(reactor instanceof IFluidHandler))
/*    */     {
/* 53 */       return false;
/*    */     }
/* 55 */     return ((IFluidHandler)reactor).canFill(from, fluid);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canDrain(ForgeDirection from, Fluid fluid) {
/* 61 */     TileEntityNuclearReactor reactor = getReactor();
/* 62 */     if (reactor == null || !(reactor instanceof IFluidHandler))
/*    */     {
/* 64 */       return false;
/*    */     }
/* 66 */     return ((IFluidHandler)reactor).canDrain(from, fluid);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public FluidTankInfo[] getTankInfo(ForgeDirection from) {
/* 72 */     TileEntityNuclearReactor reactor = getReactor();
/* 73 */     if (reactor == null || !(reactor instanceof IFluidHandler))
/*    */     {
/* 75 */       return null;
/*    */     }
/* 77 */     return ((IFluidHandler)reactor).getTankInfo(from);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityReactorChamberSteam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */