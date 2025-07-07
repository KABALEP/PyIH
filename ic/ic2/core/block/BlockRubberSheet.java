/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioPosition;
/*     */ import ic2.core.util.IExtraData;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockRubberSheet
/*     */   extends BlockTex
/*     */   implements IExtraData
/*     */ {
/*     */   public BlockRubberSheet(int sprite) {
/*  19 */     super(sprite, Material.field_151580_n);
/*  20 */     func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  21 */     func_149711_c(0.8F);
/*  22 */     func_149752_b(2.0F);
/*  23 */     func_149672_a(field_149775_l);
/*  24 */     func_149663_c("blockRubber");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c() {
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149686_d() {
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149742_c(World world, int i, int j, int k) {
/*  42 */     return (isBlockSupporter(world, i - 1, j, k) || isBlockSupporter(world, i + 1, j, k) || isBlockSupporter(world, i, j, k - 1) || isBlockSupporter(world, i, j, k + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBlockSupporter(World world, int i, int j, int k) {
/*  47 */     return (world.func_147445_c(i, j, k, false) || world.func_147439_a(i, j, k) == this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSupportWeight(World world, int i, int j, int k) {
/*  52 */     if (world.func_72805_g(i, j, k) == 1)
/*     */     {
/*  54 */       return true;
/*     */     }
/*  56 */     boolean xup = false;
/*  57 */     boolean xdown = false;
/*  58 */     boolean zup = false;
/*  59 */     boolean zdown = false; int x;
/*  60 */     for (x = i;; x--) {
/*     */       
/*  62 */       if (world.func_147445_c(x, j, k, false)) {
/*     */         
/*  64 */         xdown = true;
/*     */         break;
/*     */       } 
/*  67 */       if (world.func_147439_a(x, j, k) != this) {
/*     */         break;
/*     */       }
/*     */       
/*  71 */       if (world.func_147445_c(x, j - 1, k, false)) {
/*     */         
/*  73 */         xdown = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*  77 */     for (x = i;; x++) {
/*     */       
/*  79 */       if (world.func_147445_c(x, j, k, false)) {
/*     */         
/*  81 */         xup = true;
/*     */         break;
/*     */       } 
/*  84 */       if (world.func_147439_a(x, j, k) != this) {
/*     */         break;
/*     */       }
/*     */       
/*  88 */       if (world.func_147445_c(x, j - 1, k, false)) {
/*     */         
/*  90 */         xup = true;
/*     */         break;
/*     */       } 
/*     */     } 
/*  94 */     if (xup && xdown) {
/*     */       
/*  96 */       world.func_72921_c(i, j, k, 1, 3);
/*  97 */       return true;
/*     */     }  int z;
/*  99 */     for (z = k;; z--) {
/*     */       
/* 101 */       if (world.func_147445_c(i, j, z, false)) {
/*     */         
/* 103 */         zdown = true;
/*     */         break;
/*     */       } 
/* 106 */       if (world.func_147439_a(i, j, z) != this) {
/*     */         break;
/*     */       }
/*     */       
/* 110 */       if (world.func_147445_c(i, j - 1, z, false)) {
/*     */         
/* 112 */         zdown = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 116 */     for (z = k;; z++) {
/*     */       
/* 118 */       if (world.func_147445_c(i, j, z, false)) {
/*     */         
/* 120 */         zup = true;
/*     */         break;
/*     */       } 
/* 123 */       if (world.func_147439_a(i, j, z) != this) {
/*     */         break;
/*     */       }
/*     */       
/* 127 */       if (world.func_147445_c(i, j - 1, z, false)) {
/*     */         
/* 129 */         zup = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 133 */     if (zup && zdown) {
/*     */       
/* 135 */       world.func_72921_c(i, j, k, 1, 3);
/* 136 */       return true;
/*     */     } 
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int i, int j, int k, Block l) {
/* 144 */     if (world.func_72805_g(i, j, k) == 1)
/*     */     {
/* 146 */       world.func_72921_c(i, j, k, 0, 3);
/*     */     }
/* 148 */     if (!func_149742_c(world, i, j, k)) {
/*     */       
/* 150 */       func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
/* 151 */       world.func_147468_f(i, j, k);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149670_a(World world, int i, int j, int k, Entity entity) {
/* 158 */     if (world.func_147445_c(i, j - 1, k, false)) {
/*     */       return;
/*     */     }
/*     */     
/* 162 */     if (entity instanceof net.minecraft.entity.EntityLivingBase && !canSupportWeight(world, i, j, k)) {
/*     */       
/* 164 */       world.func_147468_f(i, j, k);
/*     */       return;
/*     */     } 
/* 167 */     if (entity.field_70181_x <= -0.4000000059604645D) {
/*     */       
/* 169 */       IC2.audioManager.playOnce(new AudioPosition(world, i, j, k), "Tools/RubberTrampoline.ogg");
/* 170 */       entity.field_70143_R = 0.0F;
/* 171 */       entity.field_70159_w *= 1.100000023841858D;
/* 172 */       if (entity instanceof net.minecraft.entity.EntityLivingBase) {
/*     */         
/* 174 */         if (entity instanceof net.minecraft.entity.player.EntityPlayer && entity.func_70093_af())
/*     */         {
/* 176 */           entity.field_70181_x *= -0.1000000014901161D;
/*     */         }
/*     */         else
/*     */         {
/* 180 */           entity.field_70181_x *= -0.800000011920929D;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 185 */         entity.field_70181_x *= -0.800000011920929D;
/*     */       } 
/* 187 */       entity.field_70179_y *= 1.100000023841858D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 194 */     Ic2Items.rubberTrampoline = new ItemStack(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockRubberSheet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */