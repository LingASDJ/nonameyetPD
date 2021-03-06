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
package com.ravenwolf.nnypd.actors.buffs.bonuses;

import com.ravenwolf.nnypd.Dungeon;
import com.ravenwolf.nnypd.actors.Char;
import com.ravenwolf.nnypd.actors.buffs.debuffs.Ensnared;
import com.ravenwolf.nnypd.visuals.sprites.CharSprite;
import com.ravenwolf.nnypd.visuals.ui.BuffIndicator;

public class Levitation extends Bonus {

/*    @Override
    public String toString() {
        return "Levitating";
    }

    @Override
    public String statusMessage() { return "levitating"; }

    @Override
    public String playerMessage() { return "You start floating in the air!"; }*/
    @Override
    public String toString() {
        return "浮空";
    }

    @Override
    public String statusMessage() { return "浮空"; }

    @Override
    public String playerMessage() { return "你的双脚逐渐离地，身体向空中浮去！"; }

    @Override
    public int icon() {
        return BuffIndicator.LEVITATION;
    }

    @Override
    public void applyVisual(){
        target.sprite.add( CharSprite.State.LEVITATING );
    }

    @Override
    public void removeVisual() {
        target.sprite.remove( CharSprite.State.LEVITATING );
    }

    @Override
/*    public String description() {
        return "The whole body feels like it is lighter than air! While levitating, traps and " +
                "terrain effects do not affect you, and your movement speed is increased by half.";
    }*/
    public String description() {
        return "你感觉自己现在身轻如燕！在浮空状态时，陷阱和地面效果不会影响你，同时你的移动速度得到了增强。";
    }

    @Override
    public boolean attachOnLoad( Char target ) {
        target.flying = true;
        return super.attachOnLoad(target);
    }
	
	@Override
	public boolean attachTo( Char target ) {
		if (super.attachTo( target )) {
			target.flying = true;
			Ensnared.detach(target, Ensnared.class);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void detach() {
		target.flying = false;
		Dungeon.level.press( target.pos, target );
		super.detach();
	}
}
