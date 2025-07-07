/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.block.machine.container.ContainerUraniumEnricher;
/*     */ import ic2.core.item.reactor.ItemReactorEnrichUranium;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ 
/*     */ public class TileEntityUraniumEnricher
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui {
/*  15 */   public byte type = -1;
/*  16 */   public int amount = 0;
/*     */   
/*  18 */   public int uranProgress = 0;
/*  19 */   public int itemProgress = 0;
/*     */   
/*     */   public static final int maxUranProgress = 1000;
/*     */   
/*     */   public static final int maxItemProgress = 100;
/*     */ 
/*     */   
/*     */   public TileEntityUraniumEnricher() {
/*  27 */     super(3, -1, 50000, 128, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/*  33 */     return ((this.inventory[0] != null) ? getEnergyUseFromUranType() : 0) + ((this.inventory[1] != null) ? 20 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int p_94128_1_) {
/*  39 */     return new int[] { 0, 1, 2 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_102008_b(int i, ItemStack itemstack, int j) {
/*  45 */     return (i == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_102007_a(int i, ItemStack itemstack, int j) {
/*  50 */     if (i == 0)
/*     */     {
/*  52 */       return Ic2Items.uraniumIngot.func_77969_a(itemstack);
/*     */     }
/*  54 */     if (i == 1)
/*     */     {
/*  56 */       return isMatchingUraniumType(itemstack);
/*     */     }
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  64 */     return "Uranium Enricher";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/*  70 */     ItemStack item = this.inventory[0];
/*  71 */     if (item != null && item.func_77969_a(Ic2Items.uraniumIngot) && item.field_77994_a > 0 && this.type != -1 && this.amount >= 100) {
/*     */       
/*  73 */       ItemStack result = ItemReactorEnrichUranium.UraniumType.values()[this.type].getIngot();
/*  74 */       int use = getEnergyUseFromUranType();
/*  75 */       if (hasEnergy(use) && (this.inventory[2] == null || (this.inventory[2].func_77969_a(result) && (this.inventory[2]).field_77994_a < this.inventory[2].func_77976_d())))
/*     */       {
/*  77 */         useEnergy(use);
/*  78 */         this.uranProgress++;
/*  79 */         if (this.uranProgress >= 1000) {
/*     */           
/*  81 */           this.uranProgress = 0;
/*  82 */           this.amount -= 100;
/*  83 */           if (this.amount <= 0) {
/*     */             
/*  85 */             this.amount = 0;
/*  86 */             this.type = -1;
/*  87 */             getNetwork().updateTileGuiField((TileEntity)this, "type");
/*     */           } 
/*  89 */           if (this.inventory[2] == null) {
/*     */             
/*  91 */             this.inventory[2] = result.func_77946_l();
/*     */           }
/*  93 */           else if (this.inventory[2].func_77969_a(result)) {
/*     */             
/*  95 */             (this.inventory[2]).field_77994_a++;
/*     */           } 
/*  97 */           item.field_77994_a--;
/*  98 */           if (item.field_77994_a <= 0)
/*     */           {
/* 100 */             this.inventory[0] = null;
/*     */           }
/* 102 */           getNetwork().updateTileGuiField((TileEntity)this, "amount");
/*     */         } 
/* 104 */         getNetwork().updateTileGuiField((TileEntity)this, "uranProgress");
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 109 */     else if (this.uranProgress != 0) {
/*     */       
/* 111 */       this.uranProgress = 0;
/* 112 */       getNetwork().updateTileGuiField((TileEntity)this, "uranProgress");
/*     */     } 
/*     */     
/* 115 */     if (this.inventory[1] != null && (this.inventory[1]).field_77994_a > 0 && isMatchingUraniumType(this.inventory[1])) {
/*     */       
/* 117 */       ItemStack fuel = this.inventory[1];
/* 118 */       byte itemType = getTypeFromItem(fuel);
/* 119 */       int extra = getAmountFromType(itemType);
/* 120 */       if ((this.type == -1 || (this.type == itemType && this.amount + extra <= 1000)) && hasEnergy(20))
/*     */       {
/* 122 */         this.itemProgress++;
/* 123 */         useEnergy(20);
/* 124 */         if (this.itemProgress >= 100) {
/*     */           
/* 126 */           this.itemProgress = 0;
/* 127 */           fuel.field_77994_a--;
/* 128 */           if (fuel.field_77994_a <= 0)
/*     */           {
/* 130 */             this.inventory[1] = null;
/*     */           }
/* 132 */           if (this.type == -1) {
/*     */             
/* 134 */             this.type = itemType;
/* 135 */             getNetwork().updateTileGuiField((TileEntity)this, "type");
/*     */           } 
/* 137 */           this.amount += extra;
/* 138 */           if (this.amount > 1000)
/*     */           {
/* 140 */             this.amount = 1000;
/*     */           }
/* 142 */           getNetwork().updateTileGuiField((TileEntity)this, "amount");
/*     */         } 
/* 144 */         getNetwork().updateTileGuiField((TileEntity)this, "itemProgress");
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 149 */     else if (this.itemProgress != 0) {
/*     */       
/* 151 */       this.itemProgress = 0;
/* 152 */       getNetwork().updateTileGuiField((TileEntity)this, "itemProgress");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte getTypeFromItem(ItemStack fuel) {
/* 159 */     if (this.type != -1)
/*     */     {
/* 161 */       return this.type;
/*     */     }
/* 163 */     for (ItemReactorEnrichUranium.UraniumType uran : ItemReactorEnrichUranium.UraniumType.values()) {
/*     */       
/* 165 */       if (fuel.func_77969_a(uran.getItem()))
/*     */       {
/* 167 */         return (byte)uran.ordinal();
/*     */       }
/*     */     } 
/* 170 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getAmountFromType(byte par1) {
/* 175 */     switch (par1) {
/*     */       case 0:
/* 177 */         return 25;
/* 178 */       case 1: return 200;
/* 179 */       case 2: return 100;
/* 180 */       case 3: return 200;
/* 181 */       case 4: return 25;
/*     */     } 
/* 183 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isMatchingUraniumType(ItemStack par1) {
/* 188 */     if (par1 == null)
/*     */     {
/* 190 */       return false;
/*     */     }
/* 192 */     if (this.type == -1) {
/*     */       
/* 194 */       for (ItemReactorEnrichUranium.UraniumType type : ItemReactorEnrichUranium.UraniumType.values()) {
/*     */         
/* 196 */         if (par1.func_77969_a(type.getItem()))
/*     */         {
/* 198 */           return true;
/*     */         }
/*     */       } 
/* 201 */       return false;
/*     */     } 
/* 203 */     return par1.func_77969_a(ItemReactorEnrichUranium.UraniumType.values()[this.type].getItem());
/*     */   }
/*     */ 
/*     */   
/*     */   private int getEnergyUseFromUranType() {
/* 208 */     switch (this.type) {
/*     */       case 0:
/* 210 */         return 25;
/* 211 */       case 1: return 20;
/* 212 */       case 2: return 75;
/* 213 */       case 3: return 150;
/* 214 */       case 4: return 5;
/*     */     } 
/* 216 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 222 */     return (ContainerIC2)new ContainerUraniumEnricher(this, p0.field_71071_by);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 228 */     return "block.machine.gui.GuiUraniumEnricher";
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntityUraniumEnricher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */