/*     */ package ic2.core.block.machine.logic.encoder.impl;
/*     */ 
/*     */ import ic2.core.block.machine.logic.PlannerRegistry;
/*     */ import ic2.core.block.machine.logic.ReactorLogicBase;
/*     */ import ic2.core.block.machine.logic.encoder.ByteShifter;
/*     */ import ic2.core.block.machine.logic.encoder.IEncoder;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ public class TalonEncoder
/*     */   implements IEncoder
/*     */ {
/*     */   public NBTTagCompound createDecodedData(String data) {
/*  19 */     if (!data.startsWith("http://www.talonfiremage.pwp.blueyonder.co.uk/v3/reactorplanner.html"))
/*     */     {
/*  21 */       return null;
/*     */     }
/*  23 */     if (data.indexOf("?") == -1)
/*     */     {
/*  25 */       return null;
/*     */     }
/*  27 */     ByteShifter shift = new ByteShifter(data.substring(data.indexOf('?') + 1));
/*  28 */     NBTTagCompound nbt = new NBTTagCompound();
/*  29 */     nbt.func_74768_a("Heat", shift.readInt(10) * 100);
/*  30 */     NBTTagList list = new NBTTagList();
/*  31 */     for (int x = 8; x >= 0; x--) {
/*     */       
/*  33 */       for (int y = 5; y >= 0; y--) {
/*     */         
/*  35 */         int nextType = shift.readInt(7);
/*  36 */         if (nextType != 0) {
/*     */ 
/*     */ 
/*     */           
/*  40 */           int stackSize = (nextType == 0) ? 0 : 1;
/*  41 */           if (nextType > 64) {
/*     */             
/*  43 */             stackSize = nextType - 64 + 1;
/*  44 */             nextType = shift.readInt(7);
/*     */           } 
/*  46 */           NBTTagCompound nbtData = new NBTTagCompound();
/*  47 */           nbtData.func_74768_a("Slot", y * 9 + x);
/*  48 */           nbtData.func_74777_a("ID", convertID(nextType));
/*  49 */           nbtData.func_74774_a("StackSize", (byte)stackSize);
/*  50 */           list.func_74742_a((NBTBase)nbtData);
/*     */         } 
/*     */       } 
/*  53 */     }  nbt.func_74782_a("ReactorData", (NBTBase)list);
/*  54 */     nbt.func_74774_a("Size", (byte)shift.readInt(3));
/*  55 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processData(NBTTagCompound decodedData, TileEntityReactorPlanner planner) {
/*  61 */     planner.isSteamReactor = false;
/*  62 */     planner.reactorSize = decodedData.func_74762_e("Size");
/*  63 */     ReactorLogicBase base = planner.getReactorLogic();
/*  64 */     base.clear();
/*  65 */     boolean valid = false;
/*  66 */     NBTTagList list = decodedData.func_150295_c("ReactorData", 10);
/*  67 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/*  69 */       NBTTagCompound nbt = list.func_150305_b(i);
/*  70 */       ItemStack stack = PlannerRegistry.getComponentFromID(nbt.func_74765_d("ID"));
/*  71 */       if (stack != null) {
/*     */ 
/*     */ 
/*     */         
/*  75 */         int slot = nbt.func_74762_e("Slot");
/*  76 */         base.func_70299_a(slot, StackUtil.copyWithSize(stack, nbt.func_74762_e("StackSize")));
/*  77 */         valid = true;
/*     */       } 
/*  79 */     }  if (valid) {
/*     */       
/*  81 */       (planner.getUserSettings()).startingHeat = decodedData.func_74762_e("Heat");
/*  82 */       base.validate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String createEncodedData(TileEntityReactorPlanner planner) {
/*  89 */     ReactorLogicBase base = planner.getReactorLogic();
/*  90 */     ByteShifter shifter = new ByteShifter();
/*  91 */     shifter.writeInteger(0, 16);
/*  92 */     shifter.writeInteger(planner.reactorSize, 3);
/*  93 */     for (int x = 0; x < 9; x++) {
/*     */       
/*  95 */       for (int y = 0; y < 6; y++) {
/*     */         
/*  97 */         ItemStack stack = base.getMatrixItem(x, y);
/*  98 */         if (stack == null) {
/*     */           
/* 100 */           shifter.writeInteger(0, 7);
/*     */         } else {
/*     */           
/* 103 */           int id = convertBack(PlannerRegistry.getID(stack));
/* 104 */           shifter.writeInteger(id, 7);
/* 105 */           if (stack.field_77994_a > 1)
/*     */           {
/* 107 */             shifter.writeInteger(64 + stack.field_77994_a - 1, 7); } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 111 */     shifter.writeInteger((planner.getUserSettings()).startingHeat / 100, 10);
/* 112 */     return getURL() + "?" + shifter.getDataCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 118 */     return "Talon";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBitLimit() {
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBitLimit() {
/* 130 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public short convertID(int id) {
/* 135 */     switch (id) {
/*     */       
/*     */       case 1:
/* 138 */         return 0;
/*     */       case 2:
/* 140 */         return 1;
/*     */       case 3:
/* 142 */         return 2;
/*     */       case 4:
/* 144 */         return 36;
/*     */       case 5:
/* 146 */         return 34;
/*     */       case 6:
/* 148 */         return 35;
/*     */       case 7:
/* 150 */         return 18;
/*     */       case 8:
/* 152 */         return 19;
/*     */       case 9:
/* 154 */         return 21;
/*     */       case 10:
/* 156 */         return 20;
/*     */       case 11:
/* 158 */         return 26;
/*     */       case 12:
/* 160 */         return 15;
/*     */       case 13:
/* 162 */         return 16;
/*     */       case 14:
/* 164 */         return 27;
/*     */       case 15:
/* 166 */         return 28;
/*     */       case 16:
/* 168 */         return 29;
/*     */       case 17:
/* 170 */         return 30;
/*     */       case 18:
/* 172 */         return 31;
/*     */       case 19:
/* 174 */         return 33;
/*     */       case 20:
/* 176 */         return 32;
/*     */       case 21:
/* 178 */         return 12;
/*     */       case 22:
/* 180 */         return 13;
/*     */       case 23:
/* 182 */         return 14;
/*     */       case 24:
/* 184 */         return 17;
/*     */     } 
/* 186 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int convertBack(short id) {
/* 191 */     switch (id) {
/*     */       
/*     */       case 0:
/* 194 */         return 1;
/*     */       case 1:
/* 196 */         return 2;
/*     */       case 2:
/* 198 */         return 3;
/*     */       case 36:
/* 200 */         return 4;
/*     */       case 34:
/* 202 */         return 5;
/*     */       case 35:
/* 204 */         return 6;
/*     */       case 18:
/* 206 */         return 7;
/*     */       case 19:
/* 208 */         return 8;
/*     */       case 21:
/* 210 */         return 9;
/*     */       case 20:
/* 212 */         return 10;
/*     */       case 26:
/* 214 */         return 11;
/*     */       case 15:
/* 216 */         return 12;
/*     */       case 16:
/* 218 */         return 13;
/*     */       case 27:
/* 220 */         return 14;
/*     */       case 28:
/* 222 */         return 15;
/*     */       case 29:
/* 224 */         return 16;
/*     */       case 30:
/* 226 */         return 17;
/*     */       case 31:
/* 228 */         return 18;
/*     */       case 33:
/* 230 */         return 19;
/*     */       case 32:
/* 232 */         return 20;
/*     */       case 12:
/* 234 */         return 21;
/*     */       case 13:
/* 236 */         return 22;
/*     */       case 14:
/* 238 */         return 23;
/*     */       case 17:
/* 240 */         return 24;
/*     */     } 
/* 242 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getURL() {
/* 247 */     return "http://www.talonfiremage.pwp.blueyonder.co.uk/v3/reactorplanner.html";
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\encoder\impl\TalonEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */