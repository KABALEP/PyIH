/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class GuiCreativeStorage
/*     */   extends GuiContainer
/*     */ {
/*     */   public ContainerCreativeStorage container;
/*     */   public String name;
/*     */   public String inv;
/*     */   public GuiTextField packet;
/*     */   public GuiTextField providing;
/*     */   
/*     */   public GuiCreativeStorage(ContainerCreativeStorage container) {
/*  26 */     super((Container)container);
/*  27 */     this.container = container;
/*  28 */     this.inv = StatCollector.func_74838_a("container.inventory");
/*  29 */     this.name = StatCollector.func_74838_a("blockCreativeStorage.name");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/*  34 */     super.func_73866_w_();
/*     */     
/*  36 */     this.providing = new GuiTextField(this.field_146289_q, this.field_147003_i + 5, this.field_147009_r + 60, 100, 15);
/*  37 */     this.providing.func_146180_a("" + this.container.creative.offer);
/*  38 */     this.providing.func_146205_d(true);
/*  39 */     this.providing.func_146184_c(true);
/*     */ 
/*     */     
/*  42 */     this.packet = new GuiTextField(this.field_146289_q, this.field_147003_i + 5, this.field_147009_r + 30, 40, 15);
/*  43 */     this.packet.func_146180_a("" + this.container.creative.packets);
/*  44 */     this.packet.func_146205_d(true);
/*  45 */     this.packet.func_146184_c(true);
/*  46 */     this.field_146292_n.add(new GuiButton(0, this.field_147003_i + 110, this.field_147009_r + 30, 50, 20, "Confirm"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton p_146284_1_) {
/*     */     int newEnergy, packets;
/*  52 */     if (p_146284_1_.field_146127_k != 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  60 */       newEnergy = Integer.parseInt(this.providing.func_146179_b());
/*     */     }
/*  62 */     catch (Exception e) {
/*     */       
/*  64 */       newEnergy = 0;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  69 */       packets = Integer.parseInt(this.packet.func_146179_b());
/*     */     }
/*  71 */     catch (Exception e) {
/*     */       
/*  73 */       packets = 1;
/*     */     } 
/*  75 */     if (packets <= 0)
/*     */     {
/*  77 */       packets = 1;
/*     */     }
/*  79 */     if (packets > 100)
/*     */     {
/*  81 */       packets = 100;
/*     */     }
/*  83 */     if (newEnergy <= 0)
/*     */     {
/*  85 */       newEnergy = 32;
/*     */     }
/*  87 */     if (newEnergy > EnergyNet.instance.getPowerFromTier(13))
/*     */     {
/*  89 */       newEnergy = (int)EnergyNet.instance.getPowerFromTier(13);
/*     */     }
/*     */     
/*  92 */     this.providing.func_146180_a("" + newEnergy);
/*  93 */     this.packet.func_146180_a("" + packets);
/*     */     
/*  95 */     ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.creative, newEnergy);
/*  96 */     ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.container.creative, packets);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char p_73869_1_, int p_73869_2_) {
/* 102 */     if (Character.isDigit(p_73869_1_) || p_73869_2_ == 14) {
/*     */       
/* 104 */       if (this.providing.func_146206_l()) {
/*     */         
/* 106 */         this.providing.func_146201_a(p_73869_1_, p_73869_2_);
/*     */         return;
/*     */       } 
/* 109 */       if (this.packet.func_146206_l()) {
/*     */         
/* 111 */         this.packet.func_146201_a(p_73869_1_, p_73869_2_);
/*     */         return;
/*     */       } 
/*     */     } 
/* 115 */     super.func_73869_a(p_73869_1_, p_73869_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
/* 121 */     this.providing.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
/* 122 */     this.packet.func_146192_a(p_73864_1_, p_73864_2_, p_73864_3_);
/*     */     
/* 124 */     super.func_73864_a(p_73864_1_, p_73864_2_, p_73864_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/* 129 */     this.field_146289_q.func_78276_b(this.name, (this.field_146999_f - this.field_146289_q.func_78256_a(this.name)) / 2, 6, 4210752);
/* 130 */     this.field_146289_q.func_78276_b("EU", 110, 65, 4210752);
/* 131 */     this.field_146289_q.func_78276_b("Packets", 50, 35, 4210752);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/* 136 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 137 */     this.field_146297_k.field_71446_o.func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIAdjustableTransformer.png"));
/* 138 */     int j = (this.field_146294_l - this.field_146999_f) / 2;
/* 139 */     int k = (this.field_146295_m - this.field_147000_g) / 2;
/* 140 */     func_73729_b(j, k, 0, 0, this.field_146999_f, 100);
/* 141 */     func_73729_b(j, k + 50, 0, 10, this.field_146999_f, 50);
/* 142 */     func_73729_b(j, k + 100, 0, 140, this.field_146999_f, 3);
/* 143 */     this.providing.func_146194_f();
/* 144 */     this.packet.func_146194_f();
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\GuiCreativeStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */