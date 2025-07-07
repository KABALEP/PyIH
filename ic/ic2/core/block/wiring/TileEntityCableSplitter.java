/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import cpw.mods.fml.common.eventhandler.Event;
/*    */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*    */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.common.util.ForgeDirection;
/*    */ 
/*    */ public class TileEntityCableSplitter extends TileEntityCable {
/* 11 */   public static int tickRate = 20;
/* 12 */   public int ticksUntilNextUpdate = 0;
/*    */ 
/*    */   
/*    */   public TileEntityCableSplitter(short type) {
/* 16 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityCableSplitter() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canUpdate() {
/* 27 */     return isSimulating();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145845_h() {
/* 32 */     if (this.ticksUntilNextUpdate == 0) {
/*    */       
/* 34 */       this.ticksUntilNextUpdate = 20;
/* 35 */       int power = 0;
/* 36 */       for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
/*    */         
/* 38 */         if (power > 0) {
/*    */           break;
/*    */         }
/*    */         
/* 42 */         power = Math.max(power, this.field_145850_b.func_72878_l(this.field_145851_c + dir.offsetX, this.field_145848_d + dir.offsetY, this.field_145849_e + dir.offsetZ, dir.ordinal()));
/*    */       } 
/* 44 */       if (((power > 0)) == this.addedToEnergyNet)
/*    */       {
/* 46 */         if (this.addedToEnergyNet) {
/*    */           
/* 48 */           MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 49 */           this.addedToEnergyNet = false;
/*    */         }
/*    */         else {
/*    */           
/* 53 */           MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 54 */           this.addedToEnergyNet = true;
/*    */         } 
/*    */       }
/* 57 */       setActive(this.addedToEnergyNet);
/*    */     } 
/* 59 */     this.ticksUntilNextUpdate--;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onBlockChange() {
/* 65 */     this.ticksUntilNextUpdate = 0;
/* 66 */     super.onBlockChange();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityCableSplitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */