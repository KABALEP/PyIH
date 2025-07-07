/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import ic2.api.info.IC2Classic;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.generator.block.BlockGenerator;
/*     */ import ic2.core.block.generator.container.ContainerWindGenerator;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class TileEntityWindGenerator
/*     */   extends TileEntityBaseGenerator
/*     */   implements ISidedInventory
/*     */ {
/*  23 */   public static Random randomizer = new Random();
/*  24 */   public double subproduction = 0.0D;
/*  25 */   public double substorage = 0.0D;
/*     */   
/*     */   public int ticker;
/*     */   public int obscuratedBlockCount;
/*     */   
/*     */   public TileEntityWindGenerator() {
/*  31 */     super(1, 4, 4);
/*  32 */     this.ticker = randomizer.nextInt(tickRate());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int gaugeFuelScaled(int i) {
/*  38 */     double prod = this.subproduction / 3.0D;
/*  39 */     int re = (int)(prod * i);
/*  40 */     if (re < 0)
/*     */     {
/*  42 */       return 0;
/*     */     }
/*  44 */     if (re > i)
/*     */     {
/*  46 */       return i;
/*     */     }
/*  48 */     return re;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOverheatScaled(int i) {
/*  53 */     double prod = (this.subproduction - 5.0D) / 5.0D;
/*  54 */     if (this.subproduction <= 5.0D)
/*     */     {
/*  56 */       return 0;
/*     */     }
/*  58 */     if (this.subproduction >= 10.0D)
/*     */     {
/*  60 */       return i;
/*     */     }
/*  62 */     return (int)(prod * i);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  68 */     super.onLoaded();
/*  69 */     updateObscuratedBlockCount();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gainEnergy() {
/*  75 */     this.ticker++;
/*  76 */     if (this.ticker % tickRate() == 0) {
/*     */       
/*  78 */       if (this.ticker % 8 * tickRate() == 0)
/*     */       {
/*  80 */         updateObscuratedBlockCount();
/*     */       }
/*  82 */       this.subproduction = (((IC2Classic.enabledCustoWindNetwork() ? IC2Classic.getWindNetwork().getWindStrenght(this.field_145850_b) : IC2.windStrength) * this.field_145848_d - 64.0F - this.obscuratedBlockCount) / 750.0F);
/*  83 */       if (this.subproduction <= 0.0D)
/*     */       {
/*  85 */         return false;
/*     */       }
/*  87 */       if (this.field_145850_b.func_72911_I()) {
/*     */         
/*  89 */         this.subproduction *= 1.5D;
/*     */       }
/*  91 */       else if (this.field_145850_b.func_72896_J()) {
/*     */         
/*  93 */         this.subproduction *= 1.2D;
/*     */       } 
/*  95 */       if (this.subproduction > 5.0D && this.field_145850_b.field_73012_v.nextInt(5000) <= this.subproduction - 5.0D) {
/*     */         
/*  97 */         this.subproduction = 0.0D;
/*  98 */         ItemStack stack = BlockGenerator.optionalDrops[4];
/*  99 */         if (stack != null) {
/*     */           
/* 101 */           Block block = Block.func_149634_a(stack.func_77973_b());
/* 102 */           if (block != Blocks.field_150350_a) {
/*     */             
/* 104 */             this.field_145850_b.func_147465_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, block, stack.func_77973_b().func_77647_b(stack.func_77960_j()), 3);
/* 105 */             return false;
/*     */           } 
/* 107 */           StackUtil.dropAsEntity(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, stack.func_77946_l());
/*     */         }
/*     */         else {
/*     */           
/* 111 */           this.field_145850_b.func_147465_d(this.field_145851_c, this.field_145848_d, this.field_145849_e, Block.func_149634_a(Ic2Items.generator.func_77973_b()), Ic2Items.generator.func_77960_j(), 3);
/* 112 */           for (int i = this.field_145850_b.field_73012_v.nextInt(5); i > 0; i--)
/*     */           {
/* 114 */             StackUtil.dropAsEntity(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, new ItemStack(Items.field_151042_j));
/*     */           }
/*     */         } 
/* 117 */         return false;
/*     */       } 
/* 119 */       this.subproduction *= IC2.energyGeneratorWind;
/* 120 */       this.subproduction /= 100.0D;
/*     */     } 
/* 122 */     this.substorage += this.subproduction;
/* 123 */     this.production = (short)(int)this.substorage;
/* 124 */     if (this.storage + this.production >= this.maxStorage) {
/*     */       
/* 126 */       this.substorage = 0.0D;
/* 127 */       return false;
/*     */     } 
/* 129 */     this.storage = (short)(this.storage + (short)this.production);
/* 130 */     this.substorage -= this.production;
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gainFuel() {
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateObscuratedBlockCount() {
/* 142 */     this.obscuratedBlockCount = -1;
/* 143 */     for (int x = -4; x < 5; x++) {
/*     */       
/* 145 */       for (int y = -2; y < 5; y++) {
/*     */         
/* 147 */         for (int z = -4; z < 5; z++) {
/*     */           
/* 149 */           if (!this.field_145850_b.func_147437_c(x + this.field_145851_c, y + this.field_145848_d, z + this.field_145849_e))
/*     */           {
/* 151 */             this.obscuratedBlockCount++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsFuel() {
/* 161 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 167 */     return "Wind Mill";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 172 */     return "block.generator.gui.GuiWindGenerator";
/*     */   }
/*     */ 
/*     */   
/*     */   public int tickRate() {
/* 177 */     return 128;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOperationSoundFile() {
/* 183 */     return "Generators/WindGenLoop.ogg";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delayActiveUpdate() {
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 194 */     return (ContainerIC2)new ContainerWindGenerator(entityPlayer, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 209 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/* 215 */     return 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntityWindGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */