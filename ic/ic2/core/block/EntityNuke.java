/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IC2DamageSource;
/*    */ import ic2.core.Ic2Items;
/*    */ import ic2.core.item.tool.ItemToolWrench;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityNuke
/*    */   extends EntityIC2Explosive {
/*    */   public EntityNuke(World world, double x, double y, double z) {
/* 15 */     super(world, x, y, z, 300, IC2.explosionPowerNuke, 0.05F, 1.5F, Block.func_149634_a(Ic2Items.nuke.func_77973_b()), (DamageSource)IC2DamageSource.nuke);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityNuke(World world) {
/* 20 */     this(world, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_130002_c(EntityPlayer player) {
/* 26 */     if (IC2.platform.isSimulating() && player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c] != null && player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c].func_77973_b() instanceof ItemToolWrench) {
/*    */       
/* 28 */       ItemToolWrench wrench = (ItemToolWrench)player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c].func_77973_b();
/* 29 */       if (wrench.canTakeDamage(player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c], 1)) {
/*    */         
/* 31 */         wrench.damage(player.field_71071_by.field_70462_a[player.field_71071_by.field_70461_c], 1, player);
/* 32 */         func_70106_y();
/*    */       } 
/*    */     } 
/* 35 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\EntityNuke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */