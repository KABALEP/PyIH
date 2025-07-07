/*    */ package ic2.api.energy.event;
/*    */ 
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnergyTileEvent
/*    */   extends WorldEvent
/*    */ {
/*    */   public final IEnergyTile energyTile;
/*    */   
/*    */   public EnergyTileEvent(IEnergyTile energyTile1) {
/* 18 */     super(((TileEntity)energyTile1).func_145831_w());
/*    */     
/* 20 */     if (this.world == null) throw new NullPointerException("world is null");
/*    */     
/* 22 */     this.energyTile = energyTile1;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\event\EnergyTileEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */