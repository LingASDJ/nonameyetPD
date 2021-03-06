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
package com.ravenwolf.nnypd.levels;

import com.ravenwolf.nnypd.Bones;
import com.ravenwolf.nnypd.Dungeon;
import com.ravenwolf.nnypd.actors.Actor;
import com.ravenwolf.nnypd.actors.Char;
import com.ravenwolf.nnypd.actors.mobs.Bestiary;
import com.ravenwolf.nnypd.actors.mobs.DwarvenKing;
import com.ravenwolf.nnypd.actors.mobs.Mob;
import com.ravenwolf.nnypd.items.Heap;
import com.ravenwolf.nnypd.items.Item;
import com.ravenwolf.nnypd.items.keys.SkeletonKey;
import com.ravenwolf.nnypd.levels.painters.Painter;
import com.ravenwolf.nnypd.scenes.GameScene;
import com.ravenwolf.nnypd.visuals.Assets;
import com.watabou.noosa.Scene;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class CityBossLevel extends Level {
	
	{
		color1 = 0x4b6636;
		color2 = 0xf2f2f2;

//        viewDistance = 5;
	}
	
	private static final int TOP			= 2;
	private static final int HALL_WIDTH		= 11;
	private static final int HALL_HEIGHT	= 17;
	private static final int CHAMBER_HEIGHT	= 3;
	
	private static final int LEFT	= (WIDTH - HALL_WIDTH) / 2;
	private static final int CENTER	= LEFT + HALL_WIDTH / 2;

    private static final int THRONE	    = ( TOP + 2 ) * WIDTH + CENTER;
    private static final int PEDESTAL   = (TOP + HALL_HEIGHT / 2 ) * WIDTH + CENTER;
    private static final int FOUNTAIN	= (TOP + HALL_HEIGHT - 3 ) * WIDTH + CENTER;

	private static final int[] WELLS =
            {
                THRONE + WIDTH * 3 + 3,
                THRONE + WIDTH * 3 - 3,

                THRONE + WIDTH * 6 + 4,
                THRONE + WIDTH * 6 - 4,

                THRONE + WIDTH * 9 + 3,
                THRONE + WIDTH * 9 - 3,
            };

    private static final int[] STATUES =
            {
                    THRONE - WIDTH + 4,
                    THRONE - WIDTH - 4,

                    THRONE + WIDTH * 3 + 4,
                    THRONE + WIDTH * 3 - 4,

                    THRONE + WIDTH * 9 + 4,
                    THRONE + WIDTH * 9 - 4,
            };

	private int arenaDoor;
	private boolean enteredArena = false;
	private boolean keyDropped = false;
	
	@Override
	public String tilesTex() {
		return Assets.TILES_CITY;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_CITY;
	}
	
	private static final String DOOR	= "door";
	private static final String ENTERED	= "entered";
	private static final String DROPPED	= "dropped";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( DOOR, arenaDoor );
		bundle.put( ENTERED, enteredArena );
		bundle.put( DROPPED, keyDropped );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		arenaDoor = bundle.getInt( DOOR );
		enteredArena = bundle.getBoolean( ENTERED );
		keyDropped = bundle.getBoolean( DROPPED );
	}
	
	@Override
	protected boolean build() {
		
		Painter.fill( this, LEFT, TOP, HALL_WIDTH, HALL_HEIGHT, Terrain.EMPTY );

        Painter.fill( this, LEFT, TOP, HALL_WIDTH, 1, Terrain.EMBERS);
        Painter.fill( this, LEFT, TOP + HALL_HEIGHT - 1, HALL_WIDTH, 1, Terrain.BOOKSHELF );

		Painter.fill( this, CENTER, TOP + 2, 1, HALL_HEIGHT - 2, Terrain.EMPTY_SP );
		Painter.fill( this, CENTER - 1, TOP + 1, 3, 3, Terrain.EMPTY_SP );


//		int y = TOP + 1;
//		while (y < TOP + HALL_HEIGHT) {
//			map[y * WIDTH + CENTER - 5] = Terrain.STATUE_SP;
//			map[y * WIDTH + CENTER + 5] = Terrain.STATUE_SP;
//			y += 3;
//		}
		
//		int left = pedestal( true );
//		int right = pedestal( false );
//		map[left] = map[right] = Terrain.PEDESTAL;
//		for (int i=left+1; i < right; i++) {
//			map[i] = Terrain.EMPTY_SP;
//		}

        map[THRONE] = Terrain.PEDESTAL;
        map[THRONE - WIDTH] = Terrain.PEDESTAL;

//        map[THRONE - WIDTH + 1] = Terrain.EMPTY_WELL;
//        map[THRONE - WIDTH - 1] = Terrain.EMPTY_WELL;

        map[THRONE - WIDTH + 2] = Terrain.STATUE_SP;
        map[THRONE - WIDTH - 2] = Terrain.STATUE_SP;

//        map[THRONE + 1] = Terrain.STATUE_SP;
//        map[THRONE - 1] = Terrain.STATUE_SP;

//        map[THRONE + 1] = Terrain.EMPTY_WELL;
//        map[THRONE - 1] = Terrain.EMPTY_WELL;

//        map[THRONE - 3] = Terrain.STATUE_SP;
//        map[THRONE + 3] = Terrain.STATUE_SP;

//        map[THRONE - WIDTH - 1] = Terrain.PEDESTAL;
//        map[THRONE - WIDTH + 1] = Terrain.PEDESTAL;

//        map[THRONE + WIDTH * 11 - 3] = Terrain.STATUE;
//        map[THRONE + WIDTH * 11 + 3] = Terrain.STATUE;

//        map[THRONE - WIDTH - 2] = Terrain.STATUE_SP;
//        map[THRONE - WIDTH + 2] = Terrain.STATUE_SP;
//
//        map[THRONE - WIDTH - 4] = Terrain.STATUE;
//        map[THRONE - WIDTH + 4] = Terrain.STATUE;

//        map[THRONE + WIDTH * 2 + 4] = Terrain.STATUE;
//        map[THRONE + WIDTH * 2 - 4] = Terrain.STATUE;
//
//        map[THRONE + WIDTH * 8 + 4] = Terrain.STATUE;
//        map[THRONE + WIDTH * 8 - 4] = Terrain.STATUE;
//
//        map[THRONE + WIDTH * 10 + 3] = Terrain.STATUE;
//        map[THRONE + WIDTH * 10 - 3] = Terrain.STATUE;

        int y = TOP + 2;
		while (y < TOP + HALL_HEIGHT) {
			map[y * WIDTH + CENTER - 4] = Terrain.STATUE;
			map[y * WIDTH + CENTER + 4] = Terrain.STATUE;
			y += 3;
		}

        for( int altar : WELLS) {
            map[ altar ] = Terrain.EMPTY_WELL;
        }
//
//		int left = THRONE + WIDTH * 5 - 3;
//		int right = THRONE + WIDTH * 5 + 3;
//
//		for (int i=left+1; i < right; i++) {
//			map[i] = Terrain.EMPTY_SP;
//		}

//		map[arenaDoor - 1] = Terrain.WALL_SIGN;
//		map[arenaDoor + 1] = Terrain.WALL_SIGN;

        Painter.fill( this, CENTER - 1, TOP + HALL_HEIGHT - 4, 3, 3, Terrain.EMPTY_SP );
        Painter.fill( this, CENTER - 1, TOP + HALL_HEIGHT / 2 - 1, 3, 3, Terrain.EMPTY_SP );

        map[ PEDESTAL]  = Terrain.PEDESTAL;
        map[ FOUNTAIN ] = Terrain.WATER;

        map[FOUNTAIN + WIDTH + 2] = Terrain.STATUE_SP;
        map[FOUNTAIN + WIDTH - 2] = Terrain.STATUE_SP;

		Painter.fill( this, LEFT, TOP + HALL_HEIGHT + 1, HALL_WIDTH, CHAMBER_HEIGHT, Terrain.EMPTY );
		Painter.fill( this, LEFT, TOP + HALL_HEIGHT + 1, 1, CHAMBER_HEIGHT, Terrain.BOOKSHELF );
		Painter.fill( this, LEFT + HALL_WIDTH - 1, TOP + HALL_HEIGHT + 1, 1, CHAMBER_HEIGHT, Terrain.BOOKSHELF );

        Painter.fill( this, CENTER - 1, TOP + HALL_HEIGHT + 1, 3, 3, Terrain.EMPTY_SP );
        Painter.fill( this, LEFT + 1, TOP + HALL_HEIGHT + 1, 1, CHAMBER_HEIGHT, Terrain.EMPTY_SP );
        Painter.fill( this, LEFT + HALL_WIDTH - 2, TOP + HALL_HEIGHT + 1, 1, CHAMBER_HEIGHT, Terrain.EMPTY_SP );

        arenaDoor = (TOP + HALL_HEIGHT) * WIDTH + CENTER;
        map[arenaDoor] = Terrain.DOOR_CLOSED;
		
		entrance = (TOP + HALL_HEIGHT + CHAMBER_HEIGHT / 2 + 1 ) * WIDTH + LEFT + HALL_WIDTH / 2 ;
		map[entrance] = Terrain.ENTRANCE;

        exit = (TOP - 1) * WIDTH + CENTER;
        map[exit] = Terrain.LOCKED_EXIT;

//        WellWater water = (WellWater)blobs.get( WaterOfHealth.class );
//        if (water == null) {
//            try {
//                water = new WaterOfHealth();
//                water.seed( FOUNTAIN, 9 );
//            } catch (Exception e) {
//                water = null;
//            }
//        }

//        blobs.put( WaterOfHealth.class, water );
		
		return true;
	}
	
	@Override
	protected void decorate() {

		for (int i=0; i < LENGTH; i++) {
			if (map[i] == Terrain.EMPTY && Random.Int( 10 ) == 0) {
				map[i] = Terrain.EMPTY_DECO;
			} else if (map[i] == Terrain.WALL && Random.Int( 8 ) == 0) {
				map[i] = Terrain.WALL_DECO;
			}
		}
	}
	
	public static int pedestal() {
        return PEDESTAL;
	}

    public static int[] wells() {
        return WELLS;
    }

    public static int throne( boolean well ) {
        return THRONE - WIDTH + ( well ? 1 : -1 );
    }
	
	@Override
	protected void createMobs() {	
	}
	
	public Actor respawner() {
		return null;
	}
	
	@Override
	protected void createItems() {
		Item item = Bones.get();
		if (item != null) {
			int pos;
			do {
				pos = 
					Random.IntRange( LEFT + 1, LEFT + HALL_WIDTH - 2 ) + 
					Random.IntRange( TOP + HALL_HEIGHT + 1, TOP + HALL_HEIGHT  + CHAMBER_HEIGHT ) * WIDTH;
			} while (pos == entrance || map[pos] == Terrain.SIGN);
			drop( item, pos ).type = Heap.Type.BONES;
		}
	}
	
	@Override
    public int randomRespawnCell( boolean ignoreTraps, boolean ignoreView ) {

        int cell;

        if( !enteredArena ) {
            do {
                cell = super.randomRespawnCell( ignoreTraps, ignoreView );
            } while (outsideEntranceRoom(cell) );
        } else if( !keyDropped ) {
            do {
                cell = super.randomRespawnCell( ignoreTraps, ignoreView );
            } while (!outsideEntranceRoom(cell) );
        } else {

            cell = super.randomRespawnCell( ignoreTraps, ignoreView );
        }

        return cell;
    }
	
	@Override
	public void press( int cell, Char hero ) {
		
		super.press(cell, hero);

		if (!enteredArena && outsideEntranceRoom(cell) && hero == Dungeon.hero) {

			enteredArena = true;
			
			Mob boss = Bestiary.mob( Dungeon.depth );
			boss.state = boss.HUNTING;
            boss.pos = THRONE;
            ((DwarvenKing)boss).phase = true;

            GameScene.add( boss );
			
			if (Dungeon.visible[boss.pos]) {
				boss.notice();
				boss.sprite.alpha( 0 );
				boss.sprite.parent.add( new AlphaTweener( boss.sprite, 1, 0.1f ) );
			}

			set( arenaDoor, Terrain.LOCKED_DOOR );
			GameScene.updateMap( arenaDoor );
			Dungeon.observe();

            Music.INSTANCE.play( currentTrack(), true );
		}
	}
	
	@Override
	public Heap drop( Item item, int cell ) {
		
		if (!keyDropped && item instanceof SkeletonKey) {
			
			keyDropped = true;
			
			set( arenaDoor, Terrain.DOOR_CLOSED);
			GameScene.updateMap( arenaDoor );
			Dungeon.observe();

            Music.INSTANCE.play( currentTrack(), true );
		}
		
		return super.drop( item, cell );
	}
	
	private boolean outsideEntranceRoom(int cell) {
		return cell / WIDTH < arenaDoor / WIDTH;
	}

    @Override
    public boolean noTeleport() {
        return enteredArena && !keyDropped;
    }

    @Override
    public String tileName( int tile ) {
        return CityLevel.tileNames(tile);
    }

    @Override
    public String tileDesc( int tile ) {
        return CityLevel.tileDescs(tile);
    }

    @Override
    public void addVisuals( Scene scene ) {
        super.addVisuals( scene );
        CityLevel.addVisuals(this, scene);
    }

    public String currentTrack() {
        return enteredArena && !keyDropped ? Assets.TRACK_BOSS_LOOP : super.currentTrack();
    }
}
