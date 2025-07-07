/*     */ package ic2.aeIntigration.core;
/*     */ 
/*     */ import appeng.api.implementations.items.IMemoryCard;
/*     */ import appeng.api.implementations.items.MemoryCardMessages;
/*     */ import appeng.api.parts.IPart;
/*     */ import appeng.api.parts.IPartItem;
/*     */ import appeng.items.parts.ItemMultiPart;
/*     */ import appeng.me.GridAccessException;
/*     */ import appeng.me.cache.P2PCache;
/*     */ import appeng.me.cache.helpers.TunnelCollection;
/*     */ import appeng.parts.p2p.PartP2PTunnel;
/*     */ import cpw.mods.fml.relauncher.ReflectionHelper;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Icons;
/*     */ import ic2.core.Ic2Items;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.IIcon;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraftforge.common.util.ForgeDirection;
/*     */ 
/*     */ public class ClassicEUP2PTunnel
/*     */   extends PartP2PTunnel<ClassicEUP2PTunnel>
/*     */ {
/*     */   public CableType type;
/*     */   public boolean burned = false;
/*     */   
/*     */   public ClassicEUP2PTunnel(ItemStack is) {
/*  36 */     super(is);
/*  37 */     this.type = CableType.values()[is.func_77960_j()];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IIcon getTypeTexture() {
/*  43 */     return this.type.getTexture();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPartActivate(EntityPlayer arg0, Vec3 arg1) {
/*  49 */     ItemStack item = arg0.func_71045_bC();
/*  50 */     if (item != null && !(item.func_77973_b() instanceof IMemoryCard))
/*     */     {
/*  52 */       return false;
/*     */     }
/*  54 */     boolean result = onClick(arg0, arg1);
/*  55 */     if (IC2.platform.isSimulating())
/*     */     {
/*  57 */       ENetHelper.addClickedTunnels(this);
/*     */     }
/*  59 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onClick(EntityPlayer par1, Vec3 par2) {
/*  64 */     ItemStack is = par1.field_71071_by.func_70448_g();
/*     */     
/*  66 */     if (is != null && is.func_77973_b() instanceof IMemoryCard) {
/*     */       
/*  68 */       IMemoryCard mc = (IMemoryCard)is.func_77973_b();
/*  69 */       NBTTagCompound data = mc.getData(is);
/*     */       
/*  71 */       ItemStack newType = ItemStack.func_77949_a(data);
/*  72 */       long freq = data.func_74763_f("freq");
/*     */       
/*  74 */       if (newType != null)
/*     */       {
/*  76 */         if (newType.func_77973_b() instanceof IPartItem) {
/*     */           
/*  78 */           IPart testPart = ((IPartItem)newType.func_77973_b()).createPartFromItemStack(newType);
/*  79 */           if (testPart instanceof ClassicEUP2PTunnel && ((ClassicEUP2PTunnel)testPart).type == this.type) {
/*     */             
/*  81 */             getHost().removePart(getSide(), true);
/*  82 */             ForgeDirection dir = getHost().addPart(newType, getSide(), par1);
/*  83 */             IPart newBus = getHost().getPart(dir);
/*     */             
/*  85 */             if (newBus instanceof PartP2PTunnel) {
/*     */               
/*  87 */               PartP2PTunnel<?> newTunnel = (PartP2PTunnel)newBus;
/*     */               try {
/*  89 */                 ReflectionHelper.findField(PartP2PTunnel.class, new String[] { "output" }).set(newTunnel, Boolean.valueOf(true));
/*     */               }
/*  91 */               catch (Exception e) {
/*  92 */                 e.printStackTrace();
/*     */               } 
/*     */               
/*     */               try {
/*  96 */                 P2PCache p2p = newTunnel.getProxy().getP2P();
/*  97 */                 p2p.updateFreq(newTunnel, freq);
/*     */               }
/*  99 */               catch (GridAccessException gridAccessException) {}
/*     */ 
/*     */ 
/*     */               
/* 103 */               newTunnel.onTunnelNetworkChange();
/* 104 */               if (IC2.platform.isSimulating())
/*     */               {
/* 106 */                 ENetHelper.addTunnel((ClassicEUP2PTunnel)newTunnel);
/*     */               }
/*     */             } 
/* 109 */             mc.notifyUser(par1, MemoryCardMessages.SETTINGS_LOADED);
/* 110 */             return true;
/*     */           } 
/*     */         } 
/*     */       }
/* 114 */       mc.notifyUser(par1, MemoryCardMessages.INVALID_MACHINE);
/*     */     } 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPartShiftActivate(EntityPlayer arg0, Vec3 arg1) {
/* 124 */     ItemStack item = arg0.func_71045_bC();
/* 125 */     if (item != null && !(item.func_77973_b() instanceof IMemoryCard))
/*     */     {
/* 127 */       return false;
/*     */     }
/* 129 */     boolean result = super.onPartShiftActivate(arg0, arg1);
/* 130 */     if (IC2.platform.isSimulating())
/*     */     {
/* 132 */       ENetHelper.addClickedTunnels(this);
/*     */     }
/* 134 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void gridChanged() {
/* 140 */     super.gridChanged();
/* 141 */     if (IC2.platform.isSimulating()) {
/*     */       
/* 143 */       EnergyNetwork network = ENetHelper.getNetwork(getFrequency());
/* 144 */       if (network != null && network.contains(!isOutput(), getTile(), getSide()))
/*     */       {
/* 146 */         ENetHelper.updateNetwork(network);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFromWorld() {
/* 154 */     super.removeFromWorld();
/* 155 */     if (IC2.platform.isSimulating())
/*     */     {
/* 157 */       ENetHelper.removeTunnel(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToWorld() {
/* 164 */     super.addToWorld();
/* 165 */     if (IC2.platform.isSimulating())
/*     */     {
/* 167 */       ENetHelper.addTunnel(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ForgeDirection getFacing() {
/* 173 */     return getSide();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TunnelCollection<PartP2PTunnel> getOutputsTunnel() throws GridAccessException {
/* 179 */     return getProxy().isActive() ? 
/* 180 */       getProxy().getP2P().getOutputs(getFrequency(), getClass()) : new TunnelCollection(new ArrayList(), 
/* 181 */         getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAddedToNetwork() {
/*     */     try {
/* 189 */       TunnelCollection<PartP2PTunnel> tunnel = getOutputsTunnel();
/* 190 */       if (tunnel != null)
/*     */       {
/* 192 */         for (PartP2PTunnel p2p : tunnel)
/*     */         {
/* 194 */           if (p2p instanceof ClassicEUP2PTunnel) {
/* 195 */             ENetHelper.addTunnel((ClassicEUP2PTunnel)p2p);
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 200 */     } catch (GridAccessException e) {
/*     */       
/* 202 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBurned() {
/* 208 */     this.burned = true;
/* 209 */     this.type = CableType.None;
/* 210 */     getItemStack().func_77964_b(this.type.ordinal());
/* 211 */     getHost().markForUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound data) {
/* 217 */     super.readFromNBT(data);
/* 218 */     this.burned = data.func_74767_n("Burned");
/* 219 */     this.type = CableType.values()[data.func_74762_e("Type")];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound data) {
/* 225 */     super.writeToNBT(data);
/* 226 */     data.func_74757_a("Burned", this.burned);
/* 227 */     data.func_74768_a("Type", this.type.ordinal());
/*     */   }
/*     */   
/*     */   public enum CableType
/*     */   {
/* 232 */     Tin(0.025D, 6, "Tin Cable"),
/* 233 */     Copper(0.2D, 33, "Copper Cable"),
/* 234 */     Gold(0.4D, 129, "Gold Cable"),
/* 235 */     RefinedIron(0.8D, 2049, "Refined Iron Cable"),
/* 236 */     Glass(0.025D, 513, "Glass Cable"),
/* 237 */     None(0.0D, 0, "Burned cable");
/*     */     
/*     */     double loss;
/*     */     
/*     */     int maxEnergy;
/*     */     String name;
/*     */     IIcon texture;
/*     */     
/*     */     CableType(double loss, int maxEnergy, String name) {
/* 246 */       this.loss = loss;
/* 247 */       this.maxEnergy = maxEnergy;
/* 248 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {
/* 253 */       ItemStack items = new ItemStack((Item)ItemMultiPart.instance, 1, 460);
/* 254 */       Recipes.advRecipes.addShapelessRecipe(items, new Object[] { new ItemStack(PluginAE.item, 1, ordinal()) });
/* 255 */       if (getP2PItem() == null) {
/*     */         return;
/*     */       }
/*     */       
/* 259 */       Recipes.advRecipes.addShapelessRecipe(new ItemStack(PluginAE.item, 1, ordinal()), new Object[] { items, getP2PItem() });
/*     */     }
/*     */ 
/*     */     
/*     */     public IIcon getTexture() {
/* 264 */       if (this.texture != null)
/*     */       {
/* 266 */         this.texture = null;
/*     */       }
/* 268 */       if (this.texture == null)
/*     */       {
/* 270 */         this.texture = loadTexture();
/*     */       }
/* 272 */       return this.texture;
/*     */     }
/*     */ 
/*     */     
/*     */     private IIcon loadTexture() {
/* 277 */       switch (this) {
/*     */         case Copper:
/* 279 */           return Ic2Icons.getTexture("bcable")[16];
/* 280 */         case Glass: return Blocks.field_150484_ah.func_149691_a(0, 0);
/* 281 */         case Gold: return Ic2Icons.getTexture("bcable")[32];
/* 282 */         case None: return Blocks.field_150402_ci.func_149691_a(0, 0);
/* 283 */         case RefinedIron: return Ic2Icons.getTexture("bcable")[80];
/* 284 */         case Tin: return Ic2Icons.getTexture("bcable")[160];
/* 285 */       }  return Ic2Icons.getTexture("bcable")[16];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getType() {
/* 291 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getP2PItem() {
/* 296 */       switch (this) {
/*     */         case Copper:
/* 298 */           return "ingotCopper";
/* 299 */         case Glass: return Ic2Items.glassFiberCableItem;
/* 300 */         case Gold: return new ItemStack(Items.field_151043_k);
/* 301 */         case RefinedIron: return Ic2Items.refinedIronIngot;
/* 302 */         case Tin: return "ingotTin";
/* 303 */       }  return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double getLoss() {
/* 309 */       return this.loss;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxEnergy() {
/* 314 */       return this.maxEnergy;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class P2PEntry
/*     */   {
/*     */     final ClassicEUP2PTunnel tunnel;
/*     */     
/*     */     final TileEntity tile;
/*     */     final ForgeDirection dir;
/*     */     
/*     */     public P2PEntry(ClassicEUP2PTunnel tunnel) {
/* 327 */       this.tunnel = tunnel;
/* 328 */       this.tile = tunnel.getTile();
/* 329 */       this.dir = tunnel.getFacing();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\aeIntigration\core\ClassicEUP2PTunnel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */