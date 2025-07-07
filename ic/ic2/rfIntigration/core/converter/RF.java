/*    */ package ic2.rfIntigration.core.converter;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.rfIntigration.tiles.IConverterStorage;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ public class RF
/*    */ {
/*    */   public int storedRF;
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt) {
/* 13 */     this.storedRF = nbt.func_74762_e("RF");
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 18 */     nbt.func_74768_a("RF", this.storedRF);
/*    */   }
/*    */ 
/*    */   
/*    */   public int charge(int amount, boolean simulate, IConverterStorage storage) {
/* 23 */     int conv = IC2.rfPerEU;
/* 24 */     int copy = amount;
/* 25 */     if (simulate) {
/*    */       
/* 27 */       int storing = this.storedRF;
/* 28 */       storing += amount;
/* 29 */       if (storing >= storage.getFreeSpace() * conv)
/*    */       {
/* 31 */         amount -= storing - storage.getFreeSpace() * conv;
/*    */       }
/* 33 */       return copy - amount;
/*    */     } 
/* 35 */     this.storedRF += amount;
/* 36 */     if (this.storedRF >= storage.getFreeSpace() * conv) {
/*    */       
/* 38 */       amount -= this.storedRF - storage.getFreeSpace() * conv;
/* 39 */       this.storedRF = storage.getFreeSpace() * conv;
/*    */     } 
/* 41 */     if (this.storedRF >= conv)
/*    */     {
/* 43 */       this.storedRF -= storage.charge(this.storedRF / conv) * conv;
/*    */     }
/* 45 */     return copy - amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public int discharge(int amount, boolean simulate, IConverterStorage storage) {
/* 50 */     int conv = IC2.rfForEU;
/* 51 */     if (simulate) {
/*    */       
/* 53 */       int stored = this.storedRF;
/* 54 */       if (stored >= amount)
/*    */       {
/* 56 */         return amount;
/*    */       }
/* 58 */       stored += storage.useEnergy(amount / conv + 1, true) * conv;
/* 59 */       return Math.min(amount, stored);
/*    */     } 
/* 61 */     if (this.storedRF >= amount) {
/*    */       
/* 63 */       this.storedRF -= amount;
/* 64 */       return amount;
/*    */     } 
/* 66 */     this.storedRF += storage.useEnergy(amount / conv + 1) * conv;
/* 67 */     this.storedRF -= amount;
/* 68 */     if (this.storedRF < 0) {
/*    */       
/* 70 */       amount += this.storedRF;
/* 71 */       this.storedRF = 0;
/*    */     } 
/* 73 */     return amount;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\rfIntigration\core\converter\RF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */