/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class GuiFluidOMatClosed
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerFluidOMatClosed container;
/*    */   public String name;
/*    */   public String wantLabel;
/*    */   public String offerLabel;
/*    */   public String paidForLabel;
/*    */   public String inv;
/*    */   
/*    */   public GuiFluidOMatClosed(ContainerFluidOMatClosed par1) {
/* 21 */     super((Container)par1);
/* 22 */     this.container = par1;
/* 23 */     this.name = StatCollector.func_74838_a("blockPersonalTraderFluid.name");
/* 24 */     this.wantLabel = StatCollector.func_74838_a("container.personalTrader.want.name");
/* 25 */     this.offerLabel = StatCollector.func_74838_a("container.personalTrader.offer.name");
/* 26 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 32 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 33 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/* 34 */     this.field_146289_q.func_78276_b(this.wantLabel, 12, 23, 4210752);
/* 35 */     this.field_146289_q.func_78276_b(this.offerLabel, 12, 42, 4210752);
/* 36 */     this.field_146289_q.func_78276_b(this.container.tileEntity.fluidOffer + " mB", 50, 42, 4210752);
/* 37 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.personalTraderFluid.paidFor.name", new Object[] { Integer.valueOf(this.container.tileEntity.paidFor) }), 12, 60, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 43 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 44 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIFluidOMatClosed.png"));
/* 45 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 46 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 47 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\GuiFluidOMatClosed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */