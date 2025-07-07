/*    */ package ic2.core;
/*    */ 
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.WeightedRandomChestContent;
/*    */ import net.minecraftforge.common.ChestGenHooks;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IC2Loot
/*    */ {
/*    */   private static WeightedRandomChestContent[] MINESHAFT_CORRIDOR;
/*    */   private static WeightedRandomChestContent[] STRONGHOLD_CORRIDOR;
/*    */   private static WeightedRandomChestContent[] STRONGHOLD_CROSSING;
/*    */   private static WeightedRandomChestContent[] VILLAGE_BLACKSMITH;
/*    */   private static WeightedRandomChestContent[] BONUS_CHEST;
/*    */   private static WeightedRandomChestContent[] bronzeToolsArmor;
/*    */   private static WeightedRandomChestContent[] ingots;
/*    */   private static WeightedRandomChestContent[] rubberSapling;
/*    */   
/*    */   public IC2Loot() {
/* 23 */     MINESHAFT_CORRIDOR = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Ic2Items.uraniumDrop.func_77946_l(), 1, 2, 1), new WeightedRandomChestContent(Ic2Items.bronzePickaxe.func_77946_l(), 1, 1, 1), new WeightedRandomChestContent(Ic2Items.filledTinCan.func_77946_l(), 4, 16, 8) };
/* 24 */     STRONGHOLD_CORRIDOR = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Ic2Items.matter.func_77946_l(), 1, 4, 1) };
/* 25 */     STRONGHOLD_CROSSING = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Ic2Items.bronzePickaxe.func_77946_l(), 1, 1, 1) };
/* 26 */     VILLAGE_BLACKSMITH = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Ic2Items.bronzeIngot.func_77946_l(), 2, 4, 5) };
/* 27 */     BONUS_CHEST = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Ic2Items.treetap.func_77946_l(), 1, 1, 2) };
/* 28 */     if (IC2.dissableBronzeStuff) {
/*    */       
/* 30 */       bronzeToolsArmor = new WeightedRandomChestContent[0];
/*    */     }
/*    */     else {
/*    */       
/* 34 */       bronzeToolsArmor = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Ic2Items.bronzePickaxe.func_77946_l(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeSword.func_77946_l(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeHelmet.func_77946_l(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeChestplate.func_77946_l(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeLeggings.func_77946_l(), 1, 1, 3), new WeightedRandomChestContent(Ic2Items.bronzeBoots.func_77946_l(), 1, 1, 3) };
/*    */     } 
/* 36 */     ingots = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Ic2Items.copperIngot.func_77946_l(), 2, 6, 9), new WeightedRandomChestContent(Ic2Items.tinIngot.func_77946_l(), 1, 5, 8) };
/* 37 */     rubberSapling = new WeightedRandomChestContent[0];
/*    */     
/* 39 */     if (Ic2Items.rubberSapling != null)
/*    */     {
/* 41 */       rubberSapling = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Ic2Items.rubberSapling.func_77946_l(), 1, 4, 4) };
/*    */     }
/* 43 */     addLoot("mineshaftCorridor", new WeightedRandomChestContent[][] { MINESHAFT_CORRIDOR, ingots });
/* 44 */     addLoot("pyramidDesertyChest", new WeightedRandomChestContent[][] { bronzeToolsArmor, ingots });
/* 45 */     addLoot("pyramidJungleChest", new WeightedRandomChestContent[][] { bronzeToolsArmor, ingots });
/* 46 */     addLoot("strongholdCorridor", new WeightedRandomChestContent[][] { STRONGHOLD_CORRIDOR, bronzeToolsArmor, ingots });
/* 47 */     addLoot("strongholdCrossing", new WeightedRandomChestContent[][] { STRONGHOLD_CROSSING, bronzeToolsArmor, ingots });
/* 48 */     addLoot("villageBlacksmith", new WeightedRandomChestContent[][] { VILLAGE_BLACKSMITH, bronzeToolsArmor, ingots, rubberSapling });
/* 49 */     addLoot("bonusChest", new WeightedRandomChestContent[][] { BONUS_CHEST });
/* 50 */     ChestGenHooks dungeonLoot = ChestGenHooks.getInfo("dungeonChest");
/* 51 */     dungeonLoot.addItem(new WeightedRandomChestContent(Ic2Items.copperIngot.func_77946_l(), 2, 5, 100));
/* 52 */     dungeonLoot.addItem(new WeightedRandomChestContent(Ic2Items.tinIngot.func_77946_l(), 2, 5, 100));
/* 53 */     dungeonLoot.addItem(new WeightedRandomChestContent(Ic2Items.bronzeHoe.func_77946_l(), 1, 1, 1));
/* 54 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151094_cf), 1, 1, 5));
/* 55 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151091_cg), 1, 1, 5));
/* 56 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151092_ch), 1, 1, 5));
/* 57 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151089_ci), 1, 1, 5));
/* 58 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151090_cj), 1, 1, 5));
/* 59 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151087_ck), 1, 1, 5));
/* 60 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151088_cl), 1, 1, 5));
/* 61 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151085_cm), 1, 1, 5));
/* 62 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151086_cn), 1, 1, 5));
/* 63 */     dungeonLoot.addItem(new WeightedRandomChestContent(new ItemStack(Items.field_151084_co), 1, 1, 5));
/*    */   }
/*    */ 
/*    */   
/*    */   private void addLoot(String category, WeightedRandomChestContent[][] loot) {
/* 68 */     ChestGenHooks cgh = ChestGenHooks.getInfo(category);
/* 69 */     for (WeightedRandomChestContent[] arr$ : loot) {
/*    */       
/* 71 */       WeightedRandomChestContent[] lootList = arr$;
/* 72 */       for (WeightedRandomChestContent lootEntry : arr$)
/*    */       {
/* 74 */         cgh.addItem(lootEntry);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\IC2Loot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */