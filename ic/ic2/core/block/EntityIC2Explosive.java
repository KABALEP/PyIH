/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IC2;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityIC2Explosive
/*     */   extends Entity {
/*     */   public DamageSource damageSource;
/*     */   public String igniter;
/*  18 */   public int fuse = 80;
/*  19 */   public float explosivePower = 4.0F;
/*  20 */   public float dropRate = 0.3F;
/*  21 */   public float damageVsEntitys = 1.0F;
/*  22 */   public Block renderBlock = Blocks.field_150346_d;
/*     */ 
/*     */   
/*     */   public EntityIC2Explosive(World world) {
/*  26 */     super(world);
/*  27 */     this.field_70156_m = true;
/*  28 */     func_70105_a(0.98F, 0.98F);
/*  29 */     this.field_70129_M = this.field_70131_O / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityIC2Explosive(World world, double d, double d1, double d2, int fuselength, float power, float rate, float damage, Block block, DamageSource damagesource) {
/*  34 */     this(world);
/*  35 */     func_70107_b(d, d1, d2);
/*  36 */     float f = (float)(Math.random() * 3.141592741012573D * 2.0D);
/*  37 */     this.field_70159_w = (-MathHelper.func_76126_a(f * 3.141593F / 180.0F) * 0.02F);
/*  38 */     this.field_70181_x = 0.2000000029802322D;
/*  39 */     this.field_70179_y = (-MathHelper.func_76134_b(f * 3.141593F / 180.0F) * 0.02F);
/*  40 */     this.field_70169_q = d;
/*  41 */     this.field_70167_r = d1;
/*  42 */     this.field_70166_s = d2;
/*  43 */     this.fuse = fuselength;
/*  44 */     this.explosivePower = power;
/*  45 */     this.dropRate = rate;
/*  46 */     this.damageVsEntitys = damage;
/*  47 */     this.renderBlock = block;
/*  48 */     this.damageSource = damagesource;
/*  49 */     if (damagesource == null)
/*     */     {
/*  51 */       this.damageSource = DamageSource.func_94539_a(new Explosion(world, this, d2, d1, d2, power));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityIC2Explosive(World world, double d, double d1, double d2, int fuselength, float power, float rate, float damage, Block block) {
/*  57 */     this(world, d, d1, d2, fuselength, power, rate, damage, block, (DamageSource)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_70067_L() {
/*  71 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  76 */     this.field_70169_q = this.field_70165_t;
/*  77 */     this.field_70167_r = this.field_70163_u;
/*  78 */     this.field_70166_s = this.field_70161_v;
/*  79 */     this.field_70181_x -= 0.03999999910593033D;
/*  80 */     func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*  81 */     this.field_70159_w *= 0.9800000190734863D;
/*  82 */     this.field_70181_x *= 0.9800000190734863D;
/*  83 */     this.field_70179_y *= 0.9800000190734863D;
/*  84 */     if (this.field_70122_E) {
/*     */       
/*  86 */       this.field_70159_w *= 0.699999988079071D;
/*  87 */       this.field_70179_y *= 0.699999988079071D;
/*  88 */       this.field_70181_x *= -0.5D;
/*     */     } 
/*  90 */     if (this.fuse-- <= 0) {
/*     */       
/*  92 */       if (IC2.platform.isSimulating())
/*     */       {
/*  94 */         func_70106_y();
/*  95 */         explode();
/*     */       }
/*     */       else
/*     */       {
/*  99 */         func_70106_y();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 104 */       this.field_70170_p.func_72869_a("smoke", this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void explode() {
/* 110 */     ExplosionIC2 explosion = new ExplosionIC2(this.field_70170_p, null, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosivePower, this.dropRate, this.damageVsEntitys, this.damageSource, this.igniter);
/* 111 */     explosion.doExplosion();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbttagcompound) {
/* 116 */     nbttagcompound.func_74774_a("Fuse", (byte)this.fuse);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbttagcompound) {
/* 121 */     this.fuse = nbttagcompound.func_74771_c("Fuse");
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_70053_R() {
/* 126 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityIC2Explosive setIgniter(String igniter) {
/* 131 */     this.igniter = igniter;
/* 132 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\EntityIC2Explosive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */