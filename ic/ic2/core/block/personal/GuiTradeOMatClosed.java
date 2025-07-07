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
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiTradeOMatClosed
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerTradeOMatClosed container;
/*    */   public String name;
/*    */   public String wantLabel;
/*    */   public String offerLabel;
/*    */   public String stockLabel;
/*    */   public String inv;
/*    */   
/*    */   public GuiTradeOMatClosed(ContainerTradeOMatClosed container) {
/* 25 */     super((Container)container);
/* 26 */     this.container = container;
/* 27 */     this.name = StatCollector.func_74838_a("blockPersonalTrader.name");
/* 28 */     this.wantLabel = StatCollector.func_74838_a("container.personalTrader.want.name");
/* 29 */     this.offerLabel = StatCollector.func_74838_a("container.personalTrader.offer.name");
/* 30 */     this.stockLabel = StatCollector.func_74838_a("container.personalTrader.stock.name");
/* 31 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 36 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 37 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/* 38 */     this.field_146289_q.func_78276_b(this.wantLabel, 12, 23, 4210752);
/* 39 */     this.field_146289_q.func_78276_b(this.offerLabel, 12, 42, 4210752);
/* 40 */     this.field_146289_q.func_78276_b(this.stockLabel, 12, 60, 4210752);
/* 41 */     this.field_146289_q.func_78276_b("" + this.container.tileEntity.stock, 50, 60, (this.container.tileEntity.stock > 0) ? 4210752 : 16733525);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 46 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 47 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUITradeOMatClosed.png"));
/* 48 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 49 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 50 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\GuiTradeOMatClosed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */