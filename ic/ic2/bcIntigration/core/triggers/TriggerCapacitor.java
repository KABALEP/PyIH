/*     */ package ic2.bcIntigration.core.triggers;
/*     */ 
/*     */ import buildcraft.api.statements.IStatement;
/*     */ import buildcraft.api.statements.IStatementContainer;
/*     */ import buildcraft.api.statements.IStatementParameter;
/*     */ import buildcraft.api.statements.ITriggerExternal;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
/*     */ import ic2.core.block.machine.tileentity.TileEntityAdvancedMachine;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
/*     */ import ic2.core.block.wiring.TileEntityElectricBlock;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.client.renderer.texture.IIconRegister;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ 
/*     */ public class TriggerCapacitor
/*     */   implements ITriggerExternal
/*     */ {
/*     */   int action;
/*     */   
/*     */   public TriggerCapacitor(int action) {
/*  30 */     this.action = action;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatementParameter createParameter(int arg0) {
/*  36 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  42 */     String un = getUnlocalizedDistrciption();
/*  43 */     if (un == null)
/*     */     {
/*  45 */       return "This shouldn't happen";
/*     */     }
/*  47 */     return StatCollector.func_74838_a("gate." + un + ".name");
/*     */   }
/*     */ 
/*     */   
/*     */   private String getUnlocalizedDistrciption() {
/*  52 */     switch (this.action) {
/*     */       
/*     */       case 0:
/*  55 */         return "energyStorageEmpty";
/*     */       case 1:
/*  57 */         return "energyStorageHasEnergy";
/*     */       case 2:
/*  59 */         return "energyStorageSpace";
/*     */       case 3:
/*  61 */         return "energyStorageFull";
/*     */       case 4:
/*  63 */         return "energyStorageItemEmptyCharge";
/*     */       case 5:
/*  65 */         return "energyStorageItemPartlyCharge";
/*     */       case 6:
/*  67 */         return "energyStorageItemFullyCharged";
/*     */       case 7:
/*  69 */         return "energyStorageDrainingEmptyItem";
/*     */       case 8:
/*  71 */         return "energyStorageDrainingChargedItem";
/*     */       case 9:
/*  73 */         return "energyStorageDrainingFullyChargedItem";
/*     */     } 
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IIcon getIcon() {
/*  82 */     return Ic2Icons.getTexture("triggers")[getIconID()];
/*     */   }
/*     */ 
/*     */   
/*     */   private int getIconID() {
/*  87 */     switch (this.action) {
/*     */       
/*     */       case 0:
/*  90 */         return 0;
/*     */       case 1:
/*  92 */         return 1;
/*     */       case 2:
/*  94 */         return 2;
/*     */       case 3:
/*  96 */         return 3;
/*     */       case 4:
/*  98 */         return 4;
/*     */       case 5:
/* 100 */         return 5;
/*     */       case 6:
/* 102 */         return 6;
/*     */       case 7:
/* 104 */         return 9;
/*     */       case 8:
/* 106 */         return 7;
/*     */       case 9:
/* 108 */         return 8;
/*     */     } 
/* 110 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueTag() {
/* 116 */     return "trigger.EUCapacitor." + this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxParameters() {
/* 122 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int minParameters() {
/* 128 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerIcons(IIconRegister arg0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public IStatement rotateLeft() {
/* 140 */     return (IStatement)this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTriggerActive(TileEntity tile, ForgeDirection dir, IStatementContainer arg2, IStatementParameter[] arg3) {
/* 146 */     if (tile == null)
/*     */     {
/* 148 */       return false;
/*     */     }
/* 150 */     if (tile instanceof TileEntityElectricMachine) {
/*     */       
/* 152 */       TileEntityElectricMachine machine = (TileEntityElectricMachine)tile;
/* 153 */       boolean hasEnergy = (machine.energy > machine.maxInput);
/* 154 */       boolean hasRoom = (machine.energy < machine.maxEnergy - machine.maxInput);
/* 155 */       boolean dischargeEnergy = false;
/* 156 */       boolean dischargeRoom = false;
/* 157 */       if (machine.inventory[1] != null && machine.inventory[1].func_77973_b() instanceof IElectricItem) {
/*     */         
/* 159 */         IElectricItem ei = (IElectricItem)machine.inventory[1].func_77973_b();
/* 160 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(machine.inventory[1]);
/* 161 */         if (nbt.func_74762_e("charge") > 0)
/*     */         {
/* 163 */           dischargeEnergy = true;
/*     */         }
/* 165 */         if (nbt.func_74762_e("charge") < ei.getMaxCharge(machine.inventory[1]))
/*     */         {
/* 167 */           dischargeRoom = true;
/*     */         }
/*     */       }
/* 170 */       else if (machine.inventory[1] != null && machine.inventory[1].func_77973_b() instanceof ic2.core.item.ItemBatteryDischarged) {
/*     */         
/* 172 */         dischargeEnergy = false;
/* 173 */         dischargeRoom = true;
/*     */       }
/* 175 */       else if (this.action == 7 || this.action == 8 || this.action == 9) {
/*     */         
/* 177 */         return false;
/*     */       } 
/* 179 */       switch (this.action) {
/*     */         
/*     */         case 0:
/* 182 */           return !hasEnergy;
/*     */         case 1:
/* 184 */           return hasEnergy;
/*     */         case 2:
/* 186 */           return hasRoom;
/*     */         case 3:
/* 188 */           return !hasRoom;
/*     */         case 7:
/* 190 */           return !dischargeEnergy;
/*     */         case 8:
/* 192 */           return (dischargeEnergy && dischargeRoom);
/*     */         case 9:
/* 194 */           return !dischargeRoom;
/*     */       } 
/*     */     
/* 197 */     } else if (tile instanceof TileEntityAdvancedMachine) {
/*     */       
/* 199 */       TileEntityAdvancedMachine machine = (TileEntityAdvancedMachine)tile;
/* 200 */       boolean hasEnergy = (machine.energy > machine.maxInput);
/* 201 */       boolean hasRoom = (machine.energy < machine.maxEnergy - machine.maxInput);
/* 202 */       boolean dischargeEnergy = false;
/* 203 */       boolean dischargeRoom = false;
/* 204 */       if (machine.inventory[0] != null && machine.inventory[0].func_77973_b() instanceof IElectricItem) {
/*     */         
/* 206 */         IElectricItem ei = (IElectricItem)machine.inventory[0].func_77973_b();
/* 207 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(machine.inventory[0]);
/* 208 */         if (nbt.func_74762_e("charge") > 0)
/*     */         {
/* 210 */           dischargeEnergy = true;
/*     */         }
/* 212 */         if (nbt.func_74762_e("charge") < ei.getMaxCharge(machine.inventory[0]))
/*     */         {
/* 214 */           dischargeRoom = true;
/*     */         }
/*     */       }
/* 217 */       else if (machine.inventory[0] != null && machine.inventory[0].func_77973_b() instanceof ic2.core.item.ItemBatteryDischarged) {
/*     */         
/* 219 */         dischargeEnergy = false;
/* 220 */         dischargeRoom = true;
/*     */       }
/* 222 */       else if (this.action == 7 || this.action == 8 || this.action == 9) {
/*     */         
/* 224 */         return false;
/*     */       } 
/* 226 */       switch (this.action) {
/*     */         
/*     */         case 0:
/* 229 */           return !hasEnergy;
/*     */         case 1:
/* 231 */           return hasEnergy;
/*     */         case 2:
/* 233 */           return hasRoom;
/*     */         case 3:
/* 235 */           return !hasRoom;
/*     */         case 7:
/* 237 */           return !dischargeEnergy;
/*     */         case 8:
/* 239 */           return (dischargeEnergy && dischargeRoom);
/*     */         case 9:
/* 241 */           return !dischargeRoom;
/*     */       } 
/*     */     
/* 244 */     } else if (tile instanceof TileEntityBaseGenerator) {
/*     */       
/* 246 */       TileEntityBaseGenerator gen = (TileEntityBaseGenerator)tile;
/* 247 */       boolean hasEnergy = (gen.storage > 0);
/* 248 */       boolean hasRoom = (gen.storage < gen.maxStorage);
/* 249 */       boolean chargeEnergy = false;
/* 250 */       boolean chargeRoom = false;
/* 251 */       if (gen.inventory[0] != null && gen.inventory[0].func_77973_b() instanceof IElectricItem) {
/*     */         
/* 253 */         IElectricItem ei = (IElectricItem)gen.inventory[0].func_77973_b();
/* 254 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(gen.inventory[0]);
/* 255 */         if (nbt.func_74762_e("charge") > 0)
/*     */         {
/* 257 */           chargeEnergy = true;
/*     */         }
/* 259 */         if (nbt.func_74762_e("charge") < ei.getMaxCharge(gen.inventory[0]))
/*     */         {
/* 261 */           chargeRoom = true;
/*     */         }
/*     */       }
/* 264 */       else if (gen.inventory[0] != null && gen.inventory[0].func_77973_b() instanceof ic2.core.item.ItemBatteryDischarged) {
/*     */         
/* 266 */         chargeEnergy = false;
/* 267 */         chargeRoom = true;
/*     */       }
/* 269 */       else if (this.action == 4 || this.action == 5 || this.action == 6) {
/*     */         
/* 271 */         return false;
/*     */       } 
/* 273 */       switch (this.action) {
/*     */         
/*     */         case 0:
/* 276 */           return !hasEnergy;
/*     */         case 1:
/* 278 */           return hasEnergy;
/*     */         case 2:
/* 280 */           return hasRoom;
/*     */         case 3:
/* 282 */           return !hasRoom;
/*     */         case 4:
/* 284 */           return !chargeEnergy;
/*     */         case 5:
/* 286 */           return (chargeEnergy && chargeRoom);
/*     */         case 6:
/* 288 */           return !chargeRoom;
/*     */       } 
/*     */     
/* 291 */     } else if (tile instanceof TileEntityElectricBlock) {
/*     */       
/* 293 */       TileEntityElectricBlock block = (TileEntityElectricBlock)tile;
/* 294 */       boolean hasEnergy = (block.energy > block.output);
/* 295 */       boolean hasRoom = (block.energy < block.maxStorage);
/* 296 */       boolean chargeEnergy = false;
/* 297 */       boolean chargeRoom = false;
/* 298 */       boolean dischargeEnergy = false;
/* 299 */       boolean dischargeRoom = false;
/* 300 */       if (block.inventory[0] != null && block.inventory[0].func_77973_b() instanceof IElectricItem) {
/*     */         
/* 302 */         IElectricItem ei = (IElectricItem)block.inventory[0].func_77973_b();
/* 303 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(block.inventory[0]);
/* 304 */         if (nbt.func_74762_e("charge") > 0)
/*     */         {
/* 306 */           chargeEnergy = true;
/*     */         }
/* 308 */         if (nbt.func_74762_e("charge") < ei.getMaxCharge(block.inventory[0]))
/*     */         {
/* 310 */           chargeRoom = true;
/*     */         }
/*     */       }
/* 313 */       else if (block.inventory[0] != null && block.inventory[0].func_77973_b() instanceof ic2.core.item.ItemBatteryDischarged) {
/*     */         
/* 315 */         chargeEnergy = false;
/* 316 */         chargeRoom = true;
/*     */       }
/* 318 */       else if (this.action == 4 || this.action == 5 || this.action == 6) {
/*     */         
/* 320 */         return false;
/*     */       } 
/* 322 */       if (block.inventory[1] != null && block.inventory[1].func_77973_b() instanceof IElectricItem) {
/*     */         
/* 324 */         IElectricItem ei = (IElectricItem)block.inventory[1].func_77973_b();
/* 325 */         NBTTagCompound nbt = StackUtil.getOrCreateNbtData(block.inventory[1]);
/* 326 */         if (nbt.func_74762_e("charge") > 0)
/*     */         {
/* 328 */           dischargeEnergy = true;
/*     */         }
/* 330 */         if (nbt.func_74762_e("charge") < ei.getMaxCharge(block.inventory[0]))
/*     */         {
/* 332 */           dischargeRoom = true;
/*     */         }
/*     */       }
/* 335 */       else if (block.inventory[1] != null && block.inventory[1].func_77973_b() instanceof ic2.core.item.ItemBatteryDischarged) {
/*     */         
/* 337 */         dischargeEnergy = false;
/* 338 */         dischargeRoom = true;
/*     */       }
/* 340 */       else if (this.action == 7 || this.action == 8 || this.action == 9) {
/*     */         
/* 342 */         return false;
/*     */       } 
/* 344 */       switch (this.action) {
/*     */         case 0:
/* 346 */           return !hasEnergy;
/* 347 */         case 1: return hasEnergy;
/* 348 */         case 2: return hasRoom;
/* 349 */         case 3: return !hasRoom;
/* 350 */         case 4: return !chargeEnergy;
/* 351 */         case 5: return (chargeEnergy && chargeRoom);
/* 352 */         case 6: return !chargeRoom;
/* 353 */         case 7: return !dischargeEnergy;
/* 354 */         case 8: return (dischargeEnergy && dischargeRoom);
/* 355 */         case 9: return !dischargeRoom;
/*     */       } 
/*     */     
/*     */     } 
/* 359 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\bcIntigration\core\triggers\TriggerCapacitor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */