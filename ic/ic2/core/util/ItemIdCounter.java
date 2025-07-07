/*    */ package ic2.core.util;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.common.config.ConfigCategory;
/*    */ import net.minecraftforge.common.config.Configuration;
/*    */ import net.minecraftforge.common.config.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemIdCounter
/*    */ {
/*    */   private int ID;
/*    */   private int startID;
/*    */   
/*    */   public ItemIdCounter(int StartID) {
/* 18 */     this.ID = StartID - 256;
/* 19 */     this.startID = this.ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemIdCounter updateToNextID() {
/* 24 */     this.ID++;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCurrentID() {
/* 30 */     if (Item.func_150899_d(this.ID + 256) != null) {
/*    */       
/* 32 */       int end = this.ID + 256;
/* 33 */       throw new IndexOutOfBoundsException("ItemID is already used: " + end);
/*    */     } 
/* 35 */     return this.ID;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAndUpdateCurrentID() {
/* 40 */     int cu = getCurrentID();
/* 41 */     updateToNextID();
/* 42 */     return cu;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getConfig(Configuration config, String categorie, int defaultID) {
/* 47 */     Property id = config.get(categorie, "Item StartID", defaultID, "StartID for the Items");
/* 48 */     return Integer.parseInt(id.getString());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getConfig(Configuration config, String name, String categorie, int defaultID) {
/* 53 */     Property id = config.get(categorie, name, defaultID, "StartID for the Items");
/* 54 */     return Integer.parseInt(id.getString());
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEnd(Configuration config, String cat) {
/* 59 */     int endID = this.ID - this.startID;
/* 60 */     int startIDs = this.startID + 256;
/* 61 */     int IDs = this.ID + 256;
/* 62 */     ConfigCategory cot = config.getCategory(cat);
/* 63 */     if (cot != null && cot.containsKey("Item StartID".toLowerCase(Locale.ENGLISH))) {
/*    */       
/* 65 */       Property items = cot.get("Item StartID".toLowerCase(Locale.ENGLISH));
/* 66 */       items.comment = String.format("%s%n%s%n%s", new Object[] { items.comment, "You need to Hold " + endID + " ItemIDs Free", "(From: " + startIDs + " ItemID To: " + IDs + " ItemID)" });
/* 67 */       cot.put("Item StartID".toLowerCase(Locale.ENGLISH), items);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\ItemIdCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */