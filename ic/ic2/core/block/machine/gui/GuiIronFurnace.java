/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.block.machine.container.ContainerIronFurnace;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiIronFurnace
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerIronFurnace container;
/*    */   public String name;
/*    */   public String inv;
/*    */   
/*    */   public GuiIronFurnace(ContainerIronFurnace container) {
/* 23 */     super((Container)container);
/* 24 */     this.container = container;
/* 25 */     this.name = StatCollector.func_74838_a("blockIronFurnace.name");
/* 26 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 31 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 32 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 37 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 38 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("textures/gui/container/furnace.png"));
/* 39 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 40 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 41 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 42 */     if (this.container.tileEntity.fuel > 0) {
/*    */       
/* 44 */       int l = this.container.tileEntity.gaugeFuelScaled(12);
/* 45 */       func_73729_b(j + 56, k + 36 + 12 - l, 176, 12 - l, 14, l + 2);
/*    */     } 
/* 47 */     int i1 = this.container.tileEntity.gaugeProgressScaled(24);
/* 48 */     func_73729_b(j + 79, k + 34, 176, 14, i1 + 1, 16);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiIronFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */