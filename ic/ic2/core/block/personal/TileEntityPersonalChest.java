/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IIndirectInventory;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ public class TileEntityPersonalChest
/*     */   extends TileEntityBlock
/*     */   implements IPersonalBlock, IHasGui, IIndirectInventory
/*     */ {
/*     */   private int ticksSinceSync;
/*     */   private int numUsingPlayers;
/*     */   public float lidAngle;
/*     */   public float prevLidAngle;
/*     */   public UUID owner;
/*  25 */   PersonalInventory inv = new PersonalInventory(this, "Personal Save", 54);
/*     */ 
/*     */   
/*     */   public TileEntityPersonalChest() {
/*  29 */     addNetworkFields(new String[] { "owner" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  35 */     super.func_145839_a(nbttagcompound);
/*  36 */     if (nbttagcompound.func_74764_b("PlayerOwner")) {
/*     */       
/*  38 */       NBTTagCompound nbt = nbttagcompound.func_74775_l("PlayerOwner");
/*  39 */       this.owner = new UUID(nbt.func_74763_f("UUIDMost"), nbt.func_74763_f("UUIDLeast"));
/*     */     }
/*     */     else {
/*     */       
/*  43 */       this.owner = null;
/*     */     } 
/*  45 */     this.inv.readFromNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145841_b(NBTTagCompound nbttagcompound) {
/*  51 */     super.func_145841_b(nbttagcompound);
/*  52 */     if (this.owner != null) {
/*     */       
/*  54 */       NBTTagCompound nbt = new NBTTagCompound();
/*  55 */       nbt.func_74772_a("UUIDMost", this.owner.getMostSignificantBits());
/*  56 */       nbt.func_74772_a("UUIDLeast", this.owner.getLeastSignificantBits());
/*  57 */       nbttagcompound.func_74782_a("PlayerOwner", (NBTBase)nbt);
/*     */     } 
/*  59 */     this.inv.writeToNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  71 */     if (++this.ticksSinceSync % 20 * 4 == 0 && isSimulating())
/*     */     {
/*  73 */       syncNumUsingPlayers();
/*     */     }
/*  75 */     this.prevLidAngle = this.lidAngle;
/*  76 */     float var1 = 0.1F;
/*  77 */     if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F) {
/*     */       
/*  79 */       double var2 = this.field_145851_c + 0.5D;
/*  80 */       double var3 = this.field_145849_e + 0.5D;
/*  81 */       this.field_145850_b.func_72908_a(var2, this.field_145848_d + 0.5D, var3, "random.chestopen", 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*  83 */     if ((this.numUsingPlayers == 0 && this.lidAngle > 0.0F) || (this.numUsingPlayers > 0 && this.lidAngle < 1.0F)) {
/*     */       
/*  85 */       float var4 = this.lidAngle;
/*  86 */       if (this.numUsingPlayers > 0) {
/*     */         
/*  88 */         this.lidAngle += var1;
/*     */       }
/*     */       else {
/*     */         
/*  92 */         this.lidAngle -= var1;
/*     */       } 
/*  94 */       if (this.lidAngle > 1.0F)
/*     */       {
/*  96 */         this.lidAngle = 1.0F;
/*     */       }
/*  98 */       float var5 = 0.5F;
/*  99 */       if (this.lidAngle < var5 && var4 >= var5) {
/*     */         
/* 101 */         double var3 = this.field_145851_c + 0.5D;
/* 102 */         double var6 = this.field_145849_e + 0.5D;
/* 103 */         this.field_145850_b.func_72908_a(var3, this.field_145848_d + 0.5D, var6, "random.chestclosed", 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/* 105 */       if (this.lidAngle < 0.0F)
/*     */       {
/* 107 */         this.lidAngle = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void openInventory() {
/* 114 */     this.numUsingPlayers++;
/* 115 */     syncNumUsingPlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInventory() {
/* 120 */     this.numUsingPlayers--;
/* 121 */     syncNumUsingPlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_145842_c(int event, int data) {
/* 126 */     if (event == 1)
/*     */     {
/* 128 */       this.numUsingPlayers = data;
/*     */     }
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void syncNumUsingPlayers() {
/* 135 */     this.field_145850_b.func_147452_c(this.field_145851_c, this.field_145848_d, this.field_145849_e, this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d, this.field_145849_e), 1, this.numUsingPlayers);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
/* 140 */     if (!canAccess(entityPlayer))
/*     */     {
/* 142 */       return false;
/*     */     }
/* 144 */     boolean flag = this.inv.isInvEmpty();
/* 145 */     if (!flag)
/*     */     {
/* 147 */       IC2.platform.messagePlayer(entityPlayer, StatCollector.func_74838_a("itemInfo.wrenchPickSafe.name"));
/*     */     }
/* 149 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(EntityPlayer player) {
/* 155 */     if (this.owner == null) {
/*     */       
/* 157 */       this.owner = player.func_146103_bH().getId();
/* 158 */       getNetwork().updateTileEntityField((TileEntity)this, "owner");
/* 159 */       return true;
/*     */     } 
/* 161 */     return canAccess(player.func_146103_bH().getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAccess(UUID player) {
/* 167 */     if (this.owner == null)
/*     */     {
/* 169 */       return true;
/*     */     }
/* 171 */     return this.owner.equals(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer entityPlayer) {
/* 176 */     return new ContainerPersonalChest(entityPlayer, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer entityPlayer) {
/* 181 */     return "block.personal.GuiPersonalChest";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer entityPlayer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(EntityPlayer player) {
/* 191 */     if (!canAccess(player))
/*     */     {
/* 193 */       return null;
/*     */     }
/* 195 */     return getInventory(player.func_146103_bH().getId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IPersonalInventory getInventory(UUID player) {
/* 201 */     if (!canAccess(player))
/*     */     {
/* 203 */       return null;
/*     */     }
/* 205 */     return this.inv;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\personal\TileEntityPersonalChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */