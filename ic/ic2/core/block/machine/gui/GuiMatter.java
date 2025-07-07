/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.block.machine.container.ContainerMatter;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiMatter
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerMatter container;
/*    */   public String name;
/*    */   public String progressLabel;
/*    */   public String amplifierLabel;
/*    */   public String inv;
/*    */   
/*    */   public GuiMatter(ContainerMatter container) {
/* 25 */     super((Container)container);
/* 26 */     this.container = container;
/* 27 */     this.name = StatCollector.func_74838_a("blockMatter.name");
/* 28 */     this.progressLabel = StatCollector.func_74838_a("container.matter.progress.name");
/* 29 */     this.amplifierLabel = StatCollector.func_74838_a("container.matter.amplifier.name");
/* 30 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 35 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 36 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/* 37 */     this.field_146289_q.func_78276_b(this.progressLabel, 16, 20, 4210752);
/* 38 */     this.field_146289_q.func_78276_b(this.container.tileEntity.getProgressAsString(), 16, 28, 4210752);
/* 39 */     if (this.container.tileEntity.scrap > 0) {
/*    */       
/* 41 */       this.field_146289_q.func_78276_b(this.amplifierLabel, 16, 44, 4210752);
/* 42 */       this.field_146289_q.func_78276_b("" + this.container.tileEntity.scrap, 16, 56, 4210752);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 48 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 49 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIMatter.png"));
/* 50 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 51 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 52 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiMatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */