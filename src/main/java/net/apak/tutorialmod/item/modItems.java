package net.apak.tutorialmod.item;

import net.apak.tutorialmod.TutorialMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class modItems{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final DeferredRegister <Item> ITEMS= DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MOD_ID);


    public static final RegistryObject<Item> CSWORD =
            ITEMS.register("csword", () -> new SwordItem(
                    Tiers.NETHERITE, 1,10,
                    new Item.Properties()
                            .durability(1000)
                            .fireResistant()
            )
                    {
                        @Override
                        public boolean hurtEnemy(ItemStack stack,LivingEntity target, LivingEntity attacker){
                            boolean result = super.hurtEnemy(stack, target, attacker);
                            float accDam = target.getMaxHealth()- target.getHealth();

                            if (result && !target.level().isClientSide()){
                                    applyEffect(target, attacker);
                                    LOGGER.info("Sword Damage: " + accDam );
                            }

                            return result;

                        }
                    }
            );
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static void applyEffect(LivingEntity target, LivingEntity attacker) {
        Level level = target.level();

        if (!level.isClientSide()) {
            if (attacker instanceof Player player) {
                attacker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 2));

            }
        }
    }

}
