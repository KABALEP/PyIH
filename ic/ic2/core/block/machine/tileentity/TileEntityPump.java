/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.Event;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
/*     */ import ic2.core.block.machine.container.ContainerPump;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.util.FakePlayerFactory;
/*     */ import net.minecraftforge.event.entity.player.FillBucketEvent;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidBlock;
/*     */ import net.minecraftforge.fluids.IFluidContainerItem;
/*     */ 
/*     */ public class TileEntityPump
/*     */   extends TileEntityElecMachine implements IHasGui {
/*     */   public int soundTicker;
/*  38 */   public short pumpCharge = 0;
/*  39 */   public byte delay = 0;
/*     */   
/*     */   private AudioSource audioSource;
/*     */   
/*     */   public TileEntityPump() {
/*  44 */     super(2, 1, 200, 32);
/*  45 */     this.soundTicker = IC2.random.nextInt(64);
/*  46 */     addGuiFields(new String[] { "pumpCharge" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  52 */     return "Pump";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  58 */     super.func_145845_h();
/*  59 */     boolean needsInvUpdate = false;
/*  60 */     if (this.energy > 0 && !isPumpReady()) {
/*     */       
/*  62 */       useEnergy(1);
/*  63 */       this.pumpCharge = (short)(this.pumpCharge + 1);
/*  64 */       getNetwork().updateTileGuiField((TileEntity)this, "pumpCharge");
/*     */     } 
/*  66 */     if (this.delay > 0)
/*     */     {
/*  68 */       this.delay = (byte)(this.delay - 1);
/*     */     }
/*  70 */     if (this.energy <= this.maxEnergy)
/*     */     {
/*  72 */       needsInvUpdate = provideEnergy();
/*     */     }
/*  74 */     if (this.delay <= 0 && isPumpReady()) {
/*     */       
/*  76 */       needsInvUpdate = pump();
/*  77 */       if (needsInvUpdate) {
/*     */         
/*  79 */         this.delay = 120;
/*  80 */         getNetwork().updateTileGuiField((TileEntity)this, "pumpCharge");
/*     */       } 
/*     */     } 
/*  83 */     if (getActive() == isPumpReady() && this.energy > 0)
/*     */     {
/*  85 */       setActive(!getActive());
/*     */     }
/*  87 */     if (needsInvUpdate)
/*     */     {
/*  89 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/*  96 */     if (isRendering() && this.audioSource != null) {
/*     */       
/*  98 */       IC2.audioManager.removeSources(this);
/*  99 */       this.audioSource = null;
/*     */     } 
/* 101 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean pump() {
/* 106 */     if (!canHarvest() || !isFluidBelow())
/*     */     {
/* 108 */       return false;
/*     */     }
/* 110 */     if (isWaterBelow() || isLavaBelow()) {
/*     */       
/* 112 */       Block id = this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
/* 113 */       if (id == Blocks.field_150355_j || id == Blocks.field_150353_l)
/*     */       {
/* 115 */         if (pumpThis(id)) {
/*     */           
/* 117 */           this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
/* 118 */           return true;
/*     */         } 
/*     */       }
/*     */     } 
/* 122 */     if (this.inventory[0] != null && this.inventory[0].func_77973_b() == Items.field_151133_ar) {
/*     */       
/* 124 */       FillBucketEvent evt = new FillBucketEvent((EntityPlayer)FakePlayerFactory.getMinecraft((WorldServer)func_145831_w()), new ItemStack(Items.field_151133_ar), this.field_145850_b, new MovingObjectPosition(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, 1, Vec3.func_72443_a(this.field_145851_c, (this.field_145848_d - 1), this.field_145849_e)));
/* 125 */       MinecraftForge.EVENT_BUS.post((Event)evt);
/* 126 */       if (evt.result != null && evt.result.func_77973_b() != Items.field_151133_ar) {
/*     */         
/* 128 */         List<ItemStack> drops = new ArrayList();
/* 129 */         drops.add(evt.result);
/* 130 */         StackUtil.distributeDrop((TileEntity)this, drops);
/* 131 */         ItemStack itemStack = this.inventory[0];
/* 132 */         itemStack.field_77994_a--;
/* 133 */         if ((this.inventory[0]).field_77994_a <= 0)
/*     */         {
/* 135 */           this.inventory[0] = null;
/*     */         }
/* 137 */         this.pumpCharge = 0;
/* 138 */         return true;
/*     */       } 
/*     */     } 
/* 141 */     if (this.inventory[0] != null && this.inventory[0].func_77973_b() instanceof IFluidContainerItem) {
/*     */       
/* 143 */       IFluidContainerItem item = (IFluidContainerItem)this.inventory[0].func_77973_b();
/* 144 */       FluidStack fluid = getFluidBelow(false);
/* 145 */       boolean flag = false;
/* 146 */       if (item.fill(this.inventory[0], fluid, false) == fluid.amount) {
/*     */         
/* 148 */         item.fill(this.inventory[0], getFluidBelow(true), true);
/* 149 */         flag = true;
/* 150 */         this.pumpCharge = 0;
/*     */       } 
/* 152 */       if ((flag && isFull(this.inventory[0], item)) || !flag) {
/*     */         
/* 154 */         List<ItemStack> drops2 = new ArrayList();
/* 155 */         drops2.add(this.inventory[0].func_77946_l());
/* 156 */         this.inventory[0] = null;
/* 157 */         StackUtil.distributeDrop((TileEntity)this, drops2);
/*     */       } 
/* 159 */       return flag;
/*     */     } 
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFluidBelow() {
/* 166 */     return (getFluidBelow(false) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidStack getFluidBelow(boolean drain) {
/* 171 */     Block block = this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
/* 172 */     if (this.field_145850_b.func_147437_c(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e))
/*     */     {
/* 174 */       return null;
/*     */     }
/* 176 */     Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
/* 177 */     if (fluid == null)
/*     */     {
/* 179 */       return null;
/*     */     }
/* 181 */     if (fluid == FluidRegistry.WATER || fluid == FluidRegistry.LAVA) {
/*     */       
/* 183 */       if (drain)
/*     */       {
/* 185 */         this.field_145850_b.func_147468_f(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
/*     */       }
/* 187 */       return new FluidStack(fluid, 1000);
/*     */     } 
/* 189 */     if (block instanceof IFluidBlock)
/*     */     {
/* 191 */       return ((IFluidBlock)block).drain(this.field_145850_b, this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, drain);
/*     */     }
/* 193 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGeoAvaible() {
/* 198 */     return (isGeoAviable(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) || isGeoAviable(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) || isGeoAviable(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) || isGeoAviable(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) || isGeoAviable(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) || isGeoAviable(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGeoAviable(int x, int y, int z) {
/* 203 */     return this.field_145850_b.func_147438_o(x, y, z) instanceof TileEntityGeoGenerator;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWaterBelow() {
/* 208 */     return (this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == Blocks.field_150355_j && this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLavaBelow() {
/* 213 */     return (this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == Blocks.field_150353_l && this.field_145850_b.func_72805_g(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean pumpThis(Block water) {
/* 218 */     if (water == Blocks.field_150353_l && deliverLavaToGeo()) {
/*     */       
/* 220 */       this.pumpCharge = 0;
/* 221 */       return true;
/*     */     } 
/* 223 */     if (this.inventory[0] != null && this.inventory[0].func_77973_b() == Items.field_151133_ar) {
/*     */       
/* 225 */       ItemStack drop = null;
/* 226 */       if (water == Blocks.field_150355_j)
/*     */       {
/* 228 */         drop = new ItemStack(Items.field_151131_as);
/*     */       }
/* 230 */       if (water == Blocks.field_150353_l)
/*     */       {
/* 232 */         drop = new ItemStack(Items.field_151129_at);
/*     */       }
/* 234 */       List<ItemStack> drops = new ArrayList();
/* 235 */       drops.add(drop);
/* 236 */       StackUtil.distributeDrop((TileEntity)this, drops);
/* 237 */       (this.inventory[0]).field_77994_a--;
/* 238 */       if ((this.inventory[0]).field_77994_a <= 0)
/*     */       {
/* 240 */         this.inventory[0] = null;
/*     */       }
/* 242 */       this.pumpCharge = 0;
/* 243 */       return true;
/*     */     } 
/* 245 */     if (this.inventory[0] != null && this.inventory[0].func_77973_b() == Ic2Items.cell.func_77973_b()) {
/*     */       
/* 247 */       ItemStack drop = null;
/* 248 */       if (water == Blocks.field_150355_j)
/*     */       {
/* 250 */         drop = Ic2Items.waterCell.func_77946_l();
/*     */       }
/* 252 */       if (water == Blocks.field_150353_l)
/*     */       {
/* 254 */         drop = Ic2Items.lavaCell.func_77946_l();
/*     */       }
/* 256 */       ItemStack itemStack = this.inventory[0];
/* 257 */       itemStack.field_77994_a--;
/* 258 */       if ((this.inventory[0]).field_77994_a <= 0)
/*     */       {
/* 260 */         this.inventory[0] = null;
/*     */       }
/* 262 */       List<ItemStack> drops2 = new ArrayList();
/* 263 */       drops2.add(drop);
/* 264 */       StackUtil.distributeDrop((TileEntity)this, drops2);
/* 265 */       this.pumpCharge = 0;
/* 266 */       return true;
/*     */     } 
/* 268 */     if (this.inventory[0] != null && this.inventory[0].func_77973_b() instanceof IFluidContainerItem) {
/*     */       
/* 270 */       IFluidContainerItem item = (IFluidContainerItem)this.inventory[0].func_77973_b();
/* 271 */       FluidStack stack = (water == Blocks.field_150355_j) ? new FluidStack(FluidRegistry.WATER, 1000) : new FluidStack(FluidRegistry.LAVA, 1000);
/* 272 */       boolean flag = false;
/* 273 */       if (item.fill(this.inventory[0], stack, false) == stack.amount) {
/*     */         
/* 275 */         item.fill(this.inventory[0], stack, true);
/* 276 */         this.pumpCharge = 0;
/* 277 */         flag = true;
/*     */       } 
/* 279 */       if ((flag && isFull(this.inventory[0], item)) || !flag) {
/*     */         
/* 281 */         List<ItemStack> drops2 = new ArrayList();
/* 282 */         drops2.add(this.inventory[0].func_77946_l());
/* 283 */         this.inventory[0] = null;
/* 284 */         StackUtil.distributeDrop((TileEntity)this, drops2);
/*     */       } 
/* 286 */       return flag;
/*     */     } 
/* 288 */     this.pumpCharge = 0;
/* 289 */     return putInChestBucket(water);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFull(ItemStack par1, IFluidContainerItem par2) {
/* 294 */     if (par2.getFluid(par1) == null)
/*     */     {
/* 296 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 300 */     return ((par2.getFluid(par1)).amount >= par2.getCapacity(par1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean putInChestBucket(Block water) {
/* 306 */     return (putInChestBucket(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e, water) || putInChestBucket(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e, water) || putInChestBucket(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e, water) || putInChestBucket(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e, water) || putInChestBucket(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1, water) || putInChestBucket(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1, water));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean putInChestBucket(int x, int y, int z, Block water) {
/* 311 */     if (!(this.field_145850_b.func_147438_o(x, y, z) instanceof TileEntityChest))
/*     */     {
/* 313 */       return false;
/*     */     }
/* 315 */     TileEntityChest chest = (TileEntityChest)this.field_145850_b.func_147438_o(x, y, z);
/* 316 */     for (int i = 0; i < chest.func_70302_i_(); i++) {
/*     */       
/* 318 */       if (chest.func_70301_a(i) != null && chest.func_70301_a(i).func_77973_b() == Items.field_151133_ar) {
/*     */         
/* 320 */         if (water == Blocks.field_150355_j)
/*     */         {
/* 322 */           chest.func_70301_a(i).func_150996_a(Items.field_151131_as);
/*     */         }
/* 324 */         if (water == Blocks.field_150353_l)
/*     */         {
/* 326 */           chest.func_70301_a(i).func_150996_a(Items.field_151129_at);
/*     */         }
/* 328 */         return true;
/*     */       } 
/*     */     } 
/* 331 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 338 */     super.func_145839_a(nbttagcompound);
/* 339 */     this.pumpCharge = nbttagcompound.func_74765_d("pumpCharge");
/* 340 */     this.delay = nbttagcompound.func_74771_c("Delay");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 346 */     super.func_145841_b(nbttagcompound);
/* 347 */     nbttagcompound.func_74777_a("pumpCharge", this.pumpCharge);
/* 348 */     nbttagcompound.func_74774_a("Delay", this.delay);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPumpReady() {
/* 353 */     return (this.pumpCharge >= 200);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHarvest() {
/* 358 */     return (isPumpReady() && ((this.inventory[0] != null && (this.inventory[0].func_77973_b() == Ic2Items.cell.func_77973_b() || this.inventory[0].func_77973_b() == Items.field_151133_ar || this.inventory[0].func_77973_b() instanceof IFluidContainerItem)) || isBucketInChestAvaible() || isGeoAvaible()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBucketInChestAvaible() {
/* 363 */     return (isBucketInChestAvaible(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) || isBucketInChestAvaible(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) || isBucketInChestAvaible(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) || isBucketInChestAvaible(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) || isBucketInChestAvaible(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) || isBucketInChestAvaible(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBucketInChestAvaible(int x, int y, int z) {
/* 368 */     if (!(this.field_145850_b.func_147438_o(x, y, z) instanceof TileEntityChest))
/*     */     {
/* 370 */       return false;
/*     */     }
/* 372 */     TileEntityChest chest = (TileEntityChest)this.field_145850_b.func_147438_o(x, y, z);
/* 373 */     for (int i = 0; i < chest.func_70302_i_(); i++) {
/*     */       
/* 375 */       if (chest.func_70301_a(i) != null && chest.func_70301_a(i).func_77973_b() == Items.field_151133_ar)
/*     */       {
/* 377 */         return true;
/*     */       }
/*     */     } 
/* 380 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean deliverLavaToGeo() {
/* 385 */     boolean flag = false;
/* 386 */     if (!flag && this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e) instanceof TileEntityGeoGenerator) {
/*     */       
/* 388 */       TileEntityGeoGenerator geo = (TileEntityGeoGenerator)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d + 1, this.field_145849_e);
/* 389 */       if (geo.canTakeBucket()) {
/*     */         
/* 391 */         flag = true;
/* 392 */         geo.distributeLava(1000);
/*     */       } 
/*     */     } 
/* 395 */     if (!flag && this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) instanceof TileEntityGeoGenerator) {
/*     */       
/* 397 */       TileEntityGeoGenerator geo = (TileEntityGeoGenerator)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
/* 398 */       if (geo.canTakeBucket()) {
/*     */         
/* 400 */         flag = true;
/* 401 */         geo.distributeLava(1000);
/*     */       } 
/*     */     } 
/* 404 */     if (!flag && this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityGeoGenerator) {
/*     */       
/* 406 */       TileEntityGeoGenerator geo = (TileEntityGeoGenerator)this.field_145850_b.func_147438_o(this.field_145851_c + 1, this.field_145848_d, this.field_145849_e);
/* 407 */       if (geo.canTakeBucket()) {
/*     */         
/* 409 */         flag = true;
/* 410 */         geo.distributeLava(1000);
/*     */       } 
/*     */     } 
/* 413 */     if (!flag && this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e) instanceof TileEntityGeoGenerator) {
/*     */       
/* 415 */       TileEntityGeoGenerator geo = (TileEntityGeoGenerator)this.field_145850_b.func_147438_o(this.field_145851_c - 1, this.field_145848_d, this.field_145849_e);
/* 416 */       if (geo.canTakeBucket()) {
/*     */         
/* 418 */         flag = true;
/* 419 */         geo.distributeLava(1000);
/*     */       } 
/*     */     } 
/* 422 */     if (!flag && this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1) instanceof TileEntityGeoGenerator) {
/*     */       
/* 424 */       TileEntityGeoGenerator geo = (TileEntityGeoGenerator)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e + 1);
/* 425 */       if (geo.canTakeBucket()) {
/*     */         
/* 427 */         flag = true;
/* 428 */         geo.distributeLava(1000);
/*     */       } 
/*     */     } 
/* 431 */     if (!flag && this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1) instanceof TileEntityGeoGenerator) {
/*     */       
/* 433 */       TileEntityGeoGenerator geo = (TileEntityGeoGenerator)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d, this.field_145849_e - 1);
/* 434 */       if (geo.canTakeBucket()) {
/*     */         
/* 436 */         flag = true;
/* 437 */         geo.distributeLava(1000);
/*     */       } 
/*     */     } 
/* 440 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 446 */     return (ContainerIC2)new ContainerPump(entityPlayer, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 452 */     return "block.machine.gui.GuiPump";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 462 */     if (field.equals("active") && this.prevActive != getActive()) {
/*     */       
/* 464 */       if (this.audioSource == null)
/*     */       {
/* 466 */         this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/PumpOp.ogg", true, false, IC2.audioManager.defaultVolume);
/*     */       }
/* 468 */       if (getActive()) {
/*     */         
/* 470 */         if (this.audioSource != null)
/*     */         {
/* 472 */           this.audioSource.play();
/*     */         }
/*     */       }
/* 475 */       else if (this.audioSource != null) {
/*     */         
/* 477 */         this.audioSource.stop();
/*     */       } 
/*     */     } 
/* 480 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int var1) {
/* 485 */     return new int[] { 0 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/* 491 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityPump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */