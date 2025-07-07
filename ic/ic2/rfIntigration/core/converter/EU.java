/*    */ package ic2.rfIntigration.core.converter;
/*    */ 
/*    */ import ic2.rfIntigration.tiles.IConverterStorage;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EU
/*    */ {
/*    */   public double storedEU;
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt) {
/* 14 */     this.storedEU = nbt.func_74769_h("EU");
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 19 */     nbt.func_74780_a("EU", this.storedEU);
/*    */   }
/*    */ 
/*    */   
/*    */   public double charge(double amount, IConverterStorage storage) {
/* 24 */     double copy = amount;
/* 25 */     amount = applyLoss(amount, storage, false);
/* 26 */     this.storedEU += amount;
/* 27 */     if (this.storedEU >= storage.getFreeSpace()) {
/*    */       
/* 29 */       amount -= this.storedEU - storage.getFreeSpace();
/* 30 */       this.storedEU = storage.getFreeSpace();
/*    */     } 
/* 32 */     if (this.storedEU > 0.0D)
/*    */     {
/* 34 */       this.storedEU -= storage.charge((int)this.storedEU);
/*    */     }
/* 36 */     amount = applyLoss(amount, storage, true);
/* 37 */     return copy - amount;
/*    */   }
/*    */ 
/*    */   
/*    */   private double applyLoss(double input, IConverterStorage storage, boolean after) {
/* 42 */     if (after)
/*    */     {
/* 44 */       return input / storage.getLoss();
/*    */     }
/* 46 */     return input * storage.getLoss();
/*    */   }
/*    */ 
/*    */   
/*    */   public void discharge(double amount, IConverterStorage storage) {
/* 51 */     amount /= storage.getLoss();
/* 52 */     if (this.storedEU >= amount) {
/*    */       
/* 54 */       this.storedEU -= amount;
/*    */       return;
/*    */     } 
/* 57 */     this.storedEU += storage.useEnergy((int)amount + 1);
/* 58 */     this.storedEU -= amount;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\core\converter\EU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */