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
package com.ravenwolf.nnypd.scenes;

import com.ravenwolf.nnypd.Difficulties;
import com.ravenwolf.nnypd.Rankings;
import com.ravenwolf.nnypd.NoNameYetPixelDungeon;
import com.ravenwolf.nnypd.misc.utils.Utils;
import com.ravenwolf.nnypd.visuals.Assets;
import com.ravenwolf.nnypd.visuals.effects.Flare;
import com.ravenwolf.nnypd.visuals.sprites.ItemSprite;
import com.ravenwolf.nnypd.visuals.sprites.ItemSpriteSheet;
import com.ravenwolf.nnypd.visuals.ui.Archs;
import com.ravenwolf.nnypd.visuals.ui.ExitButton;
import com.ravenwolf.nnypd.visuals.ui.Icons;
import com.ravenwolf.nnypd.visuals.ui.Window;
import com.ravenwolf.nnypd.visuals.windows.WndError;
import com.ravenwolf.nnypd.visuals.windows.WndRanking;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.ravenwolf.nnypd.visuals.ui.RenderedTextMultiline;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;

public class RankingsScene extends PixelScene {
	
	private static final int DEFAULT_COLOR	= 0xCCCCCC;

    private static final String TXT_TITLE	    	= "排行榜(%s)";
    private static final String TXT_TOTAL		    = "游戏次数：%s/%s";
    //typo waiting to be fixed, will leave it for now
    private static final String TXT_HAS_NO_GAEMS    = "尚无%s难度下的游戏记录";

    private static final String TXT_NO_INFO     	= "无额外信息";
	
	private static final float ROW_HEIGHT_L	= 22;
	private static final float ROW_HEIGHT_P	= 28;
	
	private static final float MAX_ROW_WIDTH	= 180;
	
	private static final float GAP	= 4;

    int difficulty;
	
	private Archs archs;
    private DifficultyButton diffBtn0;
    private DifficultyButton diffBtn1;
    private DifficultyButton diffBtn2;
    private DifficultyButton diffBtn3;

	@Override
	public void create() {
		
		super.create();

        difficulty = NoNameYetPixelDungeon.lastDifficulty();
		
		Music.INSTANCE.play( Assets.TRACK_MAIN_THEME, true );
		Music.INSTANCE.volume( 1f );
		
		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;
		
		archs = new Archs();
		archs.setSize( w, h );
		add( archs );
		
		Rankings.INSTANCE.load( difficulty );

        float rowHeight = NoNameYetPixelDungeon.landscape() ? ROW_HEIGHT_L : ROW_HEIGHT_P;

        float left = (w - Math.min( MAX_ROW_WIDTH, w )) / 2 + GAP;
        float top = align( (h - rowHeight * Rankings.TABLE_SIZE ) / 2 - GAP );

        int pos = 0;

        //BitmapText title = PixelScene.createText( Utils.format( TXT_TITLE, Difficulties.NAMES[ difficulty ] ), 9 );
		RenderedText title = PixelScene.renderText( Utils.format( TXT_TITLE, Difficulties.NAMES[ difficulty ] ), 9 );
        title.hardlight( Window.TITLE_COLOR );
        //title.measure();
		PixelScene.align(title);
        title.x = align( (w - title.width()) / 2 );
        title.y = align( top - title.height() - GAP );
        add( title );

		if (Rankings.INSTANCE.records.size() > 0) {

			for (Rankings.Record rec : Rankings.INSTANCE.records) {
				Record row = new Record( pos, pos == Rankings.INSTANCE.lastRecord, rec );
				row.setRect( left, top + pos * rowHeight, w - left * 2, rowHeight );
				add( row );
				
				pos++;
			}
			
//			if (Rankings.INSTANCE.totalNumber >= Rankings.TABLE_SIZE) {
				//BitmapText label = PixelScene.createText( Utils.format( TXT_TOTAL, Rankings.INSTANCE.wonNumber, Rankings.INSTANCE.totalNumber ), 8 );
				RenderedText label = PixelScene.renderText( Utils.format( TXT_TOTAL, Rankings.INSTANCE.wonNumber, Rankings.INSTANCE.totalNumber ), 8 );
				label.hardlight( Rankings.INSTANCE.wonNumber > 0 ? Window.TITLE_COLOR : DEFAULT_COLOR );
				//label.measure();
				align(label);

                label.x = align( (w - label.width()) / 2 );
				label.y = align( top + pos * rowHeight + GAP );

				add( label );
//
//				BitmapText won = PixelScene.createText( Integer.toString( Rankings.INSTANCE.wonNumber ), 8 );
//				won.hardlight( Window.TITLE_COLOR );
//				won.measure();
//				add( won );



//				BitmapText total = PixelScene.createText( "/" + Rankings.INSTANCE.totalNumber, 8 );
//				total.hardlight( DEFAULT_COLOR );
//				total.measure();
//				total.x = align( (w - total.width()) / 2 );
//				total.y = align( top + pos * rowHeight + GAP );
//				add( total );
//
//				float tw = label.width() + won.width() + total.width();
//				float th = label.width() + won.width() + total.width();
//				label.x = align( (w - tw) / 2 );
//				won.x = label.x + label.width();
//				total.x = won.x + won.width();
//				label.y = won.y = total.y = align( top + pos * rowHeight + GAP );
//
//			}
			
		} else {
			
			//BitmapText noGaems = PixelScene.createMultiline( Utils.format( TXT_HAS_NO_GAEMS, Difficulties.NAMES[ difficulty ] ), 8 );
			RenderedText noGaems = PixelScene.renderText( Utils.format( TXT_HAS_NO_GAEMS, Difficulties.NAMES[ difficulty ] ), 8 );
            noGaems.hardlight( DEFAULT_COLOR );
            //noGaems.measure();
			align(noGaems);
            noGaems.x = align( (w - noGaems.width()) / 2 );
            noGaems.y = align( (h - noGaems.height()) / 2 );
			add( noGaems );

		}


        if( NoNameYetPixelDungeon.landscape() ) {

            diffBtn0 = new DifficultyButton(0);
            diffBtn0.setPos((w / 4 - diffBtn0.width()) / 2, ( h / 2 - diffBtn0.height() ) / 2);
            add(diffBtn0);

            diffBtn1 = new DifficultyButton(1);
            diffBtn1.setPos((w / 4 - diffBtn1.width()) / 2, h / 2 + ( h / 2 - diffBtn0.height() ) / 2);
            add(diffBtn1);

            diffBtn2 = new DifficultyButton(2);
            diffBtn2.setPos(w * 3 / 4 + (w / 4 - diffBtn2.width()) / 2, ( h / 2 - diffBtn0.height() ) / 2);
            add(diffBtn2);

            diffBtn3 = new DifficultyButton(3);
            diffBtn3.setPos(w * 3 / 4 + (w / 4 - diffBtn3.width()) / 2, h / 2 + ( h / 2 - diffBtn0.height() ) / 2);
            add(diffBtn3);

        } else {

            diffBtn0 = new DifficultyButton(0);
            diffBtn0.setPos((w / 4 - diffBtn0.width()) / 2, h - diffBtn0.height() - GAP * 2);
            add(diffBtn0);

            diffBtn1 = new DifficultyButton(1);
            diffBtn1.setPos(w / 4 + (w / 4 - diffBtn1.width()) / 2, h - diffBtn1.height() - GAP * 2);
            add(diffBtn1);

            diffBtn2 = new DifficultyButton(2);
            diffBtn2.setPos(w * 2 / 4 + (w / 4 - diffBtn2.width()) / 2, h - diffBtn2.height() - GAP * 2);
            add(diffBtn2);

            diffBtn3 = new DifficultyButton(3);
            diffBtn3.setPos(w * 3 / 4 + (w / 4 - diffBtn3.width()) / 2, h - diffBtn3.height() - GAP * 2);
            add(diffBtn3);

        }

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		NoNameYetPixelDungeon.switchNoFade(TitleScene.class);
	}
	
	public static class Record extends Button {
		
		private static final float GAP	= 4;
		
		private static final int TEXT_WIN	= 0xFFFF88;
		private static final int TEXT_LOSE	= 0xCCCCCC;
		private static final int FLARE_WIN	= 0x888866;
		private static final int FLARE_LOSE	= 0x666666;
		
		private Rankings.Record rec;

        private Flare flare;
        //private BitmapTextMultiline desc;
		private RenderedTextMultiline desc;

        private ItemSprite shield;
        private BitmapText position;

        private Image classIcon;
        private BitmapText chLevel;

		private Image floorIcon;
        private BitmapText flNumber;
		
		public Record( int pos, boolean latest, Rankings.Record rec ) {
			super();
			
			this.rec = rec;
			
			if (latest) {
				flare = new Flare( 6, 24 );
				flare.angularSpeed = 90;
				flare.color( rec.win ? FLARE_WIN : FLARE_LOSE );
				addToBack( flare );
			}
			
			position.text( Integer.toString( pos + 1 ) );
			position.measure();
			
			desc.text( rec.info );
			//desc.measure();
			align(desc);

            flNumber.text( Integer.toString( rec.depth ) );
            flNumber.measure();

            chLevel.text( Integer.toString( rec.level ) );
            chLevel.measure();
			
			if (rec.win) {
				shield.view( ItemSpriteSheet.AMULET, null );
				position.hardlight( TEXT_WIN );
                flNumber.hardlight( TEXT_WIN );
                chLevel.hardlight( TEXT_WIN );
				desc.hardlight( TEXT_WIN );
			} else {
				position.hardlight( TEXT_LOSE );
                flNumber.hardlight( TEXT_LOSE );
                chLevel.hardlight( TEXT_LOSE );
				desc.hardlight( TEXT_LOSE );
			}

            floorIcon.copy( Icons.get( Icons.STAIRS ) );
			classIcon.copy( Icons.get( rec.heroClass ) );
		}
		
		@Override
		protected void createChildren() {
			
			super.createChildren();

			shield = new ItemSprite( ItemSpriteSheet.TOMB, null );
			add( shield );
			
			position = new BitmapText( PixelScene.font1x );
			add( position );
			
			//desc = createMultiline( 7 );
			desc = renderMultiline( 7 );
			add( desc );

            floorIcon = new Image();
            add( floorIcon );


            flNumber = new BitmapText( PixelScene.font1x );
            add( flNumber );
			
			classIcon = new Image();
			add( classIcon );

            chLevel = new BitmapText( PixelScene.font1x );
            add( chLevel );
		}
		
		@Override
		protected void layout() {
			
			super.layout();
			
			shield.x = x;
			shield.y = y + (height - shield.height) / 2;
			
			position.x = align( shield.x + (shield.width - position.width()) / 2 );
			position.y = align( shield.y + (shield.height - position.height()) / 2 + 1 );
			
			if (flare != null) {
				flare.point( shield.center() );
			}

            floorIcon.x = align( x + width - floorIcon.width * 2 );
            floorIcon.y = shield.y;

            flNumber.x = align( floorIcon.x + (floorIcon.width - flNumber.width()) / 2 );
            flNumber.y = align( floorIcon.y + (floorIcon.height - flNumber.height()) / 2 + 1 );

            classIcon.x = align( x + width - classIcon.width );
            classIcon.y = shield.y;

            chLevel.x = align( classIcon.x + (classIcon.width - chLevel.width()) / 2 );
            chLevel.y = align( classIcon.y + (classIcon.height - chLevel.height()) / 2 + 1 );

            /*desc.x = shield.x + shield.width + GAP;
            desc.maxWidth = (int)(floorIcon.x - desc.x);
            desc.measure();
            desc.y = position.y + position.baseLine() - desc.baseLine();*/
			float x = shield.x + shield.width + GAP;
            desc.maxWidth((int)(floorIcon.x - x));
            align(desc);
            float y = position.y + position.baseLine() - desc.bottom();
            desc.setPos(x,y);
		}
		
		@Override
		protected void onClick() {
			if (rec.gameFile.length() > 0) {
				parent.add( new WndRanking( rec, rec.gameFile ) );
			} else {
				parent.add( new WndError( TXT_NO_INFO ) );
			}
		}
	}

    private class DifficultyButton extends Button {

        private Image image;
        private int difficulty;

        public DifficultyButton( int d ) {
            super();

            difficulty = d;

            image.copy( Icons.get( difficulty ) );
            image.am = ( NoNameYetPixelDungeon.lastDifficulty() == difficulty ? 1.0f : 0.5f );

            width = image.width;
            height = image.height;
        }

        @Override
        protected void createChildren() {

            super.createChildren();

            image = new Image();
            add( image );
        }

        @Override
        protected void layout() {

            super.layout();

            image.x = align( x );
            image.y = align( y  );
        }

        @Override
        protected void onClick() {

            NoNameYetPixelDungeon.lastDifficulty( difficulty );
            NoNameYetPixelDungeon.switchNoFade(RankingsScene.class);

        }

        @Override
        protected void onTouchDown() {
            Sample.INSTANCE.play(Assets.SND_CLICK );
        }
    }
}
