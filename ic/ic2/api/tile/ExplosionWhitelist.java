/*    */ package ic2.api.tile;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ExplosionWhitelist
/*    */ {
/*    */   public static void addWhitelistedBlock(Block block) {
/* 23 */     whitelist.add(block);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void removeWhitelistedBlock(Block block) {
/* 32 */     whitelist.remove(block);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isBlockWhitelisted(Block block) {
/* 42 */     return whitelist.contains(block);
/*    */   }
/*    */   
/* 45 */   private static Set<Block> whitelist = new HashSet<>();
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\tile\ExplosionWhitelist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */