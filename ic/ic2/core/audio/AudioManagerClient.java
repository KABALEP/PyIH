/*     */ package ic2.core.audio;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySoundBeacon;
/*     */ import java.net.URL;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.audio.SoundManager;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.config.Configuration;
/*     */ import net.minecraftforge.common.config.Property;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.openal.AL10;
/*     */ import paulscode.sound.SoundSystem;
/*     */ import paulscode.sound.SoundSystemConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class AudioManagerClient
/*     */   extends AudioManager
/*     */   implements IResourceManagerReloadListener
/*     */ {
/*  43 */   public float fadingDistance = 16.0F;
/*     */   private boolean enabled = true;
/*  45 */   private int maxSourceCount = 32;
/*  46 */   private int streamingSourceCount = 4;
/*     */   private boolean lateInitDone = false;
/*  48 */   private SoundSystem soundSystem = null;
/*  49 */   private float masterVolume = 0.5F;
/*  50 */   private float blockMasterVolume = 1.0F;
/*  51 */   private float currentItemMasterVolume = 1.0F;
/*  52 */   private float currentBackpackMasterVolume = 1.0F;
/*  53 */   private int ticker = 0;
/*  54 */   private int nextId = 0;
/*     */   public boolean markForRemoving = false;
/*  56 */   private Map<Object, List<AudioSource>> objectToAudioSourceMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioManagerClient() {
/*  61 */     this.defaultVolume = 1.2F;
/*  62 */     ((IReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(Configuration config) {
/*  68 */     if (config != null) {
/*     */       
/*  70 */       Property prop = config.get("general", "soundsEnabled", this.enabled);
/*  71 */       prop.comment = "Enable sounds";
/*  72 */       this.enabled = Boolean.parseBoolean(prop.getString());
/*  73 */       prop = config.get("general", "soundSourceLimit", this.maxSourceCount);
/*  74 */       prop.comment = "Maximum number of audio sources, only change if you know what you're doing";
/*  75 */       this.maxSourceCount = Integer.parseInt(prop.getString());
/*  76 */       prop = config.get("general", "Master Sound Volume", 0.5D);
/*  77 */       prop.comment = "Master Volume that you can change ingame. Please do not change it. If you really want to then do a number between 0-1";
/*  78 */       this.masterVolume = Float.parseFloat(prop.getString());
/*  79 */       prop = config.get("general", "Tile Sound Volume", 1.0D);
/*  80 */       prop.comment = "Master Volume for all Machines from IC2. Please do not change it. If you really want to then do a number between 0-1";
/*  81 */       this.blockMasterVolume = Float.parseFloat(prop.getString());
/*  82 */       prop = config.get("general", "Equippet Item Sound Volume", 1.0D);
/*  83 */       prop.comment = "Master Volume for all Items that play Sounds in your Hand from IC2. Please do not change it. If you really want to then do a number between 0-1";
/*  84 */       this.currentItemMasterVolume = Float.parseFloat(prop.getString());
/*  85 */       prop = config.get("general", "Backpack Sound Volume", 1.0D);
/*  86 */       prop.comment = "Master Volume for all Backpack Items that play Sounds from IC2. Please do not change it. If you really want to then do a number between 0-1";
/*  87 */       this.currentBackpackMasterVolume = Float.parseFloat(prop.getString());
/*  88 */       if (!this.enabled)
/*     */       {
/*  90 */         this.masterVolume = 0.0F;
/*     */       }
/*  92 */       config.save();
/*     */     } 
/*  94 */     if (this.maxSourceCount <= 6) {
/*     */       
/*  96 */       IC2.log.info("Audio source limit too low to enable IC2 sounds.");
/*  97 */       this.enabled = false;
/*  98 */       this.masterVolume = 0.0F;
/*     */     } 
/* 100 */     if (!this.enabled) {
/*     */       
/* 102 */       IC2.log.info("Sounds disabled.");
/*     */       return;
/*     */     } 
/* 105 */     if (this.maxSourceCount < 6) {
/*     */       
/* 107 */       this.enabled = false;
/* 108 */       this.masterVolume = 0.0F;
/*     */       return;
/*     */     } 
/* 111 */     IC2.log.info("Using " + this.maxSourceCount + " audio sources.");
/* 112 */     SoundSystemConfig.setNumberStreamingChannels(this.streamingSourceCount);
/* 113 */     SoundSystemConfig.setNumberNormalChannels(this.maxSourceCount - this.streamingSourceCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSound(float f) {
/* 118 */     this.masterVolume = f;
/* 119 */     if (this.maxSourceCount <= 6)
/*     */     {
/* 121 */       this.masterVolume = 0.0F;
/*     */     }
/* 123 */     IC2.config.get("general", "Master Sound Volume", 0.5D).set(this.masterVolume);
/* 124 */     IC2.config.save();
/* 125 */     if (this.masterVolume <= 0.0F) {
/*     */       
/* 127 */       this.enabled = false;
/*     */     }
/* 129 */     else if (this.masterVolume > 0.0F && this.maxSourceCount > 6) {
/*     */       
/* 131 */       this.enabled = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSoundType(float f, PositionSpec type) {
/* 137 */     switch (type) {
/*     */       
/*     */       case Backpack:
/* 140 */         this.currentBackpackMasterVolume = f;
/* 141 */         IC2.config.get("general", "Backpack Sound Volume", 1.0D).set(f);
/* 142 */         IC2.config.save();
/*     */         break;
/*     */       case Center:
/* 145 */         this.blockMasterVolume = f;
/* 146 */         IC2.config.get("general", "Tile Sound Volume", 1.0D).set(f);
/* 147 */         IC2.config.save();
/*     */         break;
/*     */       case Hand:
/* 150 */         this.currentItemMasterVolume = f;
/* 151 */         IC2.config.get("gerneal", "Equippet Item Sound Volume", 1.0D).set(f);
/* 152 */         IC2.config.save();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 161 */     TileEntitySoundBeacon.updateList((World)(Minecraft.func_71410_x()).field_71441_e);
/* 162 */     if (!this.enabled || this.soundSystem == null) {
/*     */       return;
/*     */     }
/*     */     
/* 166 */     EntityPlayer player = IC2.platform.getPlayerInstance();
/* 167 */     if (player == null || (Minecraft.func_71410_x().func_147113_T() && this.markForRemoving)) {
/*     */       
/* 169 */       List audioSourceCarriers = new ArrayList(this.objectToAudioSourceMap.keySet());
/* 170 */       for (Object obj : audioSourceCarriers)
/*     */       {
/* 172 */         removeSources(obj);
/*     */       }
/* 174 */       this.markForRemoving = false;
/*     */     }
/*     */     else {
/*     */       
/* 178 */       for (Object obj : new HashSet(this.objectToAudioSourceMap.keySet())) {
/*     */         
/* 180 */         if (obj == null) {
/*     */           
/* 182 */           this.objectToAudioSourceMap.remove(obj);
/*     */         }
/* 184 */         else if (obj instanceof TileEntity) {
/*     */           
/* 186 */           TileEntity tile = (TileEntity)obj;
/* 187 */           if ((tile.func_145831_w()).field_73011_w.field_76574_g != player.field_70170_p.field_73011_w.field_76574_g)
/*     */           {
/* 189 */             this.objectToAudioSourceMap.remove(obj);
/*     */           }
/*     */         }
/* 192 */         else if (obj instanceof Entity) {
/*     */           
/* 194 */           Entity entity = (Entity)obj;
/* 195 */           if (entity.field_70170_p.field_73011_w.field_76574_g != player.field_70170_p.field_73011_w.field_76574_g)
/*     */           {
/* 197 */             this.objectToAudioSourceMap.remove(obj);
/*     */           }
/*     */         }
/* 200 */         else if (obj instanceof AudioPosition) {
/*     */           
/* 202 */           AudioPosition audio = (AudioPosition)obj;
/* 203 */           if (audio.world.field_73011_w.field_76574_g != player.field_70170_p.field_73011_w.field_76574_g)
/*     */           {
/* 205 */             this.objectToAudioSourceMap.remove(obj);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 210 */           this.objectToAudioSourceMap.remove(obj);
/*     */         } 
/* 212 */         removeMarked(obj);
/*     */       } 
/* 214 */       Queue<AudioSource> validAudioSources = new PriorityQueue();
/* 215 */       for (Map.Entry<Object, List<AudioSource>> audioSourceList : this.objectToAudioSourceMap.entrySet()) {
/*     */ 
/*     */         
/*     */         try {
/* 219 */           for (AudioSource audioSource : audioSourceList.getValue())
/*     */           {
/* 221 */             audioSource.updateVolume(player);
/* 222 */             if (audioSource.getRealVolume() > 0.0F && audioSource.matchDimension(player))
/*     */             {
/* 224 */               validAudioSources.add(audioSource);
/*     */             }
/*     */           }
/*     */         
/*     */         }
/* 229 */         catch (Exception e) {
/*     */           
/* 231 */           removeSources(audioSourceList.getKey());
/* 232 */           FMLLog.getLogger().info("Audio Interaction Problem which got catched from Speiger's Secuirtiy System.");
/*     */         } 
/*     */       } 
/* 235 */       int i = 0;
/* 236 */       while (!validAudioSources.isEmpty()) {
/*     */         
/* 238 */         if (i < this.maxSourceCount) {
/*     */           
/* 240 */           ((AudioSource)validAudioSources.poll()).activate();
/*     */         }
/*     */         else {
/*     */           
/* 244 */           ((AudioSource)validAudioSources.poll()).cull();
/*     */         } 
/* 246 */         i++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioSource createSource(Object obj, String initialSoundFile) {
/* 254 */     return createSource(obj, PositionSpec.Center, initialSoundFile, false, false, this.defaultVolume);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioSource createSource(Object obj, PositionSpec positionSpec, String initialSoundFile, boolean loop, boolean priorized, float volume) {
/* 260 */     if (!this.enabled)
/*     */     {
/* 262 */       return null;
/*     */     }
/* 264 */     if (getVolumeForType(positionSpec) <= 0.0F)
/*     */     {
/* 266 */       return null;
/*     */     }
/* 268 */     if (this.soundSystem == null)
/*     */     {
/* 270 */       getSoundSystem();
/*     */     }
/* 272 */     if (this.soundSystem == null)
/*     */     {
/* 274 */       return null;
/*     */     }
/* 276 */     volume *= getMasterVolume();
/* 277 */     volume *= getVolumeForType(positionSpec);
/* 278 */     String sourceName = getSourceName(this.nextId);
/* 279 */     this.nextId++;
/* 280 */     AudioSource audioSource = new AudioSourceClient(this.soundSystem, sourceName, obj, positionSpec, initialSoundFile, loop, priorized, volume);
/* 281 */     if (!this.objectToAudioSourceMap.containsKey(obj))
/*     */     {
/* 283 */       this.objectToAudioSourceMap.put(obj, new LinkedList<>());
/*     */     }
/* 285 */     ((List<AudioSource>)this.objectToAudioSourceMap.get(obj)).add(audioSource);
/* 286 */     return audioSource;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSources(Object obj) {
/* 292 */     if (this.soundSystem == null) {
/*     */       return;
/*     */     }
/*     */     
/* 296 */     if (!this.objectToAudioSourceMap.containsKey(obj)) {
/*     */       return;
/*     */     }
/*     */     
/* 300 */     List<AudioSource> sources = this.objectToAudioSourceMap.get(obj);
/* 301 */     if (sources == null) {
/*     */       return;
/*     */     }
/*     */     
/* 305 */     for (AudioSource audioSource : sources)
/*     */     {
/* 307 */       audioSource.remove();
/*     */     }
/* 309 */     this.objectToAudioSourceMap.remove(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeMarked(Object obj) {
/* 314 */     if (this.soundSystem == null) {
/*     */       return;
/*     */     }
/*     */     
/* 318 */     if (!this.objectToAudioSourceMap.containsKey(obj)) {
/*     */       return;
/*     */     }
/*     */     
/* 322 */     List<AudioSource> source = new ArrayList<>();
/* 323 */     for (AudioSource audioSource : this.objectToAudioSourceMap.get(obj)) {
/*     */       
/* 325 */       if (((AudioSourceClient)audioSource).marked) {
/*     */         
/* 327 */         audioSource.remove();
/*     */         continue;
/*     */       } 
/* 330 */       source.add(audioSource);
/*     */     } 
/* 332 */     this.objectToAudioSourceMap.remove(obj);
/* 333 */     if (source.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 337 */     this.objectToAudioSourceMap.put(obj, source);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playOnce(Object obj, String soundFile) {
/* 343 */     playOnce(obj, PositionSpec.Center, soundFile, false, this.defaultVolume);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void playOnce(Object obj, PositionSpec positionSpec, String soundFile, boolean priorized, float volume) {
/* 349 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/*     */     
/* 353 */     if (getVolumeForType(positionSpec) <= 0.0F) {
/*     */       return;
/*     */     }
/*     */     
/* 357 */     if (this.soundSystem == null)
/*     */     {
/* 359 */       getSoundSystem();
/*     */     }
/* 361 */     if (this.soundSystem == null) {
/*     */       return;
/*     */     }
/*     */     
/* 365 */     AudioPosition position = AudioPosition.getFrom(obj, positionSpec);
/* 366 */     if (position == null) {
/*     */       return;
/*     */     }
/*     */     
/* 370 */     URL url = AudioSource.class.getClassLoader().getResource("assets/ic2/sounds/" + soundFile);
/* 371 */     if (url == null) {
/*     */       
/* 373 */       IC2.log.warn("Invalid sound file: " + soundFile);
/*     */       return;
/*     */     } 
/* 376 */     volume *= getMasterVolume();
/* 377 */     volume *= getVolumeForType(positionSpec);
/* 378 */     String sourceName = this.soundSystem.quickPlay(priorized, url, soundFile, false, position.x, position.y, position.z, 2, this.fadingDistance * Math.max(volume, 1.0F));
/* 379 */     this.soundSystem.setVolume(sourceName, this.masterVolume * Math.min(volume, 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMasterVolume() {
/* 385 */     return this.masterVolume;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDefaultVolume() {
/* 391 */     return 1.2F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getVolumeForType(PositionSpec type) {
/* 397 */     switch (type) {
/*     */       case Backpack:
/* 399 */         return this.currentBackpackMasterVolume;
/* 400 */       case Center: return this.blockMasterVolume;
/* 401 */       case Hand: return this.currentItemMasterVolume;
/*     */     } 
/* 403 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean testSourceCount(int n) {
/* 408 */     IntBuffer sourceBuffer = BufferUtils.createIntBuffer(n);
/*     */     
/*     */     try {
/* 411 */       AL10.alGenSources(sourceBuffer);
/* 412 */       if (AL10.alGetError() == 0)
/*     */       {
/* 414 */         AL10.alDeleteSources(sourceBuffer);
/* 415 */         return true;
/*     */       }
/*     */     
/* 418 */     } catch (Exception e) {
/*     */       
/* 420 */       AL10.alGetError();
/*     */     }
/* 422 */     catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
/*     */ 
/*     */     
/* 425 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void getSoundSystem() {
/*     */     try {
/* 432 */       this.soundSystem = (SoundSystem)ReflectionHelper.getPrivateValue(SoundManager.class, ReflectionHelper.getPrivateValue(SoundHandler.class, Minecraft.func_71410_x().func_147118_V(), 5), 4);
/*     */     }
/* 434 */     catch (Exception e) {
/*     */       
/* 436 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSourceName(int id) {
/* 442 */     return "ic2_cls" + id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_110549_a(IResourceManager par1) {
/* 448 */     List audioSourceCarriers = new ArrayList(this.objectToAudioSourceMap.keySet());
/* 449 */     for (Object obj : audioSourceCarriers)
/*     */     {
/* 451 */       removeSources(obj);
/*     */     }
/* 453 */     this.soundSystem = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\audio\AudioManagerClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */