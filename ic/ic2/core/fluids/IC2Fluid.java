/*    */ package ic2.core.fluids;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*    */ import net.minecraft.util.IIcon;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IC2Fluid
/*    */   extends Fluid
/*    */ {
/* 17 */   public static HashMap<String, Fluid> fluids = new HashMap<>();
/*    */   boolean both;
/*    */   String fluidID;
/* 20 */   IIcon[] textures = new IIcon[2];
/*    */ 
/*    */   
/*    */   public IC2Fluid(String fluidName, boolean flowing) {
/* 24 */     super(fluidName);
/* 25 */     this.fluidID = fluidName;
/* 26 */     this.both = flowing;
/* 27 */     fluids.put(fluidName, this);
/* 28 */     FluidRegistry.registerFluid(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getStillIcon() {
/* 34 */     return this.textures[0];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IIcon getFlowingIcon() {
/* 40 */     return this.textures[1];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName() {
/* 46 */     return super.getUnlocalizedName() + ".name";
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerTexture(IIconRegister par1) {
/* 52 */     if (this.both) {
/*    */       
/* 54 */       this.textures[0] = par1.func_94245_a("ic2:fluids/" + this.fluidID + "_still");
/* 55 */       this.textures[1] = par1.func_94245_a("ic2:fluids/" + this.fluidID + "_flowing");
/*    */       return;
/*    */     } 
/* 58 */     IIcon icon = par1.func_94245_a("ic2:fluids/" + this.fluidID + "_still");
/* 59 */     this.textures[0] = icon;
/* 60 */     this.textures[1] = icon;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public static void initTextures(IIconRegister par1) {
/* 66 */     for (Map.Entry<String, Fluid> entry : fluids.entrySet()) {
/*    */       
/* 68 */       Fluid fluid = entry.getValue();
/* 69 */       if (!(fluid instanceof IC2Fluid)) {
/*    */         continue;
/*    */       }
/*    */       
/* 73 */       ((IC2Fluid)fluid).registerTexture(par1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\fluids\IC2Fluid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */