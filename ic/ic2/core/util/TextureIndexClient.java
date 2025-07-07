/*    */ package ic2.core.util;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TextureIndexClient
/*    */   extends TextureIndex
/*    */ {
/* 12 */   private Map textureIndexes = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public int get(int blockId, int index) {
/* 17 */     throw new UnsupportedOperationException("port me (seems to be unused)");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 23 */     this.textureIndexes.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\TextureIndexClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */