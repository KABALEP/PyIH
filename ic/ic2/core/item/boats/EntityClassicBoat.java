/*     */ package ic2.core.item.boats;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityClassicBoat
/*     */   extends Entity
/*     */ {
/*     */   private boolean isBoatEmpty;
/*     */   private double speedMultiplier;
/*     */   private int boatPosRotationIncrements;
/*     */   private double boatX;
/*     */   private double boatY;
/*     */   private double boatZ;
/*     */   private double boatYaw;
/*     */   private double boatPitch;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityX;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityY;
/*     */   @SideOnly(Side.CLIENT)
/*     */   private double velocityZ;
/*     */   private static final String __OBFID = "CL_00001667";
/*     */   
/*     */   public EntityClassicBoat(World world) {
/*  44 */     super(world);
/*  45 */     this.isBoatEmpty = true;
/*  46 */     this.speedMultiplier = 0.07D;
/*  47 */     this.field_70156_m = true;
/*  48 */     func_70105_a(1.5F, 0.6F);
/*  49 */     this.field_70129_M = this.field_70131_O / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityClassicBoat(World world, double x, double y, double z) {
/*  54 */     this(world);
/*  55 */     func_70107_b(x, y + this.field_70129_M, z);
/*  56 */     this.field_70159_w = 0.0D;
/*  57 */     this.field_70181_x = 0.0D;
/*  58 */     this.field_70179_y = 0.0D;
/*  59 */     this.field_70169_q = x;
/*  60 */     this.field_70167_r = y;
/*  61 */     this.field_70166_s = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  75 */     this.field_70180_af.func_75682_a(16, new Float(0.0F));
/*  76 */     this.field_70180_af.func_75682_a(17, new Integer(0));
/*  77 */     this.field_70180_af.func_75682_a(18, new Integer(1));
/*  78 */     this.field_70180_af.func_75682_a(19, new Float(0.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70114_g(Entity entity) {
/*  88 */     return entity.field_70121_D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_70046_E() {
/*  96 */     return this.field_70121_D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70104_M() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double func_70042_X() {
/* 114 */     return this.field_70131_O * 0.0D - 0.30000001192092896D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70097_a(DamageSource source, float damage) {
/* 122 */     if (func_85032_ar())
/*     */     {
/* 124 */       return false;
/*     */     }
/* 126 */     if (this.field_70178_ae && source.func_76347_k())
/*     */     {
/* 128 */       return false;
/*     */     }
/* 130 */     if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
/*     */       
/* 132 */       setForwardDirection(-getForwardDirection());
/* 133 */       setTimeSinceHit(10);
/* 134 */       setDamageTaken(getDamageTaken() + damage * 10.0F);
/* 135 */       func_70018_K();
/* 136 */       boolean flag = (source.func_76346_g() instanceof EntityPlayer && ((EntityPlayer)source.func_76346_g()).field_71075_bZ.field_75098_d);
/*     */       
/* 138 */       if (flag || getDamageTaken() > 40.0F) {
/*     */         
/* 140 */         if (this.field_70153_n != null)
/*     */         {
/* 142 */           this.field_70153_n.func_70078_a(this);
/*     */         }
/*     */         
/* 145 */         if (!flag)
/*     */         {
/* 147 */           onPlayerBreaking();
/*     */         }
/*     */         
/* 150 */         func_70106_y();
/*     */       } 
/*     */       
/* 153 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70057_ab() {
/* 168 */     setForwardDirection(-getForwardDirection());
/* 169 */     setTimeSinceHit(10);
/* 170 */     setDamageTaken(getDamageTaken() * 11.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/* 179 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70056_a(double x, double y, double z, float yaw, float pitch, int p_70056_9_) {
/* 189 */     if (this.isBoatEmpty) {
/*     */       
/* 191 */       this.boatPosRotationIncrements = p_70056_9_ + 5;
/*     */     }
/*     */     else {
/*     */       
/* 195 */       double d3 = x - this.field_70165_t;
/* 196 */       double d4 = y - this.field_70163_u;
/* 197 */       double d5 = z - this.field_70161_v;
/* 198 */       double d6 = d3 * d3 + d4 * d4 + d5 * d5;
/*     */       
/* 200 */       if (d6 <= 1.0D) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 205 */       this.boatPosRotationIncrements = 3;
/*     */     } 
/*     */     
/* 208 */     this.boatX = x;
/* 209 */     this.boatY = y;
/* 210 */     this.boatZ = z;
/* 211 */     this.boatYaw = yaw;
/* 212 */     this.boatPitch = pitch;
/* 213 */     this.field_70159_w = this.velocityX;
/* 214 */     this.field_70181_x = this.velocityY;
/* 215 */     this.field_70179_y = this.velocityZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_70016_h(double x, double y, double z) {
/* 224 */     this.velocityX = this.field_70159_w = x;
/* 225 */     this.velocityY = this.field_70181_x = y;
/* 226 */     this.velocityZ = this.field_70179_y = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 234 */     super.func_70071_h_();
/*     */     
/* 236 */     if (getTimeSinceHit() > 0)
/*     */     {
/* 238 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*     */     }
/*     */     
/* 241 */     if (getDamageTaken() > 0.0F)
/*     */     {
/* 243 */       setDamageTaken(getDamageTaken() - 1.0F);
/*     */     }
/*     */     
/* 246 */     this.field_70169_q = this.field_70165_t;
/* 247 */     this.field_70167_r = this.field_70163_u;
/* 248 */     this.field_70166_s = this.field_70161_v;
/* 249 */     byte b0 = 5;
/* 250 */     double d0 = 0.0D;
/*     */     
/* 252 */     for (int i = 0; i < b0; i++) {
/*     */       
/* 254 */       double minY = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (i + 0) / b0 - 0.125D;
/* 255 */       double maxY = this.field_70121_D.field_72338_b + (this.field_70121_D.field_72337_e - this.field_70121_D.field_72338_b) * (i + 1) / b0 - 0.125D;
/* 256 */       AxisAlignedBB box = AxisAlignedBB.func_72330_a(this.field_70121_D.field_72340_a, minY, this.field_70121_D.field_72339_c, this.field_70121_D.field_72336_d, maxY, this.field_70121_D.field_72334_f);
/*     */       
/* 258 */       if (isOnWater(box))
/*     */       {
/* 260 */         d0 += 1.0D / b0;
/*     */       }
/*     */     } 
/*     */     
/* 264 */     double d10 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     if (d10 > 0.26249999999999996D) {
/*     */       
/* 271 */       double d2 = Math.cos(this.field_70177_z * Math.PI / 180.0D);
/* 272 */       double d4 = Math.sin(this.field_70177_z * Math.PI / 180.0D);
/*     */       
/* 274 */       for (int j = 0; j < 1.0D + d10 * 60.0D; j++) {
/*     */         
/* 276 */         double d5 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F);
/* 277 */         double d6 = (this.field_70146_Z.nextInt(2) * 2 - 1) * 0.7D;
/*     */ 
/*     */ 
/*     */         
/* 281 */         if (this.field_70146_Z.nextBoolean()) {
/*     */           
/* 283 */           double d8 = this.field_70165_t - d2 * d5 * 0.8D + d4 * d6;
/* 284 */           double d9 = this.field_70161_v - d4 * d5 * 0.8D - d2 * d6;
/* 285 */           this.field_70170_p.func_72869_a("splash", d8, this.field_70163_u - 0.125D, d9, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */         }
/*     */         else {
/*     */           
/* 289 */           double d8 = this.field_70165_t + d2 + d4 * d5 * 0.7D;
/* 290 */           double d9 = this.field_70161_v + d4 - d2 * d5 * 0.7D;
/* 291 */           this.field_70170_p.func_72869_a("splash", d8, this.field_70163_u - 0.125D, d9, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     if (this.field_70170_p.field_72995_K && this.isBoatEmpty) {
/*     */       
/* 301 */       if (this.boatPosRotationIncrements > 0)
/*     */       {
/* 303 */         double d2 = this.field_70165_t + (this.boatX - this.field_70165_t) / this.boatPosRotationIncrements;
/* 304 */         double d4 = this.field_70163_u + (this.boatY - this.field_70163_u) / this.boatPosRotationIncrements;
/* 305 */         double d11 = this.field_70161_v + (this.boatZ - this.field_70161_v) / this.boatPosRotationIncrements;
/* 306 */         double d12 = MathHelper.func_76138_g(this.boatYaw - this.field_70177_z);
/* 307 */         this.field_70177_z = (float)(this.field_70177_z + d12 / this.boatPosRotationIncrements);
/* 308 */         this.field_70125_A = (float)(this.field_70125_A + (this.boatPitch - this.field_70125_A) / this.boatPosRotationIncrements);
/* 309 */         this.boatPosRotationIncrements--;
/* 310 */         func_70107_b(d2, d4, d11);
/* 311 */         func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       }
/*     */       else
/*     */       {
/* 315 */         double d2 = this.field_70165_t + this.field_70159_w;
/* 316 */         double d4 = this.field_70163_u + this.field_70181_x;
/* 317 */         double d11 = this.field_70161_v + this.field_70179_y;
/* 318 */         func_70107_b(d2, d4, d11);
/*     */         
/* 320 */         if (this.field_70122_E) {
/*     */           
/* 322 */           this.field_70159_w *= 0.5D;
/* 323 */           this.field_70181_x *= 0.5D;
/* 324 */           this.field_70179_y *= 0.5D;
/*     */         } 
/*     */         
/* 327 */         this.field_70159_w *= 0.9900000095367432D;
/* 328 */         this.field_70181_x *= 0.949999988079071D;
/* 329 */         this.field_70179_y *= 0.9900000095367432D;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 334 */       if (d0 < 1.0D) {
/*     */         
/* 336 */         double d = d0 * 2.0D - 1.0D;
/* 337 */         this.field_70181_x += 0.03999999910593033D * d;
/*     */       }
/*     */       else {
/*     */         
/* 341 */         if (this.field_70181_x < 0.0D)
/*     */         {
/* 343 */           this.field_70181_x /= 2.0D;
/*     */         }
/*     */         
/* 346 */         this.field_70181_x += 0.007000000216066837D;
/*     */       } 
/*     */       
/* 349 */       if (this.field_70153_n != null && this.field_70153_n instanceof EntityLivingBase) {
/*     */         
/* 351 */         EntityLivingBase entitylivingbase = (EntityLivingBase)this.field_70153_n;
/* 352 */         float f = this.field_70153_n.field_70177_z + -entitylivingbase.field_70702_br * 90.0F;
/* 353 */         double maxSpeed = getSpeed(entitylivingbase);
/* 354 */         double lastX = this.field_70159_w;
/* 355 */         double lastZ = this.field_70179_y;
/* 356 */         this.field_70159_w += -Math.sin((f * 3.1415927F / 180.0F)) * this.speedMultiplier * maxSpeed * 0.05000000074505806D;
/* 357 */         this.field_70179_y += Math.cos((f * 3.1415927F / 180.0F)) * this.speedMultiplier * maxSpeed * 0.05000000074505806D;
/* 358 */         if (lastX - this.field_70159_w != 0.0D || lastZ - this.field_70179_y != 0.0D)
/*     */         {
/* 360 */           this.field_70177_z = (this.field_70153_n.field_70177_z - 90.0F) % 360.0F;
/*     */         }
/*     */       }
/* 363 */       else if (hasAfkSpeed()) {
/*     */         
/* 365 */         float f = this.field_70177_z + 90.0F;
/* 366 */         double maxSpeed = getAfkSpeed();
/* 367 */         this.field_70159_w += -Math.sin((f * 3.1415927F / 180.0F)) * this.speedMultiplier * maxSpeed * 0.05000000074505806D;
/* 368 */         this.field_70179_y += Math.cos((f * 3.1415927F / 180.0F)) * this.speedMultiplier * maxSpeed * 0.05000000074505806D;
/*     */       } 
/*     */       
/* 371 */       double d2 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/*     */       
/* 373 */       if (d2 > getTopSpeed()) {
/*     */         
/* 375 */         double d4 = getTopSpeed() / d2;
/* 376 */         this.field_70159_w *= d4;
/* 377 */         this.field_70179_y *= d4;
/* 378 */         d2 = getTopSpeed();
/*     */       } 
/*     */       
/* 381 */       if (d2 > d10 && this.speedMultiplier < 0.35D) {
/*     */         
/* 383 */         this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;
/*     */         
/* 385 */         if (this.speedMultiplier > 0.35D)
/*     */         {
/* 387 */           this.speedMultiplier = 0.35D;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 392 */         this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;
/*     */         
/* 394 */         if (this.speedMultiplier < 0.07D)
/*     */         {
/* 396 */           this.speedMultiplier = 0.07D;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 402 */       for (int l = 0; l < 4; l++) {
/*     */         
/* 404 */         int i1 = MathHelper.func_76128_c(this.field_70165_t + ((l % 2) - 0.5D) * 0.8D);
/* 405 */         int j = MathHelper.func_76128_c(this.field_70161_v + ((l / 2) - 0.5D) * 0.8D);
/*     */         
/* 407 */         for (int j1 = 0; j1 < 2; j1++) {
/*     */           
/* 409 */           int k = MathHelper.func_76128_c(this.field_70163_u) + j1;
/* 410 */           Block block = this.field_70170_p.func_147439_a(i1, k, j);
/*     */           
/* 412 */           if (block == Blocks.field_150431_aC) {
/*     */             
/* 414 */             this.field_70170_p.func_147468_f(i1, k, j);
/* 415 */             this.field_70123_F = false;
/*     */           }
/* 417 */           else if (block == Blocks.field_150392_bi) {
/*     */             
/* 419 */             this.field_70170_p.func_147480_a(i1, k, j, true);
/* 420 */             this.field_70123_F = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 425 */       if (this.field_70122_E) {
/*     */         
/* 427 */         this.field_70159_w *= 0.5D;
/* 428 */         this.field_70181_x *= 0.5D;
/* 429 */         this.field_70179_y *= 0.5D;
/*     */       } 
/*     */       
/* 432 */       func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */       
/* 434 */       if (this.field_70123_F && d10 > getMaxRammingSpeed()) {
/*     */         
/* 436 */         if (!this.field_70170_p.field_72995_K && !this.field_70128_L)
/*     */         {
/* 438 */           onRammingBreaking(d10);
/* 439 */           func_70106_y();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 444 */         this.field_70159_w *= 0.9900000095367432D;
/* 445 */         this.field_70181_x *= 0.949999988079071D;
/* 446 */         this.field_70179_y *= 0.9900000095367432D;
/*     */       } 
/*     */       
/* 449 */       this.field_70125_A = 0.0F;
/* 450 */       double d11 = this.field_70159_w;
/* 451 */       double d12 = this.field_70179_y;
/*     */       
/* 453 */       if (d11 * d11 + d12 * d12 > 0.001D)
/*     */       {
/* 455 */         double d = (float)(Math.atan2(d12, d11) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 458 */       func_70101_b(this.field_70177_z, this.field_70125_A);
/*     */       
/* 460 */       if (!this.field_70170_p.field_72995_K) {
/*     */         
/* 462 */         this.field_70180_af.func_75692_b(16, Float.valueOf(this.field_70177_z));
/* 463 */         List<Entity> list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */         
/* 465 */         if (list != null && !list.isEmpty())
/*     */         {
/* 467 */           for (int k1 = 0; k1 < list.size(); k1++) {
/*     */             
/* 469 */             Entity entity = list.get(k1);
/*     */             
/* 471 */             if (entity != this.field_70153_n && entity.func_70104_M() && (entity instanceof net.minecraft.entity.item.EntityBoat || entity instanceof EntityClassicBoat))
/*     */             {
/* 473 */               entity.func_70108_f(this);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 478 */         if (this.field_70153_n != null && this.field_70153_n.field_70128_L)
/*     */         {
/* 480 */           this.field_70153_n = null;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 485 */         func_70101_b(this.field_70180_af.func_111145_d(16), this.field_70125_A);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70043_V() {
/* 492 */     if (this.field_70153_n != null) {
/*     */       
/* 494 */       this.field_70177_z = this.field_70180_af.func_111145_d(16);
/* 495 */       double d0 = Math.cos(this.field_70177_z * Math.PI / 180.0D) * 0.4D;
/* 496 */       double d1 = Math.sin(this.field_70177_z * Math.PI / 180.0D) * 0.4D;
/* 497 */       this.field_70153_n.func_70107_b(this.field_70165_t + d0, this.field_70163_u + func_70042_X() + this.field_70153_n.func_70033_W(), this.field_70161_v + d1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound p_70014_1_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound p_70037_1_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float func_70053_R() {
/* 518 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_130002_c(EntityPlayer p_130002_1_) {
/* 526 */     if (this.field_70153_n != null && this.field_70153_n instanceof EntityPlayer && this.field_70153_n != p_130002_1_)
/*     */     {
/* 528 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 532 */     if (!p_130002_1_.func_70093_af() && !this.field_70170_p.field_72995_K)
/*     */     {
/* 534 */       p_130002_1_.func_70078_a(this);
/*     */     }
/*     */     
/* 537 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70064_a(double p_70064_1_, boolean p_70064_3_) {
/* 548 */     int i = MathHelper.func_76128_c(this.field_70165_t);
/* 549 */     int j = MathHelper.func_76128_c(this.field_70163_u);
/* 550 */     int k = MathHelper.func_76128_c(this.field_70161_v);
/* 551 */     if (p_70064_3_) {
/*     */       
/* 553 */       if (this.field_70143_R > 3.0F)
/*     */       {
/* 555 */         func_70069_a(this.field_70143_R);
/*     */         
/* 557 */         if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
/*     */           
/* 559 */           onFallingBreaking(this.field_70143_R);
/* 560 */           func_70106_y();
/*     */         } 
/*     */         
/* 563 */         this.field_70143_R = 0.0F;
/*     */       }
/*     */     
/* 566 */     } else if (this.field_70170_p.func_147439_a(i, j - 1, k).func_149688_o() != Material.field_151586_h && p_70064_1_ < 0.0D) {
/*     */       
/* 568 */       this.field_70143_R = (float)(this.field_70143_R - p_70064_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamageTaken(float p_70266_1_) {
/* 577 */     this.field_70180_af.func_75692_b(19, Float.valueOf(p_70266_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamageTaken() {
/* 585 */     return this.field_70180_af.func_111145_d(19);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeSinceHit(int p_70265_1_) {
/* 593 */     this.field_70180_af.func_75692_b(17, Integer.valueOf(p_70265_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimeSinceHit() {
/* 601 */     return this.field_70180_af.func_75679_c(17);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForwardDirection(int p_70269_1_) {
/* 609 */     this.field_70180_af.func_75692_b(18, Integer.valueOf(p_70269_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getForwardDirection() {
/* 617 */     return this.field_70180_af.func_75679_c(18);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void setIsBoatEmpty(boolean p_70270_1_) {
/* 626 */     this.isBoatEmpty = p_70270_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpeed(EntityLivingBase base) {
/* 631 */     return base.field_70701_bs;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAfkSpeed() {
/* 636 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAfkSpeed() {
/* 641 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlayerBreaking() {
/* 646 */     func_70099_a(getItem(), 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFallingBreaking(float fallingDistance) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRammingBreaking(double speed) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxRammingSpeed() {
/* 661 */     return 0.2D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTopSpeed() {
/* 666 */     return 0.35D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isOnWater(AxisAlignedBB aabb) {
/* 671 */     return this.field_70170_p.func_72830_b(aabb, Material.field_151586_h);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickedResult(MovingObjectPosition target) {
/* 677 */     return getItem();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation createBase(String par1) {
/* 682 */     return new ResourceLocation("ic2", "textures/models/boat/" + par1 + ".png");
/*     */   }
/*     */   
/*     */   public abstract ItemStack getItem();
/*     */   
/*     */   public abstract ResourceLocation getTexture();
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\boats\EntityClassicBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */