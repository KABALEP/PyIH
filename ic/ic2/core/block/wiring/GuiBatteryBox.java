/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.GuiIconButton;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Items;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class GuiBatteryBox
/*     */   extends GuiContainer
/*     */ {
/*     */   ContainerBatteryBox box;
/*     */   String name;
/*     */   String out;
/*     */   
/*     */   public GuiBatteryBox(ContainerBatteryBox box) {
/*  28 */     super((Container)box);
/*  29 */     this.box = box;
/*  30 */     this.field_147000_g = 184;
/*  31 */     this.name = StatCollector.func_74838_a("blockBatteryBox.name");
/*  32 */     this.out = StatCollector.func_74838_a("container.batteryBox.out.name");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146979_b(int par1, int par2) {
/*  37 */     this.field_146289_q.func_78276_b(this.name, this.field_146999_f / 2 - this.field_146289_q.func_78256_a(this.name) / 2, 6, 4210752);
/*  38 */     this.field_146289_q.func_78276_b(I18n.func_135052_a("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
/*     */     
/*  40 */     String change = "";
/*  41 */     boolean charging = false;
/*  42 */     boolean draining = false;
/*  43 */     if (this.box.box.currentChange > 0) {
/*     */       
/*  45 */       int reqTime = this.box.box.maxStorage - this.box.box.currentStored;
/*  46 */       reqTime /= this.box.box.currentChange;
/*  47 */       change = getTime(reqTime);
/*  48 */       charging = true;
/*     */     }
/*  50 */     else if (this.box.box.currentChange < 0) {
/*     */       
/*  52 */       int reqTime = -(this.box.box.currentStored / this.box.box.currentChange);
/*  53 */       change = getTime(reqTime);
/*  54 */       draining = true;
/*     */     } 
/*  56 */     if (!charging && !draining) {
/*     */       
/*  58 */       change = StatCollector.func_74838_a("container.batteryBox.noChange.name");
/*     */     }
/*  60 */     else if (charging) {
/*     */       
/*  62 */       change = StatCollector.func_74837_a("container.batteryBox.charging.name", new Object[] { change });
/*     */     }
/*  64 */     else if (draining) {
/*     */       
/*  66 */       change = StatCollector.func_74837_a("container.batteryBox.discharging.name", new Object[] { change });
/*     */     } 
/*     */     
/*  69 */     this.field_146289_q.func_78276_b(change, 8, 45, 4210752);
/*     */     
/*  71 */     this.field_146289_q.func_78276_b(StatCollector.func_74838_a("container.electricBlock.level.name"), 8, 60, 4210752);
/*  72 */     int powerlevel = this.box.box.currentStored;
/*  73 */     if (powerlevel > this.box.box.maxStorage)
/*     */     {
/*  75 */       powerlevel = this.box.box.maxStorage;
/*     */     }
/*  77 */     this.field_146289_q.func_78276_b("" + powerlevel, 8, 70, 4210752);
/*  78 */     this.field_146289_q.func_78276_b("/", 8, 80, 4210752);
/*  79 */     this.field_146289_q.func_78276_b("" + this.box.box.maxStorage, 14, 80, 4210752);
/*  80 */     this.field_146289_q.func_78276_b(this.out, 80, 70, 4210752);
/*  81 */     if (this.box.box.stationMode == 0) {
/*     */       
/*  83 */       this.field_146289_q.func_78276_b("0 EU/t", 100, 70, 4210752);
/*     */ 
/*     */     
/*     */     }
/*  87 */     else if (this.box.box.sendingMode == 0) {
/*     */       
/*  89 */       int energy = 0;
/*  90 */       if (this.box.box.currentDraw != -1)
/*     */       {
/*  92 */         energy = (int)EnergyNet.instance.getPowerFromTier(this.box.box.batteryTiers[this.box.box.currentDraw]);
/*     */       }
/*  94 */       this.field_146289_q.func_78276_b(energy + " EU/t", 100, 70, 4210752);
/*     */     }
/*  96 */     else if (this.box.box.sendingMode == 1) {
/*     */       
/*  98 */       int totalEnergy = 0;
/*  99 */       for (int i = 0; i < 9; i++) {
/*     */         
/* 101 */         if (this.box.box.providers[i] && ElectricItem.manager.getCharge(this.box.box.func_70301_a(i)) > 0.0D)
/*     */         {
/* 103 */           totalEnergy = (int)(totalEnergy + EnergyNet.instance.getPowerFromTier(this.box.box.batteryTiers[i]));
/*     */         }
/*     */       } 
/* 106 */       this.field_146289_q.func_78276_b(totalEnergy + " EU/t", 100, 70, 4210752);
/*     */     }
/*     */     else {
/*     */       
/* 110 */       HashMap<Integer, Integer> tiers = new HashMap<>();
/* 111 */       for (int i = 0; i < this.box.box.batteryTiers.length; i++) {
/*     */         
/* 113 */         int tier = this.box.box.batteryTiers[i];
/* 114 */         if (this.box.box.providers[i] && ElectricItem.manager.getCharge(this.box.box.func_70301_a(i)) > 0.0D)
/*     */         {
/* 116 */           if (!tiers.containsKey(Integer.valueOf(tier))) {
/*     */             
/* 118 */             tiers.put(Integer.valueOf(tier), Integer.valueOf(1));
/*     */           }
/*     */           else {
/*     */             
/* 122 */             tiers.put(Integer.valueOf(tier), Integer.valueOf(1 + ((Integer)tiers.get(Integer.valueOf(tier))).intValue()));
/*     */           } 
/*     */         }
/*     */       } 
/* 126 */       String result = "";
/* 127 */       if (tiers.isEmpty())
/*     */       {
/* 129 */         result = " 0 EU/t";
/*     */       }
/* 131 */       for (int j = 0; j <= 100 && !tiers.isEmpty(); j++) {
/*     */         
/* 133 */         if (tiers.size() > 1) {
/*     */           
/* 135 */           if (tiers.containsKey(Integer.valueOf(j)))
/*     */           {
/* 137 */             int count = ((Integer)tiers.remove(Integer.valueOf(j))).intValue();
/* 138 */             result = result + " " + count + "x " + EnergyNet.instance.getPowerFromTier(j);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 143 */           Map.Entry<Integer, Integer> entry = tiers.entrySet().iterator().next();
/* 144 */           result = result + " " + entry.getValue() + "x " + EnergyNet.instance.getPowerFromTier(((Integer)entry.getKey()).intValue()) + " EU/t";
/* 145 */           tiers.clear();
/*     */         } 
/*     */       } 
/* 148 */       this.field_146289_q.func_78279_b(result, 97, 70, 70, 4210752);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73866_w_() {
/* 156 */     super.func_73866_w_();
/* 157 */     GuiIconButton guiIconButton = new GuiIconButton(0, (this.field_146294_l - this.field_146999_f) / 2 + 152, (this.field_146295_m - this.field_147000_g) / 2 + 3, 20, 20, Ic2Items.glassFiberCableItem, true);
/* 158 */     this.field_146292_n.add(guiIconButton);
/*     */     
/* 160 */     guiIconButton = new GuiIconButton(1, (this.field_146294_l - this.field_146999_f) / 2 + 130, (this.field_146295_m - this.field_147000_g) / 2 + 3, 20, 20, Ic2Items.chargedReBattery, true);
/* 161 */     this.field_146292_n.add(guiIconButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146284_a(GuiButton par1) {
/* 168 */     int id = par1.field_146127_k;
/* 169 */     if (id == 0 || id == 1)
/*     */     {
/* 171 */       ((NetworkManager)IC2.network.get()).initiateClientTileEntityEvent((TileEntity)this.box.box, id);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float par1, int par2, int par3) {
/* 177 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 178 */     this.field_146297_k.func_110434_K().func_110577_a(new ResourceLocation("ic2", "textures/guiSprites/GUIBatteryBox.png"));
/* 179 */     int k = (this.field_146294_l - this.field_146999_f) / 2;
/* 180 */     int l = (this.field_146295_m - this.field_147000_g) / 2;
/* 181 */     func_73729_b(k, l, 0, 0, this.field_146999_f, this.field_147000_g);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTime(int ticks) {
/* 186 */     int TotalTime = ticks / 20;
/*     */     
/* 188 */     if (TotalTime <= 0)
/*     */     {
/* 190 */       return "0";
/*     */     }
/*     */     
/* 193 */     String Sekunde = "" + (TotalTime % 60);
/* 194 */     String Minute = "" + (TotalTime / 60 % 60);
/* 195 */     String Stunde = "" + (TotalTime / 60 / 60 % 24);
/* 196 */     String Tag = "" + (TotalTime / 60 / 60 / 24 % 30);
/* 197 */     if (Sekunde.length() == 1)
/*     */     {
/* 199 */       Sekunde = "0" + Sekunde;
/*     */     }
/* 201 */     if (Minute.length() == 1)
/*     */     {
/* 203 */       Minute = "0" + Minute;
/*     */     }
/* 205 */     if (Stunde.length() == 1)
/*     */     {
/* 207 */       Stunde = "0" + Stunde;
/*     */     }
/* 209 */     if (Tag.length() == 1)
/*     */     {
/* 211 */       Tag = "0" + Tag;
/*     */     }
/*     */     
/* 214 */     String result = "";
/* 215 */     if (Integer.valueOf(Tag).intValue() > 0)
/*     */     {
/* 217 */       result = result + Tag + ":";
/*     */     }
/* 219 */     if (Integer.valueOf(Tag).intValue() > 0 || Integer.valueOf(Stunde).intValue() > 0)
/*     */     {
/* 221 */       result = result + Stunde + ":";
/*     */     }
/* 223 */     if (Integer.valueOf(Tag).intValue() > 0 || Integer.valueOf(Stunde).intValue() > 0 || Integer.valueOf(Minute).intValue() > 0)
/*     */     {
/* 225 */       result = result + Minute + ":";
/*     */     }
/* 227 */     if (Integer.valueOf(Tag).intValue() > 0 || Integer.valueOf(Stunde).intValue() > 0 || Integer.valueOf(Minute).intValue() > 0 || Integer.valueOf(Sekunde).intValue() > 0)
/*     */     {
/* 229 */       result = result + Sekunde + "";
/*     */     }
/* 231 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\wiring\GuiBatteryBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */