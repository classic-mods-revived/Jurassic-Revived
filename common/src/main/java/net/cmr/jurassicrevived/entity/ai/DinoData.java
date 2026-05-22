package net.cmr.jurassicrevived.entity.ai;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DinoData implements IDinoData {

    private final float maxHunger;
    private final float maxThirst;

    private float hunger;
    private float thirst;

    private Mood mood;
    private Aggression aggression;
    private float territoriality;

    private DietaryClassification diet;
    private Type type;
    private Group group;
    private BirthType birthType;
    private ActivityPattern activityPattern;

    private final Set<Condition> conditions = EnumSet.noneOf(Condition.class);
    private final List<UUID> whitelistedPlayers = new ArrayList<>();

    public DinoData(
        float maxHunger,
        float maxThirst,
        Mood mood,
        Aggression aggression,
        float territoriality,
        DietaryClassification diet,
        Type type,
        Group group,
        BirthType birthType,
        ActivityPattern activityPattern
    ) {
        this.maxHunger = maxHunger;
        this.maxThirst = maxThirst;
        this.hunger = maxHunger;
        this.thirst = maxThirst;
        this.mood = mood;
        this.aggression = aggression;
        this.territoriality = territoriality;
        this.diet = diet;
        this.type = type;
        this.group = group;
        this.birthType = birthType;
        this.activityPattern = activityPattern;
    }

    @Override
    public float getHunger() {
        return hunger;
    }

    @Override
    public void setHunger(float value) {
        this.hunger = clamp(value, 0.0f, maxHunger);
    }

    @Override
    public void modifyHunger(float change) {
        setHunger(this.hunger + change);
    }

    @Override
    public float getThirst() {
        return thirst;
    }

    @Override
    public void setThirst(float value) {
        this.thirst = clamp(value, 0.0f, maxThirst);
    }

    @Override
    public Mood getMood() {
        return mood;
    }

    @Override
    public void setMood(Mood mood) {
        this.mood = mood;
    }

    @Override
    public Aggression getAggression() {
        return aggression;
    }

    @Override
    public void setAggression(Aggression aggression) {
        this.aggression = aggression;
    }

    @Override
    public float getTerritoriality() {
        return territoriality;
    }

    @Override
    public void setTerritoriality(float value) {
        this.territoriality = clamp(value, 0.0f, 1.0f);
    }

    @Override
    public DietaryClassification getDiet() {
        return diet;
    }

    @Override
    public void setDiet(DietaryClassification diet) {
        this.diet = diet;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public BirthType getBirthType() {
        return birthType;
    }

    @Override
    public void setBirthType(BirthType birthType) {
        this.birthType = birthType;
    }

    @Override
    public ActivityPattern getActivityPattern() {
        return activityPattern;
    }

    @Override
    public void setActivityPattern(ActivityPattern pattern) {
        this.activityPattern = pattern;
    }

    @Override
    public Set<Condition> getConditions() {
        return conditions;
    }

    @Override
    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    @Override
    public void removeCondition(Condition condition) {
        this.conditions.remove(condition);
    }

    @Override
    public boolean hasCondition(Condition condition) {
        return this.conditions.contains(condition);
    }

    @Override
    public void addWhitelistedPlayer(UUID playerUUID) {
        if (!this.whitelistedPlayers.contains(playerUUID)) {
            this.whitelistedPlayers.add(playerUUID);
        }
    }

    @Override
    public boolean isWhitelisted(UUID playerUUID) {
        return this.whitelistedPlayers.contains(playerUUID);
    }

    @Override
    public List<UUID> getWhitelistedPlayers() {
        return this.whitelistedPlayers;
    }

    @Override
    public void saveNBT(CompoundTag tag) {
        CompoundTag dinoTag = new CompoundTag();

        dinoTag.putFloat("Hunger", this.hunger);
        dinoTag.putFloat("Thirst", this.thirst);
        dinoTag.putString("Mood", this.mood.name());
        dinoTag.putString("Aggression", this.aggression.name());
        dinoTag.putFloat("Territoriality", this.territoriality);
        dinoTag.putString("Diet", this.diet.name());
        dinoTag.putString("Type", this.type.name());
        dinoTag.putString("Group", this.group.name());
        dinoTag.putString("BirthType", this.birthType.name());
        dinoTag.putString("ActivityPattern", this.activityPattern.name());

        int[] conditionIds = this.conditions.stream()
            .mapToInt(Enum::ordinal)
            .toArray();
        dinoTag.putIntArray("Conditions", conditionIds);

        ListTag whitelistTag = new ListTag();
        for (UUID uuid : this.whitelistedPlayers) {
            CompoundTag uuidTag = new CompoundTag();
            uuidTag.putUUID("UUID", uuid);
            whitelistTag.add(uuidTag);
        }
        dinoTag.put("WhitelistedPlayers", whitelistTag);

        tag.put("DinoData", dinoTag);
    }

    @Override
    public void loadNBT(CompoundTag tag) {
        if (!tag.contains("DinoData")) {
            return;
        }

        CompoundTag dinoTag = tag.getCompound("DinoData");

        this.hunger = clamp(dinoTag.getFloat("Hunger"), 0.0f, maxHunger);
        this.thirst = clamp(dinoTag.getFloat("Thirst"), 0.0f, maxThirst);
        this.mood = readEnum(dinoTag, "Mood", Mood.class, this.mood);
        this.aggression = readEnum(dinoTag, "Aggression", Aggression.class, this.aggression);
        this.territoriality = clamp(dinoTag.getFloat("Territoriality"), 0.0f, 1.0f);
        this.diet = readEnum(dinoTag, "Diet", DietaryClassification.class, this.diet);
        this.type = readEnum(dinoTag, "Type", Type.class, this.type);
        this.group = readEnum(dinoTag, "Group", Group.class, this.group);
        this.birthType = readEnum(dinoTag, "BirthType", BirthType.class, this.birthType);
        this.activityPattern = readEnum(dinoTag, "ActivityPattern", ActivityPattern.class, this.activityPattern);

        this.conditions.clear();
        int[] conditionIds = dinoTag.getIntArray("Conditions");
        Condition[] conditionValues = Condition.values();
        for (int conditionId : conditionIds) {
            if (conditionId >= 0 && conditionId < conditionValues.length) {
                this.conditions.add(conditionValues[conditionId]);
            }
        }

        this.whitelistedPlayers.clear();
        ListTag whitelistTag = dinoTag.getList("WhitelistedPlayers", 10);
        for (int i = 0; i < whitelistTag.size(); i++) {
            CompoundTag uuidTag = whitelistTag.getCompound(i);
            if (uuidTag.hasUUID("UUID")) {
                this.whitelistedPlayers.add(uuidTag.getUUID("UUID"));
            }
        }
    }

    @Override
    public void tickSync(SynchedEntityData entityData) {
        // No-op for now. Add EntityDataAccessors later if you want client-side GUI/model access.
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private static <E extends Enum<E>> E readEnum(CompoundTag tag, String key, Class<E> enumClass, E fallback) {
        if (!tag.contains(key)) {
            return fallback;
        }

        try {
            return Enum.valueOf(enumClass, tag.getString(key));
        } catch (IllegalArgumentException ignored) {
            return fallback;
        }
    }
}
