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
package com.ravenwolf.nnypd.visuals.effects.particles;

import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.Emitter.Factory;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Random;

public class RainParticle extends PixelParticle {

	public static final Factory FACTORY = new Factory() {
		@Override
		public void emit( Emitter emitter, int index, float x, float y ) {
			((RainParticle)emitter.recycle( RainParticle.class )).reset( x, y );
		}
		@Override
		public boolean lightMode() {
			return true;
		}
	};

	public RainParticle() {
		super();
        color(0x0088AA);
		lifespan = 1f;
		speed.set( 0, 8 );
	}
	
	private float offs;
	
	public void reset( float x, float y ) {
		revive();
		
		this.x = x;
		this.y = y;
		
		offs = -Random.Float( lifespan );
		left = lifespan - offs;
        angle = 15;
	}
	
	@Override
	public void update() {
		super.update();
		
		float p = left / lifespan;
		am = p < 0.5f ? p : 1 - p;
		scale.x = (1 - p) * 2;
		scale.y = 16 + (1 - p) * 16;
	}
}