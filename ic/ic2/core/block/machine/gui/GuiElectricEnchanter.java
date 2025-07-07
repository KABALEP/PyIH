/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.machine.container.ContainerElectricEnchanter;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiElectricEnchanter
/*    */   extends GuiContainer {
/*    */   public ContainerElectricEnchanter container;
/*    */   public String name;
/*    */   public String inv;
/*    */   public GuiButton expButton;
/*    */   
/*    */   public GuiElectricEnchanter(ContainerElectricEnchanter container) {
/* 23 */     super((Container)container);
/* 24 */     this.container = container;
/* 25 */     this.name = StatCollector.func_74838_a("blockElectricEnchanter.name");
/* 26 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_73866_w_() {
/* 32 */     super.func_73866_w_();
/* 33 */     this.expButton = new GuiButton(0, (this.field_146294_l - this.field_146999_f) / 2 + 60, (this.field_146295_m - this.field_147000_g) / 2 + 70, 45, 10, StatCollector.func_74838_a("container.payExp.name"));
/* 34 */     this.expButton.field_146124_l = (this.container.tile.exp < this.container.tile.neededExp);
/* 35 */     this.field_146292_n.add(this.expButton);
/* 36 */     GuiButton button = new GuiButton(1, (this.field_146294_l - this.field_146999_f) / 2 + 110, (this.field_146295_m - this.field_147000_g) / 2 + 70, 30, 10, StatCollector.func_74838_a("container.enchanterStart.name"));
/* 37 */     button.field_146124_l = (!this.container.tile.started && this.container.tile.exp >= this.container.tile.neededExp && this.container.tile.neededExp > 0);
/* 38 */     this.field_146292_n.add(button);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 44 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 4, 4210752);
/* 45 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/* 46 */     this.expButton.field_146124_l = (this.container.tile.exp < this.container.tile.neededExp);
/* 47 */     ((GuiButton)this.field_146292_n.get(1)).field_146124_l = (!this.container.tile.started && this.container.tile.exp >= this.container.tile.neededExp && this.container.tile.neededExp > 0);
/* 48 */     this.field_146289_q.func_78276_b(EnumChatFormatting.DARK_GREEN + "" + this.container.tile.exp + " / " + this.container.tile.neededExp, 60, 58, 4210752);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 54 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 55 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIElecEnchanter.png"));
/* 56 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 57 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 58 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/* 59 */     int l = (int)(this.container.tile.getChargeLevel() * 14.0F);
/* 60 */     if (l > 0)
/*    */     {
/* 62 */       func_73729_b(j + 10, k + 30 + 14 - l, 176, 14 - l, 14, l);
/*    */     }
/* 64 */     int i1 = (int)(this.container.tile.getProgress() * 24.0F);
/* 65 */     if (i1 > 0)
/*    */     {
/* 67 */       func_73729_b(j + 90, k + 36, 176, 14, i1 + 1, 16);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146284_a(GuiButton button) {
/* 74 */     int id = button.field_146127_k;
/* 75 */     if (id == 0) {
/*    */       
/* 77 */       ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.tile, 0);
/*    */     }
/* 79 */     else if (id == 1) {
/*    */       
/* 81 */       ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.tile, 1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\gui\GuiElectricEnchanter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */