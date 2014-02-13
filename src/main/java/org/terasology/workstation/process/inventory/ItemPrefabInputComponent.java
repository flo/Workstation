package org.terasology.workstation.process.inventory;

import com.google.common.base.Predicate;
import org.terasology.asset.AssetUri;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.logic.inventory.ItemComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcin Sciesinski <marcins78@gmail.com>
 */
public class ItemPrefabInputComponent extends InventoryInputComponent {
    public Map<String, Integer> itemCounts;

    @Override
    protected Map<Predicate<EntityRef>, Integer> getInputItems() {
        Map<Predicate<EntityRef>, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> itemCount : itemCounts.entrySet()) {
            result.put(new ItemPrefabPredicate(new AssetUri(itemCount.getKey())), itemCount.getValue());
        }

        return result;
    }

    private final static class ItemPrefabPredicate implements Predicate<EntityRef> {
        private AssetUri prefab;

        private ItemPrefabPredicate(AssetUri prefab) {
            this.prefab = prefab;
        }

        @Override
        public boolean apply(EntityRef input) {
            ItemComponent item = input.getComponent(ItemComponent.class);
            if (item == null) {
                return false;
            }
            return input.getParentPrefab().getURI().equals(prefab);
        }
    }
}