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
package com.ravenwolf.nnypd.levels.features;

import com.ravenwolf.nnypd.Dungeon;
import com.ravenwolf.nnypd.actors.Actor;
import com.ravenwolf.nnypd.items.scrolls.ScrollOfClairvoyance;
import com.ravenwolf.nnypd.levels.Level;
import com.ravenwolf.nnypd.levels.Terrain;
import com.ravenwolf.nnypd.scenes.GameScene;
import com.ravenwolf.nnypd.visuals.Assets;
import com.watabou.noosa.audio.Sample;

public class Door {

	public static void enter( int pos ) {
		Level.set(pos, Terrain.OPEN_DOOR);
		Dungeon.observe();
		
		if (Dungeon.visible[pos]) {
            GameScene.updateMap( pos );
			Sample.INSTANCE.play( Assets.SND_OPEN );
		}
	}
	
	public static void leave( int pos ) {
		if (Dungeon.level.heaps.get( pos ) == null) {
			Level.set( pos, Terrain.DOOR_CLOSED);
			Dungeon.observe();

            if (Dungeon.visible[pos]) {
                GameScene.updateMap( pos );
            }
		}
	}

    public static void discover( int pos ) {

        if( Dungeon.visible[pos] ) {

            GameScene.discoverTile( pos, Dungeon.level.map[pos] );

            Level.set( pos, Actor.findChar(pos) != null || Dungeon.level.heaps.get( pos ) != null ?
                    Terrain.OPEN_DOOR : Terrain.DOOR_CLOSED);

            GameScene.updateMap( pos );

            ScrollOfClairvoyance.discover( pos );

        }
    }
}
