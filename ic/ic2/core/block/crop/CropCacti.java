/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChunkCoordinates;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropCacti
/*     */   extends CropCardBase
/*     */ {
/*     */   public String discoveredBy() {
/*  22 */     return "SpwnX";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String name() {
/*  28 */     return "Cacti";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  34 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  40 */     switch (n) {
/*     */       case 0:
/*  42 */         return 0;
/*  43 */       case 1: return 0;
/*  44 */       case 2: return 4;
/*  45 */       case 3: return 4;
/*  46 */       case 4: return 0;
/*     */     } 
/*  48 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  54 */     return new String[] { "Green", "Cacti", "Thorns" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/*  60 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  66 */     return (crop.getAirQuality() >= 2 && crop.getSize() < 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/*  72 */     return 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  78 */     return (crop.getSize() == maxSize());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  84 */     int count = 1;
/*  85 */     if ((crop.getWorld()).field_73012_v.nextInt(3) == 0)
/*     */     {
/*  87 */       count = 2;
/*     */     }
/*  89 */     return new ItemStack(Blocks.field_150434_aF, count);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/*  95 */     return 300;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 101 */     if (entity instanceof net.minecraft.entity.item.EntityItem)
/*     */     {
/* 103 */       return super.onEntityCollision(crop, entity);
/*     */     }
/* 105 */     if (!(crop.getWorld()).field_72995_K)
/*     */     {
/* 107 */       entity.func_70097_a(DamageSource.field_76367_g, 1.0F);
/*     */     }
/* 109 */     return super.onEntityCollision(crop, entity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int weightInfluences(ICropTile crop, float humidity, float nutrients, float air) {
/* 115 */     int count = super.weightInfluences(crop, humidity, nutrients, air);
/* 116 */     count = (int)(count + 10.0F * getBiomeTemperature(crop));
/* 117 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBiomeTemperature(ICropTile crop) {
/* 122 */     ChunkCoordinates coord = crop.getLocation();
/* 123 */     BiomeGenBase base = crop.getWorld().func_72807_a(coord.field_71574_a, coord.field_71573_c);
/* 124 */     return base.func_150564_a(coord.field_71574_a, coord.field_71572_b, coord.field_71573_c) - 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getSprite(ICropTile crop) {
/* 131 */     return Ic2Icons.getTexture("bc")[57 + crop.getSize()];
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerSprites(IIconRegister iconRegister) {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropCacti.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */