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
package com.ravenwolf.nnypd.levels.traps;

import com.ravenwolf.nnypd.Dungeon;
import com.ravenwolf.nnypd.actors.Actor;
import com.ravenwolf.nnypd.actors.Char;
import com.ravenwolf.nnypd.actors.hero.Hero;
import com.ravenwolf.nnypd.items.rings.RingOfFortune;
import com.ravenwolf.nnypd.levels.Level;
import com.ravenwolf.nnypd.levels.Terrain;
import com.ravenwolf.nnypd.misc.utils.GLog;
import com.ravenwolf.nnypd.scenes.GameScene;
import com.ravenwolf.nnypd.visuals.Assets;
import com.ravenwolf.nnypd.visuals.effects.CellEmitter;
import com.ravenwolf.nnypd.visuals.effects.Speck;
import com.ravenwolf.nnypd.visuals.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public abstract class Trap {

    private static final String TXT_HIDDEN_PLATE_CLICKS = "隐藏的压力板发出脆响！";
    private static final String TXT_TRAPPED = "你发现了地上的陷阱";

    private static final String TXT_R_U_SURE =
            "你已经意识到了该地面上的陷阱。当你踩上去的时候陷阱就会被激活，通常来讲绝对不是件好事。你真的要踩进去吗？";

    private static final String TXT_YES			= "是的，我知道自己在做什么";
    private static final String TXT_NO			= "不，我改变主意了";

    public static boolean stepConfirmed = false;

    public static boolean itsATrap( int terrain ){
        switch( terrain ) {

            case Terrain.TOXIC_TRAP:
            case Terrain.FIRE_TRAP:
            case Terrain.BOULDER_TRAP:
            case Terrain.POISON_TRAP:
            case Terrain.ALARM_TRAP:
            case Terrain.LIGHTNING_TRAP:
            case Terrain.BLADE_TRAP:
            case Terrain.SUMMONING_TRAP:
                return true;

            default:
                return false;

        }
    }

    public static void askForConfirmation( final Hero hero ) {
        GameScene.show(
                new WndOptions( TXT_TRAPPED, TXT_R_U_SURE, TXT_YES, TXT_NO ) {
                    @Override
                    protected void onSelect( int index ) {
                        if (index == 0) {
                            stepConfirmed = true;
                            hero.resume();
                            stepConfirmed = false;
                        }
                    }
                }
        );
    }


    public static void trigger( int cell ) {

        Char ch = Actor.findChar( cell  );

        if (ch == Dungeon.hero) {
            Dungeon.hero.interrupt();
        }

        if( Dungeon.visible[cell] ) {

            if( ( Terrain.flags[ Dungeon.level.map[ cell ] ] & Terrain.TRAPPED ) != 0 ) {
                GLog.i(TXT_HIDDEN_PLATE_CLICKS);
            }

            Sample.INSTANCE.play( Assets.SND_TRAP);
        }


        int trap = Dungeon.level.map[cell];
        Level.set( cell, Terrain.INACTIVE_TRAP);
        GameScene.updateMap( cell );

        if (ch == Dungeon.hero) {
            if (Random.Float()<Dungeon.hero.ringBuffsBaseZero( RingOfFortune.Fortune.class )/2){
                GLog.i("由于某些原因，这些陷阱并没有被触发。");
                CellEmitter.get( ch.pos ).burst( Speck.factory( Speck.WOOL ), 4 );
                return;
            }
        }

        switch ( trap ) {

            case Terrain.SECRET_TOXIC_TRAP:
            case Terrain.TOXIC_TRAP:
                ToxicTrap.trigger( cell );
                break;

            case Terrain.SECRET_FIRE_TRAP:
            case Terrain.FIRE_TRAP:
                FireTrap.trigger( cell );
                break;

            case Terrain.SECRET_BOULDER_TRAP:
            case Terrain.BOULDER_TRAP:
                BoulderTrap.trigger( cell );
                break;

            case Terrain.SECRET_POISON_TRAP:
            case Terrain.POISON_TRAP:
                PoisonTrap.trigger( cell, ch );
                break;

            case Terrain.SECRET_ALARM_TRAP:
            case Terrain.ALARM_TRAP:
                AlarmTrap.trigger( cell );
                break;

            case Terrain.SECRET_LIGHTNING_TRAP:
            case Terrain.LIGHTNING_TRAP:
                LightningTrap.trigger( cell );
                break;

            case Terrain.SECRET_BLADE_TRAP:
            case Terrain.BLADE_TRAP:
                BladeTrap.trigger( cell, ch );
                break;

            case Terrain.SECRET_SUMMONING_TRAP:
            case Terrain.SUMMONING_TRAP:
                SummoningTrap.trigger( cell);
                break;
        }

    }
}