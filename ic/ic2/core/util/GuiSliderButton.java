/*     */ package ic2.core.util;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiSliderButton
/*     */   extends GuiButton
/*     */ {
/*  13 */   public float sliderValue = 1.0F;
/*     */   
/*  15 */   private float weelEffect = 0.0F;
/*     */   
/*     */   public boolean dragging;
/*     */   
/*     */   public String originalName;
/*     */   
/*     */   public int ticker;
/*     */ 
/*     */   
/*     */   public GuiSliderButton(int par1, int par2, int par3, String par4, float par5) {
/*  25 */     super(par1, par2, par3, 150, 20, par4);
/*  26 */     this.sliderValue = par5;
/*  27 */     this.originalName = par4;
/*  28 */     updateName();
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiSliderButton setWeelEffect(float par1) {
/*  33 */     this.weelEffect = par1;
/*  34 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_146114_a(boolean par1) {
/*  39 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146119_b(Minecraft par1Minecraft, int par2, int par3) {
/*  44 */     if (this.field_146125_m) {
/*     */       
/*  46 */       if (this.dragging) {
/*     */         
/*  48 */         this.sliderValue = (par2 - this.field_146128_h + 4) / (this.field_146120_f - 8);
/*     */         
/*  50 */         if (this.sliderValue < 0.0F)
/*     */         {
/*  52 */           this.sliderValue = 0.0F;
/*     */         }
/*     */         
/*  55 */         if (this.sliderValue > 1.0F)
/*     */         {
/*  57 */           this.sliderValue = 1.0F;
/*     */         }
/*  59 */         updateName();
/*     */       } 
/*     */       
/*  62 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  63 */       func_73729_b(this.field_146128_h + (int)(this.sliderValue * (this.field_146120_f - 8)), this.field_146129_i, 0, 66, 4, 20);
/*  64 */       func_73729_b(this.field_146128_h + (int)(this.sliderValue * (this.field_146120_f - 8)) + 4, this.field_146129_i, 196, 66, 4, 20);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateName() {
/*  70 */     int newNumber = (int)(this.sliderValue * 100.0F);
/*  71 */     if (newNumber <= 0.0F) {
/*     */       
/*  73 */       this.field_146126_j = this.originalName + ": OFF";
/*     */     }
/*     */     else {
/*     */       
/*  77 */       this.field_146126_j = this.originalName + ": " + newNumber + "%";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_146116_c(Minecraft par1Minecraft, int par2, int par3) {
/*  83 */     if (super.func_146116_c(par1Minecraft, par2, par3)) {
/*     */       
/*  85 */       this.sliderValue = (par2 - this.field_146128_h + 4) / (this.field_146120_f - 8);
/*     */       
/*  87 */       if (this.sliderValue < 0.0F)
/*     */       {
/*  89 */         this.sliderValue = 0.0F;
/*     */       }
/*     */       
/*  92 */       if (this.sliderValue > 1.0F)
/*     */       {
/*  94 */         this.sliderValue = 1.0F;
/*     */       }
/*  96 */       updateName();
/*  97 */       this.dragging = true;
/*  98 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146111_b(int par1, int par2) {
/* 111 */     if (par1 < this.field_146128_h || par1 > this.field_146128_h + this.field_146120_f) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 116 */     if (par2 < this.field_146129_i || par2 > this.field_146129_i + this.field_146121_g) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 121 */     int weel = Mouse.getDWheel();
/* 122 */     if (weel != 0 && this.weelEffect > 0.0F) {
/*     */       
/* 124 */       this.ticker = 0;
/* 125 */       weel /= 120;
/* 126 */       this.sliderValue += weel * this.weelEffect;
/* 127 */       if (this.sliderValue < 0.0F)
/*     */       {
/* 129 */         this.sliderValue = 0.0F;
/*     */       }
/*     */       
/* 132 */       if (this.sliderValue > 1.0F)
/*     */       {
/* 134 */         this.sliderValue = 1.0F;
/*     */       }
/* 136 */       updateName();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146118_a(int par1, int par2) {
/* 142 */     this.dragging = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\GuiSliderButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */