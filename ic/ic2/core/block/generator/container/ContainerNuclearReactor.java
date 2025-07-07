/*    */ package ic2.core.block.generator.container;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import ic2.core.block.generator.tileentity.TileEntityNuclearReactor;
/*    */ import ic2.core.slot.SlotReactor;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ public class ContainerNuclearReactor
/*    */   extends ContainerIC2
/*    */ {
/*    */   public TileEntityNuclearReactor tileEntity;
/*    */   public int size;
/*    */   public boolean isSteam;
/*    */   
/*    */   public ContainerNuclearReactor(EntityPlayer entityPlayer, TileEntityNuclearReactor tileEntity) {
/* 19 */     this.tileEntity = tileEntity;
/* 20 */     this.isSteam = tileEntity instanceof ic2.api.reactor.ISteamReactor;
/* 21 */     this.size = tileEntity.getReactorSize();
/* 22 */     int startX = 89 - 9 * this.size;
/* 23 */     int startY = 18;
/* 24 */     int x = 0;
/* 25 */     int y = 0; int i;
/* 26 */     for (i = 0; i < 54; i++) {
/*    */       
/* 28 */       if (x < this.size)
/*    */       {
/* 30 */         func_75146_a((Slot)new SlotReactor((IInventory)tileEntity, i, startX + 18 * x, startY + 18 * y));
/*    */       }
/* 32 */       if (++x >= 9) {
/*    */         
/* 34 */         y++;
/* 35 */         x = 0;
/*    */       } 
/*    */     } 
/* 38 */     for (i = 0; i < 3; i++) {
/*    */       
/* 40 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 42 */         func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, k + i * 9 + 9, 8 + k * 18, 140 + i * 18));
/*    */       }
/*    */     } 
/* 45 */     for (int j = 0; j < 9; j++)
/*    */     {
/* 47 */       func_75146_a(new Slot((IInventory)entityPlayer.field_71071_by, j, 8 + j * 18, 198));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer entityplayer) {
/* 54 */     return this.tileEntity.func_70300_a(entityplayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 60 */     return 6 * this.size;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 66 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\container\ContainerNuclearReactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */