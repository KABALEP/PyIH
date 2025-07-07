/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.GuiIconButton;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiElectricBlock
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerElectricBlock container;
/*    */   public String inv;
/*    */   
/*    */   public GuiElectricBlock(ContainerElectricBlock container) {
/* 27 */     super((Container)container);
/* 28 */     this.container = container;
/* 29 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 34 */     super.func_73866_w_();
/* 35 */     this.field_146292_n.add(new GuiIconButton(0, (this.field_146294_l - this.field_146999_f) / 2 + 152, (this.field_146295_m - this.field_147000_g) / 2 + 4, 20, 20, new ItemStack(Items.field_151137_ax), true));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 40 */     String name = this.container.tileEntity.getNameByTier();
/* 41 */     this.field_146289_q.func_78276_b(name, (this.field_146999_f - this.field_146289_q.func_78256_a(name)) / 2, 6, 4210752);
/* 42 */     this.field_146289_q.func_78276_b(this.inv, 30, this.field_147000_g - 96 + 2, 4210752);
/* 43 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.electricBlock.level.name"), 79, 25, 4210752);
/* 44 */     int e = this.container.tileEntity.energy;
/* 45 */     if (e > this.container.tileEntity.maxStorage)
/*    */     {
/* 47 */       e = this.container.tileEntity.maxStorage;
/*    */     }
/* 49 */     this.field_146289_q.func_78276_b(" " + e, 110, 35, 4210752);
/* 50 */     this.field_146289_q.func_78276_b("/" + this.container.tileEntity.maxStorage, 110, 45, 4210752);
/* 51 */     this.field_146289_q.func_78276_b(StatCollector.func_74837_a("container.electricBlock.output.name", new Object[] { Integer.valueOf(this.container.tileEntity.output) }), 85, 60, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 56 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 57 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIElectricBlock.png"));
/* 58 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 59 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 60 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 61 */     if (this.container.tileEntity.energy > 0) {
/*    */       
/* 63 */       int i1 = (int)(24.0F * this.container.tileEntity.getChargeLevel());
/* 64 */       func_73729_b(j + 79, k + 34, 176, 14, i1 + 1, 16);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146284_a(GuiButton guibutton) {
/* 70 */     if (guibutton.field_146127_k == 0)
/*    */     {
/* 72 */       ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.tileEntity, 0);
/*    */     }
/* 74 */     super.func_146284_a(guibutton);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\GuiElectricBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */