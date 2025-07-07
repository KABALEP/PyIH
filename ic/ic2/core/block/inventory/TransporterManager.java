/*    */ package ic2.core.block.inventory;
/*    */ 
/*    */ import ic2.core.block.inventory.transporter.BasicInventoryIterator;
/*    */ import ic2.core.block.inventory.transporter.BasicTransporter;
/*    */ import ic2.core.block.inventory.transporter.SidedInventoryIterator;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.ISidedInventory;
/*    */ import net.minecraft.inventory.InventoryLargeChest;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ 
/*    */ public class TransporterManager
/*    */ {
/* 15 */   public static TransporterManager manager = new TransporterManager();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IItemTransporter getTransporter(Object par1) {
/* 21 */     if (par1 instanceof ISidedInventory)
/*    */     {
/* 23 */       return (IItemTransporter)new BasicTransporter((IInventory)par1);
/*    */     }
/* 25 */     if (par1 instanceof IInventory)
/*    */     {
/* 27 */       return (IItemTransporter)new BasicTransporter(checkForChest((IInventory)par1));
/*    */     }
/* 29 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Iterable<IInvSlot> getIteratorForInventory(IInventory par1, ForgeDirection dir) {
/* 35 */     if (par1 instanceof ISidedInventory)
/*    */     {
/* 37 */       return (Iterable<IInvSlot>)new SidedInventoryIterator((ISidedInventory)par1, dir);
/*    */     }
/* 39 */     return (Iterable<IInvSlot>)new BasicInventoryIterator(par1);
/*    */   }
/*    */ 
/*    */   
/*    */   private IInventory checkForChest(IInventory par1) {
/* 44 */     if (par1 instanceof TileEntityChest) {
/*    */       
/* 46 */       TileEntityChest chest = getDoubleChest((TileEntityChest)par1);
/* 47 */       if (chest != null)
/*    */       {
/* 49 */         return (IInventory)new InventoryLargeChest("", par1, (IInventory)chest);
/*    */       }
/*    */     } 
/* 52 */     return par1;
/*    */   }
/*    */ 
/*    */   
/*    */   private TileEntityChest getDoubleChest(TileEntityChest chest) {
/* 57 */     if (chest.field_145991_k != null)
/*    */     {
/* 59 */       return chest.field_145991_k;
/*    */     }
/* 61 */     if (chest.field_145990_j != null)
/*    */     {
/* 63 */       return chest.field_145990_j;
/*    */     }
/* 65 */     if (chest.field_145992_i != null)
/*    */     {
/* 67 */       return chest.field_145992_i;
/*    */     }
/* 69 */     if (chest.field_145988_l != null)
/*    */     {
/* 71 */       return chest.field_145988_l;
/*    */     }
/* 73 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\inventory\TransporterManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */