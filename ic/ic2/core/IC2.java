/*      */ package ic2.core;
/*      */ 
/*      */ import cpw.mods.fml.common.FMLCommonHandler;
/*      */ import cpw.mods.fml.common.FMLLog;
/*      */ import cpw.mods.fml.common.IFuelHandler;
/*      */ import cpw.mods.fml.common.IWorldGenerator;
/*      */ import cpw.mods.fml.common.Loader;
/*      */ import cpw.mods.fml.common.Mod;
/*      */ import cpw.mods.fml.common.Mod.EventHandler;
/*      */ import cpw.mods.fml.common.Mod.Instance;
/*      */ import cpw.mods.fml.common.ModMetadata;
/*      */ import cpw.mods.fml.common.SidedProxy;
/*      */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*      */ import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
/*      */ import cpw.mods.fml.common.event.FMLPostInitializationEvent;
/*      */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*      */ import cpw.mods.fml.common.event.FMLServerStoppedEvent;
/*      */ import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/*      */ import cpw.mods.fml.common.gameevent.PlayerEvent;
/*      */ import cpw.mods.fml.common.gameevent.TickEvent;
/*      */ import cpw.mods.fml.common.registry.EntityRegistry;
/*      */ import cpw.mods.fml.common.registry.GameRegistry;
/*      */ import cpw.mods.fml.relauncher.Side;
/*      */ import ic2.api.energy.EnergyNet;
/*      */ import ic2.api.energy.IEnergyNet;
/*      */ import ic2.api.event.recipe.CannerRecipe;
/*      */ import ic2.api.event.recipe.ElectrolyzerRecipeEvent;
/*      */ import ic2.api.event.recipe.RecipeEvent;
/*      */ import ic2.api.info.IC2Classic;
/*      */ import ic2.api.info.IEnergyValueProvider;
/*      */ import ic2.api.info.IFuelValueProvider;
/*      */ import ic2.api.info.IWindTicker;
/*      */ import ic2.api.info.Info;
/*      */ import ic2.api.item.ElectricItem;
/*      */ import ic2.api.item.IElectricItemManager;
/*      */ import ic2.api.item.IWrenchHandler;
/*      */ import ic2.api.recipe.ICraftingRecipeManager;
/*      */ import ic2.api.recipe.IFluidHeatManager;
/*      */ import ic2.api.recipe.IMachineRecipeManager;
/*      */ import ic2.api.recipe.IRecipeInput;
/*      */ import ic2.api.recipe.Recipes;
/*      */ import ic2.api.tile.ExplosionWhitelist;
/*      */ import ic2.api.util.IKeyboard;
/*      */ import ic2.api.util.Keys;
/*      */ import ic2.core.audio.AudioManager;
/*      */ import ic2.core.block.BlockBarrel;
/*      */ import ic2.core.block.BlockCrop;
/*      */ import ic2.core.block.BlockDynamite;
/*      */ import ic2.core.block.BlockFoam;
/*      */ import ic2.core.block.BlockIC2Door;
/*      */ import ic2.core.block.BlockITNT;
/*      */ import ic2.core.block.BlockMetal;
/*      */ import ic2.core.block.BlockPoleFence;
/*      */ import ic2.core.block.BlockResin;
/*      */ import ic2.core.block.BlockRubLeaves;
/*      */ import ic2.core.block.BlockRubSapling;
/*      */ import ic2.core.block.BlockRubWood;
/*      */ import ic2.core.block.BlockRubberSheet;
/*      */ import ic2.core.block.BlockScaffold;
/*      */ import ic2.core.block.BlockTex;
/*      */ import ic2.core.block.BlockTexGlass;
/*      */ import ic2.core.block.BlockWall;
/*      */ import ic2.core.block.BlockWallTextured;
/*      */ import ic2.core.block.EntityDynamite;
/*      */ import ic2.core.block.EntityItnt;
/*      */ import ic2.core.block.EntityNuke;
/*      */ import ic2.core.block.EntityStickyDynamite;
/*      */ import ic2.core.block.TileEntityBarrel;
/*      */ import ic2.core.block.TileEntityBlock;
/*      */ import ic2.core.block.TileEntityCrop;
/*      */ import ic2.core.block.TileEntityTexturedWall;
/*      */ import ic2.core.block.WorldGenRubTree;
/*      */ import ic2.core.block.crop.IC2Crops;
/*      */ import ic2.core.block.generator.block.BlockConverter;
/*      */ import ic2.core.block.generator.block.BlockGenerator;
/*      */ import ic2.core.block.generator.block.BlockReactorChamber;
/*      */ import ic2.core.block.generator.tileentity.TileEntityBasicTurbine;
/*      */ import ic2.core.block.generator.tileentity.TileEntityGenerator;
/*      */ import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
/*      */ import ic2.core.block.generator.tileentity.TileEntityNuclearReactorElectric;
/*      */ import ic2.core.block.generator.tileentity.TileEntityNuclearReactorSteam;
/*      */ import ic2.core.block.generator.tileentity.TileEntityReactorChamberElectric;
/*      */ import ic2.core.block.generator.tileentity.TileEntityReactorChamberSteam;
/*      */ import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
/*      */ import ic2.core.block.generator.tileentity.TileEntitySolarHV;
/*      */ import ic2.core.block.generator.tileentity.TileEntitySolarLV;
/*      */ import ic2.core.block.generator.tileentity.TileEntitySolarMV;
/*      */ import ic2.core.block.generator.tileentity.TileEntityWaterGenerator;
/*      */ import ic2.core.block.generator.tileentity.TileEntityWaterHV;
/*      */ import ic2.core.block.generator.tileentity.TileEntityWaterLV;
/*      */ import ic2.core.block.generator.tileentity.TileEntityWaterMV;
/*      */ import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
/*      */ import ic2.core.block.machine.BlockMachine;
/*      */ import ic2.core.block.machine.BlockMachine2;
/*      */ import ic2.core.block.machine.BlockMiningPipe;
/*      */ import ic2.core.block.machine.BlockMiningTip;
/*      */ import ic2.core.block.machine.logic.PlannerRegistry;
/*      */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*      */ import ic2.core.block.machine.tileentity.TileEntityCentrifuge;
/*      */ import ic2.core.block.machine.tileentity.TileEntityCharged;
/*      */ import ic2.core.block.machine.tileentity.TileEntityCompacting;
/*      */ import ic2.core.block.machine.tileentity.TileEntityCompressor;
/*      */ import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
/*      */ import ic2.core.block.machine.tileentity.TileEntityCropScanner;
/*      */ import ic2.core.block.machine.tileentity.TileEntityCropmatron;
/*      */ import ic2.core.block.machine.tileentity.TileEntityElecFurnace;
/*      */ import ic2.core.block.machine.tileentity.TileEntityElectricEnchanter;
/*      */ import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
/*      */ import ic2.core.block.machine.tileentity.TileEntityExtractor;
/*      */ import ic2.core.block.machine.tileentity.TileEntityInduction;
/*      */ import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
/*      */ import ic2.core.block.machine.tileentity.TileEntityMacerator;
/*      */ import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
/*      */ import ic2.core.block.machine.tileentity.TileEntityMatter;
/*      */ import ic2.core.block.machine.tileentity.TileEntityMiner;
/*      */ import ic2.core.block.machine.tileentity.TileEntityOreScanner;
/*      */ import ic2.core.block.machine.tileentity.TileEntityPump;
/*      */ import ic2.core.block.machine.tileentity.TileEntityReactorPlanner;
/*      */ import ic2.core.block.machine.tileentity.TileEntityRecycler;
/*      */ import ic2.core.block.machine.tileentity.TileEntityRotary;
/*      */ import ic2.core.block.machine.tileentity.TileEntitySingularity;
/*      */ import ic2.core.block.machine.tileentity.TileEntitySoundBeacon;
/*      */ import ic2.core.block.machine.tileentity.TileEntityTeleporter;
/*      */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*      */ import ic2.core.block.machine.tileentity.TileEntityTesla;
/*      */ import ic2.core.block.machine.tileentity.TileEntityUraniumEnricher;
/*      */ import ic2.core.block.machine.tileentity.TileEntityVacuumCanner;
/*      */ import ic2.core.block.personal.BlockPersonal;
/*      */ import ic2.core.block.personal.TileEntityEnergyOMat;
/*      */ import ic2.core.block.personal.TileEntityFluidOMat;
/*      */ import ic2.core.block.personal.TileEntityPersonalChest;
/*      */ import ic2.core.block.personal.TileEntityTradeOMat;
/*      */ import ic2.core.block.wiring.BlockCable;
/*      */ import ic2.core.block.wiring.BlockChargePads;
/*      */ import ic2.core.block.wiring.BlockElectric;
/*      */ import ic2.core.block.wiring.BlockLuminator;
/*      */ import ic2.core.block.wiring.BlockLuminatorMultipart;
/*      */ import ic2.core.block.wiring.TileEntityAdjustableTransformer;
/*      */ import ic2.core.block.wiring.TileEntityBatteryBox;
/*      */ import ic2.core.block.wiring.TileEntityCable;
/*      */ import ic2.core.block.wiring.TileEntityCableDetector;
/*      */ import ic2.core.block.wiring.TileEntityCableSplitter;
/*      */ import ic2.core.block.wiring.TileEntityChargePadHV;
/*      */ import ic2.core.block.wiring.TileEntityChargePadLV;
/*      */ import ic2.core.block.wiring.TileEntityChargePadMV;
/*      */ import ic2.core.block.wiring.TileEntityChargePadNuclear;
/*      */ import ic2.core.block.wiring.TileEntityCreativeStorage;
/*      */ import ic2.core.block.wiring.TileEntityElectricBatBox;
/*      */ import ic2.core.block.wiring.TileEntityElectricMFE;
/*      */ import ic2.core.block.wiring.TileEntityElectricMFSU;
/*      */ import ic2.core.block.wiring.TileEntityLuminator;
/*      */ import ic2.core.block.wiring.TileEntityLuminatorMultipart;
/*      */ import ic2.core.block.wiring.TileEntityTransformerEV;
/*      */ import ic2.core.block.wiring.TileEntityTransformerHV;
/*      */ import ic2.core.block.wiring.TileEntityTransformerLV;
/*      */ import ic2.core.block.wiring.TileEntityTransformerMV;
/*      */ import ic2.core.energy.EnergyNetGlobal;
/*      */ import ic2.core.fluids.IC2Fluid;
/*      */ import ic2.core.fluids.ItemFluid;
/*      */ import ic2.core.item.BoxableItems;
/*      */ import ic2.core.item.ElectricItemManager;
/*      */ import ic2.core.item.GatewayElectricItemManager;
/*      */ import ic2.core.item.ItemBattery;
/*      */ import ic2.core.item.ItemBatteryDischarged;
/*      */ import ic2.core.item.ItemBatterySU;
/*      */ import ic2.core.item.ItemBooze;
/*      */ import ic2.core.item.ItemCell;
/*      */ import ic2.core.item.ItemChargePadUpgrade;
/*      */ import ic2.core.item.ItemCropSeed;
/*      */ import ic2.core.item.ItemEnrichedUraniumStuff;
/*      */ import ic2.core.item.ItemFertilizer;
/*      */ import ic2.core.item.ItemFuelCanEmpty;
/*      */ import ic2.core.item.ItemFuelCanFilled;
/*      */ import ic2.core.item.ItemGradual;
/*      */ import ic2.core.item.ItemIC2;
/*      */ import ic2.core.item.ItemMug;
/*      */ import ic2.core.item.ItemMugCoffee;
/*      */ import ic2.core.item.ItemNoUse;
/*      */ import ic2.core.item.ItemResin;
/*      */ import ic2.core.item.ItemScrapbox;
/*      */ import ic2.core.item.ItemTea;
/*      */ import ic2.core.item.ItemTerraWart;
/*      */ import ic2.core.item.ItemTinCan;
/*      */ import ic2.core.item.ItemToolbox;
/*      */ import ic2.core.item.ItemUpgradeContainer;
/*      */ import ic2.core.item.ItemUpgradeModule;
/*      */ import ic2.core.item.armor.ItemArmorBatpack;
/*      */ import ic2.core.item.armor.ItemArmorCFPack;
/*      */ import ic2.core.item.armor.ItemArmorHazmat;
/*      */ import ic2.core.item.armor.ItemArmorIC2;
/*      */ import ic2.core.item.armor.ItemArmorJetpack;
/*      */ import ic2.core.item.armor.ItemArmorJetpackCombined;
/*      */ import ic2.core.item.armor.ItemArmorJetpackElectric;
/*      */ import ic2.core.item.armor.ItemArmorJetpackNuclear;
/*      */ import ic2.core.item.armor.ItemArmorJetpackNuclearCombined;
/*      */ import ic2.core.item.armor.ItemArmorJetpackQuantumSuit;
/*      */ import ic2.core.item.armor.ItemArmorLappack;
/*      */ import ic2.core.item.armor.ItemArmorNanoSuit;
/*      */ import ic2.core.item.armor.ItemArmorNightvisionGoggles;
/*      */ import ic2.core.item.armor.ItemArmorQuantumSuit;
/*      */ import ic2.core.item.armor.ItemArmorSolarHelmet;
/*      */ import ic2.core.item.armor.ItemArmorStaticBoots;
/*      */ import ic2.core.item.block.ItemBarrel;
/*      */ import ic2.core.item.block.ItemBlockChamber;
/*      */ import ic2.core.item.block.ItemBlockChargePad;
/*      */ import ic2.core.item.block.ItemBlockConverter;
/*      */ import ic2.core.item.block.ItemBlockCrop;
/*      */ import ic2.core.item.block.ItemBlockLuminatorMultipart;
/*      */ import ic2.core.item.block.ItemBlockMetal;
/*      */ import ic2.core.item.block.ItemBlockRare;
/*      */ import ic2.core.item.block.ItemBlockScaffold;
/*      */ import ic2.core.item.block.ItemCable;
/*      */ import ic2.core.item.block.ItemDynamite;
/*      */ import ic2.core.item.block.ItemElectricBlock;
/*      */ import ic2.core.item.block.ItemGenerator;
/*      */ import ic2.core.item.block.ItemIC2Door;
/*      */ import ic2.core.item.block.ItemMachine;
/*      */ import ic2.core.item.block.ItemMachine2;
/*      */ import ic2.core.item.block.ItemPersonalBlock;
/*      */ import ic2.core.item.block.ItemRubLeaves;
/*      */ import ic2.core.item.boats.EntityCarboneBoat;
/*      */ import ic2.core.item.boats.EntityElectricBoat;
/*      */ import ic2.core.item.boats.EntityRubberBoat;
/*      */ import ic2.core.item.boats.ItemIC2Boat;
/*      */ import ic2.core.item.reactor.ItemReactorCondensator;
/*      */ import ic2.core.item.reactor.ItemReactorDepletedEnrichedUranium;
/*      */ import ic2.core.item.reactor.ItemReactorDepletedUranium;
/*      */ import ic2.core.item.reactor.ItemReactorElectricVent;
/*      */ import ic2.core.item.reactor.ItemReactorEnrichUranium;
/*      */ import ic2.core.item.reactor.ItemReactorHeatStorage;
/*      */ import ic2.core.item.reactor.ItemReactorHeatSwitch;
/*      */ import ic2.core.item.reactor.ItemReactorHeatpack;
/*      */ import ic2.core.item.reactor.ItemReactorPlating;
/*      */ import ic2.core.item.reactor.ItemReactorReflector;
/*      */ import ic2.core.item.reactor.ItemReactorUranium;
/*      */ import ic2.core.item.reactor.ItemReactorVent;
/*      */ import ic2.core.item.reactor.ItemReactorVentSpread;
/*      */ import ic2.core.item.reactor.ItemReactorVentSteam;
/*      */ import ic2.core.item.tfbp.ItemTFBPBiome;
/*      */ import ic2.core.item.tfbp.ItemTFBPChilling;
/*      */ import ic2.core.item.tfbp.ItemTFBPCultivation;
/*      */ import ic2.core.item.tfbp.ItemTFBPDesertification;
/*      */ import ic2.core.item.tfbp.ItemTFBPFlatification;
/*      */ import ic2.core.item.tfbp.ItemTFBPIrrigation;
/*      */ import ic2.core.item.tfbp.ItemTFBPMushroom;
/*      */ import ic2.core.item.tool.EntityMiningLaser;
/*      */ import ic2.core.item.tool.ItemCropnalyzer;
/*      */ import ic2.core.item.tool.ItemDebug;
/*      */ import ic2.core.item.tool.ItemElectricToolChainsaw;
/*      */ import ic2.core.item.tool.ItemElectricToolDDrill;
/*      */ import ic2.core.item.tool.ItemElectricToolDrill;
/*      */ import ic2.core.item.tool.ItemElectricToolHoe;
/*      */ import ic2.core.item.tool.ItemFrequencyTransmitter;
/*      */ import ic2.core.item.tool.ItemIC2Axe;
/*      */ import ic2.core.item.tool.ItemIC2Hoe;
/*      */ import ic2.core.item.tool.ItemIC2Pickaxe;
/*      */ import ic2.core.item.tool.ItemIC2Spade;
/*      */ import ic2.core.item.tool.ItemIC2Sword;
/*      */ import ic2.core.item.tool.ItemNanoSaber;
/*      */ import ic2.core.item.tool.ItemRemote;
/*      */ import ic2.core.item.tool.ItemScanner;
/*      */ import ic2.core.item.tool.ItemScannerAdv;
/*      */ import ic2.core.item.tool.ItemScanners;
/*      */ import ic2.core.item.tool.ItemSprayer;
/*      */ import ic2.core.item.tool.ItemTextureCopier;
/*      */ import ic2.core.item.tool.ItemToolCutter;
/*      */ import ic2.core.item.tool.ItemToolMeter;
/*      */ import ic2.core.item.tool.ItemToolMiningLaser;
/*      */ import ic2.core.item.tool.ItemToolPainter;
/*      */ import ic2.core.item.tool.ItemToolWrench;
/*      */ import ic2.core.item.tool.ItemToolWrenchElectric;
/*      */ import ic2.core.item.tool.ItemTreetap;
/*      */ import ic2.core.item.tool.ItemTreetapElectric;
/*      */ import ic2.core.network.NetworkManager;
/*      */ import ic2.core.util.BlockIdCounter;
/*      */ import ic2.core.util.BlockObject;
/*      */ import ic2.core.util.ChargeTooltipHandler;
/*      */ import ic2.core.util.EventManager;
/*      */ import ic2.core.util.FluidHeatManager;
/*      */ import ic2.core.util.IExtraData;
/*      */ import ic2.core.util.ItemIdCounter;
/*      */ import ic2.core.util.ItemInfo;
/*      */ import ic2.core.util.ItemObject;
/*      */ import ic2.core.util.Keyboard;
/*      */ import ic2.core.util.SideGateway;
/*      */ import ic2.core.util.StackUtil;
/*      */ import ic2.core.util.TextureIndex;
/*      */ import ic2.core.util.WindTicker;
/*      */ import java.io.File;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ import java.util.Random;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.EnumRarity;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemBlock;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.crafting.CraftingManager;
/*      */ import net.minecraft.item.crafting.FurnaceRecipes;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityFurnace;
/*      */ import net.minecraft.world.ChunkCoordIntPair;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*      */ import net.minecraftforge.common.BiomeDictionary;
/*      */ import net.minecraftforge.common.MinecraftForge;
/*      */ import net.minecraftforge.common.config.Configuration;
/*      */ import net.minecraftforge.common.config.Property;
/*      */ import net.minecraftforge.common.util.EnumHelper;
/*      */ import net.minecraftforge.event.world.ChunkWatchEvent;
/*      */ import net.minecraftforge.event.world.WorldEvent;
/*      */ import net.minecraftforge.fluids.Fluid;
/*      */ import net.minecraftforge.fluids.FluidContainerRegistry;
/*      */ import net.minecraftforge.fluids.FluidRegistry;
/*      */ import net.minecraftforge.fluids.FluidStack;
/*      */ import net.minecraftforge.oredict.OreDictionary;
/*      */ import net.minecraftforge.oredict.RecipeSorter;
/*      */ import net.minecraftforge.oredict.ShapelessOreRecipe;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Mod(modid = "IC2", name = "IndustrialCraft 2 Classic", version = "2.2.999", useMetadata = true, certificateFingerprint = "de041f9f6187debbc77034a344134053277aa3b0")
/*      */ public class IC2
/*      */   implements IWorldGenerator, IFuelHandler, IWrenchHandler.IWrenchRegistry
/*      */ {
/*  356 */   public static String VERSION = "2.0.395-experimental";
/*      */   
/*      */   public static boolean BETA = true;
/*      */   
/*      */   @Instance("IC2")
/*      */   private static IC2 instance;
/*      */   
/*      */   @SidedProxy(clientSide = "ic2.core.PlatformClient", serverSide = "ic2.core.Platform")
/*      */   public static Platform platform;
/*      */   
/*  366 */   public static SideGateway<NetworkManager> network = new SideGateway("ic2.core.network.NetworkManager", "ic2.core.network.NetworkManagerClient");
/*      */   
/*      */   @SidedProxy(clientSide = "ic2.core.util.KeyboardClient", serverSide = "ic2.core.util.Keyboard")
/*      */   public static Keyboard keyboard;
/*      */   
/*      */   @SidedProxy(clientSide = "ic2.core.audio.AudioManagerClient", serverSide = "ic2.core.audio.AudioManager")
/*      */   public static AudioManager audioManager;
/*      */   
/*      */   @SidedProxy(clientSide = "ic2.core.util.TextureIndexClient", serverSide = "ic2.core.util.TextureIndex")
/*      */   public static TextureIndex textureIndex;
/*      */   
/*      */   public static Logger log;
/*      */   public static IC2Achievements achievements;
/*      */   public static int cableRenderId;
/*      */   public static int fenceRenderId;
/*      */   public static int miningPipeRenderId;
/*      */   public static int luminatorRenderId;
/*      */   public static int cropRenderId;
/*  384 */   public static Random random = new Random();
/*      */   public static int windStrength;
/*      */   public static int windTicker;
/*  387 */   public static Map<Block, Map<Integer, Integer>> valuableOres = new HashMap<>();
/*  388 */   public static Map<Integer, List<ItemStack>> oreValues = new HashMap<>();
/*      */   public static boolean enableCraftingBucket = true;
/*      */   public static boolean enableCraftingCoin = true;
/*      */   public static boolean enableCraftingGlowstoneDust = true;
/*      */   public static boolean enableCraftingGunpowder = true;
/*      */   public static boolean enableCraftingITnt = true;
/*      */   public static boolean enableCraftingNuke = true;
/*      */   public static boolean enableCraftingRail = true;
/*      */   public static boolean enableDynamicIdAllocation = true;
/*      */   public static boolean enableLoggingWrench = true;
/*      */   public static boolean enableSecretRecipeHiding = true;
/*      */   public static boolean enableQuantumSpeedOnSprint = true;
/*      */   public static boolean enableMinerLapotron = true;
/*      */   public static boolean enableTeleporterInventory = true;
/*      */   public static boolean enableBurningScrap = true;
/*      */   public static boolean enableWorldGenTreeRubber = true;
/*      */   public static boolean enableWorldGenOreCopper = true;
/*      */   public static boolean enableWorldGenOreTin = true;
/*      */   public static boolean enableWorldGenOreUranium = true;
/*      */   public static boolean displayNoUseItems = false;
/*      */   public static boolean enableCustomWorldSimulator = true;
/*      */   public static boolean enableCustomIdAssigning = false;
/*      */   public static boolean enableIC2EasyMode = false;
/*      */   public static boolean enableCropHelper = true;
/*      */   public static boolean losslessAddonWrenches = false;
/*      */   public static boolean guiDissablesKeys = false;
/*      */   public static boolean chargePadUpgradeAnySide = false;
/*      */   public static boolean enableHarderEnrichedUran = false;
/*      */   public static boolean enableEnrichedUran = true;
/*      */   public static boolean enableSpecialElectricArmor = true;
/*      */   public static boolean dissableBronzeStuff = false;
/*      */   public static boolean enableOreDictOverride = false;
/*      */   public static String ironName;
/*  421 */   public static float explosionPowerNuke = 35.0F;
/*  422 */   public static float explosionPowerReactorMax = 45.0F;
/*  423 */   public static float electricSuitAbsorbtionScale = 1.0F;
/*  424 */   public static float electricSuitEnergyCostModifier = 1.0F;
/*  425 */   public static int energyGeneratorBase = 10;
/*  426 */   public static int energyGeneratorGeo = 20;
/*  427 */   public static int energyGeneratorWater = 100;
/*  428 */   public static int energyGeneratorSolar = 100;
/*  429 */   public static int energyGeneratorWind = 100;
/*  430 */   public static int energyGeneratorNuclear = 5;
/*  431 */   public static int rfPerEU = 40;
/*  432 */   public static int rfForEU = 4;
/*  433 */   public static int reactorPlannerMaxTicks = 1000;
/*  434 */   public static int maxWindChangeDelay = 6000;
/*  435 */   public static int radiationPotionId = 24;
/*      */   public static boolean suddenlyHoes = false;
/*      */   public static boolean seasonal = false;
/*      */   private static boolean showDisclaimer = false;
/*      */   public static boolean enableSteamReactor = false;
/*  440 */   public static float oreDensityFactor = 1.0F;
/*  441 */   public static int maxOreValue = 0;
/*      */   public static boolean initialized = false;
/*  443 */   public static CreativeTabIC2 tabIC2 = new CreativeTabIC2();
/*  444 */   private static String[] dyes = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
/*      */   private static Field dropChances;
/*  446 */   private static Map<World, ArrayDeque<ITickCallback>> singleTickCallbacks = new HashMap<>();
/*  447 */   private static Map<World, HashSet<ITickCallback>> continuousTickCallbacks = new HashMap<>();
/*  448 */   private static Map<World, Boolean> continuousTickCallbacksInUse = new HashMap<>();
/*  449 */   private static Map<World, List<ITickCallback>> continuousTickCallbacksToAdd = new HashMap<>();
/*  450 */   private static Map<World, List<ITickCallback>> continuousTickCallbacksToRemove = new HashMap<>();
/*  451 */   public static Map<String, ISubModul> modul = new HashMap<>();
/*  452 */   public static HashMap<String, ItemObject> blockedItems = new HashMap<>();
/*  453 */   public static HashMap<String, BlockObject> blockedBlocks = new HashMap<>();
/*  454 */   public static int networkProtocolVersion = 1;
/*      */   
/*      */   public static Configuration config;
/*      */   public static BlockIdCounter blockIDs;
/*      */   public static ItemIdCounter itemIDs;
/*  459 */   public static List<IWrenchHandler> handlers = new ArrayList<>();
/*      */ 
/*      */   
/*      */   public IC2() {
/*  463 */     instance = this;
/*  464 */     Info.ic2ModInstance = this;
/*      */   }
/*      */ 
/*      */   
/*      */   public static IC2 getInstance() {
/*  469 */     return instance;
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void load(FMLPreInitializationEvent event) {
/*  475 */     ModMetadata meta = event.getModMetadata();
/*  476 */     meta.authorList = Arrays.asList(new String[] { "Alblaka", "Speiger", "Player", "RichardG", "Thunderdark", "GregoriusT", "alexthesax", "Drashian", "Elementalist", "Feanturi", "Lurch1985", "SirusKing", "tahu44" });
/*  477 */     meta.autogenerated = false;
/*  478 */     meta.credits = "Contributions by many people";
/*  479 */     meta.modId = "IC2";
/*  480 */     meta.name = "Industrial Craft Classic";
/*  481 */     meta.updateUrl = "https://github.com/TinyModularThings/IC2Classic/releases";
/*  482 */     meta.url = "http://www.industrial-craft.net";
/*  483 */     meta.version = "2.2.999";
/*      */     
/*  485 */     log = event.getModLog();
/*      */     
/*      */     try {
/*  488 */       File configFile = new File(new File(platform.getMinecraftDir(), "config"), "IC2.cfg");
/*  489 */       config = new Configuration(configFile);
/*  490 */       config.load();
/*  491 */       log.info("Config loaded from " + configFile.getAbsolutePath());
/*      */     }
/*  493 */     catch (Exception e) {
/*      */       
/*  495 */       log.warn("Error while trying to access configuration! " + e);
/*  496 */       config = null;
/*      */     } 
/*  498 */     if (config != null) {
/*      */       
/*  500 */       Property prop = config.get("general", "enable ID selfcontroll", enableCustomIdAssigning);
/*  501 */       prop.comment = "This allows you to setup your Block/ItemIDs by yourself. That is only a feature to prevent world corruption. Dissabled by default. (Forge will kill me for that)";
/*  502 */       enableCustomIdAssigning = Boolean.parseBoolean(prop.getString());
/*  503 */       prop = config.get("general", "Radiation Potion Id", radiationPotionId);
/*  504 */       prop.comment = "Assigns the Radiation Potion Id";
/*  505 */       radiationPotionId = Integer.parseInt(prop.getString());
/*  506 */       prop = config.get("general", "enableCraftingBucket", enableCraftingBucket);
/*  507 */       prop.comment = "Enable crafting of buckets out of tin";
/*  508 */       enableCraftingBucket = Boolean.parseBoolean(prop.getString());
/*  509 */       prop = config.get("general", "enableCraftingCoin", enableCraftingCoin);
/*  510 */       prop.comment = "Enable crafting of Industrial Credit coins";
/*  511 */       enableCraftingCoin = Boolean.parseBoolean(prop.getString());
/*  512 */       prop = config.get("general", "enableCraftingGlowstoneDust", enableCraftingGlowstoneDust);
/*  513 */       prop.comment = "Enable crafting of glowstone dust out of dusts";
/*  514 */       enableCraftingGlowstoneDust = Boolean.parseBoolean(prop.getString());
/*  515 */       prop = config.get("general", "enableCraftingGunpowder", enableCraftingGunpowder);
/*  516 */       prop.comment = "Enable crafting of gunpowder out of dusts";
/*  517 */       enableCraftingGunpowder = Boolean.parseBoolean(prop.getString());
/*  518 */       prop = config.get("general", "enableCraftingITnt", enableCraftingITnt);
/*  519 */       prop.comment = "Enable crafting of ITNT";
/*  520 */       enableCraftingITnt = Boolean.parseBoolean(prop.getString());
/*  521 */       prop = config.get("general", "enableCraftingNuke", enableCraftingNuke);
/*  522 */       prop.comment = "Enable crafting of nukes";
/*  523 */       enableCraftingNuke = Boolean.parseBoolean(prop.getString());
/*  524 */       prop = config.get("general", "enableCraftingRail", enableCraftingRail);
/*  525 */       prop.comment = "Enable crafting of rails out of bronze";
/*  526 */       enableCraftingRail = Boolean.parseBoolean(prop.getString());
/*  527 */       prop = config.get("general", "enableSecretRecipeHiding", enableSecretRecipeHiding);
/*  528 */       prop.comment = "Enable hiding of secret recipes in CraftGuide/NEI";
/*  529 */       enableSecretRecipeHiding = Boolean.parseBoolean(prop.getString());
/*  530 */       prop = config.get("general", "enableQuantumSpeedOnSprint", enableQuantumSpeedOnSprint);
/*  531 */       prop.comment = "Enable activation of the quantum leggings' speed boost when sprinting instead of holding the boost key";
/*  532 */       enableQuantumSpeedOnSprint = Boolean.parseBoolean(prop.getString());
/*  533 */       prop = config.get("general", "enableMinerLapotron", enableMinerLapotron);
/*  534 */       prop.comment = "Enable usage of lapotron crystals on miners";
/*  535 */       enableMinerLapotron = Boolean.parseBoolean(prop.getString());
/*  536 */       prop = config.get("general", "enableTeleporterInventory", enableTeleporterInventory);
/*  537 */       prop.comment = "Enable calculation of inventory weight when going through a teleporter";
/*  538 */       enableTeleporterInventory = Boolean.parseBoolean(prop.getString());
/*  539 */       prop = config.get("general", "enableBurningScrap", enableBurningScrap);
/*  540 */       prop.comment = "Enable burning of scrap in a generator";
/*  541 */       enableBurningScrap = Boolean.parseBoolean(prop.getString());
/*  542 */       prop = config.get("general", "enableLoggingWrench", enableLoggingWrench);
/*  543 */       prop.comment = "Enable logging of players when they remove a machine using a wrench";
/*  544 */       enableLoggingWrench = Boolean.parseBoolean(prop.getString());
/*  545 */       prop = config.get("general", "enableWorldGenTreeRubber", enableWorldGenTreeRubber);
/*  546 */       prop.comment = "Enable generation of rubber trees in the world";
/*  547 */       enableWorldGenTreeRubber = Boolean.parseBoolean(prop.getString());
/*  548 */       prop = config.get("general", "enableWorldGenOreCopper", enableWorldGenOreCopper);
/*  549 */       prop.comment = "Enable generation of copper in the world";
/*  550 */       enableWorldGenOreCopper = Boolean.parseBoolean(prop.getString());
/*  551 */       prop = config.get("general", "enableWorldGenOreTin", enableWorldGenOreTin);
/*  552 */       prop.comment = "Enable generation of tin in the world";
/*  553 */       enableWorldGenOreTin = Boolean.parseBoolean(prop.getString());
/*  554 */       prop = config.get("general", "enableWorldGenOreUranium", enableWorldGenOreUranium);
/*  555 */       prop.comment = "Enable generation of uranium in the world";
/*  556 */       enableWorldGenOreUranium = Boolean.parseBoolean(prop.getString());
/*  557 */       prop = config.get("general", "explosionPowerNuke", Float.toString(explosionPowerNuke));
/*  558 */       prop.comment = "Explosion power of a nuke, where TNT is 4";
/*  559 */       explosionPowerNuke = Float.parseFloat(prop.getString());
/*  560 */       prop = config.get("general", "explosionPowerReactorMax", Float.toString(explosionPowerReactorMax));
/*  561 */       prop.comment = "Maximum explosion power of a nuclear reactor, where TNT is 4";
/*  562 */       explosionPowerReactorMax = Float.parseFloat(prop.getString());
/*  563 */       prop = config.get("general", "energyGeneratorBase", energyGeneratorBase);
/*  564 */       prop.comment = "Base energy generation values - increase those for higher energy yield";
/*  565 */       energyGeneratorBase = Integer.parseInt(prop.getString());
/*  566 */       energyGeneratorGeo = Integer.parseInt(config.get("general", "energyGeneratorGeo", energyGeneratorGeo).getString());
/*  567 */       energyGeneratorWater = Integer.parseInt(config.get("general", "energyGeneratorWater", energyGeneratorWater).getString());
/*  568 */       energyGeneratorSolar = Integer.parseInt(config.get("general", "energyGeneratorSolar", energyGeneratorSolar).getString());
/*  569 */       energyGeneratorWind = Integer.parseInt(config.get("general", "energyGeneratorWind", energyGeneratorWind).getString());
/*  570 */       energyGeneratorNuclear = Integer.parseInt(config.get("general", "energyGeneratorNuclear", energyGeneratorNuclear).getString());
/*  571 */       prop = config.get("general", "valuableOres", getValuableOreString());
/*  572 */       prop.comment = "List of valuable ores the miner should look for. Comma separated, format is id-metadata:value (id can be a number or a Block name) where value should be at least 1 to be considered by the miner";
/*  573 */       setValuableOreFromString(prop.getString());
/*  574 */       prop = config.get("general", "oreDensityFactor", Float.toString(oreDensityFactor));
/*  575 */       prop.comment = "Factor to adjust the ore generation rate";
/*  576 */       oreDensityFactor = Float.parseFloat(prop.getString());
/*  577 */       prop = config.get("general", "Display All No IC2 ExpItems", false);
/*  578 */       displayNoUseItems = Boolean.parseBoolean(prop.getString());
/*  579 */       prop = config.get("general", "Max Wind Change Delay", 6000);
/*  580 */       prop.comment = "This Counter Effects how long it can max take (it will be randomly shuffeld) that the wind is changing. Default: Every 5 Min (6000 Ticks)";
/*  581 */       maxWindChangeDelay = Integer.parseInt(prop.getString());
/*  582 */       prop = config.get("general", "Use Custom Wind Simulator", true);
/*  583 */       prop.comment = "The Custom Wind Simulator calculates Windspeed definded on Random Caclulations You can effect the time how often it will change.";
/*  584 */       enableCustomWorldSimulator = Boolean.parseBoolean(prop.getString());
/*  585 */       prop = config.get("general", "IC2 Easy Mode", false);
/*  586 */       prop.comment = "IC2 Easy mode is simply IC2 without explosions. But if you try to Overpower machines/Storages then they stop accepting power, some addons get effected by that maybe!";
/*  587 */       enableIC2EasyMode = Boolean.parseBoolean(prop.getString());
/*  588 */       prop = config.get("general", "Steel Mode", false);
/*  589 */       prop.comment = "Every Recipe that would require Refined Iron now needs Steel, also effects addons. Keep that in mind";
/*  590 */       ironName = Boolean.parseBoolean(prop.getString()) ? "ingotSteel" : "ingotRefinedIron";
/*  591 */       prop = config.get("general", "Crop Breeding Helper", true);
/*  592 */       prop.comment = "Enables the Nei Plugin to add extra information to the Cropseeds which make it easier for you to Breed Crops";
/*  593 */       enableCropHelper = Boolean.parseBoolean(prop.getString());
/*  594 */       prop = config.get("general", "Electric Armor Damage Absorbtion", 1.0D);
/*  595 */       prop.comment = "Balacing Config. Allows you to decrease/Increase the Quantum&Nano Armor Damage Absorbtion 1F = Standart (100% of their Capabilities) 0.1F or smaller (10% of their Capabilities) limit up does not exsist";
/*  596 */       electricSuitAbsorbtionScale = (Float.parseFloat(prop.getString()) < 0.1D) ? 0.1F : Float.parseFloat(prop.getString());
/*  597 */       prop = config.get("general", "Lossless None IC2 Wrenches", false);
/*  598 */       prop.comment = "Config for None IC2 Wrenches to be lossless Mode. By default disabled!";
/*  599 */       losslessAddonWrenches = Boolean.parseBoolean(prop.getString());
/*  600 */       prop = config.get("general", "RF_for_EU", 4);
/*  601 */       prop.comment = "The amount you get when you convert EU to RF";
/*  602 */       rfForEU = Integer.parseInt(prop.getString());
/*  603 */       prop = config.get("general", "RF_per_EU", 40);
/*  604 */       prop.comment = "The amount you need to convert RF to EU";
/*  605 */       rfPerEU = Integer.parseInt(prop.getString());
/*  606 */       prop = config.get("general", "Gui Dissables Keybinding", false);
/*  607 */       prop.comment = "IC2 Keybinding Detection is active even if a gui is Open. (Flying while having Inv Open) set to true to dissable that";
/*  608 */       guiDissablesKeys = Boolean.parseBoolean(prop.getString());
/*  609 */       prop = config.get("general", "ChargePads AnySide Upgrade", false);
/*  610 */       prop.comment = "If this config is true then you can use the ChargePad UpgradeKit on any side";
/*  611 */       chargePadUpgradeAnySide = Boolean.parseBoolean(prop.getString());
/*  612 */       prop = config.get("general", "Max Reactor Planner Ticks", 1000);
/*  613 */       prop.comment = "Sets the Max Ticks per Tick, Min: 10";
/*  614 */       reactorPlannerMaxTicks = Integer.parseInt(prop.getString());
/*  615 */       prop = config.get("general", "Harder Enriched Uran", false);
/*  616 */       prop.comment = "To make the Creation of Enriched Uran Rods harder";
/*  617 */       enableHarderEnrichedUran = Boolean.parseBoolean(prop.getString());
/*  618 */       prop = config.get("general", "Enable Enriched Uranium", true);
/*  619 */       prop.comment = "Enables the Recipes for the Enriched Uranium";
/*  620 */       enableEnrichedUran = Boolean.parseBoolean(prop.getString());
/*  621 */       prop = config.get("general", "Enable Electric Armor Ability", true);
/*  622 */       prop.comment = "Enables that Electric Damage (including the downsides) charges Electric Armor";
/*  623 */       enableSpecialElectricArmor = Boolean.parseBoolean(prop.getString());
/*  624 */       prop = config.get("general", "Electric Armor Cost Modifier", 1.0D);
/*  625 */       prop.comment = "Allows you to Change the Electric Cost per Damage of the Armor";
/*  626 */       electricSuitAbsorbtionScale = Float.parseFloat(prop.getString());
/*  627 */       prop = config.get("general", "Disable Bronze Tools", false);
/*  628 */       prop.comment = "Disables Bronze Tools (Not wrench) and Armor for crafting/loot/CreativeTabs";
/*  629 */       dissableBronzeStuff = Boolean.parseBoolean(prop.getString());
/*  630 */       prop = config.get("general", "Enable Plates = Ingots", false);
/*  631 */       prop.comment = "Enables that ic2Exp plates get their classic ingot version in the oredictionary. It can break the balance with other mods. Enable at own choice. (Its there for a reason)";
/*  632 */       enableOreDictOverride = Boolean.parseBoolean(prop.getString());
/*  633 */       config.save();
/*      */     } 
/*  635 */     if (rfForEU > rfPerEU)
/*      */     {
/*  637 */       rfForEU = rfPerEU;
/*      */     }
/*  639 */     if (reactorPlannerMaxTicks < 10)
/*      */     {
/*  641 */       reactorPlannerMaxTicks = 10;
/*      */     }
/*  643 */     audioManager.initialize(config);
/*  644 */     itemIDs = new ItemIdCounter(ItemIdCounter.getConfig(config, "idsettings", 5000));
/*  645 */     blockIDs = new BlockIdCounter(BlockIdCounter.getConfig(config, "idsettings", 3500));
/*  646 */     Keys.instance = (IKeyboard)keyboard;
/*  647 */     keyboard.init();
/*  648 */     Ic2Icons.init();
/*      */     
/*  650 */     ElectricItem.manager = (IElectricItemManager)new GatewayElectricItemManager();
/*  651 */     ElectricItem.rawManager = (IElectricItemManager)new ElectricItemManager();
/*      */     
/*  653 */     ItemInfo info = new ItemInfo();
/*  654 */     Info.itemEnergy = (IEnergyValueProvider)info;
/*  655 */     Info.itemFuel = (IFuelValueProvider)info;
/*  656 */     if (enableCustomWorldSimulator)
/*      */     {
/*  658 */       IC2Classic.windNetwork = (IWindTicker)new WindTicker();
/*      */     }
/*  660 */     loadModules();
/*      */     
/*  662 */     EnumHelper.addToolMaterial("IC2_BRONZE", 2, 350, 6.0F, 2.0F, 13);
/*  663 */     ItemArmor.ArmorMaterial bronzeArmorMaterial = EnumHelper.addArmorMaterial("IC2_BRONZE", 15, new int[] { 3, 8, 6, 3 }, 9);
/*  664 */     ItemArmor.ArmorMaterial alloyArmorMaterial = EnumHelper.addArmorMaterial("IC2_ALLOY", 50, new int[] { 4, 9, 7, 4 }, 12);
/*      */     
/*  666 */     loadState(ISubModul.LoadingState.BeforeObjectLoad);
/*      */     
/*  668 */     createBlock((Block)new BlockScaffold(Material.field_151573_f), (Class)ItemBlockScaffold.class, "ironScaffold");
/*  669 */     createBlock((Block)new BlockBarrel(), (Class)ItemBlockRare.class, "blockBarrel");
/*  670 */     createBlock((Block)new BlockCrop(), (Class)ItemBlockCrop.class, "crop");
/*  671 */     createBlock((new BlockLuminator()).func_149663_c("blockLuminator"), (Class)ItemBlockRare.class, "luminator");
/*  672 */     createBlock((Block)new BlockScaffold(Material.field_151575_d), (Class)ItemBlockScaffold.class, "scaffold");
/*  673 */     createBlock((Block)new BlockWall(188), (Class)ItemBlockRare.class, "constructionFoamWall");
/*  674 */     createBlock((Block)new BlockFoam(37), (Class)ItemBlockRare.class, "constructionFoam");
/*  675 */     createBlock((Block)new BlockMachine2(), (Class)ItemMachine2.class, "blockMachine2");
/*  676 */     createBlock((Block)new BlockMetal(), (Class)ItemBlockMetal.class, "blockMetal");
/*  677 */     createBlock((Block)new BlockMiningPipe(35), (Class)ItemBlockRare.class, "miningPipe");
/*  678 */     createBlock((new BlockLuminatorMultipart()).func_149663_c("blockLuminatorMultiPart"), (Class)ItemBlockLuminatorMultipart.class, "LuminatorMultipartBlock");
/*  679 */     createBlock((Block)new BlockElectric(), (Class)ItemElectricBlock.class, "blockElectric");
/*  680 */     createBlock((Block)new BlockCable(), (Class)ItemBlockRare.class, "cableBlock");
/*  681 */     Ic2Items.reinforcedDoorBlock = new ItemStack(createBlock((new BlockIC2Door(Material.field_151573_f)).func_149711_c(50.0F).func_149752_b(150.0F).func_149672_a(Block.field_149777_j).func_149663_c("blockDoorAlloy"), ItemBlock.class, "reinforcedDoorBlock"));
/*  682 */     Ic2Items.reinforcedGlass = new ItemStack(createBlock((new BlockTexGlass(13, Material.field_151592_s, false)).func_149711_c(5.0F).func_149752_b(150.0F).func_149672_a(Block.field_149778_k).func_149663_c("blockAlloyGlass").func_149647_a(tabIC2), ItemBlock.class, "reinforcedGlass"));
/*  683 */     Ic2Items.reinforcedStone = new ItemStack(createBlock((new BlockTex(12, Material.field_151573_f)).func_149711_c(80.0F).func_149752_b(150.0F).func_149672_a(Block.field_149777_j).func_149663_c("blockAlloy").func_149647_a(tabIC2), ItemBlock.class, "reinforcedStone"));
/*  684 */     createBlock((Block)new BlockPoleFence(1), (Class)ItemBlockRare.class, "ironFence");
/*  685 */     createBlock((Block)new BlockReactorChamber(), (Class)ItemBlockChamber.class, "reactorChamber");
/*  686 */     createBlock((new BlockRubberSheet(40)).func_149647_a(tabIC2), (Class)ItemBlockRare.class, "rubberTrampoline");
/*  687 */     Ic2Items.dynamiteStickWithRemote = new ItemStack(createBlock((new BlockDynamite(56)).func_149711_c(0.0F).func_149672_a(Block.field_149779_h).func_149663_c("blockDynamiteRemote"), (Class)ItemBlockRare.class, "dynamiteStickWithRemote"));
/*  688 */     Ic2Items.dynamiteStick = new ItemStack(createBlock((new BlockDynamite(57)).func_149711_c(0.0F).func_149672_a(Block.field_149779_h).func_149663_c("blockDynamite"), (Class)ItemBlockRare.class, "dynamiteStick"));
/*  689 */     Ic2Items.nuke = new ItemStack(createBlock((new BlockITNT(false)).func_149711_c(0.0F).func_149672_a(Block.field_149779_h).func_149663_c("blockNuke"), (Class)ItemBlockRare.class, "nuke"));
/*  690 */     Ic2Items.industrialTnt = new ItemStack(createBlock((new BlockITNT(true)).func_149711_c(0.0F).func_149672_a(Block.field_149779_h).func_149663_c("blockITNT"), (Class)ItemBlockRare.class, "industrialTNT"));
/*  691 */     createBlock((Block)new BlockResin(43), ItemBlock.class, "resinBlock");
/*      */     
/*  693 */     if (enableWorldGenTreeRubber) {
/*      */       
/*  695 */       createBlock((Block)new BlockRubSapling(), (Class)ItemBlockRare.class, "rubberSapling");
/*  696 */       createBlock((new BlockRubLeaves()).func_149647_a(tabIC2), (Class)ItemRubLeaves.class, "rubberLeaves");
/*  697 */       createBlock((new BlockRubWood()).func_149647_a(tabIC2), (Class)ItemBlockRare.class, "rubberWood");
/*      */     } 
/*      */     
/*  700 */     createBlock((Block)new BlockMiningTip(36), (Class)ItemBlockRare.class, "miningPipeTip");
/*  701 */     createBlock((Block)new BlockPersonal(), (Class)ItemPersonalBlock.class, "blockPersonal");
/*  702 */     createBlock((Block)new BlockGenerator(), (Class)ItemGenerator.class, "blockGenerator");
/*      */     
/*  704 */     if (enableWorldGenOreUranium)
/*      */     {
/*  706 */       Ic2Items.uraniumOre = new ItemStack(createBlock((new BlockTex(34, Material.field_151576_e)).func_149711_c(4.0F).func_149752_b(6.0F).func_149663_c("blockOreUran").func_149647_a(tabIC2), ItemBlock.class, "uraniumOre"));
/*      */     }
/*  708 */     if (enableWorldGenOreTin)
/*      */     {
/*  710 */       Ic2Items.tinOre = new ItemStack(createBlock((new BlockTex(33, Material.field_151576_e)).func_149711_c(3.0F).func_149752_b(5.0F).func_149663_c("blockOreTin").func_149647_a(tabIC2), ItemBlock.class, "tinOre"));
/*      */     }
/*  712 */     if (enableWorldGenOreCopper)
/*      */     {
/*  714 */       Ic2Items.copperOre = new ItemStack(createBlock((new BlockTex(32, Material.field_151576_e)).func_149711_c(3.0F).func_149752_b(5.0F).func_149663_c("blockOreCopper").func_149647_a(tabIC2), ItemBlock.class, "copperOre"));
/*      */     }
/*  716 */     createBlock((Block)new BlockMachine(), (Class)ItemMachine.class, "blockMachine");
/*  717 */     createBlock((Block)new BlockConverter(), (Class)ItemBlockConverter.class, "Converter");
/*  718 */     createBlock((Block)new BlockChargePads(), (Class)ItemBlockChargePad.class, "ChargePad-Classic");
/*  719 */     createBlock((Block)new BlockWallTextured(), (Class)ItemBlockRare.class, "constructionFoamWallTextured");
/*      */     
/*  721 */     Ic2Items.nightvisionGoggles = new ItemStack(createItem((new ItemArmorNightvisionGoggles(34, platform.addArmor("ic2/nightvision"))).func_77655_b("itemNightvisionGoggles").func_77637_a(tabIC2)));
/*  722 */     Ic2Items.airCell = new ItemStack(createItem((new ItemIC2(38)).func_77655_b("itemCellAir").func_77637_a(tabIC2)));
/*  723 */     Ic2Items.hazmatLeggings = new ItemStack(createItem((new ItemArmorHazmat(18, platform.addArmor("ic2/hazmat"), 2)).func_77655_b("itemArmorHazmatLeggings")));
/*  724 */     Ic2Items.hazmatChestplate = new ItemStack(createItem((new ItemArmorHazmat(17, platform.addArmor("ic2/hazmat"), 1)).func_77655_b("itemArmorHazmatChestplate")));
/*  725 */     Ic2Items.hazmatHelmet = new ItemStack(createItem((new ItemArmorHazmat(16, platform.addArmor("ic2/hazmat"), 0)).func_77655_b("itemArmorHazmatHelmet")));
/*  726 */     Ic2Items.reactorCondensatorLap = new ItemStack(createItem((new ItemReactorCondensator(6, 100000, true)).func_77655_b("reactorCondensatorLap").func_77637_a(tabIC2)));
/*  727 */     Ic2Items.denseCopperPlate = new ItemStack(createItem((new ItemIC2(58)).func_77655_b("itemPartDCP").func_77637_a(tabIC2)));
/*  728 */     Ic2Items.reactorCondensator = new ItemStack(createItem((new ItemReactorCondensator(5, 20000, false)).func_77655_b("reactorCondensator").func_77637_a(tabIC2)));
/*  729 */     Ic2Items.reactorReflectorThick = new ItemStack(createItem((new ItemReactorReflector(66, 40000, true)).func_77655_b("reactorReflectorThick").func_77637_a(tabIC2)));
/*  730 */     Ic2Items.reactorReflector = new ItemStack(createItem((new ItemReactorReflector(65, 10000, false)).func_77655_b("reactorReflector").func_77637_a(tabIC2)));
/*  731 */     Ic2Items.reactorHeatpack = new ItemStack(createItem((new ItemReactorHeatpack(64, 1000, 1)).func_77655_b("reactorHeatpack").func_77637_a(tabIC2)));
/*  732 */     Ic2Items.reactorVentDiamond = new ItemStack(createItem((new ItemReactorVent(3, 1000, 12, 0)).setID(20).func_77655_b("reactorVentDiamond").func_77637_a(tabIC2)));
/*  733 */     Ic2Items.reactorVentSpread = new ItemStack(createItem((new ItemReactorVentSpread(4, 4)).func_77655_b("reactorVentSpread").func_77637_a(tabIC2)));
/*  734 */     Ic2Items.reactorVentGold = new ItemStack(createItem((new ItemReactorVent(2, 1000, 20, 36)).setID(21).func_77655_b("reactorVentGold").func_77637_a(tabIC2)));
/*  735 */     Ic2Items.reactorVentCore = new ItemStack(createItem((new ItemReactorVent(1, 1000, 5, 5)).setID(19).func_77655_b("reactorVentCore").func_77637_a(tabIC2)));
/*  736 */     Ic2Items.reactorVent = new ItemStack(createItem((new ItemReactorVent(0, 1000, 6, 0)).setID(18).func_77655_b("reactorVent").func_77637_a(tabIC2)));
/*  737 */     Ic2Items.reactorHeatSwitchDiamond = new ItemStack(createItem((new ItemReactorHeatSwitch(19, 10000, 24, 8)).setID(30).func_77655_b("reactorHeatSwitchDiamond").func_77637_a(tabIC2)));
/*  738 */     Ic2Items.reactorHeatSwitchSpread = new ItemStack(createItem((new ItemReactorHeatSwitch(18, 5000, 36, 0)).setID(29).func_77655_b("reactorHeatSwitchSpread").func_77637_a(tabIC2)));
/*  739 */     Ic2Items.reactorHeatSwitchCore = new ItemStack(createItem((new ItemReactorHeatSwitch(17, 5000, 0, 72)).setID(28).func_77655_b("reactorHeatSwitchCore").func_77637_a(tabIC2)));
/*  740 */     Ic2Items.reactorPlatingExplosive = new ItemStack(createItem((new ItemReactorPlating(50, 500, 0.9F)).setID(32).func_77655_b("reactorPlatingExplosive").func_77637_a(tabIC2)));
/*  741 */     Ic2Items.reactorPlatingHeat = new ItemStack(createItem((new ItemReactorPlating(49, 2000, 0.99F)).setID(33).func_77655_b("reactorPlatingHeat").func_77637_a(tabIC2)));
/*  742 */     Ic2Items.reactorCoolantSix = new ItemStack(createItem((new ItemReactorHeatStorage(82, 60000)).setID(14).func_77655_b("reactorCoolantSix").func_77637_a(tabIC2)));
/*  743 */     Ic2Items.reactorCoolantTriple = new ItemStack(createItem((new ItemReactorHeatStorage(81, 30000)).setID(13).func_77655_b("reactorCoolantTriple").func_77637_a(tabIC2)));
/*  744 */     Ic2Items.reactorUraniumQuad = new ItemStack(createItem((new ItemReactorUranium(98, 4)).func_77655_b("reactorUraniumQuad").func_77637_a(tabIC2)));
/*  745 */     Ic2Items.reactorUraniumDual = new ItemStack(createItem((new ItemReactorUranium(97, 2)).func_77655_b("reactorUraniumDual").func_77637_a(tabIC2)));
/*  746 */     Ic2Items.coolant = new ItemStack(createItem((new ItemFluid("coolant")).func_77655_b("itemCoolant")));
/*  747 */     Ic2Items.debug = new ItemStack(createItem((Item)new ItemDebug()));
/*  748 */     Ic2Items.weedEx = new ItemStack(createItem((new ItemIC2(24)).setSpriteID("i1").func_77655_b("itemWeedEx").func_77625_d(1).func_77656_e(64).func_77637_a(tabIC2)));
/*  749 */     Ic2Items.grinPowder = new ItemStack(createItem((new ItemIC2(81)).func_77655_b("itemGrinPowder").func_77637_a(tabIC2)));
/*  750 */     Ic2Items.mugBooze = new ItemStack(createItem((new ItemBooze(164)).func_77655_b("beer")));
/*  751 */     Ic2Items.barrel = new ItemStack(createItem((new ItemBarrel(88)).func_77655_b("itemBarrel").func_77637_a(tabIC2)));
/*  752 */     Ic2Items.hops = new ItemStack(createItem((new ItemIC2(85)).func_77655_b("itemHops").func_77637_a(tabIC2)));
/*  753 */     Ic2Items.mugCoffee = new ItemStack(createItem((new ItemMugCoffee(161)).func_77637_a(tabIC2).func_77655_b("coffee")));
/*  754 */     Ic2Items.mugEmpty = new ItemStack(createItem((new ItemMug(160)).func_77655_b("itemMugEmpty").func_77637_a(tabIC2)));
/*  755 */     Ic2Items.coffeePowder = new ItemStack(createItem((new ItemIC2(84)).func_77655_b("itemCoffeePowder").func_77637_a(tabIC2)));
/*  756 */     Ic2Items.coffeeBeans = new ItemStack(createItem((new ItemIC2(83)).func_77655_b("itemCoffeeBeans").func_77637_a(tabIC2)));
/*  757 */     Ic2Items.terraWart = new ItemStack(createItem((new ItemTerraWart(82)).func_77655_b("itemTerraWart")));
/*  758 */     Ic2Items.staticBoots = new ItemStack(createItem((new ItemArmorStaticBoots(40, platform.addArmor("ic2/rubber"))).func_77655_b("itemStaticBoots").func_77637_a(tabIC2)));
/*  759 */     Ic2Items.solarHelmet = new ItemStack(createItem((new ItemArmorSolarHelmet(32, platform.addArmor("ic2/solar"), 1)).func_77655_b("itemSolarHelmet").func_77637_a(tabIC2)));
/*  760 */     Ic2Items.toolbox = new ItemStack(createItem((new ItemToolbox(56)).func_77655_b("itemToolbox").func_77637_a(tabIC2)));
/*  761 */     Ic2Items.mushroomTerraformerBlueprint = new ItemStack(createItem((new ItemTFBPMushroom(96)).func_77655_b("itemTFBPMushroom").func_77637_a(tabIC2)));
/*  762 */     Ic2Items.electricHoe = new ItemStack(createItem((new ItemElectricToolHoe(37)).func_77655_b("itemToolHoe").func_77637_a(tabIC2)));
/*  763 */     Ic2Items.hydratingCell = new ItemStack(createItem((new ItemGradual(40)).func_77655_b("itemCellHydrant").func_77637_a(tabIC2)));
/*  764 */     Ic2Items.fertilizer = new ItemStack(createItem((new ItemFertilizer(80)).func_77655_b("itemFertilizer").func_77637_a(tabIC2)));
/*  765 */     Ic2Items.cropnalyzer = new ItemStack(createItem((new ItemCropnalyzer(43)).func_77655_b("itemCropanalyzer").func_77637_a(tabIC2)));
/*  766 */     Ic2Items.electricTreetap = new ItemStack(createItem((new ItemTreetapElectric(36)).func_77655_b("itemTreetapElectric").func_77637_a(tabIC2)));
/*  767 */     createItem((Item)new ItemUpgradeModule());
/*  768 */     Ic2Items.cropSeed = new ItemStack(createItem((new ItemCropSeed(87)).func_77655_b("itemCropSeed")));
/*  769 */     Ic2Items.lapPack = new ItemStack(createItem((new ItemArmorLappack(36, platform.addArmor("ic2/lappack"))).func_77655_b("itemArmorLappack").func_77637_a(tabIC2)));
/*  770 */     Ic2Items.iridiumOre = new ItemStack(createItem((new ItemIC2(59)).setRarity(EnumRarity.rare).func_77655_b("itemOreIridium").func_77637_a(tabIC2)));
/*  771 */     Ic2Items.cfPack = new ItemStack(createItem((new ItemArmorCFPack(37, platform.addArmor("ic2/batpack"))).func_77655_b("itemArmorCFPack").func_77637_a(tabIC2)));
/*  772 */     Ic2Items.silverDust = new ItemStack(createItem((new ItemIC2(7)).func_77655_b("itemDustSilver").func_77637_a(tabIC2)));
/*  773 */     Ic2Items.constructionFoamSprayer = new ItemStack(createItem((new ItemSprayer(16)).func_77655_b("itemFoamSprayer").func_77637_a(tabIC2)));
/*  774 */     Ic2Items.constructionFoamPellet = new ItemStack(createItem((new ItemIC2(72)).func_77655_b("itemPartPellet").func_77637_a(tabIC2)));
/*  775 */     Ic2Items.clayDust = new ItemStack(createItem((new ItemIC2(6)).func_77655_b("itemDustClay").func_77637_a(tabIC2)));
/*  776 */     Ic2Items.frequencyTransmitter = new ItemStack(createItem((new ItemFrequencyTransmitter(21)).func_77655_b("itemFreq").func_77625_d(1).func_77637_a(tabIC2)));
/*  777 */     Ic2Items.industrialDiamond = new ItemStack(createItem((new ItemIC2(51)).func_77655_b("itemPartIndustrialDiamond")));
/*  778 */     Ic2Items.coalChunk = new ItemStack(createItem((new ItemIC2(50)).func_77655_b("itemPartCoalChunk").func_77637_a(tabIC2)));
/*  779 */     Ic2Items.compressedCoalBall = new ItemStack(createItem((new ItemIC2(49)).func_77655_b("itemPartCoalBlock").func_77637_a(tabIC2)));
/*  780 */     Ic2Items.coalBall = new ItemStack(createItem((new ItemIC2(48)).func_77655_b("itemPartCoalBall").func_77637_a(tabIC2)));
/*  781 */     Ic2Items.scrapBox = new ItemStack(createItem((new ItemScrapbox(66)).func_77655_b("itemScrapbox").func_77637_a(tabIC2)));
/*  782 */     Ic2Items.electricWrench = new ItemStack(createItem((new ItemToolWrenchElectric(35)).func_77655_b("itemToolWrenchElectric").func_77637_a(tabIC2)));
/*  783 */     Ic2Items.flatificatorTerraformerBlueprint = new ItemStack(createItem((new ItemTFBPFlatification(102)).func_77655_b("itemTFBPFlatification").func_77637_a(tabIC2)));
/*  784 */     Ic2Items.desertificationTerraformerBlueprint = new ItemStack(createItem((new ItemTFBPDesertification(101)).func_77655_b("itemTFBPDesertification").func_77637_a(tabIC2)));
/*  785 */     Ic2Items.chillingTerraformerBlueprint = new ItemStack(createItem((new ItemTFBPChilling(100)).func_77655_b("itemTFBPChilling").func_77637_a(tabIC2)));
/*  786 */     Ic2Items.irrigationTerraformerBlueprint = new ItemStack(createItem((new ItemTFBPIrrigation(99)).func_77655_b("itemTFBPIrrigation").func_77637_a(tabIC2)));
/*  787 */     Ic2Items.cultivationTerraformerBlueprint = new ItemStack(createItem((new ItemTFBPCultivation(98)).func_77655_b("itemTFBPCultivation").func_77637_a(tabIC2)));
/*  788 */     Ic2Items.terraformerBlueprint = new ItemStack(createItem((new ItemIC2(97)).setSpriteID("i1").func_77655_b("itemTFBP").func_77637_a(tabIC2)));
/*  789 */     Ic2Items.iridiumPlate = new ItemStack(createItem((new ItemIC2(60)).setRarity(EnumRarity.rare).func_77655_b("itemPartIridium").func_77637_a(tabIC2)));
/*  790 */     Ic2Items.nanoSaber = new ItemStack(createItem((new ItemNanoSaber(39, false)).func_77655_b("itemNanoSaber").func_77637_a(tabIC2).func_77637_a(tabIC2)));
/*  791 */     Ic2Items.enabledNanoSaber = new ItemStack(createItem((new ItemNanoSaber(40, true)).func_77655_b("itemNanoSaberActive").func_77637_a(tabIC2)));
/*  792 */     Ic2Items.carbonPlate = new ItemStack(createItem((new ItemIC2(54)).func_77655_b("itemPartCarbonPlate").func_77637_a(tabIC2)));
/*  793 */     Ic2Items.carbonMesh = new ItemStack(createItem((new ItemIC2(53)).func_77655_b("itemPartCarbonMesh").func_77637_a(tabIC2)));
/*  794 */     Ic2Items.carbonFiber = new ItemStack(createItem((new ItemIC2(52)).func_77655_b("itemPartCarbonFibre").func_77637_a(tabIC2)));
/*  795 */     Ic2Items.cutter = new ItemStack(createItem((new ItemToolCutter(54)).func_77655_b("itemToolCutter").func_77637_a(tabIC2)));
/*  796 */     Ic2Items.whitePainter = new ItemStack(createItem((new ItemToolPainter(15)).func_77655_b("itemToolPainterWhite").func_77637_a(tabIC2)));
/*  797 */     Ic2Items.orangePainter = new ItemStack(createItem((new ItemToolPainter(14)).func_77655_b("itemToolPainterOrange").func_77637_a(tabIC2)));
/*  798 */     Ic2Items.magentaPainter = new ItemStack(createItem((new ItemToolPainter(13)).func_77655_b("itemToolPainterMagenta").func_77637_a(tabIC2)));
/*  799 */     Ic2Items.cloudPainter = new ItemStack(createItem((new ItemToolPainter(12)).func_77655_b("itemToolPainterCloud").func_77637_a(tabIC2)));
/*  800 */     Ic2Items.yellowPainter = new ItemStack(createItem((new ItemToolPainter(11)).func_77655_b("itemToolPainterYellow").func_77637_a(tabIC2)));
/*  801 */     Ic2Items.limePainter = new ItemStack(createItem((new ItemToolPainter(10)).func_77655_b("itemToolPainterLime").func_77637_a(tabIC2)));
/*  802 */     Ic2Items.pinkPainter = new ItemStack(createItem((new ItemToolPainter(9)).func_77655_b("itemToolPainterPink").func_77637_a(tabIC2)));
/*  803 */     Ic2Items.darkGreyPainter = new ItemStack(createItem((new ItemToolPainter(8)).func_77655_b("itemToolPainterDarkGrey").func_77637_a(tabIC2)));
/*  804 */     Ic2Items.lightGreyPainter = new ItemStack(createItem((new ItemToolPainter(7)).func_77655_b("itemToolPainterLightGrey").func_77637_a(tabIC2)));
/*  805 */     Ic2Items.cyanPainter = new ItemStack(createItem((new ItemToolPainter(6)).func_77655_b("itemToolPainterCyan").func_77637_a(tabIC2)));
/*  806 */     Ic2Items.purplePainter = new ItemStack(createItem((new ItemToolPainter(5)).func_77655_b("itemToolPainterPurple").func_77637_a(tabIC2)));
/*  807 */     Ic2Items.bluePainter = new ItemStack(createItem((new ItemToolPainter(4)).func_77655_b("itemToolPainterBlue").func_77637_a(tabIC2)));
/*  808 */     Ic2Items.brownPainter = new ItemStack(createItem((new ItemToolPainter(3)).func_77655_b("itemToolPainterBrown").func_77637_a(tabIC2)));
/*  809 */     Ic2Items.greenPainter = new ItemStack(createItem((new ItemToolPainter(2)).func_77655_b("itemToolPainterGreen").func_77637_a(tabIC2)));
/*  810 */     Ic2Items.redPainter = new ItemStack(createItem((new ItemToolPainter(1)).func_77655_b("itemToolPainterRed").func_77637_a(tabIC2)));
/*  811 */     Ic2Items.blackPainter = new ItemStack(createItem((new ItemToolPainter(0)).func_77655_b("itemToolPainterBlack").func_77637_a(tabIC2)));
/*  812 */     Ic2Items.painter = new ItemStack(createItem((new ItemIC2(80)).setSpriteID("i1").func_77655_b("itemToolPainter").func_77637_a(tabIC2)));
/*  813 */     Ic2Items.quantumBoots = new ItemStack(createItem((new ItemArmorQuantumSuit(11, platform.addArmor("ic2/quantum"), 3)).func_77655_b("itemArmorQuantumBoots")));
/*  814 */     Ic2Items.quantumLeggings = new ItemStack(createItem((new ItemArmorQuantumSuit(10, platform.addArmor("ic2/quantum"), 2)).func_77655_b("itemArmorQuantumLegs")));
/*  815 */     Ic2Items.quantumBodyarmor = new ItemStack(createItem((new ItemArmorQuantumSuit(9, platform.addArmor("ic2/quantum"), 1)).func_77655_b("itemArmorQuantumChestplate")));
/*  816 */     Ic2Items.quantumHelmet = new ItemStack(createItem((new ItemArmorQuantumSuit(8, platform.addArmor("ic2/quantum"), 0)).func_77655_b("itemArmorQuantumHelmet")));
/*  817 */     Ic2Items.nanoBoots = new ItemStack(createItem((new ItemArmorNanoSuit(7, platform.addArmor("ic2/nano"), 3)).func_77655_b("itemArmorNanoBoots")));
/*  818 */     Ic2Items.nanoLeggings = new ItemStack(createItem((new ItemArmorNanoSuit(6, platform.addArmor("ic2/nano"), 2)).func_77655_b("itemArmorNanoLegs")));
/*  819 */     Ic2Items.nanoBodyarmor = new ItemStack(createItem((new ItemArmorNanoSuit(5, platform.addArmor("ic2/nano"), 1)).func_77655_b("itemArmorNanoChestplate")));
/*  820 */     Ic2Items.nanoHelmet = new ItemStack(createItem((new ItemArmorNanoSuit(4, platform.addArmor("ic2/nano"), 0)).func_77655_b("itemArmorNanoHelmet")));
/*  821 */     Ic2Items.batPack = new ItemStack(createItem((new ItemArmorBatpack(35, platform.addArmor("ic2/batpack"))).func_77655_b("itemArmorBatpack").func_77637_a(tabIC2)));
/*  822 */     Ic2Items.electrolyzedWaterCell = new ItemStack(createItem((new ItemIC2(39)).func_77655_b("itemCellWaterElectro").func_77637_a(tabIC2)));
/*  823 */     Ic2Items.ecMeter = new ItemStack(createItem((new ItemToolMeter(42)).func_77655_b("itemToolMeter").func_77637_a(tabIC2)));
/*  824 */     Ic2Items.wrench = new ItemStack(createItem((new ItemToolWrench(53)).func_77655_b("itemToolWrench").func_77637_a(tabIC2)));
/*  825 */     createItem((Item)new ItemCable(96));
/*  826 */     Ic2Items.reinforcedDoor = new ItemStack(createItem((new ItemIC2Door(63, Block.func_149634_a(Ic2Items.reinforcedDoorBlock.func_77973_b()))).func_77655_b("itemDoorAlloy")));
/*  827 */     Ic2Items.coin = new ItemStack(createItem((new ItemIC2(62)).func_77655_b("itemCoin")));
/*  828 */     Ic2Items.advancedAlloy = new ItemStack(createItem((new ItemIC2(55)).func_77655_b("itemPartAlloy").func_77637_a(tabIC2)));
/*  829 */     Ic2Items.matter = new ItemStack(createItem((new ItemIC2(64)).setRarity(EnumRarity.rare).func_77655_b("itemMatter").func_77637_a(tabIC2)));
/*  830 */     Ic2Items.scrap = new ItemStack(createItem((new ItemIC2(65)).func_77655_b("itemScrap").func_77637_a(tabIC2)));
/*  831 */     Ic2Items.advancedCircuit = new ItemStack(createItem((new ItemIC2(57)).setRarity(EnumRarity.uncommon).func_77655_b("itemPartCircuitAdv").func_77637_a(tabIC2)));
/*  832 */     Ic2Items.electronicCircuit = new ItemStack(createItem((new ItemIC2(56)).func_77655_b("itemPartCircuit").func_77637_a(tabIC2)));
/*  833 */     Ic2Items.bronzeBoots = new ItemStack(createItem((new ItemArmorIC2(3, bronzeArmorMaterial, platform.addArmor("ic2/bronze"), 3, Ic2Items.bronzeIngot, false)).func_77655_b("itemArmorBronzeBoots")));
/*  834 */     Ic2Items.bronzeLeggings = new ItemStack(createItem((new ItemArmorIC2(2, bronzeArmorMaterial, platform.addArmor("ic2/bronze"), 2, Ic2Items.bronzeIngot, false)).func_77655_b("itemArmorBronzeLegs")));
/*  835 */     Ic2Items.bronzeChestplate = new ItemStack(createItem((new ItemArmorIC2(1, bronzeArmorMaterial, platform.addArmor("ic2/bronze"), 1, Ic2Items.bronzeIngot, false)).func_77655_b("itemArmorBronzeChestplate")));
/*  836 */     Ic2Items.bronzeHelmet = new ItemStack(createItem((new ItemArmorIC2(0, bronzeArmorMaterial, platform.addArmor("ic2/bronze"), 0, Ic2Items.bronzeIngot, false)).func_77655_b("itemArmorBronzeHelmet")));
/*  837 */     Ic2Items.bronzeHoe = new ItemStack(createItem((new ItemIC2Hoe(52, Item.ToolMaterial.IRON, Ic2Items.bronzeIngot)).func_77655_b("itemToolBronzeHoe").func_77637_a(tabIC2)));
/*  838 */     Ic2Items.bronzeShovel = new ItemStack(createItem((new ItemIC2Spade(51, Item.ToolMaterial.IRON, 5.0F, Ic2Items.bronzeIngot)).func_77655_b("itemToolBronzeSpade").func_77637_a(tabIC2)));
/*  839 */     Ic2Items.bronzeSword = new ItemStack(createItem((new ItemIC2Sword(50, Item.ToolMaterial.IRON, 7, Ic2Items.bronzeIngot)).func_77655_b("itemToolBronzeSword").func_77637_a(tabIC2)));
/*  840 */     Ic2Items.bronzeAxe = new ItemStack(createItem((new ItemIC2Axe(49, Item.ToolMaterial.IRON, 5.0F, Ic2Items.bronzeIngot)).func_77655_b("itemToolBronzeAxe").func_77637_a(tabIC2)));
/*  841 */     Ic2Items.bronzePickaxe = new ItemStack(createItem((new ItemIC2Pickaxe(48, Item.ToolMaterial.IRON, 5.0F, Ic2Items.bronzeIngot)).func_77655_b("itemToolBronzePickaxe").func_77637_a(tabIC2)));
/*  842 */     Ic2Items.nearDepletedUraniumCell = new ItemStack(createItem((new ItemIC2(114)).setSpriteID("i3").func_77655_b("itemCellUranEmpty").func_77637_a(tabIC2)));
/*  843 */     Ic2Items.reEnrichedUraniumCell = new ItemStack(createItem((new ItemIC2(113)).setSpriteID("i3").func_77655_b("itemCellUranEnriched").func_77637_a(tabIC2)));
/*  844 */     Ic2Items.reactorIsotopeCell = new ItemStack(createItem((new ItemReactorDepletedUranium(112)).setSpriteID("i3").func_77655_b("reactorIsotopeCell").func_77637_a(tabIC2)));
/*  845 */     Ic2Items.reactorHeatSwitch = new ItemStack(createItem((new ItemReactorHeatSwitch(16, 2500, 12, 4)).setID(27).func_77655_b("reactorHeatSwitch").func_77637_a(tabIC2)));
/*  846 */     Ic2Items.reactorPlating = new ItemStack(createItem((new ItemReactorPlating(48, 1000, 0.95F)).setID(31).func_77655_b("reactorPlating").func_77637_a(tabIC2)));
/*  847 */     Ic2Items.reactorCoolantSimple = new ItemStack(createItem((new ItemReactorHeatStorage(80, 10000)).setID(12).func_77655_b("reactorCoolantSimple").func_77637_a(tabIC2)));
/*  848 */     Ic2Items.reactorUraniumSimple = new ItemStack(createItem((new ItemReactorUranium(96, 1)).func_77655_b("reactorUraniumSimple").func_77637_a(tabIC2)));
/*  849 */     Ic2Items.miningLaser = new ItemStack(createItem((new ItemToolMiningLaser(38)).func_77655_b("itemToolMiningLaser").func_77637_a(tabIC2)));
/*  850 */     Ic2Items.electricJetpack = new ItemStack(createItem((new ItemArmorJetpackElectric(39, platform.addArmor("ic2/jetpack"))).func_77655_b("itemArmorJetpackElectric").func_77637_a(tabIC2)));
/*  851 */     Ic2Items.jetpack = new ItemStack(createItem((new ItemArmorJetpack(38, platform.addArmor("ic2/jetpack"))).func_77655_b("itemArmorJetpack").func_77637_a(tabIC2)));
/*  852 */     Ic2Items.hazmatBoots = new ItemStack(createItem((new ItemArmorHazmat(19, platform.addArmor("ic2/hazmat"), 3)).func_77655_b("itemArmorRubBoots")));
/*  853 */     Ic2Items.treetap = new ItemStack(createItem((new ItemTreetap(55)).func_77655_b("itemTreetap").func_77637_a(tabIC2)));
/*  854 */     Ic2Items.remote = new ItemStack(createItem((new ItemRemote(20)).func_77655_b("itemRemote").func_77637_a(tabIC2)));
/*  855 */     Ic2Items.stickyDynamite = new ItemStack(createItem((new ItemDynamite(23, true)).func_77655_b("itemDynamiteSticky").func_77637_a(tabIC2)));
/*  856 */     Ic2Items.dynamite = new ItemStack(createItem((new ItemDynamite(22, false)).func_77655_b("itemDynamite").func_77637_a(tabIC2)));
/*  857 */     Ic2Items.rubber = new ItemStack(createItem((new ItemIC2(74)).func_77655_b("itemRubber").func_77637_a(tabIC2)));
/*  858 */     Ic2Items.resin = new ItemStack(createItem((new ItemResin(73)).func_77655_b("itemHarz").func_77637_a(tabIC2)));
/*  859 */     Ic2Items.waterCell = new ItemStack(createItem((new ItemIC2(34)).func_77655_b("itemCellWater").func_77637_a(tabIC2)));
/*  860 */     Ic2Items.ovScanner = new ItemStack(createItem((new ItemScannerAdv(113, 2)).func_77655_b("itemScannerAdv").func_77637_a(tabIC2)));
/*  861 */     Ic2Items.odScanner = new ItemStack(createItem((new ItemScanner(112, 1)).func_77655_b("itemScanner").func_77637_a(tabIC2)));
/*  862 */     Ic2Items.filledTinCan = new ItemStack(createItem((new ItemTinCan(79)).func_77655_b("itemTinCanFilled").func_77637_a(tabIC2)));
/*  863 */     Ic2Items.tinCan = new ItemStack(createItem((new ItemIC2(78)).func_77655_b("itemTinCan").func_77637_a(tabIC2)));
/*  864 */     Ic2Items.compressedPlantBall = new ItemStack(createItem((new ItemIC2(77)).func_77655_b("itemFuelPlantCmpr").func_77637_a(tabIC2)));
/*  865 */     Ic2Items.plantBall = new ItemStack(createItem((new ItemIC2(76)).func_77655_b("itemFuelPlantBall").func_77637_a(tabIC2)));
/*  866 */     Ic2Items.hydratedCoalClump = new ItemStack(createItem((new ItemIC2(75)).func_77655_b("itemFuelCoalCmpr").func_77637_a(tabIC2)));
/*  867 */     Ic2Items.hydratedCoalDust = new ItemStack(createItem((new ItemIC2(8)).func_77655_b("itemFuelCoalDust").func_77637_a(tabIC2)));
/*  868 */     Ic2Items.biofuelCell = new ItemStack(createItem((new ItemIC2(37)).func_77655_b("itemCellBioRef").func_77637_a(tabIC2)));
/*  869 */     Ic2Items.coalfuelCell = new ItemStack(createItem((new ItemIC2(35)).func_77655_b("itemCellCoalRef").func_77637_a(tabIC2)));
/*  870 */     Ic2Items.bioCell = new ItemStack(createItem((new ItemIC2(40)).func_77655_b("itemCellBio").func_77637_a(tabIC2)));
/*  871 */     Ic2Items.hydratedCoalCell = new ItemStack(createItem((new ItemIC2(36)).func_77655_b("itemCellCoal").func_77637_a(tabIC2)));
/*  872 */     Ic2Items.fuelCan = new ItemStack(createItem((new ItemFuelCanEmpty(18)).func_77655_b("itemFuelCanEmpty").func_77637_a(tabIC2)));
/*  873 */     Ic2Items.filledFuelCan = new ItemStack(createItem((new ItemFuelCanFilled(19)).func_77655_b("itemFuelCan").func_77625_d(1).func_77642_a(Ic2Items.fuelCan.func_77973_b())));
/*  874 */     Ic2Items.chainsaw = new ItemStack(createItem((new ItemElectricToolChainsaw(34)).func_77655_b("itemToolChainsaw").func_77637_a(tabIC2)));
/*  875 */     Ic2Items.diamondDrill = new ItemStack(createItem((new ItemElectricToolDDrill(33)).func_77655_b("itemToolDDrill").func_77637_a(tabIC2)));
/*  876 */     Ic2Items.miningDrill = new ItemStack(createItem((new ItemElectricToolDrill(32)).func_77655_b("itemToolDrill").func_77637_a(tabIC2)));
/*  877 */     Ic2Items.lavaCell = new ItemStack(createItem((new ItemIC2(33)).func_77655_b("itemCellLava").func_77637_a(tabIC2)));
/*  878 */     Ic2Items.cell = new ItemStack(createItem((new ItemCell(32)).func_77655_b("itemCellEmpty").func_77637_a(tabIC2)));
/*  879 */     Ic2Items.suBattery = new ItemStack(createItem((new ItemBatterySU(15, 1000, 1)).func_77655_b("itemBatSU").func_77637_a(tabIC2)));
/*  880 */     Ic2Items.reBattery = new ItemStack(createItem((new ItemBatteryDischarged(0, 10000, 100, 1)).func_77655_b("itemBatRE").func_77637_a(tabIC2)));
/*  881 */     Ic2Items.lapotronCrystal = new ItemStack(createItem((new ItemBattery(10, 1000000, 600, 3)).setRarity(EnumRarity.uncommon).func_77655_b("itemBatLamaCrystal").func_77637_a(tabIC2)));
/*  882 */     Ic2Items.energyCrystal = new ItemStack(createItem((new ItemBattery(5, 100000, 250, 2)).func_77655_b("itemBatCrystal").func_77637_a(tabIC2)));
/*  883 */     Ic2Items.chargedReBattery = new ItemStack(createItem((new ItemBattery(0, 10000, 100, 1)).func_77655_b("itemBatRECharged").func_77637_a(tabIC2)));
/*  884 */     Ic2Items.uraniumDrop = new ItemStack(createItem((new ItemIC2(61)).func_77655_b("itemOreUran").func_77637_a(tabIC2)));
/*  885 */     Ic2Items.uraniumIngot = new ItemStack(createItem((new ItemIC2(21)).func_77655_b("itemIngotUran").func_77637_a(tabIC2)));
/*  886 */     Ic2Items.mixedMetalIngot = new ItemStack(createItem((new ItemIC2(20)).func_77655_b("itemIngotAlloy").func_77637_a(tabIC2)));
/*  887 */     Ic2Items.bronzeIngot = new ItemStack(createItem((new ItemIC2(19)).func_77655_b("itemIngotBronze").func_77637_a(tabIC2)));
/*  888 */     Ic2Items.tinIngot = new ItemStack(createItem((new ItemIC2(18)).func_77655_b("itemIngotTin").func_77637_a(tabIC2)));
/*  889 */     Ic2Items.copperIngot = new ItemStack(createItem((new ItemIC2(17)).func_77655_b("itemIngotCopper").func_77637_a(tabIC2)));
/*  890 */     Ic2Items.refinedIronIngot = new ItemStack(createItem((new ItemIC2(16)).func_77655_b("itemIngotAdvIron").func_77637_a(tabIC2)));
/*  891 */     Ic2Items.smallIronDust = new ItemStack(createItem((new ItemIC2(10)).func_77655_b("itemDustIronSmall").func_77637_a(tabIC2)));
/*  892 */     Ic2Items.bronzeDust = new ItemStack(createItem((new ItemIC2(5)).func_77655_b("itemDustBronze").func_77637_a(tabIC2)));
/*  893 */     Ic2Items.tinDust = new ItemStack(createItem((new ItemIC2(4)).func_77655_b("itemDustTin").func_77637_a(tabIC2)));
/*  894 */     Ic2Items.copperDust = new ItemStack(createItem((new ItemIC2(3)).func_77655_b("itemDustCopper").func_77637_a(tabIC2)));
/*  895 */     Ic2Items.goldDust = new ItemStack(createItem((new ItemIC2(2)).func_77655_b("itemDustGold").func_77637_a(tabIC2)));
/*  896 */     Ic2Items.ironDust = new ItemStack(createItem((new ItemIC2(1)).func_77655_b("itemDustIron").func_77637_a(tabIC2)));
/*  897 */     Ic2Items.coalDust = new ItemStack(createItem((new ItemIC2(0)).func_77655_b("itemDustCoal").func_77637_a(tabIC2)));
/*  898 */     Ic2Items.noUse = new ItemStack(createItem((Item)new ItemNoUse()));
/*  899 */     Ic2Items.silverIngot = new ItemStack(createItem((new ItemIC2(22)).func_77655_b("itemIngotSilver").func_77637_a(tabIC2)));
/*  900 */     Ic2Items.advSolarHelmet = new ItemStack(createItem((new ItemArmorSolarHelmet(33, platform.addArmor("ic2/solar"), 5)).func_77655_b("itemAdvancedSolarHelmet").func_77637_a(tabIC2)));
/*  901 */     Ic2Items.nuclearJetpack = new ItemStack(createItem((new ItemArmorJetpackNuclear(41, platform.addArmor("ic2/jetpack"))).func_77655_b("itemArmorJetpackNuclear").func_77637_a(tabIC2)));
/*  902 */     Ic2Items.scrapMetal = new ItemStack(createItem((new ItemIC2(67)).func_77655_b("itemScrapMetal").func_77637_a(tabIC2)));
/*  903 */     Ic2Items.scrapMetalChunk = new ItemStack(createItem((new ItemIC2(68)).func_77655_b("scrapMetalChunk").func_77637_a(tabIC2)));
/*  904 */     Ic2Items.scrapMetalBlade = new ItemStack(createItem((new ItemIC2(69)).func_77655_b("scrapMetalBlade").func_77637_a(tabIC2)));
/*  905 */     Ic2Items.steam = new ItemStack(createItem((new ItemFluid("steam")).func_77655_b("fluid.steam")));
/*  906 */     Ic2Items.reactorSteamVent = new ItemStack(createItem((new ItemReactorVentSteam(32, 1100, 6, 0)).setID(22).func_77655_b("reactorSteamVent").func_77637_a(tabIC2)));
/*  907 */     Ic2Items.reactorSteamVentCore = new ItemStack(createItem((new ItemReactorVentSteam(33, 1100, 5, 5)).setID(23).func_77655_b("reactorSteamVentCore").func_77637_a(tabIC2)));
/*  908 */     Ic2Items.reactorSteamVentGold = new ItemStack(createItem((new ItemReactorVentSteam(34, 1100, 20, 36)).setID(24).func_77655_b("reactorSteamVentGold").func_77637_a(tabIC2)));
/*  909 */     Ic2Items.reactorSteamVentDiamond = new ItemStack(createItem((new ItemReactorVentSteam(35, 1100, 12, 0)).setID(25).func_77655_b("reactorSteamVentDiamond").func_77637_a(tabIC2)));
/*  910 */     createItem((new ItemScanners(114)).func_77637_a(tabIC2));
/*  911 */     Ic2Items.obsidianDust = new ItemStack(createItem((new ItemIC2(9)).func_77655_b("itemObsidianDust").func_77637_a(tabIC2)));
/*  912 */     Ic2Items.rawObsidianBlade = new ItemStack(createItem((new ItemIC2(70)).func_77655_b("itemRawObsidianBlade").func_77637_a(tabIC2)));
/*  913 */     Ic2Items.obsidianBlade = new ItemStack(createItem((new ItemIC2(71)).func_77655_b("itemObsidianBlade").func_77637_a(tabIC2)));
/*  914 */     Ic2Items.teaLeaf = new ItemStack(createItem((new ItemIC2(86)).func_77655_b("itemTeaLeaf").func_77637_a(tabIC2)));
/*  915 */     Ic2Items.coldTea = new ItemStack(createItem((new ItemIC2(172)).func_77625_d(1).func_77655_b("itemColdTea").func_77637_a(tabIC2)));
/*  916 */     Ic2Items.blackTea = new ItemStack(createItem((new ItemTea(173, 0)).func_77655_b("itemBlackTea").func_77637_a(tabIC2)));
/*  917 */     Ic2Items.tea = new ItemStack(createItem((new ItemTea(174, 1)).func_77655_b("itemTea").func_77637_a(tabIC2)));
/*  918 */     Ic2Items.iceTea = new ItemStack(createItem((new ItemTea(175, 2)).func_77655_b("itemIceTea").func_77637_a(tabIC2)));
/*  919 */     Ic2Items.compositeArmor = new ItemStack(createItem((new ItemArmorIC2(13, alloyArmorMaterial, platform.addArmor("ic2/alloy"), 1, Ic2Items.advancedAlloy, true)).func_77655_b("itemArmorAlloyChestplate")));
/*  920 */     Ic2Items.compositeBoots = new ItemStack(createItem((new ItemArmorIC2(15, alloyArmorMaterial, platform.addArmor("ic2/alloy"), 3, Ic2Items.advancedAlloy, true)).func_77655_b("itemArmorAlloyBoots")));
/*  921 */     Ic2Items.compositeLeggings = new ItemStack(createItem((new ItemArmorIC2(14, alloyArmorMaterial, platform.addArmor("ic2/alloy"), 2, Ic2Items.advancedAlloy, true)).func_77655_b("itemArmorAlloyLeggings")));
/*  922 */     Ic2Items.compositeHelmet = new ItemStack(createItem((new ItemArmorIC2(12, alloyArmorMaterial, platform.addArmor("ic2/alloy"), 0, Ic2Items.advancedAlloy, true)).func_77655_b("itemArmorAlloyHelmet")));
/*  923 */     Ic2Items.terraFormerBiomeBlueprint = new ItemStack(createItem((new ItemIC2(128)).setSpriteID("i1").func_77655_b("itemTerraformerBiomeBlueprint").func_77637_a(tabIC2)));
/*  924 */     createItem((Item)new ItemTFBPBiome());
/*  925 */     Ic2Items.flour = new ItemStack(createItem((new ItemIC2(11)).func_77655_b("itemFlour").func_77637_a(tabIC2)));
/*  926 */     Ic2Items.specialFertilzer = new ItemStack(createItem((new ItemIC2(89)).setHasEffect().func_77637_a(tabIC2).func_77655_b("itemSpecialFertilizer")));
/*  927 */     Ic2Items.combinedElectricJetpack = new ItemStack(createItem((new ItemArmorJetpackCombined(42, platform.addArmor("ic2/jetpack"))).func_77637_a(tabIC2).func_77655_b("itemArmorCombinedJetpack")));
/*  928 */     Ic2Items.combinedNuclearJetpack = new ItemStack(createItem((new ItemArmorJetpackNuclearCombined(43, platform.addArmor("ic2/jetpack"))).func_77637_a(tabIC2).func_77655_b("itemArmorNuclearCombindedJetpack")));
/*  929 */     Ic2Items.obscurator = new ItemStack(createItem((new ItemTextureCopier()).func_77655_b("itemObscurator").func_77637_a(tabIC2)));
/*  930 */     createItem((new ItemChargePadUpgrade(144)).setSpriteID("i1").func_77637_a(tabIC2));
/*  931 */     Ic2Items.quantumBodyJetpackArmor = new ItemStack(createItem((new ItemArmorJetpackQuantumSuit(44, platform.addArmor("ic2/quantum"))).func_77655_b("itemQuantumArmorJetpack").func_77637_a(tabIC2)));
/*  932 */     createItem((new ItemReactorEnrichUranium(99)).setSpriteID("i3").func_77655_b("itemEnrichedUraniumCells").func_77637_a(tabIC2));
/*  933 */     Ic2Items.upgradeContainer = new ItemStack(createItem((new ItemUpgradeContainer()).func_77655_b("itemUpgradeContainer").func_77637_a(tabIC2)));
/*  934 */     createItem((new ItemReactorDepletedEnrichedUranium(115)).setSpriteID("i3").func_77655_b("itemDepletedEnrichedUraniumCells").func_77637_a(tabIC2));
/*  935 */     createItem((new ItemEnrichedUraniumStuff()).func_77655_b("itemEnrichedUranStuff").func_77637_a(tabIC2));
/*  936 */     createItem((new ItemReactorElectricVent()).func_77637_a(tabIC2));
/*  937 */     createItem((new ItemIC2Boat()).func_77637_a(tabIC2));
/*      */     
/*  939 */     Blocks.field_150343_Z.func_149752_b(60.0F);
/*  940 */     Blocks.field_150381_bn.func_149752_b(60.0F);
/*  941 */     Blocks.field_150477_bB.func_149752_b(60.0F);
/*  942 */     Blocks.field_150467_bQ.func_149752_b(60.0F);
/*  943 */     Blocks.field_150358_i.func_149752_b(30.0F);
/*  944 */     Blocks.field_150355_j.func_149752_b(30.0F);
/*  945 */     Blocks.field_150353_l.func_149752_b(30.0F);
/*  946 */     loadState(ISubModul.LoadingState.AfterObjectLoad);
/*  947 */     ((BlockIC2Door)Block.func_149634_a(Ic2Items.reinforcedDoorBlock.func_77973_b())).setItemDropped(Ic2Items.reinforcedDoor.func_77973_b());
/*  948 */     ExplosionWhitelist.addWhitelistedBlock(Blocks.field_150357_h);
/*  949 */     ExpHelper.init();
/*      */ 
/*      */     
/*  952 */     Recipes.matterAmplifier = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  953 */     Recipes.macerator = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  954 */     Recipes.extractor = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  955 */     Recipes.compressor = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  956 */     Recipes.advRecipes = (ICraftingRecipeManager)new AdvCraftingRecipeManager();
/*  957 */     Recipes.centrifuge = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  958 */     Recipes.metalformerCutting = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  959 */     Recipes.metalformerExtruding = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  960 */     Recipes.metalformerRolling = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  961 */     Recipes.oreWashing = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  962 */     Recipes.blockcutter = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  963 */     Recipes.blastfurance = (IMachineRecipeManager)new BasicMachineRecipeManager();
/*  964 */     Recipes.FluidHeatGenerator = (IFluidHeatManager)new FluidHeatManager();
/*  965 */     Recipes.cannerBottle = new BottlerRecipes();
/*  966 */     Recipes.cannerEnrich = new BottlerEnrichRecipes();
/*  967 */     Recipes.semiFluidGenerator = new SemiFluidFuelManager();
/*  968 */     Recipes.liquidCooldownManager = new HeatExchangerManager();
/*  969 */     Recipes.liquidHeatupManager = new HeatExchangerManager();
/*  970 */     TileEntityRecycler.preInit();
/*  971 */     ItemScrapbox.initData();
/*  972 */     ItemTFBPCultivation.init();
/*  973 */     ItemTFBPFlatification.init();
/*      */     
/*  975 */     EnergyNet.instance = (IEnergyNet)EnergyNetGlobal.initialize();
/*      */     
/*  977 */     IC2Crops.init();
/*      */     
/*  979 */     Info.DMG_ELECTRIC = IC2DamageSource.electricity;
/*  980 */     Info.DMG_NUKE_EXPLOSION = IC2DamageSource.nuke;
/*  981 */     Info.DMG_RADIATION = IC2DamageSource.radiation;
/*      */     
/*  983 */     FurnaceRecipes furnaceRecipes = FurnaceRecipes.func_77602_a();
/*  984 */     if (Ic2Items.rubberWood != null)
/*      */     {
/*  986 */       furnaceRecipes.func_151394_a(Ic2Items.rubberWood, new ItemStack(Blocks.field_150364_r, 1, 3), 0.1F);
/*      */     }
/*  988 */     if (Ic2Items.tinOre != null)
/*      */     {
/*  990 */       furnaceRecipes.func_151394_a(Ic2Items.tinOre, Ic2Items.tinIngot, 0.5F);
/*      */     }
/*  992 */     if (Ic2Items.copperOre != null)
/*      */     {
/*  994 */       furnaceRecipes.func_151394_a(Ic2Items.copperOre, Ic2Items.copperIngot, 0.5F);
/*      */     }
/*  996 */     furnaceRecipes.func_151396_a(Items.field_151042_j, Ic2Items.refinedIronIngot, 0.2F);
/*  997 */     furnaceRecipes.func_151394_a(Ic2Items.ironDust, new ItemStack(Items.field_151042_j, 1), 0.1F);
/*  998 */     furnaceRecipes.func_151394_a(Ic2Items.goldDust, new ItemStack(Items.field_151043_k, 1), 0.1F);
/*  999 */     furnaceRecipes.func_151394_a(Ic2Items.tinDust, Ic2Items.tinIngot.func_77946_l(), 0.1F);
/* 1000 */     furnaceRecipes.func_151394_a(Ic2Items.copperDust, Ic2Items.copperIngot.func_77946_l(), 0.1F);
/* 1001 */     furnaceRecipes.func_151394_a(Ic2Items.hydratedCoalDust, Ic2Items.coalDust.func_77946_l(), 0.0F);
/* 1002 */     furnaceRecipes.func_151394_a(Ic2Items.bronzeDust, Ic2Items.bronzeIngot.func_77946_l(), 0.1F);
/* 1003 */     furnaceRecipes.func_151394_a(Ic2Items.resin, Ic2Items.rubber.func_77946_l(), 0.3F);
/* 1004 */     furnaceRecipes.func_151396_a(Ic2Items.mugCoffee.func_77973_b(), new ItemStack(Ic2Items.mugCoffee.func_77973_b(), 1, 1), 0.0F);
/* 1005 */     furnaceRecipes.func_151394_a(Ic2Items.silverDust, Ic2Items.silverIngot, 0.1F);
/* 1006 */     furnaceRecipes.func_151394_a(Ic2Items.coldTea, Ic2Items.blackTea, 0.1F);
/* 1007 */     furnaceRecipes.func_151394_a(Ic2Items.flour, new ItemStack(Items.field_151025_P), 0.35F);
/* 1008 */     ((ItemElectricToolChainsaw)Ic2Items.chainsaw.func_77973_b()).init();
/* 1009 */     ((ItemElectricToolDrill)Ic2Items.miningDrill.func_77973_b()).init();
/* 1010 */     ((ItemElectricToolDDrill)Ic2Items.diamondDrill.func_77973_b()).init();
/* 1011 */     ((ItemNanoSaber)Ic2Items.nanoSaber.func_77973_b()).init();
/* 1012 */     Block.func_149634_a(Ic2Items.reinforcedStone.func_77973_b()).setHarvestLevel("pickaxe", 2);
/* 1013 */     Block.func_149634_a(Ic2Items.reinforcedDoorBlock.func_77973_b()).setHarvestLevel("pickaxe", 2);
/* 1014 */     Block.func_149634_a(Ic2Items.insulatedCopperCableBlock.func_77973_b()).setHarvestLevel("axe", 0);
/* 1015 */     Block.func_149634_a(Ic2Items.constructionFoamWall.func_77973_b()).setHarvestLevel("pickaxe", 1);
/* 1016 */     MinecraftForge.EVENT_BUS.register(new ChargeTooltipHandler());
/* 1017 */     if (Ic2Items.copperOre != null)
/*      */     {
/* 1019 */       Block.func_149634_a(Ic2Items.copperOre.func_77973_b()).setHarvestLevel("pickaxe", 1);
/*      */     }
/* 1021 */     if (Ic2Items.tinOre != null)
/*      */     {
/* 1023 */       Block.func_149634_a(Ic2Items.tinOre.func_77973_b()).setHarvestLevel("pickaxe", 1);
/*      */     }
/* 1025 */     if (Ic2Items.uraniumOre != null)
/*      */     {
/* 1027 */       Block.func_149634_a(Ic2Items.uraniumOre.func_77973_b()).setHarvestLevel("pickaxe", 2);
/*      */     }
/* 1029 */     if (Ic2Items.rubberWood != null)
/*      */     {
/* 1031 */       Block.func_149634_a(Ic2Items.rubberWood.func_77973_b()).setHarvestLevel("axe", 0);
/*      */     }
/* 1033 */     windStrength = 10 + random.nextInt(10);
/* 1034 */     windTicker = 0;
/* 1035 */     Blocks.field_150480_ab.setFireInfo(Block.func_149634_a(Ic2Items.scaffold.func_77973_b()), 8, 20);
/* 1036 */     if (Ic2Items.rubberLeaves != null)
/*      */     {
/* 1038 */       Blocks.field_150480_ab.setFireInfo(Block.func_149634_a(Ic2Items.rubberLeaves.func_77973_b()), 30, 20);
/*      */     }
/* 1040 */     if (Ic2Items.rubberWood != null)
/*      */     {
/* 1042 */       Blocks.field_150480_ab.setFireInfo(Block.func_149634_a(Ic2Items.rubberWood.func_77973_b()), 4, 20);
/*      */     }
/* 1044 */     MinecraftForge.EVENT_BUS.register(this);
/* 1045 */     for (String oreName : OreDictionary.getOreNames()) {
/*      */       
/* 1047 */       for (ItemStack ore : OreDictionary.getOres(oreName))
/*      */       {
/* 1049 */         registerOre(new OreDictionary.OreRegisterEvent(oreName, ore));
/*      */       }
/*      */     } 
/* 1052 */     assert Ic2Items.uraniumDrop != null;
/* 1053 */     assert Ic2Items.bronzeIngot != null;
/* 1054 */     assert Ic2Items.copperIngot != null;
/* 1055 */     assert Ic2Items.refinedIronIngot != null;
/* 1056 */     assert Ic2Items.tinIngot != null;
/* 1057 */     assert Ic2Items.uraniumIngot != null;
/* 1058 */     assert Ic2Items.rubber != null;
/* 1059 */     if (Ic2Items.copperOre != null)
/*      */     {
/* 1061 */       OreDictionary.registerOre("oreCopper", Ic2Items.copperOre);
/*      */     }
/* 1063 */     if (Ic2Items.tinOre != null)
/*      */     {
/* 1065 */       OreDictionary.registerOre("oreTin", Ic2Items.tinOre);
/*      */     }
/* 1067 */     if (Ic2Items.uraniumOre != null)
/*      */     {
/* 1069 */       OreDictionary.registerOre("oreUranium", Ic2Items.uraniumOre);
/*      */     }
/* 1071 */     OreDictionary.registerOre("dropUranium", Ic2Items.uraniumDrop);
/* 1072 */     OreDictionary.registerOre("dustBronze", Ic2Items.bronzeDust);
/* 1073 */     OreDictionary.registerOre("dustClay", Ic2Items.clayDust);
/* 1074 */     OreDictionary.registerOre("dustCoal", Ic2Items.coalDust);
/* 1075 */     OreDictionary.registerOre("dustCopper", Ic2Items.copperDust);
/* 1076 */     OreDictionary.registerOre("dustGold", Ic2Items.goldDust);
/* 1077 */     OreDictionary.registerOre("dustIron", Ic2Items.ironDust);
/* 1078 */     OreDictionary.registerOre("dustSilver", Ic2Items.silverDust);
/* 1079 */     OreDictionary.registerOre("dustTin", Ic2Items.tinDust);
/* 1080 */     OreDictionary.registerOre("ingotBronze", Ic2Items.bronzeIngot);
/* 1081 */     OreDictionary.registerOre("ingotCopper", Ic2Items.copperIngot);
/* 1082 */     OreDictionary.registerOre("ingotRefinedIron", Ic2Items.refinedIronIngot);
/* 1083 */     OreDictionary.registerOre("ingotTin", Ic2Items.tinIngot);
/* 1084 */     OreDictionary.registerOre("ingotUranium", Ic2Items.uraniumIngot);
/* 1085 */     OreDictionary.registerOre("IngotEnrichedUranium", Ic2Items.redstoneEnrichedUraniumIngot);
/* 1086 */     OreDictionary.registerOre("IngotEnrichedUranium", Ic2Items.blazeEnrichedUraniumIngot);
/* 1087 */     OreDictionary.registerOre("IngotEnrichedUranium", Ic2Items.enderpearlEnrichedUraniumIngot);
/* 1088 */     OreDictionary.registerOre("IngotEnrichedUranium", Ic2Items.netherstarEnrichedUraniumIngot);
/* 1089 */     OreDictionary.registerOre("IngotEnrichedUranium", Ic2Items.charcoalEnrichedUraniumIngot);
/*      */     
/* 1091 */     OreDictionary.registerOre("ingotSilver", Ic2Items.silverIngot);
/* 1092 */     OreDictionary.registerOre("itemRubber", Ic2Items.rubber);
/* 1093 */     OreDictionary.registerOre("itemRawRubber", Ic2Items.resin);
/*      */ 
/*      */     
/* 1096 */     OreDictionary.registerOre("blockBronze", Ic2Items.bronzeBlock);
/* 1097 */     OreDictionary.registerOre("blockCopper", Ic2Items.copperBlock);
/* 1098 */     OreDictionary.registerOre("blockTin", Ic2Items.tinBlock);
/* 1099 */     OreDictionary.registerOre("blockUranium", Ic2Items.uraniumBlock);
/*      */     
/* 1101 */     OreDictionary.registerOre("circuitBasic", Ic2Items.electronicCircuit);
/* 1102 */     OreDictionary.registerOre("circuitAdvanced", Ic2Items.advancedCircuit);
/*      */     
/* 1104 */     OreDictionary.registerOre("gemDiamond", Ic2Items.industrialDiamond);
/* 1105 */     OreDictionary.registerOre("gemDiamond", Items.field_151045_i);
/*      */     
/* 1107 */     OreDictionary.registerOre("UUMatter", Ic2Items.matter);
/* 1108 */     OreDictionary.registerOre("cropTea", Ic2Items.teaLeaf);
/* 1109 */     OreDictionary.registerOre("dustWheat", Ic2Items.flour);
/*      */ 
/*      */     
/* 1112 */     if (Ic2Items.rubberWood != null)
/*      */     {
/* 1114 */       OreDictionary.registerOre("woodRubber", Ic2Items.rubberWood);
/*      */     }
/* 1116 */     IC2Potion.init();
/* 1117 */     new IC2Loot();
/* 1118 */     achievements = new IC2Achievements();
/* 1119 */     enableDynamicIdAllocation = false;
/*      */     
/* 1121 */     if (config != null)
/*      */     {
/* 1123 */       config.save();
/*      */     }
/* 1125 */     EntityRegistry.registerModEntity(EntityMiningLaser.class, "MiningLaser", 0, this, 160, 5, true);
/* 1126 */     EntityRegistry.registerModEntity(EntityDynamite.class, "Dynamite", 1, this, 160, 5, true);
/* 1127 */     EntityRegistry.registerModEntity(EntityStickyDynamite.class, "StickyDynamite", 2, this, 160, 5, true);
/* 1128 */     EntityRegistry.registerModEntity(EntityItnt.class, "Itnt", 3, this, 160, 5, true);
/* 1129 */     EntityRegistry.registerModEntity(EntityNuke.class, "Nuke", 4, this, 160, 5, true);
/* 1130 */     EntityRegistry.registerModEntity(EntityRubberBoat.class, "rubberBoat", 5, this, 80, 1, true);
/* 1131 */     EntityRegistry.registerModEntity(EntityCarboneBoat.class, "carbonBoat", 6, this, 80, 1, true);
/* 1132 */     EntityRegistry.registerModEntity(EntityElectricBoat.class, "electricBoat", 7, this, 80, 1, true);
/* 1133 */     int d = Integer.parseInt((new SimpleDateFormat("Mdd")).format(new Date()));
/* 1134 */     suddenlyHoes = (d > Math.cbrt(6.4E7D) && d < Math.cbrt(6.5939264E7D));
/* 1135 */     seasonal = (d > Math.cbrt(1.089547389E9D) && d < Math.cbrt(1.338273208E9D));
/* 1136 */     FMLCommonHandler.instance().bus().register(this);
/* 1137 */     GameRegistry.registerWorldGenerator(this, 0);
/* 1138 */     GameRegistry.registerFuelHandler(this);
/* 1139 */     BoxableItems.init();
/* 1140 */     loadFluids();
/* 1141 */     RecipeSorter.register("ic2:shaped", AdvRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shapeless");
/* 1142 */     RecipeSorter.register("ic2:shapeless", AdvShapelessRecipe.class, RecipeSorter.Category.SHAPELESS, "after:ic2:shaped");
/* 1143 */     RecipeSorter.register("ic2:grandual", RecipeGradual.class, RecipeSorter.Category.SHAPELESS, "after:ic2:shapeless");
/* 1144 */     MinecraftForge.EVENT_BUS.register(new SubIconHandler.ForgeEventHandler());
/* 1145 */     platform.init();
/*      */ 
/*      */     
/* 1148 */     initialized = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void onInit(FMLInitializationEvent event) {
/* 1155 */     StackUtil.addOreIfMissing("plateIron", Ic2Items.refinedIronIngot);
/* 1156 */     StackUtil.addOreIfMissing("plateGold", new ItemStack(Items.field_151043_k));
/* 1157 */     StackUtil.addOreIfMissing("plateCopper", Ic2Items.copperIngot);
/* 1158 */     StackUtil.addOreIfMissing("plateTin", Ic2Items.tinIngot);
/* 1159 */     StackUtil.addOreIfMissing("plateLapis", new ItemStack(Items.field_151100_aR, 1, 4));
/* 1160 */     StackUtil.addOreIfMissing("plateBronze", Ic2Items.bronzeIngot);
/*      */     
/* 1162 */     registerCraftingRecipes();
/*      */     
/* 1164 */     TileEntityCompressor.init();
/* 1165 */     TileEntityExtractor.init();
/* 1166 */     TileEntityMacerator.init();
/* 1167 */     TileEntityMatter.init();
/* 1168 */     TileEntityElectrolyzer.init();
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadFluids() {
/* 1173 */     FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.LAVA, 1000), Ic2Items.lavaCell.func_77946_l(), Ic2Items.cell.func_77946_l());
/* 1174 */     FluidContainerRegistry.registerFluidContainer(new FluidStack(FluidRegistry.WATER, 1000), Ic2Items.waterCell.func_77946_l(), Ic2Items.cell.func_77946_l());
/* 1175 */     (new IC2Fluid("coolant", false)).setDensity(1000).setViscosity(3000);
/* 1176 */     FluidContainerRegistry.registerFluidContainer(new FluidStack((Fluid)IC2Fluid.fluids.get("coolant"), 1000), Ic2Items.reactorCoolantSimple.func_77946_l(), Ic2Items.cell.func_77946_l());
/* 1177 */     FluidContainerRegistry.registerFluidContainer(new FluidStack((Fluid)IC2Fluid.fluids.get("coolant"), 3000), Ic2Items.reactorCoolantTriple.func_77946_l(), Ic2Items.cell.func_77946_l());
/* 1178 */     FluidContainerRegistry.registerFluidContainer(new FluidStack((Fluid)IC2Fluid.fluids.get("coolant"), 6000), Ic2Items.reactorCoolantSix.func_77946_l(), Ic2Items.cell.func_77946_l());
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadAfterFluids() {
/* 1183 */     if (!FluidRegistry.isFluidRegistered("steam")) {
/* 1184 */       (new IC2Fluid("steam", false)).setDensity(-1000).setViscosity(500);
/*      */     } else {
/* 1186 */       IC2Fluid.fluids.put("steam", FluidRegistry.getFluid("steam"));
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void modsLoaded(FMLPostInitializationEvent event) {
/* 1192 */     TileEntityRecycler.init(config);
/* 1193 */     BlockPoleFence.loadBoots(config);
/* 1194 */     PlannerRegistry.init();
/* 1195 */     config.save();
/* 1196 */     if (!initialized)
/*      */     {
/* 1198 */       platform.displayError("IndustrialCraft 2 has failed to initialize properly.");
/*      */     }
/*      */     
/* 1201 */     addValuableOre(Blocks.field_150365_q, 1);
/* 1202 */     addValuableOre(Blocks.field_150352_o, 3);
/* 1203 */     addValuableOre(Blocks.field_150450_ax, 3);
/* 1204 */     addValuableOre(Blocks.field_150369_x, 3);
/* 1205 */     addValuableOre(Blocks.field_150366_p, 4);
/* 1206 */     addValuableOre(Blocks.field_150482_ag, 5);
/* 1207 */     addValuableOre(Blocks.field_150412_bA, 5);
/* 1208 */     addValuableOre(Blocks.field_150426_aN, 1);
/* 1209 */     loadAfterFluids();
/*      */     
/* 1211 */     loadState(ISubModul.LoadingState.PostLoad);
/*      */     
/* 1213 */     String minorMods = "";
/*      */     
/*      */     try {
/* 1216 */       Class<?> modPortalGun = Class.forName("portalgun.common.PortalGun");
/* 1217 */       Method addBlockIDToGrabListMeta = modPortalGun.getMethod("addBlockIDToGrabList", new Class[] { int.class, int[].class });
/* 1218 */       Method addBlockIDToGrabList = modPortalGun.getMethod("addBlockIDToGrabList", new Class[] { int.class });
/* 1219 */       if (Ic2Items.rubberWood != null)
/*      */       {
/* 1221 */         addBlockIDToGrabListMeta.invoke(null, new Object[] { Ic2Items.rubberWood.func_77973_b(), { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 } });
/*      */       }
/* 1223 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.miningPipe.func_77973_b() });
/* 1224 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.miningPipeTip.func_77973_b() });
/* 1225 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.batBox.func_77973_b() });
/* 1226 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.machine.func_77973_b() });
/* 1227 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.teleporter.func_77973_b() });
/* 1228 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.luminator.func_77973_b() });
/* 1229 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.activeLuminator.func_77973_b() });
/* 1230 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.scaffold.func_77973_b() });
/* 1231 */       addBlockIDToGrabList.invoke(null, new Object[] { Ic2Items.rubberTrampoline.func_77973_b() });
/* 1232 */       minorMods = minorMods + ", Portal Gun";
/*      */     }
/* 1234 */     catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1239 */       Method addCustomItem = Class.forName("mod_Gibbing").getMethod("addCustomItem", new Class[] { int.class, double.class });
/* 1240 */       addCustomItem.invoke(null, new Object[] { Ic2Items.nanoSaber.func_77973_b(), Double.valueOf(0.5D) });
/* 1241 */       addCustomItem.invoke(null, new Object[] { Ic2Items.chainsaw.func_77973_b(), Double.valueOf(0.5D) });
/* 1242 */       addCustomItem.invoke(null, new Object[] { Ic2Items.miningDrill.func_77973_b(), Double.valueOf(0.333D) });
/* 1243 */       addCustomItem.invoke(null, new Object[] { Ic2Items.diamondDrill.func_77973_b(), Double.valueOf(0.333D) });
/* 1244 */       minorMods = minorMods + ", Mob Amputation";
/*      */     }
/* 1246 */     catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1251 */       Field axes = Class.forName("mod_Timber").getDeclaredField("axes");
/* 1252 */       axes.set(null, axes.get(null) + ", " + Ic2Items.bronzeAxe.func_77973_b() + ", " + Ic2Items.chainsaw.func_77973_b());
/* 1253 */       minorMods = minorMods + ", Timber";
/*      */     }
/* 1255 */     catch (Throwable throwable) {}
/*      */ 
/*      */     
/* 1258 */     log.info("Loaded minor compatibility modules: " + (minorMods.isEmpty() ? "none" : minorMods.substring(2)));
/* 1259 */     GameRegistry.registerTileEntity(TileEntityBlock.class, "Empty Management TileEntity");
/* 1260 */     GameRegistry.registerTileEntity(TileEntityTexturedWall.class, "Textured Wall");
/* 1261 */     GameRegistry.registerTileEntity(TileEntityIronFurnace.class, "Iron Furnace");
/* 1262 */     GameRegistry.registerTileEntity(TileEntityElecFurnace.class, "Electric Furnace");
/* 1263 */     GameRegistry.registerTileEntity(TileEntityMacerator.class, "Macerator");
/* 1264 */     GameRegistry.registerTileEntity(TileEntityExtractor.class, "Extractor");
/* 1265 */     GameRegistry.registerTileEntity(TileEntityCompressor.class, "Compressor");
/* 1266 */     GameRegistry.registerTileEntity(TileEntityGenerator.class, "Generator");
/* 1267 */     GameRegistry.registerTileEntity(TileEntityGeoGenerator.class, "Geothermal Generator");
/* 1268 */     GameRegistry.registerTileEntity(TileEntityWaterGenerator.class, "Water Mill");
/* 1269 */     GameRegistry.registerTileEntity(TileEntitySolarGenerator.class, "Solar Panel");
/* 1270 */     GameRegistry.registerTileEntity(TileEntitySolarLV.class, "LV-Solar Panel-Classic");
/* 1271 */     GameRegistry.registerTileEntity(TileEntitySolarMV.class, "MV-Solar Panel-Classic");
/* 1272 */     GameRegistry.registerTileEntity(TileEntitySolarHV.class, "HV-Solar Panel-Classic");
/* 1273 */     GameRegistry.registerTileEntity(TileEntityWaterLV.class, "LV-Water Mill-Classic");
/* 1274 */     GameRegistry.registerTileEntity(TileEntityWaterMV.class, "MV-Water Mill-Classic");
/* 1275 */     GameRegistry.registerTileEntity(TileEntityWaterHV.class, "HV-Water Mill-Classic");
/* 1276 */     GameRegistry.registerTileEntity(TileEntityBasicTurbine.class, "Basic-Classic-SteamTurbine");
/* 1277 */     GameRegistry.registerTileEntity(TileEntityWindGenerator.class, "Wind Mill");
/* 1278 */     GameRegistry.registerTileEntity(TileEntityCanner.class, "Canning Machine");
/* 1279 */     GameRegistry.registerTileEntity(TileEntityMiner.class, "Miner");
/* 1280 */     GameRegistry.registerTileEntity(TileEntityPump.class, "Pump");
/* 1281 */     GameRegistry.registerTileEntity(TileEntityNuclearReactorSteam.class, "SteamReactor-Classic");
/* 1282 */     GameRegistry.registerTileEntity(TileEntityReactorChamberSteam.class, "SteamChamber-Classic");
/* 1283 */     if (BlockGenerator.tileEntityNuclearReactorClass == TileEntityNuclearReactorElectric.class)
/*      */     {
/* 1285 */       GameRegistry.registerTileEntity(TileEntityNuclearReactorElectric.class, "Nuclear Reactor");
/*      */     }
/* 1287 */     if (BlockReactorChamber.tileEntityReactorChamberClass == TileEntityReactorChamberElectric.class)
/*      */     {
/* 1289 */       GameRegistry.registerTileEntity(TileEntityReactorChamberElectric.class, "Reactor Chamber");
/*      */     }
/* 1291 */     GameRegistry.registerTileEntity(TileEntityMagnetizer.class, "Magnetizer");
/* 1292 */     GameRegistry.registerTileEntity(TileEntityCable.class, "Cable");
/* 1293 */     GameRegistry.registerTileEntity(TileEntityCableSplitter.class, "Cable-Splitter");
/* 1294 */     GameRegistry.registerTileEntity(TileEntityCableDetector.class, "Cable-Detector");
/* 1295 */     GameRegistry.registerTileEntity(TileEntityElectricBatBox.class, "BatBox");
/* 1296 */     GameRegistry.registerTileEntity(TileEntityElectricMFE.class, "MFE");
/* 1297 */     GameRegistry.registerTileEntity(TileEntityElectricMFSU.class, "MFSU");
/* 1298 */     GameRegistry.registerTileEntity(TileEntityTransformerLV.class, "LV-Transformer");
/* 1299 */     GameRegistry.registerTileEntity(TileEntityTransformerMV.class, "MV-Transformer");
/* 1300 */     GameRegistry.registerTileEntity(TileEntityTransformerHV.class, "HV-Transformer");
/* 1301 */     GameRegistry.registerTileEntity(TileEntityTransformerEV.class, "EV-Transformer");
/* 1302 */     GameRegistry.registerTileEntity(TileEntityAdjustableTransformer.class, "Adjustable-Transformer");
/* 1303 */     GameRegistry.registerTileEntity(TileEntityChargePadLV.class, "ChargePadLV-Classic");
/* 1304 */     GameRegistry.registerTileEntity(TileEntityChargePadMV.class, "ChargePadMV-Classic");
/* 1305 */     GameRegistry.registerTileEntity(TileEntityChargePadHV.class, "ChargePadHV-Classic");
/* 1306 */     GameRegistry.registerTileEntity(TileEntityChargePadNuclear.class, "ChargePadNuclear-Classic");
/* 1307 */     GameRegistry.registerTileEntity(TileEntityBatteryBox.class, "BatteryBox");
/* 1308 */     GameRegistry.registerTileEntity(TileEntityLuminator.class, "Luminator");
/* 1309 */     GameRegistry.registerTileEntity(TileEntityLuminatorMultipart.class, "LuminatorMultiPart");
/* 1310 */     GameRegistry.registerTileEntity(TileEntityElectrolyzer.class, "Electrolyzer");
/* 1311 */     GameRegistry.registerTileEntity(TileEntityCropScanner.class, "CropScanner");
/* 1312 */     GameRegistry.registerTileEntity(TileEntityCropHarvester.class, "IC2CCropHarvester");
/* 1313 */     GameRegistry.registerTileEntity(TileEntityUraniumEnricher.class, "Uranium Enricher");
/* 1314 */     if (BlockPersonal.tileEntityPersonalChestClass == TileEntityPersonalChest.class)
/*      */     {
/* 1316 */       GameRegistry.registerTileEntity(TileEntityPersonalChest.class, "Personal Safe");
/*      */     }
/* 1318 */     GameRegistry.registerTileEntity(TileEntityTradeOMat.class, "Trade-O-Mat");
/* 1319 */     GameRegistry.registerTileEntity(TileEntityFluidOMat.class, "Fluid-O-Mat");
/* 1320 */     GameRegistry.registerTileEntity(TileEntityEnergyOMat.class, "Energy-O-Mat");
/* 1321 */     GameRegistry.registerTileEntity(TileEntityRecycler.class, "Recycler");
/* 1322 */     GameRegistry.registerTileEntity(TileEntityInduction.class, "Induction Furnace");
/* 1323 */     GameRegistry.registerTileEntity(TileEntitySingularity.class, "SingularityCompressor-Classic");
/* 1324 */     GameRegistry.registerTileEntity(TileEntityCentrifuge.class, "CentrifugeExtractor-Classic");
/* 1325 */     GameRegistry.registerTileEntity(TileEntityRotary.class, "Rotary Macerator-Classic");
/* 1326 */     GameRegistry.registerTileEntity(TileEntityCharged.class, "Charged-Electorlyzer-Classic");
/* 1327 */     GameRegistry.registerTileEntity(TileEntityVacuumCanner.class, "Vacum Canner-Classic");
/* 1328 */     GameRegistry.registerTileEntity(TileEntityCompacting.class, "Compacting Recycler-Classic");
/* 1329 */     GameRegistry.registerTileEntity(TileEntityMatter.class, "Mass Fabricator");
/* 1330 */     GameRegistry.registerTileEntity(TileEntityTerra.class, "Terraformer");
/* 1331 */     GameRegistry.registerTileEntity(TileEntityTeleporter.class, "Teleporter");
/* 1332 */     GameRegistry.registerTileEntity(TileEntityTesla.class, "Tesla Coil");
/* 1333 */     GameRegistry.registerTileEntity(TileEntityCableDetector.class, "Detector Cable");
/* 1334 */     GameRegistry.registerTileEntity(TileEntityCableSplitter.class, "SplitterCable");
/* 1335 */     GameRegistry.registerTileEntity(TileEntityCrop.class, "TECrop");
/* 1336 */     GameRegistry.registerTileEntity(TileEntityBarrel.class, "TEBarrel");
/* 1337 */     GameRegistry.registerTileEntity(TileEntityCropmatron.class, "Crop-Matron");
/* 1338 */     GameRegistry.registerTileEntity(TileEntityElectricEnchanter.class, "Electric Enchanter");
/* 1339 */     GameRegistry.registerTileEntity(TileEntityOreScanner.class, "OreScanner");
/* 1340 */     GameRegistry.registerTileEntity(TileEntityReactorPlanner.class, "Reactor Planner");
/* 1341 */     GameRegistry.registerTileEntity(TileEntitySoundBeacon.class, "Sound Beacon");
/* 1342 */     GameRegistry.registerTileEntity(TileEntityCreativeStorage.class, "Creative Generator");
/* 1343 */     ExpHelper.onAfterInit();
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void onFinishLoad(FMLLoadCompleteEvent evt) {
/* 1349 */     validateStacks();
/*      */   }
/*      */ 
/*      */   
/*      */   @EventHandler
/*      */   public void onServerStopped(FMLServerStoppedEvent evt) {
/* 1355 */     ((NetworkManager)network.get()).onUnload();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateStacks() {
/*      */     try {
/* 1362 */       Class<Ic2Items> clz = Ic2Items.class;
/* 1363 */       Field[] items = clz.getFields();
/* 1364 */       for (int i = 0; i < items.length; i++) {
/*      */ 
/*      */         
/*      */         try {
/* 1368 */           ItemStack item = (ItemStack)items[i].get(null);
/* 1369 */           if (item != null)
/*      */           {
/* 1371 */             if (item.field_77994_a != 1) {
/*      */               
/*      */               try {
/*      */                 
/* 1375 */                 FMLLog.getLogger().info("Item: " + item.func_82833_r() + " Has not stacksize 0");
/*      */               }
/* 1377 */               catch (Exception exception) {}
/*      */             }
/*      */ 
/*      */             
/* 1381 */             item.field_77994_a = 1;
/* 1382 */             items[i].set(null, item);
/*      */           }
/*      */         
/* 1385 */         } catch (Exception e) {
/*      */           
/* 1387 */           e.printStackTrace();
/*      */         }
/*      */       
/*      */       } 
/* 1391 */     } catch (Exception e) {
/*      */       
/* 1393 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void registerWrenchSupporter(IWrenchHandler par1) {
/* 1400 */     if (par1 == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1404 */     this; boolean wasEmpty = handlers.isEmpty();
/* 1405 */     handlers.add(par1);
/* 1406 */     if (wasEmpty)
/*      */     {
/* 1408 */       new EventManager();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadState(ISubModul.LoadingState par1) {
/* 1414 */     this; for (ISubModul modul : IC2.modul.values()) {
/*      */       
/* 1416 */       if (par1 == ISubModul.LoadingState.BeforeObjectLoad) {
/*      */         
/* 1418 */         modul.beforeItemLoad(); continue;
/*      */       } 
/* 1420 */       if (par1 == ISubModul.LoadingState.AfterObjectLoad) {
/*      */         
/* 1422 */         modul.afterItemLoad(); continue;
/*      */       } 
/* 1424 */       if (par1 == ISubModul.LoadingState.PostLoad)
/*      */       {
/* 1426 */         modul.onPostLoad();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadModules() {
/* 1433 */     List<ISubModul> modules = getSubModules(new String[] { "neiIntegration", "cgIntegration", "bobIntigration", "tcIntigration", "aeIntigration", "nerIntigration", "thcIntigration", "bcIntigration", "toolIntigration", "rfIntigration", "peIntigration", "waIntigration" });
/*      */ 
/*      */     
/* 1436 */     if (modules != null && modules.size() > 0)
/*      */     {
/* 1438 */       for (ISubModul modul : modules) {
/*      */         
/* 1440 */         log.info("Try Loading SubModul: " + modul.getModulName());
/* 1441 */         if (Boolean.parseBoolean(config.get("modules", modul.getConfigName(), true).getString()) && modul.canLoad() && modul.supportsSide(FMLCommonHandler.instance().getEffectiveSide())) {
/*      */           
/* 1443 */           this; IC2.modul.put(modul.getModulName(), modul);
/* 1444 */           log.info("Loaded SubModul: " + modul.getModulName());
/*      */           
/*      */           continue;
/*      */         } 
/* 1448 */         log.info("Could not Load SubModul: " + modul.getModulName());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private List<ISubModul> getSubModules(String... par1) {
/* 1456 */     ArrayList<ISubModul> modules = new ArrayList<>();
/* 1457 */     for (String cu : par1) {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/* 1462 */         Class<?> clz = Class.forName("ic2." + cu + ".SubModul");
/* 1463 */         if (clz != null)
/*      */         {
/* 1465 */           ISubModul modul = (ISubModul)clz.newInstance();
/* 1466 */           if (modul != null)
/*      */           {
/* 1468 */             modules.add(modul);
/*      */           }
/*      */         }
/*      */       
/* 1472 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */     
/* 1476 */     return modules;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBurnTime(ItemStack stack) {
/* 1482 */     if (Ic2Items.rubberSapling != null && stack.equals(Ic2Items.rubberSapling))
/*      */     {
/* 1484 */       return 80;
/*      */     }
/* 1486 */     if (stack.func_77973_b() == Items.field_151120_aE)
/*      */     {
/* 1488 */       return 50;
/*      */     }
/* 1490 */     if (stack.func_77973_b() == Item.func_150898_a(Blocks.field_150434_aF))
/*      */     {
/* 1492 */       return 50;
/*      */     }
/* 1494 */     if (stack.func_77973_b() == Ic2Items.scrap.func_77973_b())
/*      */     {
/* 1496 */       return 350;
/*      */     }
/* 1498 */     if (stack.func_77973_b() == Ic2Items.scrapBox.func_77973_b())
/*      */     {
/* 1500 */       return 3150;
/*      */     }
/* 1502 */     if (stack.func_77973_b() == Ic2Items.lavaCell.func_77973_b())
/*      */     {
/* 1504 */       return TileEntityFurnace.func_145952_a(new ItemStack(Items.field_151129_at));
/*      */     }
/* 1506 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerCraftingRecipes() {
/* 1511 */     if (ironName.equals("ingotSteel") && OreDictionary.getOres("ingotSteel", false).size() <= 0) {
/*      */       
/* 1513 */       ironName = "ingotRefinedIron";
/* 1514 */       log.info("Dissabled steel");
/*      */     } 
/* 1516 */     ItemStack refinedIron = ironName.equals("ingotSteel") ? OreDictionary.getOres("ingotSteel", false).get(0) : Ic2Items.refinedIronIngot.func_77946_l();
/* 1517 */     if (OreDictionary.doesOreNameExist("dustLead")) {
/*      */       
/* 1519 */       if (OreDictionary.doesOreNameExist("oreLead"))
/*      */       {
/* 1521 */         TileEntityMacerator.addRecipe("oreLead", 1, OreDictionary.getOres("dustLead").get(0));
/*      */       }
/* 1523 */       if (OreDictionary.doesOreNameExist("ingotLead"))
/*      */       {
/* 1525 */         TileEntityMacerator.addRecipe("ingotLead", 1, OreDictionary.getOres("dustLead").get(0));
/*      */       }
/*      */     } 
/* 1528 */     Object basic = Loader.isModLoaded("gregtech") ? "circuitBasic" : Ic2Items.electronicCircuit.func_77946_l();
/* 1529 */     Object adv = Loader.isModLoaded("gregtech") ? "circuitAdvanced" : Ic2Items.advancedCircuit.func_77946_l();
/*      */     
/* 1531 */     ICraftingRecipeManager advRecipes = Recipes.advRecipes;
/* 1532 */     advRecipes.addRecipe(Ic2Items.rubberBoat, new Object[] { "   ", "XYX", "XXX", Character.valueOf('X'), "itemRubber", Character.valueOf('Y'), Items.field_151124_az });
/* 1533 */     advRecipes.addShapelessRecipe(Ic2Items.rubberBoat, new Object[] { Ic2Items.brokenRubberBoat, "itemRubber", "itemRubber" });
/* 1534 */     advRecipes.addRecipe(Ic2Items.carbonBoat, new Object[] { "   ", "XYX", "XXX", Character.valueOf('X'), Ic2Items.carbonPlate, Character.valueOf('Y'), Items.field_151124_az });
/* 1535 */     advRecipes.addRecipe(Ic2Items.electricBoat, new Object[] { "   ", "XYS", "XXC", Character.valueOf('S'), Blocks.field_150442_at, Character.valueOf('C'), Ic2Items.basicTurbine, Character.valueOf('X'), ironName, Character.valueOf('Y'), Ic2Items.carbonBoat });
/* 1536 */     advRecipes.addRecipe(Ic2Items.copperBlock, new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), "ingotCopper" });
/* 1537 */     advRecipes.addRecipe(Ic2Items.bronzeBlock, new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), "ingotBronze" });
/* 1538 */     advRecipes.addRecipe(Ic2Items.tinBlock, new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), "ingotTin" });
/* 1539 */     advRecipes.addRecipe(Ic2Items.uraniumBlock, new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), "ingotUranium" });
/* 1540 */     advRecipes.addRecipe(Ic2Items.ironFurnace, new Object[] { "III", "I I", "III", Character.valueOf('I'), Items.field_151042_j });
/* 1541 */     advRecipes.addRecipe(Ic2Items.ironFurnace, new Object[] { " I ", "I I", "IFI", Character.valueOf('I'), Items.field_151042_j, Character.valueOf('F'), Blocks.field_150460_al });
/* 1542 */     advRecipes.addRecipe(Ic2Items.electroFurnace, new Object[] { " C ", "RFR", Character.valueOf('C'), basic, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('F'), Ic2Items.ironFurnace });
/* 1543 */     advRecipes.addRecipe(Ic2Items.macerator, new Object[] { "FFF", "SMS", " C ", Character.valueOf('F'), Items.field_151145_ak, Character.valueOf('S'), Blocks.field_150347_e, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), basic });
/* 1544 */     advRecipes.addRecipe(Ic2Items.extractor, new Object[] { "TMT", "TCT", Character.valueOf('T'), Ic2Items.treetap, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), basic });
/* 1545 */     advRecipes.addRecipe(Ic2Items.compressor, new Object[] { "S S", "SMS", "SCS", Character.valueOf('S'), Blocks.field_150348_b, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), basic });
/* 1546 */     advRecipes.addRecipe(Ic2Items.miner, new Object[] { "CMC", " P ", " P ", Character.valueOf('P'), Ic2Items.miningPipe, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), basic });
/* 1547 */     advRecipes.addRecipe(Ic2Items.pump, new Object[] { "cCc", "cMc", "PTP", Character.valueOf('c'), Ic2Items.cell, Character.valueOf('T'), Ic2Items.treetap, Character.valueOf('P'), Ic2Items.miningPipe, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), basic });
/* 1548 */     advRecipes.addRecipe(Ic2Items.magnetizer, new Object[] { "RFR", "RMR", "RFR", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('F'), Ic2Items.ironFence, Character.valueOf('M'), Ic2Items.machine });
/* 1549 */     advRecipes.addRecipe(Ic2Items.electrolyzer, new Object[] { "c c", "cCc", "EME", Character.valueOf('E'), Ic2Items.cell, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), basic });
/* 1550 */     advRecipes.addRecipe(Ic2Items.advancedMachine, new Object[] { " A ", "CMC", " A ", Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('M'), Ic2Items.machine });
/* 1551 */     advRecipes.addRecipe(Ic2Items.advancedMachine, new Object[] { " C ", "AMA", " C ", Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('M'), Ic2Items.machine });
/* 1552 */     advRecipes.addRecipe(Ic2Items.personalSafe, new Object[] { "c", "M", "C", Character.valueOf('c'), basic, Character.valueOf('C'), Blocks.field_150486_ae, Character.valueOf('M'), Ic2Items.machine });
/* 1553 */     advRecipes.addRecipe(Ic2Items.tradeOMat, new Object[] { "RRR", "CMC", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Blocks.field_150486_ae, Character.valueOf('M'), Ic2Items.machine });
/* 1554 */     advRecipes.addRecipe(Ic2Items.fluidOMat, new Object[] { "GGG", "PMP", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('P'), Ic2Items.pump, Character.valueOf('M'), Ic2Items.machine });
/* 1555 */     advRecipes.addRecipe(Ic2Items.energyOMat, new Object[] { "RBR", "CMC", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('M'), Ic2Items.machine, Character.valueOf('B'), Ic2Items.reBattery });
/* 1556 */     advRecipes.addRecipe(Ic2Items.massFabricator, new Object[] { "GCG", "ALA", "GCG", Character.valueOf('A'), Ic2Items.advancedMachine, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('C'), adv });
/* 1557 */     advRecipes.addRecipe(Ic2Items.terraformer, new Object[] { "GTG", "DMD", "GDG", Character.valueOf('T'), Ic2Items.terraformerBlueprint, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('D'), Blocks.field_150346_d, Character.valueOf('M'), Ic2Items.advancedMachine });
/* 1558 */     advRecipes.addRecipe(Ic2Items.teleporter, new Object[] { "GFG", "CMC", "GDG", Character.valueOf('M'), Ic2Items.advancedMachine, Character.valueOf('C'), Ic2Items.glassFiberCableItem, Character.valueOf('F'), Ic2Items.frequencyTransmitter, Character.valueOf('G'), adv, Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1559 */     advRecipes.addRecipe(Ic2Items.teleporter, new Object[] { "GFG", "CMC", "GDG", Character.valueOf('M'), Ic2Items.advancedMachine, Character.valueOf('C'), Ic2Items.glassFiberCableItem, Character.valueOf('F'), Ic2Items.frequencyTransmitter, Character.valueOf('G'), adv, Character.valueOf('D'), Items.field_151045_i });
/* 1560 */     advRecipes.addRecipe(Ic2Items.inductionFurnace, new Object[] { "CCC", "CFC", "CMC", Character.valueOf('C'), "ingotCopper", Character.valueOf('F'), Ic2Items.electroFurnace, Character.valueOf('M'), Ic2Items.advancedMachine });
/* 1561 */     advRecipes.addRecipe(Ic2Items.machine, new Object[] { "III", "I I", "III", Character.valueOf('I'), ironName });
/* 1562 */     advRecipes.addRecipe(Ic2Items.recycler, new Object[] { " G ", "DMD", "IDI", Character.valueOf('D'), Blocks.field_150346_d, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('M'), Ic2Items.compressor, Character.valueOf('I'), ironName });
/* 1563 */     advRecipes.addRecipe(Ic2Items.canner, new Object[] { "TCT", "TMT", "TTT", Character.valueOf('T'), "ingotTin", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), basic });
/* 1564 */     advRecipes.addRecipe(Ic2Items.teslaCoil, new Object[] { "RRR", "RMR", "ICI", Character.valueOf('M'), Ic2Items.mvTransformer, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), basic, Character.valueOf('I'), ironName });
/* 1565 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.luminator, 8), new Object[] { "ICI", "GTG", "GGG", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('I'), ironName, Character.valueOf('T'), Ic2Items.tinCableItem, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1566 */     advRecipes.addShapelessRecipe(Ic2Items.luminatorMultipart.func_77946_l(), new Object[] { Ic2Items.luminator.func_77946_l() });
/* 1567 */     advRecipes.addShapelessRecipe(Ic2Items.luminator.func_77946_l(), new Object[] { Ic2Items.luminatorMultipart.func_77946_l() });
/* 1568 */     advRecipes.addRecipe(Ic2Items.generator, new Object[] { " B ", "III", " F ", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('F'), Ic2Items.ironFurnace, Character.valueOf('I'), ironName });
/* 1569 */     advRecipes.addRecipe(Ic2Items.generator, new Object[] { " B ", "III", " F ", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('F'), Ic2Items.ironFurnace, Character.valueOf('I'), ironName });
/* 1570 */     advRecipes.addRecipe(Ic2Items.generator, new Object[] { "B", "M", "F", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('F'), Blocks.field_150460_al, Character.valueOf('M'), Ic2Items.machine });
/* 1571 */     advRecipes.addRecipe(Ic2Items.generator, new Object[] { "B", "M", "F", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('F'), Blocks.field_150460_al, Character.valueOf('M'), Ic2Items.machine });
/* 1572 */     advRecipes.addRecipe(Ic2Items.reactorChamber, new Object[] { " C ", "CMC", " C ", Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('M'), Ic2Items.machine });
/* 1573 */     advRecipes.addRecipe(Ic2Items.lvChargePad, new Object[] { "XYX", "CVC", Character.valueOf('Y'), Blocks.field_150456_au, Character.valueOf('X'), "itemRubber", Character.valueOf('C'), basic, Character.valueOf('V'), Ic2Items.batBox });
/* 1574 */     advRecipes.addRecipe(Ic2Items.mvChargePad, new Object[] { "XYX", "CVC", Character.valueOf('Y'), Blocks.field_150456_au, Character.valueOf('X'), "itemRubber", Character.valueOf('C'), basic, Character.valueOf('V'), Ic2Items.mfeUnit });
/* 1575 */     advRecipes.addRecipe(Ic2Items.hvChargePad, new Object[] { "XYX", "CVC", Character.valueOf('Y'), Blocks.field_150456_au, Character.valueOf('X'), Ic2Items.carbonPlate, Character.valueOf('C'), adv, Character.valueOf('V'), Ic2Items.mfsUnit });
/* 1576 */     advRecipes.addRecipe(Ic2Items.soundBeacon, new Object[] { "XYX", "YCY", "DYD", Character.valueOf('X'), Ic2Items.frequencyTransmitter, Character.valueOf('Y'), "ingotCopper", Character.valueOf('C'), Ic2Items.machine, Character.valueOf('D'), Ic2Items.reBattery });
/* 1577 */     advRecipes.addRecipe(Ic2Items.soundBeacon, new Object[] { "XYX", "YCY", "DYD", Character.valueOf('X'), Ic2Items.frequencyTransmitter, Character.valueOf('Y'), "ingotCopper", Character.valueOf('C'), Ic2Items.machine, Character.valueOf('D'), Ic2Items.chargedReBattery });
/* 1578 */     advRecipes.addRecipe(Ic2Items.basicTurbine, new Object[] { " A ", "XYC", " B ", Character.valueOf('A'), basic, Character.valueOf('X'), Ic2Items.windMill, Character.valueOf('Y'), Ic2Items.generator, Character.valueOf('C'), Ic2Items.waterMill, Character.valueOf('B'), Ic2Items.doubleInsulatedBronzeCableItem });
/* 1579 */     if (energyGeneratorWater > 0)
/*      */     {
/* 1581 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.waterMill, 2), new Object[] { "SPS", "PGP", "SPS", Character.valueOf('S'), "stickWood", Character.valueOf('P'), "plankWood", Character.valueOf('G'), Ic2Items.generator });
/*      */     }
/* 1583 */     if (energyGeneratorSolar > 0)
/*      */     {
/* 1585 */       advRecipes.addRecipe(Ic2Items.solarPanel, new Object[] { "CgC", "gCg", "cGc", Character.valueOf('G'), Ic2Items.generator, Character.valueOf('C'), "dustCoal", Character.valueOf('g'), Blocks.field_150359_w, Character.valueOf('c'), basic });
/*      */     }
/* 1587 */     if (energyGeneratorWind > 0)
/*      */     {
/* 1589 */       advRecipes.addRecipe(Ic2Items.windMill, new Object[] { "I I", " G ", "I I", Character.valueOf('I'), Items.field_151042_j, Character.valueOf('G'), Ic2Items.generator });
/*      */     }
/* 1591 */     if (energyGeneratorNuclear > 0) {
/*      */       
/* 1593 */       advRecipes.addRecipe(Ic2Items.nuclearReactor, new Object[] { " c ", "CCC", " G ", Character.valueOf('C'), Ic2Items.reactorChamber, Character.valueOf('c'), adv, Character.valueOf('G'), Ic2Items.generator });
/* 1594 */       advRecipes.addRecipe(Ic2Items.nuclearChargePad, new Object[] { "XYX", "CVC", Character.valueOf('Y'), Ic2Items.hvChargePad, Character.valueOf('X'), Ic2Items.iridiumPlate, Character.valueOf('C'), Ic2Items.chillingTerraformerBlueprint, Character.valueOf('V'), Ic2Items.nuclearReactor });
/*      */     } 
/* 1596 */     if (energyGeneratorGeo > 0)
/*      */     {
/* 1598 */       advRecipes.addRecipe(Ic2Items.geothermalGenerator, new Object[] { "gCg", "gCg", "IGI", Character.valueOf('G'), Ic2Items.generator, Character.valueOf('C'), Ic2Items.cell, Character.valueOf('g'), Blocks.field_150359_w, Character.valueOf('I'), ironName });
/*      */     }
/* 1600 */     advRecipes.addRecipe(Ic2Items.steamReactor, new Object[] { "OXO", "YYY", "OBO", Character.valueOf('O'), Ic2Items.advancedAlloy, Character.valueOf('X'), adv, Character.valueOf('Y'), Ic2Items.steamReactorChamber, Character.valueOf('B'), Ic2Items.nuclearReactor });
/* 1601 */     advRecipes.addRecipe(Ic2Items.steamReactorChamber, new Object[] { "XYX", "YCY", "XYX", Character.valueOf('Y'), Ic2Items.reinforcedGlass, Character.valueOf('C'), Ic2Items.reactorChamber, Character.valueOf('X'), Ic2Items.advancedAlloy });
/* 1602 */     advRecipes.addRecipe(Ic2Items.lvSolarPanel, new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), Ic2Items.solarPanel, Character.valueOf('y'), Ic2Items.lvTransformer });
/* 1603 */     advRecipes.addRecipe(Ic2Items.mvSolarPanel, new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), Ic2Items.lvSolarPanel, Character.valueOf('y'), Ic2Items.mvTransformer });
/* 1604 */     advRecipes.addRecipe(Ic2Items.hvSolarPanel, new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), Ic2Items.mvSolarPanel, Character.valueOf('y'), Ic2Items.hvTransformer });
/* 1605 */     advRecipes.addRecipe(Ic2Items.lvWaterMill, new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), Ic2Items.waterMill, Character.valueOf('y'), Ic2Items.lvTransformer });
/* 1606 */     advRecipes.addRecipe(Ic2Items.mvWaterMill, new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), Ic2Items.lvWaterMill, Character.valueOf('y'), Ic2Items.mvTransformer });
/* 1607 */     advRecipes.addRecipe(Ic2Items.hvWaterMill, new Object[] { "xxx", "xyx", "xxx", Character.valueOf('x'), Ic2Items.mvWaterMill, Character.valueOf('y'), Ic2Items.hvTransformer });
/* 1608 */     advRecipes.addShapelessRecipe(Ic2Items.reactorUraniumSimple, new Object[] { Ic2Items.reEnrichedUraniumCell, "dustCoal" });
/* 1609 */     advRecipes.addShapelessRecipe(new ItemStack(Ic2Items.reactorIsotopeCell.func_77973_b(), 1, 9999), new Object[] { Ic2Items.nearDepletedUraniumCell, "dustCoal" });
/* 1610 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.nearDepletedUraniumCell, 8), new Object[] { "CCC", "CUC", "CCC", Character.valueOf('C'), Ic2Items.cell, Character.valueOf('U'), "ingotUranium" });
/* 1611 */     advRecipes.addShapelessRecipe(Ic2Items.reactorUraniumSimple, new Object[] { Ic2Items.cell, "ingotUranium" });
/* 1612 */     advRecipes.addRecipe(Ic2Items.reactorUraniumDual, new Object[] { "UCU", Character.valueOf('U'), Ic2Items.reactorUraniumSimple, Character.valueOf('C'), Ic2Items.denseCopperPlate });
/* 1613 */     advRecipes.addRecipe(Ic2Items.reactorUraniumQuad, new Object[] { " U ", "CCC", " U ", Character.valueOf('U'), Ic2Items.reactorUraniumDual, Character.valueOf('C'), Ic2Items.denseCopperPlate });
/* 1614 */     advRecipes.addRecipe(Ic2Items.reactorCoolantSimple, new Object[] { " T ", "TWT", " T ", Character.valueOf('W'), "liquid$" + Block.func_149682_b(Blocks.field_150355_j), Character.valueOf('T'), "ingotTin" });
/* 1615 */     advRecipes.addRecipe(Ic2Items.reactorCoolantTriple, new Object[] { "TTT", "CCC", "TTT", Character.valueOf('C'), Ic2Items.reactorCoolantSimple, Character.valueOf('T'), "ingotTin" });
/* 1616 */     advRecipes.addRecipe(Ic2Items.reactorCoolantSix, new Object[] { "TCT", "TcT", "TCT", Character.valueOf('C'), Ic2Items.reactorCoolantTriple, Character.valueOf('T'), "ingotTin", Character.valueOf('c'), Ic2Items.denseCopperPlate });
/* 1617 */     advRecipes.addShapelessRecipe(Ic2Items.reactorPlating, new Object[] { Ic2Items.denseCopperPlate, Ic2Items.advancedAlloy });
/* 1618 */     advRecipes.addShapelessRecipe(Ic2Items.reactorPlatingHeat, new Object[] { Ic2Items.reactorPlating, Ic2Items.denseCopperPlate, Ic2Items.denseCopperPlate });
/* 1619 */     advRecipes.addShapelessRecipe(Ic2Items.reactorPlatingExplosive, new Object[] { Ic2Items.reactorPlating, Ic2Items.advancedAlloy, Ic2Items.advancedAlloy });
/* 1620 */     advRecipes.addRecipe(Ic2Items.reactorHeatSwitch, new Object[] { " c ", "TCT", " T ", Character.valueOf('c'), basic, Character.valueOf('T'), "ingotTin", Character.valueOf('C'), Ic2Items.denseCopperPlate });
/* 1621 */     advRecipes.addRecipe(Ic2Items.reactorHeatSwitchCore, new Object[] { "C", "S", "C", Character.valueOf('S'), Ic2Items.reactorHeatSwitch, Character.valueOf('C'), Ic2Items.denseCopperPlate });
/* 1622 */     advRecipes.addRecipe(Ic2Items.reactorHeatSwitchSpread, new Object[] { " G ", "GSG", " G ", Character.valueOf('S'), Ic2Items.reactorHeatSwitch, Character.valueOf('G'), Items.field_151043_k });
/* 1623 */     advRecipes.addRecipe(Ic2Items.reactorHeatSwitchDiamond, new Object[] { "GcG", "SCS", "GcG", Character.valueOf('S'), Ic2Items.reactorHeatSwitch, Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('G'), Ic2Items.glassFiberCableItem, Character.valueOf('c'), basic });
/* 1624 */     advRecipes.addRecipe(Ic2Items.reactorVent, new Object[] { "I#I", "# #", "I#I", Character.valueOf('I'), ironName, Character.valueOf('#'), Blocks.field_150411_aY });
/* 1625 */     advRecipes.addRecipe(Ic2Items.reactorVentCore, new Object[] { "C", "V", "C", Character.valueOf('V'), Ic2Items.reactorVent, Character.valueOf('C'), Ic2Items.denseCopperPlate });
/* 1626 */     advRecipes.addRecipe(Ic2Items.reactorVentGold, new Object[] { "G", "V", "G", Character.valueOf('V'), Ic2Items.reactorVentCore, Character.valueOf('G'), Items.field_151043_k });
/* 1627 */     advRecipes.addRecipe(Ic2Items.reactorVentSpread, new Object[] { "#T#", "TVT", "#T#", Character.valueOf('V'), Ic2Items.reactorVent, Character.valueOf('#'), Blocks.field_150411_aY, Character.valueOf('T'), "ingotTin" });
/* 1628 */     advRecipes.addRecipe(Ic2Items.reactorVentDiamond, new Object[] { "#V#", "#D#", "#V#", Character.valueOf('V'), Ic2Items.reactorVent, Character.valueOf('#'), Blocks.field_150411_aY, Character.valueOf('D'), Items.field_151045_i });
/* 1629 */     advRecipes.addRecipe(Ic2Items.reactorSteamVent, new Object[] { "I#I", "#X#", "I#I", Character.valueOf('X'), Ic2Items.reactorVent, Character.valueOf('I'), Blocks.field_150359_w, Character.valueOf('#'), Blocks.field_150411_aY });
/* 1630 */     advRecipes.addShapelessRecipe(Ic2Items.reactorVent, new Object[] { Ic2Items.reactorSteamVent });
/* 1631 */     advRecipes.addRecipe(Ic2Items.reactorSteamVentCore, new Object[] { "C", "V", "C", Character.valueOf('V'), Ic2Items.reactorSteamVent, Character.valueOf('C'), Ic2Items.denseCopperPlate });
/* 1632 */     advRecipes.addRecipe(Ic2Items.reactorSteamVentCore, new Object[] { "I#I", "#X#", "I#I", Character.valueOf('X'), Ic2Items.reactorVentCore, Character.valueOf('I'), Blocks.field_150359_w, Character.valueOf('#'), Blocks.field_150411_aY });
/* 1633 */     advRecipes.addShapelessRecipe(Ic2Items.reactorVentCore, new Object[] { Ic2Items.reactorSteamVentCore });
/* 1634 */     advRecipes.addRecipe(Ic2Items.reactorSteamVentGold, new Object[] { "G", "V", "G", Character.valueOf('V'), Ic2Items.reactorSteamVentCore, Character.valueOf('G'), Items.field_151043_k });
/* 1635 */     advRecipes.addRecipe(Ic2Items.reactorSteamVentGold, new Object[] { "I#I", "#X#", "I#I", Character.valueOf('X'), Ic2Items.reactorVentGold, Character.valueOf('I'), Blocks.field_150359_w, Character.valueOf('#'), Blocks.field_150411_aY });
/* 1636 */     advRecipes.addShapelessRecipe(Ic2Items.reactorVentGold, new Object[] { Ic2Items.reactorSteamVentGold });
/* 1637 */     advRecipes.addRecipe(Ic2Items.reactorSteamVentDiamond, new Object[] { "#V#", "#D#", "#V#", Character.valueOf('V'), Ic2Items.reactorSteamVent, Character.valueOf('#'), Blocks.field_150411_aY, Character.valueOf('D'), Items.field_151045_i });
/* 1638 */     advRecipes.addRecipe(Ic2Items.reactorSteamVentDiamond, new Object[] { "I#I", "#X#", "I#I", Character.valueOf('X'), Ic2Items.reactorVentDiamond, Character.valueOf('I'), Blocks.field_150359_w, Character.valueOf('#'), Blocks.field_150411_aY });
/* 1639 */     advRecipes.addShapelessRecipe(Ic2Items.reactorVentDiamond, new Object[] { Ic2Items.reactorSteamVentDiamond });
/* 1640 */     advRecipes.addRecipe(Ic2Items.reactorHeatpack, new Object[] { "c", "L", "C", Character.valueOf('c'), basic, Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('L'), Ic2Items.lavaCell });
/* 1641 */     advRecipes.addRecipe(Ic2Items.reactorReflector, new Object[] { "TcT", "cCc", "TcT", Character.valueOf('c'), "dustCoal", Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('T'), "dustTin" });
/* 1642 */     advRecipes.addRecipe(Ic2Items.reactorReflectorThick, new Object[] { " R ", "RCR", " R ", Character.valueOf('C'), Ic2Items.denseCopperPlate, Character.valueOf('R'), Ic2Items.reactorReflector });
/* 1643 */     advRecipes.addRecipe(Ic2Items.reactorCondensator, new Object[] { "RRR", "RVR", "RSR", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('V'), Ic2Items.reactorVent, Character.valueOf('S'), Ic2Items.reactorHeatSwitch });
/* 1644 */     new RecipeGradual((ItemGradual)Ic2Items.reactorCondensator.func_77973_b(), new ItemStack(Items.field_151137_ax), 10000);
/* 1645 */     advRecipes.addRecipe(Ic2Items.reactorCondensatorLap, new Object[] { "RVR", "CLC", "RSR", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('V'), Ic2Items.reactorVentCore, Character.valueOf('S'), Ic2Items.reactorHeatSwitchCore, Character.valueOf('C'), Ic2Items.reactorCondensator, Character.valueOf('L'), Blocks.field_150368_y });
/* 1646 */     new RecipeGradual((ItemGradual)Ic2Items.reactorCondensatorLap.func_77973_b(), new ItemStack(Items.field_151137_ax), 5000);
/* 1647 */     new RecipeGradual((ItemGradual)Ic2Items.reactorCondensatorLap.func_77973_b(), new ItemStack(Items.field_151100_aR, 1, 4), 40000);
/* 1648 */     advRecipes.addRecipe(Ic2Items.batBox, new Object[] { "PCP", "BBB", "PPP", Character.valueOf('P'), "plankWood", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('B'), Ic2Items.reBattery });
/* 1649 */     advRecipes.addRecipe(Ic2Items.batBox, new Object[] { "PCP", "BBB", "PPP", Character.valueOf('P'), "plankWood", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('B'), Ic2Items.chargedReBattery });
/* 1650 */     advRecipes.addRecipe(Ic2Items.mfeUnit, new Object[] { "cCc", "CMC", "cCc", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('c'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('C'), Ic2Items.energyCrystal });
/* 1651 */     advRecipes.addRecipe(Ic2Items.mfsUnit, new Object[] { "LCL", "LML", "LAL", Character.valueOf('M'), Ic2Items.mfeUnit, Character.valueOf('A'), Ic2Items.advancedMachine, Character.valueOf('C'), adv, Character.valueOf('L'), Ic2Items.lapotronCrystal });
/* 1652 */     advRecipes.addRecipe(Ic2Items.batteryBox, new Object[] { "XCX", "VBV", "XYX", Character.valueOf('Y'), Ic2Items.evTransformer, Character.valueOf('C'), Ic2Items.glassFiberCableItem, Character.valueOf('X'), Ic2Items.advancedMachine, Character.valueOf('B'), Blocks.field_150486_ae, Character.valueOf('V'), adv });
/* 1653 */     advRecipes.addRecipe(Ic2Items.lvTransformer, new Object[] { "PCP", "ccc", "PCP", Character.valueOf('P'), "plankWood", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('c'), "ingotCopper" });
/* 1654 */     advRecipes.addRecipe(Ic2Items.mvTransformer, new Object[] { " C ", " M ", " C ", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.doubleInsulatedGoldCableItem });
/* 1655 */     advRecipes.addRecipe(Ic2Items.hvTransformer, new Object[] { " c ", "CED", " c ", Character.valueOf('E'), Ic2Items.mvTransformer, Character.valueOf('c'), Ic2Items.trippleInsulatedIronCableItem, Character.valueOf('D'), Ic2Items.energyCrystal, Character.valueOf('C'), basic });
/* 1656 */     advRecipes.addRecipe(Ic2Items.evTransformer, new Object[] { " c ", "CED", " c ", Character.valueOf('E'), Ic2Items.hvTransformer, Character.valueOf('c'), ironName, Character.valueOf('D'), Ic2Items.lapotronCrystal, Character.valueOf('C'), adv });
/* 1657 */     advRecipes.addRecipe(Ic2Items.adjustableTransformer, new Object[] { "X", "Y", "C", Character.valueOf('X'), Ic2Items.lvTransformer, Character.valueOf('Y'), adv, Character.valueOf('C'), Ic2Items.hvTransformer });
/* 1658 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.reinforcedStone, 8), new Object[] { "SSS", "SAS", "SSS", Character.valueOf('S'), Blocks.field_150348_b, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1659 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.reinforcedGlass, 7), new Object[] { "GAG", "GGG", "GAG", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1660 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.reinforcedGlass, 7), new Object[] { "GGG", "AGA", "GGG", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1661 */     advRecipes.addRecipe(Ic2Items.remote, new Object[] { " c ", "GCG", "TTT", Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('C'), basic, Character.valueOf('T'), Blocks.field_150335_W });
/* 1662 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.rubberTrampoline, 3), new Object[] { "RRR", "RRR", Character.valueOf('R'), "itemRubber" });
/* 1663 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150478_aa, 4), new Object[] { "R", "I", Character.valueOf('I'), "stickWood", Character.valueOf('R'), Ic2Items.resin, Boolean.valueOf(true) });
/* 1664 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.scaffold, 16), new Object[] { "PPP", " s ", "s s", Character.valueOf('P'), "plankWood", Character.valueOf('s'), "stickWood" });
/* 1665 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.ironScaffold, 16), new Object[] { "PPP", " s ", "s s", Character.valueOf('P'), ironName, Character.valueOf('s'), Ic2Items.ironFence.func_77973_b() });
/* 1666 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.ironFence, 12), new Object[] { "III", "III", Character.valueOf('I'), ironName });
/* 1667 */     if (enableCraftingITnt) {
/*      */       
/* 1669 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.industrialTnt, 4), new Object[] { "FFF", "TTT", "FFF", Character.valueOf('F'), Items.field_151145_ak, Character.valueOf('T'), Blocks.field_150335_W });
/* 1670 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.industrialTnt, 4), new Object[] { "FTF", "FTF", "FTF", Character.valueOf('F'), Items.field_151145_ak, Character.valueOf('T'), Blocks.field_150335_W });
/*      */     } 
/* 1672 */     if (enableCraftingNuke)
/*      */     {
/* 1674 */       advRecipes.addRecipe(Ic2Items.nuke, new Object[] { "GUG", "UGU", "GUG", Character.valueOf('G'), Items.field_151016_H, Character.valueOf('U'), "ingotUranium", Boolean.valueOf(true) });
/*      */     }
/* 1676 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150348_b, 16), new Object[] { "   ", " M ", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1677 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150359_w, 32), new Object[] { " M ", "M M", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1678 */     advRecipes.addRecipe(new ItemStack((Block)Blocks.field_150349_c, 16), new Object[] { "   ", "M  ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1679 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150341_Y, 16), new Object[] { "   ", " M ", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1680 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150322_A, 16), new Object[] { "   ", "  M", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1681 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150433_aE, 4), new Object[] { "M M", "   ", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1682 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150355_j, 1), new Object[] { "   ", " M ", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1683 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150353_l, 1), new Object[] { " M ", " M ", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1684 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150366_p, 2), new Object[] { "M M", " M ", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1685 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150352_o, 2), new Object[] { " M ", "MMM", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1686 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150343_Z, 12), new Object[] { "M M", "M M", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1687 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150424_aL, 16), new Object[] { "  M", " M ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1688 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150426_aN, 8), new Object[] { " M ", "M M", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1689 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150364_r, 8), new Object[] { " M ", "   ", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1690 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150434_aF, 48), new Object[] { " M ", "MMM", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1691 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150395_bd, 24), new Object[] { "M  ", "M  ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1692 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150325_L, 12), new Object[] { "M M", "   ", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1693 */     advRecipes.addRecipe(new ItemStack(Items.field_151044_h, 20), new Object[] { "  M", "M  ", "  M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1694 */     advRecipes.addRecipe(new ItemStack(Items.field_151045_i, 1), new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1695 */     advRecipes.addRecipe(new ItemStack(Items.field_151137_ax, 24), new Object[] { "   ", " M ", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1696 */     advRecipes.addRecipe(new ItemStack(Items.field_151100_aR, 9, 4), new Object[] { " M ", " M ", " MM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1697 */     advRecipes.addRecipe(new ItemStack(Items.field_151008_G, 32), new Object[] { " M ", " M ", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1698 */     advRecipes.addRecipe(new ItemStack(Items.field_151126_ay, 16), new Object[] { "   ", "   ", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1699 */     advRecipes.addRecipe(new ItemStack(Items.field_151016_H, 15), new Object[] { "MMM", "M  ", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1700 */     advRecipes.addRecipe(new ItemStack(Items.field_151119_aD, 48), new Object[] { "MM ", "M  ", "MM ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1701 */     advRecipes.addRecipe(new ItemStack(Items.field_151100_aR, 32, 3), new Object[] { "MM ", "  M", "MM ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1702 */     advRecipes.addRecipe(new ItemStack(Items.field_151100_aR, 48, 0), new Object[] { " MM", " MM", " M ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1703 */     advRecipes.addRecipe(new ItemStack(Items.field_151120_aE, 48), new Object[] { "M M", "M M", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1704 */     advRecipes.addRecipe(new ItemStack(Items.field_151145_ak, 32), new Object[] { " M ", "MM ", "MM ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1705 */     advRecipes.addRecipe(new ItemStack(Items.field_151103_aS, 32), new Object[] { "M  ", "MM ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1706 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.resin, 21), new Object[] { "M M", "   ", "M M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1707 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.iridiumOre, 1), new Object[] { "MMM", " M ", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1708 */     advRecipes.addRecipe(new ItemStack((Block)Blocks.field_150391_bh, 24), new Object[] { "   ", "M M", "MMM", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1709 */     advRecipes.addRecipe(new ItemStack(Blocks.field_150417_aV, 48, 3), new Object[] { "MM ", "MM ", "M  ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/* 1710 */     if (Ic2Items.copperOre != null) {
/*      */       
/* 1712 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.copperOre, 5), new Object[] { "  M", "M M", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/*      */     }
/*      */     else {
/*      */       
/* 1716 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.copperDust, 10), new Object[] { "  M", "M M", "   ", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/*      */     } 
/* 1718 */     if (Ic2Items.tinOre != null) {
/*      */       
/* 1720 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinOre, 5), new Object[] { "   ", "M M", "  M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/*      */     }
/*      */     else {
/*      */       
/* 1724 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinDust, 10), new Object[] { "   ", "M M", "  M", Character.valueOf('M'), Ic2Items.matter, Boolean.valueOf(true) });
/*      */     } 
/* 1726 */     if (Ic2Items.rubberWood != null)
/*      */     {
/* 1728 */       advRecipes.addRecipe(new ItemStack(Blocks.field_150344_f, 3, 3), new Object[] { "W", Character.valueOf('W'), Ic2Items.rubberWood });
/*      */     }
/* 1730 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedCopperCableItem, 6), new Object[] { "RRR", "CCC", "RRR", Character.valueOf('C'), "ingotCopper", Character.valueOf('R'), "itemRubber" });
/* 1731 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedCopperCableItem, 6), new Object[] { "RCR", "RCR", "RCR", Character.valueOf('C'), "ingotCopper", Character.valueOf('R'), "itemRubber" });
/* 1732 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedBronzeCableItem, 6), new Object[] { "RRR", "CCC", "RRR", Character.valueOf('C'), "ingotBronze", Character.valueOf('R'), "itemRubber" });
/* 1733 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedBronzeCableItem, 6), new Object[] { "RCR", "RCR", "RCR", Character.valueOf('C'), "ingotBronze", Character.valueOf('R'), "itemRubber" });
/* 1734 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.doubleInsulatedBronzeCableItem, 2), new Object[] { " R ", "RCR", " R ", Character.valueOf('C'), "ingotBronze", Character.valueOf('R'), "itemRubber" });
/* 1735 */     advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.insulatedBronzeCableItem, 1), new Object[] { Ic2Items.bronzeCableItem, "itemRubber" });
/* 1736 */     advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.doubleInsulatedBronzeCableItem, 1), new Object[] { Ic2Items.insulatedBronzeCableItem, "itemRubber" });
/* 1737 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.copperCableItem, 6), new Object[] { "CCC", Character.valueOf('C'), "ingotCopper" });
/* 1738 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.bronzeCableItem, 6), new Object[] { "CCC", Character.valueOf('C'), "ingotBronze" });
/* 1739 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.goldCableItem, 12), new Object[] { "GGG", Character.valueOf('G'), Items.field_151043_k });
/* 1740 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedGoldCableItem, 4), new Object[] { " R ", "RGR", " R ", Character.valueOf('G'), Items.field_151043_k, Character.valueOf('R'), "itemRubber" });
/* 1741 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.glassFiberCableItem, 4), new Object[] { "GGG", "RDR", "GGG", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('D'), Items.field_151045_i });
/* 1742 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.glassFiberCableItem, 4), new Object[] { "GGG", "RDR", "GGG", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1743 */     advRecipes.addRecipe(Ic2Items.detectorCableItem, new Object[] { " C ", "RIR", " R ", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('I'), Ic2Items.trippleInsulatedIronCableItem, Character.valueOf('C'), basic });
/* 1744 */     advRecipes.addRecipe(Ic2Items.splitterCableItem, new Object[] { " R ", "ILI", " R ", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('I'), Ic2Items.trippleInsulatedIronCableItem, Character.valueOf('L'), Blocks.field_150442_at });
/* 1745 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.ironCableItem, 12), new Object[] { "III", Character.valueOf('I'), ironName });
/* 1746 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.insulatedIronCableItem, 4), new Object[] { " R ", "RIR", " R ", Character.valueOf('I'), ironName, Character.valueOf('R'), "itemRubber" });
/* 1747 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.glassFiberCableItem, 6), new Object[] { "GGG", "SDS", "GGG", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('S'), "ingotSilver", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('D'), Items.field_151045_i });
/* 1748 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.glassFiberCableItem, 6), new Object[] { "GGG", "SDS", "GGG", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('S'), "ingotSilver", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1749 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinCableItem, 9), new Object[] { "TTT", Character.valueOf('T'), "ingotTin" });
/* 1750 */     advRecipes.addShapelessRecipe(Ic2Items.insulatedCopperCableItem, new Object[] { "itemRubber", Ic2Items.copperCableItem });
/* 1751 */     advRecipes.addShapelessRecipe(Ic2Items.insulatedGoldCableItem, new Object[] { "itemRubber", Ic2Items.goldCableItem });
/* 1752 */     advRecipes.addShapelessRecipe(Ic2Items.doubleInsulatedGoldCableItem, new Object[] { "itemRubber", Ic2Items.insulatedGoldCableItem });
/* 1753 */     advRecipes.addShapelessRecipe(Ic2Items.doubleInsulatedGoldCableItem, new Object[] { "itemRubber", "itemRubber", Ic2Items.goldCableItem });
/* 1754 */     advRecipes.addShapelessRecipe(Ic2Items.insulatedIronCableItem, new Object[] { "itemRubber", Ic2Items.ironCableItem });
/* 1755 */     advRecipes.addShapelessRecipe(Ic2Items.doubleInsulatedIronCableItem, new Object[] { "itemRubber", Ic2Items.insulatedIronCableItem });
/* 1756 */     advRecipes.addShapelessRecipe(Ic2Items.trippleInsulatedIronCableItem, new Object[] { "itemRubber", Ic2Items.doubleInsulatedIronCableItem });
/* 1757 */     advRecipes.addShapelessRecipe(Ic2Items.trippleInsulatedIronCableItem, new Object[] { "itemRubber", "itemRubber", Ic2Items.insulatedIronCableItem });
/* 1758 */     advRecipes.addShapelessRecipe(Ic2Items.doubleInsulatedIronCableItem, new Object[] { "itemRubber", "itemRubber", Ic2Items.ironCableItem });
/* 1759 */     advRecipes.addShapelessRecipe(Ic2Items.trippleInsulatedIronCableItem, new Object[] { "itemRubber", "itemRubber", "itemRubber", Ic2Items.ironCableItem });
/* 1760 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.suBattery, 5), new Object[] { "C", "R", "D", Character.valueOf('D'), "dustCoal", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1761 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.suBattery, 5), new Object[] { "C", "D", "R", Character.valueOf('D'), "dustCoal", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1762 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.suBattery, 8), new Object[] { "c", "C", "R", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Ic2Items.hydratedCoalDust, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem });
/* 1763 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.suBattery, 8), new Object[] { "c", "R", "C", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Ic2Items.hydratedCoalDust, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem });
/* 1764 */     advRecipes.addRecipe(Ic2Items.reBattery, new Object[] { " C ", "TRT", "TRT", Character.valueOf('T'), "ingotTin", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1765 */     advRecipes.addRecipe(Ic2Items.energyCrystal, new Object[] { "RRR", "RDR", "RRR", Character.valueOf('D'), Items.field_151045_i, Character.valueOf('R'), Items.field_151137_ax });
/* 1766 */     advRecipes.addRecipe(Ic2Items.energyCrystal, new Object[] { "RRR", "RDR", "RRR", Character.valueOf('D'), Ic2Items.industrialDiamond, Character.valueOf('R'), Items.field_151137_ax });
/* 1767 */     advRecipes.addRecipe(Ic2Items.lapotronCrystal, new Object[] { "LCL", "LDL", "LCL", Character.valueOf('D'), Ic2Items.energyCrystal, Character.valueOf('C'), basic, Character.valueOf('L'), new ItemStack(Items.field_151100_aR, 1, 4) });
/* 1768 */     advRecipes.addRecipe(Ic2Items.treetap, new Object[] { " P ", "PPP", "P  ", Character.valueOf('P'), "plankWood" });
/* 1769 */     advRecipes.addRecipe(Ic2Items.painter, new Object[] { " CC", " IC", "I  ", Character.valueOf('C'), Blocks.field_150325_L, Character.valueOf('I'), Items.field_151042_j });
/* 1770 */     advRecipes.addRecipe(new ItemStack(Items.field_151046_w, 1), new Object[] { "DDD", " S ", " S ", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1771 */     advRecipes.addRecipe(new ItemStack(Items.field_151012_L, 1), new Object[] { "DD ", " S ", " S ", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1772 */     advRecipes.addRecipe(new ItemStack(Items.field_151047_v, 1), new Object[] { "D", "S", "S", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1773 */     advRecipes.addRecipe(new ItemStack(Items.field_151056_x, 1), new Object[] { "DD ", "DS ", " S ", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1774 */     advRecipes.addRecipe(new ItemStack(Items.field_151048_u, 1), new Object[] { "D", "D", "S", Character.valueOf('S'), "stickWood", Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1775 */     advRecipes.addRecipe(new ItemStack(Ic2Items.constructionFoamSprayer.func_77973_b(), 1, 1601), new Object[] { "SS ", "Ss ", "  S", Character.valueOf('S'), Blocks.field_150347_e, Character.valueOf('s'), "stickWood" });
/* 1776 */     new RecipeGradual((ItemGradual)Ic2Items.constructionFoamSprayer.func_77973_b(), Ic2Items.constructionFoamPellet, 100);
/* 1777 */     advRecipes.addRecipe(Ic2Items.wrench, new Object[] { "B B", "BBB", " B ", Character.valueOf('B'), "ingotBronze" });
/* 1778 */     advRecipes.addRecipe(Ic2Items.cutter, new Object[] { "A A", " A ", "I I", Character.valueOf('A'), ironName, Character.valueOf('I'), Items.field_151042_j });
/* 1779 */     advRecipes.addRecipe(Ic2Items.toolbox, new Object[] { "ICI", "III", Character.valueOf('C'), Blocks.field_150486_ae, Character.valueOf('I'), ironName });
/* 1780 */     advRecipes.addRecipe(Ic2Items.miningDrill, new Object[] { " I ", "ICI", "IBI", Character.valueOf('I'), ironName, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), basic });
/* 1781 */     advRecipes.addRecipe(Ic2Items.miningDrill, new Object[] { " I ", "ICI", "IBI", Character.valueOf('I'), ironName, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), basic });
/* 1782 */     advRecipes.addRecipe(Ic2Items.chainsaw, new Object[] { " II", "ICI", "BI ", Character.valueOf('I'), ironName, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), basic });
/* 1783 */     advRecipes.addRecipe(Ic2Items.chainsaw, new Object[] { " II", "ICI", "BI ", Character.valueOf('I'), ironName, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), basic });
/* 1784 */     advRecipes.addRecipe(Ic2Items.diamondDrill, new Object[] { " D ", "DdD", Character.valueOf('D'), Items.field_151045_i, Character.valueOf('d'), new ItemStack(Ic2Items.miningDrill.func_77973_b(), 1, 32767) });
/* 1785 */     advRecipes.addRecipe(Ic2Items.diamondDrill, new Object[] { " D ", "DdD", Character.valueOf('D'), Items.field_151045_i, Character.valueOf('d'), new ItemStack(Ic2Items.miningDrill.func_77973_b(), 1, 32767) });
/* 1786 */     advRecipes.addRecipe(Ic2Items.odScanner, new Object[] { " G ", "CBC", "ccc", Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('C'), basic });
/* 1787 */     advRecipes.addRecipe(Ic2Items.odScanner, new Object[] { " G ", "CBC", "ccc", Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('C'), basic });
/* 1788 */     advRecipes.addRecipe(Ic2Items.ovScanner, new Object[] { " G ", "GCG", "cSc", Character.valueOf('S'), Ic2Items.odScanner, Character.valueOf('c'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('C'), adv });
/* 1789 */     advRecipes.addRecipe(Ic2Items.ovScanner, new Object[] { " G ", "GCG", "cSc", Character.valueOf('S'), Ic2Items.reBattery, Character.valueOf('c'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('C'), adv });
/* 1790 */     advRecipes.addRecipe(Ic2Items.ovScanner, new Object[] { " G ", "GCG", "cSc", Character.valueOf('S'), Ic2Items.chargedReBattery, Character.valueOf('c'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('C'), adv });
/* 1791 */     advRecipes.addRecipe(Ic2Items.rangedODScanner, new Object[] { "VVV", "XYX", "CCC", Character.valueOf('X'), Ic2Items.odScanner, Character.valueOf('Y'), adv, Character.valueOf('C'), Ic2Items.insulatedGoldCableItem, Character.valueOf('V'), Items.field_151114_aO });
/* 1792 */     advRecipes.addRecipe(Ic2Items.rangedOVScanner, new Object[] { "VVV", "XYX", "CCC", Character.valueOf('X'), Ic2Items.ovScanner, Character.valueOf('Y'), adv, Character.valueOf('C'), Ic2Items.insulatedGoldCableItem, Character.valueOf('V'), Items.field_151114_aO });
/* 1793 */     advRecipes.addRecipe(Ic2Items.bigRangedODScanner, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('X'), Items.field_151114_aO, Character.valueOf('Y'), adv, Character.valueOf('C'), Ic2Items.odScanner, Character.valueOf('V'), Ic2Items.rangedODScanner });
/* 1794 */     advRecipes.addRecipe(Ic2Items.bigRangedOVScanner, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('X'), Items.field_151114_aO, Character.valueOf('Y'), adv, Character.valueOf('C'), Ic2Items.ovScanner, Character.valueOf('V'), Ic2Items.rangedOVScanner });
/* 1795 */     advRecipes.addRecipe(Ic2Items.lowOreValueScanner, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('X'), Items.field_151114_aO, Character.valueOf('Y'), basic, Character.valueOf('V'), Ic2Items.odScanner, Character.valueOf('C'), Ic2Items.ovScanner });
/* 1796 */     advRecipes.addRecipe(Ic2Items.medOreValueScanner, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('X'), Items.field_151114_aO, Character.valueOf('Y'), adv, Character.valueOf('V'), Ic2Items.odScanner, Character.valueOf('C'), Ic2Items.ovScanner });
/* 1797 */     advRecipes.addRecipe(Ic2Items.highOreValueScanner, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('Y'), Items.field_151114_aO, Character.valueOf('X'), basic, Character.valueOf('V'), Ic2Items.odScanner, Character.valueOf('C'), Ic2Items.ovScanner });
/* 1798 */     advRecipes.addRecipe(Ic2Items.setOreValueScanner, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('Y'), Items.field_151114_aO, Character.valueOf('X'), adv, Character.valueOf('V'), Ic2Items.odScanner, Character.valueOf('C'), Ic2Items.ovScanner });
/* 1799 */     advRecipes.addRecipe(Ic2Items.electricWrench, new Object[] { "  W", " C ", "B  ", Character.valueOf('W'), Ic2Items.wrench, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), basic });
/* 1800 */     advRecipes.addRecipe(Ic2Items.electricWrench, new Object[] { "  W", " C ", "B  ", Character.valueOf('W'), Ic2Items.wrench, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), basic });
/* 1801 */     advRecipes.addRecipe(Ic2Items.electricTreetap, new Object[] { "  W", " C ", "B  ", Character.valueOf('W'), Ic2Items.treetap, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), basic });
/* 1802 */     advRecipes.addRecipe(Ic2Items.electricTreetap, new Object[] { "  W", " C ", "B  ", Character.valueOf('W'), Ic2Items.treetap, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), basic });
/* 1803 */     advRecipes.addRecipe(Ic2Items.ecMeter, new Object[] { " G ", "cCc", "c c", Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('C'), basic });
/* 1804 */     advRecipes.addRecipe(Ic2Items.miningLaser, new Object[] { "Rcc", "AAC", " AA", Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('C'), adv, Character.valueOf('c'), Ic2Items.energyCrystal, Character.valueOf('R'), Items.field_151137_ax });
/* 1805 */     advRecipes.addRecipe(Ic2Items.nanoSaber, new Object[] { "GA ", "GA ", "CcC", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1806 */     advRecipes.addRecipe(Ic2Items.electricHoe, new Object[] { "II ", " C ", " B ", Character.valueOf('I'), ironName, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('C'), basic });
/* 1807 */     advRecipes.addRecipe(Ic2Items.electricHoe, new Object[] { "II ", " C ", " B ", Character.valueOf('I'), ironName, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('C'), basic });
/* 1808 */     advRecipes.addShapelessRecipe(Ic2Items.frequencyTransmitter, new Object[] { basic, Ic2Items.insulatedCopperCableItem });
/* 1809 */     advRecipes.addRecipe(Ic2Items.advancedCircuit, new Object[] { "RGR", "LCL", "RGR", Character.valueOf('L'), new ItemStack(Items.field_151100_aR, 1, 4), Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), basic });
/* 1810 */     advRecipes.addRecipe(Ic2Items.advancedCircuit, new Object[] { "RLR", "GCG", "RLR", Character.valueOf('L'), new ItemStack(Items.field_151100_aR, 1, 4), Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), basic });
/* 1811 */     advRecipes.addRecipe(Ic2Items.plantBall, new Object[] { "PPP", "P P", "PPP", Character.valueOf('P'), Items.field_151015_O });
/* 1812 */     advRecipes.addRecipe(Ic2Items.plantBall, new Object[] { "PPP", "P P", "PPP", Character.valueOf('P'), Items.field_151120_aE });
/* 1813 */     advRecipes.addRecipe(Ic2Items.plantBall, new Object[] { "PPP", "P P", "PPP", Character.valueOf('P'), Items.field_151014_N });
/* 1814 */     advRecipes.addRecipe(Ic2Items.plantBall, new Object[] { "PPP", "P P", "PPP", Character.valueOf('P'), "treeLeaves" });
/* 1815 */     if (Ic2Items.rubberLeaves != null)
/*      */     {
/* 1817 */       advRecipes.addRecipe(Ic2Items.plantBall, new Object[] { "PPP", "P P", "PPP", Character.valueOf('P'), Ic2Items.rubberLeaves });
/*      */     }
/* 1819 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.plantBall, 2), new Object[] { "PPP", "P P", "PPP", Character.valueOf('P'), "treeSapling" });
/* 1820 */     advRecipes.addRecipe(Ic2Items.plantBall, new Object[] { "PPP", "P P", "PPP", Character.valueOf('P'), Blocks.field_150329_H });
/* 1821 */     advRecipes.addRecipe(Ic2Items.carbonFiber, new Object[] { "CC", "CC", Character.valueOf('C'), "dustCoal" });
/* 1822 */     advRecipes.addRecipe(Ic2Items.iridiumPlate, new Object[] { "IAI", "ADA", "IAI", Character.valueOf('I'), Ic2Items.iridiumOre, Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('D'), Items.field_151045_i });
/* 1823 */     advRecipes.addRecipe(Ic2Items.iridiumPlate, new Object[] { "IAI", "ADA", "IAI", Character.valueOf('I'), Ic2Items.iridiumOre, Character.valueOf('A'), Ic2Items.advancedAlloy, Character.valueOf('D'), Ic2Items.industrialDiamond });
/* 1824 */     advRecipes.addRecipe(Ic2Items.coalBall, new Object[] { "CCC", "CFC", "CCC", Character.valueOf('C'), "dustCoal", Character.valueOf('F'), Items.field_151145_ak });
/* 1825 */     advRecipes.addRecipe(Ic2Items.coalChunk, new Object[] { "###", "#O#", "###", Character.valueOf('#'), Ic2Items.compressedCoalBall, Character.valueOf('O'), Blocks.field_150343_Z });
/* 1826 */     advRecipes.addRecipe(Ic2Items.coalChunk, new Object[] { "###", "#O#", "###", Character.valueOf('#'), Ic2Items.compressedCoalBall, Character.valueOf('O'), Blocks.field_150339_S, Boolean.valueOf(true) });
/* 1827 */     advRecipes.addRecipe(Ic2Items.coalChunk, new Object[] { "###", "#O#", "###", Character.valueOf('#'), Ic2Items.compressedCoalBall, Character.valueOf('O'), Blocks.field_150336_V, Boolean.valueOf(true) });
/* 1828 */     advRecipes.addRecipe(Ic2Items.smallIronDust, new Object[] { "CTC", "TCT", "CTC", Character.valueOf('C'), "dustCopper", Character.valueOf('T'), "dustTin", Boolean.valueOf(true) });
/* 1829 */     advRecipes.addRecipe(Ic2Items.smallIronDust, new Object[] { "TCT", "CTC", "TCT", Character.valueOf('C'), "dustCopper", Character.valueOf('T'), "dustTin", Boolean.valueOf(true) });
/* 1830 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.hydratedCoalDust, 8), new Object[] { "CCC", "CWC", "CCC", Character.valueOf('C'), "dustCoal", Character.valueOf('W'), "liquid$" + Block.func_149682_b(Blocks.field_150355_j) });
/* 1831 */     advRecipes.addRecipe(StackUtil.copyWithSize(refinedIron, 8), new Object[] { "M", Character.valueOf('M'), Ic2Items.machine });
/* 1832 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.copperIngot, 9), new Object[] { "B", Character.valueOf('B'), Ic2Items.copperBlock });
/* 1833 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinIngot, 9), new Object[] { "B", Character.valueOf('B'), Ic2Items.tinBlock });
/* 1834 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.bronzeIngot, 9), new Object[] { "B", Character.valueOf('B'), Ic2Items.bronzeBlock });
/* 1835 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.uraniumIngot, 9), new Object[] { "B", Character.valueOf('B'), Ic2Items.uraniumBlock });
/* 1836 */     advRecipes.addRecipe(Ic2Items.electronicCircuit.func_77946_l(), new Object[] { "CCC", "RIR", "CCC", Character.valueOf('I'), ironName, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1837 */     advRecipes.addRecipe(Ic2Items.electronicCircuit.func_77946_l(), new Object[] { "CRC", "CIC", "CRC", Character.valueOf('I'), ironName, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1838 */     advRecipes.addRecipe(Ic2Items.compositeArmor, new Object[] { "A A", "ALA", "AIA", Character.valueOf('L'), Items.field_151027_R, Character.valueOf('I'), Items.field_151030_Z, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1839 */     advRecipes.addRecipe(Ic2Items.compositeArmor, new Object[] { "A A", "AIA", "ALA", Character.valueOf('L'), Items.field_151027_R, Character.valueOf('I'), Items.field_151030_Z, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1840 */     advRecipes.addRecipe(Ic2Items.compositeBoots, new Object[] { "ALA", "AIA", Character.valueOf('L'), Items.field_151021_T, Character.valueOf('I'), Items.field_151167_ab, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1841 */     advRecipes.addRecipe(Ic2Items.compositeBoots, new Object[] { "AIA", "ALA", Character.valueOf('L'), Items.field_151021_T, Character.valueOf('I'), Items.field_151167_ab, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1842 */     advRecipes.addRecipe(Ic2Items.compositeHelmet, new Object[] { "AAA", "AIA", " L ", Character.valueOf('L'), Items.field_151024_Q, Character.valueOf('I'), Items.field_151028_Y, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1843 */     advRecipes.addRecipe(Ic2Items.compositeHelmet, new Object[] { "AAA", "ALA", " I ", Character.valueOf('L'), Items.field_151024_Q, Character.valueOf('I'), Items.field_151028_Y, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1844 */     advRecipes.addRecipe(Ic2Items.compositeLeggings, new Object[] { "AAA", "AIA", "ALA", Character.valueOf('L'), Items.field_151026_S, Character.valueOf('I'), Items.field_151165_aa, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1845 */     advRecipes.addRecipe(Ic2Items.compositeLeggings, new Object[] { "AAA", "ALA", "AIA", Character.valueOf('L'), Items.field_151026_S, Character.valueOf('I'), Items.field_151165_aa, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1846 */     advRecipes.addRecipe(Ic2Items.nanoHelmet, new Object[] { "CcC", "CGC", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal, Character.valueOf('G'), Blocks.field_150359_w });
/* 1847 */     advRecipes.addRecipe(Ic2Items.nanoBodyarmor, new Object[] { "C C", "CcC", "CCC", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal });
/* 1848 */     advRecipes.addRecipe(Ic2Items.nanoLeggings, new Object[] { "CcC", "C C", "C C", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal });
/* 1849 */     advRecipes.addRecipe(Ic2Items.nanoBoots, new Object[] { "C C", "CcC", Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('c'), Ic2Items.energyCrystal });
/* 1850 */     advRecipes.addRecipe(Ic2Items.quantumHelmet, new Object[] { " n ", "ILI", "CGC", Character.valueOf('n'), Ic2Items.nanoHelmet, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('G'), Ic2Items.reinforcedGlass, Character.valueOf('C'), adv });
/* 1851 */     advRecipes.addRecipe(Ic2Items.quantumBodyarmor, new Object[] { "AnA", "ILI", "IAI", Character.valueOf('n'), Ic2Items.nanoBodyarmor, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('A'), Ic2Items.advancedAlloy });
/* 1852 */     advRecipes.addRecipe(Ic2Items.quantumLeggings, new Object[] { "MLM", "InI", "G G", Character.valueOf('n'), Ic2Items.nanoLeggings, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('G'), Items.field_151114_aO, Character.valueOf('M'), Ic2Items.machine });
/* 1853 */     advRecipes.addRecipe(Ic2Items.quantumBoots, new Object[] { "InI", "RLR", Character.valueOf('n'), Ic2Items.nanoBoots, Character.valueOf('I'), Ic2Items.iridiumPlate, Character.valueOf('L'), Ic2Items.lapotronCrystal, Character.valueOf('R'), Ic2Items.hazmatBoots });
/* 1854 */     advRecipes.addRecipe(Ic2Items.hazmatHelmet, new Object[] { " O ", "RGR", "R#R", Character.valueOf('R'), "itemRubber", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('#'), Blocks.field_150411_aY, Character.valueOf('O'), new ItemStack(Items.field_151100_aR, 1, 14) });
/* 1855 */     advRecipes.addRecipe(Ic2Items.hazmatChestplate, new Object[] { "R R", "ROR", "ROR", Character.valueOf('R'), "itemRubber", Character.valueOf('O'), new ItemStack(Items.field_151100_aR, 1, 14) });
/* 1856 */     advRecipes.addRecipe(Ic2Items.hazmatLeggings, new Object[] { "ROR", "R R", "R R", Character.valueOf('R'), "itemRubber", Character.valueOf('O'), new ItemStack(Items.field_151100_aR, 1, 14) });
/* 1857 */     advRecipes.addRecipe(Ic2Items.hazmatBoots, new Object[] { "R R", "R R", "RWR", Character.valueOf('R'), "itemRubber", Character.valueOf('W'), Blocks.field_150325_L });
/* 1858 */     advRecipes.addRecipe(Ic2Items.batPack, new Object[] { "BCB", "BTB", "B B", Character.valueOf('T'), "ingotTin", Character.valueOf('C'), basic, Character.valueOf('B'), Ic2Items.chargedReBattery });
/* 1859 */     advRecipes.addRecipe(Ic2Items.batPack, new Object[] { "BCB", "BTB", "B B", Character.valueOf('T'), "ingotTin", Character.valueOf('C'), basic, Character.valueOf('B'), Ic2Items.reBattery });
/* 1860 */     advRecipes.addRecipe(Ic2Items.lapPack, new Object[] { "LAL", "LBL", "L L", Character.valueOf('L'), Blocks.field_150368_y, Character.valueOf('A'), adv, Character.valueOf('B'), Ic2Items.batPack });
/* 1861 */     advRecipes.addRecipe(Ic2Items.solarHelmet, new Object[] { "III", "ISI", "CCC", Character.valueOf('I'), Items.field_151042_j, Character.valueOf('S'), Ic2Items.solarPanel, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1862 */     advRecipes.addRecipe(Ic2Items.solarHelmet, new Object[] { " H ", " S ", "CCC", Character.valueOf('H'), Items.field_151028_Y, Character.valueOf('S'), Ic2Items.solarPanel, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1863 */     advRecipes.addRecipe(Ic2Items.staticBoots, new Object[] { "I I", "ISI", "CCC", Character.valueOf('I'), Items.field_151042_j, Character.valueOf('S'), Blocks.field_150325_L, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1864 */     advRecipes.addRecipe(Ic2Items.staticBoots, new Object[] { " H ", " S ", "CCC", Character.valueOf('H'), Items.field_151167_ab, Character.valueOf('S'), Blocks.field_150325_L, Character.valueOf('C'), Ic2Items.insulatedCopperCableItem });
/* 1865 */     if (!dissableBronzeStuff) {
/*      */       
/* 1867 */       advRecipes.addRecipe(Ic2Items.bronzeHelmet, new Object[] { "BBB", "B B", Character.valueOf('B'), "ingotBronze" });
/* 1868 */       advRecipes.addRecipe(Ic2Items.bronzeChestplate, new Object[] { "B B", "BBB", "BBB", Character.valueOf('B'), "ingotBronze" });
/* 1869 */       advRecipes.addRecipe(Ic2Items.bronzeLeggings, new Object[] { "BBB", "B B", "B B", Character.valueOf('B'), "ingotBronze" });
/* 1870 */       advRecipes.addRecipe(Ic2Items.bronzeBoots, new Object[] { "B B", "B B", Character.valueOf('B'), "ingotBronze" });
/* 1871 */       advRecipes.addRecipe(Ic2Items.bronzePickaxe, new Object[] { "BBB", " S ", " S ", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood" });
/* 1872 */       advRecipes.addRecipe(Ic2Items.bronzeAxe, new Object[] { "BB", "SB", "S ", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood" });
/* 1873 */       advRecipes.addRecipe(Ic2Items.bronzeHoe, new Object[] { "BB", "S ", "S ", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood" });
/* 1874 */       advRecipes.addRecipe(Ic2Items.bronzeSword, new Object[] { "B", "B", "S", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood" });
/* 1875 */       advRecipes.addRecipe(Ic2Items.bronzeShovel, new Object[] { " B ", " S ", " S ", Character.valueOf('B'), "ingotBronze", Character.valueOf('S'), "stickWood" });
/*      */     } 
/* 1877 */     advRecipes.addRecipe(new ItemStack(Ic2Items.jetpack.func_77973_b(), 1, 18001), new Object[] { "ICI", "IFI", "R R", Character.valueOf('I'), ironName, Character.valueOf('C'), basic, Character.valueOf('F'), Ic2Items.fuelCan, Character.valueOf('R'), Items.field_151137_ax });
/* 1878 */     advRecipes.addRecipe(Ic2Items.electricJetpack, new Object[] { "ICI", "IBI", "G G", Character.valueOf('I'), ironName, Character.valueOf('C'), adv, Character.valueOf('B'), Ic2Items.batBox, Character.valueOf('G'), Items.field_151114_aO });
/* 1879 */     advRecipes.addRecipe(Ic2Items.nuclearJetpack, new Object[] { "XCX", "DVD", "XAX", Character.valueOf('A'), Ic2Items.electricJetpack, Character.valueOf('V'), Ic2Items.nuclearReactor, Character.valueOf('D'), Ic2Items.reactorChamber, Character.valueOf('X'), adv, Character.valueOf('C'), Ic2Items.evTransformer });
/* 1880 */     advRecipes.addRecipe(Ic2Items.terraformerBlueprint, new Object[] { " C ", " A ", "R R", Character.valueOf('C'), basic, Character.valueOf('A'), adv, Character.valueOf('R'), Items.field_151137_ax });
/* 1881 */     advRecipes.addRecipe(Ic2Items.cultivationTerraformerBlueprint, new Object[] { " S ", "S#S", " S ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('S'), Items.field_151014_N });
/* 1882 */     advRecipes.addRecipe(Ic2Items.desertificationTerraformerBlueprint, new Object[] { " S ", "S#S", " S ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('S'), Blocks.field_150354_m });
/* 1883 */     advRecipes.addRecipe(Ic2Items.irrigationTerraformerBlueprint, new Object[] { " W ", "W#W", " W ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('W'), Items.field_151131_as });
/* 1884 */     advRecipes.addRecipe(Ic2Items.chillingTerraformerBlueprint, new Object[] { " S ", "S#S", " S ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('S'), Items.field_151126_ay });
/* 1885 */     advRecipes.addRecipe(Ic2Items.flatificatorTerraformerBlueprint, new Object[] { " D ", "D#D", " D ", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('D'), Blocks.field_150346_d });
/* 1886 */     advRecipes.addRecipe(Ic2Items.mushroomTerraformerBlueprint, new Object[] { "mMm", "M#M", "mMm", Character.valueOf('#'), Ic2Items.terraformerBlueprint, Character.valueOf('M'), Blocks.field_150338_P, Character.valueOf('m'), Blocks.field_150391_bh });
/* 1887 */     advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] { Ic2Items.cultivationTerraformerBlueprint });
/* 1888 */     advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] { Ic2Items.irrigationTerraformerBlueprint });
/* 1889 */     advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] { Ic2Items.chillingTerraformerBlueprint });
/* 1890 */     advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] { Ic2Items.desertificationTerraformerBlueprint });
/* 1891 */     advRecipes.addShapelessRecipe(Ic2Items.terraformerBlueprint, new Object[] { Ic2Items.flatificatorTerraformerBlueprint });
/* 1892 */     advRecipes.addRecipe(Ic2Items.overclockerUpgrade, new Object[] { "CCC", "WEW", Character.valueOf('C'), Ic2Items.reactorCoolantSimple, Character.valueOf('W'), Ic2Items.insulatedCopperCableItem, Character.valueOf('E'), basic });
/* 1893 */     advRecipes.addRecipe(Ic2Items.transformerUpgrade, new Object[] { "GGG", "WTW", "GEG", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('W'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('T'), Ic2Items.mvTransformer, Character.valueOf('E'), basic });
/* 1894 */     advRecipes.addRecipe(Ic2Items.energyStorageUpgrade, new Object[] { "www", "WBW", "wEw", Character.valueOf('w'), Blocks.field_150344_f, Character.valueOf('W'), Ic2Items.insulatedCopperCableItem, Character.valueOf('B'), Ic2Items.reBattery, Character.valueOf('E'), basic });
/* 1895 */     advRecipes.addRecipe(Ic2Items.energyStorageUpgrade, new Object[] { "www", "WBW", "wEw", Character.valueOf('w'), Blocks.field_150344_f, Character.valueOf('W'), Ic2Items.insulatedCopperCableItem, Character.valueOf('B'), Ic2Items.chargedReBattery, Character.valueOf('E'), basic });
/* 1896 */     advRecipes.addRecipe(Ic2Items.reinforcedDoor, new Object[] { "SS", "SS", "SS", Character.valueOf('S'), Ic2Items.reinforcedStone });
/* 1897 */     advRecipes.addRecipe(Ic2Items.scrapBox, new Object[] { "SSS", "SSS", "SSS", Character.valueOf('S'), Ic2Items.scrap });
/* 1898 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.stickyDynamite, 8), new Object[] { "DDD", "DRD", "DDD", Character.valueOf('D'), Ic2Items.dynamite, Character.valueOf('R'), Ic2Items.resin });
/* 1899 */     advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.dynamite, 8), new Object[] { Ic2Items.industrialTnt, Items.field_151007_F });
/* 1900 */     advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.bronzeDust, 4), new Object[] { "dustTin", "dustCopper", "dustCopper", "dustCopper" });
/* 1901 */     advRecipes.addShapelessRecipe(Ic2Items.ironDust, new Object[] { Ic2Items.smallIronDust, Ic2Items.smallIronDust });
/* 1902 */     advRecipes.addShapelessRecipe(Ic2Items.carbonMesh, new Object[] { Ic2Items.carbonFiber, Ic2Items.carbonFiber });
/* 1903 */     advRecipes.addShapelessRecipe(new ItemStack((Block)Blocks.field_150320_F, 1), new Object[] { Blocks.field_150331_J, Ic2Items.resin, Boolean.valueOf(true) });
/* 1904 */     advRecipes.addRecipe(new ItemStack((Block)Blocks.field_150331_J, 1), new Object[] { "TTT", "#X#", "#R#", Character.valueOf('#'), Blocks.field_150347_e, Character.valueOf('X'), "ingotBronze", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('T'), Blocks.field_150344_f, Boolean.valueOf(true) });
/* 1905 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.miningPipe, 8), new Object[] { "I I", "I I", "ITI", Character.valueOf('I'), ironName, Character.valueOf('T'), Ic2Items.treetap });
/* 1906 */     if (Ic2Items.rubberSapling != null)
/*      */     {
/* 1908 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.plantBall, 2), new Object[] { "PPP", "P P", "PPP", Character.valueOf('P'), Ic2Items.rubberSapling });
/*      */     }
/* 1910 */     if (enableCraftingGlowstoneDust)
/*      */     {
/* 1912 */       advRecipes.addRecipe(new ItemStack(Items.field_151114_aO, 1), new Object[] { "RGR", "GRG", "RGR", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('G'), "dustGold", Boolean.valueOf(true) });
/*      */     }
/* 1914 */     if (enableCraftingGunpowder)
/*      */     {
/* 1916 */       advRecipes.addRecipe(new ItemStack(Items.field_151016_H, 3), new Object[] { "RCR", "CRC", "RCR", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), "dustCoal", Boolean.valueOf(true) });
/*      */     }
/* 1918 */     if (enableCraftingBucket)
/*      */     {
/* 1920 */       advRecipes.addRecipe(new ItemStack(Items.field_151133_ar, 1), new Object[] { "T T", " T ", Character.valueOf('T'), "ingotTin", Boolean.valueOf(true) });
/*      */     }
/* 1922 */     if (enableCraftingCoin)
/*      */     {
/* 1924 */       advRecipes.addRecipe(((ItemStack)OreDictionary.getOres(ironName).get(0)).func_77946_l(), new Object[] { "III", "III", "III", Character.valueOf('I'), Ic2Items.coin });
/*      */     }
/* 1926 */     if (enableCraftingCoin)
/*      */     {
/* 1928 */       advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.coin, 16), new Object[] { "II", "II", Character.valueOf('I'), ironName });
/*      */     }
/* 1930 */     if (enableCraftingRail)
/*      */     {
/* 1932 */       advRecipes.addRecipe(new ItemStack(Blocks.field_150448_aq, 8), new Object[] { "B B", "BsB", "B B", Character.valueOf('B'), "ingotBronze", Character.valueOf('s'), "stickWood", Boolean.valueOf(true) });
/*      */     }
/* 1934 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.crop, 2), new Object[] { "S S", "S S", Character.valueOf('S'), "stickWood" });
/* 1935 */     advRecipes.addRecipe(new ItemStack(Ic2Items.cropnalyzer.func_77973_b()), new Object[] { "cc ", "RGR", "RCR", Character.valueOf('G'), Blocks.field_150359_w, Character.valueOf('c'), Ic2Items.insulatedCopperCableItem, Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('C'), basic });
/* 1936 */     advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.fertilizer, 2), new Object[] { Ic2Items.scrap, new ItemStack(Items.field_151100_aR, 1, 15) });
/* 1937 */     advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.fertilizer, 2), new Object[] { Ic2Items.scrap, Ic2Items.scrap, Ic2Items.fertilizer });
/* 1938 */     advRecipes.addRecipe(Ic2Items.weedEx, new Object[] { "R", "G", "C", Character.valueOf('R'), Items.field_151137_ax, Character.valueOf('G'), Ic2Items.grinPowder, Character.valueOf('C'), Ic2Items.cell });
/* 1939 */     advRecipes.addRecipe(Ic2Items.cropmatron, new Object[] { "cBc", "CMC", "CCC", Character.valueOf('M'), Ic2Items.machine, Character.valueOf('C'), Ic2Items.crop, Character.valueOf('c'), basic, Character.valueOf('B'), Blocks.field_150486_ae });
/* 1940 */     advRecipes.addRecipe(new ItemStack(Ic2Items.mugEmpty.func_77973_b()), new Object[] { "SS ", "SSS", "SS ", Character.valueOf('S'), Blocks.field_150348_b });
/* 1941 */     advRecipes.addShapelessRecipe(new ItemStack(Ic2Items.coffeePowder.func_77973_b()), new Object[] { Ic2Items.coffeeBeans });
/* 1942 */     advRecipes.addShapelessRecipe(new ItemStack(Ic2Items.mugCoffee.func_77973_b()), new Object[] { Ic2Items.mugEmpty, Ic2Items.coffeePowder, "liquid$" + Block.func_149682_b(Blocks.field_150355_j) });
/* 1943 */     advRecipes.addShapelessRecipe(new ItemStack(Ic2Items.mugCoffee.func_77973_b(), 1, 2), new Object[] { new ItemStack(Ic2Items.mugCoffee.func_77973_b(), 1, 1), Items.field_151102_aT, Items.field_151117_aB });
/* 1944 */     if (Ic2Items.rubberWood != null)
/*      */     {
/* 1946 */       advRecipes.addRecipe(new ItemStack(Ic2Items.barrel.func_77973_b()), new Object[] { "P", "W", "P", Character.valueOf('P'), Blocks.field_150344_f, Character.valueOf('W'), Ic2Items.rubberWood });
/*      */     }
/* 1948 */     advRecipes.addRecipe(new ItemStack(Ic2Items.mugEmpty.func_77973_b()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Ic2Items.mugBooze.func_77973_b(), 1, 32767) });
/* 1949 */     advRecipes.addRecipe(new ItemStack(Ic2Items.barrel.func_77973_b()), new Object[] { "#", Character.valueOf('#'), new ItemStack(Ic2Items.barrel.func_77973_b(), 1, 32767) });
/*      */ 
/*      */     
/* 1952 */     List<IRecipe> recipeList = CraftingManager.func_77594_a().func_77592_b();
/* 1953 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.blackPainter, new Object[] { Ic2Items.painter, "dyeBlack" }));
/* 1954 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.redPainter, new Object[] { Ic2Items.painter, "dyeRed" }));
/* 1955 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.greenPainter, new Object[] { Ic2Items.painter, "dyeGreen" }));
/* 1956 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.brownPainter, new Object[] { Ic2Items.painter, "dyeBrown" }));
/* 1957 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.bluePainter, new Object[] { Ic2Items.painter, "dyeBlue" }));
/* 1958 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.purplePainter, new Object[] { Ic2Items.painter, "dyePurple" }));
/* 1959 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.cyanPainter, new Object[] { Ic2Items.painter, "dyeCyan" }));
/* 1960 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.lightGreyPainter, new Object[] { Ic2Items.painter, new ItemStack(Items.field_151100_aR, 1, 7) }));
/* 1961 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.darkGreyPainter, new Object[] { Ic2Items.painter, new ItemStack(Items.field_151100_aR, 1, 8) }));
/* 1962 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.pinkPainter, new Object[] { Ic2Items.painter, "dyePink" }));
/* 1963 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.limePainter, new Object[] { Ic2Items.painter, "dyeLime" }));
/* 1964 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.yellowPainter, new Object[] { Ic2Items.painter, "dyeYellow" }));
/* 1965 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.cloudPainter, new Object[] { Ic2Items.painter, "dyeLightBlue" }));
/* 1966 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.magentaPainter, new Object[] { Ic2Items.painter, "dyeMagenta" }));
/* 1967 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.orangePainter, new Object[] { Ic2Items.painter, "dyeOrange" }));
/* 1968 */     recipeList.add(new ShapelessOreRecipe(Ic2Items.whitePainter, new Object[] { Ic2Items.painter, "dyeWhite" }));
/* 1969 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.blackPainter.func_77973_b(), 1, 32767) });
/* 1970 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.redPainter.func_77973_b(), 1, 32767) });
/* 1971 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.greenPainter.func_77973_b(), 1, 32767) });
/* 1972 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.brownPainter.func_77973_b(), 1, 32767) });
/* 1973 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.bluePainter.func_77973_b(), 1, 32767) });
/* 1974 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.purplePainter.func_77973_b(), 1, 32767) });
/* 1975 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.cyanPainter.func_77973_b(), 1, 32767) });
/* 1976 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.lightGreyPainter.func_77973_b(), 1, 32767) });
/* 1977 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.darkGreyPainter.func_77973_b(), 1, 32767) });
/* 1978 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.pinkPainter.func_77973_b(), 1, 32767) });
/* 1979 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.limePainter.func_77973_b(), 1, 32767) });
/* 1980 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.yellowPainter.func_77973_b(), 1, 32767) });
/* 1981 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.cloudPainter.func_77973_b(), 1, 32767) });
/* 1982 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.magentaPainter.func_77973_b(), 1, 32767) });
/* 1983 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.orangePainter.func_77973_b(), 1, 32767) });
/* 1984 */     advRecipes.addShapelessRecipe(Ic2Items.painter, new Object[] { new ItemStack(Ic2Items.whitePainter.func_77973_b(), 1, 32767) });
/* 1985 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.cell, 16), new Object[] { " T ", "T T", " T ", Character.valueOf('T'), "ingotTin" });
/* 1986 */     advRecipes.addRecipe(Ic2Items.fuelCan, new Object[] { " TT", "T T", "TTT", Character.valueOf('T'), "ingotTin" });
/* 1987 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.tinCan, 4), new Object[] { "T T", "TTT", Character.valueOf('T'), "ingotTin" });
/* 1988 */     advRecipes.addShapelessRecipe(Ic2Items.waterCell, new Object[] { Ic2Items.cell, Items.field_151131_as });
/* 1989 */     advRecipes.addShapelessRecipe(Ic2Items.lavaCell, new Object[] { Ic2Items.cell, Items.field_151129_at });
/* 1990 */     advRecipes.addShapelessRecipe(new ItemStack(Blocks.field_150343_Z, 1), new Object[] { Ic2Items.waterCell, Ic2Items.waterCell, Ic2Items.lavaCell, Ic2Items.lavaCell });
/* 1991 */     advRecipes.addShapelessRecipe(Ic2Items.hydratedCoalDust, new Object[] { "dustCoal", "liquid$" + Block.func_149682_b(Blocks.field_150355_j) });
/* 1992 */     advRecipes.addShapelessRecipe(Ic2Items.hydratedCoalCell, new Object[] { Ic2Items.cell, Ic2Items.hydratedCoalClump });
/* 1993 */     advRecipes.addShapelessRecipe(Ic2Items.bioCell, new Object[] { Ic2Items.cell, Ic2Items.compressedPlantBall });
/* 1994 */     advRecipes.addRecipe(new ItemStack(Ic2Items.cfPack.func_77973_b(), 1, 259), new Object[] { "SCS", "FTF", "F F", Character.valueOf('T'), "ingotTin", Character.valueOf('C'), basic, Character.valueOf('F'), Ic2Items.fuelCan, Character.valueOf('S'), new ItemStack(Ic2Items.constructionFoamSprayer.func_77973_b(), 1, 1601) });
/* 1995 */     advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.constructionFoam, 3), new Object[] { "dustClay", "liquid$" + Block.func_149682_b(Blocks.field_150355_j), Items.field_151137_ax, "dustCoal" });
/* 1996 */     advRecipes.addShapelessRecipe(new ItemStack(Items.field_151045_i), new Object[] { Ic2Items.industrialDiamond });
/* 1997 */     advRecipes.addRecipe(StackUtil.copyWithSize(Ic2Items.mixedMetalIngot, 2), new Object[] { "III", "BBB", "TTT", Character.valueOf('I'), ironName, Character.valueOf('B'), "ingotBronze", Character.valueOf('T'), "ingotTin" });
/* 1998 */     advRecipes.addRecipe(Ic2Items.remote, new Object[] { " C ", "TLT", " F ", Character.valueOf('C'), Ic2Items.insulatedCopperCableItem, Character.valueOf('F'), Ic2Items.frequencyTransmitter, Character.valueOf('L'), new ItemStack(Items.field_151100_aR, 1, 4), Character.valueOf('T'), "ingotTin" });
/* 1999 */     advRecipes.addRecipe(Ic2Items.nightvisionGoggles, new Object[] { "XYX", "CVC", "BNB", Character.valueOf('X'), Ic2Items.reactorHeatSwitchDiamond, Character.valueOf('Y'), Ic2Items.nanoHelmet, Character.valueOf('C'), Ic2Items.luminator, Character.valueOf('V'), Ic2Items.reinforcedGlass, Character.valueOf('B'), "itemRubber", Character.valueOf('N'), adv });
/* 2000 */     advRecipes.addRecipe(Ic2Items.scrapMetalBlade, new Object[] { "XXX", "XYX", "XXX", Character.valueOf('X'), Items.field_151145_ak, Character.valueOf('Y'), Ic2Items.scrapMetalChunk });
/* 2001 */     advRecipes.addRecipe(Ic2Items.rotaryMacerator, new Object[] { "XXX", "XYX", "XCX", Character.valueOf('X'), Ic2Items.scrapMetalBlade, Character.valueOf('Y'), Ic2Items.macerator, Character.valueOf('C'), Ic2Items.advancedMachine });
/* 2002 */     advRecipes.addRecipe(Ic2Items.rotaryMacerator, new Object[] { "XXX", "XYX", "XCX", Character.valueOf('X'), Ic2Items.obsidianBlade, Character.valueOf('Y'), Ic2Items.macerator, Character.valueOf('C'), Ic2Items.advancedMachine });
/* 2003 */     advRecipes.addRecipe(Ic2Items.singularityCompressor, new Object[] { "XXX", "XYX", "XCX", Character.valueOf('X'), Blocks.field_150343_Z, Character.valueOf('Y'), Ic2Items.compressor, Character.valueOf('C'), Ic2Items.advancedMachine });
/* 2004 */     advRecipes.addRecipe(Ic2Items.centrifugeExtractor, new Object[] { "XXX", "XYX", "XCX", Character.valueOf('X'), Ic2Items.electrolyzedWaterCell, Character.valueOf('Y'), Ic2Items.extractor, Character.valueOf('C'), Ic2Items.advancedMachine });
/* 2005 */     advRecipes.addRecipe(Ic2Items.compactingRecycler, new Object[] { "XXX", "XYX", "XCX", Character.valueOf('X'), Blocks.field_150331_J, Character.valueOf('Y'), Ic2Items.recycler, Character.valueOf('C'), Ic2Items.advancedMachine });
/* 2006 */     advRecipes.addRecipe(Ic2Items.vacuumCanner, new Object[] { "XPX", "XYX", "XCX", Character.valueOf('X'), Ic2Items.airCell, Character.valueOf('P'), Ic2Items.pump, Character.valueOf('Y'), Ic2Items.canner, Character.valueOf('C'), Ic2Items.advancedMachine });
/* 2007 */     advRecipes.addRecipe(Ic2Items.chargedElectrolyzer, new Object[] { "XXX", "XYX", "XCX", Character.valueOf('X'), Ic2Items.lvTransformer, Character.valueOf('Y'), Ic2Items.electrolyzer, Character.valueOf('C'), Ic2Items.advancedMachine });
/* 2008 */     advRecipes.addRecipe(Ic2Items.redstoneSUpgrade, new Object[] { "XAX", "YCY", "XVX", Character.valueOf('X'), "ingotBronze", Character.valueOf('Y'), "itemRubber", Character.valueOf('A'), Items.field_151107_aW, Character.valueOf('C'), Blocks.field_150429_aA, Character.valueOf('V'), Items.field_151132_bS });
/* 2009 */     advRecipes.addRecipe(Ic2Items.redstoneIUpgrade, new Object[] { "X X", " C ", "X X", Character.valueOf('X'), "ingotTin", Character.valueOf('C'), Blocks.field_150429_aA });
/* 2010 */     advRecipes.addRecipe(Ic2Items.importBasicUpgrade, new Object[] { "XCX", "YVY", "XBX", Character.valueOf('X'), "ingotCopper", Character.valueOf('Y'), basic, Character.valueOf('C'), Blocks.field_150320_F, Character.valueOf('V'), Ic2Items.miningPipe, Character.valueOf('B'), Blocks.field_150429_aA });
/* 2011 */     advRecipes.addRecipe(Ic2Items.exportBasicUpgrade, new Object[] { "XCX", "YVY", "XBX", Character.valueOf('X'), "ingotCopper", Character.valueOf('Y'), basic, Character.valueOf('C'), Blocks.field_150331_J, Character.valueOf('V'), Ic2Items.miningPipe, Character.valueOf('B'), Blocks.field_150429_aA });
/* 2012 */     advRecipes.addRecipe(Ic2Items.importUpgrade, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.importBasicUpgrade, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.reBattery });
/* 2013 */     advRecipes.addRecipe(Ic2Items.importUpgrade, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.importBasicUpgrade, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.chargedReBattery });
/* 2014 */     advRecipes.addRecipe(Ic2Items.exportUpgrade, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.exportBasicUpgrade, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.reBattery });
/* 2015 */     advRecipes.addRecipe(Ic2Items.exportUpgrade, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.exportBasicUpgrade, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.chargedReBattery });
/* 2016 */     advRecipes.addShapelessRecipe(Ic2Items.coldTea, new Object[] { "cropTea", Ic2Items.mugEmpty, "liquid$" + Block.func_149682_b(Blocks.field_150355_j) });
/* 2017 */     advRecipes.addShapelessRecipe(Ic2Items.tea, new Object[] { Ic2Items.blackTea, Items.field_151117_aB });
/* 2018 */     advRecipes.addShapelessRecipe(Ic2Items.iceTea, new Object[] { Ic2Items.blackTea, Items.field_151102_aT, Blocks.field_150432_aD });
/* 2019 */     advRecipes.addRecipe(Ic2Items.rawObsidianBlade, new Object[] { "XXX", "XYX", "XXX", Character.valueOf('X'), Ic2Items.obsidianDust, Character.valueOf('Y'), Items.field_151145_ak });
/* 2020 */     advRecipes.addRecipe(Ic2Items.mufflerUpgrade, new Object[] { "XXX", "XYX", "XXX", Character.valueOf('X'), Blocks.field_150325_L, Character.valueOf('Y'), Ic2Items.machine });
/* 2021 */     advRecipes.addRecipe(Ic2Items.muteUpgrade, new Object[] { "XYX", "YCY", "XYX", Character.valueOf('X'), Blocks.field_150325_L, Character.valueOf('C'), Items.field_151007_F, Character.valueOf('Y'), Ic2Items.mufflerUpgrade });
/* 2022 */     advRecipes.addRecipe(Ic2Items.electricEnchanter, new Object[] { " X ", "YCY", "VBV", Character.valueOf('X'), Blocks.field_150467_bQ, Character.valueOf('Y'), adv, Character.valueOf('C'), Blocks.field_150381_bn, Character.valueOf('V'), Blocks.field_150342_X, Character.valueOf('B'), Ic2Items.mfsUnit });
/* 2023 */     advRecipes.addRecipe(Ic2Items.advSolarHelmet, new Object[] { "XXX", "XYX", "CVC", Character.valueOf('Y'), Ic2Items.solarHelmet, Character.valueOf('X'), Ic2Items.solarPanel, Character.valueOf('C'), basic, Character.valueOf('V'), Ic2Items.insulatedGoldCableItem });
/* 2024 */     advRecipes.addRecipe(Ic2Items.plantBall, new Object[] { "XXX", "X X", "XXX", Character.valueOf('X'), Blocks.field_150395_bd });
/* 2025 */     advRecipes.addShapelessRecipe(StackUtil.copyWithSize(Ic2Items.specialFertilzer, 3), new Object[] { Ic2Items.fertilizer.func_77946_l(), "liquid$" + Block.func_149682_b(Blocks.field_150355_j), new ItemStack(Items.field_151100_aR, 1, 15), Ic2Items.grinPowder, Ic2Items.scrap, Ic2Items.scrap });
/* 2026 */     advRecipes.addRecipe(Ic2Items.combinedNuclearJetpack, new Object[] { "XCX", "DVD", "XAX", Character.valueOf('A'), Ic2Items.combinedElectricJetpack, Character.valueOf('V'), Ic2Items.nuclearReactor, Character.valueOf('D'), Ic2Items.reactorChamber, Character.valueOf('X'), adv, Character.valueOf('C'), Ic2Items.evTransformer });
/* 2027 */     advRecipes.addRecipe(Ic2Items.combinedElectricJetpack, new Object[] { " C ", "XYX", "VBV", Character.valueOf('C'), Ic2Items.transformerUpgrade, Character.valueOf('Y'), Ic2Items.batPack, Character.valueOf('X'), adv, Character.valueOf('V'), Ic2Items.electricJetpack, Character.valueOf('B'), Ic2Items.lapPack });
/* 2028 */     advRecipes.addRecipe(Ic2Items.combinedNuclearJetpack, new Object[] { " C ", "XYX", "VBV", Character.valueOf('Y'), Ic2Items.nuclearJetpack, Character.valueOf('C'), Ic2Items.batPack, Character.valueOf('X'), adv, Character.valueOf('V'), Ic2Items.electricJetpack, Character.valueOf('B'), Ic2Items.lapPack });
/* 2029 */     advRecipes.addRecipe(Ic2Items.terraFormerBiomeBlueprint, new Object[] { " X ", " X ", "Y Y", Character.valueOf('X'), adv, Character.valueOf('Y'), Items.field_151137_ax });
/* 2030 */     advRecipes.addShapelessRecipe(Ic2Items.cultivationBiomeBlueprint, new Object[] { Ic2Items.terraFormerBiomeBlueprint, Ic2Items.cultivationTerraformerBlueprint });
/* 2031 */     advRecipes.addShapelessRecipe(Ic2Items.desertificationBiomeBlueprint, new Object[] { Ic2Items.terraFormerBiomeBlueprint, Ic2Items.desertificationTerraformerBlueprint });
/* 2032 */     advRecipes.addRecipe(Ic2Items.forestificationBiomeBlueprint, new Object[] { "X X", " C ", "XVX", Character.valueOf('C'), Ic2Items.terraFormerBiomeBlueprint, Character.valueOf('V'), Ic2Items.cultivationBiomeBlueprint, Character.valueOf('X'), "treeSapling" });
/* 2033 */     advRecipes.addShapelessRecipe(Ic2Items.irrigationBiomeBlueprint, new Object[] { Ic2Items.terraFormerBiomeBlueprint, Ic2Items.irrigationTerraformerBlueprint });
/* 2034 */     advRecipes.addShapelessRecipe(Ic2Items.mushroomBiomeBlueprint, new Object[] { Ic2Items.terraFormerBiomeBlueprint, Ic2Items.mushroomTerraformerBlueprint });
/* 2035 */     advRecipes.addRecipe(Ic2Items.undergrowthBiomeBlueprint, new Object[] { "X X", " C ", "X X", Character.valueOf('C'), Ic2Items.forestificationBiomeBlueprint, Character.valueOf('X'), Blocks.field_150395_bd });
/* 2036 */     advRecipes.addShapelessRecipe(Ic2Items.chillingBiomeBlueprint, new Object[] { Ic2Items.terraFormerBiomeBlueprint, Ic2Items.chillingTerraformerBlueprint });
/* 2037 */     advRecipes.addRecipe(new ItemStack((Block)Blocks.field_150331_J), new Object[] { "XXX", "YCY", "YVY", Character.valueOf('Y'), "cobblestone", Character.valueOf('X'), "plankWood", Character.valueOf('V'), Items.field_151137_ax, Character.valueOf('C'), "ingotSilver" });
/* 2038 */     advRecipes.addRecipe(new ItemStack((Block)Blocks.field_150331_J), new Object[] { "XXX", "YCY", "YVY", Character.valueOf('Y'), "cobblestone", Character.valueOf('X'), "plankWood", Character.valueOf('V'), Items.field_151137_ax, Character.valueOf('C'), "ingotBronze" });
/* 2039 */     advRecipes.addRecipe(Ic2Items.obscurator, new Object[] { "XCX", "YVB", "NMN", Character.valueOf('N'), Blocks.field_150430_aB, Character.valueOf('M'), adv, Character.valueOf('Y'), Blocks.field_150320_F, Character.valueOf('V'), Ic2Items.energyCrystal, Character.valueOf('B'), Blocks.field_150331_J, Character.valueOf('C'), "blockGlass", Character.valueOf('X'), Items.field_151159_an });
/* 2040 */     advRecipes.addRecipe(Ic2Items.padUpgradeProximity, new Object[] { "XXX", "YCY", "VAV", Character.valueOf('X'), adv, Character.valueOf('Y'), Ic2Items.overclockerUpgrade, Character.valueOf('C'), Ic2Items.splitterCableItem, Character.valueOf('V'), Ic2Items.doubleInsulatedGoldCableItem });
/* 2041 */     advRecipes.addRecipe(Ic2Items.padUpgradeWideBand, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('A'), adv, Character.valueOf('V'), Ic2Items.splitterCableItem, Character.valueOf('Y'), Ic2Items.padUpgradeProximity, Character.valueOf('C'), Ic2Items.energyCrystal });
/* 2042 */     advRecipes.addRecipe(Ic2Items.padUpgradeArmorPriorty, new Object[] { "XYX", "CVC", Character.valueOf('Y'), Items.field_151079_bi, Character.valueOf('X'), Items.field_151114_aO, Character.valueOf('C'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('V'), Ic2Items.padUpgradeProximity });
/* 2043 */     advRecipes.addRecipe(Ic2Items.padUpgradeBasicFieldUpgrade, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('Y'), Ic2Items.detectorCableItem, Character.valueOf('X'), Ic2Items.reactorReflector, Character.valueOf('C'), Ic2Items.splitterCableItem, Character.valueOf('V'), Ic2Items.padUpgradeWideBand });
/* 2044 */     advRecipes.addRecipe(Ic2Items.padUpgradeFieldUpgrade, new Object[] { "XYX", "CVC", "XYX", Character.valueOf('X'), Ic2Items.padUpgradeBasicFieldUpgrade, Character.valueOf('V'), adv, Character.valueOf('Y'), Ic2Items.efficencyUpgrade, Character.valueOf('C'), Ic2Items.splitterCableItem });
/* 2045 */     advRecipes.addRecipe(Ic2Items.padUpgradeAdvFieldUpgrade, new Object[] { "XYX", "CVC", "BNB", Character.valueOf('C'), Ic2Items.padUpgradeFieldUpgrade, Character.valueOf('N'), adv, Character.valueOf('B'), Ic2Items.efficencyUpgrade, Character.valueOf('V'), Ic2Items.splitterCableItem, Character.valueOf('X'), Ic2Items.reactorReflectorThick, Character.valueOf('Y'), Ic2Items.overclockerUpgrade });
/* 2046 */     advRecipes.addRecipe(Ic2Items.padUpgradeDamage, new Object[] { "XXX", "YCY", Character.valueOf('X'), Ic2Items.nearDepletedUraniumCell, Character.valueOf('Y'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('C'), adv });
/* 2047 */     advRecipes.addRecipe(Ic2Items.padUpgradeDrain, new Object[] { "XXX", "YCY", Character.valueOf('X'), Items.field_151114_aO, Character.valueOf('Y'), Ic2Items.doubleInsulatedGoldCableItem, Character.valueOf('C'), adv });
/* 2048 */     advRecipes.addRecipe(Ic2Items.crystalUpgradeKid, new Object[] { "X", "Y", "C", Character.valueOf('X'), Ic2Items.electricWrench, Character.valueOf('Y'), basic, Character.valueOf('C'), Ic2Items.mfeUnit });
/* 2049 */     advRecipes.addRecipe(Ic2Items.mfsUpgradeKid, new Object[] { "XCX", "XYX", "XVX", Character.valueOf('Y'), Ic2Items.wrench, Character.valueOf('C'), adv, Character.valueOf('X'), Ic2Items.lapotronCrystal, Character.valueOf('V'), Ic2Items.advancedMachine });
/* 2050 */     advRecipes.addRecipe(Ic2Items.mfsUpgradeKid, new Object[] { "Y", "M", Character.valueOf('Y'), Ic2Items.wrench, Character.valueOf('M'), Ic2Items.mfsUnit });
/* 2051 */     advRecipes.addRecipe(Ic2Items.lapotronicUpgradeKid, new Object[] { "XYX", "CVC", Character.valueOf('V'), Ic2Items.mfsUpgradeKid, Character.valueOf('X'), adv, Character.valueOf('C'), Ic2Items.carbonPlate, Character.valueOf('Y'), Ic2Items.electricWrench });
/* 2052 */     advRecipes.addRecipe(Ic2Items.fissionUpgradeKid, new Object[] { "XYX", "VCV", Character.valueOf('X'), Ic2Items.iridiumPlate, Character.valueOf('C'), Ic2Items.nuclearReactor, Character.valueOf('V'), Ic2Items.chillingTerraformerBlueprint, Character.valueOf('Y'), Ic2Items.electricWrench });
/* 2053 */     advRecipes.addRecipe(Ic2Items.efficencyUpgrade, new Object[] { " X ", "CVC", Character.valueOf('X'), Ic2Items.glassFiberCableItem, Character.valueOf('C'), Ic2Items.doubleInsulatedBronzeCableItem, Character.valueOf('V'), adv });
/* 2054 */     advRecipes.addRecipe(Ic2Items.expCollectorUpgrade, new Object[] { "XYX", "CVC", "XBX", Character.valueOf('X'), Ic2Items.denseCopperPlate, Character.valueOf('Y'), Ic2Items.machine, Character.valueOf('C'), adv, Character.valueOf('V'), Blocks.field_150438_bZ, Character.valueOf('B'), Items.field_151069_bo });
/* 2055 */     advRecipes.addRecipe(Ic2Items.quantumBodyJetpackArmor, new Object[] { " X ", "CVC", " Y ", Character.valueOf('Y'), Ic2Items.lapotronCrystal, Character.valueOf('C'), adv, Character.valueOf('X'), Ic2Items.quantumBodyarmor, Character.valueOf('V'), Ic2Items.combinedElectricJetpack });
/* 2056 */     advRecipes.addRecipe(Ic2Items.energyMultiplierUpgrade, new Object[] { "XYX", "YCY", "XYX", Character.valueOf('Y'), Ic2Items.energyStorageUpgrade, Character.valueOf('X'), adv, Character.valueOf('C'), Ic2Items.reBattery });
/* 2057 */     advRecipes.addRecipe(Ic2Items.energyMultiplierUpgrade, new Object[] { "XYX", "YCY", "XYX", Character.valueOf('Y'), Ic2Items.energyStorageUpgrade, Character.valueOf('X'), adv, Character.valueOf('C'), Ic2Items.chargedReBattery });
/* 2058 */     advRecipes.addRecipe(Ic2Items.loudnessUpgrade, new Object[] { "X", "Y", Character.valueOf('X'), Ic2Items.mufflerUpgrade, Character.valueOf('Y'), Blocks.field_150429_aA });
/* 2059 */     advRecipes.addRecipe(Ic2Items.oreScanner, new Object[] { "VXV", "MYN", "BCB", Character.valueOf('M'), Ic2Items.luminator, Character.valueOf('N'), Ic2Items.batteryBox, Character.valueOf('V'), Ic2Items.reactorCoolantSimple, Character.valueOf('X'), adv, Character.valueOf('Y'), Ic2Items.advancedMachine, Character.valueOf('C'), Ic2Items.miningPipe });
/* 2060 */     advRecipes.addRecipe(Ic2Items.cropScanner, new Object[] { " C ", "VBV", Character.valueOf('C'), Ic2Items.cropnalyzer, Character.valueOf('V'), basic, Character.valueOf('B'), Ic2Items.machine });
/* 2061 */     advRecipes.addRecipe(Ic2Items.reactorPlanner, new Object[] { "XCX", "VBV", "XNX", Character.valueOf('X'), basic, Character.valueOf('C'), Blocks.field_150486_ae, Character.valueOf('B'), Ic2Items.nuclearReactor, Character.valueOf('V'), Ic2Items.reactorChamber, Character.valueOf('N'), Ic2Items.machine });
/* 2062 */     advRecipes.addRecipe(Ic2Items.upgradeContainer, new Object[] { "XYX", "YCY", "XYX", Character.valueOf('X'), Ic2Items.carbonPlate, Character.valueOf('Y'), Ic2Items.iridiumPlate, Character.valueOf('C'), Blocks.field_150447_bR });
/* 2063 */     advRecipes.addRecipe(Ic2Items.reactorElectricVent, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.reactorVent, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.reBattery });
/* 2064 */     advRecipes.addRecipe(Ic2Items.reactorElectricVentCore, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.reactorVentCore, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.reBattery });
/* 2065 */     advRecipes.addRecipe(Ic2Items.reactorElectricVentGold, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.reactorVentGold, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.reBattery });
/* 2066 */     advRecipes.addRecipe(Ic2Items.reactorElectricVentDiamond, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.reactorVentDiamond, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.reBattery });
/* 2067 */     advRecipes.addRecipe(Ic2Items.reactorElectricVent, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.reactorVent, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.chargedReBattery });
/* 2068 */     advRecipes.addRecipe(Ic2Items.reactorElectricVentCore, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.reactorVentCore, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.chargedReBattery });
/* 2069 */     advRecipes.addRecipe(Ic2Items.reactorElectricVentGold, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.reactorVentGold, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.chargedReBattery });
/* 2070 */     advRecipes.addRecipe(Ic2Items.reactorElectricVentDiamond, new Object[] { "BCB", " A ", Character.valueOf('C'), Ic2Items.reactorVentDiamond, Character.valueOf('B'), adv, Character.valueOf('A'), Ic2Items.chargedReBattery });
/*      */ 
/*      */     
/* 2073 */     if (enableEnrichedUran) {
/*      */       
/* 2075 */       advRecipes.addRecipe(Ic2Items.uraniumEnricher, new Object[] { "XYC", "VBV", Character.valueOf('X'), Ic2Items.extractor, Character.valueOf('Y'), Ic2Items.canner, Character.valueOf('C'), Ic2Items.compressor, Character.valueOf('V'), Ic2Items.reBattery, Character.valueOf('B'), Ic2Items.advancedMachine });
/* 2076 */       advRecipes.addRecipe(Ic2Items.uraniumEnricher, new Object[] { "XYC", "VBV", Character.valueOf('X'), Ic2Items.extractor, Character.valueOf('Y'), Ic2Items.canner, Character.valueOf('C'), Ic2Items.compressor, Character.valueOf('V'), Ic2Items.chargedReBattery, Character.valueOf('B'), Ic2Items.advancedMachine });
/* 2077 */       for (ItemReactorEnrichUranium.UraniumType type : ItemReactorEnrichUranium.UraniumType.values()) {
/*      */         
/* 2079 */         advRecipes.addShapelessRecipe(type.getSimpleRod(), new Object[] { type.getReEnriched(), "dustCoal" });
/* 2080 */         advRecipes.addRecipe(type.getDualRod(), new Object[] { "UCU", Character.valueOf('U'), type.getSimpleRod(), Character.valueOf('C'), Ic2Items.denseCopperPlate });
/* 2081 */         advRecipes.addRecipe(type.getQuadRod(), new Object[] { " U ", "CCC", " U ", Character.valueOf('U'), type.getDualRod(), Character.valueOf('C'), Ic2Items.denseCopperPlate });
/* 2082 */         advRecipes.addRecipe(StackUtil.copyWithSize(type.getNearDepleted(), 8), new Object[] { "CCC", "CUC", "CCC", Character.valueOf('C'), Ic2Items.cell, Character.valueOf('U'), type.getIngot() });
/* 2083 */         advRecipes.addShapelessRecipe(type.getNewIsotopic(), new Object[] { type.getNearDepleted(), "dustCoal" });
/*      */         
/* 2085 */         if (!enableHarderEnrichedUran)
/*      */         {
/* 2087 */           advRecipes.addShapelessRecipe(type.getSimpleRod(), new Object[] { Ic2Items.cell, type.getIngot() });
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2093 */     ItemStack eJetpack = StackUtil.copyWithWildCard(Ic2Items.electricJetpack);
/* 2094 */     ItemStack eCJetpack = StackUtil.copyWithWildCard(Ic2Items.combinedElectricJetpack);
/*      */     
/* 2096 */     ItemStack nuclearJetpack = StackUtil.copyWithWildCard(Ic2Items.nuclearJetpack);
/* 2097 */     ItemStack cNuclearJetpack = StackUtil.copyWithWildCard(Ic2Items.combinedNuclearJetpack);
/* 2098 */     ItemArmorJetpack jet = (ItemArmorJetpack)eJetpack.func_77973_b();
/* 2099 */     jet.setJetpackOverrideRequest(eJetpack, true);
/* 2100 */     jet.setJetpackOverrideRequest(eCJetpack, true);
/* 2101 */     jet.setJetpackOverrideRequest(nuclearJetpack, true);
/* 2102 */     jet.setJetpackOverrideRequest(cNuclearJetpack, true);
/* 2103 */     ItemStack[] armors = { new ItemStack((Item)Items.field_151030_Z, 1, 32767), new ItemStack((Item)Items.field_151171_ah), Ic2Items.compositeArmor.func_77946_l() };
/* 2104 */     ItemStack[] jetpacks = { eJetpack, eCJetpack, nuclearJetpack, cNuclearJetpack };
/* 2105 */     for (int x = 0; x < jetpacks.length; x++) {
/*      */       
/* 2107 */       ItemStack jetp = jetpacks[x].func_77946_l();
/* 2108 */       jetp.func_77964_b(0);
/* 2109 */       for (int i = 0; i < armors.length; i++) {
/*      */         
/* 2111 */         advRecipes.addShapelessRecipe(jetp, new Object[] { jetpacks[x], armors[i] });
/*      */       } 
/* 2113 */       advRecipes.addShapelessRecipe(jetp, new Object[] { jetpacks[x] });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onChunkLoadEvent(ChunkWatchEvent.Watch evt) {
/* 2127 */     Chunk chunk = evt.player.field_70170_p.func_72964_e(evt.chunk.field_77276_a, evt.chunk.field_77275_b);
/* 2128 */     if (chunk != null)
/*      */     {
/* 2130 */       for (TileEntity tile : chunk.field_150816_i.values())
/*      */       {
/* 2132 */         ((NetworkManager)network.get()).sendInitialData(evt.player, tile);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onServerTick(TickEvent.ServerTickEvent evt) {
/* 2140 */     if (evt.phase == TickEvent.Phase.START) {
/*      */       return;
/*      */     }
/*      */     
/* 2144 */     ((NetworkManager)network.get()).clearGuiFields();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
/* 2150 */     if (enableWorldGenTreeRubber) {
/*      */       
/* 2152 */       BiomeGenBase biomegenbase = world.func_72959_q().func_76935_a(chunkX * 16 + 16, chunkZ * 16 + 16);
/* 2153 */       int rubbertrees = 0;
/* 2154 */       if (BiomeDictionary.isBiomeOfType(biomegenbase, BiomeDictionary.Type.CONIFEROUS))
/*      */       {
/* 2156 */         rubbertrees += random.nextInt(3);
/*      */       }
/* 2158 */       if (BiomeDictionary.isBiomeOfType(biomegenbase, BiomeDictionary.Type.FOREST) || BiomeDictionary.isBiomeOfType(biomegenbase, BiomeDictionary.Type.JUNGLE))
/*      */       {
/* 2160 */         rubbertrees += random.nextInt(5) + 1;
/*      */       }
/* 2162 */       if (BiomeDictionary.isBiomeOfType(biomegenbase, BiomeDictionary.Type.SWAMP))
/*      */       {
/* 2164 */         rubbertrees += random.nextInt(10) + 5;
/*      */       }
/* 2166 */       if (random.nextInt(100) + 1 <= rubbertrees * 2)
/*      */       {
/* 2168 */         (new WorldGenRubTree()).func_76484_a(world, random, chunkX * 16 + random.nextInt(16), rubbertrees, chunkZ * 16 + random.nextInt(16));
/*      */       }
/*      */     } 
/* 2171 */     int baseScale = Math.round((getSeaLevel(world) + 1) * oreDensityFactor);
/* 2172 */     if (enableWorldGenOreCopper && Ic2Items.copperOre != null) {
/*      */       
/* 2174 */       int baseCount = 15 * baseScale / 64;
/* 2175 */       for (int count = (int)Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount), n = 0; n < count; n++) {
/*      */         
/* 2177 */         int x = chunkX * 16 + random.nextInt(16);
/* 2178 */         int y = random.nextInt(40 * baseScale / 64) + random.nextInt(20 * baseScale / 64) + 10 * baseScale / 64;
/* 2179 */         int z = chunkZ * 16 + random.nextInt(16);
/* 2180 */         (new WorldGenMinable(Block.func_149634_a(Ic2Items.copperOre.func_77973_b()), 10)).func_76484_a(world, random, x, y, z);
/*      */       } 
/*      */     } 
/* 2183 */     if (enableWorldGenOreTin && Ic2Items.tinOre != null) {
/*      */       
/* 2185 */       int baseCount = 25 * baseScale / 64;
/* 2186 */       for (int count = (int)Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount), n = 0; n < count; n++) {
/*      */         
/* 2188 */         int x = chunkX * 16 + random.nextInt(16);
/* 2189 */         int y = random.nextInt(40 * baseScale / 64);
/* 2190 */         int z = chunkZ * 16 + random.nextInt(16);
/* 2191 */         (new WorldGenMinable(Block.func_149634_a(Ic2Items.tinOre.func_77973_b()), 6)).func_76484_a(world, random, x, y, z);
/*      */       } 
/*      */     } 
/* 2194 */     if (enableWorldGenOreUranium && Ic2Items.uraniumOre != null) {
/*      */       
/* 2196 */       int baseCount = 20 * baseScale / 64;
/* 2197 */       for (int count = (int)Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount), n = 0; n < count; n++) {
/*      */         
/* 2199 */         int x = chunkX * 16 + random.nextInt(16);
/* 2200 */         int y = random.nextInt(64 * baseScale / 64);
/* 2201 */         int z = chunkZ * 16 + random.nextInt(16);
/* 2202 */         (new WorldGenMinable(Block.func_149634_a(Ic2Items.uraniumOre.func_77973_b()), 3)).func_76484_a(world, random, x, y, z);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onWorldTick(TickEvent.WorldTickEvent evt) {
/* 2210 */     if (evt.type == TickEvent.Type.WORLD) {
/*      */       
/* 2212 */       if (evt.phase == TickEvent.Phase.START)
/*      */       {
/* 2214 */         onTickStart(evt.world);
/*      */       }
/* 2216 */       if (evt.phase == TickEvent.Phase.END)
/*      */       {
/* 2218 */         onTickEnd(evt.world);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onPlayerTick(TickEvent.PlayerTickEvent evt) {
/* 2226 */     if (evt.phase == TickEvent.Phase.END) {
/*      */       
/* 2228 */       EntityPlayer player = evt.player;
/* 2229 */       if (((Entity)player).field_70128_L) {
/*      */         return;
/*      */       }
/*      */       
/* 2233 */       boolean needsInventoryUpdate = false;
/* 2234 */       for (int i = 0; i < 4; i++) {
/*      */         
/* 2236 */         if (player.field_71071_by.field_70460_b[i] != null && player.field_71071_by.field_70460_b[i].func_77973_b() instanceof IItemTickListener && ((IItemTickListener)player.field_71071_by.field_70460_b[i].func_77973_b()).onTick(player, player.field_71071_by.field_70460_b[i]))
/*      */         {
/* 2238 */           needsInventoryUpdate = true;
/*      */         }
/*      */       } 
/* 2241 */       if (needsInventoryUpdate)
/*      */       {
/* 2243 */         player.field_71070_bA.func_75142_b();
/*      */       }
/* 2245 */       if (evt.side == Side.SERVER) {
/*      */         
/* 2247 */         TileEntity tile = (TileEntity)NetworkManager.watchingTile.get(player);
/* 2248 */         if (tile != null) {
/*      */           
/* 2250 */           ((NetworkManager)network.get()).updateGuiChanges(evt.player, tile);
/* 2251 */           if (player.field_71070_bA == player.field_71069_bz)
/*      */           {
/* 2253 */             NetworkManager.watchingTile.remove(player);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTickStart(World world) {
/* 2262 */     if (!singleTickCallbacks.containsKey(world)) {
/*      */       
/* 2264 */       singleTickCallbacks.put(world, new ArrayDeque<>());
/* 2265 */       continuousTickCallbacks.put(world, new HashSet<>());
/* 2266 */       continuousTickCallbacksInUse.put(world, Boolean.valueOf(false));
/* 2267 */       continuousTickCallbacksToRemove.put(world, new Vector<>());
/*      */     } 
/*      */     
/* 2270 */     if (enableCustomWorldSimulator) {
/*      */       
/* 2272 */       WindTicker ticker = (WindTicker)IC2Classic.windNetwork;
/* 2273 */       if (ticker != null)
/*      */       {
/* 2275 */         ticker.onTick(world);
/*      */ 
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 2281 */     else if (windTicker % 128 == 0) {
/*      */       
/* 2283 */       updateWind(world);
/*      */     } 
/*      */ 
/*      */     
/* 2287 */     windTicker++;
/* 2288 */     this; TextureIndex textureIndex = IC2.textureIndex;
/* 2289 */     textureIndex.t++;
/* 2290 */     EnergyNetGlobal.onTickStart(world);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTickEnd(World world) {
/* 2295 */     EnergyNetGlobal.onTickEnd(world);
/* 2296 */     if (singleTickCallbacks.containsKey(world)) {
/*      */       
/* 2298 */       Queue<ITickCallback> worldSingleTickCallbacks = singleTickCallbacks.get(world);
/*      */       ITickCallback singleTickCallback;
/* 2300 */       while ((singleTickCallback = worldSingleTickCallbacks.poll()) != null)
/*      */       {
/* 2302 */         singleTickCallback.tickCallback(world);
/*      */       }
/*      */     } 
/* 2305 */     if (singleTickCallbacks.containsKey(null)) {
/*      */       
/* 2307 */       Queue<ITickCallback> worldSingleTickCallbacks = singleTickCallbacks.get(null);
/*      */       ITickCallback singleTickCallback;
/* 2309 */       while ((singleTickCallback = worldSingleTickCallbacks.poll()) != null)
/*      */       {
/* 2311 */         singleTickCallback.tickCallback(world);
/*      */       }
/*      */     } 
/* 2314 */     if (continuousTickCallbacks.containsKey(world)) {
/*      */       
/* 2316 */       HashSet<ITickCallback> worldContinuousTickCallbacks = continuousTickCallbacks.get(world);
/* 2317 */       continuousTickCallbacksInUse.put(world, Boolean.valueOf(true));
/* 2318 */       for (ITickCallback tickCallback : worldContinuousTickCallbacks)
/*      */       {
/* 2320 */         tickCallback.tickCallback(world);
/*      */       }
/* 2322 */       continuousTickCallbacksInUse.put(world, Boolean.valueOf(false));
/* 2323 */       if (continuousTickCallbacksToAdd.containsKey(world)) {
/*      */         
/* 2325 */         worldContinuousTickCallbacks.addAll(continuousTickCallbacksToAdd.get(world));
/* 2326 */         ((List)continuousTickCallbacksToAdd.get(world)).clear();
/*      */       } 
/* 2328 */       if (continuousTickCallbacksToRemove.containsKey(world) && continuousTickCallbacksToAdd.get(world) != null) {
/*      */         
/* 2330 */         worldContinuousTickCallbacks.removeAll(continuousTickCallbacksToAdd.get(world));
/* 2331 */         ((List)continuousTickCallbacksToAdd.get(world)).clear();
/*      */       } 
/*      */     } 
/* 2334 */     ((NetworkManager)network.get()).onTick(world);
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
/* 2340 */     ((NetworkManager)network.get()).sendLoginData();
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
/* 2346 */     NetworkManager.watchingTile.remove(event.player);
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void onWorldLoad(WorldEvent.Load event) {
/* 2352 */     textureIndex.reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void addSingleTickCallback(World world, ITickCallback tickCallback) {
/* 2357 */     if (!singleTickCallbacks.containsKey(world))
/*      */     {
/* 2359 */       singleTickCallbacks.put(world, new ArrayDeque<>());
/*      */     }
/* 2361 */     ((ArrayDeque<ITickCallback>)singleTickCallbacks.get(world)).add(tickCallback);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addContinuousTickCallback(World world, ITickCallback tickCallback) {
/* 2367 */     if (world == null) {
/*      */       
/* 2369 */       log.warn("addContinuousTickCallback with world = null, callback = " + tickCallback);
/* 2370 */       Thread.dumpStack();
/*      */       return;
/*      */     } 
/* 2373 */     if (!continuousTickCallbacksInUse.containsKey(world) || continuousTickCallbacksInUse.get(world) == null) {
/*      */       
/* 2375 */       if (!continuousTickCallbacks.containsKey(world))
/*      */       {
/* 2377 */         continuousTickCallbacks.put(world, new HashSet<>());
/*      */       }
/* 2379 */       ((HashSet<ITickCallback>)continuousTickCallbacks.get(world)).add(tickCallback);
/*      */     }
/*      */     else {
/*      */       
/* 2383 */       if (continuousTickCallbacksToRemove.containsKey(world))
/*      */       {
/* 2385 */         ((List)continuousTickCallbacksToRemove.get(world)).remove(tickCallback);
/*      */       }
/* 2387 */       if (!continuousTickCallbacksToAdd.containsKey(world))
/*      */       {
/* 2389 */         continuousTickCallbacksToAdd.put(world, new Vector<>());
/*      */       }
/* 2391 */       ((List<ITickCallback>)continuousTickCallbacksToAdd.get(world)).add(tickCallback);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void removeContinuousTickCallback(World world, ITickCallback tickCallback) {
/* 2397 */     if (!continuousTickCallbacksInUse.containsKey(world) || continuousTickCallbacksInUse.get(world) == null) {
/*      */       
/* 2399 */       if (continuousTickCallbacks.containsKey(world))
/*      */       {
/* 2401 */         ((HashSet)continuousTickCallbacks.get(world)).remove(tickCallback);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2406 */       if (continuousTickCallbacksToAdd.containsKey(world))
/*      */       {
/* 2408 */         ((List)continuousTickCallbacksToAdd.get(world)).remove(tickCallback);
/*      */       }
/* 2410 */       if (!continuousTickCallbacksToRemove.containsKey(world))
/*      */       {
/* 2412 */         continuousTickCallbacksToRemove.put(world, new Vector<>());
/*      */       }
/* 2414 */       ((List<ITickCallback>)continuousTickCallbacksToRemove.get(world)).add(tickCallback);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateWind(World world) {
/* 2420 */     if (world.field_73011_w.field_76574_g != 0) {
/*      */       return;
/*      */     }
/*      */     
/* 2424 */     int upChance = 10;
/* 2425 */     int downChance = 10;
/* 2426 */     if (windStrength > 20)
/*      */     {
/* 2428 */       upChance -= windStrength - 20;
/*      */     }
/* 2430 */     if (windStrength < 10)
/*      */     {
/* 2432 */       downChance -= 10 - windStrength;
/*      */     }
/* 2434 */     if (random.nextInt(100) <= upChance) {
/*      */       
/* 2436 */       windStrength++;
/*      */       return;
/*      */     } 
/* 2439 */     if (random.nextInt(100) <= downChance)
/*      */     {
/* 2441 */       windStrength--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void explodeMachineAt(World world, int x, int y, int z, boolean doDrop) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void explodeMachineAt(World world, int x, int y, int z) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSeaLevel(World world) {
/* 2462 */     return world.field_73011_w.func_76557_i();
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getWorldHeight(World world) {
/* 2467 */     return world.func_72800_K();
/*      */   }
/*      */ 
/*      */   
/*      */   public Block createBlock(Block block, Class<? extends ItemBlock> clz, String name) {
/* 2472 */     if (blockedBlocks.containsKey(name)) {
/*      */       
/* 2474 */       BlockObject object = blockedBlocks.remove(name);
/* 2475 */       return createBlock(object.block, object.item, name);
/*      */     } 
/*      */     
/* 2478 */     this; if (enableCustomIdAssigning) {
/*      */       
/* 2480 */       int id = blockIDs.getAndUpdateCurrentID();
/* 2481 */       Block.field_149771_c.func_148756_a(id, name, block);
/*      */       
/*      */       try {
/* 2484 */         ItemBlock itemBlock = clz.getConstructor(new Class[] { Block.class }).newInstance(new Object[] { block });
/* 2485 */         Item.field_150901_e.func_148756_a(id, name, itemBlock);
/*      */       }
/* 2487 */       catch (Exception e) {
/*      */         
/* 2489 */         throw new RuntimeException(e);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2494 */       block = GameRegistry.registerBlock(block, clz, name);
/*      */     } 
/*      */     
/* 2497 */     if (block instanceof IExtraData)
/*      */     {
/* 2499 */       ((IExtraData)block).init();
/*      */     }
/* 2501 */     return block;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item createItem(Item item, String identity) {
/* 2506 */     if (blockedItems.containsKey(identity)) {
/*      */       
/* 2508 */       ItemObject object = blockedItems.remove(identity);
/* 2509 */       return createItem(object.item, identity);
/*      */     } 
/*      */     
/* 2512 */     this; if (enableCustomIdAssigning) {
/*      */       
/* 2514 */       Item.field_150901_e.func_148756_a(itemIDs.getAndUpdateCurrentID(), identity, item);
/*      */     }
/*      */     else {
/*      */       
/* 2518 */       item = GameRegistry.registerItem(item, identity, "ic2");
/*      */     } 
/* 2520 */     if (item instanceof IExtraData)
/*      */     {
/* 2522 */       ((IExtraData)item).init();
/*      */     }
/* 2524 */     return item;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item createItem(Item item) {
/* 2529 */     return createItem(item, item.func_77658_a());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void addValuableOre(IRecipeInput input, int value) {
/* 2534 */     ItemStack item = input.getInputs().get(0);
/* 2535 */     addValuableOre(item.func_77973_b(), item.func_77973_b().func_77647_b(item.func_77960_j()), value);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void addValuableOre(Block blockId, int value) {
/* 2540 */     addValuableOre(Block.func_149682_b(blockId), 32767, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void addValuableOre(Item itemId, int value) {
/* 2545 */     addValuableOre(Item.func_150891_b(itemId), 32767, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void addValuableOre(Item itemId, int meta, int value) {
/* 2550 */     addValuableOre(Block.func_149634_a(itemId), meta, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void addValuableOre(Block blockId, int meta, int value) {
/* 2555 */     addOre(blockId, meta, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void addValuableOre(int blockId, int metaData, int value) {
/* 2560 */     addOre(Block.func_149729_e(blockId), metaData, value);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addOre(Block par1, int meta, int value) {
/* 2565 */     if (par1 == Blocks.field_150350_a) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2570 */     if (valuableOres.containsKey(par1)) {
/*      */       
/* 2572 */       Map<Integer, Integer> metaMap = valuableOres.get(par1);
/* 2573 */       if (metaMap.containsKey(Integer.valueOf(32767))) {
/*      */         return;
/*      */       }
/*      */       
/* 2577 */       if (meta == 32767)
/*      */       {
/* 2579 */         metaMap.clear();
/* 2580 */         metaMap.put(Integer.valueOf(32767), Integer.valueOf(value));
/* 2581 */         maxOreValue = Math.max(maxOreValue, value);
/*      */       }
/* 2583 */       else if (!metaMap.containsKey(Integer.valueOf(meta)))
/*      */       {
/* 2585 */         metaMap.put(Integer.valueOf(meta), Integer.valueOf(value));
/* 2586 */         maxOreValue = Math.max(maxOreValue, value);
/*      */       }
/* 2588 */       else if (metaMap.containsKey(Integer.valueOf(meta)) && ((Integer)metaMap.get(Integer.valueOf(meta))).intValue() < value)
/*      */       {
/* 2590 */         metaMap.put(Integer.valueOf(meta), Integer.valueOf(value));
/* 2591 */         maxOreValue = Math.max(maxOreValue, value);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2596 */       Map<Integer, Integer> metaMap = new TreeMap<>();
/* 2597 */       metaMap.put(Integer.valueOf(meta), Integer.valueOf(value));
/* 2598 */       valuableOres.put(par1, metaMap);
/* 2599 */       maxOreValue = Math.max(maxOreValue, value);
/*      */     } 
/* 2601 */     if (!oreValues.containsKey(Integer.valueOf(value)))
/*      */     {
/* 2603 */       oreValues.put(Integer.valueOf(value), new ArrayList<>());
/*      */     }
/* 2605 */     ((List<ItemStack>)oreValues.get(Integer.valueOf(value))).add(new ItemStack(par1, 1, meta));
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getValuableOreString() {
/* 2610 */     StringBuilder ret = new StringBuilder();
/* 2611 */     boolean first = true;
/*      */     
/* 2613 */     for (Map.Entry<Block, Map<Integer, Integer>> entry : valuableOres.entrySet()) {
/*      */       
/* 2615 */       for (Map.Entry<Integer, Integer> entry2 : (Iterable<Map.Entry<Integer, Integer>>)((Map)entry.getValue()).entrySet()) {
/*      */         
/* 2617 */         if (first) {
/*      */           
/* 2619 */           first = false;
/*      */         }
/*      */         else {
/*      */           
/* 2623 */           ret.append(", ");
/*      */         } 
/* 2625 */         ret.append(Block.field_149771_c.func_148750_c(entry.getKey()));
/* 2626 */         if (((Integer)entry2.getKey()).intValue() != 32767) {
/*      */           
/* 2628 */           ret.append("-");
/* 2629 */           ret.append(entry2.getKey());
/*      */         } 
/* 2631 */         ret.append(":");
/* 2632 */         ret.append(entry2.getValue());
/*      */       } 
/*      */     } 
/* 2635 */     return ret.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setValuableOreFromString(String str) {
/* 2640 */     valuableOres.clear();
/*      */     
/* 2642 */     String[] arr$ = str.trim().split("\\s*,\\s*"), strParts = arr$;
/* 2643 */     for (String strPart : arr$) {
/*      */       
/* 2645 */       String[] idMetaValue = strPart.split("\\s*:\\s*");
/* 2646 */       String[] idMeta = idMetaValue[0].split("\\s*-\\s*");
/* 2647 */       if (idMeta[0].length() != 0) {
/*      */         
/* 2649 */         Block blockId = Block.func_149684_b(idMeta[0]);
/* 2650 */         if (blockId != null && blockId != Blocks.field_150350_a) {
/*      */ 
/*      */ 
/*      */           
/* 2654 */           int metaData = 32767;
/* 2655 */           int value = 1;
/* 2656 */           if (idMeta.length == 2)
/*      */           {
/* 2658 */             metaData = Integer.parseInt(idMeta[1]);
/*      */           }
/* 2660 */           if (idMetaValue.length == 2)
/*      */           {
/* 2662 */             value = Integer.parseInt(idMetaValue[1]);
/*      */           }
/* 2664 */           addOre(blockId, metaData, value);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @SubscribeEvent
/*      */   public void registerOre(OreDictionary.OreRegisterEvent event) {
/* 2672 */     String oreClass = event.Name;
/* 2673 */     ItemStack ore = event.Ore;
/*      */     
/* 2675 */     if (oreClass.equals("oreCopper")) {
/*      */       
/* 2677 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 3);
/* 2678 */       TileEntityMacerator.addRecipe(ore.func_77946_l(), StackUtil.copyWithSize(Ic2Items.copperDust, 2), 0.35F);
/*      */     }
/* 2680 */     else if (oreClass.equals("oreGemRuby") || oreClass.equals("oreGemGreenSapphire") || oreClass.equals("oreGemSapphire") || oreClass.equals("oreRedstone") || oreClass.equals("oreGold")) {
/*      */       
/* 2682 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 5);
/*      */     }
/* 2684 */     else if (oreClass.equals("oreSilver")) {
/*      */       
/* 2686 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 5);
/* 2687 */       TileEntityMacerator.addRecipe(ore.func_77946_l(), StackUtil.copyWithSize(Ic2Items.silverDust, 2), 0.7F);
/*      */     }
/* 2689 */     else if (oreClass.equals("oreTin")) {
/*      */       
/* 2691 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 3);
/* 2692 */       TileEntityMacerator.addRecipe(ore.func_77946_l(), StackUtil.copyWithSize(Ic2Items.tinDust, 2), 0.5F);
/*      */     }
/* 2694 */     else if (oreClass.equals("oreUranium")) {
/*      */       
/* 2696 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 6);
/* 2697 */       TileEntityMacerator.addRecipe(ore.func_77946_l(), StackUtil.copyWithSize(Ic2Items.uraniumDrop, 2), 1.0F);
/*      */     }
/* 2699 */     else if (oreClass.equals("dropUranium")) {
/*      */       
/* 2701 */       TileEntityCompressor.addRecipe(ore.func_77946_l(), Ic2Items.uraniumIngot.func_77946_l(), 0.25F);
/*      */     }
/* 2703 */     else if (oreClass.equals("oreLead") || oreClass.equals("oreLapis") || oreClass.equals("oreQuartz")) {
/*      */       
/* 2705 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 4);
/*      */     }
/* 2707 */     else if (oreClass.equals("oreIron")) {
/*      */       
/* 2709 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 3);
/*      */     }
/* 2711 */     else if (oreClass.equals("oreDiamond") || oreClass.equals("oreEmerald")) {
/*      */       
/* 2713 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 6);
/*      */     }
/* 2715 */     else if (oreClass.startsWith("ore")) {
/*      */       
/* 2717 */       addValuableOre(ore.func_77973_b(), ore.func_77960_j(), 1);
/*      */     }
/* 2719 */     else if (oreClass.equals("ingotTin")) {
/*      */       
/* 2721 */       TileEntityMacerator.addRecipe(ore.func_77946_l(), StackUtil.copyWithSize(Ic2Items.tinDust, 1), 0.2F);
/*      */     }
/* 2723 */     else if (oreClass.equals("ingotCopper")) {
/*      */       
/* 2725 */       TileEntityMacerator.addRecipe(ore.func_77946_l(), StackUtil.copyWithSize(Ic2Items.copperDust, 1), 0.2F);
/* 2726 */       TileEntityCompressor.addRecipe(StackUtil.copyWithSize(ore, 8), Ic2Items.denseCopperPlate.func_77946_l(), 0.2F);
/*      */     }
/* 2728 */     else if (oreClass.equals("ingotSilver")) {
/*      */       
/* 2730 */       TileEntityMacerator.addRecipe(ore.func_77946_l(), StackUtil.copyWithSize(Ic2Items.silverDust, 1), 0.5F);
/*      */     }
/* 2732 */     else if (oreClass.startsWith("dye")) {
/*      */       
/* 2734 */       int dyeId = -1;
/* 2735 */       for (int i = 0; i < dyes.length; i++) {
/*      */         
/* 2737 */         if (oreClass.equals(dyes[i])) {
/*      */           
/* 2739 */           dyeId = i;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2743 */       if (dyeId > -1)
/*      */       {
/* 2745 */         ItemToolPainter.dyes.put(new ChunkCoordIntPair(Item.func_150891_b(ore.func_77973_b()), ore.func_77960_j()), Integer.valueOf(dyeId));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @SubscribeEvent
/*      */   public void addElectroRecipe(RecipeEvent evt) {
/* 2753 */     if (evt.isCanceled()) {
/*      */       return;
/*      */     }
/*      */     
/* 2757 */     if (evt instanceof ElectrolyzerRecipeEvent) {
/*      */       
/* 2759 */       ElectrolyzerRecipeEvent event = (ElectrolyzerRecipeEvent)evt;
/* 2760 */       TileEntityElectrolyzer.addRecipe(event.getInput(), event.getOutput(), event.energy, event.charge, event.discharge);
/*      */     } 
/* 2762 */     if (evt instanceof CannerRecipe) {
/*      */       
/* 2764 */       CannerRecipe canner = (CannerRecipe)evt;
/* 2765 */       if (canner instanceof CannerRecipe.FuelValue)
/*      */       {
/* 2767 */         TileEntityCanner.fuelValues.put(new TileEntityElectrolyzer.ItemRecipe(canner.getInput()), Integer.valueOf(canner.getFuelValue()));
/*      */       }
/* 2769 */       if (canner instanceof CannerRecipe.FuelMultiplier)
/*      */       {
/* 2771 */         TileEntityCanner.fuelMultiplyers.put(new TileEntityElectrolyzer.ItemRecipe(canner.getInput()), Float.valueOf(canner.getFuelValue() / 100.0F));
/*      */       }
/* 2773 */       if (canner instanceof CannerRecipe.FoodEffect)
/*      */       {
/* 2775 */         TileEntityCanner.specialFood.put(new TileEntityElectrolyzer.ItemRecipe(canner.getInput()), Integer.valueOf(canner.getFuelValue()));
/*      */       }
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\core\IC2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */