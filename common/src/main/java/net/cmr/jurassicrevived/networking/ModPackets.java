package net.cmr.jurassicrevived.networking;

import dev.architectury.fluid.FluidStack;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import io.netty.buffer.Unpooled;
import net.cmr.jurassicrevived.Constants;
import net.cmr.jurassicrevived.screen.custom.TankMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
//? if >1.20.1 {
/*import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
*///?}
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ModPackets {
	public static final ResourceLocation TANK_SYNC = Constants.rl("tank_sync");

	public static void register() {
		//? if >1.20.1 {
		/*EnvExecutor.runInEnv(Env.CLIENT, () -> () -> NetworkManager.registerReceiver(NetworkManager.Side.S2C, TANK_SYNC_TYPE, TANK_SYNC_STREAM_CODEC, (payload, context) -> {
			FluidStack fluidStack = payload.fluidStack();
			context.queue(() -> {
				Player player = context.getPlayer();
				if (player != null) {
					AbstractContainerMenu menu = player.containerMenu;
					if (menu instanceof TankMenu tankMenu) {
						tankMenu.setFluid(fluidStack);
					}
				}
			});
		}));
		*///?} else {
		EnvExecutor.runInEnv(Env.CLIENT, () -> () -> NetworkManager.registerReceiver(NetworkManager.Side.S2C, TANK_SYNC, (buf, context) -> {
			FluidStack fluidStack = FluidStack.read(buf);
			context.queue(() -> {
				Player player = context.getPlayer();
				if (player != null) {
					AbstractContainerMenu menu = player.containerMenu;
					if (menu instanceof TankMenu tankMenu) {
						tankMenu.setFluid(fluidStack);
					}
				}
			});
		}));
		//?}
	}

	public static void sendTankSync(ServerPlayer player, FluidStack fluidStack) {
		//? if >1.20.1 {
		/*NetworkManager.sendToPlayer(player, new TankSyncPayload(fluidStack));
		*///?} else {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		fluidStack.write(buf);
		NetworkManager.sendToPlayer(player, TANK_SYNC, buf);
		//?}
	}

	//? if >1.20.1 {
	/*public static final CustomPacketPayload.Type<TankSyncPayload> TANK_SYNC_TYPE = new CustomPacketPayload.Type<>(TANK_SYNC);
	public static final StreamCodec<RegistryFriendlyByteBuf, TankSyncPayload> TANK_SYNC_STREAM_CODEC = StreamCodec.of((buf, payload) -> payload.write(buf), TankSyncPayload::new);

	public record TankSyncPayload(FluidStack fluidStack) implements CustomPacketPayload {
		public TankSyncPayload(RegistryFriendlyByteBuf buf) {
			this(readFluid(buf));
		}

		public void write(RegistryFriendlyByteBuf buf) {
			if (fluidStack.isEmpty()) {
				buf.writeBoolean(false);
			} else {
				buf.writeBoolean(true);
				buf.writeResourceLocation(BuiltInRegistries.FLUID.getKey(fluidStack.getFluid()));
				buf.writeLong(fluidStack.getAmount());
				//? if >1.20.1 {
				/^DataComponentPatch.STREAM_CODEC.encode(buf, fluidStack.getPatch());
				^///?} else {
				buf.writeNbt(fluidStack.getTag());
				//?}
			}
		}

		private static FluidStack readFluid(RegistryFriendlyByteBuf buf) {
			if (!buf.readBoolean()) {
				return FluidStack.empty();
			}
			ResourceLocation fluidId = buf.readResourceLocation();
			long amount = buf.readLong();
			//? if >1.20.1 {
			/^DataComponentPatch patch = DataComponentPatch.STREAM_CODEC.decode(buf);
			^///?} else {
			CompoundTag tag = buf.readNbt();
			//?}
			Fluid fluid = BuiltInRegistries.FLUID.get(fluidId);
			if (fluid == Fluids.EMPTY) {
				return FluidStack.empty();
			}
			//? if >1.20.1 {
			/^return FluidStack.create(fluid, amount, patch);
			^///?} else {
			FluidStack stack = FluidStack.create(fluid, amount);
			stack.setTag(tag);
			return stack;
			//?}
		}

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TANK_SYNC_TYPE;
		}
	}
	*///?}
}