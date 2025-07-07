/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.PointExplosion;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityDynamite
/*     */   extends Entity implements IProjectile {
/*     */   public boolean sticky = false;
/*  17 */   public static int netId = 142;
/*     */   public int stickX;
/*     */   public int stickY;
/*     */   public int stickZ;
/*  21 */   public int fuse = 100;
/*     */   private boolean inGround = false;
/*     */   public EntityLivingBase owner;
/*     */   private int ticksInGround;
/*  25 */   private int ticksInAir = 0;
/*     */ 
/*     */   
/*     */   public EntityDynamite(World world, double x, double y, double z) {
/*  29 */     super(world);
/*  30 */     func_70105_a(0.5F, 0.5F);
/*  31 */     func_70107_b(x, y, z);
/*  32 */     this.field_70129_M = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDynamite(World world, double x, double y, double z, boolean sticky) {
/*  37 */     this(world, x, y, z);
/*  38 */     this.sticky = sticky;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDynamite(World world) {
/*  43 */     this(world, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDynamite(World world, EntityLivingBase entityliving) {
/*  48 */     super(world);
/*  49 */     this.owner = entityliving;
/*  50 */     func_70105_a(0.5F, 0.5F);
/*  51 */     func_70012_b(((Entity)entityliving).field_70165_t, ((Entity)entityliving).field_70163_u + entityliving.func_70047_e(), ((Entity)entityliving).field_70161_v, ((Entity)entityliving).field_70177_z, ((Entity)entityliving).field_70125_A);
/*  52 */     this.field_70165_t -= (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.141593F) * 0.16F);
/*  53 */     this.field_70163_u -= 0.1000000014901161D;
/*  54 */     this.field_70161_v -= (MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.141593F) * 0.16F);
/*  55 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*  56 */     this.field_70129_M = 0.0F;
/*  57 */     this.field_70159_w = (-MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.141593F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.141593F));
/*  58 */     this.field_70179_y = (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.141593F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.141593F));
/*  59 */     this.field_70181_x = -MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.141593F);
/*  60 */     func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */   
/*     */   public void func_70186_c(double d, double d1, double d2, float f, float f1) {
/*  69 */     float f2 = MathHelper.func_76133_a(d * d + d1 * d1 + d2 * d2);
/*  70 */     d /= f2;
/*  71 */     d1 /= f2;
/*  72 */     d2 /= f2;
/*  73 */     d += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * f1;
/*  74 */     d1 += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * f1;
/*  75 */     d2 += this.field_70146_Z.nextGaussian() * 0.007499999832361937D * f1;
/*  76 */     d *= f;
/*  77 */     d1 *= f;
/*  78 */     d2 *= f;
/*  79 */     this.field_70159_w = d;
/*  80 */     this.field_70181_x = d1;
/*  81 */     this.field_70179_y = d2;
/*  82 */     float f3 = MathHelper.func_76133_a(d * d + d2 * d2);
/*  83 */     float n = (float)(Math.atan2(d, d2) * 180.0D / 3.141592741012573D);
/*  84 */     this.field_70177_z = n;
/*  85 */     this.field_70126_B = n;
/*  86 */     float n2 = (float)(Math.atan2(d1, f3) * 180.0D / 3.141592741012573D);
/*  87 */     this.field_70125_A = n2;
/*  88 */     this.field_70127_C = n2;
/*  89 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70016_h(double d, double d1, double d2) {
/*  94 */     this.field_70159_w = d;
/*  95 */     this.field_70181_x = d1;
/*  96 */     this.field_70179_y = d2;
/*  97 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/*     */       
/*  99 */       float f = MathHelper.func_76133_a(d * d + d2 * d2);
/* 100 */       float n = (float)(Math.atan2(d, d2) * 180.0D / 3.141592741012573D);
/* 101 */       this.field_70177_z = n;
/* 102 */       this.field_70126_B = n;
/* 103 */       float n2 = (float)(Math.atan2(d1, f) * 180.0D / 3.141592741012573D);
/* 104 */       this.field_70125_A = n2;
/* 105 */       this.field_70127_C = n2;
/* 106 */       this.field_70127_C = this.field_70125_A;
/* 107 */       this.field_70126_B = this.field_70177_z;
/* 108 */       func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
/* 109 */       this.ticksInGround = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 115 */     super.func_70071_h_();
/* 116 */     if (this.field_70127_C == 0.0F && this.field_70126_B == 0.0F) {
/*     */       
/* 118 */       float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 119 */       float n = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / 3.141592741012573D);
/* 120 */       this.field_70177_z = n;
/* 121 */       this.field_70126_B = n;
/* 122 */       float n2 = (float)(Math.atan2(this.field_70181_x, f) * 180.0D / 3.141592741012573D);
/* 123 */       this.field_70125_A = n2;
/* 124 */       this.field_70127_C = n2;
/*     */     } 
/* 126 */     if (this.fuse-- <= 0) {
/*     */       
/* 128 */       if (IC2.platform.isSimulating())
/*     */       {
/* 130 */         func_70106_y();
/* 131 */         explode();
/*     */       }
/*     */       else
/*     */       {
/* 135 */         func_70106_y();
/*     */       }
/*     */     
/* 138 */     } else if (this.fuse < 100 && this.fuse % 2 == 0) {
/*     */       
/* 140 */       this.field_70170_p.func_72869_a("smoke", this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, 0.0D, 0.0D, 0.0D);
/*     */     } 
/* 142 */     if (this.inGround) {
/*     */       
/* 144 */       this.ticksInGround++;
/* 145 */       if (this.ticksInGround >= 200)
/*     */       {
/* 147 */         func_70106_y();
/*     */       }
/* 149 */       if (this.sticky) {
/*     */         
/* 151 */         this.fuse -= 3;
/* 152 */         this.field_70159_w = 0.0D;
/* 153 */         this.field_70181_x = 0.0D;
/* 154 */         this.field_70179_y = 0.0D;
/* 155 */         if (this.field_70170_p.func_147439_a(this.stickX, this.stickY, this.stickZ) != null) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     this.ticksInAir++;
/* 162 */     Vec3 vec3d = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 163 */     Vec3 vec3d2 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 164 */     MovingObjectPosition movingobjectposition = this.field_70170_p.func_147447_a(vec3d, vec3d2, false, true, false);
/* 165 */     vec3d = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 166 */     vec3d2 = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/* 167 */     if (movingobjectposition != null) {
/*     */       
/* 169 */       vec3d2 = Vec3.func_72443_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
/* 170 */       float remainX = (float)(movingobjectposition.field_72307_f.field_72450_a - this.field_70165_t);
/* 171 */       float remainY = (float)(movingobjectposition.field_72307_f.field_72448_b - this.field_70163_u);
/* 172 */       float remainZ = (float)(movingobjectposition.field_72307_f.field_72449_c - this.field_70161_v);
/* 173 */       float f2 = MathHelper.func_76133_a((remainX * remainX + remainY * remainY + remainZ * remainZ));
/* 174 */       this.stickX = movingobjectposition.field_72311_b;
/* 175 */       this.stickY = movingobjectposition.field_72312_c;
/* 176 */       this.stickZ = movingobjectposition.field_72309_d;
/* 177 */       this.field_70165_t -= (remainX / f2) * 0.0500000007450581D;
/* 178 */       this.field_70163_u -= (remainY / f2) * 0.0500000007450581D;
/* 179 */       this.field_70161_v -= (remainZ / f2) * 0.0500000007450581D;
/* 180 */       this.field_70165_t += remainX;
/* 181 */       this.field_70163_u += remainY;
/* 182 */       this.field_70161_v += remainZ;
/* 183 */       this.field_70159_w *= (0.75F - this.field_70146_Z.nextFloat());
/* 184 */       this.field_70181_x *= -0.300000011920929D;
/* 185 */       this.field_70179_y *= (0.75F - this.field_70146_Z.nextFloat());
/* 186 */       this.inGround = true;
/*     */     }
/*     */     else {
/*     */       
/* 190 */       this.field_70165_t += this.field_70159_w;
/* 191 */       this.field_70163_u += this.field_70181_x;
/* 192 */       this.field_70161_v += this.field_70179_y;
/* 193 */       this.inGround = false;
/*     */     } 
/* 195 */     float f3 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
/* 196 */     this.field_70177_z = (float)(Math.atan2(this.field_70159_w, this.field_70179_y) * 180.0D / 3.141592741012573D);
/* 197 */     this.field_70125_A = (float)(Math.atan2(this.field_70181_x, f3) * 180.0D / 3.141592741012573D);
/* 198 */     while (this.field_70125_A - this.field_70127_C < -180.0F)
/*     */     {
/* 200 */       this.field_70127_C -= 360.0F;
/*     */     }
/* 202 */     while (this.field_70125_A - this.field_70127_C >= 180.0F)
/*     */     {
/* 204 */       this.field_70127_C += 360.0F;
/*     */     }
/* 206 */     while (this.field_70177_z - this.field_70126_B < -180.0F)
/*     */     {
/* 208 */       this.field_70126_B -= 360.0F;
/*     */     }
/* 210 */     while (this.field_70177_z - this.field_70126_B >= 180.0F)
/*     */     {
/* 212 */       this.field_70126_B += 360.0F;
/*     */     }
/* 214 */     this.field_70125_A = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * 0.2F;
/* 215 */     this.field_70177_z = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * 0.2F;
/* 216 */     float f4 = 0.98F;
/* 217 */     float f5 = 0.04F;
/* 218 */     if (func_70090_H()) {
/*     */       
/* 220 */       this.fuse += 2000;
/* 221 */       for (int i1 = 0; i1 < 4; i1++) {
/*     */         
/* 223 */         float f6 = 0.25F;
/* 224 */         this.field_70170_p.func_72869_a("bubble", this.field_70165_t - this.field_70159_w * f6, this.field_70163_u - this.field_70181_x * f6, this.field_70161_v - this.field_70179_y * f6, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */       } 
/* 226 */       f4 = 0.75F;
/*     */     } 
/* 228 */     this.field_70159_w *= f4;
/* 229 */     this.field_70181_x *= f4;
/* 230 */     this.field_70179_y *= f4;
/* 231 */     this.field_70181_x -= f5;
/* 232 */     func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound nbttagcompound) {
/* 237 */     nbttagcompound.func_74774_a("inGround", (byte)(this.inGround ? 1 : 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70037_a(NBTTagCompound nbttagcompound) {
/* 242 */     this.inGround = (nbttagcompound.func_74771_c("inGround") == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_70053_R() {
/* 247 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void explode() {
/* 252 */     PointExplosion explosion = new PointExplosion(this.field_70170_p, null, (int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v, 1.0F, 1.0F, 0.8F);
/* 253 */     explosion.doExplosionA(1, 1, 1, 1, 1, 1);
/* 254 */     explosion.doExplosionB(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\EntityDynamite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */