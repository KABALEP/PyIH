/*     */ package ic2.core.block.machine.logic.encoder.impl;
/*     */ 
/*     */ import ic2.core.block.machine.logic.PlannerRegistry;
/*     */ import ic2.core.block.machine.logic.ReactorLogicBase;
/*     */ import ic2.core.block.machine.logic.encoder.IEncoder;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OldNBTEncoder
/*     */   implements IEncoder
/*     */ {
/*     */   public NBTTagCompound createDecodedData(String data) {
/*     */     try {
/*  34 */       DataInputStream stream = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream((new BigInteger(data, 36)).toByteArray())));
/*  35 */       String exportedNBT = stream.readUTF();
/*  36 */       stream.close();
/*  37 */       if (exportedNBT.startsWith("{") && exportedNBT.endsWith("}"))
/*     */       {
/*  39 */         return (NBTTagCompound)JsonToNBT.func_150315_a(exportedNBT);
/*     */       }
/*     */     }
/*  42 */     catch (Exception exception) {}
/*     */ 
/*     */     
/*  45 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processData(NBTTagCompound decodedData, TileEntityReactorPlanner planner) {
/*  51 */     planner.isSteamReactor = decodedData.func_74767_n("SteamReactor");
/*  52 */     planner.reactorSize = 6;
/*  53 */     ReactorLogicBase base = planner.getReactorLogic();
/*  54 */     base.clear();
/*  55 */     NBTTagList list = decodedData.func_150295_c("Data", 10);
/*  56 */     boolean valid = false;
/*  57 */     for (int i = 0; i < list.func_74745_c(); i++) {
/*     */       
/*  59 */       NBTTagCompound data = list.func_150305_b(i);
/*  60 */       short id = data.func_74765_d("ID");
/*  61 */       int[] array = data.func_74759_k("Slots");
/*  62 */       ItemStack item = PlannerRegistry.getComponentFromID(id);
/*  63 */       for (int z = 0; z < array.length; z++) {
/*     */         
/*  65 */         int slot = array[z];
/*  66 */         if (slot != -1) {
/*     */           
/*  68 */           base.func_70299_a(slot, ItemStack.func_77944_b(item));
/*  69 */           valid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*  73 */     if (valid)
/*     */     {
/*  75 */       base.validate();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String createEncodedData(TileEntityReactorPlanner planner) {
/*  82 */     ReactorLogicBase logic = planner.getReactorLogic();
/*  83 */     Map<Short, List<Integer>> amounts = new HashMap<>();
/*  84 */     for (int i = 0; i < 54; i++) {
/*     */       
/*  86 */       ItemStack stack = logic.func_70301_a(i);
/*  87 */       if (stack != null) {
/*     */         
/*  89 */         short id = PlannerRegistry.getID(stack);
/*  90 */         if (id != -1) {
/*     */           
/*  92 */           List<Integer> list = amounts.get(Short.valueOf(id));
/*  93 */           if (list == null) {
/*     */             
/*  95 */             list = new ArrayList<>();
/*  96 */             amounts.put(Short.valueOf(id), list);
/*     */           } 
/*  98 */           list.add(Integer.valueOf(i));
/*     */         } 
/*     */       } 
/*     */     } 
/* 102 */     NBTTagList dataList = new NBTTagList();
/* 103 */     for (Map.Entry<Short, List<Integer>> entry : amounts.entrySet()) {
/*     */       
/* 105 */       NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 106 */       nBTTagCompound.func_74777_a("ID", ((Short)entry.getKey()).shortValue());
/* 107 */       List<Integer> slots = entry.getValue();
/* 108 */       if (slots.size() == 1)
/*     */       {
/* 110 */         slots.add(Integer.valueOf(-1));
/*     */       }
/* 112 */       int[] slotArray = new int[slots.size()];
/* 113 */       for (int j = 0; j < slots.size(); j++)
/*     */       {
/* 115 */         slotArray[j] = ((Integer)slots.get(j)).intValue();
/*     */       }
/* 117 */       nBTTagCompound.func_74783_a("Slots", slotArray);
/* 118 */       dataList.func_74742_a((NBTBase)nBTTagCompound);
/*     */     } 
/* 120 */     NBTTagCompound nbt = new NBTTagCompound();
/* 121 */     nbt.func_74782_a("Data", (NBTBase)dataList);
/* 122 */     nbt.func_74757_a("SteamReactor", planner.isSteamReactor);
/*     */     
/*     */     try {
/* 125 */       ByteArrayOutputStream array = new ByteArrayOutputStream();
/* 126 */       DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(array));
/* 127 */       stream.writeUTF(nbt.toString());
/* 128 */       stream.close();
/* 129 */       return (new BigInteger(array.toByteArray())).toString(36);
/*     */     }
/* 131 */     catch (Exception exception) {
/*     */ 
/*     */       
/* 134 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 140 */     return "First (NBT)";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasBitLimit() {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBitLimit() {
/* 152 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\encoder\impl\OldNBTEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */