/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.api.tile.ExplosionWhitelist;
/*     */ import ic2.core.item.armor.ItemArmorHazmat;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkPosition;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExplosionIC2
/*     */ {
/*     */   private Random ExplosionRNG;
/*     */   private World worldObj;
/*     */   private int mapHeight;
/*     */   public double explosionX;
/*     */   public double explosionY;
/*     */   public double explosionZ;
/*     */   public Entity exploder;
/*     */   public float power;
/*     */   public float explosionDropRate;
/*     */   public float explosionDamage;
/*     */   public DamageSource damageSource;
/*     */   public String igniter;
/*     */   public List<EntityLivingBase> entitiesInRange;
/*     */   public Map vecMap;
/*     */   public Map<ChunkPosition, Boolean> destroyedBlockPositions;
/*  48 */   private double dropPowerLimit = 8.0D;
/*  49 */   private int secondaryRayCount = 5;
/*     */   
/*     */   public Explosion fakeExplosion;
/*     */   
/*     */   public ExplosionIC2(World world, Entity entity, double d, double d1, double d2, float power, float drop, float entitydamage, DamageSource damagesource) {
/*  54 */     this.ExplosionRNG = new Random();
/*  55 */     this.vecMap = new HashMap<>();
/*  56 */     this.destroyedBlockPositions = new HashMap<>();
/*  57 */     this.worldObj = world;
/*  58 */     this.mapHeight = IC2.getWorldHeight(world);
/*  59 */     this.exploder = entity;
/*  60 */     this.power = power;
/*  61 */     this.explosionDropRate = drop;
/*  62 */     this.explosionDamage = entitydamage;
/*  63 */     this.explosionX = d;
/*  64 */     this.explosionY = d1;
/*  65 */     this.explosionZ = d2;
/*  66 */     this.damageSource = damagesource;
/*  67 */     this.fakeExplosion = new Explosion(world, entity, d2, d1, d2, power);
/*  68 */     if (damagesource == null)
/*     */     {
/*  70 */       this.damageSource = DamageSource.func_94539_a(this.fakeExplosion);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ExplosionIC2(World world, Entity entity, double d, double d1, double d2, float power, float drop, float entitydamage) {
/*  76 */     this(world, entity, d, d1, d2, power, drop, entitydamage, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ExplosionIC2(World world, Entity entity, double d, double d1, double d2, float power, float drop, float entitydamage, DamageSource damagesource, String igniter) {
/*  81 */     this(world, entity, d, d1, d2, power, drop, entitydamage, damagesource);
/*  82 */     this.igniter = igniter;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doExplosion() {
/*  87 */     if (this.power <= 0.0F) {
/*     */       return;
/*     */     }
/*     */     
/*  91 */     double maxDistance = this.power / 0.4D;
/*  92 */     this.entitiesInRange = this.worldObj.func_72872_a(EntityLivingBase.class, AxisAlignedBB.func_72330_a(this.explosionX - maxDistance, this.explosionY - maxDistance, this.explosionZ - maxDistance, this.explosionX + maxDistance, this.explosionY + maxDistance, this.explosionZ + maxDistance));
/*  93 */     for (int steps = (int)Math.ceil(Math.PI / Math.atan(1.0D / maxDistance)), phi_n = 0; phi_n < 2 * steps; phi_n++) {
/*     */       
/*  95 */       for (int theta_n = 0; theta_n < steps; theta_n++) {
/*     */         
/*  97 */         double phi = 6.283185307179586D / steps * phi_n;
/*  98 */         double theta = Math.PI / steps * theta_n;
/*  99 */         shootRay(this.explosionX, this.explosionY, this.explosionZ, phi, theta, this.power, (phi_n % 8 == 0 && theta_n % 8 == 0));
/*     */       } 
/*     */     } 
/* 102 */     if (this.damageSource == IC2DamageSource.nuke)
/*     */     {
/* 104 */       for (EntityLivingBase entity : this.worldObj.func_72872_a(EntityLivingBase.class, AxisAlignedBB.func_72330_a(this.explosionX - 100.0D, this.explosionY - 100.0D, this.explosionZ - 100.0D, this.explosionX + 100.0D, this.explosionY + 100.0D, this.explosionZ + 100.0D))) {
/*     */         
/* 106 */         if (!ItemArmorHazmat.hasCompleteHazmat(entity)) {
/*     */           
/* 108 */           double distance = entity.func_70011_f(this.explosionX, this.explosionY, this.explosionZ);
/* 109 */           int hungerLength = (int)(120.0D * (100.0D - distance));
/* 110 */           int poisonLength = (int)(80.0D * (30.0D - distance));
/* 111 */           if (hungerLength >= 0)
/*     */           {
/* 113 */             entity.func_70690_d(new PotionEffect(Potion.field_76438_s.field_76415_H, hungerLength, 0));
/*     */           }
/* 115 */           if (poisonLength < 0) {
/*     */             continue;
/*     */           }
/*     */           
/* 119 */           entity.func_70690_d(new PotionEffect(IC2Potion.radiation.field_76415_H, poisonLength, 0));
/*     */         } 
/*     */       } 
/*     */     }
/* 123 */     ((NetworkManager)IC2.network.get()).initiateExplosionEffect(this.worldObj, this.explosionX, this.explosionY, this.explosionZ, (this.damageSource == IC2DamageSource.nuke));
/* 124 */     Map<XZposition, Map<ItemWithMeta, DropData>> blocksToDrop = new HashMap<>();
/* 125 */     for (Map.Entry<ChunkPosition, Boolean> entry : this.destroyedBlockPositions.entrySet()) {
/*     */       
/* 127 */       int x = ((ChunkPosition)entry.getKey()).field_151329_a;
/* 128 */       int y = ((ChunkPosition)entry.getKey()).field_151327_b;
/* 129 */       int z = ((ChunkPosition)entry.getKey()).field_151328_c;
/* 130 */       Block blockId = this.worldObj.func_147439_a(x, y, z);
/* 131 */       if (blockId != null) {
/*     */         
/* 133 */         if (((Boolean)entry.getValue()).booleanValue()) {
/*     */           
/* 135 */           double effectX = (x + this.worldObj.field_73012_v.nextFloat());
/* 136 */           double effectY = (y + this.worldObj.field_73012_v.nextFloat());
/* 137 */           double effectZ = (z + this.worldObj.field_73012_v.nextFloat());
/* 138 */           double d3 = effectX - this.explosionX;
/* 139 */           double d2 = effectY - this.explosionY;
/* 140 */           d3 = effectZ - this.explosionZ;
/* 141 */           double effectDistance = MathHelper.func_76133_a(d3 * d3 + d2 * d2 + d3 * d3);
/* 142 */           d3 /= effectDistance;
/* 143 */           d2 /= effectDistance;
/* 144 */           d3 /= effectDistance;
/* 145 */           double d4 = 0.5D / (effectDistance / this.power + 0.1D);
/* 146 */           d4 *= (this.worldObj.field_73012_v.nextFloat() * this.worldObj.field_73012_v.nextFloat() + 0.3F);
/* 147 */           d3 *= d4;
/* 148 */           d2 *= d4;
/* 149 */           d3 *= d4;
/* 150 */           this.worldObj.func_72869_a("explode", (effectX + this.explosionX) / 2.0D, (effectY + this.explosionY) / 2.0D, (effectZ + this.explosionZ) / 2.0D, d3, d2, d3);
/* 151 */           this.worldObj.func_72869_a("smoke", effectX, effectY, effectZ, d3, d2, d3);
/* 152 */           Block block = blockId;
/* 153 */           if (this.worldObj.field_73012_v.nextFloat() <= this.explosionDropRate) {
/*     */             
/* 155 */             int meta = this.worldObj.func_72805_g(x, y, z);
/* 156 */             for (ItemStack itemStack : block.getDrops(this.worldObj, x, y, z, meta, 0)) {
/*     */               
/* 158 */               XZposition xZposition = new XZposition(x / 2, z / 2);
/* 159 */               if (!blocksToDrop.containsKey(xZposition))
/*     */               {
/* 161 */                 blocksToDrop.put(xZposition, new HashMap<>());
/*     */               }
/* 163 */               Map<ItemWithMeta, DropData> map = blocksToDrop.get(xZposition);
/* 164 */               ItemWithMeta itemWithMeta = new ItemWithMeta(itemStack.func_77973_b(), itemStack.func_77960_j());
/* 165 */               if (!map.containsKey(itemWithMeta)) {
/*     */                 
/* 167 */                 map.put(itemWithMeta, new DropData(itemStack.field_77994_a, y));
/*     */                 
/*     */                 continue;
/*     */               } 
/* 171 */               map.put(itemWithMeta, ((DropData)map.get(itemWithMeta)).add(itemStack.field_77994_a, y));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 176 */         this.worldObj.func_147468_f(x, y, z);
/* 177 */         blockId.func_149723_a(this.worldObj, x, y, z, this.fakeExplosion);
/*     */       } 
/*     */     } 
/* 180 */     for (Map.Entry<XZposition, Map<ItemWithMeta, DropData>> entry2 : blocksToDrop.entrySet()) {
/*     */       
/* 182 */       XZposition xZposition2 = entry2.getKey();
/* 183 */       for (Map.Entry entry3 : ((Map)entry2.getValue()).entrySet()) {
/*     */         
/* 185 */         ItemWithMeta itemWithMeta2 = (ItemWithMeta)entry3.getKey();
/*     */         int count;
/* 187 */         for (count = ((DropData)entry3.getValue()).n; count > 0; count -= stackSize) {
/*     */           
/* 189 */           int stackSize = Math.min(count, 64);
/* 190 */           EntityItem entityitem = new EntityItem(this.worldObj, (xZposition2.x + this.worldObj.field_73012_v.nextFloat()) * 2.0D, ((DropData)entry3.getValue()).maxY + 0.5D, (xZposition2.z + this.worldObj.field_73012_v.nextFloat()) * 2.0D, new ItemStack(itemWithMeta2.itemId, stackSize, itemWithMeta2.metaData));
/* 191 */           entityitem.field_145804_b = 10;
/* 192 */           this.worldObj.func_72838_d((Entity)entityitem);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void shootRay(double x, double y, double z, double phi, double theta, double power, boolean killEntities) {
/* 200 */     double deltaX = Math.sin(theta) * Math.cos(phi);
/* 201 */     double deltaY = Math.cos(theta);
/* 202 */     double deltaZ = Math.sin(theta) * Math.sin(phi);
/*     */     
/*     */     do {
/* 205 */       boolean isAir = this.worldObj.func_147437_c((int)x, (int)y, (int)z);
/* 206 */       Block blockId = this.worldObj.func_147439_a((int)x, (int)y, (int)z);
/* 207 */       double absorption = 0.5D;
/* 208 */       if (!isAir)
/*     */       {
/* 210 */         absorption += (blockId.getExplosionResistance(this.exploder, this.worldObj, (int)x, (int)y, (int)z, this.explosionX, this.explosionY, this.explosionZ) + 4.0D) * 0.3D;
/*     */       }
/* 212 */       if (absorption > 1000.0D && !ExplosionWhitelist.isBlockWhitelisted(blockId)) {
/*     */         
/* 214 */         absorption = 0.5D;
/*     */       }
/*     */       else {
/*     */         
/* 218 */         if (absorption > power) {
/*     */           break;
/*     */         }
/*     */         
/* 222 */         if (!isAir) {
/*     */           
/* 224 */           ChunkPosition position = new ChunkPosition((int)x, (int)y, (int)z);
/* 225 */           if (!this.destroyedBlockPositions.containsKey(position) || (power > 8.0D && ((Boolean)this.destroyedBlockPositions.get(position)).booleanValue()))
/*     */           {
/* 227 */             this.destroyedBlockPositions.put(position, Boolean.valueOf((power <= 8.0D)));
/*     */           }
/*     */         } 
/*     */       } 
/* 231 */       if (killEntities) {
/*     */         
/* 233 */         Iterator<EntityLivingBase> it = this.entitiesInRange.iterator();
/* 234 */         while (it.hasNext()) {
/*     */           
/* 236 */           EntityLivingBase entity = it.next();
/* 237 */           if ((entity.field_70165_t - x) * (entity.field_70165_t - x) + (entity.field_70163_u - y) * (entity.field_70163_u - y) + (entity.field_70161_v - z) * (entity.field_70161_v - z) <= 25.0D) {
/*     */             
/* 239 */             double dx = ((Entity)entity).field_70165_t - this.explosionX;
/* 240 */             double dy = ((Entity)entity).field_70163_u - this.explosionY;
/* 241 */             double dz = ((Entity)entity).field_70161_v - this.explosionZ;
/* 242 */             double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
/* 243 */             double distanceFactor = power / 2.0D / (Math.pow(distance, 0.8D) + 1.0D);
/* 244 */             entity.func_70097_a(this.damageSource, (int)Math.pow(distanceFactor * 3.0D, 2.0D));
/* 245 */             if (this.damageSource == IC2DamageSource.nuke && entity instanceof EntityPlayer && this.igniter != null && ((EntityPlayer)entity).func_146103_bH().getName().equals(this.igniter) && entity.func_110143_aJ() <= 0.0F)
/*     */             {
/* 247 */               IC2.achievements.issueAchievement((EntityPlayer)entity, "dieFromOwnNuke");
/*     */             }
/* 249 */             dx /= distance;
/* 250 */             dy /= distance;
/* 251 */             dz /= distance;
/* 252 */             entity.field_70159_w += dx * distanceFactor;
/* 253 */             entity.field_70181_x += dy * distanceFactor;
/* 254 */             entity.field_70179_y += dz * distanceFactor;
/* 255 */             it.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/* 259 */       if (absorption > 10.0D)
/*     */       {
/* 261 */         for (int i = 0; i < 5; i++)
/*     */         {
/* 263 */           shootRay(x, y, z, this.ExplosionRNG.nextDouble() * 2.0D * Math.PI, this.ExplosionRNG.nextDouble() * Math.PI, absorption * 0.4D, false);
/*     */         }
/*     */       }
/* 266 */       power -= absorption;
/* 267 */       x += deltaX;
/* 268 */       y += deltaY;
/* 269 */       z += deltaZ;
/* 270 */       if (y <= 0.0D)
/*     */       {
/*     */         break;
/*     */       }
/*     */     }
/* 275 */     while (y < this.mapHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   static class DropData
/*     */   {
/*     */     int n;
/*     */     
/*     */     int maxY;
/*     */     
/*     */     DropData(int n, int y) {
/* 286 */       this.n = n;
/* 287 */       this.maxY = y;
/*     */     }
/*     */ 
/*     */     
/*     */     public DropData add(int n, int y) {
/* 292 */       this.n += n;
/* 293 */       if (y > this.maxY)
/*     */       {
/* 295 */         this.maxY = y;
/*     */       }
/* 297 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class ItemWithMeta
/*     */   {
/*     */     Item itemId;
/*     */     
/*     */     int metaData;
/*     */     
/*     */     ItemWithMeta(Item itemId, int metaData) {
/* 309 */       this.itemId = itemId;
/* 310 */       this.metaData = metaData;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 316 */       if (obj instanceof ItemWithMeta) {
/*     */         
/* 318 */         ItemWithMeta itemWithMeta = (ItemWithMeta)obj;
/* 319 */         return (itemWithMeta.itemId == this.itemId && itemWithMeta.metaData == this.metaData);
/*     */       } 
/* 321 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 327 */       return this.itemId.hashCode() * 31 ^ this.metaData;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class XZposition
/*     */   {
/*     */     int x;
/*     */     
/*     */     int z;
/*     */     
/*     */     XZposition(int x, int z) {
/* 339 */       this.x = x;
/* 340 */       this.z = z;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 346 */       if (obj instanceof XZposition) {
/*     */         
/* 348 */         XZposition xZposition = (XZposition)obj;
/* 349 */         return (xZposition.x == this.x && xZposition.z == this.z);
/*     */       } 
/* 351 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 357 */       return this.x * 31 ^ this.z;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ExplosionIC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */