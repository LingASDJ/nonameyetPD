/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2019 Considered Hamster
 *
 * No Name Yet Pixel Dungeon
 * Copyright (C) 2018-2019 RavenWolf
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.ravenwolf.nnypd;

import com.ravenwolf.nnypd.actors.mobs.Mob;
import com.ravenwolf.nnypd.items.Item;
import com.ravenwolf.nnypd.items.bags.HerbPouch;
import com.ravenwolf.nnypd.items.bags.ScrollHolder;
import com.ravenwolf.nnypd.items.bags.WandHolster;
import com.ravenwolf.nnypd.items.potions.Potion;
import com.ravenwolf.nnypd.items.rings.Ring;
import com.ravenwolf.nnypd.items.scrolls.Scroll;
import com.ravenwolf.nnypd.items.wands.Wand;
import com.ravenwolf.nnypd.misc.utils.GLog;
import com.ravenwolf.nnypd.scenes.PixelScene;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Badges {
	
	public enum Badge {
		MONSTERS_SLAIN_1( "击杀10名敌人", 0 ),
		MONSTERS_SLAIN_2( "击杀50名敌人", 1 ),
		MONSTERS_SLAIN_3( "击杀150名敌人", 2 ),
		MONSTERS_SLAIN_4( "击杀250名敌人", 3 ),
		GOLD_COLLECTED_1( "收集100金币", 4 ),
		GOLD_COLLECTED_2( "收集500金币", 5 ),
		GOLD_COLLECTED_3( "收集2500金币", 6 ),
		GOLD_COLLECTED_4( "收集7500金币", 7 ),
		LEVEL_REACHED_1( "达到16级", 8 ),
		LEVEL_REACHED_2( "达到21级", 9 ),
		LEVEL_REACHED_3( "达到26级", 10 ),
		LEVEL_REACHED_4( "达到31级", 11 ),
		ALL_POTIONS_IDENTIFIED( "全部药水鉴定", 16 ),
		ALL_SCROLLS_IDENTIFIED( "全部卷轴鉴定", 17 ),
		ALL_RINGS_IDENTIFIED( "全部戒指鉴定", 18 ),
		ALL_WANDS_IDENTIFIED( "全部法杖鉴定", 19 ),
		ALL_ITEMS_IDENTIFIED( "全部药水、卷轴、戒指和法杖鉴定", 35, true ),
		BAG_BOUGHT_SEED_POUCH,
		BAG_BOUGHT_SCROLL_HOLDER,
		BAG_BOUGHT_WAND_HOLSTER,
		ALL_BAGS_BOUGHT( "买下所有包裹", 23 ),
		DEATH_FROM_FIRE( "死于火焰", 24 ),
		DEATH_FROM_POISON( "死于剧毒", 25 ),
		DEATH_FROM_GAS( "死于毒气", 26 ),
		DEATH_FROM_HUNGER( "死于饥饿", 27 ),
		DEATH_FROM_GLYPH( "死于护甲刻印", 57 ),
		DEATH_FROM_FALLING( "死于坠落楼层", 59 ),
		YASD( "死于火焰、剧毒、毒气和饥饿", 34, true ),
		BOSS_SLAIN_1_WARRIOR,
		BOSS_SLAIN_1_MAGE,
		BOSS_SLAIN_1_ROGUE,
		BOSS_SLAIN_1_HUNTRESS,
		BOSS_SLAIN_1( "斩杀第1个boss", 12 ),
		BOSS_SLAIN_2( "斩杀第2个boss", 13 ),
		BOSS_SLAIN_3( "斩杀第3个boss", 14 ),
		BOSS_SLAIN_4( "斩杀第4个boss", 15 ),
		BOSS_SLAIN_1_ALL_CLASSES( "分别使用所有职业斩杀第1个boss", 32, true ),
		BOSS_SLAIN_3_GLADIATOR,
		BOSS_SLAIN_3_BERSERKER,
		BOSS_SLAIN_3_WARLOCK,
		BOSS_SLAIN_3_BATTLEMAGE,
		BOSS_SLAIN_3_FREERUNNER,
		BOSS_SLAIN_3_ASSASSIN,
		BOSS_SLAIN_3_SNIPER,
		BOSS_SLAIN_3_WARDEN,
		BOSS_SLAIN_3_ALL_SUBCLASSES(
				"3rd boss slain by Gladiator, Berserker, Warlock, Battlemage, Freerunner, Assassin, Sniper & Warden", 33, true ),
		RING_OF_HAGGLER( "Ring of Haggler obtained", 20 ),
		RING_OF_THORNS( "Ring of Thorns obtained", 21 ),
		STRENGTH_ATTAINED_1( "喝下1瓶力量药剂", 40 ),
		STRENGTH_ATTAINED_2( "喝下3瓶力量药剂", 41 ),
		STRENGTH_ATTAINED_3( "喝下6瓶力量药剂", 42 ),
		STRENGTH_ATTAINED_4( "喝下10瓶力量药剂", 43 ),
		FOOD_EATEN_1( "吃掉10份食物", 44 ),
		FOOD_EATEN_2( "吃掉20份食物", 45 ),
		FOOD_EATEN_3( "吃掉30份食物", 46 ),
		FOOD_EATEN_4( "吃掉40份食物", 47 ),
		MASTERY_WARRIOR,
		MASTERY_SCHOLAR,
		MASTERY_BRIGAND,
		MASTERY_ACOLYTE,
		ITEMS_UPGRADED_1( "使用1张升级卷轴", 48 ),
		ITEMS_UPGRADED_2( "使用3张升级卷轴", 49 ),
		ITEMS_UPGRADED_3( "使用6张升级卷轴", 50 ),
		ITEMS_UPGRADED_4( "使用10张升级卷轴", 51 ),
		RARE_ALBINO,
		RARE_BANDIT,
		RARE_SHIELDED,
		RARE_SENIOR,
		RARE_ACIDIC,
		RARE( "击杀所有稀有怪物", 37, true ),
		VICTORY_WARRIOR,
		VICTORY_SCHOLAR,
		VICTORY_BRIGAND,
		VICTORY_ACOLYTE,
		VICTORY_0( "在简单难度下获得Yendor护符", 60 ),
		VICTORY_1( "在普通难度下获得Yendor护符", 61 ),
		VICTORY_2( "在困难难度下获得Yendor护符", 62 ),
		VICTORY_3( "在专家难度下获得Yendor护符", 63 ),
		//		VICTORY_ALL_CLASSES( "Amulet of Yendor obtained by Warrior, Mage, Rogue & Huntress", 36, true ),
		VICTORY_ALL_CLASSES( "Amulet of Yendor obtained on Hardcore difficulty", 36, true ),
		MASTERY_COMBO( "达成10连击", 56 ),
		POTIONS_COOKED_1( "一场游戏中酿造3瓶药水", 52 ),
		POTIONS_COOKED_2( "一场游戏中酿造6瓶药水", 53 ),
		POTIONS_COOKED_3( "一场游戏中酿造9瓶药水", 54 ),
		POTIONS_COOKED_4( "一场游戏中酿造12瓶药水", 55 ),
		NO_MONSTERS_SLAIN( "在不击杀任何怪物的情况下通过楼层", 28 ),
		GRIM_WEAPON( "使用不洁武器附魔杀死一个怪物", 29 ),
		PIRANHAS( "消灭6只食人鱼", 30 ),
		NIGHT_HUNTER( "在夜间击杀15只怪物", 58 ),
		//		GAMES_PLAYED_1( "10 games played", 60, true ),
//		GAMES_PLAYED_2( "100 games played", 61, true ),
//		GAMES_PLAYED_3( "500 games played", 62, true ),
//		GAMES_PLAYED_4( "2000 games played", 63, true ),
		HAPPY_END( "幸福结局", 38 ),
		CHAMPION( "在专家难度下获胜", 39, true ),
		SUPPORTER( "感谢你的支持!", 31, true );
		
		public boolean meta;
		
		public String description;
		public int image;
		
		Badge(String description, int image) {
			this( description, image, false );
		}
		
		Badge(String description, int image, boolean meta) {
			this.description = description;
			this.image = image;
			this.meta = meta;
		}
		
		Badge() {
			this( "", -1 );
		}
	}
	
	private static HashSet<Badge> global;
	private static HashSet<Badge> local = new HashSet<Badges.Badge>();
	
	private static boolean saveNeeded = false;
	
	public static Callback loadingListener = null;
	
	public static void reset() {
		local.clear();
		loadGlobal();
	}
	
	private static final String BADGES_FILE	= "badges.dat";
	private static final String BADGES		= "badges";
	
	private static HashSet<Badge> restore( Bundle bundle ) {
		HashSet<Badge> badges = new HashSet<Badge>();
		
		String[] names = bundle.getStringArray( BADGES );
		for (int i=0; i < names.length; i++) {
			try {
				badges.add( Badge.valueOf( names[i] ) );
			} catch (Exception e) {
			}
		}
	
		return badges;
	}
	
	private static void store( Bundle bundle, HashSet<Badge> badges ) {
		int count = 0;
		String names[] = new String[badges.size()];
		
		for (Badge badge:badges) {
			names[count++] = badge.toString();
		}
		bundle.put( BADGES, names );
	}
	
	public static void loadLocal( Bundle bundle ) {
		local = restore( bundle );
	}
	
	public static void saveLocal( Bundle bundle ) {
		store( bundle, local );
	}
	
	public static void loadGlobal() {
		if (global == null) {
			try {
				InputStream input = Game.instance.openFileInput( BADGES_FILE );
				Bundle bundle = Bundle.read(input);
				input.close();
				
				global = restore( bundle );
				
			} catch (IOException e) {
				global = new HashSet<Badge>();
			}
		}
	}
	
	public static void saveGlobal() {
		
		Bundle bundle = null;
		
		if (saveNeeded) {
			
			bundle = new Bundle();
			store(bundle, global);
			
			try {
				OutputStream output = Game.instance.openFileOutput(BADGES_FILE, Game.MODE_PRIVATE);
				Bundle.write( bundle, output );
				output.close();
				saveNeeded = false;
			} catch (IOException e) {
				
			}
		}
	}

	public static void validateMonstersSlain() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;

            if (!local.contains(Badge.MONSTERS_SLAIN_1) && Statistics.enemiesSlain >= 10) {
                badge = Badge.MONSTERS_SLAIN_1;
                local.add(badge);
            }
            if (!local.contains(Badge.MONSTERS_SLAIN_2) && Statistics.enemiesSlain >= 50) {
                badge = Badge.MONSTERS_SLAIN_2;
                local.add(badge);
            }
            if (!local.contains(Badge.MONSTERS_SLAIN_3) && Statistics.enemiesSlain >= 150) {
                badge = Badge.MONSTERS_SLAIN_3;
                local.add(badge);
            }
            if (!local.contains(Badge.MONSTERS_SLAIN_4) && Statistics.enemiesSlain >= 250) {
                badge = Badge.MONSTERS_SLAIN_4;
                local.add(badge);
            }

            displayBadge(badge);
        }
	}
	
	public static void validateGoldCollected() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;

            if (!local.contains(Badge.GOLD_COLLECTED_1) && Statistics.goldCollected >= 100) {
                badge = Badge.GOLD_COLLECTED_1;
                local.add(badge);
            }
            if (!local.contains(Badge.GOLD_COLLECTED_2) && Statistics.goldCollected >= 500) {
                badge = Badge.GOLD_COLLECTED_2;
                local.add(badge);
            }
            if (!local.contains(Badge.GOLD_COLLECTED_3) && Statistics.goldCollected >= 2500) {
                badge = Badge.GOLD_COLLECTED_3;
                local.add(badge);
            }
            if (!local.contains(Badge.GOLD_COLLECTED_4) && Statistics.goldCollected >= 7500) {
                badge = Badge.GOLD_COLLECTED_4;
                local.add(badge);
            }

            displayBadge(badge);
        }
	}
	
	public static void validateLevelReached() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {


            Badge badge = null;

            if (!local.contains(Badge.LEVEL_REACHED_1) && Dungeon.hero.lvl >= 16) {
                badge = Badge.LEVEL_REACHED_1;
                local.add(badge);
            }
            if (!local.contains(Badge.LEVEL_REACHED_2) && Dungeon.hero.lvl >= 21) {
                badge = Badge.LEVEL_REACHED_2;
                local.add(badge);
            }
            if (!local.contains(Badge.LEVEL_REACHED_3) && Dungeon.hero.lvl >= 26) {
                badge = Badge.LEVEL_REACHED_3;
                local.add(badge);
            }
            if (!local.contains(Badge.LEVEL_REACHED_4) && Dungeon.hero.lvl >= 31) {
                badge = Badge.LEVEL_REACHED_4;
                local.add(badge);
            }

            displayBadge(badge);
        }
	}

    public static void validateStrengthAttained() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;

            if (!local.contains(Badge.STRENGTH_ATTAINED_1) && Dungeon.hero.strBonus >= 1) {
                badge = Badge.STRENGTH_ATTAINED_1;
                local.add(badge);
            }
            if (!local.contains(Badge.STRENGTH_ATTAINED_2) && Dungeon.hero.strBonus >= 3) {
                badge = Badge.STRENGTH_ATTAINED_2;
                local.add(badge);
            }
            if (!local.contains(Badge.STRENGTH_ATTAINED_3) && Dungeon.hero.strBonus >= 6) {
                badge = Badge.STRENGTH_ATTAINED_3;
                local.add(badge);
            }
            if (!local.contains(Badge.STRENGTH_ATTAINED_4) && Dungeon.hero.strBonus >= 10) {
                badge = Badge.STRENGTH_ATTAINED_4;
                local.add(badge);
            }

            displayBadge(badge);
        }
    }

    public static void validateItemsUpgraded() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;

            if (!local.contains(Badge.ITEMS_UPGRADED_1) && Statistics.itemsUpgraded >= 1) {
                badge = Badge.ITEMS_UPGRADED_1;
                local.add(badge);
            }
            if (!local.contains(Badge.ITEMS_UPGRADED_2) && Statistics.itemsUpgraded >= 3) {
                badge = Badge.ITEMS_UPGRADED_2;
                local.add(badge);
            }
            if (!local.contains(Badge.ITEMS_UPGRADED_3) && Statistics.itemsUpgraded >= 6) {
                badge = Badge.ITEMS_UPGRADED_3;
                local.add(badge);
            }
            if (!local.contains(Badge.ITEMS_UPGRADED_4) && Statistics.itemsUpgraded >= 10) {
                badge = Badge.ITEMS_UPGRADED_4;
                local.add(badge);
            }

            displayBadge(badge);
        }
    }
	
	public static void validateFoodEaten() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;

            if (!local.contains(Badge.FOOD_EATEN_1) && Statistics.foodEaten >= 10) {
                badge = Badge.FOOD_EATEN_1;
                local.add(badge);
            }
            if (!local.contains(Badge.FOOD_EATEN_2) && Statistics.foodEaten >= 20) {
                badge = Badge.FOOD_EATEN_2;
                local.add(badge);
            }
            if (!local.contains(Badge.FOOD_EATEN_3) && Statistics.foodEaten >= 30) {
                badge = Badge.FOOD_EATEN_3;
                local.add(badge);
            }
            if (!local.contains(Badge.FOOD_EATEN_4) && Statistics.foodEaten >= 40) {
                badge = Badge.FOOD_EATEN_4;
                local.add(badge);
            }

            displayBadge(badge);
        }
	}
	
	public static void validatePotionsCooked() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;

            if (!local.contains(Badge.POTIONS_COOKED_1) && Statistics.potionsCooked >= 3) {
                badge = Badge.POTIONS_COOKED_1;
                local.add(badge);
            }
            if (!local.contains(Badge.POTIONS_COOKED_2) && Statistics.potionsCooked >= 6) {
                badge = Badge.POTIONS_COOKED_2;
                local.add(badge);
            }
            if (!local.contains(Badge.POTIONS_COOKED_3) && Statistics.potionsCooked >= 9) {
                badge = Badge.POTIONS_COOKED_3;
                local.add(badge);
            }
            if (!local.contains(Badge.POTIONS_COOKED_4) && Statistics.potionsCooked >= 12) {
                badge = Badge.POTIONS_COOKED_4;
                local.add(badge);
            }

            displayBadge(badge);
        }
	}
	
	public static void validatePiranhasKilled() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;

            if (!local.contains(Badge.PIRANHAS) && Statistics.piranhasKilled >= 6) {
                badge = Badge.PIRANHAS;
                local.add(badge);
            }

            displayBadge(badge);
        }
	}
	
//	public static void validateItemLevelAcquired(Item item) {
//
//        if ( Dungeon.difficulty > Difficulties.EASY ) {
//
//
//            // This method should be called:
//            // 1) When an item gets obtained (Item.collect)
//            // 2) When an item gets upgraded (ScrollOfUpgrade, ScrollOfWeaponUpgrade, ShortSword, WandOfMagicMissile)
//            // 3) When an item gets identified
//            if (item.known < Item.UPGRADE_KNOWN) {
//                return;
//            }
//
//            Badge badge = null;
//
//            if (item instanceof Weapon && ((Weapon) item).isEnchanted() ||
//                    item instanceof Armour && ((Armour) item).isInscribed()) {
//
//                if (!local.contains(Badge.ITEM_LEVEL_1) && item.bonus >= 0) {
//                    badge = Badge.ITEM_LEVEL_1;
//                    local.add(badge);
//                }
//                if (!local.contains(Badge.ITEM_LEVEL_2) && item.bonus >= 1) {
//                    badge = Badge.ITEM_LEVEL_2;
//                    local.add(badge);
//                }
//                if (!local.contains(Badge.ITEM_LEVEL_3) && item.bonus >= 2) {
//                    badge = Badge.ITEM_LEVEL_3;
//                    local.add(badge);
//                }
//                if (!local.contains(Badge.ITEM_LEVEL_4) && item.bonus >= 3) {
//                    badge = Badge.ITEM_LEVEL_4;
//                    local.add(badge);
//                }
//            }
//
//            displayBadge(badge);
//        }
//	}
	
	public static void validateAllPotionsIdentified() {

        if ( Dungeon.difficulty > Difficulties.EASY && Dungeon.hero != null && Dungeon.hero.isAlive() &&
			!local.contains( Badge.ALL_POTIONS_IDENTIFIED ) && Potion.allKnown()) {
			
			Badge badge = Badge.ALL_POTIONS_IDENTIFIED;
			local.add( badge );
			displayBadge( badge );
			
			validateAllItemsIdentified();
		}
	}
	
	public static void validateAllScrollsIdentified() {

        if ( Dungeon.difficulty > Difficulties.EASY && Dungeon.hero != null && Dungeon.hero.isAlive() &&
			!local.contains( Badge.ALL_SCROLLS_IDENTIFIED ) && Scroll.allKnown()) {
			
			Badge badge = Badge.ALL_SCROLLS_IDENTIFIED;
			local.add( badge );
			displayBadge( badge );
			
			validateAllItemsIdentified();
		}
	}
	
	public static void validateAllRingsIdentified() {

        if ( Dungeon.difficulty > Difficulties.EASY && Dungeon.hero != null && Dungeon.hero.isAlive() &&
			!local.contains( Badge.ALL_RINGS_IDENTIFIED ) && Ring.allKnown()) {
			
			Badge badge = Badge.ALL_RINGS_IDENTIFIED;
			local.add( badge );
			displayBadge( badge );
			
			validateAllItemsIdentified();
		}
	}
	
	public static void validateAllWandsIdentified() {

        if ( Dungeon.difficulty > Difficulties.EASY && Dungeon.hero != null && Dungeon.hero.isAlive() &&
			!local.contains( Badge.ALL_WANDS_IDENTIFIED ) && Wand.allKnown()) {
			
			Badge badge = Badge.ALL_WANDS_IDENTIFIED;
			local.add( badge );
			displayBadge( badge );
			
			validateAllItemsIdentified();
		}
	}
	
	public static void validateAllBagsBought( Item bag ) {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;
            if (bag instanceof HerbPouch) {
                badge = Badge.BAG_BOUGHT_SEED_POUCH;
            } else if (bag instanceof ScrollHolder) {
                badge = Badge.BAG_BOUGHT_SCROLL_HOLDER;
            } else if (bag instanceof WandHolster) {
                badge = Badge.BAG_BOUGHT_WAND_HOLSTER;
            }

            if (badge != null) {

                local.add(badge);

                if (!local.contains(Badge.ALL_BAGS_BOUGHT) &&
                        local.contains(Badge.BAG_BOUGHT_SCROLL_HOLDER) &&
                        local.contains(Badge.BAG_BOUGHT_SEED_POUCH) &&
                        local.contains(Badge.BAG_BOUGHT_WAND_HOLSTER)) {

                    badge = Badge.ALL_BAGS_BOUGHT;
                    local.add(badge);
                    displayBadge(badge);
                }
            }
        }
	}
	
	public static void validateAllItemsIdentified() {

        if ( Dungeon.difficulty > Difficulties.EASY &&
            !global.contains( Badge.ALL_ITEMS_IDENTIFIED ) &&
			global.contains( Badge.ALL_POTIONS_IDENTIFIED ) &&
			global.contains( Badge.ALL_SCROLLS_IDENTIFIED ) &&
			global.contains( Badge.ALL_RINGS_IDENTIFIED ) &&
			global.contains( Badge.ALL_WANDS_IDENTIFIED )) {
			
			Badge badge = Badge.ALL_ITEMS_IDENTIFIED;
			displayBadge(badge);
		}
	}
	
	public static void validateDeathFromFire() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = Badge.DEATH_FROM_FIRE;
            local.add(badge);
            displayBadge(badge);

            validateYASD();
        }
	}
	
	public static void validateDeathFromPoison() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = Badge.DEATH_FROM_POISON;
            local.add(badge);
            displayBadge(badge);

            validateYASD();
        }
	}
	
	public static void validateDeathFromGas() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = Badge.DEATH_FROM_GAS;
            local.add(badge);
            displayBadge(badge);

            validateYASD();
        }
	}
	
	public static void validateDeathFromHunger() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = Badge.DEATH_FROM_HUNGER;
            local.add(badge);
            displayBadge(badge);

            validateYASD();
        }
	}
	
	public static void validateDeathFromGlyph() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = Badge.DEATH_FROM_GLYPH;
            local.add(badge);
            displayBadge(badge);
        }
	}
	
	public static void validateDeathFromFalling() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = Badge.DEATH_FROM_FALLING;
            local.add(badge);
            displayBadge(badge);
        }
	}
	
	private static void validateYASD() {

        if ( Dungeon.difficulty > Difficulties.EASY &&
            global.contains( Badge.DEATH_FROM_FIRE ) &&
			global.contains( Badge.DEATH_FROM_POISON ) &&
			global.contains( Badge.DEATH_FROM_GAS ) &&
			global.contains( Badge.DEATH_FROM_HUNGER)) {

			Badge badge = Badge.YASD;
			local.add( badge );
			displayBadge( badge );
		}

	}
	
	public static void validateBossSlain() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {

            Badge badge = null;
            switch (Dungeon.depth) {
                case 6:
                    badge = Badge.BOSS_SLAIN_1;
                    break;
                case 12:
                    badge = Badge.BOSS_SLAIN_2;
                    break;
                case 18:
                    badge = Badge.BOSS_SLAIN_3;
                    break;
                case 24:
                    badge = Badge.BOSS_SLAIN_4;
                    break;
            }

            if (badge != null) {
                local.add(badge);
                displayBadge(badge);

                if (badge == Badge.BOSS_SLAIN_1) {
                    switch (Dungeon.hero.heroClass) {
                        case WARRIOR:
                            badge = Badge.BOSS_SLAIN_1_WARRIOR;
                            break;
                        case SCHOLAR:
                            badge = Badge.BOSS_SLAIN_1_MAGE;
                            break;
                        case BRIGAND:
                            badge = Badge.BOSS_SLAIN_1_ROGUE;
                            break;
                        case ACOLYTE:
                            badge = Badge.BOSS_SLAIN_1_HUNTRESS;
                            break;
                    }
                    local.add(badge);
                    if (!global.contains(badge)) {
                        global.add(badge);
                        saveNeeded = true;
                    }

                    if (global.contains(Badge.BOSS_SLAIN_1_WARRIOR) &&
                            global.contains(Badge.BOSS_SLAIN_1_MAGE) &&
                            global.contains(Badge.BOSS_SLAIN_1_ROGUE) &&
                            global.contains(Badge.BOSS_SLAIN_1_HUNTRESS)) {

                        badge = Badge.BOSS_SLAIN_1_ALL_CLASSES;
                        if (!global.contains(badge)) {
                            displayBadge(badge);
                            global.add(badge);
                            saveNeeded = true;
                        }
                    }
                } else if (badge == Badge.BOSS_SLAIN_3) {
                    switch (Dungeon.hero.subClass) {
                        case KNIGHT:
                            badge = Badge.BOSS_SLAIN_3_GLADIATOR;
                            break;
                        case BERSERKER:
                            badge = Badge.BOSS_SLAIN_3_BERSERKER;
                            break;
                        case CONJURER:
                            badge = Badge.BOSS_SLAIN_3_WARLOCK;
                            break;
                        case BATTLEMAGE:
                            badge = Badge.BOSS_SLAIN_3_BATTLEMAGE;
                            break;
                        case SLAYER:
                            badge = Badge.BOSS_SLAIN_3_FREERUNNER;
                            break;
                        case ASSASSIN:
                            badge = Badge.BOSS_SLAIN_3_ASSASSIN;
                            break;
                        case SNIPER:
                            badge = Badge.BOSS_SLAIN_3_SNIPER;
                            break;
                        case AMAZON:
                            badge = Badge.BOSS_SLAIN_3_WARDEN;
                            break;
                        default:
                            return;
                    }
                    local.add(badge);
                    if (!global.contains(badge)) {
                        global.add(badge);
                        saveNeeded = true;
                    }

                    if (global.contains(Badge.BOSS_SLAIN_3_GLADIATOR) &&
                            global.contains(Badge.BOSS_SLAIN_3_BERSERKER) &&
                            global.contains(Badge.BOSS_SLAIN_3_WARLOCK) &&
                            global.contains(Badge.BOSS_SLAIN_3_BATTLEMAGE) &&
                            global.contains(Badge.BOSS_SLAIN_3_FREERUNNER) &&
                            global.contains(Badge.BOSS_SLAIN_3_ASSASSIN) &&
                            global.contains(Badge.BOSS_SLAIN_3_SNIPER) &&
                            global.contains(Badge.BOSS_SLAIN_3_WARDEN)) {

                        badge = Badge.BOSS_SLAIN_3_ALL_SUBCLASSES;
                        if (!global.contains(badge)) {
                            displayBadge(badge);
                            global.add(badge);
                            saveNeeded = true;
                        }
                    }
                }
            }
        }
	}
	
	public static void validateMastery() {

        if ( Dungeon.difficulty > Difficulties.EASY ) {


            Badge badge = null;
            switch (Dungeon.hero.heroClass) {
                case WARRIOR:
                    badge = Badge.MASTERY_WARRIOR;
                    break;
                case SCHOLAR:
                    badge = Badge.MASTERY_SCHOLAR;
                    break;
                case BRIGAND:
                    badge = Badge.MASTERY_BRIGAND;
                    break;
                case ACOLYTE:
                    badge = Badge.MASTERY_ACOLYTE;
                    break;
            }

            if (!global.contains(badge)) {
                global.add(badge);
                saveNeeded = true;
            }
        }
	}
	
	public static void validateMasteryCombo( int n ) {

        if ( Dungeon.difficulty > Difficulties.EASY &&
            !local.contains( Badge.MASTERY_COMBO ) &&
            n == 10
        ) {
			Badge badge = Badge.MASTERY_COMBO;
			local.add( badge );
			displayBadge( badge );
		}
	}
	
	public static void validateRingOfHaggler() {
//		if (!local.contains( Badge.RING_OF_HAGGLER ) && new RingOfFortune().isTypeKnown()) {
//			Badge badge = Badge.RING_OF_HAGGLER;
//			local.add( badge );
//			displayBadge( badge );
//		}
	}
	
	public static void validateRingOfThorns() {
//		if (!local.contains( Badge.RING_OF_THORNS ) && new RingOfSorcery().isTypeKnown()) {
//			Badge badge = Badge.RING_OF_THORNS;
//			local.add( badge );
//			displayBadge( badge );
//		}
	}
	
	public static void validateRare( Mob mob ) {
		
//		Badge badge = null;
//		if (mob instanceof Albino) {
//			badge = Badge.RARE_ALBINO;
//		} else if (mob instanceof Bandit) {
//			badge = Badge.RARE_BANDIT;
//		} else if (mob instanceof Shielded) {
//			badge = Badge.RARE_SHIELDED;
//		} else if (mob instanceof Senior) {
//			badge = Badge.RARE_SENIOR;
//		} else if (mob instanceof Acidic) {
//			badge = Badge.RARE_ACIDIC;
//		}
//		if (!global.contains( badge )) {
//			global.add( badge );
//			saveNeeded = true;
//		}
//
//		if (global.contains( Badge.RARE_ALBINO ) &&
//			global.contains( Badge.RARE_BANDIT ) &&
//			global.contains( Badge.RARE_SHIELDED ) &&
//			global.contains( Badge.RARE_SENIOR ) &&
//			global.contains( Badge.RARE_ACIDIC )) {
//
//			badge = Badge.RARE;
//			displayBadge( badge );
//		}
	}
	
	public static void validateVictory() {

        Badge badge = null;

        if (Dungeon.difficulty >= Difficulties.EASY) {
            badge = Badge.VICTORY_0;
            local.add(badge);
        }
        if (Dungeon.difficulty >= Difficulties.NORMAL) {
            badge = Badge.VICTORY_1;
            local.add(badge);
        }
        if (Dungeon.difficulty >= Difficulties.HARDCORE) {
            badge = Badge.VICTORY_2;
            local.add(badge);
        }
        if (Dungeon.difficulty >= Difficulties.IMPOSSIBLE) {
            badge = Badge.VICTORY_3;
            local.add(badge);
        }

        displayBadge(badge);

        switch (Dungeon.hero.heroClass) {
            case WARRIOR:
                badge = Badge.VICTORY_WARRIOR;
                break;
            case SCHOLAR:
                badge = Badge.VICTORY_SCHOLAR;
                break;
            case BRIGAND:
                badge = Badge.VICTORY_BRIGAND;
                break;
            case ACOLYTE:
                badge = Badge.VICTORY_ACOLYTE;
                break;
        }

        local.add(badge);

        if (!global.contains(badge)) {
            global.add(badge);
            saveNeeded = true;
        }




//		if (global.contains( Badge.VICTORY_WARRIOR ) &&
//			global.contains( Badge.VICTORY_SCHOLAR) &&
//			global.contains( Badge.VICTORY_BRIGAND) &&
//			global.contains( Badge.VICTORY_ACOLYTE)) {
//
//			badge = Badge.VICTORY_ALL_CLASSES;
//			displayBadge( badge );
//		}

	}
	
	public static void validateNoKilling() {

        if ( Dungeon.difficulty > Difficulties.EASY &&
            !local.contains( Badge.NO_MONSTERS_SLAIN ) &&
            Statistics.completedWithNoKilling
        ) {
			Badge badge = Badge.NO_MONSTERS_SLAIN;
			local.add( badge );
			displayBadge( badge );
		}
	}
	
	public static void validateGrimWeapon() {
		if ( Dungeon.difficulty > Difficulties.EASY &&
            !local.contains( Badge.GRIM_WEAPON )
        ) {
			Badge badge = Badge.GRIM_WEAPON;
			local.add( badge );
			displayBadge( badge );
		}
	}
	
	public static void validateNightHunter() {
		if ( Dungeon.difficulty > Difficulties.EASY &&
            !local.contains( Badge.NIGHT_HUNTER ) &&
            Statistics.nightHunt >= 15
        ) {
			Badge badge = Badge.NIGHT_HUNTER;
			local.add( badge );
			displayBadge( badge );
		}
	}
	
	public static void validateSupporter() {

		global.add( Badge.SUPPORTER );
		saveNeeded = true;
		
//		PixelScene.showBadge( Badge.SUPPORTER );
	}
	
//	public static void validateGamesPlayed() {
//		Badge badge = null;
//		if (Rankings.INSTANCE.totalNumber >= 10) {
//			badge = Badge.GAMES_PLAYED_1;
//		}
//		if (Rankings.INSTANCE.totalNumber >= 100) {
//			badge = Badge.GAMES_PLAYED_2;
//		}
//		if (Rankings.INSTANCE.totalNumber >= 500) {
//			badge = Badge.GAMES_PLAYED_3;
//		}
//		if (Rankings.INSTANCE.totalNumber >= 2000) {
//			badge = Badge.GAMES_PLAYED_4;
//		}
//
//		displayBadge( badge );
//	}
	
	public static void validateHappyEnd() {
		displayBadge( Badge.HAPPY_END );
	}
	
	public static void validateChampion() {
		displayBadge( Badge.CHAMPION );
	}
	
	private static void displayBadge( Badge badge ) {
		
		if (badge == null) {
			return;
		}
		
		if (global.contains( badge )) {
			
			if (!badge.meta) {
				GLog.h( "获得徽章: %s", badge.description );
			}
			
		} else {
			
			global.add( badge );
			saveNeeded = true;
			
			if (badge.meta) {
				GLog.h( "新的超级徽章: %s", badge.description );
			} else {
				GLog.h( "新的徽章: %s", badge.description );
			}	
			PixelScene.showBadge( badge );
		}
	}
	
	public static boolean isUnlocked( Badge badge ) {
		return global.contains( badge );
	}
	
	public static void disown( Badge badge ) {
		loadGlobal();
		global.remove( badge );
		saveNeeded = true;
	}
	
	public static List<Badge> filtered( boolean global ) {
		
		HashSet<Badge> filtered = new HashSet<Badge>( global ? Badges.global : Badges.local );
		
		if (!global) {
			Iterator<Badge> iterator = filtered.iterator();
			while (iterator.hasNext()) {
				Badge badge = iterator.next();
				if (badge.meta) {
					iterator.remove();
				}
			}
		}
		
		leaveBest( filtered, Badge.MONSTERS_SLAIN_1, Badge.MONSTERS_SLAIN_2, Badge.MONSTERS_SLAIN_3, Badge.MONSTERS_SLAIN_4 );
		leaveBest( filtered, Badge.GOLD_COLLECTED_1, Badge.GOLD_COLLECTED_2, Badge.GOLD_COLLECTED_3, Badge.GOLD_COLLECTED_4 );
		leaveBest( filtered, Badge.BOSS_SLAIN_1, Badge.BOSS_SLAIN_2, Badge.BOSS_SLAIN_3, Badge.BOSS_SLAIN_4 );
		leaveBest( filtered, Badge.LEVEL_REACHED_1, Badge.LEVEL_REACHED_2, Badge.LEVEL_REACHED_3, Badge.LEVEL_REACHED_4 );
		leaveBest( filtered, Badge.STRENGTH_ATTAINED_1, Badge.STRENGTH_ATTAINED_2, Badge.STRENGTH_ATTAINED_3, Badge.STRENGTH_ATTAINED_4 );
		leaveBest( filtered, Badge.FOOD_EATEN_1, Badge.FOOD_EATEN_2, Badge.FOOD_EATEN_3, Badge.FOOD_EATEN_4 );
		leaveBest( filtered, Badge.ITEMS_UPGRADED_1, Badge.ITEMS_UPGRADED_2, Badge.ITEMS_UPGRADED_3, Badge.ITEMS_UPGRADED_4 );
		leaveBest( filtered, Badge.POTIONS_COOKED_1, Badge.POTIONS_COOKED_2, Badge.POTIONS_COOKED_3, Badge.POTIONS_COOKED_4 );
		leaveBest( filtered, Badge.BOSS_SLAIN_1_ALL_CLASSES, Badge.BOSS_SLAIN_3_ALL_SUBCLASSES );
		leaveBest( filtered, Badge.DEATH_FROM_FIRE, Badge.YASD );
		leaveBest( filtered, Badge.DEATH_FROM_GAS, Badge.YASD );
		leaveBest( filtered, Badge.DEATH_FROM_HUNGER, Badge.YASD );
		leaveBest( filtered, Badge.DEATH_FROM_POISON, Badge.YASD );
		leaveBest( filtered, Badge.VICTORY_0, Badge.VICTORY_1, Badge.VICTORY_2, Badge.VICTORY_3 );
//		leaveBest( filtered, Badge.VICTORY, Badge.VICTORY_ALL_CLASSES );
//		leaveBest( filtered, Badge.GAMES_PLAYED_1, Badge.GAMES_PLAYED_2, Badge.GAMES_PLAYED_3, Badge.GAMES_PLAYED_4 );
		
		ArrayList<Badge> list = new ArrayList<Badge>( filtered );
		Collections.sort( list );
		
		return list;
	}
	
	private static void leaveBest( HashSet<Badge> list, Badge...badges ) {
		for (int i=badges.length-1; i > 0; i--) {
			if (list.contains( badges[i])) {
				for (int j=0; j < i; j++) {
					list.remove( badges[j] );
				}
				break;
			}
		}
	}
}
