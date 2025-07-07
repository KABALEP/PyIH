/*    */ package ic2.core.audio;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.PlatformClient;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptions;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiScreenOptionsSounds;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiChoseAudioManager
/*    */   extends GuiScreen
/*    */ {
/*    */   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
/* 20 */     func_146276_q_();
/* 21 */     this.field_146292_n.clear();
/* 22 */     int k = this.field_146294_l / 2;
/* 23 */     int l = this.field_146295_m / 2;
/* 24 */     this.field_146292_n.add(new GuiButton(0, k - 160, l - 70, 150, 20, "Normal Sound Options"));
/* 25 */     this.field_146292_n.add(new GuiButton(1, k + 10, l - 70, 150, 20, "IC2 Sound Options"));
/* 26 */     this.field_146292_n.add(new GuiButton(2, k - 50, l + 20, 100, 20, "Back"));
/* 27 */     func_73732_a(this.field_146289_q, "Choose Sound Options", this.field_146294_l / 2, 15, 16777215);
/* 28 */     super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_146284_a(GuiButton par1) {
/* 34 */     int id = par1.field_146127_k;
/* 35 */     if (id == 0) {
/*    */       
/* 37 */       this.field_146297_k.func_147108_a((GuiScreen)new GuiScreenOptionsSounds(this, this.field_146297_k.field_71474_y));
/*    */     }
/* 39 */     else if (id == 1) {
/*    */       
/* 41 */       this.field_146297_k.func_147108_a(new GuiAudioManager(this));
/*    */     }
/* 43 */     else if (id == 2) {
/*    */       
/* 45 */       this.field_146297_k.func_147108_a((GuiScreen)new GuiOptions(null, this.field_146297_k.field_71474_y));
/* 46 */       (PlatformClient)IC2.platform; PlatformClient.open = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\audio\GuiChoseAudioManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */