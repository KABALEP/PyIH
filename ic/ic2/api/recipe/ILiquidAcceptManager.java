package ic2.api.recipe;

import java.util.Set;
import net.minecraftforge.fluids.Fluid;

public interface ILiquidAcceptManager {
  boolean acceptsFluid(Fluid paramFluid);
  
  Set<Fluid> getAcceptedFluids();
}


/* Location:              C:\Users\KABALEP\Desktop\мусор\IC2Classic-1.7.10-1.2.4.jar!\ic2\api\recipe\ILiquidAcceptManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */