/*     */ package ic2.core;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*     */ import cpw.mods.fml.common.gameevent.PlayerEvent;
/*     */ import ic2.core.item.armor.ItemArmorJetpack;
/*     */ import java.util.HashMap;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraftforge.common.AchievementPage;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
/*     */ import net.minecraftforge.event.entity.player.PlayerEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IC2Achievements
/*     */ {
/*     */   public HashMap achievementList;
/*     */   private int achievementBaseX;
/*     */   private int achievementBaseY;
/*     */   
/*     */   public IC2Achievements() {
/*  35 */     this.achievementBaseX = -4;
/*  36 */     this.achievementBaseY = -5;
/*  37 */     this.achievementList = new HashMap<>();
/*  38 */     registerAchievement(736750, "acquireResin", 2, 0, Ic2Items.resin, AchievementList.field_76005_g, false);
/*  39 */     if (Ic2Items.copperOre != null || Ic2Items.tinOre != null || Ic2Items.uraniumOre != null)
/*     */     {
/*  41 */       registerAchievement(736751, "mineOre", 4, 0, (Ic2Items.copperOre == null) ? Ic2Items.tinOre : ((Ic2Items.tinOre == null) ? Ic2Items.uraniumOre : Ic2Items.copperOre), AchievementList.field_76012_o, false);
/*     */     }
/*  43 */     registerAchievement(736752, "acquireRefinedIron", 0, 0, Ic2Items.refinedIronIngot, AchievementList.field_76016_k, false);
/*  44 */     registerAchievement(736753, "buildCable", 0, 2, Ic2Items.insulatedCopperCableItem, "acquireRefinedIron", false);
/*  45 */     registerAchievement(736754, "buildGenerator", 6, 2, Ic2Items.generator, "buildCable", false);
/*  46 */     registerAchievement(736755, "buildMacerator", 6, 0, Ic2Items.macerator, "buildGenerator", false);
/*  47 */     registerAchievement(736757, "buildCoalDiamond", 8, 0, Ic2Items.industrialDiamond, "buildMacerator", false);
/*  48 */     registerAchievement(736758, "buildElecFurnace", 8, 2, Ic2Items.electroFurnace, "buildGenerator", false);
/*  49 */     registerAchievement(736759, "buildIndFurnace", 10, 2, Ic2Items.inductionFurnace, "buildElecFurnace", false);
/*  50 */     registerAchievement(736761, "buildCompressor", 4, 4, Ic2Items.compressor, "buildGenerator", false);
/*  51 */     registerAchievement(736762, "compressUranium", 2, 4, Ic2Items.uraniumIngot, "buildCompressor", false);
/*  52 */     registerAchievement(736763, "dieFromOwnNuke", 0, 4, Ic2Items.nuke, "compressUranium", true);
/*  53 */     registerAchievement(736764, "buildExtractor", 8, 4, Ic2Items.extractor, "buildGenerator", false);
/*  54 */     registerAchievement(736760, "buildBatBox", 6, 6, Ic2Items.batBox, "buildGenerator", false);
/*  55 */     registerAchievement(736765, "buildDrill", 8, 6, Ic2Items.miningDrill, "buildBatBox", false);
/*  56 */     registerAchievement(736766, "buildDDrill", 10, 6, Ic2Items.diamondDrill, "buildDrill", false);
/*  57 */     registerAchievement(736767, "buildChainsaw", 4, 6, Ic2Items.chainsaw, "buildBatBox", false);
/*  58 */     registerAchievement(736768, "killCreeperChainsaw", 2, 6, Ic2Items.chainsaw, "buildChainsaw", true);
/*  59 */     registerAchievement(736769, "buildMFE", 6, 8, Ic2Items.mfeUnit, "buildBatBox", false);
/*  60 */     registerAchievement(736770, "buildMassFab", 8, 8, Ic2Items.massFabricator, "buildBatBox", false);
/*  61 */     registerAchievement(736771, "acquireMatter", 10, 8, Ic2Items.matter, "buildMassFab", false);
/*  62 */     registerAchievement(736772, "buildQArmor", 12, 8, Ic2Items.quantumBodyarmor, "acquireMatter", false);
/*  63 */     registerAchievement(736773, "starveWithQHelmet", 14, 8, Ic2Items.quantumHelmet, "buildQArmor", true);
/*  64 */     registerAchievement(736774, "buildMiningLaser", 4, 8, Ic2Items.miningLaser, "buildMFE", false);
/*  65 */     registerAchievement(736775, "killDragonMiningLaser", 2, 8, Ic2Items.miningLaser, "buildMiningLaser", true);
/*  66 */     registerAchievement(736776, "buildMFS", 6, 10, Ic2Items.mfsUnit, "buildMFE", false);
/*  67 */     registerAchievement(736777, "buildTeleporter", 4, 10, Ic2Items.teleporter, "buildMFS", false);
/*  68 */     registerAchievement(736778, "teleportFarAway", 2, 10, Ic2Items.teleporter, "buildTeleporter", true);
/*  69 */     registerAchievement(736779, "buildTerraformer", 8, 10, Ic2Items.terraformer, "buildMFS", false);
/*  70 */     registerAchievement(736780, "terraformEndCultivation", 10, 10, Ic2Items.cultivationTerraformerBlueprint, "buildTerraformer", true);
/*  71 */     AchievementPage.registerAchievementPage(new AchievementPage("IndustrialCraft 2", (Achievement[])this.achievementList.values().toArray((Object[])new Achievement[this.achievementList.size()])));
/*  72 */     MinecraftForge.EVENT_BUS.register(this);
/*  73 */     FMLCommonHandler.instance().bus().register(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Achievement registerAchievement(int id, String textId, int x, int y, ItemStack icon, Achievement requirement, boolean special) {
/*  78 */     Achievement achievement = new Achievement("" + id, textId, this.achievementBaseX + x, this.achievementBaseY + y, icon, requirement);
/*  79 */     if (special)
/*     */     {
/*  81 */       achievement.func_75987_b();
/*     */     }
/*  83 */     achievement.func_75971_g();
/*  84 */     this.achievementList.put(textId, achievement);
/*  85 */     return achievement;
/*     */   }
/*     */ 
/*     */   
/*     */   public Achievement registerAchievement(int id, String textId, int x, int y, ItemStack icon, String requirement, boolean special) {
/*  90 */     Achievement achievement = new Achievement("" + id, textId, this.achievementBaseX + x, this.achievementBaseY + y, icon, getAchievement(requirement));
/*  91 */     if (special)
/*     */     {
/*  93 */       achievement.func_75987_b();
/*     */     }
/*  95 */     achievement.func_75971_g();
/*  96 */     this.achievementList.put(textId, achievement);
/*  97 */     return achievement;
/*     */   }
/*     */ 
/*     */   
/*     */   public void issueAchievement(EntityPlayer entityplayer, String textId) {
/* 102 */     if (this.achievementList.containsKey(textId))
/*     */     {
/* 104 */       entityplayer.func_71029_a((StatBase)this.achievementList.get(textId));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Achievement getAchievement(String textId) {
/* 110 */     if (this.achievementList.containsKey(textId))
/*     */     {
/* 112 */       return (Achievement)this.achievementList.get(textId);
/*     */     }
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onCrafted(PlayerEvent.ItemCraftedEvent evt) {
/* 120 */     ItemStack itemstack = evt.crafting;
/* 121 */     EntityPlayer entityplayer = evt.player;
/* 122 */     assert itemstack != null;
/* 123 */     assert entityplayer != null;
/*     */     
/* 125 */     if (itemstack.func_77969_a(Ic2Items.generator)) {
/*     */       
/* 127 */       issueAchievement(entityplayer, "buildGenerator");
/*     */     }
/* 129 */     else if (itemstack.func_77973_b() == Ic2Items.insulatedCopperCableItem.func_77973_b()) {
/*     */       
/* 131 */       issueAchievement(entityplayer, "buildCable");
/*     */     }
/* 133 */     else if (itemstack.func_77969_a(Ic2Items.macerator)) {
/*     */       
/* 135 */       issueAchievement(entityplayer, "buildMacerator");
/*     */     }
/* 137 */     else if (itemstack.func_77969_a(Ic2Items.electroFurnace)) {
/*     */       
/* 139 */       issueAchievement(entityplayer, "buildElecFurnace");
/*     */     }
/* 141 */     else if (itemstack.func_77969_a(Ic2Items.compressor)) {
/*     */       
/* 143 */       issueAchievement(entityplayer, "buildCompressor");
/*     */     }
/* 145 */     else if (itemstack.func_77969_a(Ic2Items.batBox)) {
/*     */       
/* 147 */       issueAchievement(entityplayer, "buildBatBox");
/*     */     }
/* 149 */     else if (itemstack.func_77969_a(Ic2Items.mfeUnit)) {
/*     */       
/* 151 */       issueAchievement(entityplayer, "buildMFE");
/*     */     }
/* 153 */     else if (itemstack.func_77969_a(Ic2Items.teleporter)) {
/*     */       
/* 155 */       issueAchievement(entityplayer, "buildTeleporter");
/*     */     }
/* 157 */     else if (itemstack.func_77969_a(Ic2Items.massFabricator)) {
/*     */       
/* 159 */       issueAchievement(entityplayer, "buildMassFab");
/*     */     }
/* 161 */     else if (itemstack.func_77973_b() == Ic2Items.quantumBodyarmor.func_77973_b() || itemstack.func_77973_b() == Ic2Items.quantumBoots.func_77973_b() || itemstack.func_77973_b() == Ic2Items.quantumHelmet.func_77973_b() || itemstack.func_77973_b() == Ic2Items.quantumLeggings.func_77973_b()) {
/*     */       
/* 163 */       issueAchievement(entityplayer, "buildQArmor");
/*     */     }
/* 165 */     else if (itemstack.func_77969_a(Ic2Items.extractor)) {
/*     */       
/* 167 */       issueAchievement(entityplayer, "buildExtractor");
/*     */     }
/* 169 */     else if (itemstack.func_77973_b() == Ic2Items.miningDrill.func_77973_b()) {
/*     */       
/* 171 */       issueAchievement(entityplayer, "buildDrill");
/*     */     }
/* 173 */     else if (itemstack.func_77973_b() == Ic2Items.diamondDrill.func_77973_b()) {
/*     */       
/* 175 */       issueAchievement(entityplayer, "buildDDrill");
/*     */     }
/* 177 */     else if (itemstack.func_77973_b() == Ic2Items.chainsaw.func_77973_b()) {
/*     */       
/* 179 */       issueAchievement(entityplayer, "buildChainsaw");
/*     */     }
/* 181 */     else if (itemstack.func_77973_b() == Ic2Items.miningLaser.func_77973_b()) {
/*     */       
/* 183 */       issueAchievement(entityplayer, "buildMiningLaser");
/*     */     }
/* 185 */     else if (itemstack.func_77969_a(Ic2Items.mfsUnit)) {
/*     */       
/* 187 */       issueAchievement(entityplayer, "buildMFS");
/*     */     }
/* 189 */     else if (itemstack.func_77969_a(Ic2Items.terraformer)) {
/*     */       
/* 191 */       issueAchievement(entityplayer, "buildTerraformer");
/*     */     }
/* 193 */     else if (itemstack.func_77969_a(Ic2Items.coalChunk)) {
/*     */       
/* 195 */       issueAchievement(entityplayer, "buildCoalDiamond");
/*     */     }
/* 197 */     else if (itemstack.func_77969_a(Ic2Items.inductionFurnace)) {
/*     */       
/* 199 */       issueAchievement(entityplayer, "buildIndFurnace");
/*     */     } 
/* 201 */     if (itemstack.func_77973_b() instanceof ItemArmorJetpack) {
/*     */       
/* 203 */       ItemArmorJetpack jet = (ItemArmorJetpack)itemstack.func_77973_b();
/* 204 */       if (jet.hasJetpackOverrideRequest(itemstack)) {
/*     */         
/* 206 */         IInventory inv = evt.craftMatrix;
/* 207 */         ItemStack jetpack = null;
/* 208 */         ItemStack armor = null;
/* 209 */         for (int i = 0; i < inv.func_70302_i_(); i++) {
/*     */           
/* 211 */           ItemStack item = inv.func_70301_a(i);
/* 212 */           if (item != null)
/*     */           {
/* 214 */             if (item.func_77973_b() instanceof ItemArmorJetpack) {
/*     */               
/* 216 */               if (jetpack != null) {
/*     */                 return;
/*     */               }
/*     */               
/* 220 */               jetpack = item.func_77946_l();
/*     */             }
/* 222 */             else if (item.func_77973_b() instanceof net.minecraft.item.ItemArmor) {
/*     */               
/* 224 */               if (armor != null) {
/*     */                 return;
/*     */               }
/*     */               
/* 228 */               armor = item.func_77946_l();
/*     */             } 
/*     */           }
/*     */         } 
/* 232 */         if (jetpack != null) {
/*     */           
/* 234 */           ItemStack drop = jet.getArmor(jetpack);
/* 235 */           if (drop != null)
/*     */           {
/* 237 */             entityplayer.func_71019_a(drop, true);
/*     */           }
/* 239 */           jet.addJetpackArmor(itemstack, armor);
/* 240 */           jet.setJetpackOverrideRequest(itemstack, false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSmelting(PlayerEvent.ItemSmeltedEvent evt) {
/* 249 */     ItemStack itemstack = evt.smelting;
/* 250 */     EntityPlayer entityplayer = evt.player;
/*     */     
/* 252 */     assert itemstack != null;
/* 253 */     assert entityplayer != null;
/*     */     
/* 255 */     if (itemstack.func_77969_a(Ic2Items.refinedIronIngot))
/*     */     {
/* 257 */       issueAchievement(entityplayer, "acquireRefinedIron");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMachineOp(EntityPlayer entityplayer, ItemStack itemstack, IInventory inventory) {
/* 263 */     if (inventory instanceof ic2.core.block.machine.tileentity.TileEntityCompressor && itemstack.func_77973_b() == Ic2Items.uraniumIngot.func_77973_b()) {
/*     */       
/* 265 */       issueAchievement(entityplayer, "compressUranium");
/*     */     }
/* 267 */     else if (inventory instanceof ic2.core.block.machine.tileentity.TileEntityMatter && itemstack.func_77973_b() == Ic2Items.matter.func_77973_b()) {
/*     */       
/* 269 */       issueAchievement(entityplayer, "acquireMatter");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onItemPickup(EntityItemPickupEvent event) {
/* 276 */     if ((Ic2Items.copperOre != null && event.item.func_92059_d().func_77969_a(Ic2Items.copperOre)) || (Ic2Items.tinOre != null && event.item.func_92059_d().func_77969_a(Ic2Items.tinOre)) || (Ic2Items.uraniumDrop != null && event.item.func_92059_d().func_77969_a(Ic2Items.uraniumDrop)))
/*     */     {
/* 278 */       issueAchievement(((PlayerEvent)event).entityPlayer, "mineOre");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\IC2Achievements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */