/*    */ package ic2.api.energy;
/*    */ 
/*    */ public class PacketStat
/*    */   implements Comparable<PacketStat>
/*    */ {
/*    */   public final int energy;
/*    */   public final long count;
/*    */   
/*    */   public PacketStat(int par1, long par2) {
/* 10 */     this.energy = par1;
/* 11 */     this.count = par2;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getPacketCount() {
/* 16 */     return this.count;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPacketEnergy() {
/* 21 */     return this.energy;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(PacketStat o) {
/* 27 */     if (o.energy < this.energy)
/*    */     {
/* 29 */       return 1;
/*    */     }
/* 31 */     if (o.energy > this.energy)
/*    */     {
/* 33 */       return -1;
/*    */     }
/* 35 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\energy\PacketStat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */