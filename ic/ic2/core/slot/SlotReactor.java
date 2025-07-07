/*    */ package ic2.core.slot;
/*    */ 
/*    */ import ic2.core.block.generator.tileentity.TileEntityNuclearReactor;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotReactor
/*    */   extends Slot
/*    */ {
/*    */   public SlotReactor(IInventory par1iInventory, int par2, int par3, int par4) {
/* 12 */     super(par1iInventory, par2, par3, par4);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 17 */     return (stack != null && TileEntityNuclearReactor.isUsefullReactorItem(stack));
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\slot\SlotReactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */