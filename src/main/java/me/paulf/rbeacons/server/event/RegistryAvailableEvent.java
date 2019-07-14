package me.paulf.rbeacons.server.event;

import net.minecraftforge.fml.common.eventhandler.GenericEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public final class RegistryAvailableEvent<T extends IForgeRegistryEntry<T>> extends GenericEvent<T> {
	private final IForgeRegistry<T> registry;

	private RegistryAvailableEvent(final Class<T> type, final IForgeRegistry<T> registry) {
		super(type);
		this.registry = registry;
	}

	public IForgeRegistry<T> getRegistry() {
		return this.registry;
	}

	public static <T extends IForgeRegistryEntry<T>> RegistryAvailableEvent<T> create(final IForgeRegistry<T> registry) {
		return new RegistryAvailableEvent<>(registry.getRegistrySuperType(), registry);
	}
}
