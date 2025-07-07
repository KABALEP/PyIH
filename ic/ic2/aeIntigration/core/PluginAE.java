/*    */ package ic2.aeIntigration.core;
/*    */ 
/*    */ import appeng.client.render.BusRenderer;
/*    */ import appeng.core.Api;
/*    */ import com.google.common.base.Optional;
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import ic2.core.IC2;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraftforge.client.IItemRenderer;
/*    */ import net.minecraftforge.client.MinecraftForgeClient;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PluginAE
/*    */ {
/*    */   public static Item item;
/*    */   
/*    */   public static void load() {
/* 24 */     item = IC2.getInstance().createItem((new AEItem()).func_77637_a((CreativeTabs)IC2.tabIC2).func_77655_b("ClassicEUP2PBus"));
/* 25 */     IC2.platform.addLocalization("item.ClassicEUP2PBus.name", "P2P Tunnel EU (Classic)");
/* 26 */     ENetHelper helper = new ENetHelper();
/* 27 */     FMLCommonHandler.instance().bus().register(helper);
/* 28 */     MinecraftForge.EVENT_BUS.register(helper);
/* 29 */     if (IC2.platform.isRendering())
/*    */     {
/* 31 */       loadClient();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   private static void loadClient() {
/* 38 */     MinecraftForgeClient.registerItemRenderer(item, (IItemRenderer)BusRenderer.INSTANCE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void postLoad() {
/*    */     try {
/* 45 */       for (ClassicEUP2PTunnel.CableType type : ClassicEUP2PTunnel.CableType.values())
/*    */       {
/* 47 */         type.init();
/*    */       }
/*    */     }
/* 50 */     catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 55 */       Optional<Block> maybeOre = Api.INSTANCE.definitions().blocks().quartzOre().maybeBlock();
/* 56 */       if (maybeOre.isPresent())
/*    */       {
/* 58 */         IC2.addValuableOre((Block)maybeOre.get(), 3);
/*    */       }
/* 60 */       maybeOre = Api.INSTANCE.definitions().blocks().quartzOreCharged().maybeBlock();
/* 61 */       if (maybeOre.isPresent())
/*    */       {
/* 63 */         IC2.addValuableOre((Block)maybeOre.get(), 3);
/*    */       }
/*    */     }
/* 66 */     catch (Exception e) {
/*    */       
/* 68 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\aeIntigration\core\PluginAE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */