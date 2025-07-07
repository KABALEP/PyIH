/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.ContainerIC2;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class ContainerCreativeStorage
/*    */   extends ContainerIC2 {
/*    */   public TileEntityCreativeStorage creative;
/*    */   
/*    */   public ContainerCreativeStorage(TileEntityCreativeStorage tile) {
/* 11 */     this.creative = tile;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int guiInventorySize() {
/* 17 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInput() {
/* 23 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_75145_c(EntityPlayer p0) {
/* 29 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\ContainerCreativeStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */