/*    */ package ic2.api.item;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public final class ElectricItem
/*    */ {
/*    */   public static IElectricItemManager manager;
/*    */   public static IElectricItemManager rawManager;
/*    */   
/*    */   public static void registerBackupManager(IBackupElectricItemManager manager) {
/* 37 */     backupManagers.add(manager);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IBackupElectricItemManager getBackupManager(ItemStack stack) {
/* 49 */     for (IBackupElectricItemManager manager : backupManagers) {
/* 50 */       if (manager.handles(stack)) return manager;
/*    */     
/*    */     } 
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   
/* 57 */   private static final List<IBackupElectricItemManager> backupManagers = new ArrayList<>();
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\item\ElectricItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */