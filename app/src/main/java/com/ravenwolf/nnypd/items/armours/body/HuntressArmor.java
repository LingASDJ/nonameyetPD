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
package com.ravenwolf.nnypd.items.armours.body;

import com.ravenwolf.nnypd.visuals.sprites.ItemSpriteSheet;

public class HuntressArmor extends BodyArmorCloth {

//	private static final String TXT_NO_ENEMIES 		= "No enemies in sight";
//	private static final String TXT_NOT_HUNTRESS	= "Only huntresses can use this armor!";
//
//	private static final String AC_SPECIAL = "SPECTRAL BLADES";

	{
//		name = "elven cloak";
        name = "精灵斗篷";

        image = ItemSpriteSheet.ARMOR_HUNTRESS;

        appearance = 3;
	}

    public HuntressArmor() {
        super( 1 );
    }

    public int getHauntedIndex(){
        return 1;
    }

    @Override
/*    public String desc() {
        return "Elven cloaks are usually valued because elven fabric reacts negatively at everything " +
                "touched by dark magicks or evil intentions. This effect is hardly noticeable for " +
                "anyone except the wearer of the cloak, but in a dungeon like this it certainly " +
                "comes in handy.";
    }*/
    public String desc() {
        return "因为精灵织物对一切黑暗魔法和恶意的能量都能产生一定的影响，所以精灵斗篷的价值远高于寻常布甲。" +
                "织物产生的反应几乎无法被使用者外的任何生物察觉，在地牢的环境中异常实用。";
    }

//    public HuntressArmor() {
//        super( 1 );
//    }

//	private HashMap<Callback, Mob> targets = new HashMap<Callback, Mob>();
//
//	@Override
//	public String special() {
//		return AC_SPECIAL;
//	}
//
//	@Override
//	public void doSpecial() {
//
//		Item proto = new Shuriken();
//
//		for (Mob mob : Dungeon.bonus.mobs) {
//			if (Level.fieldOfView[mob.pos]) {
//
//				Callback callback = new Callback() {
//					@Override
//					public void call() {
//						curUser.attack( targets.get( this ) );
//						targets.remove( this );
//						if (targets.isEmpty()) {
//							curUser.spendAndNext( curUser.attackDelay() );
//						}
//					}
//				};
//
//				((MissileSprite)curUser.sprite.parent.recycle( MissileSprite.class )).
//					fail( curUser.pos, mob.pos, proto, callback );
//
//				targets.put( callback, mob );
//			}
//		}
//
//		if (targets.size() == 0) {
//			GLog.w( TXT_NO_ENEMIES );
//			return;
//		}
//
//		curUser.HP -= (curUser.HP / 3);
//
//		curUser.sprite.cast( curUser.pos );
//		curUser.busy();
//	}
//
//	@Override
//	public boolean doEquip( Hero hero ) {
//		if (hero.heroClass == HeroClass.ACOLYTE) {
//			return super.doEquip( hero );
//		} else {
//			GLog.w( TXT_NOT_HUNTRESS );
//			return false;
//		}
//	}

//	@Override
//	public String desc() {
//		return
//			"A huntress in such cloak can create a fan of spectral blades. Each of these blades " +
//			"will target a single enemy in the huntress's field of view, inflicting damage depending " +
//			"on her currently equipped melee weapon.";

//	}
}