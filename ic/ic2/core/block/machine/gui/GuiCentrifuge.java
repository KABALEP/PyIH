/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.block.machine.container.ContainerAdvancedMachine;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class GuiCentrifuge
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerAdvancedMachine container;
/*    */   public String name;
/*    */   public String heatLabel;
/*    */   public String inv;
/*    */   
/*    */   public GuiCentrifuge(ContainerAdvancedMachine container) {
/* 20 */     super((Container)container);
/* 21 */     this.container = container;
/* 22 */     this.name = StatCollector.func_74838_a("blockCentrifug.name");
/* 23 */     this.heatLabel = StatCollector.func_74838_a("container.Centrifug.speed.name");
/* 24 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 29 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 30 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/* 31 */     this.field_146289_q.func_78276_b(this.heatLabel, 10, 36, 4210752);
/* 32 */     int heat = (int)(this.container.machine.getSpeedLevel() * 100.0F);
/* 33 */     this.field_146289_q.func_78276_b("" + heat + "%", 10, 44, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 38 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 39 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUICentrifuge.png"));
/* 40 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 41 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 42 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 43 */     int l = (int)(this.container.machine.getChargeLevel() * 14.0F);
/* 44 */     if (l > 0)
/*    */     {
/* 46 */       func_73729_b(j + 40, k + 36 + 14 - l, 176, 14 - l, 14, l);
/*    */     }
/* 48 */     int i1 = (int)(this.container.machine.getProgress() * 24.0F);
/* 49 */     func_73729_b(j + 63, k + 34, 176, 14, i1 + 1, 16);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiCentrifuge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */