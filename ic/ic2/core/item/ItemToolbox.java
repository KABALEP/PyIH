/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.api.item.ItemWrapper;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemToolbox
/*     */   extends ItemIC2 implements IBoxable {
/*     */   public ItemToolbox(int index) {
/*  20 */     super(index);
/*  21 */     func_77625_d(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IIcon func_77617_a(int i) {
/*  27 */     if (i == 0)
/*     */     {
/*  29 */       return Ic2Icons.getTexture("i1")[this.iconIndex + 1];
/*     */     }
/*  31 */     return Ic2Icons.getTexture("i1")[this.iconIndex];
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_77667_c(ItemStack p_77667_1_) {
/*  36 */     if (p_77667_1_ == null)
/*     */     {
/*  38 */       return "DAMN TMI CAUSING NPE's!";
/*     */     }
/*  40 */     return "item.itemToolbox";
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack[] getInventoryFromNBT(ItemStack is) {
/*  45 */     ItemStack[] re = new ItemStack[8];
/*  46 */     if (is.func_77978_p() == null)
/*     */     {
/*  48 */       return re;
/*     */     }
/*  50 */     NBTTagCompound tag = is.func_77978_p();
/*  51 */     for (int i = 0; i < 8; i++) {
/*     */       
/*  53 */       NBTTagCompound item = tag.func_74775_l("box" + i);
/*  54 */       if (item != null)
/*     */       {
/*  56 */         re[i] = ItemStack.func_77949_a(item);
/*     */       }
/*     */     } 
/*  59 */     return re;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack itemstack, World world, EntityPlayer entityplayer) {
/*  64 */     if (!IC2.platform.isSimulating())
/*     */     {
/*  66 */       return itemstack;
/*     */     }
/*  68 */     if (itemstack.func_77960_j() == 0) {
/*     */       
/*  70 */       pack(itemstack, entityplayer);
/*     */     }
/*     */     else {
/*     */       
/*  74 */       unpack(itemstack, entityplayer);
/*     */     } 
/*  76 */     if (!IC2.platform.isRendering())
/*     */     {
/*  78 */       entityplayer.field_71070_bA.func_75142_b();
/*     */     }
/*  80 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack wayne) {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pack(ItemStack toolbox, EntityPlayer player) {
/*  91 */     ItemStack[] hotbar = player.field_71071_by.field_70462_a;
/*  92 */     NBTTagCompound mainbox = new NBTTagCompound();
/*  93 */     int boxcount = 0;
/*  94 */     for (int i = 0; i < 9; i++) {
/*     */       
/*  96 */       if (hotbar[i] != null && hotbar[i] != toolbox)
/*     */       {
/*  98 */         if (ItemWrapper.canBeStoredInToolbox(hotbar[i]))
/*     */         {
/*     */ 
/*     */           
/* 102 */           if (hotbar[i].func_77976_d() <= 1 || hotbar[i].func_77973_b() == Ic2Items.scaffold.func_77973_b() || hotbar[i].func_77973_b() == Ic2Items.miningPipe.func_77973_b()) {
/*     */ 
/*     */ 
/*     */             
/* 106 */             NBTTagCompound myBox = new NBTTagCompound();
/* 107 */             hotbar[i].func_77955_b(myBox);
/* 108 */             hotbar[i] = null;
/* 109 */             mainbox.func_74782_a("box" + boxcount, (NBTBase)myBox);
/* 110 */             boxcount++;
/*     */           }  }  } 
/*     */     } 
/* 113 */     if (boxcount == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 117 */     toolbox.func_77982_d(mainbox);
/* 118 */     toolbox.func_77964_b(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unpack(ItemStack toolbox, EntityPlayer player) {
/* 123 */     NBTTagCompound box = toolbox.func_77978_p();
/* 124 */     if (box == null) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     ItemStack[] inventory = getInventoryFromNBT(toolbox);
/* 129 */     ItemStack[] hotbar = player.field_71071_by.field_70462_a;
/* 130 */     int inv = 0;
/* 131 */     for (int i = 0; i < inventory.length; i++) {
/*     */       
/* 133 */       if (inventory[inv] == null) {
/*     */         break;
/*     */       }
/*     */       
/* 137 */       if (hotbar[i] == null) {
/*     */         
/* 139 */         hotbar[i] = inventory[inv];
/* 140 */         inv++;
/*     */       } 
/*     */     } 
/* 143 */     while (inv < 8 && inventory[inv] != null) {
/*     */       
/* 145 */       StackUtil.dropAsEntity(((Entity)player).field_70170_p, (int)((Entity)player).field_70165_t, (int)((Entity)player).field_70163_u, (int)((Entity)player).field_70161_v, inventory[inv]);
/* 146 */       inv++;
/*     */     } 
/* 148 */     toolbox.func_77982_d((NBTTagCompound)null);
/* 149 */     toolbox.func_77964_b(0);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\ItemToolbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */