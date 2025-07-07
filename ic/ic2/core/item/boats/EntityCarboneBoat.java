/*    */ package ic2.core.item.boats;
/*    */ 
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class EntityCarboneBoat
/*    */   extends EntityClassicBoat
/*    */ {
/*    */   public EntityCarboneBoat(World world) {
/* 13 */     super(world);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityCarboneBoat(World world, double x, double y, double z) {
/* 18 */     super(world, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 24 */     return Ic2Items.carbonBoat.func_77946_l();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 30 */     return createBase("boatCarbon");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRammingBreaking(double speed) {
/* 36 */     func_70099_a(getItem(), 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onFallingBreaking(float fallingDistance) {
/* 42 */     func_70099_a(getItem(), 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMaxRammingSpeed() {
/* 48 */     return 0.4D;
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\boats\EntityCarboneBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */