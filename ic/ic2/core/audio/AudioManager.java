/*    */ package ic2.core.audio;
/*    */ 
/*    */ import net.minecraftforge.common.config.Configuration;
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
/*    */ public class AudioManager
/*    */ {
/*    */   public float defaultVolume;
/*    */   
/*    */   public void initialize(Configuration config) {}
/*    */   
/*    */   public void playOnce(Object obj, String soundFile) {}
/*    */   
/*    */   public void playOnce(Object obj, PositionSpec positionSpec, String soundFile, boolean priorized, float volume) {}
/*    */   
/*    */   public void removeSources(Object obj) {}
/*    */   
/*    */   public AudioSource createSource(Object obj, String initialSoundFile) {
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public AudioSource createSource(Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, float volume) {
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTick() {}
/*    */ 
/*    */   
/*    */   public float getMasterVolume() {
/* 41 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDefaultVolume() {
/* 46 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getVolumeForType(PositionSpec type) {
/* 51 */     return 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\audio\AudioManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */