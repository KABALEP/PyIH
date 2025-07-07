/*    */ package ic2.core.block.generator.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
/*    */ import ic2.core.slot.SlotCharge;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerSolarGenerator
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntitySolarGenerator tileEntity;
/*    */   
/*    */   public ContainerSolarGenerator(EntityPlayer entityPlayer, TileEntitySolarGenerator tileEntity) {
/* 16 */     this.tileEntity = tileEntity;
/* 17 */     func_75146_a((Slot)new SlotCharge((IInventory)tileEntity, tileEntity.tier, 0, 80, 26));
/* 18 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 20 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 22 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*    */       }
/*    */     } 
/* 25 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 27 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 34 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 40 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 46 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\container\ContainerSolarGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */