package net.cmr.jurassicrevived.entity.ai;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IDinoData {
    // Core Vitals
    float getHunger();
    void setHunger(float value);
    void modifyHunger(float change);
    
    float getThirst();
    void setThirst(float value);

    // Mood & Personality
    enum Mood { ANGRY, NEUTRAL, CONTENT, HAPPY, SCARED, SLEEPY }
    Mood getMood();
    void setMood(Mood mood);

    // Aggression / Territoriality
    // Replaces old float getTerritoriality? Or keeps it? 
    // "I want to add... Aggression, and a Condition." and "And this list for Aggression: Free-Roaming, Territorial, and Neutral"
    // We will keep getTerritoriality() as a float for logic if needed, but add the Enum.
    enum Aggression { FREE_ROAMING, TERRITORIAL, NEUTRAL, SCAVENGER }
    Aggression getAggression();
    void setAggression(Aggression aggression);

    float getTerritoriality();
    void setTerritoriality(float value);

    // New Stats
    enum DietaryClassification { CRUSTACIVORE, DETRITIVORE, HERBIVORE, INSECTIVORE, OMNIVORE, PISCIVORE, PLANKTIVORE, CARNIVORE }
    DietaryClassification getDiet();
    void setDiet(DietaryClassification diet);

    enum Type { TERRESTRIAL, AVIAN, AMPHIBIOUS, MARINE }
    Type getType();
    void setType(Type type);

    enum Group { THEROPOD, THYREOPHORAN, CERAPOD, SAUROPOD, ORNITHOPOD, AMPHIBIAN, ARCHOSAUROMORPH, PLEURODIRE, PTEROSAUR, REPTILIOMORPH, SQUAMATE }
    Group getGroup();
    void setGroup(Group group);

    enum BirthType { EGG_LAYING, LIVE_BIRTH }
    BirthType getBirthType();
    void setBirthType(BirthType birthType);

    enum ActivityPattern { CATHEMERAL, CREPUSCULAR, DIURNAL, NOCTURNAL }
    ActivityPattern getActivityPattern();
    void setActivityPattern(ActivityPattern pattern);

    // Conditions
    enum Condition { COMATOSE, INFECTED, LOW_HEALTH, HUNGRY, OVERHEATING, POISONED, SEDATED, SUFFOCATING, TAMED, WITHER, READY_TO_MATE, THIRSTY, STARVING, DEHYDRATED, FREEZING, SLEEPING }
    Set<Condition> getConditions();
    void addCondition(Condition condition);
    void removeCondition(Condition condition);
    boolean hasCondition(Condition condition);

    // Taming / Whitelist
    void addWhitelistedPlayer(UUID playerUUID);
    boolean isWhitelisted(UUID playerUUID);
    List<UUID> getWhitelistedPlayers();

    // Serialization & Sync
    void saveNBT(CompoundTag tag);
    void loadNBT(CompoundTag tag);

    // Called on entity tick to sync specific data (Hunger/Mood) to client via SynchedEntityData if needed
    void tickSync(SynchedEntityData entityData);
}
