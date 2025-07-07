/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockIC2Explosive
/*     */   extends BlockTex
/*     */ {
/*     */   public boolean canExplodeByHand;
/*     */   
/*     */   public BlockIC2Explosive(boolean manual) {
/*  20 */     super(Material.field_151590_u);
/*  21 */     this.canExplodeByHand = manual;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149726_b(World world, int i, int j, int k) {
/*  26 */     super.func_149726_b(world, i, j, k);
/*  27 */     if (world.func_72864_z(i, j, k) && getExplosionEntity(world, i, j, k, null, ActivationType.BlockUpdate) != null)
/*     */     {
/*  29 */       removedByPlayer(world, null, i, j, k, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149695_a(World world, int i, int j, int k, Block l) {
/*  35 */     if (l != null && l.func_149744_f() && world.func_72864_z(i, j, k) && getExplosionEntity(world, i, j, k, null, ActivationType.BlockUpdate) != null)
/*     */     {
/*  37 */       removedByPlayer(world, null, i, j, k, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random random) {
/*  43 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World world, int i, int j, int k) {
/*  48 */     EntityIC2Explosive entitytntprimed = getExplosionEntity(world, i + 0.5F, j + 0.5F, k + 0.5F, null, ActivationType.Explosion);
/*  49 */     if (entitytntprimed != null) {
/*     */       
/*  51 */       entitytntprimed.fuse = world.field_73012_v.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
/*  52 */       world.func_72838_d(entitytntprimed);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
/*  59 */     if (!IC2.platform.isSimulating())
/*     */     {
/*  61 */       return false;
/*     */     }
/*  63 */     int l = world.func_72805_g(x, y, z);
/*  64 */     world.func_147468_f(x, y, z);
/*  65 */     if (player != null && (l & 0x1) == 0 && !this.canExplodeByHand) {
/*     */       
/*  67 */       func_149642_a(world, x, y, z, new ItemStack(this, 1, 0));
/*     */     }
/*     */     else {
/*     */       
/*  71 */       onIgnite(world, player, x, y, z);
/*  72 */       EntityIC2Explosive entitytntprimed = getExplosionEntity(world, x + 0.5F, y + 0.5F, z + 0.5F, (player == null) ? null : player.func_146103_bH().getName(), ActivationType.Player);
/*  73 */       world.func_72838_d(entitytntprimed);
/*  74 */       world.func_72956_a(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
/*     */     } 
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149727_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
/*  81 */     if (par5EntityPlayer.func_71045_bC() != null && par5EntityPlayer.func_71045_bC().func_77973_b() == Items.field_151033_d) {
/*     */       
/*  83 */       par1World.func_72921_c(par2, par3, par4, 1, 3);
/*  84 */       removedByPlayer(par1World, par5EntityPlayer, par2, par3, par4, false);
/*  85 */       return true;
/*     */     } 
/*  87 */     return super.func_149727_a(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract EntityIC2Explosive getExplosionEntity(World paramWorld, float paramFloat1, float paramFloat2, float paramFloat3, String paramString, ActivationType paramActivationType);
/*     */ 
/*     */   
/*     */   public void onIgnite(World world, EntityPlayer player, int x, int y, int z) {}
/*     */   
/*     */   public enum ActivationType
/*     */   {
/*  98 */     BlockUpdate,
/*  99 */     Explosion,
/* 100 */     Player;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\BlockIC2Explosive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */