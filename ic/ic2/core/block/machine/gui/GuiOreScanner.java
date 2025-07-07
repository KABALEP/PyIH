/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.machine.container.ContainerOreScanner;
/*    */ import ic2.core.block.machine.tileentity.TileEntityOreScanner;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiOreScanner
/*    */   extends GuiContainer {
/*    */   String name;
/*    */   
/*    */   public GuiOreScanner(ContainerOreScanner par1) {
/* 20 */     super((Container)par1);
/* 21 */     this.scanner = par1.scanner;
/* 22 */     this.name = StatCollector.func_74838_a("blockOreScanner.name");
/* 23 */     this.inv = StatCollector.func_74838_a("container.inventory");
/* 24 */     this.field_147000_g = 222;
/*    */   }
/*    */   String inv;
/*    */   TileEntityOreScanner scanner;
/*    */   
/*    */   public void func_73866_w_() {
/* 30 */     super.func_73866_w_();
/* 31 */     this.field_146292_n.clear();
/* 32 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 33 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 34 */     this.field_146292_n.add(new GuiButton(0, j + 145, k + 110, 20, 10, "-"));
/* 35 */     this.field_146292_n.add(new GuiButton(1, j + 145, k + 35, 20, 10, "+"));
/* 36 */     this.field_146292_n.add(new GuiButton(2, j + 132, k + 70, 35, 10, StatCollector.func_74838_a("container.reset.name")));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 41 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 42 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 91, 4210752);
/* 43 */     if (!this.scanner.finished) {
/*    */       
/* 45 */       int amount = (int)(this.scanner.currentBlocks / this.scanner.maxBlocks * 100.0D);
/* 46 */       this.field_146289_q.func_78276_b(amount + "%", 75, 75, 4210752);
/*    */       return;
/*    */     } 
/* 49 */     int[][] array = { { 30, 37 }, { 30, 54 }, { 30, 71 }, { 30, 89 }, { 30, 107 } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 56 */     for (int i = 0; i < 5; i++) {
/*    */       
/* 58 */       int amount = this.scanner.inv.getSize(this.scanner.inv.func_70301_a(i));
/* 59 */       if (amount > 0)
/*    */       {
/* 61 */         this.field_146289_q.func_78276_b(amount + "x", array[i][0], array[i][1], 4210752);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 68 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 69 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIOreScanner.png"));
/* 70 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 71 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 72 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 73 */     if (this.scanner.energy > 0) {
/*    */       
/* 75 */       int i1 = (int)(this.scanner.energy / this.scanner.maxEnergy * 24.0D);
/* 76 */       func_73729_b(j + 135, k + 9 + 14 - i1, 176, 14 - i1, 14, i1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146284_a(GuiButton button) {
/* 83 */     int id = button.field_146127_k;
/* 84 */     if (id == 0) {
/*    */       
/* 86 */       ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.scanner, -1);
/*    */     }
/* 88 */     else if (id == 1) {
/*    */       
/* 90 */       ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.scanner, 1);
/*    */     }
/* 92 */     else if (id == 2) {
/*    */       
/* 94 */       ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.scanner, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiOreScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */