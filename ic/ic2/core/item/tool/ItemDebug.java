/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import ic2.api.network.INetworkItemEventListener;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.tile.IEnergyStorage;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityCrop;
/*     */ import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElecMachine;
/*     */ import ic2.core.block.personal.IPersonalBlock;
/*     */ import ic2.core.energy.EnergyNetGlobal;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.network.packets.IC2Packet;
/*     */ import ic2.core.network.packets.server.EnergyGridPacket;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ItemDebug
/*     */   extends ItemIC2
/*     */   implements INetworkItemEventListener {
/*     */   private static String[] modes;
/*     */   private int mode;
/*  47 */   public static Map<Integer, List<AxisAlignedBB>> grids = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ItemDebug() {
/*  51 */     super(17);
/*  52 */     setSpriteID("i1");
/*  53 */     this.mode = 1;
/*  54 */     func_77627_a(false);
/*  55 */     func_77637_a((CreativeTabs)IC2.tabIC2);
/*  56 */     func_77655_b("itemDebug");
/*  57 */     if (IC2.platform.isRendering())
/*     */     {
/*  59 */       MinecraftForge.EVENT_BUS.register(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent
/*     */   public void onRenderLast(RenderWorldLastEvent evt) {
/*  67 */     EntityClientPlayerMP entityClientPlayerMP = (Minecraft.func_71410_x()).field_71439_g;
/*  68 */     if (entityClientPlayerMP == null) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     ItemStack item = entityClientPlayerMP.func_71045_bC();
/*  73 */     if (item == null || item.func_77973_b() != this) {
/*     */       return;
/*     */     }
/*     */     
/*  77 */     if (((ItemDebug)item.func_77973_b()).mode != 2) {
/*     */       return;
/*     */     }
/*     */     
/*  81 */     List<AxisAlignedBB> boxList = getStuffToRender((EntityPlayer)entityClientPlayerMP);
/*  82 */     if (boxList.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     World world = ((EntityPlayer)entityClientPlayerMP).field_70170_p;
/*     */     
/*  88 */     double x = ((EntityPlayer)entityClientPlayerMP).field_70142_S + (((EntityPlayer)entityClientPlayerMP).field_70165_t - ((EntityPlayer)entityClientPlayerMP).field_70142_S) * evt.partialTicks;
/*  89 */     double y = ((EntityPlayer)entityClientPlayerMP).field_70137_T + (((EntityPlayer)entityClientPlayerMP).field_70163_u - ((EntityPlayer)entityClientPlayerMP).field_70137_T) * evt.partialTicks;
/*  90 */     double z = ((EntityPlayer)entityClientPlayerMP).field_70136_U + (((EntityPlayer)entityClientPlayerMP).field_70161_v - ((EntityPlayer)entityClientPlayerMP).field_70136_U) * evt.partialTicks;
/*     */     
/*  92 */     for (AxisAlignedBB box : boxList) {
/*     */       
/*  94 */       double xCoord = -x + box.field_72340_a - box.field_72336_d;
/*  95 */       double yCoord = -y + box.field_72338_b - box.field_72337_e;
/*  96 */       double zCoord = -z + box.field_72339_c - box.field_72334_f;
/*  97 */       xCoord -= xCoord + x;
/*  98 */       yCoord -= yCoord + y;
/*  99 */       zCoord -= zCoord + z;
/* 100 */       GL11.glPushMatrix();
/* 101 */       GL11.glTranslated(xCoord, yCoord, zCoord);
/* 102 */       GL11.glDepthMask(false);
/* 103 */       GL11.glDisable(3553);
/* 104 */       GL11.glDisable(2896);
/* 105 */       GL11.glDisable(2884);
/* 106 */       GL11.glDisable(3042);
/* 107 */       RenderGlobal.func_147590_a(box, 16776960);
/* 108 */       GL11.glEnable(3553);
/* 109 */       GL11.glEnable(2896);
/* 110 */       GL11.glEnable(2884);
/* 111 */       GL11.glDisable(3042);
/* 112 */       GL11.glDepthMask(true);
/* 113 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AxisAlignedBB> getStuffToRender(EntityPlayer player) {
/* 119 */     int dim = player.field_71093_bK;
/* 120 */     if (dim != player.field_70170_p.field_73011_w.field_76574_g)
/*     */     {
/* 122 */       dim = player.field_70170_p.field_73011_w.field_76574_g;
/*     */     }
/* 124 */     if (!grids.containsKey(Integer.valueOf(dim)))
/*     */     {
/* 126 */       return new ArrayList<>();
/*     */     }
/* 128 */     double x = player.field_70165_t;
/* 129 */     double y = player.field_70163_u;
/* 130 */     double z = player.field_70161_v;
/* 131 */     List<AxisAlignedBB> boxList = grids.get(Integer.valueOf(dim));
/* 132 */     Set<AxisAlignedBB> returnList = new HashSet<>();
/* 133 */     for (AxisAlignedBB box : boxList) {
/*     */       
/* 135 */       double closeX = (x > box.field_72336_d) ? box.field_72336_d : ((x < box.field_72340_a) ? box.field_72340_a : x);
/* 136 */       double closeY = (y > box.field_72337_e) ? box.field_72337_e : ((y < box.field_72338_b) ? box.field_72338_b : z);
/* 137 */       double closeZ = (z > box.field_72334_f) ? box.field_72334_f : ((z < box.field_72339_c) ? box.field_72339_c : y);
/* 138 */       if (player.func_70092_e(closeX, closeY, closeZ) < 4096.0D)
/*     */       {
/* 140 */         returnList.add(box);
/*     */       }
/*     */     } 
/* 143 */     return new ArrayList<>(returnList);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_77659_a(ItemStack item, World world, EntityPlayer player) {
/* 149 */     if (player.func_70093_af()) {
/*     */       
/* 151 */       if (IC2.platform.isSimulating()) {
/*     */         
/* 153 */         this.mode++;
/* 154 */         if (this.mode >= modes.length)
/*     */         {
/* 156 */           this.mode = 0;
/*     */         }
/* 158 */         ((NetworkManager)IC2.network.get()).initiateItemEvent(player, item, this.mode, true);
/* 159 */         IC2.platform.messagePlayer(player, "Debug Item Mode: " + modes[this.mode]);
/*     */       } 
/* 161 */       return item;
/*     */     } 
/*     */ 
/*     */     
/* 165 */     if (IC2.platform.isSimulating())
/*     */     {
/* 167 */       if (this.mode == 2)
/*     */       {
/* 169 */         ((NetworkManager)IC2.network.get()).sendCustomPacket(player, (IC2Packet)new EnergyGridPacket(EnergyNetGlobal.getBoxes()));
/*     */       }
/*     */     }
/*     */     
/* 173 */     return item; } public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) { Block blockId;
/*     */     TileEntity tileEntity;
/*     */     TileEntity tileEntity1;
/*     */     String plat;
/*     */     String message;
/* 178 */     if (player.func_70093_af())
/*     */     {
/* 180 */       return false;
/*     */     }
/* 182 */     switch (this.mode) {
/*     */ 
/*     */       
/*     */       case 0:
/* 186 */         blockId = world.func_147439_a(x, y, z);
/* 187 */         tileEntity1 = world.func_147438_o(x, y, z);
/* 188 */         plat = IC2.platform.isRendering() ? "client" : (IC2.platform.isSimulating() ? "sp" : "server");
/*     */         
/* 190 */         if (blockId != Blocks.field_150350_a) {
/*     */           
/* 192 */           message = "[" + plat + "] id: " + blockId + " name: " + blockId.func_149739_a() + " te: " + tileEntity1;
/*     */         }
/*     */         else {
/*     */           
/* 196 */           message = "[" + plat + "] id: " + blockId + " name: null te: " + tileEntity1;
/*     */         } 
/* 198 */         IC2.platform.messagePlayer(player, message);
/* 199 */         System.out.println(message);
/* 200 */         if (tileEntity1 != null) {
/*     */           
/* 202 */           message = "[" + plat + "] interfaces:";
/* 203 */           Class<?> c = tileEntity1.getClass();
/*     */           
/*     */           while (true) {
/* 206 */             for (Class<?> i : c.getInterfaces())
/*     */             {
/* 208 */               message = message + " " + i.getName();
/*     */             }
/* 210 */             c = c.getSuperclass();
/*     */             
/* 212 */             if (c == null)
/* 213 */             { IC2.platform.messagePlayer(player, message);
/* 214 */               System.out.println(message); break; } 
/*     */           } 
/* 216 */         }  if (blockId != null) {
/*     */           
/* 218 */           System.out.println("block fields:");
/* 219 */           dumpObjectFields(blockId);
/*     */         } 
/* 221 */         if (tileEntity1 != null) {
/*     */           
/* 223 */           System.out.println("tile entity fields:");
/* 224 */           dumpObjectFields(tileEntity1);
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 231 */         if (!IC2.platform.isSimulating())
/*     */         {
/* 233 */           return false;
/*     */         }
/* 235 */         tileEntity = world.func_147438_o(x, y, z);
/* 236 */         if (tileEntity instanceof TileEntityBlock) {
/*     */           
/* 238 */           TileEntityBlock te = (TileEntityBlock)tileEntity;
/* 239 */           IC2.platform.messagePlayer(player, "Block: Active=" + te.getActive() + " Facing=" + te.getFacing());
/*     */         } 
/* 241 */         if (tileEntity instanceof TileEntityBaseGenerator) {
/*     */           
/* 243 */           TileEntityBaseGenerator te2 = (TileEntityBaseGenerator)tileEntity;
/* 244 */           IC2.platform.messagePlayer(player, "BaseGen: Fuel=" + te2.fuel + " Storage=" + te2.storage);
/*     */         } 
/* 246 */         if (tileEntity instanceof TileEntityElecMachine) {
/*     */           
/* 248 */           TileEntityElecMachine te3 = (TileEntityElecMachine)tileEntity;
/* 249 */           IC2.platform.messagePlayer(player, "ElecMachine: Energy=" + te3.energy);
/*     */         } 
/* 251 */         if (tileEntity instanceof IEnergyStorage) {
/*     */           
/* 253 */           IEnergyStorage te4 = (IEnergyStorage)tileEntity;
/* 254 */           IC2.platform.messagePlayer(player, "EnergyStorage: Stored=" + te4.getStored());
/*     */         } 
/* 256 */         if (tileEntity instanceof IReactor) {
/*     */           
/* 258 */           IReactor te5 = (IReactor)tileEntity;
/* 259 */           IC2.platform.messagePlayer(player, "Reactor: Heat=" + te5.getHeat() + " MaxHeat=" + te5.getMaxHeat() + " HEM=" + te5.getHeatEffectModifier() + " Output=" + te5.getReactorEnergyOutput());
/*     */         } 
/* 261 */         if (tileEntity instanceof IPersonalBlock) {
/*     */           
/* 263 */           IPersonalBlock te6 = (IPersonalBlock)tileEntity;
/* 264 */           IC2.platform.messagePlayer(player, "PersonalBlock: CanAccess=" + te6.canAccess(player));
/*     */         } 
/* 266 */         if (tileEntity instanceof TileEntityCrop) {
/*     */           
/* 268 */           TileEntityCrop te7 = (TileEntityCrop)tileEntity;
/* 269 */           IC2.platform.messagePlayer(player, "PersonalBlock: Crop=" + te7.getID() + " Size=" + te7.getSize() + " Growth=" + te7.getGrowth() + " Gain=" + te7.getGain() + " Resistance=" + te7.getResistance() + " Nutrients=" + te7.getNutrientStorage() + " Water=" + te7.getHydrationStorage() + " GrowthPoints=" + te7.growthPoints);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 275 */     return (this.mode != 2); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void dumpObjectFields(Object o) {
/* 280 */     Class<?> fieldDeclaringClass = o.getClass();
/*     */ 
/*     */     
/*     */     do {
/* 284 */       Field[] arr$ = fieldDeclaringClass.getDeclaredFields(), fields = arr$;
/* 285 */       for (Field field : arr$) {
/*     */         
/* 287 */         boolean accessible = field.isAccessible();
/* 288 */         field.setAccessible(true);
/*     */         
/*     */         try {
/* 291 */           System.out.println("name: " + fieldDeclaringClass.getName() + "." + field.getName() + " type: " + field.getType() + " value: " + field.get(o));
/*     */         }
/* 293 */         catch (IllegalAccessException e) {
/*     */           
/* 295 */           System.out.println("name: " + fieldDeclaringClass.getName() + "." + field.getName() + " type: " + field.getType() + " value: <can't access>");
/*     */         } 
/* 297 */         field.setAccessible(accessible);
/*     */       } 
/* 299 */       fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
/*     */     }
/* 301 */     while (fieldDeclaringClass != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(ItemStack metaData, EntityPlayer player, int event) {
/* 307 */     this.mode = event;
/* 308 */     grids.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 313 */     modes = new String[] { "Interfaces and Fields", "Tile Data", "EnergyNet Grids" };
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\item\tool\ItemDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */