/*    */ package ic2.core.block.generator.gui;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.block.generator.container.ContainerBaseGenerator;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiGeoGenerator
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerBaseGenerator container;
/*    */   public String name;
/*    */   public String inv;
/*    */   
/*    */   public GuiGeoGenerator(ContainerBaseGenerator container) {
/* 22 */     super((Container)container);
/* 23 */     this.container = container;
/* 24 */     this.name = StatCollector.func_74838_a("blockGeoGenerator.name");
/* 25 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 30 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 31 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 36 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 37 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIGeoGenerator.png"));
/* 38 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 39 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 40 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 41 */     if (this.container.tileEntity.fuel > 0) {
/*    */       
/* 43 */       int i1 = this.container.tileEntity.gaugeFuelScaled(14);
/* 44 */       func_73729_b(j + 66, k + 36 + 14 - i1, 176, 14 - i1, 14, i1);
/*    */     } 
/* 46 */     if (this.container.tileEntity.storage > 0) {
/*    */       
/* 48 */       int i2 = this.container.tileEntity.gaugeStorageScaled(22);
/* 49 */       func_73729_b(j + 89, k + 39, 177, 19, i2, 6);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\gui\GuiGeoGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */