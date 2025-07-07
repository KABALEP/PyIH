/*     */ package ic2.core.item.block;
/*     */ 
/*     */ import ic2.core.block.generator.block.BlockReactorChamber;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemBlockChamber
/*     */   extends ItemBlockRare
/*     */ {
/*     */   public ItemBlockChamber(Block block) {
/*  17 */     super(block);
/*  18 */     func_77656_e(0);
/*  19 */     func_77627_a(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_77647_b(int i) {
/*  24 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack itemstack) {
/*  29 */     int meta = itemstack.func_77960_j();
/*  30 */     switch (meta) {
/*     */       case 0:
/*  32 */         return "blockReactorChamber";
/*  33 */       case 1: return "blockSteamChamber";
/*  34 */     }  return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
/*  41 */     Block block = p_77648_3_.func_147439_a(p_77648_4_, p_77648_5_, p_77648_6_);
/*  42 */     if (block == Blocks.field_150431_aC && (p_77648_3_.func_72805_g(p_77648_4_, p_77648_5_, p_77648_6_) & 0x7) < 1) {
/*     */       
/*  44 */       p_77648_7_ = 1;
/*     */     }
/*  46 */     else if (block != Blocks.field_150395_bd && block != Blocks.field_150329_H && block != Blocks.field_150330_I && !block.isReplaceable((IBlockAccess)p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) {
/*     */       
/*  48 */       if (p_77648_7_ == 0)
/*     */       {
/*  50 */         p_77648_5_--;
/*     */       }
/*     */       
/*  53 */       if (p_77648_7_ == 1)
/*     */       {
/*  55 */         p_77648_5_++;
/*     */       }
/*     */       
/*  58 */       if (p_77648_7_ == 2)
/*     */       {
/*  60 */         p_77648_6_--;
/*     */       }
/*     */       
/*  63 */       if (p_77648_7_ == 3)
/*     */       {
/*  65 */         p_77648_6_++;
/*     */       }
/*     */       
/*  68 */       if (p_77648_7_ == 4)
/*     */       {
/*  70 */         p_77648_4_--;
/*     */       }
/*     */       
/*  73 */       if (p_77648_7_ == 5)
/*     */       {
/*  75 */         p_77648_4_++;
/*     */       }
/*     */     } 
/*     */     
/*  79 */     if (p_77648_1_.field_77994_a == 0)
/*     */     {
/*  81 */       return false;
/*     */     }
/*  83 */     if (!p_77648_2_.func_82247_a(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_))
/*     */     {
/*  85 */       return false;
/*     */     }
/*  87 */     if (p_77648_5_ == 255 && this.field_150939_a.func_149688_o().func_76220_a())
/*     */     {
/*  89 */       return false;
/*     */     }
/*  91 */     if (canBlockPlaceAt(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_2_, p_77648_1_.func_77960_j())) {
/*     */       
/*  93 */       int i1 = func_77647_b(p_77648_1_.func_77960_j());
/*  94 */       int j1 = this.field_150939_a.func_149660_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, i1);
/*     */       
/*  96 */       if (placeBlockAt(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, j1)) {
/*     */         
/*  98 */         p_77648_3_.func_72908_a((p_77648_4_ + 0.5F), (p_77648_5_ + 0.5F), (p_77648_6_ + 0.5F), this.field_150939_a.field_149762_H.func_150496_b(), (this.field_150939_a.field_149762_H.func_150497_c() + 1.0F) / 2.0F, this.field_150939_a.field_149762_H.func_150494_d() * 0.8F);
/*  99 */         p_77648_1_.field_77994_a--;
/*     */       } 
/*     */       
/* 102 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canBlockPlaceAt(World world, int x, int y, int z, EntityPlayer player, int meta) {
/* 112 */     AxisAlignedBB axis = this.field_150939_a.func_149668_a(world, x, y, z);
/* 113 */     if (axis != null && world.func_72917_a(axis, (Entity)player)) {
/*     */       
/* 115 */       Block block = world.func_147439_a(x, y, z);
/* 116 */       if (block.isReplaceable((IBlockAccess)world, x, y, z) && ((BlockReactorChamber)this.field_150939_a).isReactorNearby(world, x, y, z, (meta == 1)))
/*     */       {
/* 118 */         return true;
/*     */       }
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\block\ItemBlockChamber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */