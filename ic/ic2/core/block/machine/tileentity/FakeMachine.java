/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.api.energy.tile.IEnergySink;
/*    */ import ic2.api.tile.IMachine;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ public class FakeMachine
/*    */   extends TileEntity
/*    */   implements IMachine, IEnergySink
/*    */ {
/*    */   List<IMachine.UpgradeType> types;
/*    */   IEnergySink sink;
/*    */   
/*    */   public FakeMachine(IEnergySink par1, List<IMachine.UpgradeType> par2) {
/* 19 */     this.sink = par1;
/* 20 */     TileEntity tile = (TileEntity)par1;
/* 21 */     func_145834_a(tile.func_145831_w());
/* 22 */     this.field_145851_c = tile.field_145851_c;
/* 23 */     this.field_145848_d = tile.field_145848_d;
/* 24 */     this.field_145849_e = tile.field_145849_e;
/* 25 */     this.types = par2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getEnergy() {
/* 31 */     return 0.0D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean useEnergy(double amount, boolean simulate) {
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRedstoneSensitive(boolean active) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRedstoneSensitive() {
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isProcessing() {
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValidInput(ItemStack par1) {
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<IMachine.UpgradeType> getSupportedTypes() {
/* 67 */     return this.types;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
/* 73 */     return this.sink.acceptsEnergyFrom(emitter, direction);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDemandedEnergy() {
/* 79 */     return this.sink.getDemandedEnergy();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSinkTier() {
/* 85 */     return this.sink.getSinkTier();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
/* 91 */     return this.sink.injectEnergy(directionFrom, amount, voltage);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\FakeMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */