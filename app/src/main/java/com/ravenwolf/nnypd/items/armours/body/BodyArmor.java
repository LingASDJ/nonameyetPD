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

import com.ravenwolf.nnypd.Dungeon;
import com.ravenwolf.nnypd.actors.buffs.special.Guard;
import com.ravenwolf.nnypd.actors.hero.Hero;
import com.ravenwolf.nnypd.items.armours.Armour;
import com.ravenwolf.nnypd.items.armours.glyphs.Durability;
import com.ravenwolf.nnypd.items.armours.glyphs.Featherfall;
import com.ravenwolf.nnypd.visuals.sprites.HeroSprite;
import com.ravenwolf.nnypd.visuals.ui.QuickSlot;

public abstract class BodyArmor extends Armour {

	public int appearance;

    public BodyArmor( int tier ) {

        super(tier);

    }

    public int getHauntedIndex(){
        return 3;
    }
	
	@Override
	public boolean doEquip( Hero hero ) {
		
		detach(hero.belongings.backpack);
		
		if ( ( hero.belongings.armor == null || hero.belongings.armor.doUnequip( hero, true, false ) ) &&
                ( !isCursed() || isCursedKnown() || !detectCursed( this, hero ) ) ) {

			hero.belongings.armor = this;

            ((HeroSprite)hero.sprite).updateArmor();

            onEquip( hero );

			hero.spendAndNext(time2equip(hero));

			return true;
			
		} else {

            QuickSlot.refresh();
            hero.spendAndNext(time2equip(hero) * 0.5f);
            if ( !collect( hero.belongings.backpack ) ) {
                Dungeon.level.drop( this, hero.pos ).sprite.drop();
            }
			return false;
			
		}
	}
	
	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		if (super.doUnequip( hero, collect, single )) {
            hero.remove(Guard.class);
			hero.belongings.armor = null;

			((HeroSprite)hero.sprite).updateArmor();
			
			return true;
			
		} else {
			
			return false;
			
		}
	}

    @Override
    public boolean isEquipped( Hero hero ) {
        return hero.belongings.armor == this;
    }

    @Override
    protected float time2equip( Hero hero ) {
        return super.speedFactor( hero ) * 3;
    }

    @Override
    public int dr( int bonus ) {
        //return tier * ( /*3*/2 + state ) +1
        return tier * 4/*( state +1)*/ +2
                //+ ( glyph instanceof Durability || bonus >= 0 ? tier * bonus : 0 )
                +tier * bonus
                //+ ( glyph instanceof Durability && bonus >= 0 ? /*2*/1 + bonus : 0 ) ;
                + ( glyph instanceof Durability & isCursedKnown()? !isCursed()? 1 + bonus : -tier : 0 ) ;
    }
    
    //@Override
    public int minDr( int bonus ) {
        return tier-1 +bonus;
    }

    public int minDr() {
        return minDr(bonus) ;
    }

    @Override
    public int str(int bonus) {
        //return 9 + tier * 4 - ( glyph instanceof Featherfall ? bonus : 0 );
        return 8 + tier * 4 - ( glyph instanceof Featherfall & isCursedKnown()? !isCursed()? 1 + bonus/2 : -1 : 0 ) - ( bonus+1)/2;
    }
	
	@Override
/*	public String info() {

        final String p = "\n\n";
        final String s = " ";

        int heroStr = Dungeon.hero.STR();
        int itemStr = strShown( isIdentified() );
        float penalty = currentPenalty(Dungeon.hero, strShown(isIdentified())) * 2.5f;
        int armor = Math.max(0, isIdentified() ? dr() : dr(0) );
        int minArmor = Math.max(0, isIdentified() ? minDr() : minDr(0) );

        StringBuilder info = new StringBuilder( desc() );

        info.append( p );

        if (isIdentified()) {
            info.append( "This _tier-" + tier + " " + ( !descType().isEmpty() ? descType() + " " : "" )  + "armor_ requires _" + itemStr + " points of strength_ to use effectively and" +

                    " will increase your _armor class by " +minArmor+ "-" + armor + " points_.");

            info.append( p );

            if (itemStr > heroStr) {
                info.append(
                        "Because of your inadequate strength, your stealth and dexterity in this armor " +
                                "will be _decreased by " + penalty + "%_ and your movement will be _" + (int)(100 - 10000 / (100 + penalty)) + "% slower_." );
            } else {
                info.append(
                        "While you are wearing this armor, your stealth and dexterity will " + ( penalty > 0 ? "be _decreased by " + penalty + "%_": "_not be decreased_" ) + "." );
            }
        } else {
            info.append(  "Usually _tier-" + tier + " " + ( !descType().isEmpty() ? descType() + " " : "" )  + "armors_ require _" + itemStr + " points of strength_ to be worn effectively and" +
                    " will increase your _armor class by " + minArmor+ "-" + armor+ " points_." );

            info.append( p );

            if (itemStr > heroStr) {
                info.append(
                        "Because of your inadequate strength, your stealth and dexterity in this armor " +
                                "probably will be _decreased by " + penalty + "%_ and your movement will be _" + (int)(100 - 10000 / (100 + penalty)) + "% slower_." );
            } else {
                info.append(
                        "While you are wearing this armor, your stealth and dexterity will " + ( penalty > 0 ? "be _decreased by " + penalty + "%_": "_not be decreased_" ) + "." );
            }
        }

        info.append( p );

        if (isEquipped( Dungeon.hero )) {

            info.append( "You are wearing the " + name + "." );

        } else if( Dungeon.hero.belongings.backpack.items.contains(this) ) {

            info.append( "The " + name + " is in your backpack." );

        } else {

            info.append( "The " + name + " lies on the dungeon's floor." );

        }

        info.append( s );

        if( isIdentified() && bonus > 0 ) {
            info.append( "It appears to be _upgraded_." );
        } else if( isCursedKnown() ) {
            info.append( !isCursed() ? "It appears to be _non-cursed_." :
                    "A malevolent _curse_ seems to be lurking within this " + name +"." );
        } else {
            info.append( " This " + name + " is _unidentified_." );
        }

        info.append( s );

        if( isEnchantKnown() && glyph != null ) {
            info.append( " " + ( isIdentified() && bonus != 0 ? "Also" : "However" ) + ", it seems to be _enchanted to " + glyph.desc(this) + "_." );
        }

        info.append( " This is a _" + lootChapterAsString() +"_ armor." );

        return info.toString();

	}*/
    public String info() {

        final String p = "\n\n";
        final String s = " ";

        int heroStr = Dungeon.hero.STR();
        int itemStr = strShown( isIdentified() );
        float penalty = currentPenalty(Dungeon.hero, strShown(isIdentified())) * 2.5f;
        int armor = Math.max(0, isIdentified() ? dr() : dr(0) );
        int minArmor = Math.max(0, isIdentified() ? minDr() : minDr(0) );

        StringBuilder info = new StringBuilder( desc() );

        info.append( p );

        if (isIdentified()) {
            info.append( "??????_" + tier + "???" + ( !descType().isEmpty() ? descType() + " " : "" )  + "??????_??????_" + itemStr + "?????????_??????????????????" +

                    "??????????????????_ " +minArmor+ "-" + armor + "?????????_???");

            info.append( p );

            if (itemStr > heroStr) {
                info.append(
                        "????????????????????????????????????????????????????????????????????????_??????" + penalty + "%_????????????????????????_" + (int)(100 - 10000 / (100 + penalty)) + "%???????????????_???" );
            } else {
                info.append(
                        "?????????????????????????????????????????????????????? " + (  penalty > 0 ? "???_??????" + penalty + "%_" : "_????????????_" ) + "???" );
            }
        } else {
            info.append(  "????????????_" + tier + "???" + ( !descType().isEmpty() ? descType() + " " : "" )  + "??????_??????_" + itemStr + "?????????_??????????????????" +
                    "??????????????????_" + minArmor+ "-" + armor+ "?????????_???" );

            info.append( p );

            if (itemStr > heroStr) {
                info.append(
                        "??????????????????????????????????????????????????????????????????????????????_??????" + penalty + "%_????????????????????????_" + (int)(100 - 10000 / (100 + penalty)) + "%???????????????_???" );
            } else {
                info.append(
                        "???????????????????????????????????????????????????  " +
                                ( penalty > 0 ? "???_??????" + penalty + "%_" : "_????????????_" ) +
                                ", ???????????????????????????????????????????????????" );
            }
        }

        info.append( p );

        if (isEquipped( Dungeon.hero )) {

            info.append( "???????????????" + name + "???" );

        } else if( Dungeon.hero.belongings.backpack.items.contains(this) ) {

            info.append( "??????" + name + "????????????????????????" );

        } else {

            info.append( "??????" + name + "??????????????????" );

        }

        info.append( s );

        if( isIdentified() && bonus > 0 ) {
            info.append( "???????????????_??????_???" );
        } else if( isCursedKnown() ) {
            info.append( !isCursed() ? "???????????????_???????????????_???" :
                    "?????????????????????????????????_??????_?????????"/* + name */ + "???" );
        } else {
            info.append( "??????" + name + "???_????????????_???" );
        }

        info.append( s );

        if( isEnchantKnown() && glyph != null ) {
            info.append( "" + ( isIdentified() && bonus != 0 ? "??????" : "??????" ) + "???????????????_" + glyph.desc(this) + "_????????????" );
        }

        info.append( "????????????_" + lootChapterAsString() +"_?????????" );

        return info.toString();

    }

}
