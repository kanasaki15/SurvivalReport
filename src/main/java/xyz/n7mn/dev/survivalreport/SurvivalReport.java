package xyz.n7mn.dev.survivalreport;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SurvivalReport extends JavaPlugin {

    private JDA jda = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        try {
            jda = JDABuilder.createLight(getConfig().getString("token"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_EMOJIS)
                    .enableCache(CacheFlag.VOICE_STATE)
                    .enableCache(CacheFlag.EMOTE)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();
        } catch (Exception ex){
            ex.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }


        getCommand("report").setExecutor(new ReportCommand(this, jda));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (jda != null){
            jda.shutdownNow();
        }
    }
}
