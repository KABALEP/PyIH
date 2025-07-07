/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.block.machine.container.ContainerCropHarvester;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiCropHarvester
/*    */   extends GuiContainer
/*    */ {
/*    */   TileEntityCropHarvester harvester;
/*    */   String name;
/*    */   String inv;
/*    */   
/*    */   public GuiCropHarvester(ContainerCropHarvester p_i1072_1_) {
/* 19 */     super((Container)p_i1072_1_);
/* 20 */     this.harvester = p_i1072_1_.machine;
/* 21 */     this.name = StatCollector.func_74838_a("blockCropHarvester.name");
/* 22 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 27 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 28 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 33 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 34 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUICropHarvester.png"));
/* 35 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 36 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 37 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 38 */     if (this.harvester.energy > 0) {
/*    */       
/* 40 */       int l = (int)(this.harvester.energy / this.harvester.maxEnergy * 14.0F);
/* 41 */       func_73729_b(j + 20, k + 36 + 14 - l, 176, 14 - l, 14, l);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiCropHarvester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */