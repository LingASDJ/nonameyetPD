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
import com.ravenwolf.nnypd.actors.blobs.WellWater;
import com.ravenwolf.nnypd.levels.Level;
import com.ravenwolf.nnypd.levels.Room;
import com.ravenwolf.nnypd.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class WellPainter extends Painter {

	public static void paint( Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.EMPTY );
		
		Point c = room.center();
		set( level, c.x, c.y, Terrain.WELL );

		WellWater water = (WellWater)level.blobs.get( WellWater.class );
		if (water == null) {
			try {
				water = new WellWater();
			} catch (Exception e) {
				water = null;
			}
		}

		int charges = 3 + Dungeon.chapter()/2 + Random.Int( 2 );

		water.seed( c.x + Level.WIDTH * c.y, charges );
		level.blobs.put( WellWater.class, water );
		
		room.entrance().set( Room.Door.Type.REGULAR );
	}
}
