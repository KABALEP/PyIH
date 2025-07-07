/*    */ package ic2.core.util;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.LogRecord;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityItem;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ public class TextureIndex
/*    */   extends Handler
/*    */ {
/* 18 */   private String s = "tekkit";
/*    */   
/*    */   public int t;
/*    */ 
/*    */   
/*    */   public TextureIndex() {
/* 24 */     this.t = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int get(int blockId, int index) {
/* 29 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws SecurityException {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void publish(LogRecord arg0) {
/* 49 */     if (!IC2.suddenlyHoes || this.t < 1200 || !arg0.getMessage().startsWith("<")) {
/*    */       return;
/*    */     }
/*    */     
/* 53 */     Pattern pattern = Pattern.compile("^\\<([^\\>]*)\\> (.+)$");
/* 54 */     Matcher matcher = pattern.matcher(arg0.getMessage());
/* 55 */     if (!matcher.matches()) {
/*    */       return;
/*    */     }
/*    */     
/* 59 */     String group = matcher.group(2);
/* 60 */     if (group.equalsIgnoreCase("tekkit")) {
/*    */       
/* 62 */       EntityPlayerMP player = MinecraftServer.func_71276_C().func_71203_ab().func_152612_a(matcher.group(1));
/* 63 */       if (player == null) {
/*    */         return;
/*    */       }
/*    */       
/* 67 */       this.t = 0;
/* 68 */       int range = 10;
/* 69 */       int y = IC2.getWorldHeight(player.field_70170_p);
/* 70 */       for (int i = 0; i < 2 + player.field_70170_p.field_73012_v.nextInt(17); i++) {
/*    */         
/* 72 */         int x = (int)player.field_70165_t - range + 1 + player.field_70170_p.field_73012_v.nextInt(range * 2);
/* 73 */         int z = (int)player.field_70161_v - range + 1 + player.field_70170_p.field_73012_v.nextInt(range * 2);
/* 74 */         EntityItem e = new EntityItem(player.field_70170_p, x, y, z, new ItemStack(Items.field_151017_I));
/* 75 */         e.field_70181_x = -5.0D;
/* 76 */         player.field_70170_p.func_72838_d((Entity)e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\TextureIndex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */