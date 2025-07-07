/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.item.ItemBooze;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class TileEntityBarrel
/*     */   extends TileEntity {
/*  13 */   public int type = 0;
/*  14 */   public int boozeAmount = 0;
/*  15 */   public int age = 0;
/*     */   public boolean detailed = true;
/*  17 */   public int treetapSide = 0;
/*  18 */   public int hopsCount = 0;
/*  19 */   public int wheatCount = 0;
/*  20 */   public int solidRatio = 0;
/*  21 */   public int hopsRatio = 0;
/*  22 */   public int timeRatio = 0;
/*     */ 
/*     */   
/*     */   public void set(int value) {
/*  26 */     this.type = ItemBooze.getTypeOfValue(value);
/*  27 */     if (this.type > 0)
/*     */     {
/*  29 */       this.boozeAmount = ItemBooze.getAmountOfValue(value);
/*     */     }
/*  31 */     if (this.type == 1) {
/*     */       
/*  33 */       this.detailed = false;
/*  34 */       this.hopsRatio = ItemBooze.getHopsRatioOfBeerValue(value);
/*  35 */       this.solidRatio = ItemBooze.getSolidRatioOfBeerValue(value);
/*  36 */       this.timeRatio = ItemBooze.getTimeRatioOfBeerValue(value);
/*     */     } 
/*  38 */     if (this.type == 2) {
/*     */       
/*  40 */       this.detailed = true;
/*  41 */       this.age = timeNedForRum(this.boozeAmount) * ItemBooze.getProgressOfRumValue(value) / 100;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  47 */     super.func_145839_a(nbttagcompound);
/*  48 */     this.type = nbttagcompound.func_74771_c("type");
/*  49 */     this.boozeAmount = nbttagcompound.func_74771_c("waterCount");
/*  50 */     this.age = nbttagcompound.func_74762_e("age");
/*  51 */     this.treetapSide = nbttagcompound.func_74771_c("treetapSide");
/*  52 */     this.detailed = nbttagcompound.func_74767_n("detailed");
/*  53 */     if (this.type == 1) {
/*     */       
/*  55 */       if (this.detailed) {
/*     */         
/*  57 */         this.hopsCount = nbttagcompound.func_74771_c("hopsCount");
/*  58 */         this.wheatCount = nbttagcompound.func_74771_c("wheatCount");
/*     */       } 
/*  60 */       this.solidRatio = nbttagcompound.func_74771_c("solidRatio");
/*  61 */       this.hopsRatio = nbttagcompound.func_74771_c("hopsRatio");
/*  62 */       this.timeRatio = nbttagcompound.func_74771_c("timeRatio");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  68 */     super.func_145841_b(nbttagcompound);
/*  69 */     nbttagcompound.func_74774_a("type", (byte)this.type);
/*  70 */     nbttagcompound.func_74774_a("waterCount", (byte)this.boozeAmount);
/*  71 */     nbttagcompound.func_74768_a("age", this.age);
/*  72 */     nbttagcompound.func_74774_a("treetapSide", (byte)this.treetapSide);
/*  73 */     nbttagcompound.func_74757_a("detailed", this.detailed);
/*  74 */     if (this.type == 1) {
/*     */       
/*  76 */       if (this.detailed) {
/*     */         
/*  78 */         nbttagcompound.func_74774_a("hopsCount", (byte)this.hopsCount);
/*  79 */         nbttagcompound.func_74774_a("wheatCount", (byte)this.wheatCount);
/*     */       } 
/*  81 */       nbttagcompound.func_74774_a("solidRatio", (byte)this.solidRatio);
/*  82 */       nbttagcompound.func_74774_a("hopsRatio", (byte)this.hopsRatio);
/*  83 */       nbttagcompound.func_74774_a("timeRatio", (byte)this.timeRatio);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  89 */     if (!isEmpty() && this.treetapSide < 2) {
/*     */       
/*  91 */       this.age++;
/*  92 */       if (this.type == 1 && this.timeRatio < 5) {
/*     */         
/*  94 */         int x = this.timeRatio;
/*  95 */         if (x == 4)
/*     */         {
/*  97 */           x += 2;
/*     */         }
/*  99 */         if (this.age >= 24000.0D * Math.pow(3.0D, x)) {
/*     */           
/* 101 */           this.age = 0;
/* 102 */           this.timeRatio++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 110 */     return (this.type == 0 || this.boozeAmount <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean rightclick(EntityPlayer player) {
/* 115 */     ItemStack cur = player.func_71045_bC();
/* 116 */     if (cur == null)
/*     */     {
/* 118 */       return false;
/*     */     }
/* 120 */     if (cur.func_77973_b() == Items.field_151131_as) {
/*     */       
/* 122 */       if (!this.detailed || this.boozeAmount >= 32 || this.type > 1)
/*     */       {
/* 124 */         return false;
/*     */       }
/* 126 */       this.type = 1;
/* 127 */       cur.func_150996_a(Items.field_151131_as);
/* 128 */       this.boozeAmount++;
/* 129 */       return true;
/*     */     } 
/* 131 */     if (cur.func_77973_b() == Ic2Items.waterCell.func_77973_b()) {
/*     */       
/* 133 */       if (!this.detailed || this.type > 1)
/*     */       {
/* 135 */         return false;
/*     */       }
/* 137 */       this.type = 1;
/* 138 */       int i = cur.field_77994_a;
/* 139 */       if (player.func_70093_af())
/*     */       {
/* 141 */         i = 1;
/*     */       }
/* 143 */       if (i > 32 - this.boozeAmount)
/*     */       {
/* 145 */         i = 32 - this.boozeAmount;
/*     */       }
/* 147 */       if (i <= 0)
/*     */       {
/* 149 */         return false;
/*     */       }
/* 151 */       this.boozeAmount += i;
/* 152 */       ItemStack itemStack = cur;
/* 153 */       itemStack.field_77994_a -= i;
/* 154 */       if (cur.field_77994_a <= 0)
/*     */       {
/* 156 */         player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*     */       }
/* 158 */       return true;
/*     */     } 
/* 160 */     if (cur.func_77973_b() == Items.field_151015_O) {
/*     */       
/* 162 */       if (!this.detailed || this.type > 1)
/*     */       {
/* 164 */         return false;
/*     */       }
/* 166 */       this.type = 1;
/* 167 */       int i = cur.field_77994_a;
/* 168 */       if (player.func_70093_af())
/*     */       {
/* 170 */         i = 1;
/*     */       }
/* 172 */       if (i > 64 - this.wheatCount)
/*     */       {
/* 174 */         i = 64 - this.wheatCount;
/*     */       }
/* 176 */       if (i <= 0)
/*     */       {
/* 178 */         return false;
/*     */       }
/* 180 */       this.wheatCount += i;
/* 181 */       ItemStack itemStack2 = cur;
/* 182 */       itemStack2.field_77994_a -= i;
/* 183 */       if (cur.field_77994_a <= 0)
/*     */       {
/* 185 */         player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*     */       }
/* 187 */       alterComposition();
/* 188 */       return true;
/*     */     } 
/* 190 */     if (cur.func_77973_b() == Ic2Items.hops.func_77973_b()) {
/*     */       
/* 192 */       if (!this.detailed || this.type > 1)
/*     */       {
/* 194 */         return false;
/*     */       }
/* 196 */       this.type = 1;
/* 197 */       int i = cur.field_77994_a;
/* 198 */       if (player.func_70093_af())
/*     */       {
/* 200 */         i = 1;
/*     */       }
/* 202 */       if (i > 64 - this.hopsCount)
/*     */       {
/* 204 */         i = 64 - this.hopsCount;
/*     */       }
/* 206 */       if (i <= 0)
/*     */       {
/* 208 */         return false;
/*     */       }
/* 210 */       this.hopsCount += i;
/* 211 */       ItemStack itemStack3 = cur;
/* 212 */       itemStack3.field_77994_a -= i;
/* 213 */       if (cur.field_77994_a <= 0)
/*     */       {
/* 215 */         player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*     */       }
/* 217 */       alterComposition();
/* 218 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 222 */     if (cur.func_77973_b() != Items.field_151120_aE)
/*     */     {
/* 224 */       return false;
/*     */     }
/* 226 */     if (this.age > 600 || (this.type > 0 && this.type != 2))
/*     */     {
/* 228 */       return false;
/*     */     }
/* 230 */     this.type = 2;
/* 231 */     int wantgive = cur.field_77994_a;
/* 232 */     if (player.func_70093_af())
/*     */     {
/* 234 */       wantgive = 1;
/*     */     }
/* 236 */     if (wantgive > 32 - this.boozeAmount)
/*     */     {
/* 238 */       wantgive = 32 - this.boozeAmount;
/*     */     }
/* 240 */     if (wantgive <= 0)
/*     */     {
/* 242 */       return false;
/*     */     }
/* 244 */     this.boozeAmount += wantgive;
/* 245 */     ItemStack itemStack4 = cur;
/* 246 */     itemStack4.field_77994_a -= wantgive;
/* 247 */     if (cur.field_77994_a <= 0)
/*     */     {
/* 249 */       player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*     */     }
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void alterComposition() {
/* 257 */     if (this.timeRatio == 0)
/*     */     {
/* 259 */       this.age = 0;
/*     */     }
/* 261 */     if (this.timeRatio == 1)
/*     */     {
/* 263 */       if (this.field_145850_b.field_73012_v.nextBoolean()) {
/*     */         
/* 265 */         this.timeRatio = 0;
/*     */       }
/* 267 */       else if (this.field_145850_b.field_73012_v.nextBoolean()) {
/*     */         
/* 269 */         this.timeRatio = 5;
/*     */       } 
/*     */     }
/* 272 */     if (this.timeRatio == 2 && this.field_145850_b.field_73012_v.nextBoolean())
/*     */     {
/* 274 */       this.timeRatio = 5;
/*     */     }
/* 276 */     if (this.timeRatio > 2)
/*     */     {
/* 278 */       this.timeRatio = 5;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean drainLiquid(int amount) {
/* 284 */     if (isEmpty())
/*     */     {
/* 286 */       return false;
/*     */     }
/* 288 */     if (amount > this.boozeAmount)
/*     */     {
/* 290 */       return false;
/*     */     }
/* 292 */     enforceUndetailed();
/* 293 */     if (this.type == 2) {
/*     */       
/* 295 */       int progress = this.age * 100 / timeNedForRum(this.boozeAmount);
/* 296 */       this.boozeAmount -= amount;
/* 297 */       this.age = progress / 100 * timeNedForRum(this.boozeAmount);
/*     */     }
/*     */     else {
/*     */       
/* 301 */       this.boozeAmount -= amount;
/*     */     } 
/* 303 */     if (this.boozeAmount <= 0) {
/*     */       
/* 305 */       if (this.type == 1) {
/*     */         
/* 307 */         this.hopsCount = 0;
/* 308 */         this.wheatCount = 0;
/* 309 */         this.hopsRatio = 0;
/* 310 */         this.solidRatio = 0;
/* 311 */         this.timeRatio = 0;
/*     */       } 
/* 313 */       this.type = 0;
/* 314 */       this.detailed = true;
/* 315 */       this.boozeAmount = 0;
/*     */     } 
/* 317 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enforceUndetailed() {
/* 322 */     if (!this.detailed) {
/*     */       return;
/*     */     }
/*     */     
/* 326 */     this.detailed = false;
/* 327 */     if (this.type == 1) {
/*     */       
/* 329 */       float hops = (this.wheatCount > 0) ? (this.hopsCount / this.wheatCount) : 10.0F;
/* 330 */       if (this.hopsCount <= 0 && this.wheatCount <= 0)
/*     */       {
/* 332 */         hops = 0.0F;
/*     */       }
/* 334 */       float solid = (this.boozeAmount > 0) ? ((this.hopsCount + this.wheatCount) / this.boozeAmount) : 10.0F;
/* 335 */       if (hops <= 0.25F)
/*     */       {
/* 337 */         this.hopsRatio = 0;
/*     */       }
/* 339 */       if (hops > 0.25F && hops <= 0.3333333F)
/*     */       {
/* 341 */         this.hopsRatio = 1;
/*     */       }
/* 343 */       if (hops > 0.3333333F && hops <= 0.5F)
/*     */       {
/* 345 */         this.hopsRatio = 2;
/*     */       }
/* 347 */       if (hops > 0.5F && hops < 2.0F)
/*     */       {
/* 349 */         this.hopsRatio = 3;
/*     */       }
/* 351 */       if (hops >= 2.0F && hops < 3.0F)
/*     */       {
/* 353 */         this.hopsRatio = 4;
/*     */       }
/* 355 */       if (hops >= 3.0F && hops < 4.0F)
/*     */       {
/* 357 */         this.hopsRatio = 5;
/*     */       }
/* 359 */       if (hops >= 4.0F && hops < 5.0F)
/*     */       {
/* 361 */         this.hopsRatio = 6;
/*     */       }
/* 363 */       if (hops >= 5.0F)
/*     */       {
/* 365 */         this.timeRatio = 5;
/*     */       }
/* 367 */       if (solid <= 0.4166667F && solid > 0.4166667F && solid <= 0.5F)
/*     */       {
/* 369 */         this.solidRatio = 1;
/*     */       }
/* 371 */       if (solid > 0.5F && solid < 1.0F)
/*     */       {
/* 373 */         this.solidRatio = 2;
/*     */       }
/* 375 */       if (solid == 1.0F)
/*     */       {
/* 377 */         this.solidRatio = 3;
/*     */       }
/* 379 */       if (solid > 1.0F && solid < 2.0F)
/*     */       {
/* 381 */         this.solidRatio = 4;
/*     */       }
/* 383 */       if (solid >= 2.0F && solid < 2.4F)
/*     */       {
/* 385 */         this.solidRatio = 5;
/*     */       }
/* 387 */       if (solid >= 2.4F && solid < 4.0F)
/*     */       {
/* 389 */         this.solidRatio = 6;
/*     */       }
/* 391 */       if (solid >= 4.0F)
/*     */       {
/* 393 */         this.timeRatio = 5;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useTreetapOn(EntityPlayer player, int side) {
/* 400 */     ItemStack cur = player.func_71045_bC();
/* 401 */     if (cur != null && cur.func_77973_b() == Ic2Items.treetap.func_77973_b() && cur.func_77960_j() == 0 && side > 1) {
/*     */       
/* 403 */       this.treetapSide = side;
/* 404 */       update();
/* 405 */       if (!player.field_71075_bZ.field_75098_d) {
/*     */         
/* 407 */         ItemStack itemStack = player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c];
/* 408 */         itemStack.field_77994_a--;
/* 409 */         if ((player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c]).field_77994_a == 0)
/*     */         {
/* 411 */           player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] = null;
/*     */         }
/*     */       } 
/* 414 */       return true;
/*     */     } 
/* 416 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 421 */     this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public int calculateMetaValue() {
/* 426 */     if (isEmpty())
/*     */     {
/* 428 */       return 0;
/*     */     }
/* 430 */     if (this.type == 1) {
/*     */       
/* 432 */       enforceUndetailed();
/* 433 */       int value = 0;
/* 434 */       value |= this.timeRatio;
/* 435 */       value <<= 3;
/* 436 */       value |= this.hopsRatio;
/* 437 */       value <<= 3;
/* 438 */       value |= this.solidRatio;
/* 439 */       value <<= 5;
/* 440 */       value |= this.boozeAmount - 1;
/* 441 */       value <<= 2;
/* 442 */       value |= this.type;
/* 443 */       return value;
/*     */     } 
/* 445 */     if (this.type == 2) {
/*     */       
/* 447 */       enforceUndetailed();
/* 448 */       int value = 0;
/* 449 */       int progress = this.age * 100 / timeNedForRum(this.boozeAmount);
/* 450 */       if (progress > 100)
/*     */       {
/* 452 */         progress = 100;
/*     */       }
/* 454 */       value |= progress;
/* 455 */       value <<= 5;
/* 456 */       value |= this.boozeAmount - 1;
/* 457 */       value <<= 2;
/* 458 */       value |= this.type;
/* 459 */       return value;
/*     */     } 
/* 461 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int timeNedForRum(int amount) {
/* 466 */     return (int)((1200 * amount) * Math.pow(0.95D, (amount - 1)));
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\TileEntityBarrel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */