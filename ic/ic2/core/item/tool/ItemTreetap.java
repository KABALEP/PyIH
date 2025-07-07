/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.TileEntityBarrel;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class ItemTreetap
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemTreetap(int index) {
/*  26 */     super(index);
/*  27 */     func_77625_d(1);
/*  28 */     func_77656_e(16);
/*  29 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float a, float b, float c) {
/*  34 */     if (world.func_147439_a(i, j, k) == Block.func_149634_a(Ic2Items.blockBarrel.func_77973_b()))
/*     */     {
/*  36 */       return ((TileEntityBarrel)world.func_147438_o(i, j, k)).useTreetapOn(entityplayer, side);
/*     */     }
/*  38 */     if (world.func_147439_a(i, j, k) == Block.func_149634_a(Ic2Items.rubberWood.func_77973_b())) {
/*     */       
/*  40 */       if (attemptExtract(entityplayer, world, i, j, k, side, (List)null) && IC2.platform.isSimulating())
/*     */       {
/*  42 */         itemstack.func_77972_a(1, (EntityLivingBase)entityplayer);
/*     */       }
/*  44 */       return true;
/*     */     } 
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void ejectHarz(World world, int x, int y, int z, int side, int quantity) {
/*  51 */     double ejectX = x + 0.5D;
/*  52 */     double ejectY = y + 0.5D;
/*  53 */     double ejectZ = z + 0.5D;
/*  54 */     if (side == 2) {
/*     */       
/*  56 */       ejectZ -= 0.3D;
/*     */     }
/*  58 */     else if (side == 5) {
/*     */       
/*  60 */       ejectX += 0.3D;
/*     */     }
/*  62 */     else if (side == 3) {
/*     */       
/*  64 */       ejectZ += 0.3D;
/*     */     }
/*  66 */     else if (side == 4) {
/*     */       
/*  68 */       ejectX -= 0.3D;
/*     */     } 
/*  70 */     for (int i = 0; i < quantity; i++) {
/*     */       
/*  72 */       EntityItem entityitem = new EntityItem(world, ejectX, ejectY, ejectZ, Ic2Items.resin.func_77946_l());
/*  73 */       entityitem.field_145804_b = 10;
/*  74 */       world.func_72838_d((Entity)entityitem);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean attemptExtract(EntityPlayer entityplayer, World world, int i, int j, int k, int l, List<ItemStack> stacks) {
/*  80 */     int meta = world.func_72805_g(i, j, k);
/*  81 */     if (meta < 2 || meta % 6 != l)
/*     */     {
/*  83 */       return false;
/*     */     }
/*  85 */     if (meta < 6) {
/*     */       
/*  87 */       if (IC2.platform.isSimulating()) {
/*     */         
/*  89 */         world.func_72921_c(i, j, k, meta + 6, 3);
/*  90 */         if (stacks != null) {
/*     */           
/*  92 */           stacks.add(StackUtil.copyWithSize(Ic2Items.resin, world.field_73012_v.nextInt(3) + 1));
/*     */         }
/*     */         else {
/*     */           
/*  96 */           ejectHarz(world, i, j, k, l, world.field_73012_v.nextInt(3) + 1 + getFortuneBonus(entityplayer.func_71045_bC()));
/*     */         } 
/*  98 */         if (entityplayer != null)
/*     */         {
/* 100 */           IC2.achievements.issueAchievement(entityplayer, "acquireResin");
/*     */         }
/* 102 */         world.func_147464_a(i, j, k, Block.func_149634_a(Ic2Items.rubberWood.func_77973_b()), 100);
/* 103 */         ((NetworkManager)IC2.network.get()).announceBlockUpdate(world, i, j, k);
/*     */       } 
/* 105 */       if (IC2.platform.isRendering() && entityplayer != null)
/*     */       {
/* 107 */         IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/Treetap.ogg", true, IC2.audioManager.defaultVolume);
/*     */       }
/* 109 */       return true;
/*     */     } 
/* 111 */     if (world.field_73012_v.nextInt(5) == 0 && IC2.platform.isSimulating()) {
/*     */       
/* 113 */       world.func_72921_c(i, j, k, 1, 3);
/* 114 */       ((NetworkManager)IC2.network.get()).announceBlockUpdate(world, i, j, k);
/*     */     } 
/* 116 */     if (world.field_73012_v.nextInt(5) == 0) {
/*     */       
/* 118 */       if (IC2.platform.isSimulating()) {
/*     */         
/* 120 */         ejectHarz(world, i, j, k, l, 1 + getFortuneBonus(entityplayer.func_71045_bC()));
/* 121 */         if (stacks != null) {
/*     */           
/* 123 */           stacks.add(StackUtil.copyWithSize(Ic2Items.resin, 1));
/*     */         }
/*     */         else {
/*     */           
/* 127 */           ejectHarz(world, i, j, k, l, 1 + getFortuneBonus(entityplayer.func_71045_bC()));
/*     */         } 
/*     */       } 
/* 130 */       if (IC2.platform.isRendering() && entityplayer != null)
/*     */       {
/* 132 */         IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/Treetap.ogg", true, IC2.audioManager.defaultVolume);
/*     */       }
/* 134 */       return true;
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFortuneBonus(ItemStack item) {
/* 141 */     int level = EnchantmentHelper.func_77506_a(Enchantment.field_77346_s.field_77352_x, item);
/* 142 */     if (level <= 0)
/*     */     {
/* 144 */       return 0;
/*     */     }
/* 146 */     return field_77697_d.nextInt(level + 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemTreetap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */