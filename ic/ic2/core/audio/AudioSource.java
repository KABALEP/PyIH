/*    */ package ic2.core.audio;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
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
/*    */ 
/*    */ public class AudioSource
/*    */ {
/*    */   public void remove() {}
/*    */   
/*    */   public void play() {}
/*    */   
/*    */   public void pause() {}
/*    */   
/*    */   public void stop() {}
/*    */   
/*    */   public void setLoop(boolean loop) {}
/*    */   
/*    */   public void flush() {}
/*    */   
/*    */   public boolean isPlaying() {
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void markForRemove() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public float getVolume() {
/* 44 */     return 0.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setVolume(float volume) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPitch(float pitch) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void updatePosition() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void activate() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void cull() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateVolume(EntityPlayer player) {}
/*    */ 
/*    */   
/*    */   public float getRealVolume() {
/* 73 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchDimension(EntityPlayer par1) {
/* 78 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRemoved() {
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\audio\AudioSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */