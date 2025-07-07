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
/*    */ public class GuiPersonalChest
/*    */   extends GuiContainer
/*    */ {
/*    */   public ContainerPersonalChest container;
/*    */   public String name;
/*    */   public String inv;
/*    */   
/*    */   public GuiPersonalChest(ContainerPersonalChest container) {
/* 21 */     super((Container)container);
/* 22 */     this.container = container;
/* 23 */     this.name = StatCollector.func_74838_a("blockPersonalChest.name");
/* 24 */     this.inv = StatCollector.func_74838_a("container.inventory");
/* 25 */     this.field_147000_g = 222;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 30 */     this.field_146289_q.func_78276_b(this.name, 8, 6, 4210752);
/* 31 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 36 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 37 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIPersonalChest.png"));
/* 38 */     int xOffset = (this.field_146294_l - this.field_146999_f) / 2;
/* 39 */     int yOffset = (this.field_146295_m - this.field_147000_g) / 2;
/* 40 */     func_73729_b(xOffset, yOffset, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\GuiPersonalChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */