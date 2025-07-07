/*    */ package ic2.api.tile;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IMachine
/*    */ {
/*    */   double getEnergy();
/*    */   
/*    */   boolean useEnergy(double paramDouble, boolean paramBoolean);
/*    */   
/*    */   void setRedstoneSensitive(boolean paramBoolean);
/*    */   
/*    */   boolean isRedstoneSensitive();
/*    */   
/*    */   boolean isProcessing();
/*    */   
/*    */   boolean isValidInput(ItemStack paramItemStack);
/*    */   
/*    */   List<UpgradeType> getSupportedTypes();
/*    */   
/*    */   public enum UpgradeType
/*    */   {
/* 80 */     ImportExport,
/* 81 */     MachineModifierA,
/* 82 */     MachineModifierB,
/* 83 */     RedstoneControl,
/* 84 */     Processing,
/* 85 */     Sounds,
/* 86 */     WorldInteraction,
/* 87 */     Custom;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\tile\IMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */