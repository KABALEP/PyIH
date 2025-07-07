/*     */ package ic2.core.block.crop;
/*     */ 
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropVenomilia
/*     */   extends CropCardBase
/*     */ {
/*     */   public String name() {
/*  23 */     return "Venomilia";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String discoveredBy() {
/*  29 */     return "raGan";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int tier() {
/*  35 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int stat(int n) {
/*  41 */     switch (n) {
/*     */ 
/*     */       
/*     */       case 0:
/*  45 */         return 3;
/*     */ 
/*     */       
/*     */       case 1:
/*  49 */         return 1;
/*     */ 
/*     */       
/*     */       case 2:
/*  53 */         return 3;
/*     */ 
/*     */       
/*     */       case 3:
/*  57 */         return 3;
/*     */ 
/*     */       
/*     */       case 4:
/*  61 */         return 3;
/*     */     } 
/*     */ 
/*     */     
/*  65 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] attributes() {
/*  73 */     return new String[] { "Purple", "Flower", "Tulip", "Poison" };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon getSprite(ICropTile crop) {
/*  79 */     if (crop.getSize() <= 3)
/*     */     {
/*  81 */       return Ic2Icons.getTexture("bc")[crop.getSize() + 11];
/*     */     }
/*  83 */     if (crop.getSize() == 4)
/*     */     {
/*  85 */       return Ic2Icons.getTexture("bc")[23];
/*     */     }
/*  87 */     return Ic2Icons.getTexture("bc")[25];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  93 */     return ((crop.getSize() <= 4 && crop.getLightLevel() >= 12) || crop.getSize() == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  99 */     return (crop.getSize() >= 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/* 105 */     if (crop.getSize() == 5)
/*     */     {
/* 107 */       return new ItemStack(Ic2Items.grinPowder.func_77973_b(), 1);
/*     */     }
/* 109 */     if (crop.getSize() >= 4)
/*     */     {
/* 111 */       return new ItemStack(Items.field_151100_aR, 1, 5);
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSizeAfterHarvest(ICropTile crop) {
/* 119 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int growthDuration(ICropTile crop) {
/* 125 */     if (crop.getSize() >= 3)
/*     */     {
/* 127 */       return 600;
/*     */     }
/* 129 */     return 400;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean rightclick(ICropTile crop, EntityPlayer player) {
/* 135 */     if (!player.func_70093_af())
/*     */     {
/* 137 */       onEntityCollision(crop, (Entity)player);
/*     */     }
/* 139 */     return crop.harvest(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean leftclick(ICropTile crop, EntityPlayer player) {
/* 145 */     if (!player.func_70093_af())
/*     */     {
/* 147 */       onEntityCollision(crop, (Entity)player);
/*     */     }
/* 149 */     return crop.pick(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 155 */     if (crop.getSize() == 5 && entity instanceof EntityLivingBase) {
/*     */       
/* 157 */       if (entity instanceof EntityPlayer && ((EntityPlayer)entity).func_70093_af() && IC2.random.nextInt(50) != 0)
/*     */       {
/* 159 */         return super.onEntityCollision(crop, entity);
/*     */       }
/* 161 */       ((EntityLivingBase)entity).func_70690_d(new PotionEffect(Potion.field_76436_u.func_76396_c(), (IC2.random.nextInt(10) + 5) * 20, 0));
/* 162 */       crop.setSize((byte)4);
/* 163 */       crop.updateState();
/*     */     } 
/* 165 */     return super.onEntityCollision(crop, entity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWeed(ICropTile crop) {
/* 171 */     return (crop.getSize() == 5 && crop.getGrowth() >= 8);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxSize() {
/* 177 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHavestSize(ICropTile crop) {
/* 183 */     return 5;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropVenomilia.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */