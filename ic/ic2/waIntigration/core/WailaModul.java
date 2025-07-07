/*    */ package ic2.waIntigration.core;
/*    */ 
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import ic2.core.IC2;
/*    */ import mcp.mobius.waila.api.impl.ConfigHandler;
/*    */ import mcp.mobius.waila.api.impl.ModuleRegistrar;
/*    */ import net.minecraftforge.common.config.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WailaModul
/*    */ {
/*    */   static boolean ignoreWaila = false;
/*    */   
/*    */   public static void preInit() {
/* 17 */     Property prop = IC2.config.get("general", "Ignore Waila Config", false);
/* 18 */     prop.comment = "This config allows you to skip the Waila Config. If something is not working correctly. Note this will ignore you Waila Settings";
/* 19 */     ignoreWaila = Boolean.parseBoolean(prop.getString());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void load() {
/* 24 */     for (EnergyTileHandler.HudConfig conf : EnergyTileHandler.HudConfig.values())
/*    */     {
/* 26 */       ConfigHandler.instance().addConfig("IC2Classic", conf.getConfig(), conf.getName());
/*    */     }
/* 28 */     EnergyTileHandler handler = new EnergyTileHandler(ignoreWaila);
/* 29 */     ModuleRegistrar.instance().registerBodyProvider(handler, IEnergyTile.class);
/* 30 */     ModuleRegistrar.instance().registerNBTProvider(handler, IEnergyTile.class);
/* 31 */     FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\waIntigration\core\WailaModul.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */