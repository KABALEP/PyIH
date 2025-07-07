/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.machine.tileentity.TileEntityMatter;
/*    */ import ic2.core.slot.SlotMatterScrap;
/*    */ import ic2.core.slot.SlotOutput;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ public class ContainerMatter
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityMatter tileEntity;
/*    */   
/*    */   public ContainerMatter(EntityPlayer entityPlayer, TileEntityMatter tileEntity) {
/* 18 */     this.tileEntity = tileEntity;
/* 19 */     func_75146_a((Slot)new SlotMatterScrap((IInventory)tileEntity, 0, 114, 54));
/* 20 */     func_75146_a((Slot)new SlotOutput(entityPlayer, (IInventory)tileEntity, 1, 114, 18));
/* 21 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 23 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 25 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 28 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 30 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 37 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 43 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 49 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\container\ContainerMatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */