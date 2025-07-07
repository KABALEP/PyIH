/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBooze
/*     */   extends ItemIC2
/*     */ {
/*     */   public String[] solidRatio;
/*     */   public String[] hopsRatio;
/*     */   public String[] timeRatio;
/*     */   public int[] baseDuration;
/*     */   public float[] baseIntensity;
/*  23 */   public static float rumStackability = 2.0F;
/*  24 */   public static int rumDuration = 600;
/*     */ 
/*     */   
/*     */   public ItemBooze(int index) {
/*  28 */     super(index);
/*  29 */     this.solidRatio = new String[] { "Watery ", "Clear ", "Lite ", "", "Strong ", "Thick ", "Stodge ", "X" };
/*  30 */     this.hopsRatio = new String[] { "Soup ", "Alcfree ", "White ", "", "Dark ", "Full ", "Black ", "X" };
/*  31 */     this.timeRatio = new String[] { "Brew", "Youngster", "Beer", "Ale", "Dragonblood", "X", "X", "X" };
/*  32 */     this.baseDuration = new int[] { 300, 600, 900, 1200, 1600, 2000, 2400 };
/*  33 */     this.baseIntensity = new float[] { 0.4F, 0.75F, 1.0F, 1.5F, 2.0F };
/*  34 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int meta) {
/*  40 */     int type = getTypeOfValue(meta);
/*  41 */     if (type == 1)
/*     */     {
/*  43 */       return Ic2Icons.getTexture("i0")[this.iconIndex + getTimeRatioOfBeerValue(meta)];
/*     */     }
/*  45 */     if (type == 2)
/*     */     {
/*  47 */       return Ic2Icons.getTexture("i0")[this.iconIndex + 6];
/*     */     }
/*  49 */     return Ic2Icons.getTexture("i0")[this.iconIndex];
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77653_i(ItemStack itemstack) {
/*  54 */     int meta = itemstack.func_77960_j();
/*  55 */     int type = getTypeOfValue(meta);
/*  56 */     if (type == 1) {
/*     */       
/*  58 */       if (getTimeRatioOfBeerValue(meta) == 5)
/*     */       {
/*  60 */         return StatCollector.func_74838_a("item.beerBlack.name");
/*     */       }
/*  62 */       return StatCollector.func_74838_a("item.beerSolidRatio" + getSolidRatioOfBeerValue(meta) + ".name") + StatCollector.func_74838_a("item.beerHopsRatio" + getHopsRatioOfBeerValue(meta) + ".name") + StatCollector.func_74838_a("item.beerTimeRatio" + getTimeRatioOfBeerValue(meta) + ".name");
/*     */     } 
/*     */ 
/*     */     
/*  66 */     if (type == 2)
/*     */     {
/*  68 */       return StatCollector.func_74838_a("item.beerRum.name");
/*     */     }
/*  70 */     return StatCollector.func_74838_a("item.beerZero.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77654_b(ItemStack itemstack, World world, EntityPlayer player) {
/*  79 */     int meta = itemstack.func_77960_j();
/*  80 */     int type = getTypeOfValue(meta);
/*  81 */     if (type == 0)
/*     */     {
/*  83 */       return new ItemStack(Ic2Items.mugEmpty.func_77973_b());
/*     */     }
/*  85 */     if (type == 1) {
/*     */       
/*  87 */       if (getTimeRatioOfBeerValue(meta) == 5)
/*     */       {
/*  89 */         return drinkBlackStuff(player);
/*     */       }
/*  91 */       int solidRatio = getSolidRatioOfBeerValue(meta);
/*  92 */       int alc = getHopsRatioOfBeerValue(meta);
/*  93 */       int duration = this.baseDuration[solidRatio];
/*  94 */       float intensity = this.baseIntensity[getTimeRatioOfBeerValue(meta)];
/*  95 */       player.func_71024_bL().func_75122_a(6 - alc, solidRatio * 0.15F);
/*  96 */       int max = (int)(intensity * alc * 0.5F);
/*  97 */       PotionEffect slow = player.func_70660_b(Potion.field_76419_f);
/*  98 */       int level = -1;
/*  99 */       if (slow != null)
/*     */       {
/* 101 */         level = slow.func_76458_c();
/*     */       }
/* 103 */       amplifyEffect(player, Potion.field_76419_f, max, intensity, duration);
/* 104 */       if (level > -1) {
/*     */         
/* 106 */         amplifyEffect(player, Potion.field_76420_g, max, intensity, duration);
/* 107 */         if (level > 0) {
/*     */           
/* 109 */           amplifyEffect(player, Potion.field_76421_d, max / 2, intensity, duration);
/* 110 */           if (level > 1) {
/*     */             
/* 112 */             amplifyEffect(player, Potion.field_76429_m, max - 1, intensity, duration);
/* 113 */             if (level > 2) {
/*     */               
/* 115 */               amplifyEffect(player, Potion.field_76431_k, 0, intensity, duration);
/* 116 */               if (level > 3)
/*     */               {
/* 118 */                 player.func_70690_d(new PotionEffect(Potion.field_76433_i.field_76415_H, 1, ((Entity)player).field_70170_p.field_73012_v.nextInt(3)));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 125 */     if (type == 2)
/*     */     {
/* 127 */       if (getProgressOfRumValue(meta) < 100) {
/*     */         
/* 129 */         drinkBlackStuff(player);
/*     */       }
/*     */       else {
/*     */         
/* 133 */         amplifyEffect(player, Potion.field_76426_n, 0, rumStackability, rumDuration);
/* 134 */         PotionEffect def = player.func_70660_b(Potion.field_76429_m);
/* 135 */         int level2 = -1;
/* 136 */         if (def != null)
/*     */         {
/* 138 */           level2 = def.func_76458_c();
/*     */         }
/* 140 */         amplifyEffect(player, Potion.field_76429_m, 2, rumStackability, rumDuration);
/* 141 */         if (level2 >= 0)
/*     */         {
/* 143 */           amplifyEffect(player, Potion.field_76440_q, 0, rumStackability, rumDuration);
/*     */         }
/* 145 */         if (level2 >= 1)
/*     */         {
/* 147 */           amplifyEffect(player, Potion.field_76431_k, 0, rumStackability, rumDuration);
/*     */         }
/*     */       } 
/*     */     }
/* 151 */     return new ItemStack(Ic2Items.mugEmpty.func_77973_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public void amplifyEffect(EntityPlayer player, Potion potion, int max, float intensity, int duration) {
/* 156 */     PotionEffect eff = player.func_70660_b(potion);
/* 157 */     if (eff == null) {
/*     */       
/* 159 */       player.func_70690_d(new PotionEffect(potion.field_76415_H, duration, 0));
/*     */     }
/*     */     else {
/*     */       
/* 163 */       int newdur = eff.func_76459_b();
/* 164 */       int maxnewdur = (int)(duration * (1.0F + intensity * 2.0F) - newdur) / 2;
/* 165 */       if (maxnewdur < 0)
/*     */       {
/* 167 */         maxnewdur = 0;
/*     */       }
/* 169 */       if (maxnewdur < duration)
/*     */       {
/* 171 */         duration = maxnewdur;
/*     */       }
/* 173 */       newdur += duration;
/* 174 */       int newamp = eff.func_76458_c();
/* 175 */       if (newamp < max)
/*     */       {
/* 177 */         newamp++;
/*     */       }
/* 179 */       player.func_70690_d(new PotionEffect(potion.field_76415_H, newdur, newamp));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack drinkBlackStuff(EntityPlayer player) {
/* 185 */     switch (player.field_70170_p.field_73012_v.nextInt(6)) {
/*     */ 
/*     */       
/*     */       case 1:
/* 189 */         player.func_70690_d(new PotionEffect(Potion.field_76431_k.field_76415_H, 1200, 0));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 194 */         player.func_70690_d(new PotionEffect(Potion.field_76440_q.field_76415_H, 2400, 0));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/* 199 */         player.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, 2400, 0));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 4:
/* 204 */         player.func_70690_d(new PotionEffect(Potion.field_76436_u.field_76415_H, 200, 2));
/*     */         break;
/*     */ 
/*     */       
/*     */       case 5:
/* 209 */         player.func_70690_d(new PotionEffect(Potion.field_76433_i.field_76415_H, 1, ((Entity)player).field_70170_p.field_73012_v.nextInt(4)));
/*     */         break;
/*     */     } 
/*     */     
/* 213 */     return new ItemStack(Ic2Items.mugEmpty.func_77973_b());
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_77626_a(ItemStack itemstack) {
/* 218 */     return 32;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumAction func_77661_b(ItemStack itemstack) {
/* 223 */     return EnumAction.drink;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer player) {
/* 228 */     player.func_71008_a(itemstack, func_77626_a(itemstack));
/* 229 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getTypeOfValue(int value) {
/* 234 */     return skipGetOfValue(value, 0, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getAmountOfValue(int value) {
/* 239 */     if (getTypeOfValue(value) == 0)
/*     */     {
/* 241 */       return 0;
/*     */     }
/* 243 */     return skipGetOfValue(value, 2, 5) + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSolidRatioOfBeerValue(int value) {
/* 248 */     return skipGetOfValue(value, 7, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getHopsRatioOfBeerValue(int value) {
/* 253 */     return skipGetOfValue(value, 10, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getTimeRatioOfBeerValue(int value) {
/* 258 */     return skipGetOfValue(value, 13, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getProgressOfRumValue(int value) {
/* 263 */     return skipGetOfValue(value, 7, 7);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int skipGetOfValue(int value, int bitshift, int take) {
/* 268 */     value >>= bitshift;
/* 269 */     take = (int)Math.pow(2.0D, take) - 1;
/* 270 */     return value & take;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemBooze.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */