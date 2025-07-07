/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.tile.ISpecialWrenchable;
/*     */ import ic2.api.tile.IWrenchable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemToolWrench
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemToolWrench(int index) {
/*  26 */     super(index);
/*  27 */     func_77656_e(160);
/*  28 */     func_77625_d(1);
/*  29 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeDamage(ItemStack stack, int amount) {
/*  34 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
/*  40 */     if (!canTakeDamage(itemstack, 1))
/*     */     {
/*  42 */       return false;
/*     */     }
/*  44 */     Block blockId = world.func_147439_a(x, y, z);
/*  45 */     int metaData = world.func_72805_g(x, y, z);
/*  46 */     TileEntity tileEntity = world.func_147438_o(x, y, z);
/*  47 */     if (tileEntity instanceof TileEntityTerra) {
/*     */       
/*  49 */       TileEntityTerra tileEntityTerra = (TileEntityTerra)tileEntity;
/*  50 */       if (tileEntityTerra.ejectBlueprint()) {
/*     */         
/*  52 */         if (IC2.platform.isSimulating())
/*     */         {
/*  54 */           damage(itemstack, 1, entityPlayer);
/*     */         }
/*  56 */         if (IC2.platform.isRendering())
/*     */         {
/*  58 */           IC2.audioManager.playOnce(entityPlayer, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
/*     */         }
/*  60 */         return IC2.platform.isSimulating();
/*     */       } 
/*     */     } 
/*  63 */     if (tileEntity instanceof IWrenchable) {
/*     */       
/*  65 */       IWrenchable wrenchable = (IWrenchable)tileEntity;
/*  66 */       if (IC2.keyboard.isAltKeyDown(entityPlayer)) {
/*     */         
/*  68 */         if (entityPlayer.func_70093_af())
/*     */         {
/*  70 */           side = (wrenchable.getFacing() + 5) % 6;
/*     */         }
/*     */         else
/*     */         {
/*  74 */           side = (wrenchable.getFacing() + 1) % 6;
/*     */         }
/*     */       
/*  77 */       } else if (entityPlayer.func_70093_af()) {
/*     */         
/*  79 */         side += side % 2 * -2 + 1;
/*     */       } 
/*  81 */       if (wrenchable.wrenchCanSetFacing(entityPlayer, side)) {
/*     */         
/*  83 */         if (IC2.platform.isSimulating()) {
/*     */           
/*  85 */           wrenchable.setFacing((short)side);
/*  86 */           damage(itemstack, 1, entityPlayer);
/*     */         } 
/*  88 */         if (IC2.platform.isRendering())
/*     */         {
/*  90 */           IC2.audioManager.playOnce(entityPlayer, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
/*     */         }
/*  92 */         return IC2.platform.isSimulating();
/*     */       } 
/*  94 */       if (wrenchable instanceof ISpecialWrenchable && canTakeDamage(itemstack, 5) && ((ISpecialWrenchable)wrenchable).canDoSpecial(entityPlayer, side)) {
/*     */         
/*  96 */         if (IC2.platform.isSimulating()) {
/*     */           
/*  98 */           boolean result = ((ISpecialWrenchable)wrenchable).doSpecial(entityPlayer, side);
/*  99 */           if (result)
/*     */           {
/* 101 */             damage(itemstack, 5, entityPlayer);
/*     */           }
/*     */           
/* 104 */           return true;
/*     */         } 
/* 106 */         if (IC2.platform.isRendering())
/*     */         {
/* 108 */           IC2.audioManager.playOnce(entityPlayer, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
/*     */         }
/* 110 */         return IC2.platform.isSimulating();
/*     */       } 
/* 112 */       if (canTakeDamage(itemstack, overrideWrenchSuccessRate(itemstack) ? 100 : 10) && wrenchable.wrenchCanRemove(entityPlayer)) {
/*     */         
/* 114 */         if (IC2.platform.isSimulating()) {
/*     */           
/* 116 */           Block block = blockId;
/* 117 */           boolean dropOriginalBlock = false;
/* 118 */           double base = wrenchable.getWrenchDropRate();
/* 119 */           base *= applyFortune(itemstack, getModifier(itemstack));
/* 120 */           dropOriginalBlock = (world.field_73012_v.nextFloat() <= base);
/* 121 */           damage(itemstack, 10, entityPlayer);
/* 122 */           if (!dropOriginalBlock && overrideWrenchSuccessRate(itemstack)) {
/*     */             
/* 124 */             dropOriginalBlock = true;
/* 125 */             damage(itemstack, 200, entityPlayer);
/*     */           } 
/* 127 */           List<ItemStack> drops = block.getDrops(world, x, y, z, metaData, 0);
/* 128 */           if (dropOriginalBlock)
/*     */           {
/* 130 */             if (drops.isEmpty()) {
/*     */               
/* 132 */               drops.add(wrenchable.getWrenchDrop(entityPlayer));
/*     */             }
/*     */             else {
/*     */               
/* 136 */               drops.set(0, wrenchable.getWrenchDrop(entityPlayer));
/*     */             } 
/*     */           }
/* 139 */           for (ItemStack itemStack : drops)
/*     */           {
/* 141 */             StackUtil.dropAsEntity(world, x, y, z, itemStack);
/*     */           }
/* 143 */           world.func_147468_f(x, y, z);
/*     */         } 
/* 145 */         if (IC2.platform.isRendering())
/*     */         {
/* 147 */           IC2.audioManager.playOnce(entityPlayer, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.defaultVolume);
/*     */         }
/* 149 */         return IC2.platform.isSimulating();
/*     */       } 
/*     */     } 
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getModifier(ItemStack stack) {
/* 157 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double applyFortune(ItemStack stack, double base) {
/* 162 */     int fortune = EnchantmentHelper.func_77506_a(Enchantment.field_77346_s.field_77352_x, stack);
/* 163 */     for (int i = 0; i < fortune; i++)
/*     */     {
/* 165 */       base += base * 0.1D;
/*     */     }
/* 167 */     return base;
/*     */   }
/*     */ 
/*     */   
/*     */   public void damage(ItemStack is, int damage, EntityPlayer player) {
/* 172 */     is.func_77972_a(damage, (EntityLivingBase)player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean overrideWrenchSuccessRate(ItemStack itemStack) {
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_77616_k(ItemStack p_77616_1_) {
/* 183 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemToolWrench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */