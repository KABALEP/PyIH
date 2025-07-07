/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.block.machine.container.ContainerElectricMachine;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiCompressor
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerElectricMachine container;
/*    */   public String name;
/*    */   public String inv;
/*    */   
/*    */   public GuiCompressor(ContainerElectricMachine container) {
/* 22 */     super((Container)container);
/* 23 */     this.container = container;
/* 24 */     this.name = StatCollector.func_74838_a("blockCompressor.name");
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
/* 37 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUICompressor.png"));
/* 38 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 39 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 40 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 41 */     int chargeLevel = (int)(14.0F * this.container.tileEntity.getChargeLevel());
/* 42 */     int progress = (int)(24.0F * this.container.tileEntity.getProgress());
/* 43 */     if (chargeLevel > 0)
/*    */     {
/* 45 */       func_73729_b(j + 56, k + 36 + 14 - chargeLevel, 176, 14 - chargeLevel, 14, chargeLevel);
/*    */     }
/* 47 */     if (progress > 0)
/*    */     {
/* 49 */       func_73729_b(j + 79, k + 34, 176, 14, progress + 1, 16);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiCompressor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */