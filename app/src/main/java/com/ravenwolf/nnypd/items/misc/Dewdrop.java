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
package com.ravenwolf.nnypd.items.misc;

import com.ravenwolf.nnypd.actors.hero.Hero;
import com.ravenwolf.nnypd.items.Item;
import com.ravenwolf.nnypd.visuals.Assets;
import com.ravenwolf.nnypd.visuals.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class Dewdrop extends Item {

    public  static final int    NUTRIETY    = 6;

    private static final String TXT_VALUE	= "%+dHP";

	{
		name = "dewdrop";
		image = ItemSpriteSheet.DEWDROP;
		
		stackable = true;
	}
	
	@Override
	public boolean doPickUp( Hero hero ) {
		
		Waterskin vial = hero.belongings.getItem( Waterskin.class );

		if (vial == null || vial.isFull()) {

			return false;

			/*int power = Math.max( 1, ( hero.HT - hero.HP ) / 3 );
			
			int effect = Math.min( hero.HT - hero.HP, power * quantity );

			if ( effect > 0 ) {

				hero.HP += effect;
				hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				hero.sprite.showStatus(CharSprite.POSITIVE, TXT_VALUE, effect);
//                hero.buff( Hunger.class ).satisfy( NUTRIETY );

			} else {

                return false;

            }*/
			
		} else if (vial != null) {
			
			vial.collectDew( this );
			
		}
		
		Sample.INSTANCE.play( Assets.SND_DEWDROP );
//		hero.spendAndNext( TIME_TO_PICK_UP );
		
		return true;
	}
	
	@Override
	public String info() {
		return "A crystal clear dewdrop.";
	}
}
