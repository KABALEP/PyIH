/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ItemCell
/*    */   extends ItemIC2
/*    */ {
/*    */   public ItemCell(int index) {
/* 18 */     super(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/* 23 */     float f = 1.0F;
/* 24 */     float f2 = entityplayer.field_70127_C + entityplayer.field_70125_A - entityplayer.field_70127_C * f;
/* 25 */     float f3 = entityplayer.field_70126_B + entityplayer.field_70177_z - entityplayer.field_70126_B * f;
/* 26 */     double d = entityplayer.field_70169_q + entityplayer.field_70165_t - entityplayer.field_70169_q * f;
/* 27 */     double d2 = entityplayer.field_70167_r + entityplayer.field_70163_u - entityplayer.field_70167_r * f + 1.62D - entityplayer.field_70129_M;
/* 28 */     double d3 = entityplayer.field_70166_s + entityplayer.field_70161_v - entityplayer.field_70166_s * f;
/* 29 */     Vec3 vec3d = Vec3.func_72443_a(d, d2, d3);
/* 30 */     float f4 = MathHelper.func_76134_b(-f3 * 0.01745329F - 3.141593F);
/* 31 */     float f5 = MathHelper.func_76126_a(-f3 * 0.01745329F - 3.141593F);
/* 32 */     float f6 = -MathHelper.func_76134_b(-f2 * 0.01745329F);
/* 33 */     float f7 = MathHelper.func_76126_a(-f2 * 0.01745329F);
/* 34 */     float f8 = f5 * f6;
/* 35 */     float f9 = f7;
/* 36 */     float f10 = f4 * f6;
/* 37 */     double d4 = 5.0D;
/* 38 */     Vec3 vec3d2 = vec3d.func_72441_c(f8 * d4, f9 * d4, f10 * d4);
/* 39 */     MovingObjectPosition movingobjectposition = world.func_147447_a(vec3d, vec3d2, true, false, false);
/* 40 */     if (movingobjectposition == null)
/*    */     {
/* 42 */       return itemstack;
/*    */     }
/* 44 */     if (movingobjectposition.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
/*    */       
/* 46 */       int i = movingobjectposition.field_72311_b;
/* 47 */       int j = movingobjectposition.field_72312_c;
/* 48 */       int k = movingobjectposition.field_72309_d;
/* 49 */       if (!world.func_72962_a(entityplayer, i, j, k))
/*    */       {
/* 51 */         return itemstack;
/*    */       }
/* 53 */       if (world.func_147439_a(i, j, k) == Blocks.field_150355_j && world.func_72805_g(i, j, k) == 0 && storeCell(Ic2Items.waterCell.func_77946_l(), entityplayer)) {
/*    */         
/* 55 */         world.func_147468_f(i, j, k);
/* 56 */         itemstack.field_77994_a--;
/* 57 */         return itemstack;
/*    */       } 
/* 59 */       if (world.func_147439_a(i, j, k) == Blocks.field_150353_l && world.func_72805_g(i, j, k) == 0 && storeCell(Ic2Items.lavaCell.func_77946_l(), entityplayer)) {
/*    */         
/* 61 */         world.func_147468_f(i, j, k);
/* 62 */         itemstack.field_77994_a--;
/* 63 */         return itemstack;
/*    */       } 
/*    */     } 
/* 66 */     return itemstack;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean storeCell(ItemStack cell, EntityPlayer player) {
/* 71 */     if (player.field_71071_by.func_70441_a(cell)) {
/*    */       
/* 73 */       if (!IC2.platform.isRendering())
/*    */       {
/* 75 */         player.field_71070_bA.func_75142_b();
/*    */       }
/* 77 */       return true;
/*    */     } 
/* 79 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemCell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */