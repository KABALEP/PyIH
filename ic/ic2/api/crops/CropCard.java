/*     */ package ic2.api.crops;
/*     */ 
/*     */ import cpw.mods.fml.common.Loader;
/*     */ import cpw.mods.fml.common.ModContainer;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CropCard
/*     */ {
/*     */   @SideOnly(Side.CLIENT)
/*     */   protected IIcon[] textures;
/*  22 */   private final String modId = getModId();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String name();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String owner() {
/*  50 */     return this.modId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String displayName() {
/*  63 */     return name();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  72 */     return "unknown";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String desc(int i) {
/*  83 */     String[] att = attributes();
/*     */     
/*  85 */     if (att == null || att.length == 0) return "";
/*     */     
/*  87 */     if (i == 0) {
/*  88 */       String str = att[0];
/*  89 */       if (att.length >= 2) {
/*  90 */         str = str + ", " + att[1];
/*  91 */         if (att.length >= 3) str = str + ","; 
/*     */       } 
/*  93 */       return str;
/*     */     } 
/*  95 */     if (att.length < 3) return ""; 
/*  96 */     String s = att[2];
/*  97 */     if (att.length >= 4) s = s + ", " + att[3]; 
/*  98 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getrootslength(ICropTile crop) {
/* 107 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int tier();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int stat(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String[] attributes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int maxSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSprites(IIconRegister iconRegister) {
/* 159 */     this.textures = new IIcon[maxSize()];
/*     */     
/* 161 */     for (int i = 1; i <= this.textures.length; i++)
/*     */     {
/* 163 */       this.textures[i - 1] = iconRegister.func_94245_a("ic2:crop/blockCrop." + name() + "." + i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getSprite(ICropTile crop) {
/* 175 */     if (crop.getSize() <= 0 || crop.getSize() > this.textures.length) return null;
/*     */     
/* 177 */     return this.textures[crop.getSize() - 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 185 */     return tier() * 200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean canGrow(ICropTile paramICropTile);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air) {
/* 218 */     return (int)(humidity + nutrients + air);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCross(ICropTile crop) {
/* 228 */     return (crop.getSize() >= 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rightclick(ICropTile crop, EntityPlayer player) {
/* 243 */     return crop.harvest(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getOptimalHavestSize(ICropTile paramICropTile);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean canBeHarvested(ICropTile paramICropTile);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float dropGainChance() {
/* 270 */     return (float)Math.pow(0.95D, tier());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract ItemStack getGain(ICropTile paramICropTile);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 288 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean leftclick(ICropTile crop, EntityPlayer player) {
/* 302 */     return crop.pick(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float dropSeedChance(ICropTile crop) {
/* 313 */     if (crop.getSize() == 1) return 0.0F; 
/* 314 */     float base = 0.5F;
/* 315 */     if (crop.getSize() == 2) base /= 2.0F; 
/* 316 */     for (int i = 0; i < tier(); ) { base = (float)(base * 0.8D); i++; }
/* 317 */      return base;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSeeds(ICropTile crop) {
/* 329 */     return crop.generateSeeds(crop.getCrop(), crop.getGrowth(), crop.getGain(), crop.getResistance(), crop.getScanLevel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighbourChange(ICropTile crop) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int emitRedstone(ICropTile crop) {
/* 346 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyed(ICropTile crop) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEmittedLight(ICropTile crop) {
/* 364 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 375 */     if (entity instanceof EntityLivingBase) {
/* 376 */       return ((EntityLivingBase)entity).func_70051_ag();
/*     */     }
/*     */     
/* 379 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(ICropTile crop) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWeed(ICropTile crop) {
/* 401 */     return (crop.getSize() >= 2 && (crop
/* 402 */       .getCrop() == Crops.weed || crop.getGrowth() >= 24));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final int getId() {
/* 414 */     return Crops.instance.getIdFor(this);
/*     */   }
/*     */   
/*     */   private static String getModId() {
/* 418 */     ModContainer modContainer = Loader.instance().activeModContainer();
/*     */     
/* 420 */     if (modContainer != null) {
/* 421 */       return modContainer.getModId();
/*     */     }
/*     */     
/*     */     assert false;
/*     */     
/* 426 */     return "unknown";
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\crops\CropCard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */