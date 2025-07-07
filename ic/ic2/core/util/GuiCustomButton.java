/*     */ package ic2.core.util;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCustomButton
/*     */   extends GuiButton
/*     */   implements IDrawButton
/*     */ {
/*  21 */   private List<String> hoverText = new ArrayList<>();
/*     */   
/*     */   public GuiCustomButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/*  24 */     super(buttonId, x, y, widthIn, heightIn, buttonText);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiCustomButton addHoverText(String text) {
/*  29 */     if (!Strings.isNullOrEmpty(text))
/*     */     {
/*  31 */       this.hoverText.add(text);
/*     */     }
/*  33 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiCustomButton clearText() {
/*  38 */     this.hoverText.clear();
/*  39 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPostDraw(Minecraft mc, int mouseX, int mouseY) {
/*  45 */     if (this.field_146123_n)
/*     */     {
/*  47 */       if (!this.hoverText.isEmpty()) {
/*     */         
/*  49 */         drawHoveringText(this.hoverText, mouseX, mouseY, mc.field_71466_p);
/*  50 */         GL11.glDisable(32826);
/*  51 */         RenderHelper.func_74518_a();
/*  52 */         GL11.glDisable(2896);
/*  53 */         GL11.glDisable(2929);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawHoveringText(List<String> p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font) {
/*  60 */     if (!p_146283_1_.isEmpty()) {
/*     */       
/*  62 */       GL11.glDisable(32826);
/*  63 */       RenderHelper.func_74518_a();
/*  64 */       GL11.glDisable(2896);
/*  65 */       GL11.glDisable(2929);
/*  66 */       int k = 0;
/*  67 */       Iterator<String> iterator = p_146283_1_.iterator();
/*     */       
/*  69 */       while (iterator.hasNext()) {
/*     */         
/*  71 */         String s = iterator.next();
/*  72 */         int l = font.func_78256_a(s);
/*     */         
/*  74 */         if (l > k)
/*     */         {
/*  76 */           k = l;
/*     */         }
/*     */       } 
/*     */       
/*  80 */       int j2 = p_146283_2_ + 12;
/*  81 */       int k2 = p_146283_3_ - 12;
/*  82 */       int i1 = 8;
/*     */       
/*  84 */       if (p_146283_1_.size() > 1)
/*     */       {
/*  86 */         i1 += 2 + (p_146283_1_.size() - 1) * 10;
/*     */       }
/*     */       
/*  89 */       if (j2 + k > this.field_146120_f)
/*     */       {
/*  91 */         j2 -= 28 + k;
/*     */       }
/*     */       
/*  94 */       if (k2 + i1 + 6 > this.field_146121_g)
/*     */       {
/*  96 */         k2 = this.field_146121_g - i1 - 6;
/*     */       }
/*     */       
/*  99 */       this.field_73735_i = 300.0F;
/* 100 */       int j1 = -267386864;
/* 101 */       func_73733_a(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
/* 102 */       func_73733_a(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
/* 103 */       func_73733_a(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
/* 104 */       func_73733_a(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
/* 105 */       func_73733_a(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
/* 106 */       int k1 = 1347420415;
/* 107 */       int l1 = (k1 & 0xFEFEFE) >> 1 | k1 & 0xFF000000;
/* 108 */       func_73733_a(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
/* 109 */       func_73733_a(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
/* 110 */       func_73733_a(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
/* 111 */       func_73733_a(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);
/*     */       
/* 113 */       for (int i2 = 0; i2 < p_146283_1_.size(); i2++) {
/*     */         
/* 115 */         String s1 = p_146283_1_.get(i2);
/* 116 */         font.func_78261_a(s1, j2, k2, -1);
/*     */         
/* 118 */         if (i2 == 0)
/*     */         {
/* 120 */           k2 += 2;
/*     */         }
/*     */         
/* 123 */         k2 += 10;
/*     */       } 
/*     */       
/* 126 */       this.field_73735_i = 0.0F;
/* 127 */       GL11.glEnable(2896);
/* 128 */       GL11.glEnable(2929);
/* 129 */       RenderHelper.func_74519_b();
/* 130 */       GL11.glEnable(32826);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\cor\\util\GuiCustomButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */