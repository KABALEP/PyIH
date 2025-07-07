/*     */ package ic2.core.audio;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySoundBeacon;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import paulscode.sound.SoundSystem;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class AudioSourceClient
/*     */   extends AudioSource
/*     */   implements Comparable<AudioSourceClient>
/*     */ {
/*     */   private SoundSystem soundSystem;
/*     */   private String sourceName;
/*     */   private boolean valid = false;
/*     */   private boolean culled = false;
/*     */   private Object obj;
/*     */   private AudioPosition position;
/*     */   private PositionSpec positionSpec;
/*     */   private float configuredVolume;
/*     */   private float realVolume;
/*     */   private boolean isPlaying = false;
/*     */   boolean marked = false;
/*     */   
/*     */   public AudioSourceClient(SoundSystem soundSystem, String sourceName, Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, float volume) {
/*  34 */     this.soundSystem = soundSystem;
/*  35 */     this.sourceName = sourceName;
/*  36 */     this.obj = obj;
/*  37 */     this.positionSpec = positionSpec;
/*  38 */     URL url = AudioSource.class.getClassLoader().getResource("assets/ic2/sounds/" + initialSoundFile);
/*  39 */     if (url == null) {
/*     */       
/*  41 */       System.out.println("Invalid sound file: " + initialSoundFile);
/*     */       return;
/*     */     } 
/*  44 */     this.position = AudioPosition.getFrom(obj, positionSpec);
/*  45 */     soundSystem.newSource(priorized, sourceName, url, initialSoundFile, loop, this.position.x, this.position.y, this.position.z, 0, ((AudioManagerClient)IC2.audioManager).fadingDistance * Math.max(volume, 1.0F));
/*  46 */     this.valid = true;
/*  47 */     setVolume(volume);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(AudioSourceClient x) {
/*  53 */     if (this.culled)
/*     */     {
/*  55 */       return (int)((this.realVolume * 0.9F - x.realVolume) * 128.0F);
/*     */     }
/*  57 */     return (int)((this.realVolume - x.realVolume) * 128.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/*  63 */     if (!this.valid) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     stop();
/*  68 */     this.realVolume = 0.0F;
/*  69 */     this.soundSystem.removeSource(this.sourceName);
/*  70 */     this.sourceName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void markForRemove() {
/*  76 */     this.marked = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void play() {
/*  82 */     if (!this.valid) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     if (this.isPlaying) {
/*     */       return;
/*     */     }
/*     */     
/*  90 */     this.isPlaying = true;
/*  91 */     if (this.culled) {
/*     */       return;
/*     */     }
/*     */     
/*  95 */     this.soundSystem.play(this.sourceName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPlaying() {
/* 101 */     if (!this.valid)
/*     */     {
/* 103 */       return false;
/*     */     }
/* 105 */     if (!this.isPlaying)
/*     */     {
/* 107 */       return false;
/*     */     }
/* 109 */     return this.soundSystem.playing(this.sourceName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {
/* 115 */     if (!this.valid) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     if (!this.isPlaying) {
/*     */       return;
/*     */     }
/*     */     
/* 123 */     this.isPlaying = false;
/* 124 */     if (this.culled) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     this.soundSystem.pause(this.sourceName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 134 */     if (!this.valid) {
/*     */       return;
/*     */     }
/*     */     
/* 138 */     if (!this.isPlaying || !this.soundSystem.playing(this.sourceName)) {
/*     */       
/* 140 */       if (this.isPlaying)
/*     */       {
/* 142 */         this.isPlaying = false;
/*     */       }
/*     */       return;
/*     */     } 
/* 146 */     this.isPlaying = false;
/* 147 */     if (this.culled) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     this.soundSystem.stop(this.sourceName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoop(boolean loop) {
/* 157 */     if (!this.valid) {
/*     */       return;
/*     */     }
/*     */     
/* 161 */     if (this.culled) {
/*     */       return;
/*     */     }
/*     */     
/* 165 */     this.soundSystem.setLooping(this.sourceName, loop);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 171 */     if (!this.valid) {
/*     */       return;
/*     */     }
/*     */     
/* 175 */     if (!this.isPlaying) {
/*     */       return;
/*     */     }
/*     */     
/* 179 */     if (this.culled) {
/*     */       return;
/*     */     }
/*     */     
/* 183 */     this.soundSystem.flush(this.sourceName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cull() {
/* 189 */     if (!this.valid || this.culled) {
/*     */       return;
/*     */     }
/*     */     
/* 193 */     if (this.isPlaying)
/*     */     {
/* 195 */       this.soundSystem.stop(this.sourceName);
/*     */     }
/* 197 */     this.soundSystem.cull(this.sourceName);
/* 198 */     this.culled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void activate() {
/* 204 */     if (!this.valid || !this.culled) {
/*     */       return;
/*     */     }
/*     */     
/* 208 */     this.soundSystem.activate(this.sourceName);
/* 209 */     this.culled = false;
/* 210 */     if (this.isPlaying)
/*     */     {
/* 212 */       this.soundSystem.play(this.sourceName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getVolume() {
/* 219 */     if (!this.valid)
/*     */     {
/* 221 */       return 0.0F;
/*     */     }
/* 223 */     return this.soundSystem.getVolume(this.sourceName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRealVolume() {
/* 229 */     return this.realVolume;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVolume(float volume) {
/* 235 */     this.configuredVolume = volume;
/* 236 */     this.soundSystem.setVolume(this.sourceName, 0.001F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPitch(float pitch) {
/* 242 */     if (!this.valid) {
/*     */       return;
/*     */     }
/*     */     
/* 246 */     this.soundSystem.setPitch(this.sourceName, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePosition() {
/* 252 */     if (!this.valid) {
/*     */       return;
/*     */     }
/*     */     
/* 256 */     this.position = AudioPosition.getFrom(this.obj, this.positionSpec);
/* 257 */     this.soundSystem.setPosition(this.sourceName, this.position.x, this.position.y, this.position.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRemoved() {
/* 263 */     return (!this.valid || this.sourceName == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateVolume(EntityPlayer player) {
/*     */     float distance;
/* 269 */     if (!this.valid || !this.isPlaying) {
/*     */       
/* 271 */       this.realVolume = 0.0F;
/*     */       return;
/*     */     } 
/* 274 */     float maxDistance = ((AudioManagerClient)IC2.audioManager).fadingDistance * Math.max(this.configuredVolume, 1.0F);
/* 275 */     float rolloffFactor = 1.0F;
/* 276 */     float referenceDistance = 1.0F;
/* 277 */     float x = (float)((Entity)player).field_70165_t;
/* 278 */     float y = (float)((Entity)player).field_70163_u;
/* 279 */     float z = (float)((Entity)player).field_70161_v;
/*     */     
/* 281 */     if (this.position.world == ((Entity)player).field_70170_p) {
/*     */       
/* 283 */       float deltaX = this.position.x - x;
/* 284 */       float deltaY = this.position.y - y;
/* 285 */       float deltaZ = this.position.z - z;
/* 286 */       distance = (float)Math.sqrt((deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ));
/*     */     }
/*     */     else {
/*     */       
/* 290 */       distance = 1.0F;
/*     */     } 
/* 292 */     if (distance > maxDistance) {
/*     */       
/* 294 */       this.realVolume = 0.0F;
/* 295 */       cull();
/*     */       return;
/*     */     } 
/* 298 */     if (distance < referenceDistance)
/*     */     {
/* 300 */       distance = referenceDistance;
/*     */     }
/* 302 */     float gain = 1.0F - rolloffFactor * (distance - referenceDistance) / (maxDistance - referenceDistance);
/* 303 */     float newRealVolume = gain * this.configuredVolume;
/* 304 */     newRealVolume *= getBeaconEffect(this.positionSpec);
/* 305 */     newRealVolume *= IC2.audioManager.getVolumeForType(this.positionSpec);
/* 306 */     newRealVolume *= IC2.audioManager.getMasterVolume();
/* 307 */     float dx = (this.position.x - x) / distance;
/* 308 */     float dy = (this.position.y - y) / distance;
/* 309 */     float dz = (this.position.z - z) / distance;
/* 310 */     if (newRealVolume > 0.1D)
/*     */     {
/* 312 */       for (int i = 0; i < distance; i++) {
/*     */         
/* 314 */         Block block = player.field_70170_p.func_147439_a((int)x, (int)y, (int)z);
/* 315 */         if (block != null)
/*     */         {
/* 317 */           if (block.func_149662_c()) {
/*     */             
/* 319 */             newRealVolume *= 0.6F;
/*     */           }
/*     */           else {
/*     */             
/* 323 */             newRealVolume *= 0.8F;
/*     */           } 
/*     */         }
/* 326 */         x += dx;
/* 327 */         y += dy;
/* 328 */         z += dz;
/*     */       } 
/*     */     }
/* 331 */     if (Math.abs(this.realVolume / newRealVolume - 1.0F) > 0.06D)
/*     */     {
/* 333 */       this.soundSystem.setVolume(this.sourceName, IC2.audioManager.getMasterVolume() * IC2.audioManager.getVolumeForType(this.positionSpec) * Math.min(newRealVolume, 1.0F));
/*     */     }
/* 335 */     this.realVolume = newRealVolume;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getBeaconEffect(PositionSpec spec) {
/* 340 */     float result = 1.0F;
/* 341 */     List<TileEntitySoundBeacon> beacons = TileEntitySoundBeacon.beacon;
/* 342 */     for (TileEntitySoundBeacon beacon : beacons) {
/*     */       
/* 344 */       if (beacon.getActive() && beacon.isInRange(this.position, spec)) {
/*     */         
/* 346 */         result *= beacon.getEffect(spec);
/* 347 */         if (result <= 0.0F) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 353 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchDimension(EntityPlayer par1) {
/* 359 */     return (par1 != null && this.position != null && this.position.world.field_73011_w.field_76574_g == par1.field_70170_p.field_73011_w.field_76574_g);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\audio\AudioSourceClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */