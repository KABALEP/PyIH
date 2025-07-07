/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.item.ITerraformerBP;
/*     */ import ic2.api.item.ITerraformingBP;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.item.tfbp.TerraformerBluePrint;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class TileEntityTerra
/*     */   extends TileEntityElecMachine {
/*  20 */   public int failedAttempts = 0;
/*  21 */   public int lastX = -1;
/*  22 */   public int lastY = -1;
/*  23 */   public int lastZ = -1;
/*  24 */   public AudioSource audioSource = null;
/*  25 */   public int inactiveTicks = 0;
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/*  29 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntityTerra() {
/*  34 */     super(1, 0, 100000, 512);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  40 */     return "Terraformer";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  46 */     super.func_145845_h();
/*  47 */     boolean newActive = false;
/*  48 */     ITerraformerBP tfbp = getBluePrint();
/*  49 */     if (tfbp != null)
/*     */     {
/*  51 */       if (this.energy >= tfbp.getConsume(this.inventory[0])) {
/*     */         
/*  53 */         newActive = true;
/*  54 */         int x = this.field_145851_c;
/*  55 */         int z = this.field_145849_e;
/*  56 */         int range = 1;
/*  57 */         if (this.lastY > -1) {
/*     */           
/*  59 */           range = tfbp.getRange(this.inventory[0]) / 10;
/*  60 */           x = this.lastX - this.field_145850_b.field_73012_v.nextInt(range + 1) + this.field_145850_b.field_73012_v.nextInt(range + 1);
/*  61 */           z = this.lastZ - this.field_145850_b.field_73012_v.nextInt(range + 1) + this.field_145850_b.field_73012_v.nextInt(range + 1);
/*     */         }
/*     */         else {
/*     */           
/*  65 */           if (this.failedAttempts > 4)
/*     */           {
/*  67 */             this.failedAttempts = 4;
/*     */           }
/*  69 */           range = tfbp.getRange(this.inventory[0]) * (this.failedAttempts + 1) / 5;
/*  70 */           x = x - this.field_145850_b.field_73012_v.nextInt(range + 1) + this.field_145850_b.field_73012_v.nextInt(range + 1);
/*  71 */           z = z - this.field_145850_b.field_73012_v.nextInt(range + 1) + this.field_145850_b.field_73012_v.nextInt(range + 1);
/*     */         } 
/*  73 */         if (tfbp.terraform(this.inventory[0], this.field_145850_b, x, z, this.field_145848_d)) {
/*     */           
/*  75 */           this.energy -= tfbp.getConsume(this.inventory[0]);
/*  76 */           this.failedAttempts = 0;
/*  77 */           this.lastX = x;
/*  78 */           this.lastZ = z;
/*  79 */           this.lastY = this.field_145848_d;
/*     */         }
/*     */         else {
/*     */           
/*  83 */           this.energy -= tfbp.getConsume(this.inventory[0]) / 10;
/*  84 */           this.failedAttempts++;
/*  85 */           this.lastY = -1;
/*     */         } 
/*     */       } 
/*     */     }
/*  89 */     if (newActive) {
/*     */       
/*  91 */       this.inactiveTicks = 0;
/*  92 */       setActive(true);
/*     */     }
/*  94 */     else if (!newActive && getActive() && this.inactiveTicks++ > 30) {
/*     */       
/*  96 */       setActive(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ITerraformerBP getBluePrint() {
/* 102 */     ItemStack stack = this.inventory[0];
/* 103 */     if (stack == null)
/*     */     {
/* 105 */       return null;
/*     */     }
/* 107 */     Item item = stack.func_77973_b();
/* 108 */     if (item instanceof ITerraformerBP)
/*     */     {
/* 110 */       return (ITerraformerBP)item;
/*     */     }
/* 112 */     if (item instanceof ITerraformingBP)
/*     */     {
/* 114 */       return (ITerraformerBP)new TerraformerBluePrint((ITerraformingBP)item);
/*     */     }
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 122 */     if (isRendering() && this.audioSource != null) {
/*     */       
/* 124 */       IC2.audioManager.removeSources(this);
/* 125 */       this.audioSource = null;
/*     */     } 
/* 127 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double injectEnergy(ForgeDirection directionFrom, double amount, double volt) {
/* 133 */     if (amount > 512.0D)
/*     */     {
/* 135 */       return 0.0D;
/*     */     }
/* 137 */     if (this.energy + amount > this.maxEnergy) {
/*     */       
/* 139 */       int unused = (int)(this.energy + amount - this.maxEnergy);
/* 140 */       this.energy = this.maxEnergy;
/* 141 */       return unused;
/*     */     } 
/* 143 */     this.energy = (int)(this.energy + amount);
/* 144 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ejectBlueprint() {
/* 149 */     if (this.inventory[0] == null)
/*     */     {
/* 151 */       return false;
/*     */     }
/* 153 */     if (isSimulating()) {
/*     */       
/* 155 */       StackUtil.dropAsEntity(this.field_145850_b, this.field_145851_c, this.field_145848_d, this.field_145849_e, this.inventory[0]);
/* 156 */       this.inventory[0] = null;
/*     */     } 
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertBlueprint(ItemStack tfbp) {
/* 163 */     ejectBlueprint();
/* 164 */     this.inventory[0] = tfbp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFirstSolidBlockFrom(World world, int x, int z, int y) {
/* 169 */     while (y > 0) {
/*     */       
/* 171 */       if (world.func_147445_c(x, y, z, false))
/*     */       {
/* 173 */         return y;
/*     */       }
/* 175 */       y--;
/*     */     } 
/* 177 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getFirstBlockFrom(World world, int x, int z, int y) {
/* 182 */     while (y > 0) {
/*     */       
/* 184 */       if (!world.func_147437_c(x, y, z))
/*     */       {
/* 186 */         return y;
/*     */       }
/* 188 */       y--;
/*     */     } 
/* 190 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean switchGround(World world, Block from, Block to, int x, int y, int z, boolean upwards) {
/* 195 */     if (upwards) {
/*     */       
/* 197 */       int saveY = ++y;
/*     */       
/*     */       while (true) {
/* 200 */         Block id = world.func_147439_a(x, y - 1, z);
/* 201 */         if (world.func_147437_c(x, y - 1, z) || id != from) {
/*     */           break;
/*     */         }
/*     */         
/* 205 */         y--;
/*     */       } 
/* 207 */       if (saveY == y)
/*     */       {
/* 209 */         return false;
/*     */       }
/* 211 */       world.func_147449_b(x, y, z, to);
/* 212 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 218 */       Block block = world.func_147439_a(x, y, z);
/* 219 */       if (world.func_147437_c(x, y, z) || block != to) {
/*     */         break;
/*     */       }
/*     */       
/* 223 */       y--;
/*     */     } 
/* 225 */     Block id2 = world.func_147439_a(x, y, z);
/* 226 */     if (world.func_147437_c(x, y, z) || id2 != from)
/*     */     {
/* 228 */       return false;
/*     */     }
/* 230 */     world.func_147449_b(x, y, z, to);
/* 231 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BiomeGenBase getBiomeAt(World world, int x, int z) {
/* 237 */     return world.func_72938_d(x, z).func_76591_a(x & 0xF, z & 0xF, world.func_72959_q());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setBiomeAt(World world, int x, int z, BiomeGenBase biome) {
/* 242 */     Chunk chunk = world.func_72938_d(x, z);
/* 243 */     byte[] array = chunk.func_76605_m();
/* 244 */     array[(z & 0xF) << 4 | x & 0xF] = (byte)(biome.field_76756_M & 0xFF);
/* 245 */     chunk.func_76616_a(array);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 251 */     if (field.equals("active") && this.prevActive != getActive()) {
/*     */       
/* 253 */       if (this.audioSource == null)
/*     */       {
/* 255 */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Terraformers/TerraformerGenericloop.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */       }
/* 257 */       if (getActive()) {
/*     */         
/* 259 */         if (this.audioSource != null)
/*     */         {
/* 261 */           this.audioSource.play();
/*     */         }
/*     */       }
/* 264 */       else if (this.audioSource != null) {
/*     */         
/* 266 */         this.audioSource.stop();
/*     */       } 
/*     */     } 
/* 269 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 275 */     ITerraformerBP tfbp = getBluePrint();
/* 276 */     if (tfbp != null)
/*     */     {
/* 278 */       return tfbp.getConsume(this.inventory[0]);
/*     */     }
/* 280 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityTerra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */