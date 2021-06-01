package xyz.n7mn.dev.survivalreport;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportCommand implements CommandExecutor {

    private final Plugin plugin;
    private final JDA jda;

    public ReportCommand(Plugin plugin, JDA jda){
        this.plugin = plugin;
        this.jda = jda;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0){
            sender.sendMessage(ChatColor.YELLOW + "[ななみ生活鯖] "+ChatColor.RESET+"こちらは通報コマンドです。\n" +
                    "使い方は「/report 内容」です。\n" +
                    "例：「/report ここが荒れています！」、「/report ○○○○さんが空飛んでいます！」" +
                    "※いたずらや嘘の通報はBAN対象です。");
            return true;
        }

        Player player = null;
        String loca = "(不明)";
        if (sender instanceof Player){
            player = (Player) sender;

            loca = "ワールド名 : "+player.getLocation().getWorld().getName() + "\n" +
                    "X : " + player.getLocation().getBlockX()+"\n" +
                    "Y : " + player.getLocation().getBlockY()+"\n" +
                    "Z : " + player.getLocation().getBlockZ()+"";
        }

        StringBuffer sb = new StringBuffer();

        for (String str : args){
            sb.append(str);
            sb.append(" ");
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(sender.getName()+" さんの通報");
        builder.setDescription("" +
                "内容：\n" +
                "```\n" +
                ""+sb.toString().replaceAll("`","｀")+"\n" +
                "```\n" +
                "\n" +
                "通報位置 : " + loca +
                "運営へ\n" +
                "対応が済んだらリアクションをお願いします。"
        );
        builder.setFooter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        jda.getTextChannelById(plugin.getConfig().getString("sendChannelID")).sendMessage(builder.build()).queue();
        sender.sendMessage(ChatColor.YELLOW + "[ななみ生活鯖] "+ChatColor.GREEN+"通報ありがとうございました。");

        return true;
    }
}
