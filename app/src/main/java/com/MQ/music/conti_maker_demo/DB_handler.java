package com.MQ.music.conti_maker_demo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DB_handler {
	
	private DB_helper dbhelper;
    private SQLiteDatabase db;
    private DB_query Query = new DB_query();
    
    private DB_handler(Context ctx) {
        this.dbhelper = new  DB_helper(ctx);
        this.db = dbhelper.getWritableDatabase();
    }
    
    
    public static DB_handler open(Context ctx)  throws SQLException{
    	
    	DB_handler handler = new DB_handler(ctx);    
        return handler;  
   }
    

    public String makeLike(String insert){
       	String out ="";
       	for(int i = 0 ; i < insert.length() ; i++){
       		if(insert.charAt(i) != ' '){out = out +"%"+insert.charAt(i);}
       	}
       	out = out+"%";
       	return out;
       }
    
    //=============================================================================================================================================================
	/*
     * MUSIC
     */
    public Cursor get_music_name() {
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC  ORDER BY m_name ASC" , null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_music_name(String m_name) {
    	
    	String selection[]={makeLike(m_name)};
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC WHERE m_name LIKE ?  ORDER BY m_name ASC" , selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }

    public Cursor get_music_words(String m_words) {
    	
    	String selection[]={makeLike(m_words)};
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC WHERE m_words LIKE ?  ORDER BY m_words ASC" , selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_music_code(String m_code) {
    	
    	String selection[]={String.valueOf(m_code)+"%"};
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC WHERE m_code LIKE ?  ORDER BY m_code , m_name ASC " , selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }

    public Cursor get_music(int m_id) throws SQLException{
    	
    	String selection[]={String.valueOf(m_id)};
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC WHERE m_id = ?  " , selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_music() throws SQLException{
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC  ORDER BY m_name ASC " ,null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_music_bag() {
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC WHERE m_bag = 1  ORDER BY m_name ASC " , null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    

    public int get_music_m_id(int position){
    	int out_m_id = position;
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC  ORDER BY m_name ASC " , null);
    	
    	if(cursor.moveToPosition(position)){
        	out_m_id = cursor.getInt(cursor.getColumnIndex("m_id"));
    	}
    	
    	cursor.close();
    	return out_m_id;
    }

    //=============================================================================================================================================================
	/*
     * MUSIC_SUB
     */

    public Cursor get_music_sub (int ms_id) throws SQLException{
    	
    	String selection[]={String.valueOf(ms_id)};
    	Cursor cursor = db.rawQuery("SELECT * FROM MUSIC_SUB WHERE ms_id = ?  ORDER BY ms_num ASC" , selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }

    //=============================================================================================================================================================
	/*
     * CONTI
     */
    public Cursor get_conti_music_mdate(){
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTINUITY ORDER BY c_mdate DESC , c_name ASC , c_writer ASC " ,null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_conti_music_name(){
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTINUITY ORDER BY c_name ASC, c_mdate DESC , c_writer ASC   " ,null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_conti_music_writer(){
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTINUITY ORDER BY  c_writer ASC, c_mdate DESC ,  c_name ASC" ,null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    
    public Cursor get_continuity(String c_name) throws SQLException{

    	String selection[]={c_name};
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTINUITY WHERE c_name = ?  " ,selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }

    

    //=============================================================================================================================================================
	/*
     * CONTI_MUSIC
     */
    
    public Cursor get_conti_music(String cm_c_name, int cm_num) throws SQLException{

    	String selection[]={cm_c_name , String.valueOf(cm_num)};
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTI_MUSIC , MUSIC  WHERE cm_m_id = m_id AND cm_c_name = ? AND cm_num = ? " ,selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_conti_music(String cm_c_name) throws SQLException{

    	String selection[]={cm_c_name};
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTI_MUSIC , MUSIC  WHERE cm_m_id = m_id and cm_c_name = ?  ORDER BY cm_num ASC " ,selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    public void insert_conti_music(String cm_c_name,int cm_num,int cm_m_id) {
    	
    	try {
    		db.execSQL( Query.Insert_CONTI_MUSIC( cm_c_name, cm_num, cm_m_id));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
    
    public void delete_conti_music(String cm_c_name) {
    	
    	try {
    		db.execSQL( Query.Delete_CONTI_MUSIC(cm_c_name));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
    }

    //=============================================================================================================================================================
    /*
     * CONTINUITY
     */
    public int get_size_c_name(String c_name){
    	int size;
    	String selection[]={c_name};
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTINUITY WHERE c_name = ? " ,selection);
    	size = cursor.getCount();
    	cursor.close();
    	return size;
    }

    public void delete_continuity(String c_name) {
    	
    	try {
    		db.execSQL( Query.Delete_CONTINUITY(c_name));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
    
    public void insert_continuity(String c_name,int c_mdate,String c_writer,String c_subject,String c_new) {
    	
    	try {
    		db.execSQL( Query.Insert_CONTINUITY(c_name, c_mdate, c_writer, c_subject ,c_new));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
    
    public void Update_CONTINUITY(String c_name){
 	   
 	   	db.execSQL( Query.Update_CONTINUITY(c_name));
   }

    //=============================================================================================================================================================
	/*
     * MEMBERS
     */
   public void Update_members(String mb_id, String mb_name,String mb_registrationId,String mb_etc) {
	   
	   	db.execSQL( Query.Update_MEMBERS(mb_id, mb_name, mb_registrationId, mb_etc));
  }


    public void insert_members(String mb_id, String mb_name,String mb_registrationId,String mb_etc) {
    	db.execSQL( Query.Insert_MEMBERS(mb_id, mb_name, mb_registrationId, mb_etc));
    	
    }
    
    public void delete_members(String mb_id) {
    	
    	try {
    		db.execSQL( Query.Delete_MEMBERS(mb_id));
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public Cursor get_members(String mb_id) throws SQLException{

    	String selection[]={mb_id};
    	Cursor cursor = db.rawQuery("SELECT * FROM MEMBERS WHERE mb_id = ?  " ,selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_members_name() throws SQLException{
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM MEMBERS ORDER BY mb_name ASC " ,null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    public Cursor get_members_id() throws SQLException{
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM MEMBERS ORDER BY mb_id ASC " ,null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }

    //=============================================================================================================================================================
	/*
     * CONTI_MEMBERS
     */

    public void insert_conti_members(String cmb_c_name, String cmb_mb_id) {
    	
    	try {
    		db.execSQL( Query.Insert_CONTI_MEMBERS(cmb_c_name, cmb_mb_id));
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }
    
    public void delete_conti_members(String c_name , String mb_id) {
    	
    	try {
    		db.execSQL( Query.Delete_CONTI_MEMBERS(c_name , mb_id));
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public void delete_conti_members_mb_id(String mb_id) {
    	
    	try {
    		db.execSQL( Query.Delete_CONTI_MEMBERS_cmb_mb_id(mb_id));
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    public void delete_conti_members_c_name(String cmb_c_name) {
    	
    	try {
    		db.execSQL( Query.Delete_CONTI_MEMBERS_cmb_c_name(cmb_c_name));
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    
    public Cursor get_conti_members(String c_name ,String mb_id) throws SQLException{

    	String selection[]={c_name, mb_id};
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTI_MEMBERS , MEMBERS , CONTINUITY WHERE cmb_c_name = c_name AND cmb_mb_id = mb_id AND cmb_c_name = ? AND cmb_mb_id = ? " ,selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }

    
    
    public Cursor get_conti_members_cmb_c_name(String c_name) throws SQLException{

    	String selection[]={c_name};
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTI_MEMBERS , MEMBERS , CONTINUITY WHERE cmb_c_name = c_name AND cmb_mb_id = mb_id AND cbm_c_name = ?  " ,selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    
    public Cursor get_conti_members_cmb_mb_id(String mb_id) throws SQLException{

    	String selection[]={mb_id};
    	Cursor cursor = db.rawQuery("SELECT * FROM CONTI_MEMBERS , MEMBERS , CONTINUITY WHERE cmb_c_name = c_name AND cmb_mb_id = mb_id AND cmb_mb_id = ? " ,selection);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }

    
    
    public Cursor get_conti_members() throws SQLException{

    	Cursor cursor = db.rawQuery("SELECT * FROM CONTI_MEMBERS , MEMBERS , CONTINUITY WHERE cmb_c_name = c_name AND cmb_mb_id = mb_id  ORDER BY cmb_c_name ASC " ,null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
     
   

    //=============================================================================================================================================================
	/*
     * System
     */
    public Cursor get_System(){
    	
    	Cursor cursor = db.rawQuery("SELECT * FROM SYSTEM " ,null);
        	if (cursor != null) { cursor.moveToFirst(); }
    	return cursor;
    }
    
    
   public void Insert_System(String s_id , String s_registrationId,String s_name,String s_etc, String date, String s_version){
	   db.execSQL( Query.Insert_SYSTEM(s_id, s_registrationId ,s_name,s_etc, date , "�ʱ�ȭ", s_version));
   }

   public void delete_System(String s_id) {
   	
   	try {
   		db.execSQL( Query.Delete_SYSTEM(s_id));
		} catch (Exception e) {
			// TODO: handle exception
		}
   	
   }
   
    
   public void Update_System_registrationId(String s_id	,String registrationId){
	   
	   	db.execSQL( Query.Update_SYSTEM_registrationId(s_id ,	registrationId	));
  }
   
   public void Update_System_ad(String s_id	,String s_ad){
	   
	   	db.execSQL( Query.Update_SYSTEM_ad(s_id ,	s_ad	));
  }
   
   
   public void Update_System_history(String s_id	,String s_history){
	   
	   	db.execSQL( Query.Update_SYSTEM_history(s_id,	s_history	));
  }
  
   
   public void Update_System_name_etc(String s_id	,String s_name,String s_etc){
	   
	   	db.execSQL( Query.Update_SYSTEM_name(s_id, s_name));
	   	db.execSQL( Query.Update_SYSTEM_etc(s_id, s_etc));
  }
    
    public void close() {
    	 dbhelper.close();
    }
    
	
}