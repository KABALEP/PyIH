/*    */ package ic2.core.block.personal;
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
/*    */ public class GuiTradeOMatOpen
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerTradeOMatOpen container;
/*    */   public String name;
/*    */   public String totalTradesLabel0;
/*    */   public String totalTradesLabel1;
/*    */   public String inv;
/*    */   
/*    */   public GuiTradeOMatOpen(ContainerTradeOMatOpen container) {
/* 23 */     super((Container)container);
/* 24 */     this.container = container;
/* 25 */     this.name = StatCollector.func_74838_a("blockPersonalTrader.name");
/* 26 */     this.totalTradesLabel0 = StatCollector.func_74838_a("container.personalTrader.totalTrades0.name");
/* 27 */     this.totalTradesLabel1 = StatCollector.func_74838_a("container.personalTrader.totalTrades1.name");
/* 28 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 33 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 34 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/* 35 */     this.field_146289_q.func_78276_b(this.totalTradesLabel0, 112, 20, 4210752);
/* 36 */     this.field_146289_q.func_78276_b(this.totalTradesLabel1, 112, 28, 4210752);
/* 37 */     this.field_146289_q.func_78276_b("" + this.container.tileEntity.totalTradeCount, 112, 36, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 42 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 43 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUITradeOMatOpen.png"));
/* 44 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 45 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 46 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\GuiTradeOMatOpen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */