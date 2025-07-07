/*     */ package ic2.core.block.generator.tileentity;
/*     */ 
/*     */ import com.google.common.math.DoubleMath;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.generator.container.ContainerSolarGenerator;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntitySolarGenerator
/*     */   extends TileEntityBaseGenerator
/*     */   implements ISidedInventory
/*     */ {
/*  25 */   public static Random randomizer = new Random();
/*     */   public int ticker;
/*     */   public boolean sunIsVisible = false;
/*  28 */   public double extraEnergy = 0.0D;
/*     */ 
/*     */   
/*     */   public TileEntitySolarGenerator() {
/*  32 */     this(1, 1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntitySolarGenerator(int par1, int par2, int par3) {
/*  37 */     super(par1, par2, par3);
/*  38 */     this.ticker = randomizer.nextInt(tickRate());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  44 */     super.onLoaded();
/*  45 */     updateSunVisibility();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int gaugeFuelScaled(int i) {
/*  51 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gainEnergy() {
/*  57 */     if (this.ticker++ % tickRate() == 0)
/*     */     {
/*  59 */       updateSunVisibility();
/*     */     }
/*  61 */     if (this.sunIsVisible && this.storage < this.maxStorage) {
/*     */       
/*  63 */       double gen = this.production * IC2.energyGeneratorSolar / 100.0D;
/*  64 */       this.extraEnergy += gen;
/*  65 */       if (this.extraEnergy >= 1.0D) {
/*     */         
/*  67 */         int amount = DoubleMath.roundToInt(this.extraEnergy, RoundingMode.FLOOR);
/*  68 */         this.storage = (short)(this.storage + amount);
/*  69 */         this.extraEnergy -= amount;
/*     */       } 
/*  71 */       return true;
/*     */     } 
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gainFuel() {
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSunVisibility() {
/*  84 */     this.sunIsVisible = isSunVisible(this.field_145850_b, this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
/*  85 */     getNetwork().updateTileGuiField((TileEntity)this, "sunIsVisible");
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSunVisible(World world, int x, int y, int z) {
/*  90 */     return (world.func_72935_r() && !world.field_73011_w.field_76576_e && world.func_72937_j(x, y, z) && (world.func_72959_q().func_76935_a(x, z) instanceof net.minecraft.world.biome.BiomeGenDesert || (!world.func_72896_J() && !world.func_72911_I())));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsFuel() {
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/* 102 */     return "Solar Panel";
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 107 */     return (ContainerIC2)new ContainerSolarGenerator(entityPlayer, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 112 */     return "block.generator.gui.GuiSolarGenerator";
/*     */   }
/*     */ 
/*     */   
/*     */   public int tickRate() {
/* 117 */     return 128;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delayActiveUpdate() {
/* 123 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 138 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 143 */     return StatCollector.func_74838_a("blockSolarGenerator.name");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnergyAmount() {
/* 149 */     return (int)(this.production * IC2.energyGeneratorSolar / 100.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 155 */     super.func_145839_a(nbttagcompound);
/* 156 */     this.extraEnergy = nbttagcompound.func_74769_h("ExtraEnergy");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 162 */     super.func_145841_b(nbttagcompound);
/* 163 */     nbttagcompound.func_74780_a("ExtraEnergy", this.extraEnergy);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\generator\tileentity\TileEntitySolarGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */