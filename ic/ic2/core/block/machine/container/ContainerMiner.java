/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.api.item.IMiningDrill;
/*    */ import ic2.api.item.IScannerItem;
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityMiner;
/*    */ import ic2.core.slot.SlotCustom;
/*    */ import ic2.core.slot.SlotDischarge;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerMiner
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityMiner tileEntity;
/*    */   
/*    */   public ContainerMiner(EntityPlayer entityPlayer, TileEntityMiner tileEntity) {
/* 22 */     this.tileEntity = tileEntity;
/* 23 */     func_75146_a((Slot)new SlotDischarge((IInventory)tileEntity, tileEntity.tier, 0, 81, 59));
/* 24 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { IScannerItem.class }, 1, 117, 22));
/* 25 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { Block.class }, 2, 81, 22));
/* 26 */     func_75146_a((Slot)new SlotCustom((IInventory)tileEntity, new Object[] { IMiningDrill.class }, 3, 45, 22));
/* 27 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 29 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 31 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 34 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 36 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 43 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 49 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 55 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_75134_a(EntityPlayer par1EntityPlayer) {
/* 61 */     super.func_75134_a(par1EntityPlayer);
/* 62 */     this.tileEntity.onGuiClosed(par1EntityPlayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerMiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */