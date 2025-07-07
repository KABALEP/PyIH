/*     */ package ic2.core.block.machine.logic.encoder.impl;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLLog;
/*     */ import ic2.core.block.machine.logic.PlannerRegistry;
/*     */ import ic2.core.block.machine.logic.ReactorLogicBase;
/*     */ import ic2.core.block.machine.logic.encoder.ByteShifter;
/*     */ import ic2.core.block.machine.logic.encoder.IEncoder;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagIntArray;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class V1Encoder
/*     */   implements IEncoder
/*     */ {
/*     */   public NBTTagCompound createDecodedData(String data) {
/*  28 */     ByteShifter shifter = new ByteShifter(data);
/*  29 */     if (shifter.readInt(6) != 63)
/*     */     {
/*  31 */       return null;
/*     */     }
/*  33 */     if (shifter.readInt(5) != 1)
/*     */     {
/*  35 */       return null;
/*     */     }
/*  37 */     NBTTagCompound nbt = new NBTTagCompound();
/*  38 */     nbt.func_74757_a("Steam", shifter.readBoolean());
/*  39 */     nbt.func_74768_a("Size", shifter.readInt(3));
/*  40 */     nbt.func_74768_a("MaxHeat", shifter.readInt(20));
/*  41 */     NBTTagList list = new NBTTagList();
/*  42 */     int expectedNameLenght = shifter.readInt(6);
/*  43 */     StringBuilder name = new StringBuilder(63);
/*  44 */     for (int i = expectedNameLenght; i > 0; i--)
/*     */     {
/*  46 */       name.append((char)shifter.readChar());
/*     */     }
/*  48 */     FMLLog.getLogger().info("Plan Name: " + name.reverse().toString());
/*  49 */     int expectedTypes = shifter.readInt(6);
/*  50 */     for (int j = 0; j < expectedTypes; j++) {
/*     */       
/*  52 */       NBTTagCompound entry = new NBTTagCompound();
/*  53 */       entry.func_74777_a("ID", (short)shifter.readInt(getBitLimit()));
/*  54 */       NBTTagList slotList = new NBTTagList();
/*  55 */       int expectedSlots = shifter.readInt(6);
/*  56 */       for (int slot = 0; slot < expectedSlots; slot++) {
/*     */         
/*  58 */         int currentSlot = shifter.readInt(6);
/*  59 */         int stackSize = shifter.readInt(6) + 1;
/*  60 */         slotList.func_74742_a((NBTBase)new NBTTagIntArray(new int[] { currentSlot, stackSize }));
/*     */       } 
/*  62 */       entry.func_74782_a("Slots", (NBTBase)slotList);
/*  63 */       list.func_74742_a((NBTBase)entry);
/*     */     } 
/*  65 */     nbt.func_74782_a("Data", (NBTBase)list);
/*  66 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processData(NBTTagCompound decodedData, TileEntityReactorPlanner planner) {
/*  72 */     planner.isSteamReactor = decodedData.func_74767_n("Steam");
/*  73 */     planner.reactorSize = decodedData.func_74762_e("Size");
/*  74 */     planner.setupName = decodedData.func_74779_i("PlanName");
/*  75 */     (planner.getUserSettings()).startingHeat = decodedData.func_74762_e("MaxHeat");
/*  76 */     NBTTagList list = decodedData.func_150295_c("Data", 10);
/*  77 */     ReactorLogicBase base = planner.getReactorLogic();
/*  78 */     boolean valid = false;
/*  79 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/*  81 */       NBTTagCompound nbt = list.func_150305_b(i);
/*  82 */       ItemStack stack = PlannerRegistry.getComponentFromID(nbt.func_74765_d("ID"));
/*  83 */       if (stack != null) {
/*     */ 
/*     */ 
/*     */         
/*  87 */         NBTTagList slotList = nbt.func_150295_c("Slots", 11);
/*  88 */         for (int x = 0; x < slotList.func_74745_c(); x++) {
/*     */           
/*  90 */           int[] array = slotList.func_150306_c(x);
/*  91 */           base.func_70299_a(array[0], StackUtil.copyWithSize(stack, array[1]));
/*  92 */           valid = true;
/*     */         } 
/*     */       } 
/*  95 */     }  if (valid)
/*     */     {
/*  97 */       base.validate();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String createEncodedData(TileEntityReactorPlanner planner) {
/* 104 */     ReactorLogicBase base = planner.getReactorLogic();
/* 105 */     ByteShifter shifter = new ByteShifter();
/* 106 */     Map<Short, List<Integer>> storage = new HashMap<>();
/* 107 */     for (int i = 0; i < 54; i++) {
/*     */       
/* 109 */       ItemStack stack = base.func_70301_a(i);
/* 110 */       if (stack != null) {
/*     */ 
/*     */ 
/*     */         
/* 114 */         short id = PlannerRegistry.getID(stack);
/* 115 */         if (id >> getBitLimit() <= 0)
/*     */         
/*     */         { 
/*     */           
/* 119 */           List<Integer> list = storage.get(Short.valueOf(id));
/* 120 */           if (list == null) {
/*     */             
/* 122 */             list = new ArrayList<>();
/* 123 */             storage.put(Short.valueOf(id), list);
/*     */           } 
/* 125 */           list.add(Integer.valueOf(i)); } 
/*     */       } 
/* 127 */     }  for (Map.Entry<Short, List<Integer>> entry : storage.entrySet()) {
/*     */       
/* 129 */       for (Integer slot : entry.getValue()) {
/*     */         
/* 131 */         ItemStack stack = base.func_70301_a(slot.intValue());
/* 132 */         if (stack == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 136 */         shifter.writeInteger(stack.field_77994_a - 1, 6);
/* 137 */         shifter.writeInteger(slot.intValue(), 6);
/*     */       } 
/* 139 */       shifter.writeInteger(((List)entry.getValue()).size(), 6);
/* 140 */       shifter.writeInteger(((Short)entry.getKey()).shortValue(), getBitLimit());
/*     */     } 
/* 142 */     shifter.writeInteger(storage.size(), 6);
/* 143 */     char[] chars = planner.setupName.toCharArray();
/* 144 */     for (int j = 0; j < chars.length; j++)
/*     */     {
/* 146 */       shifter.writeCharacter(chars[j]);
/*     */     }
/* 148 */     shifter.writeInteger(chars.length, 6);
/* 149 */     shifter.writeInteger(Math.min(base.maxHeat - 100, (planner.getUserSettings()).startingHeat), 20);
/* 150 */     shifter.writeInteger(planner.reactorSize, 3);
/* 151 */     shifter.writeBoolean(planner.isSteamReactor);
/* 152 */     shifter.writeInteger(1, 5);
/* 153 */     shifter.writeInteger(63, 6);
/* 154 */     return shifter.getDataCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 160 */     return "V1 Encoder";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBitLimit() {
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBitLimit() {
/* 172 */     return 10;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\encoder\impl\V1Encoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */