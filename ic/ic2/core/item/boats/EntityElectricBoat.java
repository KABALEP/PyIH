/*     */ package ic2.core.item.boats;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.AnimalChest;
/*     */ import net.minecraft.inventory.IInvBasic;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryBasic;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityElectricBoat extends EntityClassicBoat implements IInvBasic, IHasGui {
/*     */   public AnimalChest chest;
/*  23 */   private float speedCheck = 0.0F;
/*  24 */   public static double MaxUsage = 2.5D;
/*     */   
/*     */   public boolean guiOpen = false;
/*     */   
/*     */   public EntityElectricBoat(World world, double x, double y, double z) {
/*  29 */     super(world, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityElectricBoat(World world) {
/*  34 */     super(world);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {
/*  40 */     super.func_70088_a();
/*  41 */     this.field_70180_af.func_75682_a(20, new Float(0.0F));
/*  42 */     this.field_70180_af.func_75682_a(21, new Float(0.0F));
/*  43 */     this.chest = new AnimalChest("BatterySlot", 1);
/*  44 */     this.chest.func_110134_a(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/*  50 */     return Ic2Items.electricBoat.func_77946_l();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getTexture() {
/*  56 */     return createBase("boatElectric");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRammingBreaking(double speed) {
/*  62 */     func_70099_a(getItem(), 0.0F);
/*  63 */     if (this.chest.func_70301_a(0) != null) {
/*     */       
/*  65 */       func_70099_a(this.chest.func_70301_a(0), 0.0F);
/*  66 */       this.chest.func_70299_a(0, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerBreaking() {
/*  73 */     super.onPlayerBreaking();
/*  74 */     if (this.chest.func_70301_a(0) != null) {
/*     */       
/*  76 */       func_70099_a(this.chest.func_70301_a(0), 0.0F);
/*  77 */       this.chest.func_70299_a(0, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFallingBreaking(float fallingDistance) {
/*  84 */     func_70099_a(getItem(), 0.0F);
/*  85 */     if (this.chest.func_70301_a(0) != null) {
/*     */       
/*  87 */       func_70099_a(this.chest.func_70301_a(0), 0.0F);
/*  88 */       this.chest.func_70299_a(0, null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbt) {
/*  96 */     if (nbt.func_74764_b("Inv")) {
/*     */       
/*  98 */       this.chest.func_70299_a(0, ItemStack.func_77949_a(nbt.func_74775_l("Inv")));
/*     */     }
/*     */     else {
/*     */       
/* 102 */       this.chest.func_70299_a(0, null);
/*     */     } 
/* 104 */     this.field_70180_af.func_75692_b(20, Float.valueOf(nbt.func_74760_g("Speed")));
/* 105 */     this.field_70180_af.func_75692_b(21, Float.valueOf(getCharge(this.chest.func_70301_a(0))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbt) {
/* 111 */     if (this.chest.func_70301_a(0) != null) {
/*     */       
/* 113 */       NBTTagCompound data = new NBTTagCompound();
/* 114 */       this.chest.func_70301_a(0).func_77955_b(data);
/* 115 */       nbt.func_74782_a("Inv", (NBTBase)data);
/*     */     } 
/* 117 */     nbt.func_74776_a("Speed", this.speedCheck = this.field_70180_af.func_111145_d(20));
/*     */   }
/*     */ 
/*     */   
/*     */   private float getCharge(ItemStack stack) {
/* 122 */     if (stack == null || !(stack.func_77973_b() instanceof IElectricItem))
/*     */     {
/* 124 */       return 0.0F;
/*     */     }
/* 126 */     IElectricItem item = (IElectricItem)stack.func_77973_b();
/* 127 */     return (float)(ElectricItem.manager.getCharge(stack) / item.getMaxCharge(stack));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/* 133 */     if (IC2.platform.isSimulating())
/*     */     {
/* 135 */       if (this.chest.func_70301_a(0) == null) {
/*     */         
/* 137 */         if (this.speedCheck != 0.0F) {
/*     */           
/* 139 */           this.speedCheck = 0.0F;
/* 140 */           this.field_70180_af.func_75692_b(20, Float.valueOf(0.0F));
/*     */         } 
/* 142 */         if (this.field_70153_n != null && this.field_70153_n instanceof EntityPlayer)
/*     */         {
/* 144 */           if (IC2.keyboard.isSideinventoryKeyDown((EntityPlayer)this.field_70153_n) && !this.guiOpen)
/*     */           {
/* 146 */             IC2.platform.launchGui((EntityPlayer)this.field_70153_n, this);
/* 147 */             this.guiOpen = true;
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       }
/* 153 */       else if (this.field_70153_n == null || !(this.field_70153_n instanceof EntityPlayer)) {
/*     */         
/* 155 */         double energyUsage = Math.max((this.speedCheck != 0.0F) ? 1.0D : 0.0D, Math.abs(this.speedCheck * ((this.speedCheck < 0.0F) ? (MaxUsage * 2.0D) : MaxUsage)));
/* 156 */         energyUsage -= ElectricItem.manager.discharge(this.chest.func_70301_a(0), energyUsage, 2147483647, true, true, false);
/* 157 */         this.field_70180_af.func_75692_b(21, Float.valueOf(getCharge(this.chest.func_70301_a(0))));
/* 158 */         if (energyUsage != 0.0D || (this.field_70159_w == 0.0D && this.field_70179_y == 0.0D))
/*     */         {
/* 160 */           this.speedCheck = 0.0F;
/* 161 */           this.field_70180_af.func_75692_b(20, Float.valueOf(0.0F));
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 166 */         EntityPlayer player = (EntityPlayer)this.field_70153_n;
/* 167 */         if (player.field_70701_bs != 0.0F) {
/*     */           
/* 169 */           if ((player.field_70701_bs > 0.0F && this.speedCheck < 4.0F) || (player.field_70701_bs < 0.0F && this.speedCheck > -2.0F))
/*     */           {
/* 171 */             this.speedCheck += 0.05F * player.field_70701_bs;
/* 172 */             this.speedCheck = Math.min(4.0F, Math.max(-2.0F, this.speedCheck));
/* 173 */             this.field_70180_af.func_75692_b(20, Float.valueOf(this.speedCheck));
/*     */           }
/*     */         
/* 176 */         } else if (IC2.keyboard.isModeSwitchKeyDown(player)) {
/*     */           
/* 178 */           this.speedCheck = 0.0F;
/* 179 */           this.field_70180_af.func_75692_b(20, Float.valueOf(0.0F));
/*     */         } 
/* 181 */         double energyUsage = Math.max((this.speedCheck != 0.0F) ? 1.0D : 0.0D, Math.abs(this.speedCheck * ((this.speedCheck < 0.0F) ? (MaxUsage * 2.0D) : MaxUsage)));
/* 182 */         if (!ElectricItem.manager.use(this.chest.func_70301_a(0), energyUsage, (EntityLivingBase)player)) {
/*     */           
/* 184 */           this.speedCheck = 0.0F;
/* 185 */           this.field_70180_af.func_75692_b(20, Float.valueOf(0.0F));
/*     */         } 
/* 187 */         this.field_70180_af.func_75692_b(21, Float.valueOf(getCharge(this.chest.func_70301_a(0))));
/* 188 */         if (IC2.keyboard.isSideinventoryKeyDown(player) && !this.guiOpen) {
/*     */           
/* 190 */           IC2.platform.launchGui(player, this);
/* 191 */           this.guiOpen = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 196 */     super.func_70071_h_();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSpeed(EntityLivingBase base) {
/* 202 */     return this.field_70180_af.func_111145_d(20);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTopSpeed() {
/* 208 */     return 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAfkSpeed() {
/* 214 */     return (this.speedCheck != 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAfkSpeed() {
/* 220 */     return this.field_70180_af.func_111145_d(20);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxRammingSpeed() {
/* 226 */     return 2.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_76316_a(InventoryBasic p_76316_1_) {
/* 232 */     if (IC2.platform.isRendering()) {
/*     */       return;
/*     */     }
/*     */     
/* 236 */     this.field_70180_af.func_75692_b(21, Float.valueOf(getCharge(this.chest.func_70301_a(0))));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCharge() {
/* 241 */     return this.field_70180_af.func_111145_d(21);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getForce() {
/* 246 */     return this.field_70180_af.func_111145_d(20);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 252 */     return new ContainerElectricBoat(p0.field_71071_by, (IInventory)this.chest, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 258 */     return "item.boats.GuiElectricBoat";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {
/* 264 */     this.guiOpen = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\boats\EntityElectricBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */