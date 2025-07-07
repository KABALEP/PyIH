/*    */ package ic2.core.block.crop;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.api.crops.CropCard;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.util.StatCollector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CropCardBase
/*    */   extends CropCard
/*    */ {
/*    */   public String discoveredBy() {
/* 16 */     return "Alblaka";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerSprites(IIconRegister iconRegister) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public String displayName() {
/* 28 */     return StatCollector.func_74838_a("crop." + name() + ".name");
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\crop\CropCardBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */