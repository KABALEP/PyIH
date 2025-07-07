/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiFluidOMatOpen
/*    */   extends GuiContainer {
/*    */   public ContainerFluidOMatOpen container;
/*    */   public String name;
/*    */   public String offerLabel;
/*    */   public String inv;
/*    */   
/*    */   public GuiFluidOMatOpen(ContainerFluidOMatOpen par1) {
/* 24 */     super((Container)par1);
/* 25 */     this.container = par1;
/* 26 */     this.name = StatCollector.func_74838_a("blockPersonalTraderFluid.name");
/* 27 */     this.offerLabel = StatCollector.func_74838_a("container.personalTrader.offer.name");
/* 28 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 34 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 35 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/* 36 */     this.field_146289_q.func_78276_b(this.offerLabel, 112, 20, 4210752);
/* 37 */     this.field_146289_q.func_78276_b(this.container.tile.fluidOffer + " mB", 112, 28, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 43 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 44 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIFluidOMatOpen.png"));
/* 45 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 46 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 47 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 52 */     super.func_73866_w_();
/* 53 */     this.field_146292_n.add(new GuiButton(0, this.field_147003_i + 84, this.field_147009_r + 43, 28, 12, "-1000"));
/* 54 */     this.field_146292_n.add(new GuiButton(1, this.field_147003_i + 112, this.field_147009_r + 43, 28, 12, "-100"));
/* 55 */     this.field_146292_n.add(new GuiButton(2, this.field_147003_i + 84, this.field_147009_r + 55, 28, 12, "+1000"));
/* 56 */     this.field_146292_n.add(new GuiButton(3, this.field_147003_i + 112, this.field_147009_r + 55, 28, 12, "+100"));
/* 57 */     this.field_146292_n.add(new GuiButton(4, this.field_147003_i + 140, this.field_147009_r + 43, 28, 12, "-10"));
/* 58 */     this.field_146292_n.add(new GuiButton(5, this.field_147003_i + 140, this.field_147009_r + 55, 28, 12, "+10"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146284_a(GuiButton guibutton) {
/* 64 */     ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.tile, guibutton.field_146127_k);
/* 65 */     super.func_146284_a(guibutton);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\GuiFluidOMatOpen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */