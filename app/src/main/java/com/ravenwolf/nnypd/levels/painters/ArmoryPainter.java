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
package com.ravenwolf.nnypd.levels.painters;

import com.ravenwolf.nnypd.Dungeon;
import com.ravenwolf.nnypd.items.Generator;
import com.ravenwolf.nnypd.items.Item;
import com.ravenwolf.nnypd.items.keys.IronKey;
import com.ravenwolf.nnypd.levels.Level;
import com.ravenwolf.nnypd.levels.Room;
import com.ravenwolf.nnypd.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class ArmoryPainter extends Painter {

	public static void paint( Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.EMPTY );
		
		Room.Door entrance = room.entrance();
		Point statue = null;
		if (entrance.x == room.left) {
			statue = new Point( room.right-1, Random.Int( 2 ) == 0 ? room.top+1 : room.bottom-1 );
		} else if (entrance.x == room.right) {
			statue = new Point( room.left+1, Random.Int( 2 ) == 0 ? room.top+1 : room.bottom-1 );
		} else if (entrance.y == room.top) {
			statue = new Point( Random.Int( 2 ) == 0 ? room.left+1 : room.right-1, room.bottom-1 );
		} else if (entrance.y == room.bottom) {
			statue = new Point( Random.Int( 2 ) == 0 ? room.left+1 : room.right-1, room.top+1 );
		}
		if (statue != null) {
			set( level, statue, Terrain.STATUE );
		}
		
		int n = 3 + Random.Int( Dungeon.chapter() / 3 + 2 );

		for (int i=0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY);
			level.drop( i == 0 ? Generator.random( Generator.Category.AMMO ) : prize(), pos, true );
		}
		
		entrance.set( Room.Door.Type.LOCKED );
		level.addItemToSpawn( new IronKey() );
	}
	
	private static Item prize() {
		return Generator.random( Random.oneOf(
                Generator.Category.ARMOR,
                Generator.Category.WEAPON,
                Generator.Category.THROWING,
				Generator.Category.THROWING
			) );
	}
}
