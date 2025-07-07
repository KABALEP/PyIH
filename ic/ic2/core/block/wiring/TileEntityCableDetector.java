/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.api.energy.EnergyNet;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ 
/*    */ public class TileEntityCableDetector
/*    */   extends TileEntityCable {
/*  9 */   public double lastValue = -1.0D;
/* 10 */   public static int tickRate = 20;
/* 11 */   public int ticker = 0;
/*    */ 
/*    */   
/*    */   public TileEntityCableDetector(short meta) {
/* 15 */     super(meta);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityCableDetector() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 26 */     super.func_145839_a(nbttagcompound);
/* 27 */     setActiveWithoutNotify(nbttagcompound.func_74767_n("active"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 33 */     super.func_145841_b(nbttagcompound);
/* 34 */     nbttagcompound.func_74757_a("active", getActive());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canUpdate() {
/* 40 */     return isSimulating();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_145845_h() {
/* 45 */     super.func_145845_h();
/* 46 */     if (this.ticker++ % tickRate == 0) {
/*    */       
/* 48 */       double newValue = EnergyNet.instance.getNodeStats((TileEntity)this).getEnergyOut();
/* 49 */       if (this.lastValue != -1.0D)
/*    */       {
/* 51 */         if (newValue != this.lastValue && newValue != 0.0D) {
/*    */           
/* 53 */           if (!getActive())
/*    */           {
/* 55 */             setActive(true);
/* 56 */             this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*    */           }
/*    */         
/* 59 */         } else if (getActive()) {
/*    */           
/* 61 */           setActive(false);
/* 62 */           this.field_145850_b.func_147459_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e));
/*    */         } 
/*    */       }
/* 65 */       this.lastValue = newValue;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\TileEntityCableDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */