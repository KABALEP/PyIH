/*    */ package ic2.core.item.boats;
/*    */ 
/*    */ import cpw.mods.fml.common.FMLLog;
/*    */ import ic2.core.Ic2Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityRubberBoat
/*    */   extends EntityClassicBoat
/*    */ {
/*    */   public EntityRubberBoat(World world) {
/* 13 */     super(world);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityRubberBoat(World world, double x, double y, double z) {
/* 18 */     super(world, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItem() {
/* 24 */     return Ic2Items.rubberBoat.func_77946_l();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 30 */     return createBase("boatRubber");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMaxRammingSpeed() {
/* 36 */     return 0.23D;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRammingBreaking(double speed) {
/* 42 */     func_85030_a("random.pop", 16.0F, 8.0F);
/* 43 */     FMLLog.getLogger().info("Testing");
/* 44 */     func_70099_a(((speed > 0.26D) ? Ic2Items.boatRubberBroken : Ic2Items.boatRubber).func_77946_l(), 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onFallingBreaking(float fallingDistance) {
/* 50 */     func_70099_a(getItem(), 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\boats\EntityRubberBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */