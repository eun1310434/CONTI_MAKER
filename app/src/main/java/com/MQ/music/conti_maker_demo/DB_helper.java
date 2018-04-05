package com.MQ.music.conti_maker_demo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_helper extends SQLiteOpenHelper{
	
		private DB_query Query = new DB_query();// 생성자를 붙여주지 않으면 메모리의 위치를 다시 세팅하지 않기에 오류가 생긴다.
	
			public DB_helper(Context context) {
		
				super(context, "DB_ContiMaker.db", null,56);
				//2013-03-9-05:27 시험버젼 --------------> 	56 | Version code : 56
		
			}
		
			@Override
		    public void onCreate(SQLiteDatabase db) { 
				Create_Table(db);
				Insert_Table(db);
		    }
			
		    @Override
		    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 

		    	

		    	//db.execSQL(Query.Drop_CONTI_MEMBERS);
		    	//db.execSQL(Query.Drop_MEMBERS);
		    	db.execSQL(Query.Drop_MUSIC_SUB);
		    	//db.execSQL(Query.Drop_SYSTEM);
			  	//db.execSQL(Query.Drop_CONTI_MUSIC);
			  	//db.execSQL(Query.Drop_CONTINUITY);
			  	db.execSQL(Query.Drop_MUSIC);
			  	onCreate(db);
		    }
		     
		    public void Create_Table(SQLiteDatabase db){
				
		    	db.execSQL(Query.Create_MUSIC);
		    	db.execSQL(Query.Create_CONTINUITY);
			  	db.execSQL(Query.Create_CONTI_MUSIC);
		    	db.execSQL(Query.Create_SYSTEM);
		    	db.execSQL(Query.Create_MUSIC_SUB);
			  	db.execSQL(Query.Create_MEMBERS);
			  	db.execSQL(Query.Create_CONTI_MEMBERS);
		    }
		    
		    public void Insert_Table(SQLiteDatabase db){
		    	//----------------------------------------------------------------------------------------------------------	    	
		    	//MUSIC
		    	
				
/*				
try {	db.execSQL( Query.Insert_MUSIC(	1	,	"내 이름 아시죠"	,	"나를 지으신 주님"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	2	,	"감사와 찬양드리며"	,	"감사와 찬양드리며"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	3	,	"아름다우신"	,	"내 안에 주를 향한"	,	"Ab"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	4	,	"호흡 있는 모든 만물"	,	"호흡 있는 모든 만물"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	5	,	"주 사랑 놀라와"	,	"할렐루야"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	6	,	"나는 주의 친구"	,	"주님 어찌 날 생각하시는지"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	7	,	"나 무엇과도 주님을"	,	"나 무엇과도 주님을"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	8	,	"예수 나의 첫사랑 되시네"	,	"예수 나의 첫사랑 되시네"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	9	,	"마음의 예배"	,	"찬양의 열기"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	10	,	"내 삶 드리리"	,	"소망없는 내 삶에"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	11	,	"예수 닮기를"	,	"내 삶에 소망"	,	"B"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	12	,	"예수 열방의 소망"	,	"예수 열방의 소망"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	13	,	"오직 예수"	,	"주 발 앞에 나 엎드려"	,	"B"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	14	,	"믿음 따라"	,	"믿음 따라"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	15	,	"주 앞에 엎드려"	,	"주 앞에 엎드려 경배합니다"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	16	,	"돌아서지 않으리"	,	"주님 뜻대로 살기로 했네"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	17	,	"주님은 나의 사랑"	,	"주님은 나의 사랑"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	18	,	"불을 내려주소서"	,	"나는 아네 내가 살아 가는 이유"	,	"C"	,	R.drawable.wc18	,	1	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	19	,	"나의 맘 받으소서"	,	"나의 맘 받으소서"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	20	,	"우린 쉬지 않으리"	,	"우린 쉬지 않으리"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	21	,	"주의 나라가 임할 때"	,	"주의 나라가 임할 때"	,	"B"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	22	,	"주를 위한 이곳에"	,	"주를 위한 이곳에"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	23	,	"주 이름 찬양"	,	"주 이름 찬양"	,	"B"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	24	,	"만세반석"	,	"주님 같은 반석은 없도다"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	25	,	"주 하나님 지으신 모든 세계"	,	"주 하나님 지으신 모든 세계"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	26	,	"주 임재 안에서"	,	"내 모든것 나의 생명까지"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	27	,	"나의 반석이신 하나님"	,	"나의 반석이신 하나님"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	28	,	"기적이 일어나네"	,	"우리의 찬송 중에 임하신 주님"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	29	,	"나는 자유해"	,	"내가 주 찬송하리"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	30	,	"나를 향한 주의 사랑"	,	"나를 향한 주의 사랑"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	31	,	"나 기뻐하리"	,	"나 기뻐하리"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	32	,	"나의 안에 거하라"	,	"나의 안에 거하라"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	33	,	"내 영혼에 빛"	,	"내 영혼에 주의 빛 비춰주시니"	,	"Ab"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	34	,	"위대하신 주"	,	"빛 나는 왕의 왕"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	35	,	"모두 찬양해"	,	"sing sing sing"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	36	,	"갈급한 내 맘 만지시는 주"	,	"갈급한 내 맘 만지시는 주"	,	"Bb"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	37	,	"내 영혼이 은총 입어"	,	"내 영혼이 은총 입어"	,	"Ab"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	38	,	"새 힘 얻으리"	,	"새 힘 얻으리"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	39	,	"모두 외치리"	,	"주 앞에서 우리 기뻐 찬양하리"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	40	,	"멈출 수 없네"	,	"주 날 구원 했으니"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	41	,	"빛 되신 주"	,	"빛 되신 주"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	42	,	"내 기쁨 되신 주"	,	"주를 영원히 송축해"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	43	,	"나의 모습 나의 소유"	,	"나의 모습 나의 소유"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	44	,	"모든 열방 주 볼 때까지"	,	"내 눈 주의 영광을 보네"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	45	,	"찬송의 옷을 주셨네"	,	"내 손을 주께 높이 듭니다"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	46	,	"야곱의 축복"	,	"너는 담장 너머로 뻗은 나무"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	47	,	"당신은 사랑 받기 위해"	,	"당신은 사랑 받기 위해"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	48	,	"당신을 향한 노래"	,	"아주 먼 옛날 하늘에서는"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	49	,	"축복송"	,	"때로는 너의 앞에"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	50	,	"무화과 나뭇잎이 마르고"	,	"무화과 나뭇잎이 마르고"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	51	,	"물이 바다 덮음같이"	,	"세상 모든 민족이"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	52	,	"부흥 있으리라"	,	"부흥 있으리라 이 땅에"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	53	,	"세상이 당신을 모른다 하여도"	,	"세상이 당신을 모른다 하여도"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	54	,	"아바 아버지"	,	"아바 아버지"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	55	,	"예수 우리 왕이여"	,	"예수 우리 왕이여"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	56	,	"예수 피 밖에"	,	"보혈 세상의 모든"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	57	,	"우리 함께 기뻐해"	,	"우리 함께 기뻐해"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	58	,	"이 산지를 내게 주소서"	,	"주님이 주신 땅으로"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	59	,	"주 말씀 향하여"	,	"하늘의 하는 새도"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	60	,	"주 우리 아버지"	,	"주 우리 아버지"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	61	,	"주의 말씀 앞에 선"	,	"주의 말씀 앞에 선"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	62	,	"축복의 통로"	,	"당신은 하나님의 언약 안에"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	63	,	"하나님께서 당신을 통해"	,	"하나님께서 당신을 통해"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	64	,	"주만 바라볼찌라"	,	"하나님의 사랑을"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	65	,	"형제의 모습 속에"	,	"형제의 모습 속에"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	66	,	"주 나의 모든 것"	,	"약할 때 강함 되시네"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	67	,	"강물같은 주의 은혜"	,	"강물같은 주의 은혜"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	68	,	"찬양 할렐루야"	,	"거룩한 성전에 거하시며"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	69	,	"경배하리"	,	"경배하리"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	70	,	"그 사랑"	,	"아버지사랑 내가 노래해"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	71	,	"그의 생각"	,	"하나님은 너를 만드시는 분"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	72	,	"기뻐하며 왕께 노래부르리"	,	"기뻐하며 왕께 노래부르리"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	73	,	"나는 주만 높이리"	,	"나는 주만 높이리"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	74	,	"나의 마음을"	,	"나의 마음을"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	75	,	"나의 사랑이"	,	"나 주와 함께 걷기 원해요"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	76	,	"나의 슬픔을"	,	"나의 슬픔을"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	77	,	"나 주님의 기쁨"	,	"나 주님의 기쁨"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	78	,	"내가 주인삼은"	,	"내가 주인삼은"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	79	,	"내 마음 간절하게"	,	"내 마음 간절하게"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	80	,	"내 모든 삶의 행동"	,	"내 모든 삶의 행동"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	81	,	"내 평생에 가는 길"	,	"내 평생에 가는 길"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	82	,	"놀라운 십자가"	,	"주 달려 죽은 십자가"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	83	,	"당신은 영광의 왕"	,	"당신은 영광의 왕"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	84	,	"두손들고"	,	"두손들고"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	85	,	"마음이 상한 자를"	,	"마음이 상한 자를"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	86	,	"마지막 날에"	,	"마지막 날에"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	87	,	"말씀하시면"	,	"주님 말씀하시면"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	88	,	"모든 상황 속에서"	,	"모든 상황 속에서"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	89	,	"모든 능력과 모든 권세"	,	"모든 능력과 모든 권세"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	90	,	"모든 민족과 방언들 가운데"	,	"모든 민족과 방언들 가운데"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	91	,	"목마른 자들아"	,	"목마른 자들아"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	92	,	"문들아 머리 들어라"	,	"문들아 머리 들어라"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	93	,	"보혈을 지나"	,	"보혈을 지나"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	94	,	"부흥"	,	"이 땅의 황무함을 보소서"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	95	,	"부흥 2000"	,	"오소서 진리의 성령님"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	96	,	"비전"	,	"우리 보좌 앞에 모였네"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	97	,	"생명 주께 있네"	,	"생명 주께 있네"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	98	,	"선하신 목자"	,	"선하신 목자"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	99	,	"성령이여"	,	"성령이여"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	100	,	"손을 높이 들고"	,	"손을 높이 들고"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	101	,	"십자가"	,	"무엇이 변치 않아"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	102	,	"십자가의 길 순교자의 삶"	,	"내 마음에 주를 향한 사랑이"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	103	,	"영광 가장 높은 곳에"	,	"영광 가장 높은 곳에"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	104	,	"예배합니다"	,	"완전하신 나의 주"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	105	,	"예수는 왕"	,	"예수는 왕"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	106	,	"다시 한번"	,	"예수님 그의 희생 기억할 때"	,	"Eb"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	107	,	"성령의 불로"	,	"예수님 목마릅니다"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	108	,	"예수 사랑하심은"	,	"예수 사랑하심은"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	109	,	"예수 이름 높이세"	,	"수많은 무리들 줄지어"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	110	,	"예수 하나님의 공의"	,	"예수 하나님의 공의"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	111	,	"오셔서 다스리소서"	,	"산과 시내와 붉은 노을과"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	112	,	"오직 주의 사랑에 매여"	,	"오직 주의 사랑에 매여"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	113	,	"온 맘 다해"	,	"주님과 함께 하는 이 고요한 시간"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	114	,	"우리 모일 때 주 성령 임하리"	,	"우리 모일 때 주 성령 임하리"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	115	,	"우리 주 안에서 노래하며"	,	"우리 주 안에서 노래하며"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	116	,	"우리 함께 기도해"	,	"우리 함께 기도해"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	117	,	"우릴 사용하소서"	,	"우리에겐 소원이 하나있네"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	118	,	"유월절 어린양의 피로"	,	"유월절 어린양의 피로"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	119	,	"이 땅에 오직"	,	"이 땅에 오직"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	120	,	"이 땅 위에 오신"	,	"이 땅 위에 오신"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	121	,	"전심으로"	,	"주님 손에 맡겨 드리리"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	122	,	"좋으신 하나님"	,	"좋으신 하나님"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	123	,	"죄짐 맡은 우리 구주"	,	"죄짐 맡은 우리 구주"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	124	,	"주가 보이신 생명의 길"	,	"주가 보이신 생명의 길"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	125	,	"주께 가까이"	,	"주께 가까이"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	126	,	"주님 계신 곳에 나가리"	,	"주님 계신 곳에 나가리"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	127	,	"주님 다시 오실때까지"	,	"주님 다시 오실때까지"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	128	,	"주님 마음 내게 주소서"	,	"보소서 주님"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	129	,	"주님 보좌 앞에 나아가"	,	"주님 보좌 앞에 나아가"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	130	,	"주님 큰 영광 받으소서"	,	"주님 큰 영광 받으소서"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	131	,	"주님 한 분만으로"	,	"주님 한 분만으로"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	132	,	"주를 향한 나의 사랑을"	,	"주를 향한 나의 사랑을"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	133	,	"주 예수의 이름 높이세"	,	"주 예수의 이름 높이세"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	134	,	"주 음성 외에는"	,	"주 음성 외에는"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	135	,	"주의 이름 높이며 주를 찬양"	,	"주의 이름 높이며 주를 찬양"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	136	,	"주의 자비가 내려와"	,	"주의 자비가 내려와"	,	"D"	,	R.drawable.wc136	,	1	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	137	,	"주의 집에 영광이 가득해"	,	"주의 집에 영광이 가득해"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	138	,	"주의 친절한 팔에 안기세"	,	"주의 친절한 팔에 안기세"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	139	,	"주 이름 큰 능력 있도다"	,	"주 이름 큰 능력 있도다"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	140	,	"주 품에"	,	"주 품에"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	141	,	"죽임 당하신 어린양"	,	"죽임 당하신 어린양"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	142	,	"지금은 엘리야 때처럼"	,	"지금은 엘리야 때처럼"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	143	,	"찬양의 제사 드리며"	,	"주의 이름안에서"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	144	,	"풀은 마르고"	,	"풀은 마르고"	,	"A"	,	R.drawable.wc144	,	1	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	145	,	"하나님 아버지의 마음"	,	"아버지 당신의 마음에"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	146	,	"하나님 어린양 독생자 예수"	,	"하나님 어린양 독생자 예수"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	147	,	"하늘 위에 주님밖에"	,	"하늘 위에 주님밖에"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	148	,	"해뜨는 데부터"	,	"해뜨는 데부터"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	149	,	"천년이 두번 지나도"	,	"천년이 두번 지나도"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	150	,	"날 구원하신 주 감사"	,	"날 구원하신 주 감사"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	151	,	"아름답게 하리라"	,	"지금 우리가"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	152	,	"부르신 곳에서"	,	"따스한 성령님 마음으로 보네"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	153	,	"예수 피를 힘 입어"	,	"주의 보좌로 나아갈 때에"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	154	,	"예수님만을 더욱 사랑"	,	"예수님만을 더욱 사랑"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	155	,	"예수의 이름으로"	,	"예수의 이름으로"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	156	,	"주님의 은혜 넘치네"	,	"주 신실하심 놀라워"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	157	,	"주는 이 도시의 주"	,	"주는 이 도시의 주"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	158	,	"나의 가는 길"	,	"나의 가는 길"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	159	,	"또 하나의 열매를 바라시며"	,	"감사해요 깨닫지 못했었는데"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	160	,	"빈들에 마른 풀 같이"	,	"빈들에 마른 풀 같이"	,	"Bb"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	161	,	"성령이 오셨네"	,	"허무한시절 지날때"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	162	,	"예수 나의 치료자"	,	"예수 나의 좋은 치료자"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	163	,	"전능하신 나의 주 하나님"	,	"전능하신 나의 주 하나님"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	164	,	"주께 가까이 날 이끄소서"	,	"주께 가까이 날 이끄소서"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	165	,	"내 주 되신 주"	,	"주님의 임재안에"	,	"B"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	166	,	"임재"	,	"하늘의 문을 여소서"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	167	,	"주님은 산 같아서"	,	"안개가 날 가리워"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	168	,	"감사함으로"	,	"여호와를 즐거이 불러"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	169	,	"그가 오신 이유"	,	"이 세상 가장 아름다운"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	170	,	"기도전쟁"	,	"기도의 강한 용사여 "	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	171	,	"내 구주 예수를 더욱 사랑"	,	"내 구주 예수를 더욱 사랑"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	172	,	"너는 내 아들이라"	,	"힘들고 지쳐 낙망하고 넘어져"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	173	,	"선한 목자되신 우리 주"	,	"선한 목자되신 우리 주"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	174	,	"시선"	,	"내게로 부터 눈을 들어"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	175	,	"십자가의 전달자"	,	"난 지극히 작은 자 죄인 중"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	176	,	"온전케 되리"	,	"주 앞에 나와 제사를 드리네"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	177	,	"은혜로다"	,	"시작됐네 우리 주님의 능력이"	,	"D"	,	R.drawable.wc177	,	1	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	178	,	"이런 교회 되게 하소서"	,	"진정한 예배가 숨쉬는 교회"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	179	,	"이렇게 노래해"	,	"우리함께 소리 높여서"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	180	,	"주의 나라가 비전인 세대"	,	"열정의 세대 하나님나라 주의"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	181	,	"하나님께서 세상을 사랑하사"	,	"하늘보다 높으신 주의 사랑"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	182	,	"낮은 자의 하나님"	,	"나의 가장 낮은 마음"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	183	,	"성령의 불타는 교회"	,	"성령님이 임하시면"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	184	,	"그 이름 예수"	,	"세상이 어떤것으로도"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	185	,	"나로부터 시작되리"	,	"저높은 하늘위로 밝은 태양"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	186	,	"날마다"	,	"오 나의 주님 내게 생명주시니"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	187	,	"마라나타"	,	"마라타나 주 예수여"	,	"Bb"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	188	,	"천 번을 불러도"	,	"천 번을 불러봐도 내 눈에"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	189	,	"하나님의 은혜"	,	"나를 지으신이가 하나님"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	190	,	"그리스도의 계절"	,	"민족의 가슴마다"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	191	,	"약한 나로 강하게"	,	"약한 나로 강하게"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	192	,	"목마른 사슴"	,	"목마른 사슴"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	193	,	"살아계신 주"	,	"주 하나님 독생자 예수"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	194	,	"신실한 주 사랑"	,	"신실한 주 사랑"	,	"Ab"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	195	,	"Again 1907"	,	"백년전 이땅위에"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	196	,	"Higher"	,	"그 누가 뭐래도"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	197	,	"I want to see you"	,	"주님만 보기 원해요"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	198	,	"Jesus Generation"	,	"홍해앞에서 모세처럼"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	199	,	"Love is"	,	"사랑은 오래참고"	,	"Bb"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	200	,	"My Love"	,	"아침햇살에 잠에서 깨어 눈뜨면"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	201	,	"p_23"	,	"여호와는 나의 목자시니"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	202	,	"Today is The Day"	,	"억울하게 죽임을 당하시기까지"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	203	,	"Winning All"	,	"온세상 창조주"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	204	,	"You are Special"	,	"하늘의 별들처럼"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	205	,	"가난한 자 부요케 하소서"	,	"가난한 자 부요케 하소서"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	206	,	"가라"	,	"모든민족과 열방향하여 가라"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	207	,	"가라 너 주의 용사여"	,	"가라 너 주의 용사여"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	208	,	"가서 제자 삼으라"	,	"갈릴리마을 그 숲속에서"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	209	,	"가족"	,	"우리손에 가진것이"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	210	,	"갈릴리 바닷가에서"	,	"갈릴리 바닷가에서"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	211	,	"갈보리"	,	"슬픔걱정 가득차고"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	212	,	"갈보리 산 위에"	,	"갈보리 산 위에"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	213	,	"강하고 담대하라"	,	"강하고 담대하라"	,	"Bb"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	214	,	"강한 용사"	,	"강하고 담대하라"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	215	,	"강한 용사 무장하신"	,	"강한 용사 무장하신"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	216	,	"거룩 거룩 거룩 만군의 주여"	,	"거룩 거룩 거룩 만군의 주여"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	217	,	"거룩하신 전능의 주"	,	"거룩하신 전능의 주"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	218	,	"거룩하신 주님 지존하신 분"	,	"거룩하신 주님 지존하신 분"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	219	,	"거룩하신 하나님 주께 감사드리세"	,	"거룩하신 하나님 주께 감사드리세"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	220	,	"거룩한 성전에 거하시며"	,	"거룩한 성전에 거하시며"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	221	,	"거룩한 주 하나님"	,	"거룩한 주 하나님"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	222	,	"거리마다 기쁨으로"	,	"거리마다 기쁨으로"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	223	,	"겸손의 왕"	,	"왕 겸손의와 평범한 목수의"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	224	,	"겸손의 왕(주 발 앞에)"	,	"주 발 앞에 무릎 꿇게하사"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	225	,	"겸손하게 무릎 꿇고"	,	"겸손하게 무릎 꿇고"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	226	,	"경배와 사랑 드리네"	,	"경배와 사랑 드리네"	,	"Em"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	227	,	"하늘에 계신 아버지"	,	"하늘에 계신 아버지"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	228	,	"파송의 노래"	,	"너의 가는 길에"	,	"Bm"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	229	,	"날 향한 계획"	,	"내 앞에 주어진"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	230	,	"이삭의 축복"	,	"나의 사랑하는 자여"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	231	,	"내 마음에 가득채운"	,	"내 마음에 가득채운"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	232	,	"전부"	,	"나의 삶에 전부되신 주님"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	233	,	"경배하리 내 온맘 다해"	,	"경배하리 내 온맘 다해"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	234	,	"경배하리 주 하나님"	,	"경배하리 주 하나님"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	235	,	"계신 주님"	,	"나의 앞에 계신 주님"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	236	,	"고개 들어"	,	"고개 들어"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	237	,	"고멜의 노래"	,	"나를 바라볼때"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	238	,	"고백"	,	"어느날 다가온 주님의"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	239	,	"고백하지 못한 사랑"	,	"내가 주님을 얼마나"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	240	,	"고아들의 아버지"	,	"고아들의 아버지"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	241	,	"광대하신 주"	,	"광대하신 주"	,	"Bb"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	242	,	"광대하신 주님"	,	"광대하신 주님"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	243	,	"구속하신 주 찬양"	,	"나의 하나님 내 구주를"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	244	,	"구원이 하나님과"	,	"구원이 하나님과"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	245	,	"구하라"	,	"아버지께서 이세상 모든것"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	246	,	"구하라 모든 열방들을"	,	"구하라 모든 열방들을"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	247	,	"그 놀라운 사랑"	,	"Love 놀라운 사랑"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	248	,	"그 무엇보다"	,	"그 무엇보다"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	249	,	"그 분 따라 가려네"	,	"어두운 두눈 밝히시고"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	250	,	"그 분은 예수"	,	"왜 그러셨나요"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	251	,	"그 분의 길을 간다는 것은"	,	"그 분의 길을 간다는 것은"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	252	,	"그 이름"	,	"예수 그 이름"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	253	,	"그 이름 예수 만유의 주"	,	"그 이름 예수 만유의 주"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	254	,	"그 이름 예수 예수"	,	"그 이름 예수 예수"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	255	,	"그 크신 하나님의 사랑"	,	"그 크신 하나님의 사랑"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	256	,	"그 큰 일을 이루신 하나님께"	,	"그 큰 일을 이루신 하나님께"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	257	,	"그 피가"	,	"주님이 가신길"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	258	,	"그가 아시니"	,	"어떤 길로 그분 따르고"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	259	,	"그 날"	,	"사망의 그늘에 앉아"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	260	,	"그 날 이후"	,	"그대 지금 어디있나요"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	261	,	"그 날이 도적같이"	,	"그 날이 도적같이"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	262	,	"그 날이 오면"	,	"교회의 머리되신 주"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	263	,	"그는 사랑"	,	"형제의 그 눈빛속에서"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	264	,	"그는 아무런 죄도 없이"	,	"그는 아무런 죄도 없이"	,	"F#m"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	265	,	"그는 여호와 창조의 하나님"	,	"그는 여호와 창조의 하나님"	,	"Em"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	266	,	"그는 왕"	,	"모두나와 경배하며"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	267	,	"그래도"	,	"네가 나를 떠나가도"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	268	,	"그리 아니하실지라도"	,	"그리 아니하실지라도"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	269	,	"그 이름 높도다"	,	"그 이름 높도다"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	270	,	"글로리아"	,	"글로리아"	,	"Em"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	271	,	"기 높이 들고"	,	"기 높이 들고"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	272	,	"기다려요"	,	"기다려요"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	273	,	"기다리죠"	,	"세상에 있는 외로움을"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	274	,	"기대"	,	"주안에 우린 하나"	,	"Bb"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	275	,	"기도"	,	"마음이 어둡고 괴로울때"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	276	,	"기도의 집"	,	"이곳만민의 기도의 집"	,	"Dm"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	277	,	"기름 부으심"	,	"주 여호와의 신이"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	278	,	"기묘라 모사라"	,	"기묘라 모사라"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	279	,	"기뻐 찬양해"	,	"기뻐 찬양해"	,	"Em"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	280	,	"기뻐하라"	,	"아무리 힘겨워도"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	281	,	"기뻐하라 주 네 맘에"	,	"기뻐하라 주 네 맘에"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	282	,	"기뻐하며 승리의 노래 부르리"	,	"기뻐하며 승리의 노래 부르리"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	283	,	"기쁜 노래 주께 드리자"	,	"기쁜 노래 주께 드리자"	,	"Em"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	284	,	"기쁨으로 주 찬양하리"	,	"기쁨으로 주 찬양하리"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	285	,	"기쁨으로 찬양"	,	"네 모든 염려 무서운 짐"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	286	,	"기쁨의 노래"	,	"주가 지으신 주의 날에"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	287	,	"깊도다"	,	"깊도다"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	288	,	"깊어져 가네"	,	"광야 한 가운데 서면"	,	"F#m"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	289	,	"깨끗이 씻겨야 하리"	,	"부서져야 하리"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	290	,	"깨끗한 손 주옵소서"	,	"무릎꿇고 엎드리니"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	291	,	"깨어라 어둔 내 무덤에서"	,	"깨어라 어둔 내 무덤에서"	,	"A"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	292	,	"끝없는 사랑"	,	"하늘보다 높은 주의 사랑"	,	"F"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	293	,	"주님의 긍휼"	,	"주님의 긍휼"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	294	,	"주와 같이 길 가는 것"	,	"주와 같이 길 가는 것"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	295	,	"Miracle Generation"	,	"하늘의 불 내린"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	296	,	"놀라우신 주의 은혜"	,	"놀라우신 주의 은혜"	,	"D"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	297	,	"십자가"	,	"갈보리 십자가 그 안에"	,	"B"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	298	,	"신령과 진정으로"	,	"주를 찬양해"	,	"E"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	299	,	"사랑하셔서 오시었네"	,	"사랑하셔서 오시었네"	,	"C"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	300	,	"밀알"	,	"세상을 구원하기 위해"	,	"F"	,	R.drawable.wc300	,	1	));	} catch (Exception e) {}
try {	db.execSQL( Query.Insert_MUSIC(	301	,	"새 노래로"	,	"거룩한 주님의 성전에"	,	"G"	,	R.drawable.lite	,	0	));	} catch (Exception e) {}
				

		    	
		    	
		    	///


try {	db.execSQL( Query.Insert_MUSIC_SUB(		1	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		2	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		3	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		4	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		5	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		6	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		7	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		8	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		9	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		10	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		11	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		12	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		13	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		14	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		15	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		16	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		17	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		

try {	db.execSQL( Query.Insert_MUSIC_SUB(		19	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		20	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		21	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		22	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		23	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		24	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		25	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		26	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		27	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		28	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		29	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		30	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		31	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		32	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		33	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		34	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		35	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		36	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		37	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		38	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		39	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		40	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		41	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		42	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		43	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		44	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		45	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		46	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		47	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		48	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		49	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		50	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		51	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		52	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		53	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		54	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		55	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		56	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		57	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		58	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		59	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		60	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		61	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		62	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		63	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		64	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		65	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		66	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		67	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		68	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		69	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		70	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		71	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		72	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		73	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		74	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		75	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		76	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		77	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		78	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		79	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		80	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		81	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		82	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		83	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		84	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		85	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		86	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		87	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		88	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		89	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		90	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		91	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		92	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		93	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		94	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		95	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		96	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		97	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		98	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		99	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		100	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		101	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		102	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		103	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		104	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		105	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		106	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		107	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		108	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		109	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		110	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		111	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		112	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		113	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		114	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		115	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		116	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		117	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		118	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		119	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		120	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		121	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		122	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		123	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		124	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		125	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		126	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		127	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		128	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		129	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		130	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		131	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		132	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		133	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		134	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		135	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		

try {	db.execSQL( Query.Insert_MUSIC_SUB(		137	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		138	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		139	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		140	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		141	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		142	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		143	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		

try {	db.execSQL( Query.Insert_MUSIC_SUB(		145	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		146	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		147	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		148	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		149	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		150	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		151	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		152	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		153	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		154	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		155	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		156	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		157	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		158	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		159	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		160	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		161	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		162	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		163	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		164	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		165	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		166	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		167	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		168	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		169	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		170	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		171	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		172	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		173	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		174	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		175	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		176	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		

try {	db.execSQL( Query.Insert_MUSIC_SUB(		178	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		179	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		180	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		181	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		182	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		183	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		184	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		185	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		186	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		187	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		188	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		189	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		190	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		191	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		192	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		193	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		194	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		195	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		196	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		197	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		198	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		199	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		200	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		201	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		202	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		203	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		204	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		205	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		206	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		207	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		208	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		209	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		210	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		211	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		212	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		213	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		214	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		215	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		216	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		217	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		218	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		219	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		220	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		221	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		222	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		223	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		224	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		225	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		226	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		227	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		228	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		229	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		230	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		231	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		232	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		233	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		234	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		235	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		236	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		237	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		238	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		239	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		240	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		241	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		242	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		243	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		244	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		245	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		246	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		247	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		248	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		249	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		250	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		251	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		252	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		253	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		254	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		255	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		256	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		257	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		258	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		259	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		260	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		261	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		262	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		263	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		264	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		265	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		266	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		267	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		268	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		269	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		270	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		271	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		272	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		273	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		274	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		275	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		276	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		277	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		278	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		279	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		280	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		281	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		282	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		283	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		284	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		285	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		286	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		287	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		288	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		289	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		290	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		291	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		292	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		293	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		294	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		295	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		296	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		297	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		298	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		
try {	db.execSQL( Query.Insert_MUSIC_SUB(		299	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		

try {	db.execSQL( Query.Insert_MUSIC_SUB(		301	,	1	,	R.drawable.lite	));	} catch (Exception e) {}		

*/
			    //풀은 마르고
		    	try {	db.execSQL( Query.Insert_MUSIC(	144	,	"풀은 마르고"	,	"풀은 마르고"	,	"A"	,	R.drawable.wc144	,	0	));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	144	,	1, R.drawable.wc144_1));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	144	,	2, R.drawable.wc144_2));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	144	,	3, R.drawable.wc144_3));	} catch (Exception e) {}

		    	//불을 내려주소서
			    try {	db.execSQL( Query.Insert_MUSIC(	18	,	"불을 내려주소서"	,	"나는 아네 내가 살아 가는 이유"	,	"C"	,	R.drawable.wc18	,	1	));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	18	,	1, R.drawable.wc18_1));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	18	,	2, R.drawable.wc18_2));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	18	,	3, R.drawable.wc18_3));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	18	,	4, R.drawable.wc18_4));	} catch (Exception e) {}

		    	
			    //주의 자비가 내려와
			    try {	db.execSQL( Query.Insert_MUSIC(	136	,	"주의 자비가 내려와"	,	"주의 자비가 내려와"	,	"D"	,	R.drawable.wc136	,	1	));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	136	,	1, R.drawable.wc136_1));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	136	,	2, R.drawable.wc136_2));	} catch (Exception e) {}
		    
			    //은혜로다
			    try {	db.execSQL( Query.Insert_MUSIC(	177	,	"은혜로다"	,	"시작됐네 우리 주님의 능력이"	,	"D"	,	R.drawable.wc177	,	1	));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	177	,	1, R.drawable.wc177_1));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	177	,	2, R.drawable.wc177_2));	} catch (Exception e) {}

			    //밀알
			    try {	db.execSQL( Query.Insert_MUSIC(	300	,	"밀알"	,	"세상을 구원하기 위해"	,	"F"	,	R.drawable.wc300	,	1	));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	300	,	1, R.drawable.wc300_1));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	300	,	2, R.drawable.wc300_2));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	300	,	3, R.drawable.wc300_3));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	300	,	4, R.drawable.wc300_4));	} catch (Exception e) {}
			    try {	db.execSQL( Query.Insert_MUSIC_SUB(	300	,	5, R.drawable.wc300_5));	} catch (Exception e) {}
			    
		    
		    
		    }
		  
		    
}