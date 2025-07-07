/*    */ package ic2.core;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiIC2ErrorScreen
/*    */   extends GuiScreen
/*    */ {
/*    */   private String error;
/*    */   
/*    */   public GuiIC2ErrorScreen(String error) {
/* 15 */     this.error = error + "\n\nThe game will exit in 30 seconds.";
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_73863_a(int par1, int par2, float par3) {
/* 20 */     func_146278_c(0);
/* 21 */     func_73732_a(this.field_146289_q, "IndustrialCraft 2 Error", this.field_146294_l / 2, this.field_146295_m / 4 - 60 + 20, 16777215);
/* 22 */     int add = 0;
/*    */     
/* 24 */     String[] arr$ = this.error.split("\n"), split = arr$;
/* 25 */     for (String s : arr$) {
/*    */       
/* 27 */       func_73731_b(this.field_146289_q, s, this.field_146294_l / 2 - 180, this.field_146295_m / 4 - 60 + 60 - 10 + add, 10526880);
/* 28 */       add += 10;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\GuiIC2ErrorScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */