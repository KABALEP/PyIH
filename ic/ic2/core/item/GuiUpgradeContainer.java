/*    */ package ic2.core.item;
/*    */ 
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiUpgradeContainer
/*    */   extends GuiContainer
/*    */ {
/*    */   String name;
/*    */   String inv;
/*    */   
/*    */   public GuiUpgradeContainer(ContainerUpgradeContainer par1) {
/* 16 */     super((Container)par1);
/* 17 */     this.name = StatCollector.func_74838_a("item.itemUpgradeContainer.name");
/* 18 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 23 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 24 */     this.field_146289_q.func_78276_b(this.inv, 8, this.field_147000_g - 124, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 29 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 30 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIUpgradeContainer.png"));
/* 31 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 32 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 33 */     func_73729_b(j, k, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\GuiUpgradeContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */