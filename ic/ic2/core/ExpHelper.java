/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.core.item.ItemNoUse;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExpHelper
/*     */ {
/*     */   public static void init() {
/*  14 */     Ic2Items.leadOre = getItem(true);
/*  15 */     Ic2Items.leadBlock = getItem(true);
/*  16 */     Ic2Items.leadIngot = getItem(false);
/*  17 */     Ic2Items.recipeObjectCircuit = Ic2Items.electronicCircuit;
/*  18 */     Ic2Items.recipeObjectAdvCircuit = Ic2Items.advancedCircuit;
/*  19 */     Ic2Items.constructionreinforcedFoam = Ic2Items.constructionFoam;
/*  20 */     Ic2Items.insulatedtinCableBlock = Ic2Items.tinCableBlock;
/*  21 */     Ic2Items.RTGenerator = getItem(false);
/*  22 */     Ic2Items.semifluidGenerator = getItem(true);
/*  23 */     Ic2Items.cesuUnit = getItem(false);
/*  24 */     Ic2Items.centrifuge = getItem(false);
/*  25 */     Ic2Items.metalformer = getItem(false);
/*  26 */     Ic2Items.orewashingplant = getItem(false);
/*  27 */     Ic2Items.patternstorage = getItem(false);
/*  28 */     Ic2Items.scanner = getItem(false);
/*  29 */     Ic2Items.replicator = getItem(false);
/*  30 */     Ic2Items.solidcanner = Ic2Items.canner;
/*  31 */     Ic2Items.fluidbottler = Ic2Items.canner;
/*  32 */     Ic2Items.advminer = Ic2Items.miner;
/*  33 */     Ic2Items.FluidCell = getItem(true);
/*  34 */     Ic2Items.reactorLithiumCell = getItem(false);
/*  35 */     Ic2Items.TritiumCell = getItem(false);
/*  36 */     Ic2Items.UranFuel = Ic2Items.uraniumIngot;
/*  37 */     Ic2Items.MOXFuel = getItem(false);
/*  38 */     Ic2Items.Plutonium = getItem(false);
/*  39 */     Ic2Items.smallPlutonium = getItem(false);
/*  40 */     Ic2Items.Uran235 = getItem(false);
/*  41 */     Ic2Items.smallUran235 = getItem(false);
/*  42 */     Ic2Items.Uran238 = getItem(false);
/*  43 */     Ic2Items.reactorDepletedUraniumSimple = Ic2Items.nearDepletedUraniumCell;
/*  44 */     Ic2Items.reactorDepletedUraniumDual = new ItemStack(Ic2Items.nearDepletedUraniumCell.func_77973_b(), 2);
/*  45 */     Ic2Items.reactorDepletedUraniumQuad = new ItemStack(Ic2Items.nearDepletedUraniumCell.func_77973_b(), 4);
/*  46 */     Ic2Items.reactorDepletedMOXSimple = getItem(false);
/*  47 */     Ic2Items.reactorDepletedMOXDual = getItem(false);
/*  48 */     Ic2Items.reactorDepletedMOXQuad = getItem(false);
/*  49 */     Ic2Items.reactorMOXSimple = getItem(false);
/*  50 */     Ic2Items.reactorMOXDual = getItem(false);
/*  51 */     Ic2Items.reactorMOXQuad = getItem(false);
/*  52 */     Ic2Items.RTGPellets = getItem(false);
/*  53 */     Ic2Items.coil = getItem(false);
/*  54 */     Ic2Items.elemotor = getItem(false);
/*  55 */     Ic2Items.powerunit = getItem(false);
/*  56 */     Ic2Items.powerunitsmall = getItem(false);
/*  57 */     Ic2Items.casingcopper = Ic2Items.copperIngot;
/*  58 */     Ic2Items.casingtin = Ic2Items.tinIngot;
/*  59 */     Ic2Items.casingbronze = Ic2Items.bronzeIngot;
/*  60 */     Ic2Items.casinggold = new ItemStack(Items.field_151043_k);
/*  61 */     Ic2Items.casingiron = new ItemStack(Items.field_151042_j);
/*  62 */     Ic2Items.casinglead = Ic2Items.leadIngot;
/*  63 */     Ic2Items.crushedIronOre = Ic2Items.ironDust;
/*  64 */     Ic2Items.crushedGoldOre = Ic2Items.goldDust;
/*  65 */     Ic2Items.crushedCopperOre = Ic2Items.copperDust;
/*  66 */     Ic2Items.crushedTinOre = Ic2Items.tinDust;
/*  67 */     Ic2Items.crushedUraniumOre = getItem(false);
/*  68 */     Ic2Items.crushedSilverOre = Ic2Items.silverDust;
/*  69 */     Ic2Items.crushedLeadOre = getItem(false);
/*  70 */     Ic2Items.purifiedCrushedIronOre = Ic2Items.ironDust;
/*  71 */     Ic2Items.purifiedCrushedGoldOre = Ic2Items.goldDust;
/*  72 */     Ic2Items.purifiedCrushedCopperOre = Ic2Items.copperDust;
/*  73 */     Ic2Items.purifiedCrushedTinOre = Ic2Items.tinDust;
/*  74 */     Ic2Items.purifiedCrushedUraniumOre = getItem(false);
/*  75 */     Ic2Items.purifiedCrushedSilverOre = Ic2Items.silverDust;
/*  76 */     Ic2Items.purifiedCrushedLeadOre = getItem(false);
/*  77 */     Ic2Items.stoneDust = getItem(false);
/*  78 */     Ic2Items.energiumDust = getItem(false);
/*  79 */     Ic2Items.leadDust = getItem(true);
/*  80 */     Ic2Items.lapiDust = getItem(false);
/*  81 */     Ic2Items.sulfurDust = getItem(true);
/*  82 */     Ic2Items.lithiumDust = getItem(false);
/*  83 */     Ic2Items.silicondioxideDust = getItem(false);
/*  84 */     Ic2Items.diamondDust = getItem(false);
/*  85 */     Ic2Items.smallCopperDust = getItem(false);
/*  86 */     Ic2Items.smallTinDust = getItem(false);
/*  87 */     Ic2Items.smallGoldDust = getItem(false);
/*  88 */     Ic2Items.smallSilverDust = getItem(false);
/*  89 */     Ic2Items.smallLeadDust = getItem(false);
/*  90 */     Ic2Items.smallSulfurDust = getItem(false);
/*  91 */     Ic2Items.smallLithiumDust = getItem(false);
/*  92 */     Ic2Items.ForgeHammer = getItem(false);
/*  93 */     Ic2Items.iridiumDrill = Ic2Items.diamondDrill;
/*  94 */     Ic2Items.plasmaLauncher = getItem(false);
/*  95 */     Ic2Items.advbatPack = getItem(false);
/*  96 */     Ic2Items.energyPack = Ic2Items.batPack;
/*  97 */     Ic2Items.advBattery = getItem(false);
/*  98 */     Ic2Items.insulatedTinCableItem = Ic2Items.tinCableItem;
/*  99 */     Ic2Items.UuMatterCell = getItem(false);
/* 100 */     Ic2Items.CFCell = getItem(false);
/* 101 */     Ic2Items.fuelRod = Ic2Items.cell;
/* 102 */     Ic2Items.platecopper = Ic2Items.copperIngot;
/* 103 */     Ic2Items.platetin = Ic2Items.tinIngot;
/* 104 */     Ic2Items.platebronze = Ic2Items.bronzeIngot;
/* 105 */     Ic2Items.plategold = new ItemStack(Items.field_151043_k);
/* 106 */     Ic2Items.plateiron = Ic2Items.refinedIronIngot;
/* 107 */     Ic2Items.platelead = Ic2Items.leadIngot;
/* 108 */     Ic2Items.platelapi = new ItemStack(Items.field_151100_aR, 1, 4);
/* 109 */     Ic2Items.plateobsidian = new ItemStack(Blocks.field_150343_Z);
/* 110 */     Ic2Items.denseplatecopper = Ic2Items.denseCopperPlate;
/* 111 */     Ic2Items.denseplatetin = Ic2Items.tinBlock;
/* 112 */     Ic2Items.denseplatebronze = Ic2Items.bronzeBlock;
/* 113 */     Ic2Items.denseplatelead = Ic2Items.leadBlock;
/* 114 */     Ic2Items.denseplategold = new ItemStack(Blocks.field_150340_R);
/* 115 */     Ic2Items.denseplateiron = new ItemStack(Blocks.field_150339_S);
/* 116 */     Ic2Items.denseplatelapi = new ItemStack(Blocks.field_150368_y);
/* 117 */     Ic2Items.denseplateobsidian = getItem(false);
/* 118 */     Ic2Items.ejectorUpgrade = getItem(true);
/* 119 */     Ic2Items.fluidEjectorUpgrade = getItem(true);
/* 120 */     Ic2Items.constructionFoamPowder = getItem(false);
/* 121 */     Ic2Items.boatCarbon = getItem(true);
/* 122 */     Ic2Items.boatRubber = getItem(true);
/* 123 */     Ic2Items.boatRubberBroken = getItem(true);
/* 124 */     Ic2Items.boatElectric = getItem(true);
/* 125 */     Ic2Items.rawcrystalmemory = getItem(false);
/* 126 */     Ic2Items.crystalmemory = getItem(false);
/* 127 */     Ic2Items.basaltBlock = getItem(false);
/* 128 */     Ic2Items.advironblock = Ic2Items.refinedIronIngot.func_77946_l();
/* 129 */     Ic2Items.stirlingGenerator = Ic2Items.generator.func_77946_l();
/* 130 */     Ic2Items.kineticGenerator = getItem(false);
/* 131 */     Ic2Items.reactorFluidPort = getItem(false);
/* 132 */     Ic2Items.reactorvessel = getItem(false);
/* 133 */     Ic2Items.reactorAccessHatch = getItem(false);
/* 134 */     Ic2Items.reactorRedstonePort = getItem(false);
/* 135 */     Ic2Items.SolidHeatGenerator = getItem(false);
/* 136 */     Ic2Items.FluidHeatGenerator = getItem(false);
/* 137 */     Ic2Items.RTHeatGenerator = getItem(false);
/* 138 */     Ic2Items.ElecHeatGenerator = getItem(false);
/* 139 */     Ic2Items.WindKineticGenerator = Ic2Items.windMill.func_77946_l();
/* 140 */     Ic2Items.SteamKineticGenerator = getItem(false);
/* 141 */     Ic2Items.ElectricKineticGenerator = getItem(false);
/* 142 */     Ic2Items.ManualKineticGenerator = getItem(false);
/* 143 */     Ic2Items.WaterKineticGenerator = Ic2Items.waterMill.func_77946_l();
/* 144 */     Ic2Items.ChargepadbatBox = getItem(false);
/* 145 */     Ic2Items.ChargepadcesuUnit = getItem(false);
/* 146 */     Ic2Items.ChargepadmfeUnit = getItem(false);
/* 147 */     Ic2Items.ChargepadmfsUnit = getItem(false);
/* 148 */     Ic2Items.liquidheatexchanger = getItem(false);
/* 149 */     Ic2Items.fermenter = getItem(false);
/* 150 */     Ic2Items.fluidregulator = getItem(false);
/* 151 */     Ic2Items.condenser = getItem(false);
/* 152 */     Ic2Items.steamgenerator = getItem(false);
/* 153 */     Ic2Items.blastfurnace = getItem(false);
/* 154 */     Ic2Items.blockcutter = getItem(false);
/* 155 */     Ic2Items.solardestiller = getItem(false);
/* 156 */     Ic2Items.fluiddistributor = getItem(false);
/* 157 */     Ic2Items.sortingmachine = getItem(false);
/* 158 */     Ic2Items.itembuffer = getItem(false);
/* 159 */     Ic2Items.crophavester = getItem(true);
/* 160 */     Ic2Items.lathe = getItem(false);
/* 161 */     Ic2Items.woodrotor = getItem(false);
/* 162 */     Ic2Items.ironrotor = getItem(false);
/* 163 */     Ic2Items.steelrotor = getItem(false);
/* 164 */     Ic2Items.carbonrotor = getItem(false);
/* 165 */     Ic2Items.woodrotorblade = getItem(false);
/* 166 */     Ic2Items.ironrotorblade = getItem(false);
/* 167 */     Ic2Items.steelrotorblade = getItem(false);
/* 168 */     Ic2Items.carbonrotorblade = getItem(false);
/* 169 */     Ic2Items.steamturbine = getItem(false);
/* 170 */     Ic2Items.steamturbineblade = getItem(false);
/* 171 */     Ic2Items.ironblockcuttingblade = getItem(false);
/* 172 */     Ic2Items.advironblockcuttingblade = getItem(false);
/* 173 */     Ic2Items.diamondblockcuttingblade = getItem(false);
/* 174 */     Ic2Items.ironshaft = getItem(false);
/* 175 */     Ic2Items.steelshaft = getItem(false);
/* 176 */     Ic2Items.heatconductor = getItem(false);
/* 177 */     Ic2Items.copperboiler = getItem(false);
/* 178 */     Ic2Items.casingadviron = Ic2Items.refinedIronIngot.func_77946_l();
/* 179 */     Ic2Items.AshesDust = getItem(false);
/* 180 */     Ic2Items.advIronIngot = Ic2Items.refinedIronIngot.func_77946_l();
/* 181 */     Ic2Items.containmentbox = getItem(false);
/* 182 */     Ic2Items.chargingREBattery = getItem(false);
/* 183 */     Ic2Items.chargingAdvBattery = getItem(false);
/* 184 */     Ic2Items.chargingEnergyCrystal = getItem(false);
/* 185 */     Ic2Items.chargingLapotronCrystal = getItem(false);
/* 186 */     Ic2Items.mfsukit = getItem(false);
/* 187 */     Ic2Items.pahoehoelavaCell = getItem(false);
/* 188 */     Ic2Items.distilledwaterCell = getItem(false);
/* 189 */     Ic2Items.superheatedsteamCell = getItem(false);
/* 190 */     Ic2Items.steamCell = getItem(false);
/* 191 */     Ic2Items.coolantCell = getItem(false);
/* 192 */     Ic2Items.hotcoolantCell = getItem(false);
/* 193 */     Ic2Items.biomassCell = getItem(false);
/* 194 */     Ic2Items.biogasCell = getItem(false);
/* 195 */     Ic2Items.slag = getItem(false);
/* 196 */     Ic2Items.plateadviron = Ic2Items.refinedIronIngot.func_77946_l();
/* 197 */     Ic2Items.denseplateadviron = StackUtil.copyWithSize(Ic2Items.refinedIronIngot.func_77946_l(), 9);
/* 198 */     Ic2Items.turningBlankWood = getItem(false);
/* 199 */     Ic2Items.turningBlankIron = getItem(false);
/* 200 */     Ic2Items.redstoneinvUpgrade = getItem(false);
/* 201 */     Ic2Items.weedingTrowel = getItem(false);
/* 202 */     Ic2Items.weed = getItem(false);
/* 203 */     Ic2Items.smallBronzeDust = getItem(false);
/* 204 */     Ic2Items.smallLapiDust = getItem(false);
/* 205 */     Ic2Items.smallObsidianDust = getItem(false);
/* 206 */     Ic2Items.LathingTool = getItem(false);
/* 207 */     Ic2Items.windmeter = getItem(false);
/* 208 */     Ic2Items.uuMatterCell = Ic2Items.matter;
/* 209 */     Ic2Items.biochaff = Ic2Items.plantBall.func_77946_l();
/* 210 */     Ic2Items.iridiumShard = getItem(false);
/* 211 */     Ic2Items.pullingUpgrade = getItem(false);
/*     */   }
/*     */ 
/*     */   
/*     */   static ItemStack getItem(boolean par1) {
/* 216 */     return ItemNoUse.instance.addItem(par1);
/*     */   }
/*     */ 
/*     */   
/*     */   static ItemStack getOre(String oreName, ItemStack par2) {
/* 221 */     return ItemNoUse.instance.replaceWithOre(oreName, par2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onAfterInit() {
/* 226 */     Ic2Items.leadOre = getOre("oreLead", Ic2Items.leadOre);
/* 227 */     Ic2Items.leadBlock = getOre("blockLead", Ic2Items.leadBlock);
/* 228 */     Ic2Items.leadIngot = getOre("ingotLead", Ic2Items.leadIngot);
/* 229 */     Ic2Items.crushedLeadOre = getOre("dustLead", Ic2Items.crushedLeadOre);
/* 230 */     Ic2Items.purifiedCrushedLeadOre = getOre("dustLead", Ic2Items.purifiedCrushedLeadOre);
/* 231 */     Ic2Items.leadDust = getOre("dustLead", Ic2Items.leadDust);
/* 232 */     Ic2Items.obsidianDust = getOre("dustObsidian", Ic2Items.obsidianDust);
/* 233 */     Ic2Items.sulfurDust = getOre("dustSulfur", Ic2Items.sulfurDust);
/*     */   }
/*     */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\ExpHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */