package org.dvsa.testing.framework.util;

import activesupport.system.out.Output;
import org.dvsa.testing.framework.exception.NonexistingWorldStateException;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class World {

    HashMap<String, Object> state;

    public World(@NotNull HashMap<String, Object> state) {
        this.state = state;
    }

    public Object get(@NotNull String key) throws NonexistingWorldStateException {
        if (!state.containsKey(key)) {
            throw new NonexistingWorldStateException(Output.printColoredLog(String.format(
                    "[ERROR] Unable to find state using KEY: '%s'",
                    key
            )));
        }

        return state.get(key);
    }

    public void put(@NotNull String key, @NotNull Object value) {
        state.put(key, value);
    }

    public void replate(@NotNull String key, @NotNull Object value) {
        state.replace(key, value);
    }

}
