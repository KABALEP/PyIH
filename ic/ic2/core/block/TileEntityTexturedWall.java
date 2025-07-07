/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.api.energy.tile.IEnergyConductorColored;
/*     */ import ic2.core.Ic2Icons;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.IIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityTexturedWall
/*     */   extends TileEntityBlock
/*     */ {
/*  17 */   public Block[] storedBlocks = new Block[6];
/*  18 */   public int[] storedMetas = new int[6];
/*  19 */   public int[] storedSides = new int[6];
/*  20 */   public int[] color = new int[6];
/*     */ 
/*     */   
/*     */   public TileEntityTexturedWall() {
/*  24 */     addNetworkFields(new String[] { "storedBlocks", "storedMetas", "storedSides", "color" });
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock(int side) {
/*  29 */     return this.storedBlocks[side];
/*     */   }
/*     */ 
/*     */   
/*     */   public IIcon getIcon(int side) {
/*  34 */     Block block = this.storedBlocks[side];
/*  35 */     if (block == null || block == Blocks.field_150350_a)
/*     */     {
/*  37 */       return Ic2Icons.getTexture("bcable")[187 + this.color[side]];
/*     */     }
/*  39 */     return block.func_149691_a(this.storedSides[side], this.storedMetas[side]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setIcon(int state, Block block, int meta, int side) {
/*  44 */     if (this.storedBlocks[state] == block && this.storedMetas[state] == meta && this.storedSides[state] == side)
/*     */     {
/*  46 */       return false;
/*     */     }
/*  48 */     if (!Block.func_149680_a(this.storedBlocks[state], block)) {
/*     */       
/*  50 */       this.storedBlocks[state] = block;
/*  51 */       getNetwork().updateTileEntityField(this, "storedBlocks");
/*     */     } 
/*  53 */     if (this.storedMetas[state] != meta) {
/*     */       
/*  55 */       this.storedMetas[state] = meta;
/*  56 */       getNetwork().updateTileEntityField(this, "storedMetas");
/*     */     } 
/*  58 */     if (this.storedSides[state] != side) {
/*     */       
/*  60 */       this.storedSides[state] = side;
/*  61 */       getNetwork().updateTileEntityField(this, "storedSides");
/*     */     } 
/*  63 */     if (block != null && this.color[state] != 0) {
/*     */       
/*  65 */       this.color[state] = 0;
/*  66 */       getNetwork().updateTileEntityField(this, "color");
/*     */     } 
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/*  74 */     if (field.equals("color") || field.equals("storedBlocks") || field.equals("storedMetas") || field.equals("storedSides"))
/*     */     {
/*  76 */       this.field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e);
/*     */     }
/*  78 */     super.onNetworkUpdate(field);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(IEnergyConductorColored.WireColor wireColor) {
/*  83 */     for (int i = 0; i < 6; i++) {
/*     */       
/*  85 */       if (this.color[i] != wireColor.ordinal()) {
/*     */         
/*  87 */         this.color[i] = wireColor.ordinal();
/*  88 */         getNetwork().updateTileEntityField(this, "color");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setColor(int side, IEnergyConductorColored.WireColor wireColor) {
/*  95 */     if (this.color[side] != wireColor.ordinal()) {
/*     */       
/*  97 */       this.color[side] = wireColor.ordinal();
/*  98 */       getNetwork().updateTileEntityField(this, "color");
/*  99 */       setIcon(side, (Block)null, 0, 0);
/* 100 */       return true;
/*     */     } 
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/* 108 */     super.func_145839_a(nbttagcompound);
/* 109 */     this.storedBlocks = new Block[6];
/* 110 */     this.storedMetas = new int[6];
/* 111 */     this.storedSides = new int[6];
/* 112 */     this.color = new int[6];
/* 113 */     NBTTagList list = nbttagcompound.func_150295_c("DataList", 10);
/* 114 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/* 116 */       NBTTagCompound nbt = list.func_150305_b(i);
/* 117 */       int slot = nbt.func_74762_e("Slot");
/* 118 */       this.color[slot] = nbt.func_74762_e("Color");
/* 119 */       String id = nbt.func_74779_i("ID");
/* 120 */       this.storedBlocks[slot] = id.equals("Empty") ? null : Block.func_149684_b(id);
/* 121 */       this.storedMetas[slot] = nbt.func_74762_e("Meta");
/* 122 */       this.storedSides[slot] = nbt.func_74762_e("Side");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/* 129 */     super.func_145841_b(nbttagcompound);
/* 130 */     NBTTagList list = new NBTTagList();
/* 131 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 133 */       NBTTagCompound nbt = new NBTTagCompound();
/* 134 */       nbt.func_74768_a("Slot", i);
/* 135 */       nbt.func_74768_a("Color", this.color[i]);
/* 136 */       Block block = this.storedBlocks[i];
/* 137 */       String id = (block == null || block == Blocks.field_150350_a) ? "Empty" : Block.field_149771_c.func_148750_c(block);
/* 138 */       nbt.func_74778_a("ID", id);
/* 139 */       nbt.func_74768_a("Meta", this.storedMetas[i]);
/* 140 */       nbt.func_74768_a("Side", this.storedSides[i]);
/* 141 */       list.func_74742_a((NBTBase)nbt);
/*     */     } 
/* 143 */     nbttagcompound.func_74782_a("DataList", (NBTBase)list);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\TileEntityTexturedWall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */