/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.RecipeInputItemStack;
/*     */ import ic2.api.recipe.RecipeInputOreDict;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.container.ContainerMatter;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class TileEntityMatter
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui
/*     */ {
/*     */   public int soundTicker;
/*  27 */   public int scrap = 0;
/*  28 */   private int StateIdle = 0;
/*  29 */   private int StateRunning = 1;
/*  30 */   private int StateRunningScrap = 2;
/*  31 */   private int state = 0;
/*  32 */   private int prevState = 0;
/*     */   
/*     */   private AudioSource audioSource;
/*     */   private AudioSource audioSourceScrap;
/*     */   
/*     */   public TileEntityMatter() {
/*  38 */     super(2, 0, 1100000, 512);
/*  39 */     this.soundTicker = IC2.random.nextInt(32);
/*  40 */     addNetworkFields(new String[] { "state" });
/*  41 */     addGuiFields(new String[] { "scrap" });
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  46 */     addAmplifier(Ic2Items.scrap, 5000);
/*  47 */     addAmplifier(Ic2Items.scrapBox, 45000);
/*  48 */     addAmplifier(Ic2Items.scrapMetal, 100000);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addAmplifier(ItemStack input, int amplification) {
/*  53 */     addAmplifier((IRecipeInput)new RecipeInputItemStack(input), amplification);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addAmplifier(ItemStack input, int amount, int amplification) {
/*  58 */     addAmplifier((IRecipeInput)new RecipeInputItemStack(input, amount), amplification);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addAmplifier(String input, int amount, int amplification) {
/*  63 */     addAmplifier((IRecipeInput)new RecipeInputOreDict(input, amount), amplification);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addAmplifier(IRecipeInput input, int amplification) {
/*  68 */     NBTTagCompound metadata = new NBTTagCompound();
/*  69 */     metadata.func_74768_a("amplification", amplification);
/*     */     
/*  71 */     Recipes.matterAmplifier.addRecipe(input, metadata, new ItemStack[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  77 */     super.func_145839_a(nbttagcompound);
/*     */     
/*     */     try {
/*  80 */       this.scrap = nbttagcompound.func_74762_e("scrap");
/*     */     }
/*  82 */     catch (Throwable e) {
/*     */       
/*  84 */       this.scrap = nbttagcompound.func_74765_d("scrap");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  91 */     super.func_145841_b(nbttagcompound);
/*  92 */     nbttagcompound.func_74768_a("scrap", this.scrap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  98 */     return "Mass Fabricator";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 104 */     super.func_145845_h();
/* 105 */     handleRedstone();
/* 106 */     if (isRedstonePowered() || this.energy <= 0) {
/*     */       
/* 108 */       setState(0);
/* 109 */       setActive(false);
/*     */     }
/*     */     else {
/*     */       
/* 113 */       setState((this.scrap > 0) ? 2 : 1);
/* 114 */       setActive(true);
/* 115 */       boolean needsInvUpdate = false;
/* 116 */       if (this.scrap < 1000 && this.inventory[0] != null) {
/*     */         
/* 118 */         RecipeOutput output = Recipes.matterAmplifier.getOutputFor(this.inventory[0], false);
/* 119 */         if (output != null) {
/*     */           
/* 121 */           if (this.inventory[0].func_77973_b().hasContainerItem(this.inventory[0])) {
/*     */             
/* 123 */             this.inventory[0] = this.inventory[0].func_77973_b().getContainerItem(this.inventory[0]);
/*     */           }
/*     */           else {
/*     */             
/* 127 */             ItemStack itemStack = this.inventory[0];
/* 128 */             itemStack.field_77994_a--;
/* 129 */             if ((this.inventory[0]).field_77994_a <= 0)
/*     */             {
/* 131 */               this.inventory[0] = null;
/*     */             }
/*     */           } 
/* 134 */           this.scrap += output.metadata.func_74762_e("amplification");
/* 135 */           getNetwork().updateTileGuiField((TileEntity)this, "scrap");
/*     */         } 
/*     */       } 
/* 138 */       if (this.energy >= 1000000)
/*     */       {
/* 140 */         needsInvUpdate = attemptGeneration();
/*     */       }
/* 142 */       if (needsInvUpdate)
/*     */       {
/* 144 */         func_70296_d();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 152 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 154 */       IC2.audioManager.removeSources(this);
/* 155 */       this.audioSource = null;
/* 156 */       this.audioSourceScrap = null;
/*     */     } 
/* 158 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attemptGeneration() {
/* 163 */     if (this.inventory[1] == null) {
/*     */       
/* 165 */       this.inventory[1] = Ic2Items.matter.func_77946_l();
/* 166 */       this.energy -= 1000000;
/* 167 */       getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 168 */       return true;
/*     */     } 
/* 170 */     if (!this.inventory[1].func_77969_a(Ic2Items.matter) || (this.inventory[1]).field_77994_a >= this.inventory[1].func_77976_d())
/*     */     {
/* 172 */       return false;
/*     */     }
/* 174 */     this.energy -= 1000000;
/* 175 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 176 */     ItemStack itemStack = this.inventory[1];
/* 177 */     itemStack.field_77994_a++;
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 184 */     if (isRedstonePowered())
/*     */     {
/* 186 */       return 0.0D;
/*     */     }
/* 188 */     return (this.maxEnergy - this.energy);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volts) {
/* 194 */     if (amount > 512.0D)
/*     */     {
/* 196 */       return 0.0D;
/*     */     }
/* 198 */     int bonus = (int)amount;
/* 199 */     if (bonus > this.scrap)
/*     */     {
/* 201 */       bonus = this.scrap;
/*     */     }
/* 203 */     this.scrap -= bonus;
/* 204 */     this.energy = (int)(this.energy + amount + (5 * bonus));
/* 205 */     getNetwork().updateTileGuiField((TileEntity)this, "energy");
/* 206 */     getNetwork().updateTileGuiField((TileEntity)this, "scrap");
/* 207 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 213 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProgressAsString() {
/* 218 */     int p = this.energy / 10000;
/* 219 */     if (p > 100)
/*     */     {
/* 221 */       p = 100;
/*     */     }
/* 223 */     return "" + p + "%";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 229 */     return (ContainerIC2)new ContainerMatter(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 235 */     return "block.machine.gui.GuiMatter";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void setState(int state) {
/* 245 */     this.state = state;
/* 246 */     if (this.prevState != state)
/*     */     {
/* 248 */       getNetwork().updateTileEntityField((TileEntity)this, "state");
/*     */     }
/* 250 */     this.prevState = state;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 255 */     if (field.equals("state") && this.prevState != this.state) {
/*     */       
/* 257 */       if (this.audioSource != null && this.audioSource.isRemoved())
/*     */       {
/* 259 */         this.audioSource = null;
/*     */       }
/*     */       
/* 262 */       switch (this.state) {
/*     */ 
/*     */         
/*     */         case 0:
/* 266 */           if (this.audioSource != null)
/*     */           {
/* 268 */             this.audioSource.stop();
/*     */           }
/* 270 */           if (this.audioSourceScrap != null)
/*     */           {
/* 272 */             this.audioSourceScrap.stop();
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 279 */           if (this.audioSource == null)
/*     */           {
/* 281 */             this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */           }
/* 283 */           if (this.audioSource != null)
/*     */           {
/* 285 */             this.audioSource.play();
/*     */           }
/* 287 */           if (this.audioSourceScrap != null)
/*     */           {
/* 289 */             this.audioSourceScrap.stop();
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 296 */           if (this.audioSource == null)
/*     */           {
/* 298 */             this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */           }
/* 300 */           if (this.audioSourceScrap == null)
/*     */           {
/* 302 */             this.audioSourceScrap = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabScrapSolo.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */           }
/* 304 */           if (this.audioSource != null)
/*     */           {
/* 306 */             this.audioSource.play();
/*     */           }
/* 308 */           if (this.audioSourceScrap != null)
/*     */           {
/* 310 */             this.audioSourceScrap.play();
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 316 */       this.prevState = this.state;
/*     */     } 
/* 318 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 323 */     if (var1 == 0)
/*     */     {
/* 325 */       return new int[] { 0 };
/*     */     }
/* 327 */     if (var1 == 1)
/*     */     {
/* 329 */       return new int[] { 1 };
/*     */     }
/* 331 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWrenchDropRate() {
/* 336 */     return 0.7F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 342 */     return this.maxEnergy;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityMatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */