/*    */ package ic2.waIntigration.core;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*    */ import cpw.mods.fml.common.gameevent.TickEvent;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerTickHandler
/*    */ {
/*    */   @SubscribeEvent
/*    */   public void onPlayerTick(TickEvent.PlayerTickEvent event) {
/* 13 */     if (event.player == null || event.side == Side.CLIENT || event.phase == TickEvent.Phase.START) {
/*    */       return;
/*    */     }
/*    */     
/* 17 */     if (event.player.field_70170_p.func_82737_E() % 10L != 0L) {
/*    */       return;
/*    */     }
/*    */     
/* 21 */     if (EnergyTileHandler.skipping.remove(event.player)) {
/*    */       return;
/*    */     }
/*    */     
/* 25 */     EnergyTileHandler.tracking.remove(event.player);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\waIntigration\core\PlayerTickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */