/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import net.minecraft.client.gui.inventory.GuiContainer;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.StatCollector;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiNuclearJetpack
/*    */   extends GuiContainer
/*    */ {
/*    */   String name;
/*    */   
/*    */   public GuiNuclearJetpack(ContainerNuclearJetpack par1Container) {
/* 16 */     super((Container)par1Container);
/* 17 */     this.field_147000_g = 222;
/* 18 */     this.name = StatCollector.func_74838_a("container.nuclearJetpack.name");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146979_b(int par1, int par2) {
/* 24 */     this.field_146289_q.func_78276_b(this.name, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(this.name) / 2, 6, 4210752);
/* 25 */     this.field_146289_q.func_78276_b(I18n.func_135052_a("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float par1, int par2, int par3) {
/* 30 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 31 */     this.field_146297_k.func_110434_K().func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUI5by5.png"));
/* 32 */     int k = (this.field_146294_l - this.field_146999_f) / 2;
/* 33 */     int l = (this.field_146295_m - this.field_147000_g) / 2;
/* 34 */     func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\armor\GuiNuclearJetpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */