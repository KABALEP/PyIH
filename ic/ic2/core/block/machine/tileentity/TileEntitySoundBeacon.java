/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.IMachineUpgradeItem;
/*     */ import ic2.api.tile.IMachine;
/*     */ import ic2.core.ContainerIC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioPosition;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.machine.container.ContainerSoundBeacon;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class TileEntitySoundBeacon
/*     */   extends TileEntityElecMachine
/*     */   implements IHasGui
/*     */ {
/*  26 */   public static List<TileEntitySoundBeacon> beacon = new ArrayList<>();
/*     */   
/*     */   public FakeMachine machine;
/*     */   
/*  30 */   public int tileRange = 5;
/*  31 */   public int itemRange = 5;
/*  32 */   public int armorRange = 5;
/*     */   
/*  34 */   public float tileEffect = 1.0F;
/*  35 */   public float itemEffect = 1.0F;
/*  36 */   public float armorEffect = 1.0F;
/*     */ 
/*     */   
/*     */   public TileEntitySoundBeacon() {
/*  40 */     super(9, -1, 20000, 32, 1);
/*  41 */     addNetworkFields(new String[] { "tileRange", "itemRange", "armorRange", "tileEffect", "itemEffect", "armorEffect" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEnergyUsage() {
/*  47 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] func_94128_d(int p_94128_1_) {
/*  53 */     return new int[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpdate() {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String func_145825_b() {
/*  65 */     return "Sound Beacon";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/*  71 */     super.func_70296_d();
/*  72 */     this.machine = new FakeMachine(this, Arrays.asList(new IMachine.UpgradeType[] { IMachine.UpgradeType.Sounds }));
/*  73 */     this.tileRange = 5;
/*  74 */     this.itemRange = 5;
/*  75 */     this.armorRange = 5;
/*     */     
/*  77 */     this.tileEffect = 1.0F;
/*  78 */     this.itemEffect = 1.0F;
/*  79 */     this.armorEffect = 1.0F;
/*  80 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  82 */       for (int x = 0; x < 3; x++) {
/*     */         
/*  84 */         int slot = i * 3 + x;
/*  85 */         ItemStack item = this.inventory[slot];
/*  86 */         if (item != null)
/*     */         {
/*     */ 
/*     */           
/*  90 */           if (i == 0) {
/*     */             
/*  92 */             if (item.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */               
/*  94 */               this.tileEffect = (float)(this.tileEffect * Math.pow(((IMachineUpgradeItem)item.func_77973_b()).getSoundVolumeMultiplier(item, this.machine), item.field_77994_a));
/*     */             }
/*  96 */             else if (item.func_77973_b() instanceof ic2.core.item.ItemChargePadUpgrade) {
/*     */               
/*  98 */               int meta = item.func_77960_j();
/*  99 */               if (meta == 9) { this.tileRange += 2; }
/* 100 */               else if (meta == 10) { this.tileRange += 5; }
/* 101 */               else if (meta == 11) { this.tileRange += 10; }
/*     */             
/*     */             } 
/* 104 */           } else if (i == 1) {
/*     */             
/* 106 */             if (item.func_77973_b() instanceof IMachineUpgradeItem)
/*     */             {
/* 108 */               this.itemEffect = (float)(this.itemEffect * Math.pow(((IMachineUpgradeItem)item.func_77973_b()).getSoundVolumeMultiplier(item, this.machine), item.field_77994_a));
/*     */             }
/* 110 */             else if (item.func_77973_b() instanceof ic2.core.item.ItemChargePadUpgrade)
/*     */             {
/* 112 */               int meta = item.func_77960_j();
/* 113 */               if (meta == 9) { this.itemRange += 2; }
/* 114 */               else if (meta == 10) { this.itemRange += 5; }
/* 115 */               else if (meta == 11) { this.itemRange += 10; }
/*     */ 
/*     */             
/*     */             }
/*     */           
/* 120 */           } else if (item.func_77973_b() instanceof IMachineUpgradeItem) {
/*     */             
/* 122 */             this.armorEffect = (float)(this.armorEffect * Math.pow(((IMachineUpgradeItem)item.func_77973_b()).getSoundVolumeMultiplier(item, this.machine), item.field_77994_a));
/*     */           }
/* 124 */           else if (item.func_77973_b() instanceof ic2.core.item.ItemChargePadUpgrade) {
/*     */             
/* 126 */             int meta = item.func_77960_j();
/* 127 */             if (meta == 9) { this.armorRange += 2; }
/* 128 */             else if (meta == 10) { this.armorRange += 5; }
/* 129 */             else if (meta == 11) { this.armorRange += 10; }
/*     */           
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 134 */     getNetwork().updateTileEntityField((TileEntity)this, "tileRange");
/* 135 */     getNetwork().updateTileEntityField((TileEntity)this, "itemRange");
/* 136 */     getNetwork().updateTileEntityField((TileEntity)this, "armorRange");
/* 137 */     getNetwork().updateTileEntityField((TileEntity)this, "tileEffect");
/* 138 */     getNetwork().updateTileEntityField((TileEntity)this, "itemEffect");
/* 139 */     getNetwork().updateTileEntityField((TileEntity)this, "armorEffect");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145845_h() {
/* 145 */     if (this.field_145850_b.func_82737_E() % 5L != 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 149 */     if (isRendering()) {
/*     */       
/* 151 */       if (this.field_145850_b.func_82737_E() % 50L != 0L) {
/*     */         return;
/*     */       }
/*     */       
/* 155 */       addIfNeeded();
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     if (!getActive()) {
/*     */       
/* 161 */       if (this.energy >= 5)
/*     */       {
/* 163 */         useEnergy(5);
/* 164 */         setActive(true);
/*     */       }
/*     */     
/* 167 */     } else if (getActive()) {
/*     */       
/* 169 */       if (this.energy >= 5) {
/*     */         
/* 171 */         useEnergy(5);
/*     */         return;
/*     */       } 
/* 174 */       setActive(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void addIfNeeded() {
/* 181 */     Minecraft mc = Minecraft.func_71410_x();
/* 182 */     if (mc.field_71441_e == this.field_145850_b)
/*     */     {
/* 184 */       if (!beacon.contains(this))
/*     */       {
/* 186 */         beacon.add(this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 194 */     super.onLoaded();
/* 195 */     if (isRendering()) {
/*     */       
/* 197 */       addTile(this);
/*     */     }
/*     */     else {
/*     */       
/* 201 */       func_70296_d();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUnloaded() {
/* 208 */     if (isRendering())
/*     */     {
/* 210 */       removeTile(this);
/*     */     }
/* 212 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addTile(TileEntitySoundBeacon par1) {
/* 217 */     beacon.add(par1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void removeTile(TileEntitySoundBeacon par1) {
/* 222 */     beacon.remove(par1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateList(World world) {
/* 227 */     if (beacon.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 231 */     if (world == null) {
/*     */       
/* 233 */       beacon = new ArrayList<>();
/*     */       
/*     */       return;
/*     */     } 
/* 237 */     for (int i = 0; i < beacon.size(); i++) {
/*     */       
/* 239 */       TileEntitySoundBeacon tile = beacon.get(i);
/* 240 */       if (tile.func_145831_w() != world)
/*     */       {
/* 242 */         beacon.remove(i--);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInRange(AudioPosition position, PositionSpec spec) {
/* 249 */     int range = getRange(spec);
/* 250 */     int minX = this.field_145851_c - range;
/* 251 */     int minY = this.field_145848_d - range;
/* 252 */     int minZ = this.field_145849_e - range;
/* 253 */     int maxX = this.field_145851_c + range;
/* 254 */     int maxY = this.field_145848_d + range;
/* 255 */     int maxZ = this.field_145849_e + range;
/* 256 */     if (minX <= position.x && maxX >= position.x && minY <= position.y && maxY >= position.y && minZ <= position.z && maxZ >= position.z)
/*     */     {
/* 258 */       return true;
/*     */     }
/* 260 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRange(PositionSpec spec) {
/* 265 */     switch (spec) {
/*     */       case Backpack:
/* 267 */         return this.armorRange;
/* 268 */       case Center: return this.tileRange;
/* 269 */       case Hand: return this.itemRange;
/* 270 */     }  return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getEffect(PositionSpec spec) {
/* 276 */     switch (spec) {
/*     */       case Backpack:
/* 278 */         return this.armorEffect;
/* 279 */       case Center: return this.tileEffect;
/* 280 */       case Hand: return this.itemEffect;
/* 281 */     }  return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerIC2 getGuiContainer(EntityPlayer p0) {
/* 288 */     return (ContainerIC2)new ContainerSoundBeacon(this, p0.field_71071_by);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiClassName(EntityPlayer p0) {
/* 294 */     return "block.machine.gui.GuiSoundBeacon";
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer p0) {}
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\tileentity\TileEntitySoundBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */