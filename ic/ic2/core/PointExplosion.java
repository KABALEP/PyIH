/*     */ package ic2.core;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.ChunkPosition;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PointExplosion
/*     */ {
/*     */   private Random ExplosionRNG;
/*     */   private World worldObj;
/*     */   public int explosionX;
/*     */   public int explosionY;
/*     */   public int explosionZ;
/*     */   public Entity exploder;
/*     */   public float explosionSize;
/*     */   public float explosionDropRate;
/*     */   public float explosionDamage;
/*     */   public Set destroyedBlockPositions;
/*     */   private Explosion fakeExplosion;
/*     */   private DamageSource damagesource;
/*     */   
/*     */   public PointExplosion(World world, Entity entity, int x, int y, int z, float power, float drop, float entitydamage) {
/*  37 */     this.ExplosionRNG = new Random();
/*  38 */     this.destroyedBlockPositions = new HashSet();
/*  39 */     this.worldObj = world;
/*  40 */     this.exploder = entity;
/*  41 */     this.explosionSize = power;
/*  42 */     this.explosionDropRate = drop;
/*  43 */     this.explosionDamage = entitydamage;
/*  44 */     this.explosionX = x;
/*  45 */     this.explosionY = y;
/*  46 */     this.explosionZ = z;
/*  47 */     if (this.explosionX < 0)
/*     */     {
/*  49 */       this.explosionX--;
/*     */     }
/*  51 */     if (this.explosionZ < 0)
/*     */     {
/*  53 */       this.explosionZ--;
/*     */     }
/*  55 */     this.fakeExplosion = new Explosion(world, entity, x, y, z, power);
/*  56 */     this.damagesource = DamageSource.func_94539_a(this.fakeExplosion);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doExplosionA(int lowX, int lowY, int lowZ, int highX, int highY, int highZ) {
/*  61 */     for (int x = this.explosionX - lowX; x <= this.explosionX + highX; x++) {
/*     */       
/*  63 */       for (int y = this.explosionY - lowY; y <= this.explosionY + highY; y++) {
/*     */         
/*  65 */         for (int z = this.explosionZ - lowZ; z <= this.explosionZ + highZ; z++) {
/*     */           
/*  67 */           Block id = this.worldObj.func_147439_a(x, y, z);
/*  68 */           float resis = 0.0F;
/*  69 */           if (id != null)
/*     */           {
/*  71 */             resis = id.getExplosionResistance(this.exploder, this.worldObj, x, y, z, this.explosionX, this.explosionY, this.explosionZ);
/*     */           }
/*  73 */           if (this.explosionSize >= resis / 10.0F)
/*     */           {
/*  75 */             this.destroyedBlockPositions.add(new ChunkPosition(x, y, z));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  80 */     this.explosionSize *= 2.0F;
/*  81 */     int k = MathHelper.func_76128_c((this.explosionX - this.explosionSize) - 1.0D);
/*  82 */     int i1 = MathHelper.func_76128_c((this.explosionX + this.explosionSize) + 1.0D);
/*  83 */     int k2 = MathHelper.func_76128_c((this.explosionY - this.explosionSize) - 1.0D);
/*  84 */     int l1 = MathHelper.func_76128_c((this.explosionY + this.explosionSize) + 1.0D);
/*  85 */     int i2 = MathHelper.func_76128_c((this.explosionZ - this.explosionSize) - 1.0D);
/*  86 */     int j2 = MathHelper.func_76128_c((this.explosionZ + this.explosionSize) + 1.0D);
/*  87 */     List<Entity> list = this.worldObj.func_72839_b(this.exploder, AxisAlignedBB.func_72330_a(k, k2, i2, i1, l1, j2));
/*  88 */     Vec3 vec3d = Vec3.func_72443_a(this.explosionX, this.explosionY, this.explosionZ);
/*  89 */     for (int k3 = 0; k3 < list.size(); k3++) {
/*     */       
/*  91 */       Entity entity = list.get(k3);
/*  92 */       double d4 = entity.func_70011_f(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
/*  93 */       if (d4 <= 1.0D) {
/*     */         
/*  95 */         double d2 = entity.field_70165_t - this.explosionX;
/*  96 */         double d3 = entity.field_70163_u - this.explosionY;
/*  97 */         d4 = entity.field_70161_v - this.explosionZ;
/*  98 */         double d5 = MathHelper.func_76133_a(d2 * d2 + d3 * d3 + d4 * d4);
/*  99 */         d2 /= d5;
/* 100 */         d3 /= d5;
/* 101 */         d4 /= d5;
/* 102 */         double d6 = this.worldObj.func_72842_a(vec3d, entity.field_70121_D);
/* 103 */         double d7 = (1.0D - d4) * d6;
/* 104 */         entity.func_70097_a(this.damagesource, (int)(((d7 * d7 + d7) / 2.0D * 8.0D * this.explosionSize + 1.0D) * this.explosionDamage));
/* 105 */         double d8 = d7;
/* 106 */         Entity entity2 = entity;
/* 107 */         entity2.field_70159_w += d2 * d8;
/* 108 */         Entity entity3 = entity;
/* 109 */         entity3.field_70181_x += d3 * d8;
/* 110 */         Entity entity4 = entity;
/* 111 */         entity4.field_70179_y += d4 * d8;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doExplosionB(boolean flag) {
/* 118 */     this.worldObj.func_72908_a(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.field_73012_v.nextFloat() - this.worldObj.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/* 119 */     ArrayList<ChunkPosition> arraylist = new ArrayList();
/* 120 */     arraylist.addAll(this.destroyedBlockPositions);
/* 121 */     for (int i = arraylist.size() - 1; i >= 0; i--) {
/*     */       
/* 123 */       ChunkPosition chunkposition = arraylist.get(i);
/* 124 */       int j = chunkposition.field_151329_a;
/* 125 */       int k = chunkposition.field_151327_b;
/* 126 */       int l = chunkposition.field_151328_c;
/* 127 */       Block i2 = this.worldObj.func_147439_a(j, k, l);
/* 128 */       if (flag) {
/*     */         
/* 130 */         double d = (j + this.worldObj.field_73012_v.nextFloat());
/* 131 */         double d2 = (k + this.worldObj.field_73012_v.nextFloat());
/* 132 */         double d3 = (l + this.worldObj.field_73012_v.nextFloat());
/* 133 */         double d4 = d - this.explosionX;
/* 134 */         double d5 = d2 - this.explosionY;
/* 135 */         double d6 = d3 - this.explosionZ;
/* 136 */         double d7 = MathHelper.func_76133_a(d4 * d4 + d5 * d5 + d6 * d6);
/* 137 */         d4 /= d7;
/* 138 */         d5 /= d7;
/* 139 */         d6 /= d7;
/* 140 */         double d8 = 0.5D / (d7 / this.explosionSize + 0.1D);
/* 141 */         d8 *= (this.worldObj.field_73012_v.nextFloat() * this.worldObj.field_73012_v.nextFloat() + 0.3F);
/* 142 */         d4 *= d8;
/* 143 */         d5 *= d8;
/* 144 */         d6 *= d8;
/* 145 */         this.worldObj.func_72869_a("explode", (d + this.explosionX * 1.0D) / 2.0D, (d2 + this.explosionY * 1.0D) / 2.0D, (d3 + this.explosionZ * 1.0D) / 2.0D, d4, d5, d6);
/* 146 */         this.worldObj.func_72869_a("smoke", d, d2, d3, d4, d5, d6);
/*     */       } 
/* 148 */       if (i2 != null) {
/*     */         
/* 150 */         i2.func_149690_a(this.worldObj, j, k, l, this.worldObj.func_72805_g(j, k, l), this.explosionDropRate, 0);
/* 151 */         this.worldObj.func_147468_f(j, k, l);
/* 152 */         i2.func_149723_a(this.worldObj, j, k, l, this.fakeExplosion);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\PointExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */