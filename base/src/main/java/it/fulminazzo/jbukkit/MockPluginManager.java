package it.fulminazzo.jbukkit;

import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.NotNull;

public class MockPluginManager {

    @Getter
    private final CommandMap commandMap;

    public MockPluginManager(final @NotNull Server server) {
        this.commandMap = new SimpleCommandMap(server);
    }

}
