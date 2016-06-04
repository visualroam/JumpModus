package me.dominik.Jumper.methoden;

import me.dominik.Jumper.methoden.CountdownEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;

public class Countdown {

    private static Field taskIdField = null;

    static {
        try {
            taskIdField = BukkitRunnable.class.getDeclaredField("taskId");
            taskIdField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private BukkitRunnable runnable;
    private boolean downcounting;
    private long interval;
    private Plugin plugin;
    private int from;
    private int to;

    private CountdownEvent event;

    public Countdown(Plugin plugin, CountdownEvent event, int from, int to, long interval) {
        this.downcounting = from > to;
        this.interval = interval;
        this.plugin = plugin;
        this.event = event;
        this.from = from;
        this.to = to;
    }

    public void startCountdown() {
        if (this.runnable == null) {
            this.runnable = new BukkitRunnable() {
                @Override public void run() {
                    if (from == to) {
                        stopCountdown(false);
                    } else {
                        event.tick(from);
                        if (downcounting) {
                            from--;
                        } else {
                            from++;
                        }
                    }
                }
            };
        }

        this.runnable.runTaskTimer(plugin, 0L, interval);
    }

    public void stopCountdown(boolean forceStop) {
        if (event != null) {
            if (!forceStop) event.finish();
        }

        if (runnable != null && this.runnable.getTaskId() != -1) {
            this.runnable.cancel();

            try {
                taskIdField.set(runnable, -1); //Sollte so gehen ^^
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}