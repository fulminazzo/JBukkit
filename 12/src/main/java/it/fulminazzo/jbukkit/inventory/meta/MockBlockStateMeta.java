package it.fulminazzo.jbukkit.inventory.meta;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.meta.BlockStateMeta;

@Getter
@Setter
public class MockBlockStateMeta extends MockItemMeta implements BlockStateMeta {
    private BlockState blockState;

    @Override
    public boolean hasBlockState() {
        return this.blockState != null;
    }

    @Override
    public MockBlockStateMeta clone() {
        return (MockBlockStateMeta) super.clone();
    }

}
