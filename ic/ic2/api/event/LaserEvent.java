/*     */ package ic2.api.event;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Cancelable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Cancelable
/*     */ public class LaserEvent
/*     */   extends WorldEvent
/*     */ {
/*     */   public final Entity lasershot;
/*     */   public EntityLivingBase owner;
/*     */   public float range;
/*     */   public float power;
/*     */   public int blockBreaks;
/*     */   public boolean explosive;
/*     */   public boolean smelt;
/*     */   
/*     */   public LaserEvent(World world1, Entity lasershot1, EntityLivingBase owner1, float range1, float power1, int blockBreaks1, boolean explosive1, boolean smelt1) {
/*  31 */     super(world1);
/*  32 */     this.lasershot = lasershot1;
/*  33 */     this.owner = owner1;
/*  34 */     this.range = range1;
/*  35 */     this.power = power1;
/*  36 */     this.blockBreaks = blockBreaks1;
/*  37 */     this.explosive = explosive1;
/*  38 */     this.smelt = smelt1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LaserShootEvent
/*     */     extends LaserEvent
/*     */   {
/*     */     ItemStack laseritem;
/*     */ 
/*     */     
/*     */     public LaserShootEvent(World world1, Entity lasershot1, EntityLivingBase owner1, float range1, float power1, int blockBreaks1, boolean explosive1, boolean smelt1, ItemStack laseritem1) {
/*  50 */       super(world1, lasershot1, owner1, range1, power1, blockBreaks1, explosive1, smelt1);
/*  51 */       this.laseritem = laseritem1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class LaserExplodesEvent
/*     */     extends LaserEvent
/*     */   {
/*     */     public float explosionpower;
/*     */     
/*     */     public float explosiondroprate;
/*     */     public float explosionentitydamage;
/*     */     
/*     */     public LaserExplodesEvent(World world1, Entity lasershot1, EntityLivingBase owner1, float range1, float power1, int blockBreaks1, boolean explosive1, boolean smelt1, float explosionpower1, float explosiondroprate1, float explosionentitydamage1) {
/*  65 */       super(world1, lasershot1, owner1, range1, power1, blockBreaks1, explosive1, smelt1);
/*  66 */       this.explosionpower = explosionpower1;
/*  67 */       this.explosiondroprate = explosiondroprate1;
/*  68 */       this.explosionentitydamage = explosionentitydamage1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class LaserHitsBlockEvent
/*     */     extends LaserEvent
/*     */   {
/*     */     public int x;
/*     */     
/*     */     public int y;
/*     */     
/*     */     public int z;
/*     */     
/*     */     public int side;
/*     */     public boolean removeBlock;
/*     */     public boolean dropBlock;
/*     */     public float dropChance;
/*     */     
/*     */     public LaserHitsBlockEvent(World world1, Entity lasershot1, EntityLivingBase owner1, float range1, float power1, int blockBreaks1, boolean explosive1, boolean smelt1, int x1, int y1, int z1, int side1, float dropChance1, boolean removeBlock1, boolean dropBlock1) {
/*  88 */       super(world1, lasershot1, owner1, range1, power1, blockBreaks1, explosive1, smelt1);
/*  89 */       this.x = x1;
/*  90 */       this.y = y1;
/*  91 */       this.z = z1;
/*  92 */       this.side = side1;
/*  93 */       this.removeBlock = removeBlock1;
/*  94 */       this.dropBlock = dropBlock1;
/*  95 */       this.dropChance = dropChance1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LaserHitsEntityEvent
/*     */     extends LaserEvent
/*     */   {
/*     */     public Entity hitentity;
/*     */ 
/*     */ 
/*     */     
/*     */     public LaserHitsEntityEvent(World world1, Entity lasershot1, EntityLivingBase owner1, float range1, float power1, int blockBreaks1, boolean explosive1, boolean smelt1, Entity hitentity1) {
/* 110 */       super(world1, lasershot1, owner1, range1, power1, blockBreaks1, explosive1, smelt1);
/* 111 */       this.hitentity = hitentity1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\event\LaserEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */