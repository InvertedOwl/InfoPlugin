package com.exmogamers.custominfo;

import com.exmogamers.custominfo.commands.biome;
import com.exmogamers.custominfo.commands.coords;
import com.exmogamers.custominfo.commands.tool;
import com.exmogamers.custominfo.commands.ylevel;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CustomInfo extends JavaPlugin {
    public static CustomInfo instance;
    public static CustomInfo getInstance() {
        return instance;
    }
    @Override
    public void onLoad() {
        instance = this;
    }


    FileConfiguration configuration;

    public FileConfiguration getConfiguration () {
        return configuration;
    }

    @Override
    public void onEnable() {
        this.getDataFolder().mkdir();
        File customYml = new File(this.getDataFolder()+"/customYmlFile.yml");
        try {
            customYml.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileConfiguration customConfig = YamlConfiguration.loadConfiguration(customYml);

        configuration = customConfig;
        this.getCommand("coords").setExecutor(new coords());
        this.getCommand("biome").setExecutor(new biome());
        this.getCommand("ylevel").setExecutor(new ylevel());
        this.getCommand("tooldurability").setExecutor(new tool());


        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {

                    String tosend = "";

                    if (customConfig.getBoolean(player.getUniqueId() + ".coords")) {
                        tosend += "x: §6" + (int) player.getLocation().getX() + "  §r";
                        tosend += "y: §6" + (int) player.getLocation().getY() + "  §r";
                        tosend += "z: §6" + (int) player.getLocation().getZ() + "  §r";
                        tosend += "  ";


                    }
                    if (customConfig.getBoolean(player.getUniqueId() + ".biome")) {
                        tosend += "Biome: §6";

                        String biome = player.getLocation().getBlock().getBiome().toString().toLowerCase();
                        tosend += biome.substring(0, 1).toUpperCase() + biome.substring(1);
                        tosend += "   §r";
                    }
                    if (customConfig.getBoolean(player.getUniqueId() + ".ylevel")) {
                        tosend += "Y Level: §6" + (int) player.getLocation().getY() + "  §r";
                    }
                    if (customConfig.getBoolean(player.getUniqueId() + ".tooldurability")) {
                        if (player.getItemInHand().getType().getMaxDurability() > 0) {
                            int durability = player.getItemInHand().getType().getMaxDurability() - player.getItemInHand().getDurability();
                            tosend += "Durability: §6" + durability + "/" + (int) player.getItemInHand().getType().getMaxDurability() + "  §r";
                        } else {
                            tosend += "Durability: §6N/A";

                        }
                    }


                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(tosend));
                }
            }
        }, 1, 1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void saveCustomYml(FileConfiguration ymlConfig) {
        try {
            ymlConfig.save(new File(this.getDataFolder()+"/customYmlFile.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
