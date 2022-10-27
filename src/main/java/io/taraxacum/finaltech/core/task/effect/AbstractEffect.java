package io.taraxacum.finaltech.core.task.effect;

import io.taraxacum.finaltech.api.task.AbstractSingleTickerTask;
import org.bukkit.entity.LivingEntity;

/**
 * @author Final_ROOT
 * @since 2.0
 */
public abstract class AbstractEffect extends AbstractSingleTickerTask<LivingEntity> {
    private int level;

    public AbstractEffect(int time, int level) {
        super(time);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (this.maxLevel() > 0) {
            this.level = Math.min(this.maxLevel(), level);
        } else if (this.maxLevel() < 0) {
            this.level = level;
        }
    }

    abstract int maxLevel();
}
