/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityItnt
/*    */   extends EntityIC2Explosive
/*    */ {
/*    */   public EntityItnt(World world, double x, double y, double z) {
/* 11 */     super(world, x, y, z, 60, 5.5F, 0.9F, 0.3F, Block.func_149634_a(Ic2Items.industrialTnt.func_77973_b()));
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityItnt(World world) {
/* 16 */     this(world, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\EntityItnt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */