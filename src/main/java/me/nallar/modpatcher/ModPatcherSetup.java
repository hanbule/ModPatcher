package me.nallar.modpatcher;

import cpw.mods.fml.relauncher.IFMLCallHook;
import net.minecraft.launchwrapper.LaunchClassLoader;

import java.util.*;

/**
 * Set as the setup class for your CoreMod to set up ModPatcher
 *
 * <pre><code>@Override public String getSetupClass() { return ModPatcher.getSetupClass(); }</code></pre>
 */
public class ModPatcherSetup implements IFMLCallHook {
	private static boolean modPatcherInitialised = false;

	@Override
	public void injectData(Map<String, Object> data) {
		initialised((LaunchClassLoader) data.get("classLoader"));
	}

	private void initialised(LaunchClassLoader classLoader) {
		if (modPatcherInitialised) {
			return;
		}
		modPatcherInitialised = true;

		classLoader.addTransformerExclusion("me.nallar.modpatcher");
		classLoader.addTransformerExclusion("javassist");
		classLoader.addTransformerExclusion("org.json");
		LaunchClassLoaderUtil.instance = classLoader;
		LaunchClassLoaderUtil.addTransformer(ModPatcher.getInstance());
		LaunchClassLoaderUtil.dumpTransformersIfEnabled();
		LaunchClassLoaderUtil.removeRedundantExclusions();
	}

	@Override
	public Void call() throws Exception {
		return null;
	}
}
