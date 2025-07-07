/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemRemote
/*     */   extends ItemIC2
/*     */ {
/*     */   public ItemRemote(int index) {
/*  25 */     super(index);
/*  26 */     func_77625_d(1);
/*  27 */     setSpriteID("i1");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77648_a(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float a, float b, float c) {
/*  32 */     if (!IC2.platform.isSimulating())
/*     */     {
/*  34 */       return (world.func_147439_a(i, j, k) == Block.func_149634_a(Ic2Items.dynamiteStick.func_77973_b()) || world.func_147439_a(i, j, k) == Block.func_149634_a(Ic2Items.dynamiteStickWithRemote.func_77973_b()));
/*     */     }
/*  36 */     if (world.func_147439_a(i, j, k) == Block.func_149634_a(Ic2Items.dynamiteStick.func_77973_b())) {
/*     */       
/*  38 */       addRemote(i, j, k, itemstack);
/*  39 */       entityplayer.field_71070_bA.func_75142_b();
/*  40 */       world.func_147449_b(i, j, k, Block.func_149634_a(Ic2Items.dynamiteStickWithRemote.func_77973_b()));
/*  41 */       return true;
/*     */     } 
/*  43 */     if (world.func_147439_a(i, j, k) == Block.func_149634_a(Ic2Items.dynamiteStickWithRemote.func_77973_b())) {
/*     */       
/*  45 */       int index = hasRemote(i, j, k, itemstack);
/*  46 */       if (index > -1) {
/*     */         
/*  48 */         world.func_147449_b(i, j, k, Block.func_149634_a(Ic2Items.dynamiteStick.func_77973_b()));
/*  49 */         removeRemote(index, itemstack);
/*  50 */         entityplayer.field_71070_bA.func_75142_b();
/*     */       }
/*     */       else {
/*     */         
/*  54 */         IC2.platform.messagePlayer(entityplayer, StatCollector.func_74838_a("itemInfo.stickyTransmitterNotLinked.name"));
/*     */       } 
/*  56 */       return true;
/*     */     } 
/*  58 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/*  63 */     if (!IC2.platform.isSimulating())
/*     */     {
/*  65 */       return itemstack;
/*     */     }
/*  67 */     IC2.audioManager.playOnce(entityplayer, PositionSpec.Hand, "Tools/dynamiteomote.ogg", true, IC2.audioManager.getDefaultVolume());
/*  68 */     launchRemotes(world, itemstack);
/*  69 */     entityplayer.field_71070_bA.func_75142_b();
/*  70 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRemote(int x, int y, int z, ItemStack freq) {
/*  75 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(freq);
/*  76 */     if (!compound.func_74764_b("coords"))
/*     */     {
/*  78 */       compound.func_74782_a("coords", (NBTBase)new NBTTagList());
/*     */     }
/*  80 */     NBTTagList coords = compound.func_150295_c("coords", 10);
/*  81 */     NBTTagCompound coord = new NBTTagCompound();
/*  82 */     coord.func_74768_a("x", x);
/*  83 */     coord.func_74768_a("y", y);
/*  84 */     coord.func_74768_a("z", z);
/*  85 */     coords.func_74742_a((NBTBase)coord);
/*  86 */     compound.func_74782_a("coords", (NBTBase)coords);
/*  87 */     freq.func_77964_b(coords.func_74745_c());
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_77624_a(ItemStack stack, EntityPlayer player, List<String> info, boolean debugTooltips) {
/*  92 */     if (stack.func_77960_j() > 0)
/*     */     {
/*  94 */       info.add(StatCollector.func_74837_a("itemInfo.stickyTransmitterLink.name", new Object[] { Integer.valueOf(stack.func_77960_j()) }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void launchRemotes(World world, ItemStack freq) {
/* 100 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(freq);
/* 101 */     if (!compound.func_74764_b("coords")) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     NBTTagList coords = compound.func_150295_c("coords", 10);
/* 106 */     for (int i = 0; i < coords.func_74745_c(); i++) {
/*     */       
/* 108 */       NBTTagCompound coord = coords.func_150305_b(i);
/* 109 */       int x = coord.func_74762_e("x");
/* 110 */       int y = coord.func_74762_e("y");
/* 111 */       int z = coord.func_74762_e("z");
/* 112 */       Explosion fakeExplosion = new Explosion(world, null, x, y, z, 1.0F);
/* 113 */       if (world.func_147439_a(x, y, z) == Block.func_149634_a(Ic2Items.dynamiteStickWithRemote.func_77973_b())) {
/*     */         
/* 115 */         Block.func_149634_a(Ic2Items.dynamiteStickWithRemote.func_77973_b()).func_149723_a(world, x, y, z, fakeExplosion);
/* 116 */         world.func_147468_f(x, y, z);
/*     */       } 
/*     */     } 
/* 119 */     compound.func_74782_a("coords", (NBTBase)new NBTTagList());
/* 120 */     freq.func_77964_b(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int hasRemote(int x, int y, int z, ItemStack freq) {
/* 125 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(freq);
/* 126 */     if (!compound.func_74764_b("coords"))
/*     */     {
/* 128 */       return -1;
/*     */     }
/* 130 */     NBTTagList coords = compound.func_150295_c("coords", 10);
/* 131 */     for (int i = 0; i < coords.func_74745_c(); i++) {
/*     */       
/* 133 */       NBTTagCompound coord = coords.func_150305_b(i);
/* 134 */       if (coord.func_74762_e("x") == x && coord.func_74762_e("y") == y && coord.func_74762_e("z") == z)
/*     */       {
/* 136 */         return i;
/*     */       }
/*     */     } 
/* 139 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeRemote(int index, ItemStack freq) {
/* 144 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(freq);
/* 145 */     if (!compound.func_74764_b("coords")) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     NBTTagList coords = compound.func_150295_c("coords", 10);
/* 150 */     NBTTagList newCoords = new NBTTagList();
/* 151 */     for (int i = 0; i < coords.func_74745_c(); i++) {
/*     */       
/* 153 */       if (i != index)
/*     */       {
/* 155 */         newCoords.func_74742_a((NBTBase)coords.func_150305_b(i));
/*     */       }
/*     */     } 
/* 158 */     compound.func_74782_a("coords", (NBTBase)newCoords);
/* 159 */     freq.func_77964_b(newCoords.func_74745_c());
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemRemote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */