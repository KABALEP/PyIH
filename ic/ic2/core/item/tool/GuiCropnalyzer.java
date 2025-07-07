/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiCropnalyzer
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerCropnalyzer container;
/*    */   public String name;
/*    */   
/*    */   public GuiCropnalyzer(ContainerCropnalyzer container) {
/* 20 */     super((Container)container);
/* 21 */     this.container = container;
/* 22 */     this.name = StatCollector.func_74838_a("item.itemCropanalyzer.name");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 27 */     this.field_146289_q.func_78276_b(this.name, 74, 11, 0);
/* 28 */     int level = this.container.cropnalyzer.getScannedLevel();
/* 29 */     if (level <= -1) {
/*    */       return;
/*    */     }
/*    */     
/* 33 */     if (level == 0) {
/*    */       
/* 35 */       this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.cropAnalyzer0.name"), 8, 37, 16777215);
/*    */       return;
/*    */     } 
/* 38 */     this.field_146289_q.func_78276_b(this.container.cropnalyzer.getSeedName(), 8, 37, 16777215);
/* 39 */     if (level >= 2) {
/*    */       
/* 41 */       this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.cropAnalyzer.CropTier.name", new Object[] { this.container.cropnalyzer.getSeedTier() }), 8, 50, 16777215);
/* 42 */       this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.cropAnalyzer.CropOwner.name"), 8, 73, 16777215);
/* 43 */       this.field_146289_q.func_78276_b(this.container.cropnalyzer.getSeedDiscovered(), 8, 86, 16777215);
/*    */     } 
/* 45 */     if (level >= 3) {
/*    */       
/* 47 */       this.field_146289_q.func_78276_b(this.container.cropnalyzer.getSeedDesc(0), 8, 109, 16777215);
/* 48 */       this.field_146289_q.func_78276_b(this.container.cropnalyzer.getSeedDesc(1), 8, 122, 16777215);
/*    */     } 
/* 50 */     if (level >= 4) {
/*    */       
/* 52 */       this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.cropAnalyzer.Growth.name"), 118, 37, 11403055);
/* 53 */       this.field_146289_q.func_78276_b("" + this.container.cropnalyzer.getSeedGrowth(), 118, 50, 11403055);
/* 54 */       this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.cropAnalyzer.Gain.name"), 118, 73, 15649024);
/* 55 */       this.field_146289_q.func_78276_b("" + this.container.cropnalyzer.getSeedGain(), 118, 86, 15649024);
/* 56 */       this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.cropAnalyzer.Resis.name"), 118, 109, 52945);
/* 57 */       this.field_146289_q.func_78276_b("" + this.container.cropnalyzer.getSeedResistence(), 118, 122, 52945);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 63 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 64 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUICropnalyzer.png"));
/* 65 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 66 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 67 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\GuiCropnalyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */