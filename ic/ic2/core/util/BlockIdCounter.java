/*    */ package ic2.core.util;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraftforge.common.config.ConfigCategory;
/*    */ import net.minecraftforge.common.config.Configuration;
/*    */ import net.minecraftforge.common.config.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockIdCounter
/*    */ {
/*    */   private int ID;
/*    */   private int startID;
/*    */   
/*    */   public BlockIdCounter(int StartID) {
/* 19 */     this.ID = StartID;
/* 20 */     this.startID = this.ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockIdCounter updateToNextID() {
/* 25 */     this.ID++;
/* 26 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCurrentID() {
/* 31 */     Block check = Block.func_149729_e(this.ID);
/* 32 */     if (check != null && check != Blocks.field_150350_a)
/*    */     {
/* 34 */       throw new IndexOutOfBoundsException("BlockID is already used: " + this.ID);
/*    */     }
/* 36 */     return this.ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAndUpdateCurrentID() {
/* 41 */     int cu = getCurrentID();
/* 42 */     updateToNextID();
/* 43 */     return cu;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getConfig(Configuration par1, String category, int DefaultID) {
/* 48 */     Property id = par1.get(category, "Block Start ID", DefaultID, "StartID for the Blocks");
/* 49 */     return Integer.parseInt(id.getString());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEnd(Configuration config, String cat) {
/* 54 */     int endID = this.ID - this.startID;
/* 55 */     ConfigCategory cot = config.getCategory(cat);
/* 56 */     if (cot != null && cot.containsKey("Block Start ID".toLowerCase(Locale.ENGLISH))) {
/*    */       
/* 58 */       Property items = cot.get("Block Start ID".toLowerCase(Locale.ENGLISH));
/* 59 */       items.comment = String.format("%s%n%s%n%s", new Object[] { items.comment, "You need to Hold " + endID + " BlockIDs Free", "(From: " + this.startID + " BlockID To: " + this.ID + " BlockID)" });
/* 60 */       cot.put("Block Start ID".toLowerCase(Locale.ENGLISH), items);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\BlockIdCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */