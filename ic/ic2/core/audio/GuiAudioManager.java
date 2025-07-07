/*     */ package ic2.core.audio;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.GuiSliderButton;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiAudioManager
/*     */   extends GuiScreen
/*     */ {
/*     */   GuiScreen before;
/*     */   GuiSliderButton sliderMaster;
/*     */   GuiSliderButton sliderBlock;
/*     */   GuiSliderButton sliderItem;
/*     */   GuiSliderButton sliderBack;
/*     */   float lastMaster;
/*     */   float lastBlock;
/*     */   float lastItem;
/*     */   float lastBack;
/*     */   
/*     */   public GuiAudioManager(GuiScreen screen) {
/*  25 */     this.before = screen;
/*  26 */     this.lastMaster = IC2.audioManager.getMasterVolume();
/*  27 */     this.lastBlock = IC2.audioManager.getVolumeForType(PositionSpec.Center);
/*  28 */     this.lastItem = IC2.audioManager.getVolumeForType(PositionSpec.Hand);
/*  29 */     this.lastBack = IC2.audioManager.getVolumeForType(PositionSpec.Backpack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  35 */     super.func_73866_w_();
/*  36 */     this.field_146292_n.clear();
/*  37 */     int k = this.field_146294_l / 2;
/*  38 */     int l = this.field_146295_m / 2;
/*  39 */     this.sliderMaster = (new GuiSliderButton(0, k - 70, l - 70, "IC2 Master Volume", IC2.audioManager.getMasterVolume())).setWeelEffect(0.01F);
/*  40 */     this.field_146292_n.add(this.sliderMaster);
/*  41 */     this.sliderBlock = (new GuiSliderButton(1, k - 170, l - 40, "IC2 Tile Volume", IC2.audioManager.getVolumeForType(PositionSpec.Center))).setWeelEffect(0.01F);
/*  42 */     this.field_146292_n.add(this.sliderBlock);
/*  43 */     this.sliderItem = (new GuiSliderButton(2, k + 20, l - 40, "IC2 Items Volume", IC2.audioManager.getVolumeForType(PositionSpec.Hand))).setWeelEffect(0.01F);
/*  44 */     this.field_146292_n.add(this.sliderItem);
/*  45 */     this.sliderBack = (new GuiSliderButton(3, k - 80, l - 10, "IC2 Backpack Volume", IC2.audioManager.getVolumeForType(PositionSpec.Backpack))).setWeelEffect(0.01F);
/*  46 */     this.field_146292_n.add(this.sliderBack);
/*     */     
/*  48 */     this.field_146292_n.add(new GuiButton(4, k - 50, l + 20, 100, 20, "Back"));
/*  49 */     this.field_146292_n.add(new GuiButton(5, k - 75, l + 60, 150, 20, "Remove all Active Sounds"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
/*  55 */     func_146276_q_();
/*  56 */     func_73732_a(this.field_146289_q, "IC2 Sound Options", this.field_146294_l / 2, 15, 16777215);
/*  57 */     super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
/*  58 */     float f = this.sliderMaster.sliderValue;
/*  59 */     if (this.lastMaster != f)
/*     */     {
/*  61 */       ((AudioManagerClient)IC2.audioManager).updateSound(f);
/*     */     }
/*  63 */     this.sliderMaster.func_146111_b(p_73863_1_, p_73863_2_);
/*  64 */     this.lastMaster = f;
/*     */     
/*  66 */     f = this.sliderBlock.sliderValue;
/*  67 */     if (this.lastBlock != f)
/*     */     {
/*  69 */       ((AudioManagerClient)IC2.audioManager).updateSoundType(f, PositionSpec.Center);
/*     */     }
/*  71 */     this.sliderBlock.func_146111_b(p_73863_1_, p_73863_2_);
/*  72 */     this.lastBlock = f;
/*     */     
/*  74 */     f = this.sliderItem.sliderValue;
/*  75 */     if (this.lastItem != f)
/*     */     {
/*  77 */       ((AudioManagerClient)IC2.audioManager).updateSoundType(f, PositionSpec.Hand);
/*     */     }
/*  79 */     this.sliderItem.func_146111_b(p_73863_1_, p_73863_2_);
/*  80 */     this.lastItem = f;
/*     */     
/*  82 */     f = this.sliderBack.sliderValue;
/*  83 */     if (this.lastBack != f)
/*     */     {
/*  85 */       ((AudioManagerClient)IC2.audioManager).updateSoundType(f, PositionSpec.Backpack);
/*     */     }
/*  87 */     this.sliderBack.func_146111_b(p_73863_1_, p_73863_2_);
/*  88 */     this.lastBack = f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton par1) {
/*  94 */     int id = par1.field_146127_k;
/*  95 */     if (id == 4) {
/*     */       
/*  97 */       this.field_146297_k.func_147108_a(this.before);
/*     */     }
/*  99 */     else if (id == 5) {
/*     */       
/* 101 */       ((AudioManagerClient)IC2.audioManager).markForRemoving = true;
/* 102 */       par1.field_146126_j = "In Progress";
/* 103 */       par1.field_146124_l = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\audio\GuiAudioManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */