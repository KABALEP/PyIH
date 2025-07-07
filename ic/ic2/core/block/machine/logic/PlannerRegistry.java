/*     */ package ic2.core.block.machine.logic;
/*     */ 
/*     */ import ic2.api.reactor.IReactorPlannerComponent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.logic.encoder.EncoderRegistry;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.util.StatCollector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlannerRegistry
/*     */ {
/*  34 */   static Map<ItemWithMeta, ComponentStat> itemToStats = new HashMap<>();
/*  35 */   static Map<ItemWithMeta, Short> itemToId = new HashMap<>();
/*  36 */   static ItemStack[] idToItem = new ItemStack[32767];
/*     */ 
/*     */   
/*  39 */   static ItemStack[] reactorComponents = new ItemStack[0];
/*  40 */   static ItemStack[] steamReactorComponents = new ItemStack[0];
/*     */ 
/*     */   
/*  43 */   static EnumMap<IReactorPlannerComponent.ReactorComponentType, List<Short>> typeToID = new EnumMap<>(IReactorPlannerComponent.ReactorComponentType.class);
/*  44 */   static EnumMap<IReactorPlannerComponent.ReactorComponentType, List<Short>> typeToIDNormal = new EnumMap<>(IReactorPlannerComponent.ReactorComponentType.class);
/*  45 */   static EnumMap<IReactorPlannerComponent.ReactorComponentType, List<Short>> typeToIDSteam = new EnumMap<>(IReactorPlannerComponent.ReactorComponentType.class);
/*     */   
/*  47 */   static IReactorPlannerComponent.ReactorComponentType[] idToType = new IReactorPlannerComponent.ReactorComponentType[32767];
/*     */   
/*  49 */   static ComponentSorter sorter = new ComponentSorter();
/*     */ 
/*     */   
/*     */   public static void init() {
/*  53 */     List<ItemStack> reactor = new ArrayList<>();
/*  54 */     List<ItemStack> steamReactor = new ArrayList<>();
/*     */     
/*  56 */     Iterator<Item> iter = Item.field_150901_e.iterator();
/*  57 */     while (iter.hasNext()) {
/*     */       
/*  59 */       Item item = iter.next();
/*  60 */       if (item instanceof IReactorPlannerComponent) {
/*     */         
/*  62 */         IReactorPlannerComponent component = (IReactorPlannerComponent)item;
/*  63 */         List<ItemStack> items = ComponentStat.createComponents(component);
/*  64 */         for (ItemStack stack : items) {
/*     */           
/*  66 */           short id = component.getID(stack);
/*  67 */           if (idToItem[id] != null) {
/*     */             
/*  69 */             IC2.platform.displayError("ReactorPlanner ID is already used: " + id + ", Item Tried to be added: " + stack.func_82833_r() + ", Item that was added: " + idToItem[id].func_82833_r());
/*     */             return;
/*     */           } 
/*  72 */           ItemStack copy = StackUtil.copyWithSize(stack, 1);
/*  73 */           ItemWithMeta meta = new ItemWithMeta(stack);
/*  74 */           itemToId.put(meta, Short.valueOf(id));
/*  75 */           idToItem[id] = copy;
/*  76 */           IReactorPlannerComponent.ReactorType type = component.getReactorInfo(stack);
/*  77 */           IReactorPlannerComponent.ReactorComponentType compType = component.getType(stack);
/*  78 */           if (type.isReactor()) {
/*     */             
/*  80 */             reactor.add(copy);
/*  81 */             List<Short> list = typeToIDNormal.get(compType);
/*  82 */             if (list == null) {
/*     */               
/*  84 */               list = new ArrayList<>();
/*  85 */               typeToIDNormal.put(compType, list);
/*     */             } 
/*  87 */             list.add(Short.valueOf(id));
/*     */           } 
/*  89 */           if (type.isSteamReactor() && component instanceof ic2.api.reactor.ISteamReactorComponent) {
/*     */             
/*  91 */             steamReactor.add(copy);
/*  92 */             List<Short> list = typeToIDSteam.get(compType);
/*  93 */             if (list == null) {
/*     */               
/*  95 */               list = new ArrayList<>();
/*  96 */               typeToIDSteam.put(compType, list);
/*     */             } 
/*  98 */             list.add(Short.valueOf(id));
/*     */           } 
/* 100 */           idToType[id] = compType;
/* 101 */           List<Short> ids = typeToID.get(compType);
/* 102 */           if (ids == null) {
/*     */             
/* 104 */             ids = new ArrayList<>();
/* 105 */             typeToID.put(compType, ids);
/*     */           } 
/* 107 */           ids.add(Short.valueOf(id));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     Collections.sort(reactor, sorter);
/* 113 */     Collections.sort(steamReactor, sorter);
/* 114 */     reactorComponents = reactor.<ItemStack>toArray(new ItemStack[reactor.size()]);
/* 115 */     steamReactorComponents = steamReactor.<ItemStack>toArray(new ItemStack[steamReactor.size()]);
/* 116 */     EncoderRegistry.instance.init();
/*     */   }
/*     */ 
/*     */   
/*     */   public static short getID(ItemStack stack) {
/* 121 */     if (stack == null)
/*     */     {
/* 123 */       return -1;
/*     */     }
/* 125 */     Short meta = itemToId.get(new ItemWithMeta(stack));
/* 126 */     if (meta == null)
/*     */     {
/* 128 */       return -1;
/*     */     }
/* 130 */     return meta.shortValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static IReactorPlannerComponent.ReactorComponentType getTypeByItem(ItemStack stack) {
/* 135 */     short id = getID(stack);
/* 136 */     if (id == -1)
/*     */     {
/* 138 */       return null;
/*     */     }
/* 140 */     return idToType[id];
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack[] getItemsByType(byte sorting, boolean steam) {
/* 145 */     IReactorPlannerComponent.ReactorComponentType[] array = IReactorPlannerComponent.ReactorComponentType.values();
/* 146 */     if (sorting == -1 || sorting >= array.length)
/*     */     {
/* 148 */       return getItemsByType((IReactorPlannerComponent.ReactorComponentType)null, steam);
/*     */     }
/* 150 */     return getItemsByType(array[sorting], steam);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack[] getItemsByType(IReactorPlannerComponent.ReactorComponentType type, boolean steam) {
/* 155 */     if (type == null)
/*     */     {
/* 157 */       return steam ? steamReactorComponents : reactorComponents;
/*     */     }
/* 159 */     List<ItemStack> list = new ArrayList<>();
/* 160 */     List<Short> entries = (steam ? typeToIDSteam : typeToIDNormal).get(type);
/* 161 */     if (entries == null || entries.isEmpty())
/*     */     {
/* 163 */       return new ItemStack[0];
/*     */     }
/* 165 */     for (Short entry : entries) {
/*     */       
/* 167 */       ItemStack item = idToItem[entry.shortValue()];
/* 168 */       if (item != null)
/*     */       {
/* 170 */         list.add(item);
/*     */       }
/*     */     } 
/* 173 */     Collections.sort(list, sorter);
/* 174 */     return list.<ItemStack>toArray(new ItemStack[list.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack getComponentFromID(short id) {
/* 179 */     if (id < 0 || id > idToItem.length)
/*     */     {
/* 181 */       return null;
/*     */     }
/* 183 */     return ItemStack.func_77944_b(idToItem[id]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ComponentStat getStatsFromItem(ItemStack item) {
/* 188 */     if (item == null)
/*     */     {
/* 190 */       return null;
/*     */     }
/* 192 */     return itemToStats.get(new ItemWithMeta(item));
/*     */   }
/*     */   
/*     */   public static class ComponentStat
/*     */   {
/* 197 */     static DecimalFormat format = new DecimalFormat("#.##");
/* 198 */     EnumMap<IReactorPlannerComponent.ReactorComponentStat, NBTBase.NBTPrimitive> stats = new EnumMap<>(IReactorPlannerComponent.ReactorComponentStat.class);
/*     */ 
/*     */     
/*     */     public ComponentStat(IReactorPlannerComponent planner, ItemStack stack) {
/* 202 */       IReactorPlannerComponent.ReactorComponentType type = planner.getType(stack);
/* 203 */       for (IReactorPlannerComponent.ReactorComponentStat stat : type.getStats())
/*     */       {
/* 205 */         this.stats.put(stat, planner.getReactorStat(stat, stack));
/*     */       }
/* 207 */       List<IReactorPlannerComponent.ReactorComponentStat> list = planner.getExtraStats(stack);
/* 208 */       if (list != null)
/*     */       {
/* 210 */         for (IReactorPlannerComponent.ReactorComponentStat stat : list)
/*     */         {
/* 212 */           this.stats.put(stat, planner.getReactorStat(stat, stack));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public static List<ItemStack> createComponents(IReactorPlannerComponent comp) {
/* 219 */       List<ItemStack> list = new ArrayList<>();
/* 220 */       if (comp.hasSubParts()) { list.addAll(Arrays.asList(comp.getSubParts())); }
/* 221 */       else { list.add(comp.getReactorPart()); }
/*     */       
/* 223 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 225 */         ItemStack stack = list.get(i);
/* 226 */         if (stack == null) {
/*     */           
/* 228 */           list.remove(i--);
/*     */         } else {
/*     */           
/* 231 */           PlannerRegistry.itemToStats.put(new PlannerRegistry.ItemWithMeta(stack), new ComponentStat(comp, stack));
/*     */         } 
/* 233 */       }  return list;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<IReactorPlannerComponent.ReactorComponentStat> getKeys() {
/* 238 */       return this.stats.keySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getComponentText(IReactorPlannerComponent.ReactorComponentStat comp) {
/* 243 */       NBTBase.NBTPrimitive nbt = this.stats.get(comp);
/* 244 */       if (nbt.func_74732_a() == 5) {
/*     */         
/* 246 */         float value = nbt.func_150288_h();
/* 247 */         if (comp == IReactorPlannerComponent.ReactorComponentStat.ReactorEEM)
/*     */         {
/* 249 */           if (value > 0.0F && value < 1.0F)
/*     */           {
/* 251 */             return StatCollector.func_74837_a("container.reactorplannerInfo.stat." + comp.name() + ".name", new Object[] { (int)(value * 100.0F) + "%" });
/*     */           }
/*     */         }
/* 254 */         return StatCollector.func_74837_a("container.reactorplannerInfo.stat." + comp.name() + ".name", new Object[] { format.format(nbt.func_150288_h()) });
/*     */       } 
/*     */ 
/*     */       
/* 258 */       return StatCollector.func_74837_a("container.reactorplannerInfo.stat." + comp.name() + ".name", new Object[] { Integer.valueOf(nbt.func_150287_d()) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ComponentSorter
/*     */     implements Comparator<ItemStack>
/*     */   {
/*     */     public int compare(ItemStack par1, ItemStack par2) {
/* 268 */       short firstID = PlannerRegistry.getID(par1);
/* 269 */       short secID = PlannerRegistry.getID(par2);
/* 270 */       IReactorPlannerComponent.ReactorComponentType first = PlannerRegistry.idToType[firstID];
/* 271 */       IReactorPlannerComponent.ReactorComponentType second = PlannerRegistry.idToType[secID];
/*     */       
/* 273 */       if (first == null && second == null)
/*     */       {
/* 275 */         return 0;
/*     */       }
/* 277 */       if (first == null)
/*     */       {
/* 279 */         return 1;
/*     */       }
/* 281 */       if (second == null)
/*     */       {
/* 283 */         return -1;
/*     */       }
/* 285 */       if (first.ordinal() < second.ordinal())
/*     */       {
/* 287 */         return -1;
/*     */       }
/* 289 */       if (first.ordinal() > second.ordinal())
/*     */       {
/* 291 */         return 1;
/*     */       }
/* 293 */       if (firstID < secID)
/*     */       {
/* 295 */         return -1;
/*     */       }
/* 297 */       if (secID < firstID)
/*     */       {
/* 299 */         return 1;
/*     */       }
/* 301 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ItemWithMeta
/*     */   {
/*     */     Item item;
/*     */     int meta;
/*     */     int hashCode;
/*     */     
/*     */     public ItemWithMeta(ItemStack par1) {
/* 313 */       this(par1.func_77973_b(), par1.func_77960_j());
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemWithMeta(Item par1, int par2) {
/* 318 */       this.item = par1;
/* 319 */       this.meta = par2;
/* 320 */       this.hashCode = Objects.hash(new Object[] { this.item, Integer.valueOf(this.meta) });
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemWithMeta(Item par1) {
/* 325 */       this(par1, 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack toStack() {
/* 330 */       return new ItemStack(this.item, 1, this.meta);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemWithMeta toWildcard() {
/* 335 */       return new ItemWithMeta(this.item, 32767);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 341 */       if (obj instanceof ItemWithMeta) {
/*     */         
/* 343 */         ItemWithMeta itemWithMeta = (ItemWithMeta)obj;
/* 344 */         return (itemWithMeta.item == this.item && itemWithMeta.meta == this.meta);
/*     */       } 
/* 346 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 352 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\block\machine\logic\PlannerRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */