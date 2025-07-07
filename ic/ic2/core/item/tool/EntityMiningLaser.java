/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import cpw.mods.fml.common.registry.IThrowableEntity;
/*     */ import ic2.api.event.LaserEvent;
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityDragonPart;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.EntityDamageSourceIndirect;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.FakePlayer;
/*     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*     */ import net.minecraftforge.event.ForgeEventFactory;
/*     */ import net.minecraftforge.event.world.BlockEvent;
/*     */ 
/*     */ public class EntityMiningLaser
/*     */   extends Entity
/*     */   implements IThrowableEntity
/*     */ {
/*  44 */   public float range = 0.0F;
/*  45 */   public float power = 0.0F;
/*  46 */   public int blockBreaks = 0;
/*     */   
/*     */   public boolean explosive = false;
/*     */   
/*  50 */   public static Set<Block> unmineableBlocks = new HashSet<>(Arrays.asList(new Block[] { Blocks.field_150336_V, Blocks.field_150343_Z, Blocks.field_150353_l, (Block)Blocks.field_150356_k, Blocks.field_150355_j, (Block)Blocks.field_150358_i, Blocks.field_150357_h }));
/*     */   
/*     */   public static final double laserSpeed = 1.0D;
/*     */   public EntityLivingBase owner;
/*     */   public boolean headingSet = false;
/*     */   public boolean smelt = false;
/*     */   private int ticksInAir;
/*     */   
/*     */   public EntityMiningLaser(World world) {
/*  59 */     super(world);
/*     */     
/*  61 */     this.ticksInAir = 0;
/*  62 */     func_70105_a(0.8F, 0.8F);
/*  63 */     this.field_70129_M = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMiningLaser(World world, EntityLivingBase entityliving, float range, float power, int blockBreaks, boolean explosive) {
/*  68 */     this(world, entityliving, range, power, blockBreaks, explosive, entityliving.field_70177_z, entityliving.field_70125_A);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMiningLaser(World world, EntityLivingBase entityliving, float range, float power, int blockBreaks, boolean explosive, boolean smelt) {
/*  73 */     this(world, entityliving, range, power, blockBreaks, explosive, entityliving.field_70177_z, entityliving.field_70125_A);
/*  74 */     this.smelt = smelt;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMiningLaser(World world, EntityLivingBase entityliving, float range, float power, int blockBreaks, boolean explosive, double yawDeg, double pitchDeg) {
/*  79 */     this(world, entityliving, range, power, blockBreaks, explosive, yawDeg, pitchDeg, entityliving.field_70163_u + entityliving.func_70047_e() - 0.1D);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMiningLaser(World world, EntityLivingBase entityliving, float range, float power, int blockBreaks, boolean explosive, double yawDeg, double pitchDeg, double y) {
/*  84 */     super(world);
/*     */     
/*  86 */     this.ticksInAir = 0;
/*  87 */     this.owner = entityliving;
/*  88 */     func_70105_a(0.8F, 0.8F);
/*  89 */     this.field_70129_M = 0.0F;
/*     */     
/*  91 */     double yaw = Math.toRadians(yawDeg);
/*  92 */     double pitch = Math.toRadians(pitchDeg);
/*     */     
/*  94 */     double x = entityliving.field_70165_t - Math.cos(yaw) * 0.16D;
/*  95 */     double z = entityliving.field_70161_v - Math.sin(yaw) * 0.16D;
/*     */     
/*  97 */     double startMotionX = -Math.sin(yaw) * Math.cos(pitch);
/*  98 */     double startMotionY = -Math.sin(pitch);
/*  99 */     double startMotionZ = Math.cos(yaw) * Math.cos(pitch);
/*     */     
/* 101 */     func_70107_b(x, y, z);
/* 102 */     setLaserHeading(startMotionX, startMotionY, startMotionZ, 1.0D);
/*     */     
/* 104 */     this.range = range;
/* 105 */     this.power = power;
/* 106 */     this.blockBreaks = blockBreaks;
/* 107 */     this.explosive = explosive;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */   
/*     */   public void setLaserHeading(double motionX, double motionY, double motionZ, double speed) {
/* 116 */     double currentSpeed = MathHelper.func_76133_a(motionX * motionX + motionY * motionY + motionZ * motionZ);
/*     */     
/* 118 */     this.field_70159_w = motionX / currentSpeed * speed;
/* 119 */     this.field_70181_x = motionY / currentSpeed * speed;
/* 120 */     this.field_70179_y = motionZ / currentSpeed * speed;
/*     */     
/* 122 */     this.field_70126_B = this.field_70177_z = (float)Math.toDegrees(Math.atan2(motionX, motionZ));
/* 123 */     this.field_70127_C = this.field_70125_A = (float)Math.toDegrees(Math.atan2(motionY, MathHelper.func_76133_a(motionX * motionX + motionZ * motionZ)));
/*     */     
/* 125 */     this.headingSet = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70016_h(double motionX, double motionY, double motionZ) {
/* 130 */     setLaserHeading(motionX, motionY, motionZ, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 135 */     super.func_70071_h_();
/*     */     
/* 137 */     if (IC2.platform.isSimulating() && (this.range < 1.0F || this.power <= 0.0F || this.blockBreaks <= 0)) {
/*     */       
/* 139 */       if (this.explosive)
/*     */       {
/* 141 */         explode();
/*     */       }
/* 143 */       func_70106_y();
/*     */       
/*     */       return;
/*     */     } 
/* 147 */     this.ticksInAir++;
/* 148 */     Vec3 oldPosition = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/* 149 */     Vec3 newPosition = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */     
/* 151 */     MovingObjectPosition movingobjectposition = this.field_70170_p.func_147447_a(oldPosition, newPosition, false, true, false);
/*     */     
/* 153 */     oldPosition = Vec3.func_72443_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */     
/* 155 */     if (movingobjectposition != null) {
/*     */       
/* 157 */       newPosition = Vec3.func_72443_a(movingobjectposition.field_72307_f.field_72450_a, movingobjectposition.field_72307_f.field_72448_b, movingobjectposition.field_72307_f.field_72449_c);
/*     */     }
/*     */     else {
/*     */       
/* 161 */       newPosition = Vec3.func_72443_a(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */     } 
/*     */     
/* 164 */     Entity entity = null;
/* 165 */     List<Entity> list = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72321_a(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
/* 166 */     double d = 0.0D;
/* 167 */     for (int l = 0; l < list.size(); l++) {
/*     */       
/* 169 */       Entity entity1 = list.get(l);
/* 170 */       if (entity1.func_70067_L() && (entity1 != this.owner || this.ticksInAir >= 5)) {
/*     */         
/* 172 */         float f4 = 0.3F;
/* 173 */         AxisAlignedBB axisalignedbb1 = entity1.field_70121_D.func_72314_b(f4, f4, f4);
/* 174 */         MovingObjectPosition movingobjectposition1 = axisalignedbb1.func_72327_a(oldPosition, newPosition);
/* 175 */         if (movingobjectposition1 != null) {
/*     */           
/* 177 */           double d1 = oldPosition.func_72438_d(movingobjectposition1.field_72307_f);
/* 178 */           if (d1 < d || d == 0.0D) {
/*     */             
/* 180 */             entity = entity1;
/* 181 */             d = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 186 */     if (entity != null)
/*     */     {
/* 188 */       movingobjectposition = new MovingObjectPosition(entity);
/*     */     }
/* 190 */     if (movingobjectposition != null && IC2.platform.isSimulating()) {
/*     */       
/* 192 */       if (this.explosive) {
/*     */         
/* 194 */         explode();
/* 195 */         func_70106_y();
/*     */         
/*     */         return;
/*     */       } 
/* 199 */       if (movingobjectposition.field_72308_g != null) {
/*     */         
/* 201 */         LaserEvent.LaserHitsEntityEvent tEvent = new LaserEvent.LaserHitsEntityEvent(this.field_70170_p, this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, movingobjectposition.field_72308_g);
/* 202 */         MinecraftForge.EVENT_BUS.post((Event)tEvent);
/*     */         
/* 204 */         if (takeDataFromEvent((LaserEvent)tEvent))
/*     */         {
/* 206 */           int damage = (int)this.power;
/*     */           
/* 208 */           if (damage > 0) {
/*     */             
/* 210 */             if (entity != null)
/*     */             {
/* 212 */               entity.func_70015_d(damage * (this.smelt ? 2 : 1));
/*     */             }
/* 214 */             if (tEvent.hitentity.func_70097_a((new EntityDamageSourceIndirect("arrow", this, (Entity)this.owner)).func_76349_b(), damage) && this.owner instanceof EntityPlayer)
/*     */             {
/* 216 */               if ((tEvent.hitentity instanceof EntityDragon && ((EntityDragon)tEvent.hitentity).func_110143_aJ() <= 0.0F) || (tEvent.hitentity instanceof EntityDragonPart && ((EntityDragonPart)tEvent.hitentity).field_70259_a instanceof EntityDragon && ((EntityLivingBase)((EntityDragonPart)tEvent.hitentity).field_70259_a).func_110143_aJ() <= 0.0F))
/*     */               {
/* 218 */                 IC2.achievements.issueAchievement((EntityPlayer)this.owner, "killDragonMiningLaser");
/*     */               }
/*     */             }
/*     */           } 
/*     */           
/* 223 */           func_70106_y();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 228 */         LaserEvent.LaserHitsBlockEvent tEvent = new LaserEvent.LaserHitsBlockEvent(this.field_70170_p, this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, movingobjectposition.field_72311_b, movingobjectposition.field_72312_c, movingobjectposition.field_72309_d, movingobjectposition.field_72310_e, 0.9F, true, true);
/* 229 */         MinecraftForge.EVENT_BUS.post((Event)tEvent);
/*     */         
/* 231 */         if (takeDataFromEvent((LaserEvent)tEvent)) {
/*     */           
/* 233 */           Block tBlock = this.field_70170_p.func_147439_a(tEvent.x, tEvent.y, tEvent.z);
/* 234 */           int meta = this.field_70170_p.func_72805_g(tEvent.x, tEvent.y, tEvent.z);
/* 235 */           if (!this.field_70170_p.func_147437_c(tEvent.x, tEvent.y, tEvent.z) && !isBlockGlass(new ItemStack(tBlock, 1, meta)))
/*     */           {
/* 237 */             if (!canMine(tEvent, tBlock)) {
/*     */               
/* 239 */               func_70106_y();
/*     */             }
/* 241 */             else if (IC2.platform.isSimulating()) {
/*     */               
/* 243 */               float resis = 0.0F;
/* 244 */               resis = tBlock.getExplosionResistance(this, this.field_70170_p, tEvent.x, tEvent.y, tEvent.z, this.field_70165_t, this.field_70163_u, this.field_70161_v) + 0.3F;
/* 245 */               this.power -= resis / 10.0F;
/* 246 */               if (this.power >= 0.0F)
/*     */               {
/* 248 */                 if (tBlock.func_149688_o() == Material.field_151590_u || tBlock.func_149688_o() == Material.field_151590_u) {
/*     */                   
/* 250 */                   tBlock.func_149723_a(this.field_70170_p, tEvent.x, tEvent.y, tEvent.z, new Explosion(this.field_70170_p, this, tEvent.x, tEvent.y, tEvent.z, 1.0F));
/*     */                 }
/* 252 */                 else if (this.smelt) {
/*     */                   
/* 254 */                   if (tBlock.func_149688_o() == Material.field_151575_d) {
/*     */                     
/* 256 */                     tEvent.dropBlock = false;
/*     */                   }
/*     */                   else {
/*     */                     
/* 260 */                     for (ItemStack isa : tBlock.getDrops(this.field_70170_p, tEvent.x, tEvent.y, tEvent.z, this.field_70170_p.func_72805_g(tEvent.x, tEvent.y, tEvent.z), 0)) {
/*     */                       
/* 262 */                       ItemStack is = FurnaceRecipes.func_77602_a().func_151395_a(isa);
/* 263 */                       if (is != null) {
/*     */                         
/* 265 */                         Block newBlock = Block.func_149634_a(is.func_77973_b());
/*     */                         
/* 267 */                         if (newBlock != Blocks.field_150350_a && newBlock != tBlock) {
/*     */                           
/* 269 */                           tEvent.removeBlock = false;
/* 270 */                           tEvent.dropBlock = false;
/* 271 */                           this.field_70170_p.func_147465_d(tEvent.x, tEvent.y, tEvent.z, newBlock, is.func_77960_j(), 7);
/*     */                         }
/*     */                         else {
/*     */                           
/* 275 */                           tEvent.dropBlock = false;
/* 276 */                           float var6 = 0.7F;
/* 277 */                           double var7 = (this.field_70170_p.field_73012_v.nextFloat() * var6) + (1.0F - var6) * 0.5D;
/* 278 */                           double var9 = (this.field_70170_p.field_73012_v.nextFloat() * var6) + (1.0F - var6) * 0.5D;
/* 279 */                           double var11 = (this.field_70170_p.field_73012_v.nextFloat() * var6) + (1.0F - var6) * 0.5D;
/* 280 */                           EntityItem var13 = new EntityItem(this.field_70170_p, tEvent.x + var7, tEvent.y + var9, tEvent.z + var11, is.func_77946_l());
/* 281 */                           var13.field_145804_b = 10;
/* 282 */                           this.field_70170_p.func_72838_d((Entity)var13);
/*     */                         } 
/* 284 */                         this.power = 0.0F;
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/* 289 */                 if (tEvent.removeBlock) {
/*     */                   
/* 291 */                   if (tEvent.dropBlock)
/*     */                   {
/* 293 */                     tBlock.func_149690_a(this.field_70170_p, tEvent.x, tEvent.y, tEvent.z, this.field_70170_p.func_72805_g(tEvent.x, tEvent.y, tEvent.z), tEvent.dropChance, 0);
/*     */                   }
/* 295 */                   this.field_70170_p.func_147468_f(tEvent.x, tEvent.y, tEvent.z);
/*     */                   
/* 297 */                   if (this.field_70170_p.field_73012_v.nextInt(10) == 0 && tBlock.func_149688_o().func_76217_h())
/*     */                   {
/* 299 */                     this.field_70170_p.func_147465_d(tEvent.x, tEvent.y, tEvent.z, (Block)Blocks.field_150480_ab, 0, 7);
/*     */                   }
/*     */                 } 
/*     */                 
/* 303 */                 this.blockBreaks--;
/*     */               }
/*     */             
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 312 */       this.power -= 0.5F;
/*     */     } 
/*     */     
/* 315 */     func_70107_b(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
/*     */     
/* 317 */     this.range -= 0.02F;
/*     */     
/* 319 */     if (func_70090_H())
/*     */     {
/* 321 */       func_70106_y();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockGlass(ItemStack item) {
/* 327 */     if (item.func_77969_a(Ic2Items.reinforcedGlass))
/*     */     {
/* 329 */       return true;
/*     */     }
/* 331 */     if (StackUtil.hasOreTag("blockGlass", item) || StackUtil.hasOreTag("paneGlass", item))
/*     */     {
/* 333 */       return true;
/*     */     }
/* 335 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70014_b(NBTTagCompound nbttagcompound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70037_a(NBTTagCompound nbttagcompound) {}
/*     */ 
/*     */   
/*     */   public float func_70053_R() {
/* 348 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean takeDataFromEvent(LaserEvent aEvent) {
/* 353 */     this.owner = aEvent.owner;
/* 354 */     this.range = aEvent.range;
/* 355 */     this.power = aEvent.power;
/* 356 */     this.blockBreaks = aEvent.blockBreaks;
/* 357 */     this.explosive = aEvent.explosive;
/* 358 */     this.smelt = aEvent.smelt;
/* 359 */     if (aEvent.isCanceled()) {
/*     */       
/* 361 */       func_70106_y();
/* 362 */       return false;
/*     */     } 
/* 364 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void explode() {
/* 369 */     if (IC2.platform.isSimulating()) {
/*     */       
/* 371 */       LaserEvent.LaserExplodesEvent tEvent = new LaserEvent.LaserExplodesEvent(this.field_70170_p, this, this.owner, this.range, this.power, this.blockBreaks, this.explosive, this.smelt, 5.0F, 0.85F, 0.55F);
/* 372 */       MinecraftForge.EVENT_BUS.post((Event)tEvent);
/* 373 */       if (takeDataFromEvent((LaserEvent)tEvent)) {
/*     */         
/* 375 */         ExplosionIC2 explosion = new ExplosionIC2(this.field_70170_p, null, this.field_70165_t, this.field_70163_u, this.field_70161_v, tEvent.explosionpower, tEvent.explosiondroprate, this.power);
/* 376 */         if (ForgeEventFactory.onExplosionStart(this.field_70170_p, explosion.fakeExplosion)) {
/*     */           return;
/*     */         }
/*     */         
/* 380 */         explosion.doExplosion();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMine(LaserEvent.LaserHitsBlockEvent evt, Block block) {
/* 387 */     BlockEvent.BreakEvent event = getEventFromEvent(evt);
/* 388 */     MinecraftForge.EVENT_BUS.post((Event)event);
/* 389 */     if (event.isCanceled())
/*     */     {
/* 391 */       return false;
/*     */     }
/* 393 */     return !unmineableBlocks.contains(block);
/*     */   }
/*     */   
/*     */   public BlockEvent.BreakEvent getEventFromEvent(LaserEvent.LaserHitsBlockEvent evt) {
/*     */     FakePlayer fakePlayer;
/* 398 */     EntityPlayer player = null;
/* 399 */     if (evt.owner instanceof EntityPlayer) {
/*     */       
/* 401 */       player = (EntityPlayer)evt.owner;
/*     */     }
/*     */     else {
/*     */       
/* 405 */       fakePlayer = FakePlayerFactory.getMinecraft((WorldServer)evt.world);
/*     */     } 
/* 407 */     return new BlockEvent.BreakEvent(evt.x, evt.y, evt.z, evt.world, evt.world.func_147439_a(evt.x, evt.y, evt.z), evt.world.func_72805_g(evt.x, evt.y, evt.z), (EntityPlayer)fakePlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getThrower() {
/* 412 */     return (Entity)this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThrower(Entity entity) {
/* 417 */     if (entity instanceof EntityLivingBase)
/*     */     {
/* 419 */       this.owner = (EntityLivingBase)entity;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\EntityMiningLaser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */