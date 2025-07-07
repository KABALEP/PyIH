/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiAdjustableTransformer extends GuiContainer {
/*    */   ContainerAdjustableTransformer container;
/*    */   String inv;
/*    */   String name;
/*    */   
/*    */   public GuiAdjustableTransformer(ContainerAdjustableTransformer par1) {
/* 19 */     super((Container)par1);
/* 20 */     this.container = par1;
/* 21 */     this.name = StatCollector.func_74838_a("blockAdjustableTranformer.name");
/* 22 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 28 */     super.func_73866_w_();
/* 29 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 30 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 31 */     this.field_146292_n.clear();
/* 32 */     this.field_146292_n.add(new GuiButton(0, j + 5, k + 18, 15, 12, "+1"));
/* 33 */     this.field_146292_n.add(new GuiButton(1, j + 22, k + 18, 20, 12, "+10"));
/* 34 */     this.field_146292_n.add(new GuiButton(2, j + 135, k + 18, 15, 12, "-1"));
/* 35 */     this.field_146292_n.add(new GuiButton(3, j + 150, k + 18, 20, 12, "-10"));
/* 36 */     this.field_146292_n.add(new GuiButton(4, j + 5, k + 32, 15, 12, "+1"));
/* 37 */     this.field_146292_n.add(new GuiButton(5, j + 22, k + 32, 20, 12, "+10"));
/* 38 */     this.field_146292_n.add(new GuiButton(6, j + 5, k + 46, 30, 12, "+100"));
/* 39 */     this.field_146292_n.add(new GuiButton(7, j + 40, k + 46, 35, 12, "+1000"));
/* 40 */     this.field_146292_n.add(new GuiButton(8, j + 135, k + 32, 15, 12, "-1"));
/* 41 */     this.field_146292_n.add(new GuiButton(9, j + 150, k + 32, 20, 12, "-10"));
/* 42 */     this.field_146292_n.add(new GuiButton(10, j + 100, k + 46, 30, 12, "-100"));
/* 43 */     this.field_146292_n.add(new GuiButton(11, j + 135, k + 46, 35, 12, "-1000"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 48 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/*    */     
/* 50 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.adjustableTransformerEUPerPacket.name", new Object[] { Integer.valueOf(this.container.tile.energyPacket) }), 45, 30, 4210752);
/* 51 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.adjustableTransformerPacketsPerTick.name", new Object[] { Integer.valueOf(this.container.tile.packetCount) }), 45, 20, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 56 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 57 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIAdjustableTransformer.png"));
/* 58 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 59 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 60 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146284_a(GuiButton par1) {
/* 66 */     ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.tile, par1.field_146127_k);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\GuiAdjustableTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */