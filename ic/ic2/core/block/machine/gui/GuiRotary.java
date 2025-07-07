/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.block.machine.container.ContainerAdvancedMachine;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiRotary
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerAdvancedMachine container;
/*    */   public String name;
/*    */   public String heatLabel;
/*    */   public String inv;
/*    */   
/*    */   public GuiRotary(ContainerAdvancedMachine container) {
/* 19 */     super((Container)container);
/* 20 */     this.container = container;
/* 21 */     this.name = StatCollector.func_74838_a("blockRotary.name");
/* 22 */     this.heatLabel = StatCollector.func_74838_a("container.rotary.speed.name");
/* 23 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 28 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 29 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/* 30 */     this.field_146289_q.func_78276_b(this.heatLabel, 10, 36, 4210752);
/* 31 */     int heat = (int)(this.container.machine.getSpeedLevel() * 100.0F);
/* 32 */     this.field_146289_q.func_78276_b("" + heat + "%", 10, 44, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 37 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 38 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIRotary.png"));
/* 39 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 40 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 41 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 42 */     int l = (int)(this.container.machine.getChargeLevel() * 14.0F);
/* 43 */     if (l > 0)
/*    */     {
/* 45 */       func_73729_b(j + 56, k + 36 + 14 - l, 176, 14 - l, 14, l);
/*    */     }
/* 47 */     int i1 = (int)(this.container.machine.getProgress() * 24.0F);
/* 48 */     func_73729_b(j + 79, k + 34, 176, 14, i1 + 1, 16);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiRotary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */